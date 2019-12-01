package com.example.crazydrive;

public abstract class RoadItem implements RoadItemsTypes {

    private Type type;
    private int yPos;
    private int width;
    private int height;

    public RoadItem(int yPos, int width, int height) {
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }

    public void setType(Type type) {
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

    public Type getType() {
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
