package com.example.shin.final_project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import static com.example.shin.final_project.GameLayout.activity;
import static com.example.shin.final_project.cvs.*;

public  class GameView extends SurfaceView implements Runnable, View.OnClickListener{
    Button jumpBtn , atkBtn;
    TextView gameTime;
    private boolean asdfg = true;
    private Thread gameThread;
    private volatile boolean playing;
    private long thisTimeFrame;
    private float fps;
    private boolean startGame = true;
    long startFrameTime, recentTime, oldTime;
    private boolean timeFirst = true;
    private int timecount = 0;
    private int currentStage = 1;

    CharacterObject mainCharacter;
    Background background;
    Background background2;
    Ground ground;
    Ground ground1;
    Context c;
    EnemyObject enemy1;
    Bullet bullet = null;
    ArrayList<Ground> grounds = new ArrayList<Ground>();
    Intent intent;

    Random random = new Random();

    public GameView(Context context){
        super(context);
        c = context;
        ourHolder = getHolder();
        jumpBtn = GameLayout.jumpBtn;
        atkBtn = GameLayout.atkBtn;
        gameTime = GameLayout.gameTime;
        jumpBtn.setOnClickListener(this);
        atkBtn.setOnClickListener(this);
        background = new Background(c,R.drawable.metro2);
        background2 = new Background(c,R.drawable.metro2);
        background2.setXpos(background.getObj().getWidth());
        background2.setFirstcheck(false);
        ground = new Ground(c,R.drawable.goundtest);
        ground1 = new Ground(c,R.drawable.goundtest);
        enemy1 = new EnemyObject(c,R.drawable.enemy1);
        mainCharacter = new CharacterObject(c,R.drawable.shin);
        this.currentStage = GameLayout.currentStage;
        for(int i=0;i<5;i++) {
            grounds.add(i, ground1);
            grounds.get(i).num = random.nextInt()%5;
        }
    }

    @Override
    public void run() {
        while(playing){
            startFrameTime= System.currentTimeMillis();
            oldTime = (startFrameTime/1000)%10000;
            if(timeFirst){
                recentTime = oldTime;
                timeFirst = false;
            }
            update();
            draw();
            thisTimeFrame = System.currentTimeMillis() - startFrameTime;
            if(thisTimeFrame >=1 ){
                fps = 1000/thisTimeFrame;
            }
            if((oldTime - recentTime) > 1 || (recentTime - oldTime) == 9) { // 1초마다 여기로 들어옴.
                Log.d("second", " " + (oldTime - recentTime));
                recentTime = oldTime;
                timecount++;
                if(timecount>10){
                    playing =false;

                    if(currentStage >= stage) stage++;

                    intent = new Intent(activity,GameEndActivity.class);
                    activity.startActivity(intent);
                }
            }

        }
    }
    public void update(){
        if(firstSet) {
            cvsWidth = getWidth(); cvsHeight = getHeight(); firstSet = false;
        }
        makeBackGround();
        makeCharacter();
        //makeGround();
        if(isEnemyMoving){makeEnemy();}
        if(isAtck && !isBulletMoving){
            createBullet(c,R.drawable.bullet,(int)mainCharacter.getXpos(),(int)mainCharacter.getYpos(),
                    (int)mainCharacter.getXRight(),(int)mainCharacter.getYBottom(),mainCharacter.getFrameWidth());
            isBulletMoving = true;}

        if(isBulletMoving){ makeBullet(); }

    }

    public void manageCurrentFrame(){
        long time = System.currentTimeMillis();

        if(mainCharacter.isMoving()){
            if(time > mainCharacter.getLastFrameChangeTime() + mainCharacter.getFrameLengthInMillisecond()){

                Log.d("asd"," : "+mainCharacter.getCurrentFrame() + " : " + mainCharacter.getFramecount());

                mainCharacter.setLastFrameChangeTime(time);
                if(mainCharacter.isJumpcheck()){
                    mainCharacter.setCurrentHFrame(1);
                }else if(!mainCharacter.isJumpcheck() && mainCharacter.getCurrentHFrame() == 1){
                    mainCharacter.setCurrentHFrame(0);
                }
                if(mainCharacter.getCurrentHFrame() == 3){
                    if(mainCharacter.getCurrentFrame() == 19){
                        Intent intent = new Intent(activity,GameOverActivity.class);
                        activity.startActivity(intent);
                    }
                }
                mainCharacter.setCurrentFrame(mainCharacter.getCurrentFrame() + 1);

                if(mainCharacter.getCurrentFrame() >= mainCharacter.getFramecount()){//mainCharacter.getFramecount()){
                    mainCharacter.setCurrentFrame(0);
                    /*switch (mainCharacter.getCurrentHFrame()){
                        case 1 :
                            mainCharacter.setCurrentHFrame(2);
                            break;
                        case 2 :
                            mainCharacter.setCurrentHFrame(3);
                            break;
                        case 3 :
                            mainCharacter.setCurrentHFrame(4);
                            break;
                        default:
                            mainCharacter.setCurrentHFrame(1);
                            break;
                    }*/
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
            mainCharacter.drawObj();
            if(startGround1){ground.drawObj();}
            if(bullet != null ){bullet.drawObj();}
            if(enemy1 != null ){enemy1.drawObj();}
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
        if(playing) {
            switch (v.getId()) {
                case R.id.jump:
                    mainCharacter.setJumpcheck(true);
                    mainCharacter.setCurrentFrame(13);
                    break;
                case R.id.attack:
                    if (!isAtck && !isBulletMoving) isAtck = true;
                    break;
                default:
                    break;

            }
        }
    }
    public void makeBackGround(){
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
    }
    public void makeCharacter(){
        //캐릭터
        if(mainCharacter.isJumpcheck()){
            if(isCheck) {
                if(mainCharacter.isJumping()) {
                    mainCharacter.setCurrentHeight(mainCharacter.getYpos());
                    mainCharacter.setJumping(false);
                }
                mainCharacter.setYpos(mainCharacter.getYpos() - mainCharacter.getRunSpeed() / fps);
                if(mainCharacter.getYBottom()< mainCharacter.getCurrentHeight()){
                    isCheck = false;
                }
            }else{
                mainCharacter.setYpos(mainCharacter.getYpos() + mainCharacter.getRunSpeed()/fps);
                if(mainCharacter.getYpos() > mainCharacter.getCurrentHeight()){
                    isCheck = true;
                    mainCharacter.setJumpcheck(false);
                    mainCharacter.setFirstcheck(true);
                    mainCharacter.setJumping(true);
                }
            }
        }
    }
    public void makeGround(){
        //시작 땅
        /*
        if (startGround1) {
            ground.setXpos(ground.getXpos() - (float)ground.getRunSpeed());
            //if((ground.getXpos() + ground.getWidth())<=0) startGround1 = false;
        }*/
    }
    public void createBullet(Context c, int resource, int x, int x1, int y, int y1, int w){
        bullet = new Bullet(c,resource,x,x1,y,y1,w,ground.frameHeight);
        isAtck = false;
    }
    public void makeBullet(){
        bullet.setXpos(bullet.getXpos() + bullet.getRunSpeed()/fps);
        //Log.d("asd","asd " + bullet.getXpos() + " w : " + bullet.getFrameWidth() + " h : " + bullet.getFrameHeight() );
        if(bullet.getXpos() > cvsWidth){ bullet = null; isBulletMoving = false;}

    }
    public void makeEnemy(){
        if(enemy1 != null){enemy1.setXpos(enemy1.getXpos()-enemy1.getRunSpeed()/fps);}
        try{
            if(enemy1 != null &&(enemy1.getXpos()<mainCharacter.getXRight())){
                enemy1.setXpos(cvsWidth - enemy1.getFrameWidth());
                mainCharacter.setCurrentHFrame(3);
                mainCharacter.setCurrentFrame(0);
                if(asdfg){mainCharacter.setCurrentHFrame(0);asdfg = false;}
            }
            if(bullet.getXpos()>enemy1.getXpos()){
                if(bullet.getYBottom()<enemy1.getYpos()
                        ||bullet.getYpos()>enemy1.getYBottom()){
                }else{
                    enemy1.isMoving =false;
                    isBulletMoving = false;
                    bullet = null;
                    enemy1.setXpos(cvsWidth - enemy1.getFrameWidth());
                }
            }

        }catch (Exception e){

        }

    }
}