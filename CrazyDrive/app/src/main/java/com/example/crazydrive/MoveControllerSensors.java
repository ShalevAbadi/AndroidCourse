package com.example.crazydrive;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MoveControllerSensors extends GeneralMoveController implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensor;
    private float laneDelta = 2;
    private float lastState;
    public MoveControllerSensors(int numOfLanes, int initialLane, Context context){
        super(numOfLanes, initialLane);
        sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

            float x = event.values[0];
            if(x != lastState) {
                lastState = x;
                for(int i = numOfLanes/2+1; i > -numOfLanes/2; i-- ){
                    if(x >= i*laneDelta){
                        lane = numOfLanes -(i + numOfLanes/2);
                        return;
                    }
                }
            }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
