package com.mygdx.story;

public class Note {
    String info;
    Boolean hasBeenSeen;

    int x;
    int y;

    public Note(String info, int x, int y) {
        this.info = info;
        this.hasBeenSeen = false;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Boolean getHasBeenSeen() {
        return this.hasBeenSeen;
    }

    public String getInfo() {
        return this.info;
    }

    public void noteSeen() {
        this.hasBeenSeen = true;
    }
}
