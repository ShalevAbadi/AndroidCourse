package com.example.crazydrive;

import android.util.Log;

public class GameManager {

    private static GameManager instance;
    private ConfigManager conf = ConfigManager.getInstance();
    private int lives = conf.getInitialLifesCount();
    private PlayerCar playerCar;
    private Road road;
    private SpeedController speedController = new SpeedController(10);
    private GameManager(){
        this.playerCar = new PlayerCar(conf.getMoveController(), speedController, conf.getLaneWidth(), conf.getLaneWidth()/2);
        this.road = new Road(conf.getLanesCount());
    }

    public static GameManager getInstance(){
        if(GameManager.instance == null){
            GameManager.instance = new GameManager();
        }
        return GameManager.instance;
    }

    public void doStep(){
        road.moveAllRoadItems(playerCar.getSpeed());
        road.addRoadItems(playerCar);
        if(isCollision()){
            handleCollision();
        }
    }

    private void handleCollision(){
        road.removeFirstRoadItemAt(playerCar.getLane());
        lives -=1;
    }

    private boolean isCollision(){
        RoadItem tempRoadItem = road.getFirstRoadItemAt(playerCar.getLane());
        return (tempRoadItem != null && (tempRoadItem.getYPos()+ tempRoadItem.getHeight() >= (conf.getRoadLength() - playerCar.getHeight())));
    }

    public Road getRoad(){
        return road;
    }

    public PlayerCar getPlayerCar(){
        return playerCar;
    }

    public int getLives(){
        return lives;
    }

}
