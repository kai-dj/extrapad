package dj.kai.extrapadserver.adb;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Watch adb logcat for ".*?System\\.out.*?Touch.*?coordinates.*?(\\d{1,}).*?x(\\d{1,}).*"
 * Fire {@link AdbPositionEvent} when corresponding sout is found.
 *
 * @author kai lüking
 * @author www.kai.dj
 */
public class AdbWatcher implements Serializable, Runnable {
    private transient Vector listeners;
    private boolean running;
    // regex for touch coordinates in logs like I/System.out( 1234): Touch coordinates : 134.0x326.0
    private static Pattern soutPattern =
            Pattern.compile(".*?System\\.out.*?Touch.*?coordinates.*?(\\d{1,}).*?x(\\d{1,}).*");
    private Matcher matcher;

    /**
     * Start Thread to watch for adb logcat output.
     */
    public AdbWatcher() {
        System.out.println("AdbWatcher construcing - running Thread");
        new Thread(this).start();
    }

    /**
     * Start adb process, fire Event when matching String is found in logcat.
     */
    private void adbWatchLog() {
        //TODO maybe change to portable
        String[] adbLogcatCMD = {"/usr/bin/adb", "-d", "logcat", "System.out:I", "*:S"};
        Runtime rt = Runtime.getRuntime();
        Process adbLogcatProcess = null;
        try {
            adbLogcatProcess = rt.exec(adbLogcatCMD);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(adbLogcatProcess.getInputStream()));
        /*
        //TODO handle errors
        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(adbLogcatProcess.getErrorStream()));
        */
        String logcatBufferString;
        try {
            while ((logcatBufferString = stdInput.readLine()) != null && this.running) {
                matcher = soutPattern.matcher(logcatBufferString);
                if (matcher.find() && matcher.groupCount() == 2) {
                    fireAdbPositionEvent(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clears the adb log, so old events don't fire anything.
     * @return false if something went wrong
     */
    private boolean adbClearLog() {
        String[] adbClearCMD = {"/usr/bin/adb", "logcat", "-c"};
        Runtime rt = Runtime.getRuntime();
        Process adbLogcatClearProcess;
        try {
            adbLogcatClearProcess = rt.exec(adbClearCMD);
            adbLogcatClearProcess.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    /**
     * Register a listener for AdbEvents
     */
    synchronized public void addAdbListener(AdbPositionListener l) {
        if (listeners == null) {
            listeners = new Vector();
        }
        listeners.addElement(l);
    }

    /**
     * Remove a listener for AdbEvents
     */
    synchronized public void removeAdbListener(AdbPositionListener l) {
        if (listeners == null)
            listeners = new Vector();
        listeners.removeElement(l);
    }

    /**
     * Fire a AdbPositionEvent to all registered listeners
     */
    protected void fireAdbPositionEvent(int xPosition, int yPosition) {
        // if we have no listeners, do nothing...
        if (listeners != null && !listeners.isEmpty()) {
            // create the event object to send
            AdbPositionEvent event =
                    new AdbPositionEvent(this, xPosition, yPosition);

            // make a copy of the listener list in case
            //   anyone adds/removes listeners
            Vector targets;
            synchronized (this) {
                targets = (Vector) listeners.clone();
            }

            // walk through the listener list and
            //   call the AdbPositionReceived method in each
            Enumeration e = targets.elements();
            while (e.hasMoreElements()) {
                AdbPositionListener l = (AdbPositionListener) e.nextElement();
                l.AdbPositionReceived(event);
            }
        }
    }

    /**
     * runs watcher in background.
     */
    @Override
    public void run() {
        this.running = true;
        if (adbClearLog()) {
            System.out.println("Logcat cleared - start monitoring logcat");
            adbWatchLog();
        }
    }

    //TODO add option to stop watcher and deregister every listener
    public void stop() {
        this.running = false;
    }
}

