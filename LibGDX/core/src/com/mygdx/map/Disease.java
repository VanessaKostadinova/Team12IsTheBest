package com.mygdx.map;

import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.house.House;
import com.mygdx.renderable.NPC;
import com.mygdx.renderable.Node;

public class Disease {

	public final float spreadRadius = 250.0f;

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
			if(resident.isSick()){
				totalIllness += (10 * 1/(resident.getDaysInStatus()+1));
			}
			else if (resident.isDead()){
				totalIllness += (10 * (resident.getDaysInStatus()+1));
			}
		}
		house.setIllnessLevel(totalIllness);
		return totalIllness;
	}

	//TODO finish implementation
	public void infectResidents(Node house){
		Random random = new Random();
		float illnessLikelihood = house.getIllnessLevel();
		for(Node compareHouse : house.getNeighbours()){
			float distance = house.getCentreCoords().dst(compareHouse.getCentreCoords());
			illnessLikelihood += compareHouse.getIllnessLevel() * Math.pow(distance, (-1/3));
		}

		for(NPC resident : house.getAllAlive()){
			if((random.nextInt(40) + 1) < illnessLikelihood){
				resident.infect();
			}
		}
	}
}