package com.example.shin.final_project.outgame;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shin.final_project.R;
import com.example.shin.final_project.staticItem.cvs;

public class GameEndActivity extends AppCompatActivity implements View.OnClickListener{

    Button goToMenu, nextStage;
    TextView gameEnd;
    Intent intent;
    GameLayout gameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        deleteStatusBar();
        gameEnd = findViewById(R.id.endText);
        goToMenu = findViewById(R.id.goToMenu); goToMenu.setOnClickListener(this);
        nextStage = findViewById(R.id.nextStage); nextStage.setOnClickListener(this);
        gameLayout = (GameLayout)GameLayout.activity;

        intent = getIntent();

        Log.d("asdfg",""+intent.getIntExtra("stage",1));
        gameEnd.setText("Stage " + (intent.getIntExtra("stage",1))+" Clear!");
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
            case R.id.goToMenu :
                /*intent = new Intent(GameEndActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                */
                gameLayout.finish();
                finish();
            break;
            case R.id.nextStage :
                gameLayout.finish();

                intent = new Intent(GameEndActivity.this,GameLayout.class);
                Intent intent1 = getIntent();

                if(cvs.nowMade < intent1.getIntExtra("stage",1)+1){
                    Toast.makeText(getApplicationContext(),"업데이트를 기대해주세요!",  Toast.LENGTH_SHORT).show();
                }else {
                    intent.putExtra("who", intent1.getIntExtra("who", 1));
                    intent.putExtra("stage", (intent1.getIntExtra("stage", 1) +1));
                    Log.d("asdfg",""+(intent1.getIntExtra("stage", 1) +1));
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
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
