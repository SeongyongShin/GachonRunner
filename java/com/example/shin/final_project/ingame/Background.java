package com.example.shin.final_project.ingame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.view.SurfaceView;

import com.example.shin.final_project.staticItem.cvs;

import static com.example.shin.final_project.staticItem.cvs.*;

public class Background extends SurfaceView{
    private Bitmap obj;
    public boolean isMoving = true;
    private boolean firstcheck = true;
    private boolean secondcheck = true;
    private static float runSpeed = 10;
    public int frameWidth , frameHeight;
    private float Xpos = 0, Ypos = 0, XRight = 0, YBottom = 0;
    private int framecount = 0;
    private int currentFrame = 0;
    private long fps;
    private long thisTimeFrame;
    private long lastFrameChangeTime;
    private int frameLengthInMillisecond = 100;
    private RectF whereToDraw = new RectF();

    public Background(Context context, int resource) {
        super(context);
        getwh();
        obj = BitmapFactory.decodeResource(getResources(),resource);
    }
    public void drawObj(){
        if(cvs.backG) {
            obj = Bitmap.createScaledBitmap(obj, cvsWidth, cvsHeight, false);
            cvs.backG = false;
            getwh();
        }

        XRight = Xpos + frameWidth;
        YBottom = Ypos + frameHeight;

        whereToDraw.set((int)Xpos,(int)Ypos,(int)XRight,(int)YBottom);
        canvas.drawBitmap(obj,null,whereToDraw,null);
    }
    public void getwh(){
        frameWidth = canvas.getWidth();
        frameHeight = canvas.getHeight();
    }
    public void setwh(int w, int h){
        this.frameWidth = w;
        this.frameHeight = h;
    }

    public boolean isSecondcheck() {
        return secondcheck;
    }

    public void setSecondcheck(boolean secondcheck) {
        this.secondcheck = secondcheck;
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
