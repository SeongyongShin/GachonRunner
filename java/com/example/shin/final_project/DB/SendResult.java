package com.example.shin.final_project.DB;

import java.net.HttpURLConnection;
import java.net.URL;

public class SendResult {
    public SendResult(String id, int score) throws Exception {

        String param = String.format("?name=%s&score=%d",id, score);
        URL url = new URL("https://jay-d.me/gachonrunner/insert_db.php" + param);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        int resCode = con.getResponseCode();
        System.out.println("Code : " + resCode);
    }
}