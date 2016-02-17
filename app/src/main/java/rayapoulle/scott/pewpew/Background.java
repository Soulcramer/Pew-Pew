package rayapoulle.scott.pewpew;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background {

    private Bitmap image;
    private int x, y;


    public Background(Bitmap res) {
        image = res;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
        System.out.println(image.getWidth() + " ," + image.getHeight());
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

}
