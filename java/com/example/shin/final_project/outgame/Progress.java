package com.example.shin.final_project.outgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.SurfaceView;

import static com.example.shin.final_project.staticItem.cvs.canvas;
import static com.example.shin.final_project.staticItem.cvs.cvsHeight;
import static com.example.shin.final_project.staticItem.cvs.cvsWidth;

public class Progress extends SurfaceView{
    private Bitmap obj;
    private boolean firstcheck = true;
    private int frameWidth;

    private int frameHeight;

    private float Xpos = 10, Ypos = 10, XRight = 0, YBottom = 0;
    private int framecount = 1;
    private int frameHcount = 11;
    private int currentStatus = 1;
    private int currentFrame = 0;
    private int currentHFrame = 0;
    private float currentHeight =0;


    private Rect frameToDraw = new Rect(0,0,300/*frameWidth*/, 300/*frameHeight*/);
    private RectF whereToDraw = new RectF();
    public Progress(Context context, int resource) {
        super(context);
        obj = BitmapFactory.decodeResource(getResources(),resource);
    }

    public void drawObj(){
        frameToDraw.left = currentFrame * frameWidth;
        frameToDraw.right = frameToDraw.left + frameWidth;
        frameToDraw.top = currentHFrame * frameHeight;
        frameToDraw.bottom = frameToDraw.top + frameHeight;
        Log.d("aaa"," H: " + currentHFrame );
        if(firstcheck) {
            getH();
        }

        XRight = Xpos + frameWidth;
        YBottom = Ypos + frameHeight;

        whereToDraw.set((int)Xpos,(int)Ypos,(int)XRight,(int)YBottom);
        canvas.drawBitmap(obj,frameToDraw,whereToDraw,null);
    }
    public void getH(){
        firstcheck = false;

        frameWidth = cvsWidth/2;
        frameHeight = cvsHeight/10;
        Xpos = cvsWidth/2 - frameWidth/2;
        obj = Bitmap.createScaledBitmap(obj,frameWidth * framecount ,frameHeight * frameHcount,false);
    }

    public boolean isFirstcheck() {
        return firstcheck;
    }

    public void setFirstcheck(boolean firstcheck) {
        this.firstcheck = firstcheck;
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

    public int getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(int currentStatus) {
        this.currentStatus = currentStatus;
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

    public float getCurrentHeight() {
        return currentHeight;
    }

    public void setCurrentHeight(float currentHeight) {
        this.currentHeight = currentHeight;
    }
    public void setCurrentHFrame(int currentHFrame) {
        this.currentHFrame = currentHFrame;
    }


}
