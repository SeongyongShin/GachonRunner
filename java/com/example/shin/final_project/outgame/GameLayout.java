package com.example.shin.final_project.outgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.shin.final_project.R;
import com.example.shin.final_project.ingame.GameView;

import static com.example.shin.final_project.outgame.LoadingActivity.activity1;
import static com.example.shin.final_project.staticItem.cvs.backG;
import static com.example.shin.final_project.staticItem.cvs.isWaiting;
import static com.example.shin.final_project.staticItem.cvs.startGround;
import static com.example.shin.final_project.staticItem.cvs.startGround1;

public class GameLayout extends AppCompatActivity {
    private GameView gameView;
    public static Activity activity;
    public TextView t1;
    public TextView t2;
    private ConstraintLayout gameLayout;
    public static TextView gameTime;
    public static Button jumpBtn;
    public static Button atkBtn;
    public static int currentStage = 1;
    int stage,who;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = GameLayout.this;
        intent = getIntent();
        try {
            this.stage = intent.getIntExtra("stage",1);
            this.who = intent.getIntExtra("who",1);
        }catch (Exception e){
            this.stage = 1;
            this.who = 1;
        }

        currentStage = stage;
        deleteStatusBar();
        setContentView(R.layout.activity_game_layout);
        gameLayout = findViewById(R.id.gameLayout);
        gameTime = findViewById(R.id.time);
        jumpBtn = findViewById(R.id.jump);
        atkBtn = findViewById(R.id.attack);
        t1 = (TextView)findViewById(R.id.T1);
        t2 = (TextView)findViewById(R.id.T2);
        t1.setVisibility(View.VISIBLE);
        t2.setVisibility(View.VISIBLE);
        String str = "STAGE ";
        str += Integer.toString(currentStage);
        t1.setText(str);
        gameView = new GameView(this,stage,who);
        gameLayout.addView(gameView,0);
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);


    }
    @Override
    protected void onResume() {
        super.onResume();
        setting(false);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setting(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        setting(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setting(true);
    }

    private void deleteStatusBar(){
        View decorView = getWindow().getDecorView();
        int uiOption = decorView.getSystemUiVisibility();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility( uiOption );
    }
    public void setting(boolean p){
        backG = true;
        startGround1 = true;
        startGround = true;
        deleteStatusBar();
        if(p)gameView.pause();
        else gameView.resume();
    }
    public void reMoveView(){
        gameView = null;
        finish();
    }
    public void Dialog() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(GameLayout.this,LoadingActivity.class);
                startActivity(intent);
                try {
                    Thread.sleep(2000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                activity1.finish();
                isWaiting = true;
            }
        }).start();
    }

}
