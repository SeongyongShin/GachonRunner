package com.example.shin.final_project;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;

import static com.example.shin.final_project.cvs.*;

public  class GameView extends SurfaceView implements Runnable, View.OnClickListener{
    Button jumpBtn , atkBtn;
    private Thread gameThread;
    private volatile boolean playing;
    private long thisTimeFrame;
    private float fps;
    private boolean startGame = true;
    CharacterObject horse1;
    Background background;
    Background background2;
    Ground ground;
    Ground ground1;
    ArrayList<Ground> grounds = new ArrayList<Ground>();

    Random random = new Random();

    public GameView(Context context){
        super(context);
        ourHolder = getHolder();
        jumpBtn = GameLayout.jumpBtn;
        atkBtn = GameLayout.atkBtn;
        jumpBtn.setOnClickListener(this);
        atkBtn.setOnClickListener(this);

        horse1 = new CharacterObject(context,R.drawable.test);
        background = new Background(context,R.drawable.metro2);
        background2 = new Background(context,R.drawable.metro2);
        background2.setXpos(background.getObj().getWidth());
        background2.setFirstcheck(false);
        ground = new Ground(context,R.drawable.goundtest);
        ground1 = new Ground(context,R.drawable.goundtest);
        for(int i=0;i<5;i++) {
            grounds.add(i, ground1);
            grounds.get(i).num = random.nextInt()%5;
        }

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

        if(firstSet) {
            cvsWidth = getWidth();
            cvsHeight = getHeight();
            firstSet = false;
        }

        //시작 땅
        /*
        if (startGround1) {
            ground.setXpos(ground.getXpos() - (float)ground.getRunSpeed());
            //if((ground.getXpos() + ground.getWidth())<=0) startGround1 = false;
        }*/
        //배경
        if(background.isFirstcheck()){
           background.setXpos(background.getXpos() - (float) 100);
           background2.setXpos(background.frameWidth + background.getXpos());
            if(background.frameWidth + background.getXpos() <= 0) {
                background.setXpos(background2.getXpos() + background2.frameWidth);
                background.setFirstcheck(false);
            }
        }else{
            background2.setXpos(background2.getXpos() - (float) 100);
            background.setXpos(background2.frameWidth + background2.getXpos());
            if(background2.frameWidth + background2.getXpos() <= 0) {
                background2.setXpos(background.getXpos() + background.frameWidth);
                background.setFirstcheck(true);
            }
        }
        //캐릭터
        if(horse1.isJumpcheck()){
            if(isCheck) {
                    horse1.setYpos(horse1.getYpos() - horse1.getRunSpeed() / fps);
                    if(horse1.getYpos() + horse1.getFrameHeight() <= (cvsHeight - horse1.getFrameHeight() - cvsHeight/6)){
                        isCheck = false;
                    }
            }else{
                    horse1.setYpos(horse1.getYpos() + horse1.getRunSpeed()/fps);
                if(horse1.getYpos() >= cvsHeight - cvsHeight/6 - cvsHeight/5){
                    isCheck = true;
                    horse1.setJumpcheck(false);
                    horse1.setFirstcheck(true);
                }
            }
        }

    }

    public void manageCurrentFrame(){
        long time = System.currentTimeMillis();

        if(horse1.isMoving()){
            if(time > horse1.getLastFrameChangeTime() + horse1.getFrameLengthInMillisecond()){

                horse1.setLastFrameChangeTime(time);
                horse1.setCurrentFrame(horse1.getCurrentFrame()+1);

                if(horse1.getCurrentFrame() >= horse1.getFramecount()){
                    horse1.setCurrentFrame(0);
                }
            }
        }
    }

    public void draw(){
        if(ourHolder.getSurface().isValid()){
            canvas = ourHolder.lockCanvas(); // 그리기 전 캔버스를 잠근다.
            canvas.drawColor(Color.WHITE);
            manageCurrentFrame();
            background.drawObj();
            background2.drawObj();
            if(background2.isSecondcheck()){
                background2.setwh(background.frameWidth,background.frameHeight);
                background2.setSecondcheck(false);
            }
            horse1.drawObj();
            if(startGround1)ground.drawObj();
            //for(int i=0;i<5;i++){grounds.get(i).drawObj();}
            ourHolder.unlockCanvasAndPost(canvas); // 잠금을 풀면 캔버스가 그려진다.
        }
    }
    public void pause(){
        playing = false;
        try{
            gameThread.join();
        }catch (InterruptedException e){
        }

    }
    public void resume(){
        playing = true;
        gameThread = new Thread(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN :
                if(startGame) {gameThread.start(); startGame = false;}
                    //horse1.setJumpcheck(true);
                break;
        }
        return true;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jump : horse1.setJumpcheck(true);
                break;
            default:
                break;

        }
    }
}