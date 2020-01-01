package com.example.crazydrive;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;


public class TopTenDBHandler {

    public static String SHARED_PREFS = "SHARED_PREFS";
    public static String SCORES_KEY = "SCORES_KEY";
    private TopTenList records;
    private Gson gson = new Gson();
    private Context con;
    private SharedPreferences sp;

    public TopTenDBHandler(Context con){
        this.con = con;
        sp = con.getSharedPreferences(SHARED_PREFS, con.MODE_PRIVATE);
        updateRecordsFromDB();
    }

    public void updateRecordsFromDB(){
        records = gson.fromJson(sp.getString(TopTenDBHandler.SCORES_KEY, null), TopTenList.class);
        if(records == null){
            records = new TopTenList();
        }
        records.sortRecords();
    }

    public void saveRecordsToDB(){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(TopTenDBHandler.SCORES_KEY, gson.toJson(records) );
        editor.commit();
    }

    public ArrayList<TopTenRecord> getRecords(){
        return records.getRecords();
    }

    public boolean isTopTen(TopTenRecord record){
        return records.isTopTen(record);
    }

    public boolean insert(TopTenRecord record){

        if(records.isTopTen(record)){
            records.addToTopTen(record);
            records.sortRecords();
            saveRecordsToDB();
            return true;
        }
        return false;
    }

}
