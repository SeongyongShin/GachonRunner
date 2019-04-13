package com.example.shin.final_project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.SurfaceView;

import static com.example.shin.final_project.cvs.*;

public class Ground extends SurfaceView{
    private Bitmap obj;
    public boolean isMoving = true;
    public boolean firstCheck = true;

    private static boolean startGround = true;
    private float runSpeed = 1;
    public int frameWidth = 0 , frameHeight = 0 ;
    private float Xpos = 0, Ypos = 0, XRight = 0, YBottom = 0 ;
    private int framecount = 1;
    private int currentFrame = 0;
    private long fps;
    private long thisTimeFrame;
    private long lastFrameChangeTime;
    private int frameLengthInMillisecond = 100;
    private RectF whereToDraw = new RectF();
    private Rect frameToDraw = new Rect();
    private int percent = 10;
    public int num=0;


    public Ground(Context context, int resource) {
        super(context);
        obj = BitmapFactory.decodeResource(getResources(),resource);

    }

    public void drawObj(){
        if(firstCheck){
            getwh(); firstCheck = false;
            frameWidth += num * 10;

            if(startGround){frameWidth = cvsWidth; startGround = false;}
            obj = Bitmap.createScaledBitmap(obj,frameWidth * framecount ,frameHeight,false);
            frameToDraw.bottom = frameHeight;
        }
        XRight = Xpos + frameWidth;
        YBottom = Ypos + frameHeight;
        frameToDraw.left = currentFrame * frameWidth;
        frameToDraw.right = frameToDraw.left + frameWidth;
        whereToDraw.set((int)Xpos,(int)Ypos,(int)XRight,(int)YBottom);
        canvas.drawBitmap(obj,frameToDraw,whereToDraw,null);
    }
    public void getwh() {
        this.frameWidth = cvsWidth/percent;
        this.frameHeight = cvsHeight/5;
        Ypos = cvsHeight-frameHeight;
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

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public boolean isFirstCheck() {
        return firstCheck;
    }

    public void setFirstCheck(boolean firstCheck) {
        this.firstCheck = firstCheck;
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

    public RectF getWhereToDraw() {
        return whereToDraw;
    }

    public void setWhereToDraw(RectF whereToDraw) {
        this.whereToDraw = whereToDraw;
    }

    public Rect getFrameToDraw() {
        return frameToDraw;
    }

    public void setFrameToDraw(Rect frameToDraw) {
        this.frameToDraw = frameToDraw;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }


    public static boolean isStartGround() {
        return startGround;
    }

    public static void setStartGround(boolean startGround) {
        Ground.startGround = startGround;
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
