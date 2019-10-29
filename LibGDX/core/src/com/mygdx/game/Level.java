package com.mygdx.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Level {
	
	private int[][] level;
	private int height;
	private int width;
	private String file;
	private String levelFile;
	private HashMap<Integer, Texture> textures;
	
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
	}
	
	public int getHeight() {
		return height;
	}
	
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
	
	public int[][] getLevel() {
		return level;
	}
	
	public Texture getTexture(int key) {
		return textures.get(key);
	}
	
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