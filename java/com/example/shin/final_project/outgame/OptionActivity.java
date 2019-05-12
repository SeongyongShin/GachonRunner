package com.example.shin.final_project.outgame;

import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;

import com.example.shin.final_project.DB.DatabaseHelper;
import com.example.shin.final_project.R;
import com.example.shin.final_project.staticItem.cvs;

public class OptionActivity extends AppCompatActivity implements View.OnClickListener{

    public CheckBox c1,c2,c3;
    public static boolean c_1 = true,c_2 = true,c_3= true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_option);
        deleteStatusBar();

        c1 = findViewById(R.id.c1);c1.setOnClickListener(this);
        c2 = findViewById(R.id.c2);c2.setOnClickListener(this);
        c1.setChecked(c_1);
        c2.setChecked(true);

    }

    public void mOnClose(View v){
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        return;
    }

    public void mOnPopupClick(View view) {
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
            case R.id.c1:
                cvs.optionJump = !cvs.optionJump;
                c_1 = !c_1;
                break;
            case R.id.c2:
                DatabaseHelper dbHelper;
                SQLiteDatabase database;
                dbHelper = new DatabaseHelper(this, "MyRecord.db", null, 1);
                database = dbHelper.getWritableDatabase();
                dbHelper.delete_Table();
                dbHelper.close();
            default: break;
        }
    }
}