package com.example.shin.final_project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.SurfaceView;

import static com.example.shin.final_project.cvs.*;

public class test extends SurfaceView{
    private Bitmap obj;
    private float runSpeed = 1500;
    private int frameWidth = 150, frameHeight = 150;
    private float Xpos = 10, Ypos = 10, XRight = 0, YBottom = 0;
    private int framecount = 9;
    private int frameHcount = 2;
    private int currentFrame = 0;
    private int currentHFrame = 0;
    private long fps;
    private long thisTimeFrame;
    private long lastFrameChangeTime;
    private int frameLengthInMillisecond = 100;
    private Rect frameToDraw = new Rect(0,0,300/*frameWidth*/, 300/*frameHeight*/);
    private RectF whereToDraw = new RectF();

    public test(Context context, int resource) {
        super(context);
        obj = BitmapFactory.decodeResource(getResources(),resource);
        obj = Bitmap.createScaledBitmap(obj,frameWidth * framecount ,frameHeight * frameHcount,false);
    }

    public void drawObj(){
        frameToDraw.left = currentFrame * frameWidth;
        frameToDraw.right = frameToDraw.left + frameWidth;
        frameToDraw.top = currentHFrame * frameHeight;
        frameToDraw.bottom = frameToDraw.top + frameHeight;
        XRight = Xpos + frameWidth;
        YBottom = Ypos + frameHeight;
        whereToDraw.set(Xpos,Ypos,XRight,YBottom);
        canvas.drawBitmap(obj,frameToDraw,whereToDraw,null);
    }
    public Bitmap getObj() {
        return obj;
    }

    public void setObj(Bitmap obj) {
        this.obj = obj;
    }

    public float getRunSpeed() {
        return runSpeed;
    }

    public void setRunSpeed(float runSpeed) {
        this.runSpeed = runSpeed;
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public void setFrameWidth(int frameWidth) {
        this.frameWidth = frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public void setFrameHeight(int frameHeight) {
        this.frameHeight = frameHeight;
    }

    public float getXpos() {
        return Xpos;
    }

    public void setXpos(float xpos) {
        Xpos = xpos;
    }

    public float getYpos() {
        return Ypos;
    }

    public void setYpos(float ypos) {
        Ypos = ypos;
    }

    public float getXRight() {
        return XRight;
    }

    public void setXRight(float XRight) {
        this.XRight = XRight;
    }

    public float getYBottom() {
        return YBottom;
    }

    public void setYBottom(float YBottom) {
        this.YBottom = YBottom;
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

    public long getFps() {
        return fps;
    }

    public void setFps(long fps) {
        this.fps = fps;
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

    public Rect getFrameToDraw() {
        return frameToDraw;
    }

    public void setFrameToDraw(Rect frameToDraw) {
        this.frameToDraw = frameToDraw;
    }

    public RectF getWhereToDraw() {
        return whereToDraw;
    }

    public void setWhereToDraw(RectF whereToDraw) {
        this.whereToDraw = whereToDraw;
    }

    public void setCurrentHFrame(int currentHFrame) {
        this.currentHFrame = currentHFrame;
    }

}
