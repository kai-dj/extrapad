package dj.kai.extrapadserver.adb;

import java.util.EventListener;

public interface AdbPositionListener extends EventListener {
    /**
     * Called when AbdPosition is send from device via System.out.
     */
    void AdbPositionReceived(AdbPositionEvent e);
}
