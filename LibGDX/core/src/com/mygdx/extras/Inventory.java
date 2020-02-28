package com.mygdx.extras;

import com.mygdx.shop.Item;

import java.util.LinkedList;
import java.util.List;

/**
 * This class stores the items a player holds.
 * @author Vanessa
 */
public class Inventory {
    private int numberOfMasks;
    private float healingFluid;
    private float burningFluid;
    private int food;

    private Note[] notes;
    private List<Item> items;
    private static Inventory instance;

    private Inventory(int masks, float healingFluid, float burningFluid){
        this.numberOfMasks = masks;
        this.healingFluid = healingFluid;
        this.burningFluid = burningFluid;
        items = new LinkedList<Item>();
    }

    public static void createInventoryInstance(int masks, float healingFluid, float burningFluid) throws RuntimeException {
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

    public void changeFoodAmount(int foodDelta){
        food += foodDelta;
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