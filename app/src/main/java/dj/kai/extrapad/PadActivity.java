package dj.kai.extrapad;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class PadActivity extends AppCompatActivity implements View.OnTouchListener{
    ImageView padImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pad);
        padImage= (ImageView) findViewById(R.id.pad_view);
        padImage.setOnTouchListener(this);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            //MessageSender messageSender = new MessageSender();
            //messageSender.execute(motionEvent.getX()+"X"+motionEvent.getY());
            System.out.println("Touch coordinates : " +
                    String.valueOf(motionEvent.getX()) + "x" + String.valueOf(motionEvent.getY()));
        }
        return false;
    }
}
