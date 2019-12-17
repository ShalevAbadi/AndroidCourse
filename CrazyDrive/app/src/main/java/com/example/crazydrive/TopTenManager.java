package com.example.crazydrive;

import android.content.Context;

public class TopTenManager {

    ExternalStorageManager storage;
    final String STORAGE_FILE_NAME = "Top 10.json";

    public TopTenManager(Context context){
        storage = new ExternalStorageManager(context, STORAGE_FILE_NAME);
    }

    //getTopTen()
    //isTopTen()
    //addToTopTen()
}
