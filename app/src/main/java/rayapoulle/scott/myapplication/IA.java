package rayapoulle.scott.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by Scott on 28/02/2016.
 */
public class IA extends Player {

    public IA(Bitmap bitmap, Bitmap bulletBitmap, int x, int y, int fps, int frameCount) {
        super(bitmap, bulletBitmap, x, y, fps, frameCount);
        this.speed.xv = 0.9f;
        this.speed.yv = 0.9f;
    }


    public void update(long gameTime) {

        // This block make the ia change his direction sprite by depending his angle
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

        if (gameTime > frameTicker + framePeriod) {
            frameTicker = gameTime;
            // increment the frame
            currentFrame++;
            if (currentFrame >= frameNr) {
                currentFrame = 0;
            }


        }
        if (gameTime > fireTicker + (1000 / 5) && speed.xDirection != Speed.DIRECTION_NOX && speed.yDirection != Speed.DIRECTION_NOY) {
            bullets.add(new Bullet(bulletBitmap, x + (spriteWidth / 2), y + (spriteHeight / 2), speed.xDirection, speed.yDirection));
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
