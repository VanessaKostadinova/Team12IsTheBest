package com.mygdx.story;

/**
 * Contains the information about each of the notes.
 */
public class Note {

    /** The info of the note */
    String info;

    /** If the note has been seen */
    Boolean hasBeenSeen;

    /** The x and y value of the note */
    int x, y;

    /**
     * Note constructor used in the new game
     * @param info @see The info of the note
     * @param x The x value of the not
     * @param y The y value of the not
     */
    public Note(String info, int x, int y) {
        this.info = info;
        this.hasBeenSeen = false;
        this.x = x;
        this.y = y;
    }

    /**
     * Note constructor used in loading game
     * @param info @see The info of the note
     * @param x The x value of the not
     * @param y The y value of the not
     * @param hasBeenSeen if the note has been seen
     */
    public Note(String info, int x, int y, boolean hasBeenSeen) {
        this.info = info;
        this.hasBeenSeen = hasBeenSeen;
        this.x = x;
        this.y = y;
    }

    /**
     * Return the x value of the note.
     * @return The x value of the note.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Return the y value of the note.
     * @return The y value of the note.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Return if the note has been seen.
     * @return if note has been seen.
     */
    public Boolean getHasBeenSeen() {
        return this.hasBeenSeen;
    }

    /**
     * Return the string value of the note.
     * @return the description for the note.
     */
    public String getInfo() {
        return this.info;
    }

    /**
     * Set the note as having being seen.
     */
    public void noteSeen() {
        this.hasBeenSeen = true;
    }
}
