package rayapoulle.scott.pewpew;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Scott on 18/02/2016.
 */
public class PlayerAnimated {

    private static final String TAG = PlayerAnimated.class.getSimpleName();

    public Bitmap bitmap;        // the animation sequence
    public int x;                // the X coordinate of the object (top left of the image)
    public int y;                // the Y coordinate of the object (top left of the image)
    public int spritedir;
    public boolean moving;
    public Speed speed;
    private Rect sourceRect;    // the rectangle to be drawn from the animation bitmap
    private int frameNr;        // number of frames in animation
    private int currentFrame;    // the current frame
    private long frameTicker;    // the time of the last frame update
    private int framePeriod;    // milliseconds between each frame (1000/fps)
    private int spriteWidth;    // the width of the sprite to calculate the cut out rectangle
    private int spriteHeight;    // the height of the sprite


    public PlayerAnimated(Bitmap bitmap, int x, int y, int fps, int frameCount) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        currentFrame = 0;
        frameNr = frameCount;
        spriteWidth = bitmap.getWidth() / frameCount;
        spriteHeight = bitmap.getHeight() / 8;
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        framePeriod = 1000 / fps;
        frameTicker = 0l;
        speed = new Speed(0.5f, 0.5f);
    }


    public void update(long gameTime) {
        if (gameTime > frameTicker + framePeriod) {
            frameTicker = gameTime;
            // increment the frame
            currentFrame++;
            if (currentFrame >= frameNr) {
                currentFrame = 0;
            }
        }
        // define the rectangle to cut out sprite
        this.sourceRect.left = currentFrame * spriteWidth;
        this.sourceRect.right = this.sourceRect.left + spriteWidth;
        this.sourceRect.top = this.spritedir * spriteHeight;
        this.sourceRect.bottom = this.sourceRect.top + spriteHeight;

        x += (speed.xv * speed.xDirection);
        y += (speed.yv * speed.yDirection);
    }

    public void draw(Canvas canvas) {
        // where to draw the sprite

        Rect destRect = new Rect(x, y, x + spriteWidth, y + spriteHeight);
        canvas.drawBitmap(bitmap, sourceRect, destRect, null);
    }

    public void handleActionDown(float eventX, float eventY) {
        float tmpx;
        float tmpy;
        /*if (eventX > x + spriteWidth) {
            speed.setxDirection(Speed.DIRECTION_RIGHT);
            spritedir = 6;
        }
        if (eventX < x) {
            speed.setxDirection(Speed.DIRECTION_LEFT);
            spritedir = 2;
        }
        if (eventY > y + spriteHeight) {
            speed.setyDirection(Speed.DIRECTION_DOWN);
            spritedir = 0;
        }
        if (eventY < y) {
            speed.setyDirection(Speed.DIRECTION_UP);
            spritedir = 4;
        }*/
        float diffX = eventX - this.x;
        float diffY = eventY - this.y;
        float length = (float) Math.sqrt(diffX * diffX + diffY * diffY);
        float normalizedX = diffX / length;
        float normalizedY = diffY / length;

        float angle = (float) Math.acos(normalizedX);
        /*if (diffY > 0.0f) {
            rotation = (float)Math.toDegrees(angle);
        } else {
            rotation = (float)Math.toDegrees(-angle);
        }*/

        float deltaX = normalizedX * 10;
        float deltaY = normalizedY * 10;
        speed.xDirection = deltaX;
        speed.yDirection = deltaY;
        /*if ((eventX > x && eventX < x + spriteWidth) && (eventY > y && eventY < y + spriteHeight)) {
            speed.setxDirection(Speed.DIRECTION_NOX);
            speed.setyDirection(Speed.DIRECTION_NOY);
        }else if(eventX > x + spriteWidth){
            tmpy = (eventX-x)/(eventY-y);
            Log.d(TAG, "handleActionDown: " + (eventX-x)/(eventY-y));
            tmpx = 1;
            speed.setxDirection(tmpx);
            speed.setyDirection(tmpy);
        }*/


        /*if (eventX >= (x - spriteWidth / 2) && (eventX <= (x + spriteWidth / 2))) {
            if (eventY >= (y - spriteHeight / 2) && (y <= (y + spriteHeight / 2))) {
                // droid touched
                this.moving = true;

            } else {
                this.moving = false;
            }
        } else {
            this.moving = false;
        }*/
    }

}
