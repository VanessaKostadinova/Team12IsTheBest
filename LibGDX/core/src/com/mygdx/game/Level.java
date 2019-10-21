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
	private String file;
	private HashMap<Integer, Texture> textures;
	
	public Level(int width, int height,String file) {
		level = new int[height][width];
		this.height = height;
		this.file = file;
		textures = new HashMap<Integer, Texture>();
		fillHashMap();
		createLevel();
	}
	
	public int getHeight() {
		return height;
	}
	
	/**
	 * To Inder:
	 * I am storing the textures in a hashmap, this is a hardcoded version
	 * but it allows us to change the textures each bit represents more easily.
	 * 
	 * We could store the information about each level in the nodes used for 
	 * the graph and then just edit this hashMap method accordingly.
	 */
	private void fillHashMap() {
		Texture wall = new Texture(Gdx.files.internal("Wooden_Floor.gif"));
		Texture floor = new Texture(Gdx.files.internal("Floor_Stone.gif"));
		
		textures.put(0, wall);
		textures.put(1, floor);
	}
	
	public int[][] getLevel() {
		return level;
	}
	
	public Texture getTexture(int key) {
		return textures.get(key);
	}
	
	private void createLevel() {
		try {
			BufferedReader reader;
			String line;
			final String splitter = ",";
			//load reader
			reader = new BufferedReader(new FileReader(file));
			//stores the array's height (height-1 to make 0,0 bottom left not top right)
			int arrayHeight = height-1;
			//check if there is next line
			while((line = reader.readLine()) != null) {
				//creates an array with every element in the current line
				String[] currentLine = line.split(splitter);
				System.out.println(line);
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