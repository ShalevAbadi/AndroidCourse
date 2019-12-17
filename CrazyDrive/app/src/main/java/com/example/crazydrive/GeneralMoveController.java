package com.example.crazydrive;

public class GeneralMoveController implements IMoveController {
    protected int numOfLanes;
    protected int lane;

    public GeneralMoveController(int numOfLanes, int initialLane){
        this.numOfLanes = numOfLanes;
        this.lane = initialLane;
    }

    public int getLane(){
        return this.lane;
    }


}
