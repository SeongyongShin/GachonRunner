package com.example.shin.final_project;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.shin.final_project.cvs.*;

public class GameLayout extends AppCompatActivity {
    private GameView gameView;
    public static Activity activity;
    private ConstraintLayout gameLayout;
    static TextView gameTime;
    static Button jumpBtn, atkBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = GameLayout.this;
        deleteStatusBar();
        setContentView(R.layout.activity_game_layout);
        gameLayout = findViewById(R.id.gameLayout);
        gameTime = findViewById(R.id.time);
        jumpBtn = findViewById(R.id.jump);
        atkBtn = findViewById(R.id.attack);
        gameView = new GameView(this);
        gameLayout.addView(gameView,0);

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

}
