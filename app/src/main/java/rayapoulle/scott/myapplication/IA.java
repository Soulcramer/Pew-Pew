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
        if (gameTime > frameTicker + framePeriod) {
            frameTicker = gameTime;
            // increment the frame
            currentFrame++;
            if (currentFrame >= frameNr) {
                currentFrame = 0;
            }


        }
        if (gameTime > fireTicker + (1000/5) && speed.xDirection != Speed.DIRECTION_NOX && speed.yDirection != Speed.DIRECTION_NOY){
            bullets.add(new Bullet(bulletBitmap, x+(spriteWidth/2), y+(spriteHeight/2), speed.xDirection, speed.yDirection, speed.angle));
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
