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
	private Map<String, Boolean> noteSeen;
	private Boolean isDiseased;
	
	
	private boolean level1Researched = false;
	private boolean level2Researched = false;
	private boolean level3Researched = false;
	
	
	public Node(Texture textureOfHouse, float x, float y, String[] attributes) {
		super.setSprite(textureOfHouse, x, y);
		isDiseased = false;
		residents = new ArrayList<>();
		notes = new HashMap<>();
		noteSeen = new HashMap<>();
		setAllVillagers(attributes);
		this.house = new House(attributes);
		updateHouseDiseased();
	}
	
	
	public void setAllVillagers(String[] attributes) {
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
				noteSeen.put(values[0].substring(1), false);
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
	
	
	public Boolean isDiseased() {
		return isDiseased;
	}
	
	public void updateHouseDiseased() {
		this.isDiseased = false;
		for(NPC n : residents) {
			if(n.getStatus().equals("Sick") || n.getStatus().equals("Dead")) {
				this.isDiseased = true;
			}
		}
	}
	
	public Boolean isAllInHouseDiseased() {
		for(NPC n : residents) {
			if(n.getStatus().equals("Alive")) {
				return false;
			}
		}
		return true;
	}
	
	
	public int getNumberOfInfected()
	{
		int x = 0;
		for(NPC resident : residents)
		{
			if(resident.getStatus().equals("Sick") || resident.getStatus().equals("Dead")) x++;
		}
		return x;
	}
	
	public int getNumberOfSick()
	{
		int x = 0;
		for(NPC resident : residents)
		{
			if(resident.getStatus().equals("Sick")) x++;
		}
		return x;
	}
	
	public Boolean everyoneBurnt()
	{
		for(NPC resident : residents)
		{
			if(!resident.getStatus().equals("Burnt")) return false;
		}
		return true;
	}
	
	public Boolean shouldGameEnd()
	{
		for(NPC resident : residents)
		{
			if(resident.getStatus().equals("Sick")) {
				return false;
			}
			else if(resident.getStatus().equals("Dead")) {
				return false;
			}
		}
		return true;
	}
	
	public int getNumberOfResidents() {
		return residents.size();
	}

	public House getHouse() {
		return house;
	}
	
	public ArrayList<NPC> getResidents() {
		return residents;
	}
	
	public Map<Vector2, String> getNotes() {
		return notes;
	}
	
	public Map<String, Boolean> getNoteValidation() {
		return noteSeen;
	}
	
	public void setNoteSeen(String Message) {
		noteSeen.put(Message, true);
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
		for(NPC resident : residents) {
			if(rand.nextFloat()<probabilty)
			{
				resident.infect();
			}
		}
		
	}
	
	public void upgradeLevelKnown() {
		if(!level1Researched) {
			level1Researched = true;
		}
		else if(!level2Researched && level1Researched) {
			level2Researched = true;
		}
		else if(!level3Researched && level2Researched && level1Researched) {
			level3Researched = true;
		}
	}
	
	public Boolean reachedMaxLevel() {
		if(level3Researched) {
			return true;
		}
		return false;
	}
	
	public Boolean getLevel1() {
		return level1Researched;
	}
	
	public Boolean getLevel2() {
		return level2Researched;
	}
	
	public Boolean getLevel3() {
		return level3Researched;
	}
	
	public Boolean setLevel1(Boolean value) {
		return level1Researched = value;
	}
	
	public Boolean setLevel2(Boolean value) {
		return level2Researched = value;
	}
	
	public Boolean setLevel3(Boolean value) {
		return level3Researched = value;
	}
	
	public boolean areAllDead() {
		for(NPC npc : residents) {
			if(!npc.getStatus().equals("Burnt")) {
				return false;
			}
		}
		return true;
	}
	
	public boolean areAllAlive() {
		for(NPC npc : residents) {
			if(!npc.getStatus().equals("Alive")) {
				return false;
			}
		}
		return true;
	}

}