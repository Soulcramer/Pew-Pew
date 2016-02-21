/**
 *
 */
package rayapoulle.scott.pewpew;

/**
 * The Speed class keeps track of the bearing of an object
 * in the 2D plane. It holds the speed values on both axis
 * and the directions on those. An object with the ability
 * to move will contain this class and the move method will
 * update its position according to the speed.
 *
 * @author impaler
 */
public class Speed {

    public static final int DIRECTION_RIGHT = 1;
    public static final int DIRECTION_LEFT = -1;
    public static final int DIRECTION_UP = -1;
    public static final int DIRECTION_DOWN = 1;
    public static final int DIRECTION_NOX = 0;
    public static final int DIRECTION_NOY = 0;

    public float xv = 1;    // velocity value on the X axis
    public float yv = 1;    // velocity value on the Y axis

    public float xDirection = DIRECTION_NOX;
    public float yDirection = DIRECTION_NOY;

    public Speed() {
        this.xv = 0;
        this.yv = 0;
    }

    public Speed(float xv, float yv) {
        this.xv = xv;
        this.yv = yv;
    }


    // changes the direction on the X axis
    public void toggleXDirection() {
        xDirection = xDirection * -1;
    }

    // changes the direction on the Y axis
    public void toggleYDirection() {
        yDirection = yDirection * -1;
    }

}
