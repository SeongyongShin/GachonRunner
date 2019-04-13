package com.example.shin.final_project;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameLayout extends AppCompatActivity {
    private GameView gameView;
    private ConstraintLayout gameLayout;
    static Button jumpBtn, atkBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        deleteStatusBar();
        setContentView(R.layout.activity_game_layout);
        gameLayout = findViewById(R.id.gameLayout);
        jumpBtn = findViewById(R.id.jump);
        atkBtn = findViewById(R.id.attack);
        gameView = new GameView(this);
        gameLayout.addView(gameView,0);
    }
    @Override
    protected void onResume() {
        super.onResume();
        deleteStatusBar();
        gameView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        deleteStatusBar();
        gameView.pause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        deleteStatusBar();
        gameView.pause();
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

}
