package com.example.shin.final_project.DB;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shin.final_project.R;
import com.example.shin.final_project.staticItem.cvs;

public class MySQL extends AppCompatActivity implements View.OnClickListener{

    DatabaseHelper dbHelper;
    SQLiteDatabase database;
    TextView myscore;
    EditText name;
    ImageView img;
    Button save,main;
    int version = 1;
    String str = "";
    SendResult sendResult;
    public static Activity Mactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deleteStatusBar();
        setContentView(R.layout.activity_mysql);
        Mactivity = MySQL.this;
        myscore = findViewById(R.id.myscore);
        save = findViewById(R.id.saveButton);save.setOnClickListener(this);
        main = findViewById(R.id.m2);main.setOnClickListener(this);
        name = findViewById(R.id.myName);
        img = findViewById(R.id.img);
        Drawable alpha = img.getDrawable();
        alpha.setAlpha(100);

        Toast.makeText(getApplicationContext(),"이름을 입력하세요",Toast.LENGTH_SHORT).show();
        myscore.setText("Stage : "+ (cvs.sqlStage));
        dbHelper = new DatabaseHelper(MySQL.this, "MyRecord.db", null, version);
        database = dbHelper.getWritableDatabase();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.saveButton) {
            str = name.getText().toString().trim();
        if(str == ""){
            Toast.makeText(getApplicationContext(),"이름을 입력하세요",Toast.LENGTH_SHORT).show();
        }else {
                dbHelper.insert(str, String.valueOf((cvs.sqlStage)));
            Toast.makeText(getApplicationContext(),"저장되었습니다\n"+str + "  "+(cvs.sqlStage*1000) + " 점 ",Toast.LENGTH_SHORT).show();
                //myscore.setText(dbHelper.open(str).name+"\n"+(cvs.stage - 1));
                //Log.d("ass","Record.score : " + dbHelper.open(str).score);
            dbHelper.getResult();
            try {
                Log.d("getparam",str);
                sendResult = new SendResult(str,Integer.valueOf(cvs.sqlStage));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        }else if(v.getId() == R.id.m2){
            finish();
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
    protected void onResume() {
        super.onResume();
        deleteStatusBar();
    }

    @Override
    protected void onPause() {
        super.onPause();
        deleteStatusBar();
    }
}
