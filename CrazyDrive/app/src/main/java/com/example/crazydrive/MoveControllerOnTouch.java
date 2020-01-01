package com.example.crazydrive;

import android.view.MotionEvent;
import android.view.View;

public class MoveControllerOnTouch extends GeneralMoveController implements View.OnTouchListener {

    public MoveControllerOnTouch(int numOfLanes, int initialLane){
        super(numOfLanes, initialLane);
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
