package com.example.shin.final_project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.SurfaceView;

import static com.example.shin.final_project.cvs.canvas;

public class Bullet extends SurfaceView{

    private Bitmap obj;
    public boolean isMoving = false;
    private boolean firstcheck = true;
    private float runSpeed = 1000;
    public int frameWidth , frameHeight;
    private float Xpos = 0, Ypos = 0, XRight = 0, YBottom = 0;
    private float x,y,x1,y1,w;
    private int framecount = 1;
    private int currentFrame = 0;
    private long fps;
    private long thisTimeFrame;
    private long lastFrameChangeTime;
    private int frameLengthInMillisecond = 100;
    private RectF whereToDraw = new RectF();
    private Rect frameToDraw = new Rect();

    public Bullet(Context context, int resource, int x, int y, int x1, int y1, int w) {
        super(context);
        obj = BitmapFactory.decodeResource(getResources(),resource);
        this.x = x; this.x1 = x1;
        this.y = y; this.y1 = y1;
        this.w = w;
        frameToDraw.left = currentFrame * frameWidth;
        frameToDraw.right = frameToDraw.left + frameWidth;
    }

    public void drawObj() {
        if (firstcheck) {
            getH();
        }

        frameToDraw.left = currentFrame * frameWidth;
        frameToDraw.right = frameToDraw.left + frameWidth;

        XRight = Xpos + frameWidth;
        YBottom = Ypos + frameHeight;

        whereToDraw.set((int) Xpos, (int) Ypos, (int) XRight, (int) YBottom);
        canvas.drawBitmap(obj, frameToDraw, whereToDraw, null);
    }
    public void getH(){
        Xpos = (x + x1)/2;
        Ypos = (y + y1)/2;
        frameWidth = (int)w/2;
        frameHeight = (int)(y1 - y)/4;
        obj = Bitmap.createScaledBitmap(obj,frameWidth * framecount ,frameHeight,false);
        firstcheck = false;
    }

}
