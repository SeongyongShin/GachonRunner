package com.example.shin.final_project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.SurfaceView;

import static com.example.shin.final_project.cvs.canvas;
import static com.example.shin.final_project.cvs.cvsHeight;
import static com.example.shin.final_project.cvs.cvsWidth;

public class ProgressActivity extends SurfaceView{
    private Bitmap obj;
    public boolean isMoving = true;
    private boolean jumpcheck = false;
    private boolean firstcheck = true;
    private boolean isJumping = true;
    private float runSpeed = 1500;
    private int frameWidth = 222, frameHeight = 300;

    private float Xpos = 10, Ypos = 10, XRight = 0, YBottom = 0;
    private int framecount = 20;
    private int frameHcount = 4;
    private int currentStatus = 1;
    private int currentFrame = 0;
    private int currentHFrame = 0;
    private float currentHeight =0;
    private long fps;
    private long thisTimeFrame;
    private long lastFrameChangeTime;


    public void setCurrentHFrame(int currentHFrame) {
        this.currentHFrame = currentHFrame;
    }

    private int frameLengthInMillisecond = 100;
    private Rect frameToDraw = new Rect(0,0,300/*frameWidth*/, 300/*frameHeight*/);
    private RectF whereToDraw = new RectF();
    public ProgressActivity(Context context, int resource) {
        super(context);
        obj = BitmapFactory.decodeResource(getResources(),resource);
    }

    public void drawObj(){
        frameToDraw.left = currentFrame * frameWidth;
        frameToDraw.right = frameToDraw.left + frameWidth;
        frameToDraw.top = currentHFrame * frameHeight;
        frameToDraw.bottom = frameToDraw.top + frameHeight;
        if(firstcheck) {
            getH();
        }

        XRight = Xpos + frameWidth;
        YBottom = Ypos + frameHeight;

        whereToDraw.set((int)Xpos,(int)Ypos,(int)XRight,(int)YBottom);
        canvas.drawBitmap(obj,frameToDraw,whereToDraw,null);
    }
    public void getH(){
        Ypos = cvsHeight - frameHeight - cvsHeight/5;
        firstcheck = false;
        frameWidth = cvsWidth/10;
        Xpos = frameWidth;
        obj = Bitmap.createScaledBitmap(obj,frameWidth * framecount ,frameHeight * frameHcount,false);
    }
}
