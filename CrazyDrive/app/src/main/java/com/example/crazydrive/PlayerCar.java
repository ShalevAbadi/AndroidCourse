package com.example.crazydrive;

public class PlayerCar {

    private int speed;
    private int currentLane;
    private MoveController moveController;

    public PlayerCar(MoveController moveController){
        this.moveController = moveController;
        this.speed=300;
    }

    public PlayerCar(int speed){
        this.speed=speed;
    }

    public int getSpeed(){
        return this.speed;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public int getLane(){
        return this.moveController.getLane();
    }
}
