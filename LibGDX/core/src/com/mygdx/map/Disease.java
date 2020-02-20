package com.mygdx.map;

import java.util.List;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.house.House;
import com.mygdx.renderable.NPC;
import com.mygdx.renderable.Node;

public class Disease {
	
	private final float spreadRadius = 250.0f;
	private final float probabilty = 50.0f;

	public void draw(List<Node> disease, Node spreader, ShapeRenderer shapeRenderer) {
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		for(Node reciever : disease) {
			if(!(spreader.equals(reciever))) {
				
				float distance = spreader.getCentreCoords().dst(reciever.getCentreCoords());
				if(distance < spreadRadius) {
					if(spreader.isDiseased() && reciever.isDiseased()) {
						shapeRenderer.setColor(0, 1, 0, 0.1f); // GREEN line
					}
					if(spreader.isDiseased() && !reciever.isDiseased()) {
						if(reciever.everyoneBurnt()) {
							shapeRenderer.setColor(0, 0, 0, 0.1f);
						}
						else {
							shapeRenderer.setColor(0, 0, 1, 0.1f);
						}// BLUE line
					}
					if(!spreader.isDiseased() && reciever.isDiseased()) {
						if(spreader.everyoneBurnt()) {
							shapeRenderer.setColor(0, 0, 0, 0.1f);
						}
						else {
							shapeRenderer.setColor(1, 0, 0, 0.1f); // RED LINE
						}
					}
					if(!spreader.isDiseased() && !reciever.isDiseased()) {
						shapeRenderer.setColor(1, 1, 1, 0.1f); // WHITE LINE
					}
					shapeRenderer.rectLine(spreader.getCentreCoords(),reciever.getCentreCoords(),8);				
				}
			}
		}
		shapeRenderer.end();
	}

	public float calculateHouseIllness(Node house){
		float totalIllness = 0f;
		for(NPC resident : house.getNPCs()){
			if(resident.isIll()){
				totalIllness += 1 * 1/resident.getDaysInStatus();
			}
			else if (resident.isDead()){
				totalIllness += 1* resident.getDaysInStatus();
			}
		}
		return totalIllness;
	}

	//TODO finish implementation
	public float calculateResidentIllness(Node house){
		float illnessLikelihood = house.getIllnessLevel();
		house.
		return 0f;
	}
	public void diseaseSpread(List<Node> disease) {
		for(Node spreader : disease) {
			if(spreader.isDiseased()) {
				for(Node reciever : disease) {
					if(!reciever.isDiseased()) {
						if(!(spreader.equals(reciever))) {
							float distance = spreader.getCentreCoords().dst(reciever.getCentreCoords());
							if(distance < spreadRadius && diseaseImpacted()) {
								reciever.infectRandom(probabilty);
							}
						}
					}
					else {
						if(!(spreader.equals(reciever))) {
							if(!reciever.isAllInHouseDiseased()) {
								float distance = spreader.getCentreCoords().dst(reciever.getCentreCoords());
								if(distance < spreadRadius && diseaseImpacted()) {
									reciever.infectRandom(probabilty);
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void diseaseAffect(List<Node> disease) {
		for(Node spreader : disease) {
			if(spreader.isDiseased()) {
				for(NPC villagers : spreader.getResidents()) {
					if(!villagers.getStatus().equals("Dead") && !villagers.getStatus().equals("Burnt")) {
						double x = (Math.random()*((40-20)+20))+20; 
						x = x * -1;
						villagers.changeHealth((float) x);
					}
				}
			}
		}
	}

	public boolean diseaseImpacted() {
		float random = (float) (0 + Math.random() * (100));
		return random < probabilty;
	}
}