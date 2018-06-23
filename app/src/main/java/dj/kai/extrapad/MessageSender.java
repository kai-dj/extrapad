package dj.kai.extrapad;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by aik on 2/17/18.
 */

public class MessageSender extends AsyncTask<String,Void,Void> {
    Socket s;
    DataOutputStream dos;
    PrintWriter pw;

    @Override
    protected Void doInBackground(String... voids) {
        String message = voids[0];
        try
        {
            s = new Socket("192.168.2.123",8888);
            pw= new PrintWriter(s.getOutputStream());
            pw.write(message);
            pw.flush();
            pw.close();
            s.close();
            System.out.println(message+" send");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
