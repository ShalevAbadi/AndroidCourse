package com.example.crazydrive;

import java.util.ArrayList;
import java.util.Collections;

public class TopTenList {
    public static int MAX_RECORDS_COUNT = 10;
    private ArrayList<TopTenRecord> records = new ArrayList<>();

    public void sortRecords(){
        Collections.sort(records,new RecordsComparator());
    }

    public ArrayList<TopTenRecord> getRecords(){
        return records;
    }

    private int getMinIndex(){
        int minIndex = 0;
        for(int i = 0; i < records.size(); i++){
            if(records.get(i).getScore() < records.get(minIndex).getScore()){
                minIndex = i;
            }
        }
        return minIndex;
    }

    public boolean isTopTen(TopTenRecord record){
        return (records.size() < MAX_RECORDS_COUNT) || (record.getScore() > records.get(getMinIndex()).getScore());
    }

    public void addToTopTen(TopTenRecord record){
        if(records.size() == MAX_RECORDS_COUNT){
            records.remove(getMinIndex());
        }
        records.add(record);
    }

}
