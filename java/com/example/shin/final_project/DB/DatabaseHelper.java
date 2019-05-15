package com.example.shin.final_project.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        db.execSQL("CREATE TABLE RECORD (name TEXT, score TEXT);");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String name, String score) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO RECORD VALUES('" + name + "', '" + score + "')");
        db.close();
    }

    public void update(String name, String score) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 정보 수정
        db.execSQL("UPDATE RECORD SET score=" + score + " WHERE name='" + name + "';");
        db.close();
    }

    public void delete(String name) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM RECORD WHERE name='" + name + "';");
        db.close();
    }
    public void delete_Table() {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제

        try {
            db.execSQL("CREATE TABLE RECORD (name TEXT, score TEXT);");
            db.execSQL("DROP TABLE RECORD");
            db.close();
        }catch (Exception e){}
        try {
            db.execSQL("DROP TABLE RECORD");
            db.execSQL("CREATE TABLE RECORD (name TEXT, score TEXT);");
            db.close();
        }catch (Exception e){}
        try {
            db.execSQL("CREATE TABLE RECORD (name TEXT, score TEXT);");
            db.execSQL("DROP TABLE RECORD");
            db.execSQL("CREATE TABLE RECORD (name TEXT, score TEXT);");
            db.close();
        }catch (Exception e){}

    }

    public String getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM RECORD", null);
        while (cursor.moveToNext()) {
            result +=
                    cursor.getString(0)
                            + " | "
                            + cursor.getString(1)
                            + "\n";
            Log.d("ass",result + "------------");
        }

        return result;
    }
    public MyRecord open(String name) {
        // 읽기가 가능하게 DB 열기
        MyRecord myRecord = new MyRecord();
        SQLiteDatabase db = getReadableDatabase();
        int max = 0;
        int count = 0;
        String str = "";
        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM RECORD", null);
        cursor.moveToFirst();
        while(cursor.moveToNext()){
            count ++;
        }
        cursor.moveToFirst();
        try {
            while (cursor.moveToNext()) {
                myRecord.name = cursor.getString(0);
                myRecord.score = cursor.getString(1);
                str = myRecord.name;
                if(max < Integer.valueOf(myRecord.score)){ max = Integer.valueOf(myRecord.score);}
            }
            db.close();
        }catch (Exception e){}
        myRecord.name = str;
        myRecord.score = String.valueOf(max);
        return myRecord;
    }

    public MyRecord openMax() {
        // 읽기가 가능하게 DB 열기
        MyRecord myRecord = new MyRecord();
        SQLiteDatabase db = getReadableDatabase();
        int max = 0;
        int count = 0;
        int p=0;
        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM RECORD", null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            count++;
            Log.d("assd",""+count);
        }
        cursor.moveToFirst();
        try {
            while (p<=count) {
                myRecord.score = cursor.getString(1);
                Log.d("assd",""+cursor.getString(0)+"  "+myRecord.score);

                if(max < Integer.valueOf(myRecord.score)){ max = Integer.valueOf(myRecord.score);}

                cursor.moveToNext();
                p++;
            }
            db.close();
        }catch (Exception e){}
        myRecord.score = String.valueOf(max);
        return myRecord;
    }
}
