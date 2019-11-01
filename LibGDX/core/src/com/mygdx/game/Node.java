package com.mygdx.game;

import java.util.ArrayList;
import java.util.Random;

import com.mygdx.character.NPC;

public class Node {
	
	private Level level;
	private ArrayList<Node> connectedNodes;
	private ArrayList<NPC> residents;
	private ArrayList<Float[][]> coordinates;
	
	private int lowBoundResidents;
	private int highBoundResidents;
	
	public Node() {
		level = new Level("testLevel.txt");
		spawnNPCs();
		
	}
	
	public void addNode(Node node) {
		connectedNodes.add(node);
	}
	
	private int generateNumOfNPCs() {
		Random random = new Random();
		return random.nextInt((highBoundResidents - lowBoundResidents) + 1) + lowBoundResidents;
	}
	
	private void spawnNPCs() {
		int numOfNPCs = generateNumOfNPCs();
		for(int i = 0; i < numOfNPCs; i++) {
			NPC currentNPC = new NPC(0f,0f);
			residents.add(currentNPC);
		}
	}
}
