package rayapoulle.scott.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public abstract class GameObject {
    public Bitmap bitmap;        // the animation sequence
    public int x;                // the X coordinate of the object (top left of the image)
    public int y;                // the Y coordinate of the object (top left of the image)
    public int spriteWidth;    // the width of the sprite to calculate the cut out rectangle
    public int spriteHeight;    // the height of the sprite
    public Rect sourceRect;    // the rectangle to be drawn from the animation bitmap
    public int frameNr;        // number of frames in animation
    public int currentFrame;    // the current frame
    public long frameTicker;    // the time of the last frame update
    public int framePeriod;    // milliseconds between each frame (1000/fps)
    public Rect destRect;
    public Speed speed;


    public abstract void update(long gameTime);

    public abstract void draw(Canvas canvas);

}