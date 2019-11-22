package com.mygdx.shop;

public class Upgrade {
	
	protected String description;
	protected float increasingValue;
	protected float cost;

	
	public Upgrade(String description, float increasingValue, float cost) {
		this.description = description;
		this.increasingValue = increasingValue;
		this.cost = cost;
	}

}
