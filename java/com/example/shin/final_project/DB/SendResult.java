package com.example.shin.final_project.DB;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendResult extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int version = android.os.Build.VERSION.SDK_INT;
        Log.d("sdk version:",version+"");
        if(version > 8){
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    public SendResult(final String id, final int score) throws Exception {
        String param = String.format("?name=%s&score=%d",id, score*1000);
        Log.d("getparam",id);
        final URL url = new URL("https://jay-d.me/gachonrunner/insert_db.php" + param);
        final int[] resCode = new int[1];
        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection con = null;
                try {
                    con = (HttpURLConnection) url.openConnection();
                    resCode[0] = con.getResponseCode();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("Code : " + resCode[0]);
            }
        }).start();

        /*MySQL mySQL = (MySQL)MySQL.Mactivity;
        WebView webview = new WebView(mySQL);
        webview.loadUrl("jay-d.me/gachonrunner/insert_db.php" + param);*/
    }

}