package com.example.crazydrive;

import java.util.Comparator;

public class RecordsComparator implements Comparator<TopTenRecord> {

    public int compare(TopTenRecord a, TopTenRecord b){
        if(a.getScore() < b.getScore()){
            return 1;
        } else if(a.getScore() == b.getScore()){
            return 0;
        }
        return -1;
    }
}
