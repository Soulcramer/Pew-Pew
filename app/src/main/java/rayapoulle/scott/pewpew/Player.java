package rayapoulle.scott.pewpew;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public class Player extends GameObject {
    /*private Bitmap spritesheet;
    private int score;
    private int direction;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;
    private boolean isMoving;*/
    public Bitmap bitmap;
    public int x;
    public int y;
    public boolean touched;

    public Player(Bitmap res, int x, int y) {

        /*x = 100;
        y = GamePanel.HEIGHT / 2;
        score = 0;
        height = h;
        width = w;
        dx = 0;
        dy = 0;
        //l'attribut direction est un entier compris entre 0 et 7 où 0 est le SUD et  7 le SUD-EST.
        direction = 0;

        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;

        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(spritesheet, i * width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(70);
        startTime = System.nanoTime();*/
        this.x = x;
        this.y = y;
        this.bitmap = res;

    }


    public void update() {
        /*long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000 / 30;
        startTime = System.nanoTime();
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if (elapsed > 100) {
            score++;
            startTime = System.nanoTime();
        }
        animation.update();

        if (isMoving) {
            if (false) {

            }
           // dy = (int) (dya -= 1.1);

        } else {
           // dy = (int) (dya += 1.1);
        }

        dx = 0;
        dy = 0;*/
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
    }

    public void handleActionDown(int eventX, int eventY) {
        if (eventX >= (x - bitmap.getWidth() / 2) && (eventX <= (x + bitmap.getWidth() / 2))) {
            if (eventY >= (y - bitmap.getHeight() / 2) && (y <= (y + bitmap.getHeight() / 2))) {
                // droid touched
                this.touched = true;
            } else {
                this.touched = false;
            }
        } else {
            this.touched = false;
        }
    }

}