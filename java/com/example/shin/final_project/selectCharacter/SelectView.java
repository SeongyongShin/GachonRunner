package com.example.shin.final_project.selectCharacter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.example.shin.final_project.R;
import com.example.shin.final_project.outgame.GameLayout;

import static com.example.shin.final_project.selectCharacter.SelectActivity.Sactivity;
import static com.example.shin.final_project.staticItem.cvs.canvas;
import static com.example.shin.final_project.staticItem.cvs.cvsHeight;
import static com.example.shin.final_project.staticItem.cvs.cvsWidth;
import static com.example.shin.final_project.staticItem.cvs.ourHolder;

public class SelectView extends SurfaceView implements Runnable{
    public SelectShin mainCharacter;
    public SelectKim mainCharacter1;
    private long thisTimeFrame;
    private float fps;
    long startFrameTime;
    private boolean playing = true;
    boolean isSelected = false;
    int who, stage;
    Intent intent;
    public SelectView(Context context , int stage) {
        super(context);
        ourHolder = getHolder();
        this.stage = stage;
        mainCharacter = new SelectShin(context, R.drawable.selectshin1);
        mainCharacter1 = new SelectKim(context,R.drawable.selectkim1);
    }

    @Override
    public void run() {
        while(playing) {
            startFrameTime = System.currentTimeMillis();

            cvsWidth = getWidth();
            cvsHeight = getHeight();

            draw();
            thisTimeFrame = System.currentTimeMillis() - startFrameTime;
            if (thisTimeFrame >= 1) {
                fps = 1000 / thisTimeFrame;
            }
        }
    }
    public void draw(){
        if(ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            manageCurrentFrame();
            mainCharacter.drawObj();
            mainCharacter1.drawObj();
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }
    public void manageCurrentFrame(){
        long time = System.currentTimeMillis();
            if(time > mainCharacter.getLastFrameChangeTime() + mainCharacter.getFrameLengthInMillisecond()){

                mainCharacter.setLastFrameChangeTime(time);

                    mainCharacter.setCurrentFrame(mainCharacter.getCurrentFrame() + 1);
                    if(mainCharacter.getCurrentHFrame() == 1 && mainCharacter.getCurrentFrame() >=mainCharacter.getFramecount()){
                        playing = false;
                        mainCharacter.setCurrentFrame(mainCharacter.getCurrentFrame()-1);
                    }else if(mainCharacter.getCurrentFrame() >= mainCharacter.getFramecount())
                    {
                    mainCharacter.setCurrentFrame(0);
                }

                mainCharacter1.setCurrentFrame(mainCharacter1.getCurrentFrame() + 1);
                if(mainCharacter1.getCurrentHFrame() == 1 && mainCharacter1.getCurrentFrame() >=mainCharacter1.getFramecount()){
                    playing = false;
                    mainCharacter1.setCurrentFrame(mainCharacter1.getCurrentFrame()-1);
                }else if(mainCharacter1.getCurrentFrame() >= mainCharacter1.getFramecount())
                {
                    mainCharacter1.setCurrentFrame(0);
                }


                if(mainCharacter1.getCurrentHFrame() == 1 && mainCharacter1.getCurrentFrame() >=19){
                    mainCharacter1.setCurrentFrame(19);
                }
                if(mainCharacter.getCurrentHFrame() == 1 && mainCharacter.getCurrentFrame() >=19){
                    mainCharacter.setCurrentFrame(19);
                }
            }
        }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isSelected){
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    if (event.getX() < cvsWidth / 2) {
                        mainCharacter.setFramecount(20);
                        mainCharacter.setCurrentHFrame(1);
                        mainCharacter.getH();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                intent = new Intent(Sactivity, GameLayout.class);
                                intent.putExtra("who", 1);
                                intent.putExtra("stage", stage);
                                playing = false;
                                Sactivity.startActivity(intent);
                                Sactivity.finish();
                            }
                        }).start();
                    } else {
                        mainCharacter1.setCurrentHFrame(1);
                        mainCharacter1.getH();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                intent = new Intent(Sactivity, GameLayout.class);
                                intent.putExtra("who", 2);
                                intent.putExtra("stage", stage);
                                playing = false;
                                Sactivity.startActivity(intent);
                                Sactivity.finish();
                            }
                        }).start();
                    }
                    break;
            }
            isSelected = true;
    }
        return true;
    }

}
