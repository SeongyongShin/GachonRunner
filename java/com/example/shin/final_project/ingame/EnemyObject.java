package com.example.shin.final_project.ingame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.SurfaceView;

import static com.example.shin.final_project.staticItem.cvs.*;

public class EnemyObject extends SurfaceView{
    private Bitmap obj;
    public boolean isMoving = true;
    private boolean isdead = false;
    private boolean firstcheck = true;
    private float runSpeed = 500;
    private int frameWidth = 300, frameHeight = 300;
    private float Xpos = 10, Ypos = 10, XRight = 0, YBottom = 0;
    private int framecount = 10;
    private int currentFrame = 0;
    public int currentEnemy = 0;
    private long fps;
    private long thisTimeFrame;
    private long lastFrameChangeTime;
    private int frameLengthInMillisecond = 100;
    private Rect frameToDraw = new Rect(0,0,300/*frameWidth*/, 300/*frameHeight*/);
    private RectF whereToDraw = new RectF();
    public EnemyObject(Context context, int resource) {
        super(context);
        obj = BitmapFactory.decodeResource(getResources(),resource);
    }

    public void drawObj(){
        frameToDraw.left = currentFrame * frameWidth;
        frameToDraw.right = frameToDraw.left + frameWidth;
        if(firstcheck) {
            getH();
        }

        XRight = Xpos + frameWidth;
        YBottom = Ypos + frameHeight;

        whereToDraw.set((int)Xpos,(int)Ypos,(int)XRight,(int)YBottom);
        canvas.drawBitmap(obj,frameToDraw,whereToDraw,null);
    }
    public void getH(){

        framecount = 10;
        firstcheck = false;
        frameWidth = cvsHeight/3;
        frameHeight = cvsHeight/3;
        Xpos = cvsWidth;
        Ypos = cvsHeight - frameHeight - cvsHeight/5;
        obj = Bitmap.createScaledBitmap(obj,frameWidth * framecount ,frameHeight,false);
    }

    public Bitmap getObj() {
        return obj;
    }

    public void setObj(int resource) {
        obj = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),resource),frameWidth * framecount ,frameHeight,false);
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

    public boolean isDead() {
        return isdead;
    }

    public void setDead(boolean isdead) {
        this.isdead = isdead;
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
}
