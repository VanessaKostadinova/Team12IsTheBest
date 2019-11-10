package com.mygdx.character;


/**
 * Write a description of class NPC_OR_VILLAGER here.
 *
 * @author Ali
 * @version (a version number or a date)
 */
public class NPC_OR_VILLAGER
{
    // instance variables - replace the example below with your own
    public boolean isDiseased; // Keep track of whether or not the villager/NPC is infected.
    public int diseaseTime; // Keeps track of how long the villager/VPC has been infected for. If someone is infected for too long, do they die?
    public String villagerName; //Possibly give the villager a randomly selected name?
    public int lastCured; //Keep track of how long it has been since the last time the villager was cured from disease? This is so that we can add/deal disease re-emergence.
    public int gold; //Maybe the villager, upon being cured, gives the player a certain amount of gold and this will store that amount of gold. 
    
    public Disease disease;
    public int daysInfected;
    public float immunity;
    public boolean isInfected;
    /**
     * Constructor for objects of class NPC_OR_VILLAGER
     */
    public NPC_OR_VILLAGER()
    {
        // initialise instance variables
        daysInfected = 0;
        immunity = 0;
        isInfected = false;
    }

    /**
     * Increases the days an NPC has been infected for. Used for updating.
     */
    public void addInfectedDay()
    {
        daysInfected++;
    }
    /**
     * Getter for the days an NPC has been infected.
     * @return Days NPC has been infected
     */
    public int getDaysInfected()
    {
        return daysInfected;
    }
    /**
     * Tells you whether an NPC has been infected or not.
     * @return Boolean, whether NPC is infected.
     */
    public boolean isInfected()
    {
        return isInfected;
    }
    /**
     * Infects the NPC
     * @param disease
     */
    public void addDisease(Disease disease){
        this.disease = disease;
    }
    
    
}
