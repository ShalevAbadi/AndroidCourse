package com.example.crazydrive;

import android.util.Log;

public class GameManager {

    private static GameManager instance;
    private int score = 0;
    private ConfigManager conf = ConfigManager.getInstance();
    private int lives = conf.getInitialLifesCount();
    private PlayerCar playerCar;
    private Road road;
    private GameManager(){
        this.playerCar = new PlayerCar(conf.getMoveController(), new SpeedController(30), conf.getLaneWidth(), conf.getLaneWidth()/2);
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
        road.addRoadItems();
        if (road.getFirstRoadItemAt(playerCar.getLane()) != null){
            Log.i("Car Pos at: ","lane: " + playerCar.getLane() + "yPos" + road.getFirstRoadItemAt(playerCar.getLane()).getYPos());
        }
    }

    public int start(){
        return this.score;
    }

    public int getPlayerCarLane(){
        return this.playerCar.getLane();
    }

    public Road getRoad(){
        return road;
    }

    public PlayerCar getPlayerCar(){
        return playerCar;
    }


}
