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
import com.mygdx.story.Note;

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
	/** The List of notes inside this house. */
	List<Note> notes;
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
	/** The URL for the image of the current node */
	private String imageURL;
	/** If the level4 variable has been researched - used as verification right now */
	private boolean level4Researched = false;

	/**
	 * Constructor used during new game where everything is newly defined
	 * @param textureOfHouse String URL, for the image of the house
	 * @param x The x value of the house.
	 * @param y The y value of the house.
	 * @param attributes The attributes of each house, floor, torches etc.
	 */
	public Node(String textureOfHouse, float x, float y, String[] attributes) {
		illnessLevel = 0f;
		super.setSprite(new Texture(Gdx.files.internal(textureOfHouse)), x, y);
		this.imageURL = textureOfHouse;
		residents = new ArrayList<>();
		//noteSeen = new HashMap<>();
		notes = new ArrayList<>(10);
		neighbours = new ArrayList<>();
		setAllVillagers(attributes);
		this.house = new House(attributes);
	}

	/**
	 *
	 * @param house
	 * @param residents
	 * @param notes
	 * @param imageURL
	 * @param x
	 * @param y
	 */
	public Node(House house, ArrayList<NPC> residents, List<Note> notes, String imageURL, float x, float y) {
		super.setSprite(new Texture(Gdx.files.internal(imageURL)), x, y);
		this.house = house;
		this.imageURL = imageURL;
		this.residents = residents;
		neighbours = new ArrayList<>();
		System.out.println(residents.size());
		this.notes = notes;
	}

	/**
	 * Initially set all the villagers to their attributes in the text file.
	 * @param attributes
	 */
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
			}
		}
	}

	public List<NPC> getNPCs() {
		return residents;
	}

	/**
	 * The array of integers within a house.
	 * @return House integer array
	 */
	public int[][] getArray() {
		return house.getArray();
	}

	/**
	 * If the node is diseased
	 * @return if the node is diseased.
	 */
	public Boolean isDiseased() {
		return (illnessLevel > 0);
	}

	/**
	 * Add nodes to the neighbours
	 * @param neighbour
	 */
	public void addNeighbour(Node neighbour){
		neighbours.add(neighbour);
	}

	/**
	 * Checks if all the villagers within the node are diseased
	 * @return are diseased.
	 */
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
	 * @return Dead NPC's as integer
	 */
	public int getNumberOfDead()
	{
		int x = 0;
		for(NPC resident : residents)
		{
			if(resident.getStatus().equals("Dead")) x++;
		}
		return x;
	}

	/**
	 * Returns the number of burnt NPC's
	 * @return burnt NPC's as integer
	 */
	public int getNumberOfBurnt()
	{
		int x = 0;
		for(NPC resident : residents)
		{
			if(resident.getStatus().equals("Burnt")) x++;
		}
		return x;
	}

	/**
	 * Returns the number of ill NPC's
	 * @return Number of Ill NPC's as Integer
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

	/**
	 * Returns the number of ill NPC's
	 * @return Number of Alive NPC's as Integer
	 */
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

	/**
	 * Check if all the villagers have been burnt.
	 * @return if everyone is burnt.
	 */
	public Boolean everyoneBurnt()
	{
		for(NPC resident : residents)
		{
			if(!resident.getStatus().equals("Burnt")) return false;
		}
		return true;
	}

	/**
	 * Used to check whether the game should end, all might be BURNT or ALIVE
	 * @return
	 */
	public Boolean shouldGameEnd()
	{
		for(NPC resident : residents)
		{
			if(resident.getStatus().equals("Sick")) {
				return false;
			}
		}
		return true;
	}

	public House getHouse() {
		return house;
	}

	/**
	 * Get all the NPCs which are in the house right now
	 * @return NPCs in node in arrayList
	 */
	public ArrayList<NPC> getResidents() {
		return residents;
	}
	
	public List<Note> getNotes() {
		return notes;
	}

	/**
	 * Add a note to the node
	 * @param note
	 */
	public void addNotes(Note note) {
		notes.add(note);
	}

	/**
	 * Temporarily store the vital health variables of the villagers so they can be reloaded at a separate time later
	 * on if required.
	 */
	public void serializeVillagers() {    
		FileHandle handle = Gdx.files.local("temp/villagers.temp");
		StringBuilder s = new StringBuilder();
		for(NPC npc : residents) {
			s.append(npc.getHealth()).append(",").append(npc.getSprite().getX()).append(",").append(npc.getSprite().getY()).append("\n");
		}
		handle.writeString(s.toString(), false);
	}

	/**
	 * Reset all the villagers, to their values specified in the text file.
	 */
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

	/**
	 * Upgrade the level of information known about the node.
	 */
	public void upgradeLevelKnown() {
		System.out.println(level4Researched);
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

	/**
	 * If the player has learnt everything about the house
	 * @return If the player has learnt everything about this node.
	 */
	public Boolean reachedMaxLevel() {
		return level4Researched;
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
		return level4Researched;
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

	public String getImageURL() {
		return imageURL;
	}

	/**
	 * Check if all the villagers are dead
	 * @return If all the villagers in this node are dead
	 */
	public boolean areAllDead() {
		for(NPC npc : residents) {
			if(!npc.getStatus().equals("Burnt")) {
				return false;
			}
		}
		return true;
	}

	/**
	 * A check for if all the villagers in this specific node are dead
	 * @return If all villagers are dead.
	 */
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

	public Note getNote(float x, float y) {
		for(Note n : notes) {
			if(n.getX() == x && n.getY() == y) {
				return n;
			}
		}
		return null;
	}
}