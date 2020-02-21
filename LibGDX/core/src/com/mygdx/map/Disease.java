package com.mygdx.map;

import java.util.List;
import java.util.Random;

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
			System.out.println("I am executing");
			if(random.nextInt(10) <= illnessLikelihood){
				resident.infect();
			}
		}
	}

	public void diseaseSpread(Node house) {
		if(house.isDiseased()) {
			for(Node reciever : house.getNeighbours()) {
				if(!reciever.isDiseased()) {
					float distance = house.getCentreCoords().dst(reciever.getCentreCoords());
					if(distance < spreadRadius && diseaseImpacted()) {
						reciever.infectRandom(probabilty);
					}
				}
				else {
					if(!reciever.isAllInHouseDiseased()) {
						float distance = house.getCentreCoords().dst(reciever.getCentreCoords());
						if(distance < spreadRadius && diseaseImpacted()) {
							reciever.infectRandom(probabilty);
						}
					}
				}
			}
		}
	}

	public void diseaseAffect(Node spreader) {
		if(spreader.isDiseased()) {
			for(NPC villager : spreader.getResidents()) {
				if(!villager.getStatus().equals("Dead") && !villager.getStatus().equals("Burnt")) {
					double x = (Math.random()*((40-20)+20))+20;
					x = x * -1;
					villager.changeHealth((float) x);
				}
			}
		}
	}

	public boolean diseaseImpacted() {
		float random = (float) (0 + Math.random() * (100));
		return random < probabilty;
	}
}