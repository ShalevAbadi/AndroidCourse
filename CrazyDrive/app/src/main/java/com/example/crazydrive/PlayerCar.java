package com.example.crazydrive;

public class PlayerCar implements ISafeZone{

    private ISpeedController speedController;
    private IMoveController moveController;
    private int height;
    private int width;


    public PlayerCar(IMoveController moveController, ISpeedController speedController, int height, int width){
        this.moveController = moveController;
        this.speedController = speedController;
        this.height = height;
        this.width = width;
    }

    public int getSpeed(){
        return this.speedController.getSpeed();
    }

    public int getLane(){
        return this.moveController.getLane();
    }

    public int getHeight(){
        return this.height;
    }

    public int getWidth(){
        return this.width;
    }

    @Override
    public int getSafeZone() {
        return this.height;
    }
}
