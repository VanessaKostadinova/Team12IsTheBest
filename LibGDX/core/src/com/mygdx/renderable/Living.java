package com.mygdx.renderable;

public interface Living {
		
	public abstract void changeHealth(float deltaHealth);
	public abstract float getHealth();
	public abstract boolean isDead();

}
