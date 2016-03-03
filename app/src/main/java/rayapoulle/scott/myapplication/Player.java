package rayapoulle.scott.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Debug;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Scott on 18/02/2016.
 */
public class Player extends GameObject {

    private static final String TAG = Player.class.getSimpleName();


    public int spritedir;
    public Bitmap bulletBitmap;
    public boolean moving;
    public double fireTicker;
    public double angle;

    public ArrayList<Bullet> bullets;


    public Player(Bitmap bitmap, Bitmap bulletBitmap, int x, int y, int fps, int frameCount) {
        this.bitmap = bitmap;
        this.bulletBitmap = bulletBitmap;
        this.x = x;
        this.y = y;
        currentFrame = 0;
        frameNr = frameCount;
        spriteWidth = bitmap.getWidth() / frameCount;
        spriteHeight = bitmap.getHeight() / 8;
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        framePeriod = 1000 / fps;
        frameTicker = 0L;
        fireTicker = 0L;
        speed = new Speed(0.7f, 0.7f);
        angle = this.speed.angle;
        destRect = new Rect(x, y, x + spriteWidth, y + spriteHeight);
        bullets = new ArrayList<>();

    }


    public void update(long gameTime) {


        // This block make the player change his direction sprite by depending his angle
        if ( (angle = -Math.toDegrees(Math.atan2(this.speed.xDirection, this.speed.yDirection))) > 0){
            if (angle <= 22.5) {
                spritedir = 0;
            } else if (angle > 22.5 && angle <= 67.5) {
                spritedir = 1;
            } else if (angle > 67.5 && angle <= 112.5) {
                spritedir = 2;
            } else if (angle > 112.5 && angle <= 157.5) {
                spritedir = 3;
            } else if (angle > 157.5) {
                spritedir = 4;
            }
        }else{
            if (angle >= -22.5) {
                spritedir = 0;
            } else if (angle < -22.5 && angle >= -67.5) {
                spritedir = 7;
            } else if (angle < -67.5 && angle >= -112.5) {
                spritedir = 6;
            } else if (angle < -112.5 && angle >= -157.5) {
                spritedir = 5;
            } else if (angle < -157.5) {
                spritedir = 4;
            }
        }
        //Log.d(TAG, "update: " + angle);



        if (gameTime > frameTicker + framePeriod) {
            frameTicker = gameTime;
            // increment the frame
            currentFrame++;
            if (currentFrame >= frameNr) {
                currentFrame = 0;
            }


        }
        if (gameTime > fireTicker + (1000/5) && speed.xDirection != Speed.DIRECTION_NOX && speed.yDirection != Speed.DIRECTION_NOY){
            bullets.add(new Bullet(bulletBitmap, x+(spriteWidth/2), y+(spriteHeight/2), speed.xDirection, speed.yDirection));
            fireTicker = gameTime;
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
        destRect.set(x, y, x + spriteWidth, y + spriteHeight);

        canvas.drawBitmap(bitmap, sourceRect, destRect, null);
    }

}
