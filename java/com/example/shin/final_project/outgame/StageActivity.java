package com.example.shin.final_project.outgame;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shin.final_project.R;
import com.example.shin.final_project.selectCharacter.SelectActivity;
import com.example.shin.final_project.staticItem.cvs;

public class StageActivity extends AppCompatActivity implements View.OnClickListener{

    Intent intent;
    Button s1,s2,s3,s4,m1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);
        deleteStatusBar();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        Context context = getApplicationContext();
        s1 = findViewById(R.id.s1); s1.setOnClickListener(this);
        s2 = findViewById(R.id.s2); s2.setOnClickListener(this);
        s3 = findViewById(R.id.s3); s3.setOnClickListener(this);
        s4 = findViewById(R.id.s4); s4.setOnClickListener(this);
        m1 = findViewById(R.id.m1); m1.setOnClickListener(this);
        Log.d("asdfg",""+cvs.currentStage);
        Log.d("fid",""+cvs.stage);
        switch (cvs.stage){
            case 3 : s4.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.btn_stage4));
            case 2 : s3.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.btn_stage3));
            case 1 : s2.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.btn_stage2));
            break;
            default:break;
        }
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
            case R.id.s1:
                intent = new Intent(StageActivity.this,SelectActivity.class);
                intent.putExtra("stage",1);
                startActivity(intent);
                finish();
                break;
            case R.id.s2:
                if(cvs.stage<1){
                    Toast.makeText(this,"이전 스테이지(stage1)를 먼저 클리어하십시오.",Toast.LENGTH_SHORT).show();
                }else{
                    intent = new Intent(StageActivity.this,SelectActivity.class);
                    intent.putExtra("stage",2);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.s3:
                if(cvs.stage<2){
                    Toast.makeText(this,"이전 스테이지(stage2)를 먼저 클리어하십시오.",Toast.LENGTH_SHORT).show();
                }else{
                    intent = new Intent(StageActivity.this,SelectActivity.class);
                    intent.putExtra("stage",3);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.s4:
                if(cvs.stage<3){
                    Toast.makeText(this,"이전 스테이지(stage3)를 먼저 클리어하십시오.",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"아직 구현되지 않았습니다.",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                finish();
                break;
        }
    }
}
