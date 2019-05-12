package com.example.shin.final_project.DB;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shin.final_project.R;

public class RankActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView img;
    TextView tv;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        deleteStatusBar();
        img = findViewById(R.id.img2);
        tv = findViewById(R.id.rankBoard);
        button = findViewById(R.id.m0);button.setOnClickListener(this);
        Drawable alpha = img.getDrawable();
        alpha.setAlpha(100);
        try {
            GetRank getRank = new GetRank("https://jay-d.me/gachonrunner/fetch_rank.php", tv);
        } catch (Exception e) {
            e.printStackTrace();
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

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.m0){
            finish();
        }
    }
}
