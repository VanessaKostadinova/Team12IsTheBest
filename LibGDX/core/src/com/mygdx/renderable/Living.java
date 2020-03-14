package com.mygdx.renderable;

/**
 * An interface to be implemented by any classes which are living.
 * @author Vanessa
 */
public interface Living {

	/**
	 * Change the health of the living entity.
	 * @param deltaHealth
	 */
	public abstract void changeHealth(float deltaHealth);

	/**
	 * Get the health of the entity.
	 * @return health of entity.
	 */
	public abstract float getHealth();

	/**
	 * Get if the entity is dead
	 * @return is entity dead
	 */
	public abstract boolean isDead();

}
