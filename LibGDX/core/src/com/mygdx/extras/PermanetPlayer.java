package com.mygdx.extras;

import com.badlogic.gdx.Gdx;
import com.mygdx.shop.Item;

/**
 * This class stores the items a player holds.
 * @author Vanessa
 */
public class PermanetPlayer {
    public final int TOTAL_NUMBER_OF_NOTES = 10;

    private int numberOfMasks;
    private float healingFluid;
    private float burningFluid;
    private float sanity;
    private int food;
    private int energy;

    private Item[] items;
    private static PermanetPlayer instance;

    private PermanetPlayer(int masks, float healingFluid, float burningFluid){
        this.numberOfMasks = masks;
        this.healingFluid = healingFluid;
        this.burningFluid = burningFluid;
        this.items = createItems();
        this.sanity = 100f;
        this.energy = 100;
    }

    //TODO Fix values
    private Item[] createItems(){
        Item[] tempItems = new Item[5];
        tempItems[0] = new Item("BOOTS", "Faster footwear", 75f, 400);
        tempItems[1] = new Item("MASKS", "Better plague protection TM", 10f, 400);
        tempItems[2] = new Item("HEALING STRENGTH", "Better healing", 0.1f, 400);
        tempItems[3] = new Item("BURNING STRENGTH", "Burn baby burn but better", 0.5f, 400);
        return tempItems;
    }

    public static void createInventoryInstance(int masks, float healingFluid, float burningFluid) throws RuntimeException {
        if(instance == null){
            instance = new PermanetPlayer(masks, healingFluid, burningFluid);
        }
        else{
            throw new IllegalStateException("Inventory already exists");
        }
    }

    public static PermanetPlayer getPermanentPlayerInstance() throws RuntimeException {
        if(instance != null){
            return instance;
        }
        else{
            Gdx.app.log("Programming Error", "Inventory has not been created before getPermanentPlayerInstance() was called");
            throw new IllegalStateException("Inventory has not been created!");
        }
    }

    public Item getItem(int index){
        return items[index];
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

    public void changeSanity(float sanityDelta){
        sanity += sanityDelta;
    }

    public void changeEnergy(int energyDelta){
        energy += energyDelta;
    }

    public void resetEnergy(){
        energy = 100;
    }

    public void upgrade(int index){
        items[index].upgrade();
    }

    public float getBurningFluid() {
        return burningFluid;
    }

    public float getSanity() {
        return sanity;
    }

    public int getEnergy() {
        return energy;
    }

    public int getFood() {
        return food;
    }

    public float getHealingFluid() {
        return healingFluid;
    }

    public int getNumberOfMasks() {
        return numberOfMasks;
    }

    public Item[] getItems(){
        return items;
    }
}