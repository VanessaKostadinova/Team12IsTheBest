package com.mygdx.renderable;

/**
 * A set of constants used for BOX2D Lights
 * @author Inder
 * @version 1.0
 */
public class Constants {
	/**
	 * Setting the player in base(2) as 0100
	 */
	public static final short PLAYER;
	/**
	 * Setting the wall in base(2) as 0010
	 */
	public static final short WALL;

	static {
		PLAYER = 2;
		WALL = 4;
	}

}
