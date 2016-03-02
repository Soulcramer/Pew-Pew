package rayapoulle.scott.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Log;

public class Bullet extends GameObject {

    private static final String TAG = Bullet.class.getSimpleName();

    public Bullet(Bitmap bitmap, int x, int y, float xDirection, float yDirection, float angle) {



        //currentFrame = 0;
        //frameNr = frameCount;


        //framePeriod = 1000 / fps;
        //frameTicker = 0l;
        speed = new Speed(2, 2);
        this.speed.angle = angle;
        Log.d(TAG, "Bullet: " + speed.angle);
        speed.xDirection = xDirection;
        speed.yDirection = yDirection;
        Matrix matrix = new Matrix();
        matrix.postRotate(speed.angle);
        this.bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        spriteWidth = bitmap.getHeight();
        spriteHeight = bitmap.getHeight();
        this.x = x-(spriteWidth/2);
        this.y = y/*-(spriteHeight/2)*/;
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        destRect = new Rect(x, y, x + spriteWidth, y + spriteHeight);

        //bullets = new ArrayList<>();

    }


    public void update(long gameTime) {
        x += (speed.xv * speed.xDirection);
        y += (speed.yv * speed.yDirection);

    }

    public void draw(Canvas canvas) {
        // where to draw the sprite


        destRect.set(x,y,x+spriteWidth,y+spriteHeight);
        canvas.drawBitmap(bitmap, sourceRect, destRect, null);
    }
}
