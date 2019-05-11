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

import java.util.Random;

import static com.example.shin.final_project.outgame.GameLayout.activity;
import static com.example.shin.final_project.staticItem.cvs.*;
public  class GameView extends SurfaceView implements Runnable, View.OnClickListener{
    Button jumpBtn , atkBtn;
    TextView gameTime, t1,t2;
    Progress progress;
    CharacterObject mainCharacter;
    Background background;
    Background background2;
    Ground ground;
    Ground ground1[] = new Ground[10];
    Ground ground2[] = new Ground[10];
    Ground ground3[];
    Ground ground4;
    Context c;
    Bullet bullet = null;
    Intent intent;
    GameLayout gameLayout;
    Random random = new Random();
    public Thread gameThread;
    public static EnemyObject enemy1;

    private volatile boolean playing;
    private boolean asdfg = true;
    private boolean startGame = true;
    private boolean startCount = true;
    private boolean timeFirst = true;
    private boolean groundMoving = true;
    private boolean firstGroundSet = true;
    private boolean isCharacterset = true;
    public boolean forSecondGround = true;

    private long thisTimeFrame;
    private long startFrameTime, oldTime, recentTime;
    private float fps;
    private float characterX, characterY;

    private int timecount = 0;
    private int currentStage = 1;
    private int who;

    public GameView(Context context, int stage, int who){
        super(context);
        this.who = who;
        c = context;
        ourHolder = getHolder();
        isCheck = true;
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
        ground4 = ground;
        //------------------------------------------------------------------------
        for(int i=0;i<10;i++){ground1[i] = new Ground(c,R.drawable.goundtest);}
        for(int i=0;i<10;i++){ground2[i] = new Ground(c,R.drawable.goundtest);}
        //------------------------------------------------------------------------
        enemy1 = new EnemyObject(c,R.drawable.enemy1);
        progress = new Progress(c,R.drawable.progress);
        this.currentStage = GameLayout.currentStage;
        /*for(int i=0;i<5;i++) {
            grounds.add(i, ground1);
            //grounds.get(i).num = random.nextInt()%5;
        }*/
        startGround1 = true;
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
                                Thread.sleep(2000);

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
        if(firstSet) {cvsWidth = getWidth(); cvsHeight = getHeight(); firstSet = false;}
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
        if(startGround1){ if(ground.getXRight() < 0){startGround1 = false; }}
        if(mainCharacter.getYpos() > deadLine){
            mainCharacter.setCurrentHFrame(3);
            mainCharacter.setCurrentFrame(0);
            if (asdfg) {
                mainCharacter.setCurrentHFrame(0);
                asdfg = false;
            }
            if(mainCharacter.getCurrentHFrame() == 3){
                if(mainCharacter.getCurrentFrame() == 18){
                    Intent intent = new Intent(activity,GameOverActivity.class);
                    activity.startActivity(intent);
                    playing = false;
                }
            }}
            mainCharacter.setCurrentHFrame(0);
    }

    public void manageCurrentFrame(){
        long time = System.currentTimeMillis();

        if(mainCharacter.isMoving()){
            if(time > mainCharacter.getLastFrameChangeTime() + mainCharacter.getFrameLengthInMillisecond()){
                mainCharacter.setLastFrameChangeTime(time);
                if(mainCharacter.isJumpcheck() && mainCharacter.getCurrentHFrame() != 3){
                    mainCharacter.setCurrentHFrame(1);
                }else if(!mainCharacter.isJumpcheck() && mainCharacter.getCurrentHFrame() == 1){
                    mainCharacter.setCurrentHFrame(0);
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
            if(startGround1){ground.drawObj();drawGround();}
            else{drawGround();}
            if(bullet != null ){bullet.drawObj();}
            //if(enemy1.isMoving()){enemy1.drawObj();}
            enemy1.drawObj();
            //Log.d("asg","캐릭 YBottom : " + mainCharacter.getYBottom() + " 땅 Y : " + ground4.getYpos());
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
        //캐릭터 밑에 있는지 체크
        //Log.d("truth","" + isCharacterOnGround());
        if(!isCharacterOnGround()){
            if(!mainCharacter.isJumpcheck()){
                mainCharacter.setYpos(mainCharacter.getYpos() + mainCharacter.getJumpSpeed()/fps);
            }
            if(characterY - mainCharacter.getFrameHeight()> deadLine) {
                Intent intent = new Intent(activity, GameOverActivity.class);
                activity.startActivity(intent);
                playing = false;
            }
        }
        //캐릭터 점프체크
        if(mainCharacter.isJumpcheck()){
            if(isCheck) {
                if(mainCharacter.isJumping()) {
                    mainCharacter.setCurrentHeight(mainCharacter.getYpos()+60);
                    mainCharacter.setJumping(false);
                }
                mainCharacter.setYpos(mainCharacter.getYpos() - mainCharacter.getJumpSpeed() / fps);
                if(mainCharacter.getYBottom()< mainCharacter.getCurrentHeight()){
                    isCheck = false;
                }
            }else{
                mainCharacter.setYpos(mainCharacter.getYpos() + mainCharacter.getJumpSpeed()/fps);
                if(ground4.getYpos() - mainCharacter.getYBottom() < 27 &&  ground4.getYpos() - mainCharacter.getYBottom() > -27){

                    isCheck = true;
                    mainCharacter.setJumpcheck(false);
                    mainCharacter.setYBottom(ground4.getYpos());
                    mainCharacter.setYpos(ground4.getYpos()-mainCharacter.getFrameHeight());
                    mainCharacter.setJumping(true);
                }else if(mainCharacter.getYBottom() > ground4.getYpos()){


                    mainCharacter.setJumpcheck(false);

                    mainCharacter.setCurrentHFrame(3);
                    mainCharacter.setCurrentFrame(0);
                    if (asdfg) {
                        mainCharacter.setCurrentHFrame(0);
                        asdfg = false;
                    }
                    if(mainCharacter.getCurrentHFrame() == 3){
                        if(mainCharacter.getCurrentFrame() == 18){
                            Intent intent = new Intent(activity,GameOverActivity.class);
                            activity.startActivity(intent);
                            playing = false;
                        }
                    }
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

        // 이후 땅.
        if (ground1[0].isMoving()) {
            //------------------------------------------------------------------------
            ground1[0].setXpos(ground1[0].getXpos() - (float)ground1[0].getRunSpeed());
            ground1[0].setXRight(ground1[0].getXpos()+ground1[0].frameWidth);
            for(int i=1;i<10;i++){
                ground1[i].setXpos(ground1[i-1].getXRight());
                ground1[i].setXRight(ground1[i].getXpos()+ground1[i].frameWidth);
            }
            //------------------------------------------------------------------------
            ground2[0].setXpos(ground2[0].getXpos() - (float)ground1[0].getRunSpeed());
            ground2[0].setXRight(ground2[0].getXpos()+ground2[0].frameWidth);
            for(int i=1;i<10;i++){
                ground2[i].setXpos(ground2[i-1].getXRight());
                ground2[i].setXRight(ground2[i].getXpos()+ground2[i].frameWidth);
            }
            //------------------------------------------------------------------------
        }
        if(startGround1 == false) {
            if (ground1[9].getXRight() < 0) {
                ground1[0].setXpos(ground2[9].getXRight());
                groundMoving = true;
                createGroundPattern();
            }
            if (ground2[9].getXRight() < 0) {
                ground2[0].setXpos(ground1[9].getXRight());
                groundMoving = false;
                createGroundPattern();
            }
        }
    }
    public void createGroundPattern(){

        if(groundMoving){ ground3 = ground1;}
        else{ ground3 = ground2;}
        for(int i=0;i<10;i++){
            ground3[i].setYpos(ground.getYpos());
        }
        switch (random.nextInt(5)){
            case 0:
                for(int i = 1;i<9;i+=2) {
                    ground3[i].setYpos(cvsHeight);
                }
                break;

            case 1:
                ground3[2].setYpos(ground.getYpos()-ground.getFrameHeight());
                ground3[6].setYpos(ground3[2].getYpos());
                break;

            case 2:
                ground3[1].setYpos(ground.getYpos()-ground.getFrameHeight());
                ground3[2].setYpos(ground3[1].getYpos()-ground.getFrameHeight());
                ground3[3].setYpos(ground3[2].getYpos());
                ground3[4].setYpos(cvsHeight);
                ground3[5].setYpos(ground3[2].getYpos());
                ground3[6].setYpos(cvsHeight);
            break;

            case 3:
                for(int i=1;i<10;i+=2){
                    ground3[i].setYpos(ground.getYpos()-ground.getFrameHeight());
                }
                for(int i=3;i<10;i+=3){
                    ground3[i].setYpos(cvsHeight);
                }

                break;

            case 4:
                for(int i=3;i<10;i+=3){
                    ground3[i].setYpos(cvsHeight);
                }
                break;

            default:
                break;

        }

        if(groundMoving){ ground1 = ground3;}
        else{ ground2 = ground3;}
        groundMoving = !groundMoving;

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
                if (asdfg) {
                    mainCharacter.setCurrentHFrame(0);
                    asdfg = false;
                }
                if(mainCharacter.getCurrentHFrame() == 3){
                    if(mainCharacter.getCurrentFrame() == 18){
                        Intent intent = new Intent(activity,GameOverActivity.class);
                        activity.startActivity(intent);
                        playing = false;
                    }
                }

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
    /*public void die(){

            mainCharacter.setCurrentHFrame(3);
            mainCharacter.setCurrentFrame(0);
            if (asdfg) {
                mainCharacter.setCurrentHFrame(0);
                asdfg = false;
            }
            if(mainCharacter.getCurrentHFrame() == 3){
                if(mainCharacter.getCurrentFrame() == 18){
                    Intent intent = new Intent(activity,GameOverActivity.class);
                    activity.startActivity(intent);
                    playing = false;
                }
            }
    }*/
    public void drawGround(){

        if(forSecondGround){ // 초기 설정.
            //------------------------------------------------------------------------
            ground1[0].setXpos(cvsWidth-10);
            ground1[0].setXRight(ground1[0].getXpos()+ground1[0].getFrameWidth());
            for(int i=1;i<10;i++){
                ground1[i].setXpos(ground1[i-1].getXRight());
                ground1[i].drawObj();
            }
            //------------------------------------------------------------------------

            ground2[0].setXpos(ground1[9].getXRight());
            ground2[0].setXRight(ground2[0].getXpos()+ground2[0].getFrameWidth());
            for(int i=1;i<10;i++){
                ground2[i].setXpos(ground2[i-1].getXRight());
                ground2[i].drawObj();
            }
            //------------------------------------------------------------------------

            forSecondGround = false;
        }

        for(int i=0;i<10;i++){

            ground1[i].drawObj();
            ground2[i].drawObj();
            if(firstGroundSet) {
                firstGroundSetting();
                firstGroundSet = false;
            }
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
    private void firstGroundSetting(){
        for(int i=0;i<10;i++) {
            if (i % 2 == 0) {
                ground1[i].setYpos(cvsWidth-300);
                ground1[i].setXY();
            }
        }
        ground1[2].setYpos(ground.getYpos()-ground.getFrameHeight());
        ground1[6].setYpos(ground.getYpos());

    }
    private boolean isCharacterOnGround(){
        if(isCharacterset) {
            characterX = mainCharacter.getXpos() + mainCharacter.getFrameWidth() / 2;
            characterY = mainCharacter.getYBottom();
        }
            for(int i=0;i<10;i++){

                if(ground1[i].getXpos()<characterX && ground1[i].getXRight()>characterX) { // 머리박았을때.
                    if (ground1[i].getYpos() < characterY || ground1[i].getYBottom() < characterY){
                        ground4 = ground1[i];
                        return false;
                    }else if(ground1[i].getYpos() > characterY && ground1[i].getYpos() - 27 > characterY){ // 낭떠러지
                        ground4 = ground1[i];
                        return false;
                    }
                    else{
                        ground4 = ground1[i];
                        return true;
                    }
                }

                else if(ground2[i].getXpos()<characterX && ground2[i].getXRight()>characterX) {
                    if (ground2[i].getYpos() < characterY || ground2[i].getYBottom() < characterY){
                        ground4 = ground2[i];
                        return false;
                    }else if(ground2[i].getYpos() > characterY && ground2[i].getYpos()+10 > characterY){
                        ground4 = ground2[i];
                        return false;
                    }
                    else{
                        ground4 = ground2[i];
                        return true;
                    }
                }

            }
        return true;
    }
}