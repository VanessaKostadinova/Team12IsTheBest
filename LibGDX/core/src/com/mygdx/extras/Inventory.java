package com.mygdx.extras;

import com.badlogic.gdx.physics.bullet.collision._btMprSimplex_t;

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

    private boolean[] Note;

    private static Inventory instance;

    private Inventory(int masks, int maskLevel, int bootLevel, int healingLevel, int burningLevel, float healingFluid, float burningFluid){
        this.numberOfMasks = masks;
        this.healingFluid = healingFluid;
        this.burningFluid = burningFluid;
        this.maskLevel = maskLevel;
        this.bootLevel = bootLevel;
        this.healingLevel = healingLevel;
        this.burningLevel = burningLevel;
    }

    public static void createInventoryInstance(int masks, int maskLevel, int bootLevel, int healingLevel, int burningLevel, float healingFluid, float burningFluid) throws RuntimeException{
        if(instance == null){
            instance = new Inventory(masks, maskLevel, bootLevel, healingLevel, burningLevel, healingFluid, burningFluid);
        }
        else{
            throw new IllegalStateException("Inventory already exists");
        }
    }

    public static Inventory getInventoryInstance() throws RuntimeException {
        if(instance != null){
            return instance;
        }
        else{
            throw new IllegalStateException("Inventory already exists");
        }
    }

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