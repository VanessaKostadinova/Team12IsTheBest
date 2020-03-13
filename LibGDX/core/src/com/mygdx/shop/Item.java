package com.mygdx.shop;

/**
 * This class contains more information about the items which the player already has.
 * @author Inder, Vanessa
 * @version 2.0
 */
public class Item {

	/** The name of the item.*/
	protected String name;
	/** The name of the description of the item. */
	protected String description;
	/** The name increasing value of the item.*/
	protected float increasingValue;
	/** The Original cost of the item. */
	protected int originalCost;
	/** The level of the item. */
	protected int level;

	/**
	 * Used to construct a item of initial level 1.
	 * @param name The name of the item.
	 * @param description The description of the item.
	 * @param increasingValue The increasing value of the item.
	 * @param cost The cost of the item initially.
	 */
	public Item(String name, String description, float increasingValue, int cost) {
		this.name = name;
		this.description = description;
		this.increasingValue = increasingValue;
		this.originalCost = cost;
		this.level = 1;
	}

	/**
	 * Used to construct a item of the level which is inputted by the parameter.
	 * @param name The name of the item.
	 * @param description The description of the item.
	 * @param increasingValue The increasing value of the item.
	 * @param cost The cost of the item initially.
	 * @param level The level of the item.
	 */
	public Item(String name, String description, float increasingValue, int cost, int level) {
		this.name = name;
		this.description = description;
		this.increasingValue = increasingValue;
		this.originalCost = cost;
		this.level = level;
	}

	public String getDescription() {
		return description;
	}
	
	public String getName() {
		return name;
	}
	
	public float getIncreasingValue() {
		return increasingValue;
	}

	public int getOriginalCost() {
		return originalCost;
	}
	
	public int getCost() {
		return originalCost * level;
	}
	
	public int getLevel() {
		return level;
	}

	/**
	 * Upgrade the item to the next level.
	 */
	public void upgrade() {
		level += 1;
	}
}
