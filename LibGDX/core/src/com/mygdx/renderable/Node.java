package com.mygdx.renderable;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.house.House;

public class Node extends Renderable {
	
	private House house;
	private NPC[] residents;
	private String file;
	private Boolean isDiseased;
	
	public Node(Texture textureOfHouse, float x, float y) {
		super.setSprite(textureOfHouse, x, y);
		isDiseased = false;
	}
	
	public int generateNumOfNPCs(int highBound, int lowBound) {
		return lowBound;
	}
	
	public void createNPCs(int number) {
		
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
	
	public void checkIfDiseased() {
		for(NPC resident : residents) {
			resident.getStatus();
		}
	}
}
