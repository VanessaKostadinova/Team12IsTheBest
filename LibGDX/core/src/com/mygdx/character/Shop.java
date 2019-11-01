package com.mygdx.character;

import java.util.ArrayList;
/**
 * Write a description of class Shop here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Shop
{
    // instance variables - replace the example below with your own
    private int x;
    public Upgrade[] upgrades;
    /**
     * Constructor for objects of class Shop
     */
    public Shop()
    {
        // initialise instance variables
        x = 0;
        upgrades = new Upgrade[10];
        upgrades[0] = new Upgrade("upgradeName1", 0, 100);
        upgrades[1] = new Upgrade("upgradeName2", 0, 100);
        upgrades[2] = new Upgrade("upgradeName3", 0, 100);
        //etc
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void buyUpgrade(String name)
    {
        for (int i=0;i<upgrades.length;i++){
            if(upgrades[i].getName() == name){
                upgrades[i].increaseLevel();
            }
        }

    }
    public void sellUpgrade(String name)
    {
        for (int i=0;i<upgrades.length;i++){
            if(upgrades[i].getName() == name){
                upgrades[i].decreaseLevel();
            }
        }
    }
    public Upgrade getUpgrade(String name)
    {
        for (int i=0;i<upgrades.length;i++){
            if(upgrades[i].getName() == name){
                return upgrades[i];
            }
        }
        return null;
    }

}
