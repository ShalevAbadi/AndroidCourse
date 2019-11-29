package com.example.crazydrive;

public class ConfigManager {
    private static ConfigManager instance;
    private int lanesCount = 3;
    private int initialLifeCount = 3;
    private ConfigManager(){
    }

    public static ConfigManager getInstance(){
        if(ConfigManager.instance == null){
            ConfigManager.instance = new ConfigManager();
        }
        return ConfigManager.instance;
    }

    public void setLanesCount(int lanes){
        this.lanesCount = lanes;
    }

    public void setInitialLife(int life){
        this.initialLifeCount = life;
    }

    public int getLanesCount(){
        return this.lanesCount;
    }

    public int getInitialLifesCount(){
        return this.initialLifeCount;
    }
}
