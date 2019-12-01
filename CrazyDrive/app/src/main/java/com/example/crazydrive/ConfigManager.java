package com.example.crazydrive;

public class ConfigManager {
    private static ConfigManager instance;
    private int lanesCount = 3;
    private int initialLifeCount = 3;
    private int laneWidth;
    private int roadLength;
    private IMoveController moveController;
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

    public void setLaneWidth(int width){this.laneWidth = width;}

    public void setInitialLife(int life){
        this.initialLifeCount = life;
    }

    public void setRoadLength(int roadLength) {
        this.roadLength = roadLength;
    }

    public void setMoveController(IMoveController moveController) {
        this.moveController = moveController;
    }

    public IMoveController getMoveController(){
        return moveController;
    }

    public int getRoadLength() {
        return roadLength;
    }

    public int getLanesCount(){
        return this.lanesCount;
    }
    public int getLaneWidth(){
        return this.laneWidth;
    }


    public int getInitialLifesCount(){
        return this.initialLifeCount;
    }
}
