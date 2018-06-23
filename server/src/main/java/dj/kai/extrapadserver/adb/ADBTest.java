package dj.kai.extrapadserver.adb;

import dj.kai.extrapadserver.serverevents.fireXdotool;

public class ADBTest implements AdbPositionListener {
    /**
     * Test/playground for {@link AdbPositionListener}.
     */
    AdbWatcher adbWatcher;

    public ADBTest() {
        runAdb();

    }

    public void runAdb() {
        //TODO multiple Watchers redundant - just add listeners
        adbWatcher = new AdbWatcher();
        adbWatcher.addAdbListener(this);
    }

    @Override
    public void AdbPositionReceived(AdbPositionEvent e) {
        System.out.println(""+e.getSource()+"   "+e.getPosX()+"x"+e.getPosY());
        System.out.println(fireXdotool.sendKey("x"));
    }
}