package com.mygdx.character;


/**
 * Write a description of class Upgrade here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Upgrade extends Shop
{
    // instance variables - replace the example below with your own
    private int x;
    public String upgradeName;
    public int level;
    public int costOfEachLv;
    
    /**
     * Constructor for objects of class Upgrade
     */
    public Upgrade(String upgradeName, int level, int costOfEachLv)
    {
        // initialise instance variables
        x = 0;
        this.upgradeName = upgradeName;
        this.level = level;
        this.costOfEachLv = costOfEachLv;
        
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
    public void increaseLevel()
    {
        level++;
    }
    public void decreaseLevel()
    {
        level--;
    }
    public int getLevel()
    {
        return level; 
    }
    public int getCost()
    {
        return costOfEachLv;
    }
    public String getName()
    {
        return upgradeName;
    }
    
    
}
