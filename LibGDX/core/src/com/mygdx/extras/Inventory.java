package com.mygdx.extras;

/**
 * This class stores the items a player holds.
 * @author Vanessa
 */
public class Inventory {
    private final int MAX_MASK_LEVEL = 3;
    private final int MAX_BOOT_LEVEL = 3;
    private final int MAX_HEALING_LEVEL = 3;
    private final int MAX_BURNING_LEVEL = 3;

    private int numberOfMasks;
    private float healingFluid;
    private float burningFluid;

    private int maskLevel;
    private int bootLevel;
    private int healingLevel;
    private int burningLevel;

    public void changeNumberOfMasks(int numberOfMasksDelta){
        numberOfMasks += numberOfMasksDelta;
    }

    public void changeHealingFluid(float healingFluidDelta){
        healingFluid += healingFluidDelta;
    }

    public void changeBurningFluid(float burningFluidDelta){
        burningFluid += burningFluidDelta;
    }

    public void increaseMaskLevel(){
        if(maskLevel < MAX_MASK_LEVEL){
            maskLevel++;
        }
    }

    public void increaseBootLevel(){
        if(bootLevel < MAX_BOOT_LEVEL){
            bootLevel++;
        }
    }

    public void increaseHealingLevel(){
        if(healingLevel < MAX_HEALING_LEVEL){
            healingLevel++;
        }
    }

    public void increaseBurningLevel(){
        if(burningLevel < MAX_BURNING_LEVEL){
            burningLevel++;
        }
    }

    public int getBootLevel() {
        return bootLevel;
    }

    public float getBurningFluid() {
        return burningFluid;
    }

    public float getHealingFluid() {
        return healingFluid;
    }

    public int getHealingLevel() {
        return healingLevel;
    }

    public int getMaskLevel() {
        return maskLevel;
    }

    public int getNumberOfMasks() {
        return numberOfMasks;
    }
}
