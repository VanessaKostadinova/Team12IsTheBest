package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Connections {
	
	private Map<Node, List<Node>> adjMatrix;
	
	public Connections() {
		//adjMatrix = new Map<Node, List<Node>>;
	}
	
	public Map<Node, List<Node>> getMap(){
		return adjMatrix;
	}
	
	public void addNode(Node node) {
		adjMatrix.putIfAbsent(node, new ArrayList<Node>());
	}
}
