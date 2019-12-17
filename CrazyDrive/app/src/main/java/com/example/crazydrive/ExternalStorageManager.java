package com.example.crazydrive;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import static android.content.Context.MODE_PRIVATE;

public class ExternalStorageManager {

    private String fileName;
    private Context context;

    public ExternalStorageManager(Context context, String fileName){
        this.context = context;
        this.fileName = fileName;
    }

    private void save(String content) {
        FileOutputStream fos = null;
        Writer out = null;
        try {
            File file = new File(getAppRootDir(), fileName);
            fos = new FileOutputStream(file);
            out = new OutputStreamWriter(fos, "UTF-8");

            out.write(content);
            out.flush();
        } catch (Throwable e){
            e.printStackTrace();
        } finally {
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException ignored) {}
            }
            if(out!= null){
                try {
                    out.close();
                } catch (IOException ignored) {}
            }
        }
    }

    private String load() {
        String res = null;
        File file = new File(getAppRootDir(), fileName);
        if(!file.exists()){
            Log.e("", "file " +file.getAbsolutePath()+ " not found");
            return null;
        }
        FileInputStream fis = null;
        BufferedReader inputReader = null;
        try {
            fis = new FileInputStream(file);
            inputReader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
            StringBuilder strBuilder = new StringBuilder();
            String line;
            while ((line = inputReader.readLine()) != null) {
                strBuilder.append(line + "\n");
            }
            res = strBuilder.toString();
        } catch(Throwable e){
            if(fis!=null){
                try {
                    fis.close();
                } catch (IOException ignored) {}
            }
            if(inputReader!= null){
                try {
                    inputReader.close();
                } catch (IOException ignored) {}
            }
        }
        return res;
    }

    public File getAppRootDir() {
        File appRootDir;
        boolean externalStorageAvailable;
        boolean externalStorageWriteable;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            externalStorageAvailable = externalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            externalStorageAvailable = true;
            externalStorageWriteable = false;
        } else {
            externalStorageAvailable = externalStorageWriteable = false;
        }
        if (externalStorageAvailable && externalStorageWriteable) {
            appRootDir = context.getExternalFilesDir(null);
        } else {
            appRootDir = context.getDir("appRootDir", MODE_PRIVATE);
        }
        if (!appRootDir.exists()) {
            appRootDir.mkdir();
        }
        return appRootDir;
    }

}
