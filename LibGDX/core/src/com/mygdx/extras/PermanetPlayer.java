package com.mygdx.extras;

import com.badlogic.gdx.Gdx;
import com.mygdx.shop.Item;
import com.mygdx.story.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * This class stores the items a player holds.
 * @author Vanessa
 */
public class PermanetPlayer {
    public final int TOTAL_NUMBER_OF_NOTES = 10;

    /** The total number of masks held. */
    private int numberOfMasks;
    /** The total amount of healing fluid. */
    private float healingFluid;
    /** The total amount of burning fluid. */
    private float burningFluid;
    /** The total sanity held. */
    private float sanity;
    /** The total number of energy. */
    private int energy;
    /** A list of held notes. */
    private List<Note> notes;
    /** A list of items the player is holding. */
    private Item[] items;
    /** */
    private int[] chosenItems;
    /** Stores an instance of the Permanent Player class as per the singleton pattern. */
    private static PermanetPlayer instance;

    /**
     * Initialises the Permanent Player class.
     * @param masks Total masks
     * @param healingFluid Total healing fluid
     * @param burningFluid Total burning fluid
     */
    private PermanetPlayer(int masks, float healingFluid, float burningFluid){
        this.numberOfMasks = masks;
        this.healingFluid = healingFluid;
        this.burningFluid = burningFluid;
        this.notes = new ArrayList<>(100);
        this.items = createItems();
        this.sanity = 0f;
        this.energy = 100;
    }

    //TODO Fix values
    private Item[] createItems(){
        Item[] tempItems = new Item[4];
        tempItems[0] = new Item("BOOTS", "Faster footwear", 75f, 400);
        tempItems[1] = new Item("MASKS", "Better plague protection TM", 2000f, 400);
        tempItems[2] = new Item("HEALING STRENGTH", "Better healing", 0.1f, 400);
        tempItems[3] = new Item("BURNING STRENGTH", "Burn baby burn but better", 0.5f, 400);
        return tempItems;
    }

    /**
     * Creates a new instance of a permanent player
     * @param masks Total number of masks held
     * @param healingFluid Total burning fluid held
     * @param burningFluid Total healing fluid held
     * @throws RuntimeException If permanent player already exists
     */
    public static void createPermanentPlayerInstance(int masks, float healingFluid, float burningFluid) throws RuntimeException {
        if(instance == null){
            instance = new PermanetPlayer(masks, healingFluid, burningFluid);
        }
        else{
            throw new IllegalStateException("Inventory already exists");
        }
    }

    /**
     * Returns the instance of permanent player
     * @return PermanentPlayer
     * @throws RuntimeException If permanent player does not exist.
     */
    public static PermanetPlayer getPermanentPlayerInstance() throws RuntimeException {
        if(instance != null){
            return instance;
        }
        else{
            Gdx.app.log("Programming Error", "Inventory has not been created before getPermanentPlayerInstance() was called");
            throw new IllegalStateException("Inventory has not been created!");
        }
    }

    /**
     * Returns a specific item.
     * @param index The index of the item required.
     * @return The item at the given index.
     */
    public Item getItem(int index){
        return items[index];
    }

    /**
     * Reduces the number of masks held.
     * @param remove The number of masks you want to remove.
     */
    public void reduceNumberOfMasks(int remove) {
        numberOfMasks = numberOfMasks - remove;
    }

    public void reduceBurnSpray(int remove) {
        burningFluid = burningFluid - remove;
    }

    public void reduceCureSpray(int remove) {
        healingFluid = healingFluid - remove;
    }

    public void setChosenItems(int[] chosenItems) { this.chosenItems = chosenItems;};

    public int[] getChosenItems() { return this.chosenItems;}

    public void changeNumberOfMasks(int numberOfMasksDelta){
        numberOfMasks += numberOfMasksDelta;
    }

    public void changeHealingFluid(float healingFluidDelta){
        healingFluid += healingFluidDelta;
    }

    public void changeBurningFluid(float burningFluidDelta){
        burningFluid += burningFluidDelta;
    }

    public void changeSanity(float sanityDelta){
        sanity -= sanityDelta;
    }

    public void changeEnergy(int energyDelta){
        energy -= energyDelta;
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

    public void setSanity(float sanity) {
        this.sanity = sanity;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
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

    public void setItems(Item[] items){
        this.items = items;
    }

    public void addNote(Note note) {
        notes.add(note);
    }
    public List<Note> getNotes() {
        return notes;
    }
    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}