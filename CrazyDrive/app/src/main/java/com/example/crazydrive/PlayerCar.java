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
        return speedController.getSpeed();
    }

    public int getLane(){
        return moveController.getLane();
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    @Override
    public int getSafeZone() {
        return (int)(height*1.5);
    }
}
