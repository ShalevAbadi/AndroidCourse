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
        addRoadItems();
    }

    public void addRoadItems(){
        ConfigManager conf = ConfigManager.getInstance();
        Random r = new Random();
        int i = r.nextInt((conf.getLanesCount()));
            if (roadItemsQueues[i].isEmpty() || roadItemsQueues[i].get(roadItemsQueues[i].size() - 1).getYPos() > roadItemsQueues[i].get(roadItemsQueues[i].size() - 1).getHeight()) {
                roadItemsQueues[i].add(new PoliceCar(0, conf.getLaneWidth() / 2, conf.getLaneWidth()));
            }

    }

    public void moveAllRoadItems(int distance){

        for (int i = 0; i < roadItemsQueues.length; i++) {
            for( RoadItem item :roadItemsQueues[i]){
                item.setYPos(item.getYPos() + distance);
            }
        }
        for (int i = 0; i < roadItemsQueues.length; i++) {
            if(!roadItemsQueues[i].isEmpty()) {
                if (roadItemsQueues[i].get(0).getYPos() > ConfigManager.getInstance().getRoadLength() - 70 - 200) {
                    roadItemsQueues[i].remove(0);
                }
            }
        }
    }

    public ArrayList<RoadItem> getQueue(int index){
        return roadItemsQueues[index];
    }

    public RoadItem getFirstRoadItemAt(int lane){
        return roadItemsQueues[lane].get(0);
    }
}
