package com.mygdx.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.character.NPC;
/**
 * The class level deals with the level textures and coordinates.
 *
 * @author Vanessa
 * @version 09/11/19
 */
public class Level {

	/**
	 * A test NPC for debugging.
	 */
	private NPC testNPC;
	/**
	 * The level array used for drawing the level.
	 */
	private int[][] level;
	/**
	 * An array of every NPC in the level
	 */
	private NPC[] NPCs;
	/**
	 * The height of the array
	 */
	private int height;
	/**
	 * The width of the array
	 */
	private int width;
	/**
	 * The name of the file used for the basic level information
	 */
	private String file;
	/**
	 * The name of the CSV file used for the level array.
	 */
	private String levelFile;
	/**
	 * A hashmap of all the textures.
	 */
	private HashMap<Integer, Texture> textures;

	/**
	 * Constructor for the level, it needs the file name
	 * @param file The name of the file
	 */
	public Level(String file) {
		//sets the file
		this.file = file;
		//initiate the hashmap
		textures = new HashMap<Integer, Texture>();
		//read the level
		readLevelInformation();
		//initialise the array
		level = new int[height][width];
		//create the array
		createLevelArray();
		//create test NPC
		testNPC = new NPC("Sick", 6 * 32, 3 * 32, 60);
		//test array
		NPCs = new NPC[1];
		NPCs[0] = testNPC;
	}

	/**
	 * Getter for the height of the level array.
	 * @return The height of the level array.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Reads the level file given and sets up the height and width of the level array.
	 * Also sets the textures for the level.
	 */
	private void readLevelInformation() {
		try {
			//create reader
			BufferedReader reader;
			//create line
			String line;
			//initialise reader
			reader = new BufferedReader(new FileReader(file));
			//initialise counter
			int i = 0;
			//sets level file
			levelFile = reader.readLine();
			//sets height
			height =  Integer.valueOf(reader.readLine());
			//sets width
			width = Integer.valueOf(reader.readLine());
			//go through the file setting each texture
			while((line = reader.readLine()) != null) {
				textures.put(i, new Texture(Gdx.files.internal(line)));
				i++;
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Getter for the level array.
	 * @return The level array.
	 */
	public int[][] getLevel() {
		return level;
	}

	/**
	 * 
	 * @param index The NPC you need
	 * @return The NPC object at a given index.
	 */
	public NPC getNPC(int index) {
		return NPCs[index];
	}

	/**
	 * Getter for all NPCs in the level
	 * @return All the NPCs in the level.
	 */
	public NPC[] getAllNPCS() {
		return NPCs;
	}

	/**
	 * Getter for the number of NPCs in level.
	 * @return Number of NPCs in level.
	 */
	public int getNumNPCs() {
		return NPCs.length;
	}

	/**
	 * Getter for required texture.
	 * @return All the NPCs in the level.
	 */
	public Texture getTexture(int key) {
		return textures.get(key);
	}

	/**
	 * Creates the level array
	 */
	private void createLevelArray() {
		try {
			BufferedReader reader;
			String line;
			final String splitter = ",";
			//load reader
			reader = new BufferedReader(new FileReader(levelFile));
			//stores the array's height
			int arrayHeight = height-1;
			//check if there is next line
			while((line = reader.readLine()) != null) {
				//creates an array with every element in the current line
				String[] currentLine = line.split(splitter);
				//stores the current position in the array
				int arrayPosition = 0;
				//for every element in the current line
				for(String i:currentLine) {
					//creates array
					level[arrayHeight][arrayPosition] = Integer.valueOf(i);
					//increment array position
					arrayPosition++;
				}
				//increment array height location
				arrayHeight--;
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
