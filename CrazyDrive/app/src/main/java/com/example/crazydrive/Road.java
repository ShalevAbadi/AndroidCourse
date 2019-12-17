package com.example.crazydrive;

import java.util.ArrayList;
import java.util.Random;

public class Road {

    ArrayList<RoadItem>[] roadItemsQueues;

    public Road(int lanesCount){
        roadItemsQueues = new ArrayList[lanesCount];
        for (int i = 0; i < roadItemsQueues.length; i++) {
            roadItemsQueues[i] = new ArrayList();
        }
    }

    public void addRoadItems(ISafeZone safeZone){
        double chance = 0.7;
        if(isAddPossible(safeZone)) {
            ConfigManager conf = ConfigManager.getInstance();
            Random r = new Random();
            int i = r.nextInt((conf.getLanesCount()));
                if (Math.random() < chance) {
                    roadItemsQueues[i].add(new RoadItem(-conf.getLaneWidth(), conf.getLaneWidth()/2, RoadItem.Types.getRandomType()));
                }
        }
    }

    public boolean isAddPossible(ISafeZone safeZone){
        for(int i = 0; i < roadItemsQueues.length; i++){
            if (!roadItemsQueues[i].isEmpty() && roadItemsQueues[i].get(roadItemsQueues[i].size() - 1).getYPos() < safeZone.getSafeZone() + roadItemsQueues[i].get(roadItemsQueues[i].size() - 1).getHeight()) {
                return false;
            }
        }
        return true;
    }

    public void moveAllRoadItems(int distance){

        for (int i = 0; i < roadItemsQueues.length; i++) {
            for( RoadItem item :roadItemsQueues[i]){
                item.setYPos(item.getYPos() + distance);
            }
        }
        for (int i = 0; i < roadItemsQueues.length; i++) {
            if(!roadItemsQueues[i].isEmpty()) {
                if (roadItemsQueues[i].get(0).getYPos() > ConfigManager.getInstance().getRoadLength()){
                    roadItemsQueues[i].remove(0);
                }
            }
        }
    }

    public ArrayList<RoadItem> getQueue(int index){
        return roadItemsQueues[index];
    }

    public RoadItem getFirstRoadItemAt(int lane){
        if(roadItemsQueues[lane].isEmpty()){
            return null;
        }
        return roadItemsQueues[lane].get(0);
    }

    public void removeFirstRoadItemAt(int lane){
        if(!roadItemsQueues[lane].isEmpty()){
            roadItemsQueues[lane].remove(0);        }
    }
}
