package com.example.shin.final_project.DB;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class GetRank {
    TextView tv;
    public GetRank(final String articleURL, final TextView tv) throws Exception {
        this.tv = tv;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Document doc = null;
                try {
                    doc = Jsoup.connect(articleURL).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements ele = doc.select("p");
                String str = ele.text();
                String str1 = "순위"+"\t\t\t"+"이름"+"\t\t\t"+"점수\n";

                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObj = (JsonObject) jsonParser.parse(str);
                JsonArray array = (JsonArray) jsonObj.get("ranking");
                System.out.println("등수  이름    점수");
                for(int i = 0; i< array.size(); i++) {
                    JsonObject jsonObject = (JsonObject) array.get(i);

                    System.out.println(i+1 + ".\t\t\t" +jsonObject.get("name").getAsString()+ "\t\t\t" + jsonObject.get("score").getAsString());
                    staticRank.rank = i+1;
                    staticRank.username = jsonObject.get("name").getAsString();
                    staticRank.userScore = jsonObject.get("score").getAsString();
                    str1 += i+1 + ".\t\t\t" + jsonObject.get("name").getAsString() + "\t\t\t" + jsonObject.get("score").getAsString() + "\n";
                    tv.setText(str1);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
