package rayapoulle.scott.pewpew;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    public static final int MOVESPEED = -5;
    private static final String TAG = GamePanel.class.getSimpleName();
    public boolean noDraw = false;
    private MainThread thread;
    private Background bg;
    private Player player;
    private PlayerAnimated player2;
    private float scaleFactorX = getWidth() / (WIDTH * 1.f);
    private float scaleFactorY = getHeight() / (HEIGHT * 1.f);
    // the fps to be displayed
    private String avgFps;
    public GamePanel(Context context) {
        super(context);


        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.blanchinator), 50, 50);
        // create Elaine and load bitmap
        player2 = new PlayerAnimated(
                BitmapFactory.decodeResource(getResources(), R.drawable.blanchinator)
                , 200, 200    // initial position
                , 10, 6);    // FPS and number of frames in the animation

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.snow));


        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }

    public void setAvgFps(String avgFps) {
        this.avgFps = avgFps;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "Surface is being destroyed");
        // tell the thread to shut down and wait for it to finish
        // this is a clean shutdown
        boolean retry = true;
        noDraw = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // try again shutting down the thread
            }
        }
        Log.d(TAG, "Thread was shut down cleanly");

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            // delegating event handling to the droid
            //player2.handleActionDown((int) event.getX(), (int) event.getY());
            float diffX = event.getX() - player2.x;
            float diffY = event.getY() - player2.y;
            float length = (float) Math.sqrt(diffX * diffX + diffY * diffY);
            float normalizedX = diffX / length;
            float normalizedY = diffY / length;
            float deltaX = normalizedX * 10;
            float deltaY = normalizedY * 10;
            player2.speed.xDirection = deltaX;
            player2.speed.yDirection = deltaY;


        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            // touch was released
            player2.speed.xDirection = 0;
            player2.speed.yDirection = 0;
            if (player2.moving) {
                player2.moving = false;
            }
        }
        return true;

    }

    public void update() {
        /*// check collision with right wall if heading right
        if (player2.speed.getxDirection() == Speed.DIRECTION_RIGHT
                && player2.x + player2.bitmap.getWidth() / 2 >= getWidth()) {
            player2.speed.toggleXDirection();
        }
        // check collision with left wall if heading left
        if (player2.speed.getxDirection() == Speed.DIRECTION_LEFT
                && player2.x - player2.bitmap.getWidth() / 2 <= 0) {
            player2.speed.toggleXDirection();
        }
        // check collision with bottom wall if heading down
        if (player2.speed.getyDirection() == Speed.DIRECTION_DOWN
                && player2.x + player2.bitmap.getHeight() / 2 >= getHeight()) {
            player2.speed.toggleYDirection();
        }
        // check collision with top wall if heading up
        if (player2.speed.getyDirection() == Speed.DIRECTION_UP
                && player2.x - player2.bitmap.getHeight() / 2 <= 0) {
            player2.speed.toggleYDirection();
        }*/
        // Update the lone droid
        player2.update(System.currentTimeMillis());
    }

    @Override
    public void draw(Canvas canvas) {
        //canvas.scale(getWidth() / (WIDTH * 1.f),getHeight() / (HEIGHT * 1.f));
        //super.draw(canvas);
        if (!noDraw) {
            super.draw(canvas);
            bg.draw(canvas);
            displayFps(avgFps);
            player2.draw(canvas);
        }


    }

    private void displayFps(String fps) {
        if (fps != null) {
            //Log.d(TAG, fps);
        }
    }


}