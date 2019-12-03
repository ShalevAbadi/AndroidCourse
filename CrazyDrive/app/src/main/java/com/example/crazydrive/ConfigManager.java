package com.example.crazydrive;

public class ConfigManager {
    private static ConfigManager instance;
    private int lanesCount = 3;
    private int initialLifeCount = 3;
    private int laneWidth;
    private int roadLength;
    private IMoveController moveController;
    private SpeedController speedController;
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

    public void setSpeedController(SpeedController speedController1){
        this.speedController = speedController1;
    }

    public IMoveController getMoveController(){
        return moveController;
    }

    public int getRoadLength() {
        return roadLength;
    }

    public int getLanesCount(){
        return lanesCount;
    }

    public int getLaneWidth(){
        return laneWidth;
    }

    public int getInitialLivesCount(){
        return initialLifeCount;
    }

    public SpeedController getSpeedController(){
        return speedController;
    }
}
