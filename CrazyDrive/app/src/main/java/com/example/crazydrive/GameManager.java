package com.example.crazydrive;

public class GameManager {

    private static GameManager instance;
    private ConfigManager conf = ConfigManager.getInstance();
    private int lives = conf.getInitialLivesCount();
    private PlayerCar playerCar;
    private Road road;
    private long score = 0;
    private SpeedController speedController = conf.getSpeedController();
    private int increaseSpeedDelta = 100;
    private int stepsToIncreaseSpeed = increaseSpeedDelta;

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

    public static void resetGame(){
        instance = null;
    }

    public void doStep(){
        score += 1;
        increaseSpeed();
        road.moveAllRoadItems(playerCar.getSpeed());
        road.addRoadItems(playerCar);
        if(isCollision()){
            handleCollision();
        }
    }

    private void handleCollision(){
        RoadItem item = road.getFirstRoadItemAt(playerCar.getLane());
        road.removeFirstRoadItemAt(playerCar.getLane());
        if(item.getType() == RoadItem.Types.POLICE_CAR) {
            lives--;
        } if(item.getType() == RoadItem.Types.MONEY){
            score+=5;
        }
    }

    private void increaseSpeed(){
        stepsToIncreaseSpeed -=1;
        if(stepsToIncreaseSpeed == 0){
            speedController.setSpeed(speedController.getSpeed()+1);
            stepsToIncreaseSpeed = increaseSpeedDelta;
        }
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

    public long getScore(){
        return score;
    }

}
