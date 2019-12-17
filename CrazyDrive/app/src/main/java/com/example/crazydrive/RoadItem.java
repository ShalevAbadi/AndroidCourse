package com.example.crazydrive;

import java.util.Random;

public class RoadItem {
    enum Types {
        POLICE_CAR(2),
        MONEY(1);

        private int ratio;


        Types(int ratio) {
            this.ratio = ratio;
        }

        public int getRatio() {
            return ratio;
        }

        public static Types getRandomType() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];
        }

    }
    private Types type;
    private int yPos;
    private int width;
    private int height;

    public RoadItem(int yPos, int width, Types type) {
        this.yPos = yPos;
        this.type = type;
        this.width = width;
        this.height = width*type.getRatio();
    }

    public void setType(Types type) {
        this.type = type;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Types getType() {
        return type;
    }

    public int getYPos() {
        return yPos;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
