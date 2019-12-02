package com.mygdx.renderable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.house.House;

public class Node extends Renderable {
	
	private House house;
	private ArrayList<NPC> residents;

	private Map<Vector2, String> notes; 
	private Boolean isDiseased;
	
	
	public Node(Texture textureOfHouse, float x, float y, String[] attributes) {
		super.setSprite(textureOfHouse, x, y);
		isDiseased = false;
		residents = new ArrayList<>();
		notes = new HashMap<>();
		nodeLines = new ArrayList<>();
		printAllArray(attributes);
		this.house = new House(attributes);
		
	}
	
	
	public void printAllArray(String[] attributes) {
		for(String s : attributes) {
			if(s.contains(",") && !s.contains("(")) {
				String[] values = s.split(",");
				NPC n = new NPC(Float.valueOf(values[2]));
				n.updateSprite(Float.valueOf(values[0]), Float.valueOf(values[1]));
				residents.add(n);
			}
			else if (s.contains(",")) {
				String[] values = s.split(",");
				notes.put(new Vector2(Float.valueOf(values[1]), Float.valueOf(values[2])), values[0].substring(1));
			}
		}
	}
	
	public int generateNumOfNPCs(int highBound, int lowBound) {
		return lowBound;
	}
	
	
	public List<NPC> getNPCs() {
		return residents;
	}
	
	
	public void setNodes(ArrayList<NPC> villagers) {
		residents = villagers;
	}
	
	public int[][] getArray() {
		return house.getArray();
	}
	
	public void readFile() {
		
	}
	
	public Boolean isDiseased() {
		return isDiseased;
	}
	
	public void setDiseased(Boolean isDiseased) {
		this.isDiseased = isDiseased;
	}
	
	
	public int getNumberOfInfected()
	{
		int x = 0;
		for(NPC resident : residents)
		{
			if(resident.getStatus()=="Sick") x++;
		}
		return x;
	}

	public House getHouse() {
		return house;
	}
	
	public Map<Vector2, String> getNotes() {
		return notes;
	}
	
	public void serializeVillagers() {    
		FileHandle handle = Gdx.files.local("temp/villagers.temp");
		String s = "";
		for(NPC npc : residents) {
			s = s + npc.getHealth()+","+npc.getSprite().getX()+","+npc.getSprite().getY() + "\n";
		}
		handle.writeString(s, false);
	}
	
	public void resetVillagers() {
		FileHandle handle = Gdx.files.local("temp/villagers.temp");
        String lines[] = handle.readString().split("\\r?\\n");
        residents.clear();
        for(int i = 0; i < lines.length; i++) {
        	String values[] = lines[i].split(",");
			NPC n = new NPC(Float.valueOf(values[0]));
			n.updateSprite(Float.valueOf(values[1]), Float.valueOf(values[2]));
        	residents.add(n);
        }
	}


	public void infectRandom(float probabilty)
	{
		Random rand = new Random();
		if(rand.nextFloat()<probabilty)
		{
			residents.get(rand.nextInt(residents.size())).infect();
		}
		
	}
	

}