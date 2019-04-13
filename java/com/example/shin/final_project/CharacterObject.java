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

public class CharacterObject extends SurfaceView{
    private Bitmap obj;
    public boolean isMoving = true;
    private boolean jumpcheck = false;
    private boolean firstcheck = true;
    private float runSpeed = 1500;
    private int frameWidth = 222, frameHeight = 300;
    private float Xpos = 10, Ypos = 10;
    private int framecount = 20;
    private int currentFrame = 0;
    private long fps;
    private long thisTimeFrame;
    private long lastFrameChangeTime;
    private int frameLengthInMillisecond = 100;
    private Rect frameToDraw = new Rect(0,0,300/*frameWidth*/, 300/*frameHeight*/);
    private RectF whereToDraw = new RectF();
    public CharacterObject(Context context, int resource) {
        super(context);
        obj = BitmapFactory.decodeResource(getResources(),resource);
    }

    public void drawObj(){
        frameToDraw.left = currentFrame * frameWidth;
        frameToDraw.right = frameToDraw.left + frameWidth;
        if(firstcheck) {
            getH();
        }
        whereToDraw.set((int)Xpos,(int)Ypos,(int)Xpos+frameWidth,(int)Ypos+frameHeight);
        canvas.drawBitmap(obj,frameToDraw,whereToDraw,null);
    }
    public void getH(){
            Ypos = cvsHeight - cvsHeight/6 - cvsHeight/5;
            firstcheck = false;
            frameWidth = cvsWidth/10;
            Xpos = frameWidth;
             obj = Bitmap.createScaledBitmap(obj,frameWidth * framecount ,cvsHeight/6,false);
    }

    public Bitmap getObj() {
        return obj;
    }

    public void setObj(Bitmap obj) {
        this.obj = obj;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public boolean isFirstcheck() {
        return firstcheck;
    }

    public void setFirstcheck(boolean firstcheck) {
        this.firstcheck = firstcheck;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public boolean isJumpcheck() {
        return jumpcheck;
    }

    public void setJumpcheck(boolean jumpcheck) {
        this.jumpcheck = jumpcheck;
    }

    public float getRunSpeed() {
        return runSpeed;
    }

    public void setRunSpeed(float runSpeed) {
        this.runSpeed = runSpeed;
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

    public int getFramecount() {
        return framecount;
    }

    public void setFramecount(int framecount) {
        this.framecount = framecount;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
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
}
