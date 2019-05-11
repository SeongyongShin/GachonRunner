package com.example.shin.final_project.ingame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shin.final_project.R;
import com.example.shin.final_project.outgame.GameEndActivity;
import com.example.shin.final_project.outgame.GameLayout;
import com.example.shin.final_project.outgame.GameOverActivity;
import com.example.shin.final_project.outgame.Progress;

import java.util.ArrayList;
import java.util.Random;

import static com.example.shin.final_project.outgame.GameLayout.activity;
import static com.example.shin.final_project.staticItem.cvs.canvas;
import static com.example.shin.final_project.staticItem.cvs.cvsHeight;
import static com.example.shin.final_project.staticItem.cvs.cvsWidth;
import static com.example.shin.final_project.staticItem.cvs.firstSet;
import static com.example.shin.final_project.staticItem.cvs.isAtck;
import static com.example.shin.final_project.staticItem.cvs.isBulletMoving;
import static com.example.shin.final_project.staticItem.cvs.isCheck;
import static com.example.shin.final_project.staticItem.cvs.isEnemyMoving;
import static com.example.shin.final_project.staticItem.cvs.ourHolder;
import static com.example.shin.final_project.staticItem.cvs.stage;
import static com.example.shin.final_project.staticItem.cvs.startGround1;

public  class GameView extends SurfaceView implements Runnable, View.OnClickListener{
    Button jumpBtn , atkBtn;
    TextView gameTime, t1,t2;
    private boolean asdfg = true;
    public Thread gameThread;
    private volatile boolean playing;
    private boolean enemyMoving = true, threadSleep = false;
    private long thisTimeFrame;
    private float fps;
    private boolean startGame = true;
    private boolean startCount = true;
    long startFrameTime, recentTime, oldTime;
    private boolean timeFirst = true;
    private int timecount = 0;
    private int currentStage = 1;
    Progress progress;
    CharacterObject mainCharacter;
    Background background;
    Background background2;
    Ground ground;
    Ground ground1;
    Context c;
    public static EnemyObject enemy1;
    Bullet bullet = null;
    ArrayList<Ground> grounds = new ArrayList<Ground>();
    Intent intent;
    GameLayout gameLayout;

    Random random = new Random();

    private int who;
    public GameView(Context context, int stage, int who){
        super(context);
        this.who = who;
        c = context;
        ourHolder = getHolder();
        gameLayout = (GameLayout)GameLayout.activity;
        jumpBtn = GameLayout.jumpBtn;
        atkBtn = GameLayout.atkBtn;
        gameTime = GameLayout.gameTime;
        jumpBtn.setOnClickListener(this);
        atkBtn.setOnClickListener(this);
        setGame(stage,who);
        background2.setXpos(background.getObj().getWidth());
        background2.setFirstcheck(false);
        ground = new Ground(c, R.drawable.goundtest);
        ground1 = new Ground(c,R.drawable.goundtest);
        enemy1 = new EnemyObject(c,R.drawable.enemy1);
        progress = new Progress(c,R.drawable.progress);
        this.currentStage = GameLayout.currentStage;
        for(int i=0;i<5;i++) {
            grounds.add(i, ground1);
            grounds.get(i).num = random.nextInt()%5;
        }

        gameLayout.Dialog();
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
            if(startCount){
                startCount = false;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(true){
                            try {
                                Thread.sleep(1000);

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            timecount++;
                                if(progress.getCurrentHFrame() >= progress.getFrameHcount()-1 ) {
                                    playing=false;
                                    if(currentStage >= stage)stage++;
                                    intent = new Intent(activity, GameEndActivity.class);
                                    activity.startActivity(intent);
                                    break;
                                }
                                if(!playing){
                                    break;
                                }
                            makeProgress();
                        }
                    }
                }).start();
            }
            /*if((oldTime - recentTime) > 1 || (recentTime - oldTime) == 9) { // 1초마다 여기로 들어옴.
                Log.d("second", " " + (oldTime - recentTime));
                recentTime = oldTime;
                timecount++;
                if(timecount>33){
                    playing =false;

                    if(currentStage >= stage) stage++;
                    if(progress.getCurrentHFrame() == progress.getFrameHcount()) {
                        intent = new Intent(activity, GameEndActivity.class);
                        activity.startActivity(intent);
                    }
                    makeProgress();
                }

            }*/

        }
    }
    public void update(){
        if(firstSet) {
            cvsWidth = getWidth(); cvsHeight = getHeight(); firstSet = false;
        }
        makeBackGround();
        makeCharacter();
        makeGround();
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
                if(mainCharacter.isJumpcheck() && mainCharacter.getCurrentHFrame() != 3){
                    mainCharacter.setCurrentHFrame(1);
                }else if(!mainCharacter.isJumpcheck() && mainCharacter.getCurrentHFrame() == 1){
                    mainCharacter.setCurrentHFrame(0);
                }
                if(mainCharacter.getCurrentHFrame() == 3){
                    if(mainCharacter.getCurrentFrame() == 18){
                        Intent intent = new Intent(activity,GameOverActivity.class);
                        activity.startActivity(intent);
                        playing = false;
                    }
                }
                mainCharacter.setCurrentFrame(mainCharacter.getCurrentFrame() + 1);

                if(mainCharacter.getCurrentFrame() >= mainCharacter.getFramecount()){//mainCharacter.getFramecount()){
                    mainCharacter.setCurrentFrame(0);
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
            progress.drawObj();
            if(background2.isSecondcheck()){
                background2.setwh(background.frameWidth,background.frameHeight);
                background2.setSecondcheck(false);
            }
            mainCharacter.drawObj();
            if(startGround1){ground.drawObj();}
            if(bullet != null ){bullet.drawObj();}
            //if(enemy1.isMoving()){enemy1.drawObj();}
            enemy1.drawObj();

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
                //threadStart();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        t1 = gameLayout.t1;
                        t2 = gameLayout.t2;
                        t1.setVisibility(View.INVISIBLE);
                        t2.setVisibility(View.INVISIBLE);
                    }
                }).start();
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
                    if(who == 2){
                        mainCharacter.setCurrentFrame(6);
                    }else {
                        mainCharacter.setCurrentFrame(13);
                    }
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
        //시작 땅\
        if (startGround1) {
            ground.setXpos(ground.getXpos() - (float)ground.getRunSpeed());
           // if((ground.getXpos() + ground.getWidth())<=0) startGround1 = false;
        }
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
                die();

            }
            if(bullet.getXpos()>enemy1.getXpos()){
                if(bullet.getYBottom()<enemy1.getYpos()
                        ||bullet.getYpos()>enemy1.getYBottom()){
                }else{
                    //enemy1.isMoving =false;
                    isBulletMoving = false;
                    bullet = null;
                    enemy1.setXpos(cvsWidth * 2);
                    setEnemy();
                }
            }

        }catch (Exception e){

        }

    }
    public void makeProgress(){
        if(timecount%3 == 0){
            progress.setCurrentHFrame(progress.getCurrentHFrame()+1);
        }
    }
    public void die(){
        mainCharacter.setCurrentHFrame(3);
        mainCharacter.setCurrentFrame(0);
        if(asdfg){mainCharacter.setCurrentHFrame(0);asdfg = false;}
    }
    public void setGroundPatterns(){
        switch (random.nextInt(3)){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            default:
                break;

        }

    }
    public void setEnemy(){
            Log.d("ddd", "2");
            int i = random.nextInt(5);
            switch (i) {
                case 1:
                    enemy1.setObj(R.drawable.enemy1);
                    break;
                case 2:
                    enemy1.setObj(R.drawable.enemy2);
                    break;
                case 3:
                    enemy1.setObj(R.drawable.enemy3);
                break;
                case 4:
                    enemy1.setObj(R.drawable.enemy4);
                    break;
                default:
                    enemy1.setObj(R.drawable.enemy1);
                    break;
            }
    }
    private void setGame(int stage, int who){
        switch (stage){
            case 1 :
                background = new Background(c,R.drawable.metro2);
                background2 = new Background(c,R.drawable.metro2);
                break;
        }
        if(who == 1){
            mainCharacter = new CharacterObject(c,R.drawable.shin1);
        }else{
            mainCharacter = new CharacterObject(c,R.drawable.kim1);
        }
    }
}