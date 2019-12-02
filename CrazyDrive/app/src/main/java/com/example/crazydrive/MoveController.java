package com.example.crazydrive;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MoveController implements IMoveController, View.OnTouchListener {

    private int numOfLanes;
    private int lane;

    public MoveController(int numOfLanes, int initialLane){
        this.numOfLanes = numOfLanes;
        this.lane = initialLane;
    }

    public void moveRight(){
        if(this.lane < numOfLanes-1){
            this.lane+=1;
        }
    }

    public void moveLeft(){
        if(this.lane > 0){
            this.lane-=1;
        }
    }

    public int getLane(){
        return this.lane;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){

         int x =  v.getWidth();

        if(event.getX() < x/2){
            moveLeft();
        } else if (event.getX() >= x/2) {
            moveRight();
        }
            return true;
        }
        return true;
    }
}
