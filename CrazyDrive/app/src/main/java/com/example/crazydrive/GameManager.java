package com.example.crazydrive;

public class GameManager {

    private static GameManager instance;
    private int score;
    private int lives = 3;
    private ConfigManager conf = ConfigManager.getInstance();
    private PlayerCar playerCar;

    private GameManager(){
        this.playerCar = new PlayerCar(new MoveController(this.conf.getLanesCount(), this.conf.getLanesCount()/2 + 1));
    }

    public static GameManager getInstance(){
        if(GameManager.instance == null){
            GameManager.instance = new GameManager();
        }
        return GameManager.instance;
    }

    public void setConfiguration(ConfigManager conf){
        this.conf = conf;
    }

    public int start(){
        return this.score;
    }

    public int getPlayerCarLane(){
        return this.playerCar.getLane();
    }


}
