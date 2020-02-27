package com.mygdx.extras;

import com.badlogic.gdx.physics.bullet.collision._btMprSimplex_t;

/**
 * This class stores the items a player holds.
 * @author Vanessa
 */
public class Inventory {
    private int numberOfMasks;
    private float healingFluid;
    private float burningFluid;

    private Note[] Notes;

    private static Inventory instance;

    private Inventory(int masks, float healingFluid, float burningFluid){
        this.numberOfMasks = masks;
        this.healingFluid = healingFluid;
        this.burningFluid = burningFluid;
    }

    public static void createInventoryInstance(int masks, float healingFluid, float burningFluid) throws RuntimeException{
        if(instance == null){
            instance = new Inventory(masks, healingFluid, burningFluid);
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

    public float getBurningFluid() {
        return burningFluid;
    }

    public float getHealingFluid() {
        return healingFluid;
    }

    public int getNumberOfMasks() {
        return numberOfMasks;
    }
}