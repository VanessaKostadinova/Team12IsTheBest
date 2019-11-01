package com.mygdx.character;

/**
 * Write a description of class Disease here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Disease extends NPC_OR_VILLAGER
{
    // instance variables - replace the example below with your own
    private int x;
    public String name;
    /**
     * Constructor for objects of class Disease
     */
    public Disease(String name)
    {
        // initialise instance variables
        x = 0;
        this.name = name;
        
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public int sampleMethod(int y)
    {
        // put your code here
        return x + y;
    }
}
