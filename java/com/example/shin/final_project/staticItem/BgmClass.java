package com.example.shin.final_project.staticItem;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;

import com.example.shin.final_project.R;

public class BgmClass extends AppCompatActivity{
    public static MediaPlayer mp;
    public static boolean mpPlay = true;
    public static boolean firstPlay = true;
    public static boolean isPlaying = true;
    static Context c;

    public void BgmClass1(Context c) {
        this.c = c;
        playbgm();
    }

    public static void playbgm(){
        if(isPlaying && mpPlay) {
            mp = MediaPlayer.create(c, R.raw.bgm);
            mp.setLooping(true);
            mp.setVolume(0.1f,0.1f);
            mp.start();
            isPlaying = false;
        }
    }
    public static void stopbgm(){
        mp.stop();
    }
}
