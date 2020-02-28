package com.mygdx.shop;

public class Item {
	
	protected String name;
	protected String description;
	protected float increasingValue;
	protected int originalCost;
	protected int level;
	
	public Item(String name, String description, float increasingValue, int cost) {
		this.name = name;
		this.description = description;
		this.increasingValue = increasingValue;
		this.originalCost = cost;
		this.level = 1;
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
	
	public int getCost() {
		return originalCost * level;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void upgrade() {
		level += 1;
	}
}
