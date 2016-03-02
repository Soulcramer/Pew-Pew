package rayapoulle.scott.myapplication;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
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
    private Player ia;
    private Ball ball;
    private float scaleFactorX = getWidth() / (WIDTH * 1.f);
    private float scaleFactorY = getHeight() / (HEIGHT * 1.f);
    // the fps to be displayed
    private String avgFps;
    private int[] diff = new int[2];

    public GamePanel(Context context) {
        super(context);


        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);
        Display display = ((Game) context).getWindow().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        thread = new MainThread(getHolder(), this);
        // create Elaine and load bitmap
        player = new Player(
                BitmapFactory.decodeResource(getResources(), R.drawable.blanchinator), BitmapFactory.decodeResource(getResources(), R.drawable.bullet)
                , 200, 200    // initial position
                , 10, 6);    // FPS and number of frames in the animation
        ia = new IA(
                BitmapFactory.decodeResource(getResources(), R.drawable.blanchinator), BitmapFactory.decodeResource(getResources(), R.drawable.bullet)
                , 400, 400    // initial position
                , 10, 6);    // FPS and number of frames in the animation

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.snow), size.x, size.y);

        ball = new Ball(BitmapFactory.decodeResource(getResources(), R.drawable.ball), 600, 600);


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
            float diffX = event.getX() - player.x;
            float diffY = event.getY() - player.y;
            float length = (float) Math.sqrt(diffX * diffX + diffY * diffY);
            float normalizedX = diffX / length;
            float normalizedY = diffY / length;
            float deltaX = normalizedX * 10;
            float deltaY = normalizedY * 10;
            player.speed.xDirection = deltaX;
            player.speed.yDirection = deltaY;
            player.speed.angle = (float) Math.acos(normalizedX);

            if (diffY > 0.0f) {
                player.speed.angle = (float) Math.toDegrees(player.speed.angle);
                float angle = player.speed.angle;
                if (angle <= 22.5) {
                    player.spritedir = 6;
                } else if (angle > 22.5 && angle <= 67.5) {
                    player.spritedir = 7;
                } else if (angle > 67.5 && angle <= 112.5) {
                    player.spritedir = 0;
                } else if (angle > 112.5 && angle <= 157.5) {
                    player.spritedir = 1;
                } else if (angle > 157.5) {
                    player.spritedir = 2;
                }

            } else {
                player.speed.angle = (float) Math.toDegrees(-player.speed.angle);
                float angle = player.speed.angle;
                if (angle >= -22.5) {
                    player.spritedir = 6;
                } else if (angle < -22.5 && angle >= -67.5) {
                    player.spritedir = 5;
                } else if (angle < -67.5 && angle >= -112.5) {
                    player.spritedir = 4;
                } else if (angle < -112.5 && angle >= -157.5) {
                    player.spritedir = 3;
                } else if (angle < -157.5) {
                    player.spritedir = 2;
                }
            }


        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            // touch was released
            player.speed.xDirection = 0;
            player.speed.yDirection = 0;
            if (player.moving) {
                player.moving = false;
            }
        }
        return true;

    }

    public void update() {
        player.update(System.currentTimeMillis());
        // check collision with right wall if heading right
        if (player.x + player.spriteWidth > getWidth()) {
            player.x = getWidth() - player.spriteWidth;
        }
        if (player.x < 0) {
            player.x = 0;
        }
        if (player.y + player.spriteHeight > getHeight()) {
            player.y = getHeight() - player.spriteHeight;
        }
        if (player.y < 0) {
            player.y = 0;
        }
        if (ball.x + ball.spriteWidth > getWidth()) {

            ball.x = getWidth() - ball.spriteWidth;
            ball.speed.toggleXDirection();
        }
        if (ball.x < 0) {
            ball.x = 0;
            ball.speed.toggleXDirection();
        }
        if (ball.y + ball.spriteHeight > getHeight()) {
            ball.y = getHeight() - ball.spriteHeight;
            ball.speed.toggleYDirection();
        }
        if (ball.y < 0) {
            ball.y = 0;
            ball.speed.toggleYDirection();

        }

        for (int i = 0; i < player.bullets.size(); i++) {
            Bullet bullet = player.bullets.get(i);
            bullet.update(System.currentTimeMillis());
            if (ball.destRect.intersect(player.bullets.get(i).destRect)) {
                Log.d(TAG, "update: collision");
                ball.speed.xDirection = player.bullets.get(i).speed.xDirection;
                ball.speed.yDirection = player.bullets.get(i).speed.yDirection;
                ball.speed.xv *= 1.25;
                ball.speed.yv *= 1.25;
                player.bullets.remove(i);
            } else if (bullet.x + bullet.spriteWidth < 0 || bullet.x > getWidth() || bullet.y + bullet.spriteHeight < 0 || bullet.y > getHeight()) {
                player.bullets.remove(i);
            }

        }

        float diffX = ball.x - ia.x;
        float diffY = ball.y - ia.y;
        float length = (float) Math.hypot(diffX, diffY);
        float normalizedX = diffX / length;
        float normalizedY = diffY / length;
        if (Math.abs(diffX) > 100 && Math.abs(diffY) > 100) {
            float deltaX = normalizedX * 10;
            float deltaY = normalizedY * 10;
            ia.speed.xDirection = deltaX;
            ia.speed.yDirection = deltaY;
            ia.speed.angle = (float) Math.toDegrees(Math.acos(normalizedX));


        } else {
            float deltaX = normalizedX * 10;
            float deltaY = normalizedY * 10;
            ia.speed.xDirection = -deltaX;
            ia.speed.yDirection = -deltaY;
            ia.speed.angle = (float) Math.toDegrees(Math.acos(normalizedX));

        }
        for (int i = 0; i < ia.bullets.size(); i++) {
            Bullet bullet = ia.bullets.get(i);
            bullet.update(System.currentTimeMillis());
            if (ball.destRect.intersect(ia.bullets.get(i).destRect)) {
                Log.d(TAG, "update: collision");
                ball.speed.xDirection = ia.bullets.get(i).speed.xDirection;
                ball.speed.yDirection = ia.bullets.get(i).speed.yDirection;
               // ball.speed.xv *= 1.25;
                //ball.speed.yv *= 1.25;
                ia.bullets.remove(i);
            } else if (bullet.x + bullet.spriteWidth < 0 || bullet.x > getWidth() || bullet.y + bullet.spriteHeight < 0 || bullet.y > getHeight()) {
                ia.bullets.remove(i);
            }

        }
        ball.update(System.currentTimeMillis());

        Log.d(TAG, "update: " + ia.speed.angle);
        ia.update(System.currentTimeMillis());


        // Update the lone droid

    }

    @Override
    public void draw(Canvas canvas) {
        //canvas.scale(getWidth() / (WIDTH * 1.f),getHeight() / (HEIGHT * 1.f));
        //super.draw(canvas);
        if (!noDraw) {
            super.draw(canvas);
            bg.draw(canvas);
            displayFps(avgFps);
            player.draw(canvas);
            ia.draw(canvas);

            for (Bullet bullet : player.bullets) {
                bullet.draw(canvas);
            }
            for (Bullet bullet : ia.bullets) {
                bullet.draw(canvas);
            }
            ball.draw(canvas);


        }


    }

    private void displayFps(String fps) {
        if (fps != null) {
            Log.d(TAG, fps);
        }
    }


}