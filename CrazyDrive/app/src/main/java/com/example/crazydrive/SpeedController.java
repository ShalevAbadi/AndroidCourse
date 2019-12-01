package com.example.crazydrive;

public class SpeedController implements ISpeedController {

    int speed = 0;

    public SpeedController(int speed){
        this.speed = speed;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public int getSpeed(){
        return speed;
    }


}
