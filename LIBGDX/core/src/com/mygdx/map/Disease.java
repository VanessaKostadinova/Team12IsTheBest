package com.mygdx.map;

import java.util.List;
import java.util.Random;

import com.mygdx.renderable.Node;

public class Disease {
	
	private final float spreadRadius = 21.0f;
	private final float probabilty = 30.0f;
	
	public void update(List<Node> disease) {
		for(Node spreader : disease) {
			if(spreader.isDiseased()) {
				for(Node reciever : disease) {
					if(!reciever.isDiseased()) {
						if(!(spreader.equals(reciever))) {
							float distance = spreader.getCentreCoords().dst(reciever.getCentreCoords());
							if(distance < spreadRadius && diseaseImpacted()) {
								reciever.setDiseased(true);
							}
						}
					}
				}
			}
		}
	}
	
	
	public boolean diseaseImpacted() {
		float random = (float) (0 + Math.random() * (100));
		if(random < probabilty) {
			return true;
		}
		return false;
	}
	
	public float getSpreadRadius() {
		return spreadRadius;
	}
	
	
}
