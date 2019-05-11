package com.example.shin.final_project.selectCharacter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.TextView;

import static com.example.shin.final_project.selectCharacter.SelectActivity.tt;
import static com.example.shin.final_project.staticItem.cvs.canvas;
import static com.example.shin.final_project.staticItem.cvs.cvsHeight;
import static com.example.shin.final_project.staticItem.cvs.cvsWidth;

public class SelectShin extends SurfaceView {
    private Bitmap obj;
    private Boolean firstcheck = true;
    private int frameWidth = 250, frameHeight = 1000;
    private float Xpos = 1, Ypos = 1;
    private int framecount = 25;
    private int frameHcount = 2;
    private int currentFrame = 0;
    private int currentHFrame = 0;
    private float k;
    private long fps;
    private long thisTimeFrame;
    private long lastFrameChangeTime;
    private int frameLengthInMillisecond = 100;
    TextView view;
    private Rect frameToDraw = new Rect(0,0,frameWidth/*frameWidth*/, frameHeight/*frameHeight*/);
    private RectF whereToDraw = new RectF();
    public SelectShin(Context context,int resource) {
        super(context);
        obj = BitmapFactory.decodeResource(getResources(),resource);

    }
    public void getH(){
        frameWidth = 500;
        frameHeight = cvsHeight - cvsHeight/5;
        Xpos = cvsWidth/4 - frameWidth/2 - frameWidth/5;
        Ypos = cvsHeight - frameHeight;
        obj = Bitmap.createScaledBitmap(obj,frameWidth * framecount ,frameHeight * frameHcount,false);
    }
    public void drawObj(){
        if(firstcheck){
            getH();firstcheck = false;
        }
        Log.d("Xpos",+tt + " cvs : " + cvsHeight);
        frameToDraw.left = currentFrame * frameWidth;
        frameToDraw.right = frameToDraw.left + frameWidth;
        frameToDraw.top = currentHFrame * frameHeight;
        frameToDraw.bottom = frameToDraw.top + frameHeight;
        whereToDraw.set((int)Xpos ,(int)Ypos,Xpos+frameWidth,Ypos+frameHeight);
        canvas.drawBitmap(obj,frameToDraw,whereToDraw,null);
    }

    public int getFramecount() {
        return framecount;
    }

    public void setFramecount(int framecount) {
        this.framecount = framecount;
    }

    public int getFrameHcount() {
        return frameHcount;
    }

    public void setFrameHcount(int frameHcount) {
        this.frameHcount = frameHcount;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public int getCurrentHFrame() {
        return currentHFrame;
    }

    public void setCurrentHFrame(int currentHFrame) {
        this.currentHFrame = currentHFrame;
    }

    public long getThisTimeFrame() {
        return thisTimeFrame;
    }

    public void setThisTimeFrame(long thisTimeFrame) {
        this.thisTimeFrame = thisTimeFrame;
    }

    public long getLastFrameChangeTime() {
        return lastFrameChangeTime;
    }

    public void setLastFrameChangeTime(long lastFrameChangeTime) {
        this.lastFrameChangeTime = lastFrameChangeTime;
    }

    public int getFrameLengthInMillisecond() {
        return frameLengthInMillisecond;
    }

    public void setFrameLengthInMillisecond(int frameLengthInMillisecond) {
        this.frameLengthInMillisecond = frameLengthInMillisecond;
    }

}
