package com.example.shin.final_project.outgame;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.shin.final_project.R;

public class GameOverActivity extends AppCompatActivity implements View.OnClickListener{

    Button goToMenu, retry;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        deleteStatusBar();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        goToMenu = findViewById(R.id.goToMenu1); goToMenu.setOnClickListener(this);
        retry = findViewById(R.id.retry); retry.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goToMenu1 :
                intent = new Intent(GameOverActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                GameLayout gameLayout = (GameLayout)GameLayout.activity;
                gameLayout.finish();
                finish();
                break;
            case R.id.retry :
                gameLayout = (GameLayout) GameLayout.activity;
                gameLayout.finish();
                Intent intent1 = getIntent();
                intent = new Intent(GameOverActivity.this,GameLayout.class);
                intent.putExtra("who",intent1.getIntExtra("who",1));
                intent.putExtra("stage",intent1.getIntExtra("stage",1));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }
}
