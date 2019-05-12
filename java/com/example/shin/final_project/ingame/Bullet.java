package com.example.shin.final_project.ingame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.SurfaceView;

import static com.example.shin.final_project.staticItem.cvs.*;

public class Bullet extends SurfaceView{

    private Bitmap obj;
    public boolean isMoving = false;
    private boolean firstcheck = true;
    private float runSpeed = 3000;
    public int frameWidth , frameHeight;
    private float Xpos = 0, Ypos = 0, XRight = 0, YBottom = 0;
    private float x,y,x1,y1,w,g;
    private int framecount = 1;
    private int currentFrame = 0;

    private RectF whereToDraw = new RectF();
    private Rect frameToDraw = new Rect();

    public Bullet(Context context, int resource, int x, int y, int x1, int y1, int w, int g) {
        super(context);
        obj = BitmapFactory.decodeResource(getResources(),resource);
        this.x = x; this.x1 = x1;
        this.y = y; this.y1 = y1;
        this.w = w; this.g = g;
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
        canvas.drawBitmap(obj, null, whereToDraw, null);
       // Log.d("asd","x : " + x);
    }
    public void getH(){
        Xpos = (x + x1)/2;
        frameWidth = (int)w/2;
        frameHeight = (int)(y1 - y)/4;
        Ypos = y + (float)frameHeight;
        obj = Bitmap.createScaledBitmap(obj,frameWidth  *framecount ,frameHeight,false);
        firstcheck = false;
    }

    public float getRunSpeed() {
        return runSpeed;
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

    public float getYBottom() {
        return YBottom;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

}
