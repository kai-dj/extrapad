package dj.kai.extrapadserver.serverevents;

import java.io.IOException;

/** Tools to execute xdotool (sending key and mouse events in linux system).
 *
 * @author kai lüking
 * @author www.kai.dj
 */
public class fireXdotool {
    //TODO check which xdotool-bin is used - or integrate portable version
    private static String xdotoolCMD = "/usr/bin/xdotool";

    /**
     * Executes a keypress with xdotool.
     * @param key   the key to press - either one char or unicode
     * @return      true if xdotool executed successful - false if not successful (or no single char)
     */
    public static boolean sendKey(String key) {
        // check if single char oder unicode
        if (key.length()>1 && !key.startsWith("U")) {
            return false;
        }
        String[] cmd = {xdotoolCMD,"key",key};
        Runtime rt = Runtime.getRuntime();
        try {
            rt.exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
