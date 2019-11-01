package com.mygdx.game;

import java.util.ArrayList;

import com.mygdx.character.NPC;

public class Node {
	
	private Level level;
	private ArrayList<Node> connectedNodes;
	private ArrayList<NPC> residents;
	
	public Node() {
		level = new Level("testLevel.txt");
	}
	
	public void addNode(Node node) {
		connectedNodes.add(node);
	}
}
