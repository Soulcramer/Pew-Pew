package rayapoulle.scott.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Background {

    public Bitmap image;
    public int x, y, h, w;


    public Background(Bitmap res, int w, int h) {
        /*Bitmap scaled = Bitmap.createScaledBitmap(image, w, h, true); // Make sure w and h are in the correct order
        mScaledBackground = scaled;*/
        this.h = h;
        this.w = w;
        this.image = res;
    }

    public void draw(Canvas canvas) {
       /* Rect src = new Rect(0,0,image.getWidth()-1, image.getHeight()-1);
        Rect dest = new Rect(0,0,w-1, h-1);
        canvas.drawBitmap(image, src, dest, null);*/
        canvas.drawBitmap(image, 0, 0, null);

    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

}
