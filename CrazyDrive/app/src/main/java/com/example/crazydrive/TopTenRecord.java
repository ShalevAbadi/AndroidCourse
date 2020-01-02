package com.example.crazydrive;

import androidx.annotation.NonNull;

public class TopTenRecord{
    private String name = "unknown";
    private long score;
    private double lat = 0;
    private double lng = 0;
            ;
    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }


    public TopTenRecord(long score){
        this.score = score;
    }

    public void setName(String name) {
        if(!name.isEmpty()) {
            this.name = name;
        }
    }

    public void setScore(long score) {
        this.score = score;
    }


    public String getName() {
        return name;
    }

    public long getScore() {
        return score;
    }

    @NonNull
    @Override
    public String toString() {
        return (getName() + " got " + getScore() + "$");
    }
}
