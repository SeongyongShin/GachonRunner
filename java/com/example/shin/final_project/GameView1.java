package com.example.shin.final_project;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import static com.example.shin.final_project.cvs.canvas;
import static com.example.shin.final_project.cvs.ourHolder;


public  class GameView1 extends SurfaceView implements Runnable{
    private Thread gameThread;

    private volatile boolean playing;
    private long thisTimeFrame;
    private long fps;
    CharacterObject horse1;
    CharacterObject horse2;


    public GameView1(Context context){
        super(context);
        ourHolder = getHolder();

    }

    @Override
    public void run() {
        while(playing){
            long startFrameTime= System.currentTimeMillis();
            update();
            draw();

            thisTimeFrame = System.currentTimeMillis() - startFrameTime;
            if(thisTimeFrame >=1 ){
                fps = 1000/thisTimeFrame;
            }
        }
    }
    public void update(){
        if(horse1.isMoving()){
            horse1.setXpos(horse1.getXpos() + horse1.getRunSpeed()/fps);
            if(horse1.getXpos()>getWidth()){
                horse1.setYpos(horse1.getYpos() +(int)horse1.getFrameWidth());
                horse1.setXpos(10);
            }
            if(horse1.getYpos()+horse1.getFrameHeight()>getHeight()){
                horse1.setYpos(10);
            }
            Log.d("G1","x : "+horse1.getXpos() +" y : "+horse1.getYpos());
        }
    }
    public void manageCurrentFrame(){
        long time = System.currentTimeMillis();

        if(horse1.isMoving()){
            if(time > horse1.getLastFrameChangeTime() + horse1.getFrameLengthInMillisecond()){

                horse1.setLastFrameChangeTime(time);
                horse1.setCurrentFrame(horse1.getCurrentFrame()+1);
                // horse1.setCurrentFrame(0);

                if(horse1.getCurrentFrame() >= horse1.getFramecount()){
                    horse1.setCurrentFrame(0);
                }
            }
        }

    }

    public void draw(){
        if(ourHolder.getSurface().isValid()){
            canvas = ourHolder.lockCanvas(); // 그리기 전 캔버스를 잠근다.
            canvas.drawColor(Color.YELLOW);
            manageCurrentFrame();
            horse1.drawObj();
            ourHolder.unlockCanvasAndPost(canvas); // 잠금을 풀면 캔버스가 그려진다.
        }
    }
    public void pause(){
        playing = false;

        try{
            gameThread.join();
        }catch (InterruptedException e){
            Log.e("err", "JoiningThread");
        }

    }
    public void resume(){
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN :
                horse1.setMoving(!horse1.isMoving);
                break;
        }
        return true;
    }
}