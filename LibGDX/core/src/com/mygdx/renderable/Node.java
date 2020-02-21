package com.mygdx.renderable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.house.House;

import static java.lang.Float.*;

/**
 * Class node represents the back end information of each house.
 * @Authors Inder, Vanessa
 * @Version 20/02/2020
 */

public class Node extends Renderable {

	/** The house it represents */
	private House house;
	/** List of NPC's residing in this node */
	private ArrayList<NPC> residents;

	/** Map of all notes in the house */
	private Map<Vector2, String> notes = new HashMap<>();
	/** Map of all notes found in the house */
	private Map<String, Boolean> noteSeen;

	/** The illness level of this node */
	private float illnessLevel;

	/** All neighbouring nodes */
	private ArrayList<Node> neighbours;

	/** Whether the player has researched the number of living residents */
	private boolean numberAliveResearched = false;
	/** Whether the player has researched the number of ill residents */
	private boolean numberIllResearched = false;
	/** Whether the player has researched the number of dead residents */
	private boolean numberDeadResearched = false;
	private boolean level4Researched = false;
	
	
	public Node(Texture textureOfHouse, float x, float y, String[] attributes) {
		illnessLevel = 0f;
		super.setSprite(textureOfHouse, x, y);
		residents = new ArrayList<>();
		noteSeen = new HashMap<>();
		neighbours = new ArrayList<>();
		setAllVillagers(attributes);
		this.house = new House(attributes);
		//updateHouseDiseased();
	}

	public void setAllVillagers(String[] attributes) {
		for(String s : attributes) {
			if(s.contains(",") && !s.contains("(")) {
				String[] values = s.split(",");
				NPC n = new NPC(parseFloat(values[2]));
				n.updateSprite(parseFloat(values[0]),
						parseFloat(values[1]));
				residents.add(n);
			}
			else if (s.contains(",")) {
				String[] values = s.split(",");
				notes.put(new Vector2(parseFloat(values[1]), valueOf(values[2])), values[0].substring(1));
				noteSeen.put(values[0].substring(1), false);
			}
		}
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
		return (illnessLevel > 0);
	}

	public void addNeighbour(Node neighbour){
		neighbours.add(neighbour);
	}
	
	public Boolean isAllInHouseDiseased() {
		for(NPC n : residents) {
			if(n.getStatus().equals("Alive")) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns the number of dead NPC's
	 * @return Dead NPC's
	 */
	public int getNumberOfDead()
	{
		int x = 0;
		for(NPC resident : residents)
		{
			if(resident.getStatus().equals("Dead")) x++;
			if(resident.getStatus().equals("Burnt")) x++;
		}
		return x;
	}

	/**
	 * Returns the number of ill NPC's
	 * @return Ill NPC's
	 */
	public int getNumberOfSick()
	{
		int x = 0;
		for(NPC resident : residents)
		{
			if(resident.getStatus().equals("Sick")) x++;
		}
		return x;
	}
	
	public int getNumberOfAlive()
	{
		int x = 0;
		for(NPC resident : residents)
		{
			if(resident.getStatus().equals("Alive")) x++;
		}
		return x;
	}

	/**
	 * Returns a list of all non-infected living NPC's
	 * @return Alive
	 */
	public List<NPC> getAllAlive(){
		List<NPC> alive = new ArrayList<>();
		for(NPC resident : residents){
			if(resident.getStatus().equals("Alive")){
				alive.add(resident);
			}
		}
		return alive;
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
		StringBuilder s = new StringBuilder();
		for(NPC npc : residents) {
			s.append(npc.getHealth()).append(",").append(npc.getSprite().getX()).append(",").append(npc.getSprite().getY()).append("\n");
		}
		handle.writeString(s.toString(), false);
	}
	
	public void resetVillagers() {
		FileHandle handle = Gdx.files.local("temp/villagers.temp");
        String[] lines = handle.readString().split("\\r?\\n");
        residents.clear();
        for(int i = 0; i < lines.length; i++) {
			String[] values = lines[i].split(",");
			NPC n = new NPC(valueOf(values[0]));
			n.updateSprite(valueOf(values[1]), valueOf(values[2]));
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
		if(!level4Researched && numberDeadResearched && numberIllResearched && numberAliveResearched) {
			level4Researched = true;
		}
		else if(!numberDeadResearched && numberIllResearched && numberAliveResearched) {
			numberDeadResearched = true;
		}
		else if(!numberIllResearched && numberAliveResearched) {
			numberIllResearched = true;
		}
		else if(!numberAliveResearched) {
			numberAliveResearched = true;
		}
	}
	
	public Boolean reachedMaxLevel() {
		return numberDeadResearched;
	}
	
	public Boolean getLevel1() {
		return numberAliveResearched;
	}
	
	public Boolean getLevel2() {
		return numberIllResearched;
	}
	
	public Boolean getLevel3() {
		return numberDeadResearched;
	}
	
	public Boolean getLevel4() {
		return numberDeadResearched;
	}
	
	public Boolean setLevel1(Boolean value) {
		return numberAliveResearched = value;
	}
	
	public Boolean setLevel2(Boolean value) {
		return numberIllResearched = value;
	}
	
	public Boolean setLevel3(Boolean value) {
		return numberDeadResearched = value;
	}
	
	public Boolean setLevel4(Boolean value) {
		return level4Researched = value;
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

	/**
	 * Returns the total illness level for this Node
	 * @return illnessLevel
	 */
	public float getIllnessLevel(){
		return illnessLevel;
	}

	/**
	 * Returns a list of all connected nodes
	 * @return neighbours
	 */
	public List<Node> getNeighbours(){
		return neighbours;
	}

	/**
	 * Sets the level of infection, used for infecting NPC's
	 * @param illnessLevel
	 */
	public void setIllnessLevel(float illnessLevel){
		this.illnessLevel = illnessLevel;
	}
}