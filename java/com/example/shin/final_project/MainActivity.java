package com.example.shin.final_project;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private GameView gameView;
    //private GameView1 gameView1;
    public cvs cvs = new cvs();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // gameView = new GameView(this);
        deleteStatusBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        deleteStatusBar();
       // gameView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        deleteStatusBar();
      //  gameView.pause();
    }


    public void startGame(View view) {
        Intent intent = new Intent(MainActivity.this,GameLayout.class);
        startActivity(intent);
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
