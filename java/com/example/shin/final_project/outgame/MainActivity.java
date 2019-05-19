package com.example.shin.final_project.outgame;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.shin.final_project.DB.DatabaseHelper;
import com.example.shin.final_project.DB.MyRecord;
import com.example.shin.final_project.DB.MySQL;
import com.example.shin.final_project.DB.RankActivity;
import com.example.shin.final_project.R;
import com.example.shin.final_project.ingame.GameView;
import com.example.shin.final_project.selectCharacter.SelectActivity;
import com.example.shin.final_project.staticItem.cvs;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    private GameView gameView;
    DatabaseHelper dbHelper;
    SQLiteDatabase database;
    MyRecord record;
    LinearLayout img;

    //private GameView1 gameView1;
    public com.example.shin.final_project.staticItem.cvs cvs = new cvs();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        img = findViewById(R.id.ml);
        Drawable alpha = img.getBackground();
        alpha.setAlpha(200);


        if(cvs.mainAcSet) {
            DatabaseHelper dbHelper;
            SQLiteDatabase database;
            dbHelper = new DatabaseHelper(MainActivity.this, "MyRecord.db", null, 1);
            database = dbHelper.getWritableDatabase();
            cvs.stage = Integer.valueOf(dbHelper.openMax().score);
            Log.d("fid",""+cvs.stage);
            cvs.mainAcSet = false;
        }
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
        Intent intent = new Intent(MainActivity.this,SelectActivity.class);
        intent.putExtra("stage",1);
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

    public void gameOption(View view) {
        intent = new Intent(this, OptionActivity.class);
        startActivity(intent);
    }

    public void exitGame(View view) {
        finish();
    }

    public void selectStage(View view) {
        intent = new Intent(this,StageActivity.class);
        startActivity(intent);
    }
    public void goToDB(View view) {
        intent = new Intent(this, MySQL.class);
        startActivity(intent);
    }

    public void watchRank(View view) {
        intent = new Intent(this, RankActivity.class);
        startActivity(intent);
    }
}
