package rayapoulle.scott.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by Scott on 26/02/2016.
 */
public class Ball extends GameObject {

    private static final String TAG = Player.class.getSimpleName();


    public int spritedir;
    public boolean moving;


    public Ball(Bitmap bitmap, int x, int y) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        currentFrame = 0;
        spriteWidth = bitmap.getWidth();
        spriteHeight = bitmap.getHeight();
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        frameTicker = 0L;
        speed = new Speed(0.7f, 0.7f);
        destRect = new Rect(x, y, x + spriteWidth, y + spriteHeight);

    }


    public void update(long gameTime) {

        x += (speed.xv * speed.xDirection);
        y += (speed.yv * speed.yDirection);
    }

    public void draw(Canvas canvas) {
        // where to draw the sprite
        destRect.set(x, y, x + spriteWidth, y + spriteHeight);

        canvas.drawBitmap(bitmap, sourceRect, destRect, null);
    }
}
