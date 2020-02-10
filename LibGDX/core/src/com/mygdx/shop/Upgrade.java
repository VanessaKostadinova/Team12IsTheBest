package com.mygdx.shop;


public class Upgrade {
	
	protected String name;
	protected String description;
	protected float increasingValue;
	protected float cost;
	protected int level;
	
	public Upgrade(String name, String description, float increasingValue, float cost) {
		this.name = name;
		this.description = description;
		this.increasingValue = increasingValue;
		this.cost = cost;
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
	
	public float getCost() {
		return cost;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void update() {
		cost = cost + 10;
		level += 1;
	}
	

}
