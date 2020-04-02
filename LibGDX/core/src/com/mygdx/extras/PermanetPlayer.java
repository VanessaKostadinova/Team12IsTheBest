package com.mygdx.extras;

import com.badlogic.gdx.Gdx;
import com.mygdx.shop.Item;
import com.mygdx.story.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * This class stores the items a player holds.
 * @author Vanessa, Inder
 */
public class PermanetPlayer {
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
    /** The number of items the player chose for each perishable.*/
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

    /**
     * Create a bunch of items for the player.
     * @return
     */
    private Item[] createItems(){
        Item[] tempItems = new Item[4];
        tempItems[0] = new Item("BOOTS", "Faster footwear", 75f, 400);
        tempItems[1] = new Item("MASKS", "Better plague protection TM", 30f, 400);
        tempItems[2] = new Item("HEALING STRENGTH", "Better healing", 0.1f, 400);
        tempItems[3] = new Item("BURNING STRENGTH", "Burn baby burn but better", 10f, 400);
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
     * Reset set class to null after the game ends.
     */
    public static void reset() {
        instance = null;
    }

    /**
     * Reduces the number of masks held.
     * @param remove The number of masks you want to remove.
     */
    public void reduceNumberOfMasks(int remove) {
        numberOfMasks = numberOfMasks + remove;
    }

    /**
     * Reduces the burn spray held.
     * @param remove The burn spray you want to remove.
     */
    public void reduceBurnSpray(int remove) {
        burningFluid = burningFluid - remove;
    }

    /**
     * Reduces the cure spray held.
     * @param remove The cure spray you want to remove.
     */
    public void reduceCureSpray(int remove) {
        healingFluid = healingFluid - remove;
    }

    /**
     * Set the amount of chosen items.
     * @param chosenItems array of how much of each item you have chosen.
     */
    public void setChosenItems(int[] chosenItems) { this.chosenItems = chosenItems;};

    /**
     * Get the amount of all chosen items.
     * @return array of integers for inventory
     */
    public int[] getChosenItems() { return this.chosenItems;}


    /**
     * Change the sanity of the player.
     * @param sanityDelta The sanity of the player.
     */
    public void changeSanity(float sanityDelta){
        sanity += sanityDelta;
    }

    /**
     * Change the energy of the player.
     * @param energyDelta The energy of the player.
     */
    public void changeEnergy(int energyDelta){
        energy += energyDelta;
    }

    /**
     * Reset the energy of the player to 100
     */
    public void resetEnergy(){
        energy = 100;
    }


    /**
     * Get the total amount of burning fluid the player has.
     */
    public float getBurningFluid() {
        return burningFluid;
    }

    /**
     * Get the total sanity the player has.
     */
    public float getSanity() {
        return sanity;
    }

    /**
     * Update the sanity to a new value.
     * @param sanity the new sanity value
     */
    public void setSanity(float sanity) {
        this.sanity = sanity;
    }

    /**
     * Get the total amount of energy the player has.
     */
    public int getEnergy() {
        return energy;
    }

    /**
     * Update the players energy to a new value.
     * @param energy the new energy value
     */
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    /**
     * Get the total healing fluid.
     * @return the amount of healing fluid
     */
    public float getHealingFluid() {
        return healingFluid;
    }

    /**
     * Get the total number of masks.
     * @return the amount of mask the player has
     */
    public int getNumberOfMasks() {
        return numberOfMasks;
    }

    /**
     * Get the items.
     * @return the states of each of the items for the player.
     */
    public Item[] getItems(){
        return items;
    }

    /**
     * Update the players items to a new value.
     * @param items the new altered items
     */
    public void setItems(Item[] items){
        this.items = items;
    }

    /**
     * Add notes to the player.
     * @param note the new note
     */
    public void addNote(Note note) {
        notes.add(note);
    }

    /**
     * Get all the notes the player has.
     * @return the notes on the player
     */
    public List<Note> getNotes() {
        return notes;
    }

    /**
     * Update the players notes to a new value.
     * @param notes set a list of notes which the player has
     */
    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}