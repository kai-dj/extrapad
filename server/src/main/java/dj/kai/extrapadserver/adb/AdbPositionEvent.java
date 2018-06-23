package dj.kai.extrapadserver.adb;

import java.util.EventObject;

public class AdbPositionEvent extends EventObject {

    private final int posX;
    private final int posY;


  public AdbPositionEvent(Object source, int posX, int posY) {
    super(source);
    this.posX=posX;
    this.posY=posY;
  }

    /**
     * @return touch position x-coordinate
     */
    public int getPosX() {
        return posX;
        }
    /**
     * @return touch position x-coordinate
     */
    public int getPosY() {
        return posY;
    }
}
