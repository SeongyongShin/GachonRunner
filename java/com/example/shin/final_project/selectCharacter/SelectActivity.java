package com.example.shin.final_project.selectCharacter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.shin.final_project.R;

public class SelectActivity extends AppCompatActivity {
    public static Activity Sactivity;
    ConstraintLayout shin,kim;
    TextView t1;
    SelectView s1,k1;
    Thread gameThread, gameThread1;
    Intent intent;
    int stage;
    public static float tt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        deleteStatusBar();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        Context context = this;
        Sactivity = SelectActivity.this;
        shin =findViewById(R.id.shin);
        intent = getIntent();
        stage = intent.getIntExtra("stage",1);

        s1 = new SelectView(context,stage);
        t1 = findViewById(R.id.twidth);
        tt = t1.getY();
        shin.addView(s1,0);
        gameThread = new Thread(s1);
        gameThread.start();
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);

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
