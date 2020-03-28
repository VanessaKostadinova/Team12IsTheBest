package com.mygdx.map;

import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.house.House;
import com.mygdx.renderable.NPC;
import com.mygdx.renderable.Node;

/**
 * This class handles the spread of disease between each node on the map as well as the spread between each node.
 * @author Inder, Vanessa.
 */
public class Disease {

	public final float spreadRadius = 250.0f;

	/**
	 * Is able to draw the lines between the houses it is able to spread in between
	 * @param houseOnMap the list of nodes on the map
	 * @param spreader the node which is spreading the disease.
	 * @param shapeRenderer The shape renderer to allow to draw lines and set them and render the lines on to the screen.
	 */
	public void draw(List<Node> houseOnMap, Node spreader, ShapeRenderer shapeRenderer) {
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		for(Node reciever : houseOnMap) {
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

	/**
	 * Calculates the illness level of a given house based on its sick residents.
	 */
	public float calculateHouseIllness(Node house){
		float totalIllness = 0f;
		for(NPC resident : house.getNPCs()){
			if(resident.isSick()){
				totalIllness += (1.5);
			}
			else if (resident.isDead()){
				totalIllness += (2.5);
			}
		}
		house.setIllnessLevel(totalIllness);
		return totalIllness;
	}

	/**
	 * Infects random residents in a given house based on that house's and it's neighbours illness levels.
	 */
	public void infectResidents(Node house){
		Random random = new Random();
		float illnessLikelihood = house.getIllnessLevel();
		for(Node compareHouse : house.getNeighbours()){
			float distance = house.getCentreCoords().dst(compareHouse.getCentreCoords());
			illnessLikelihood += compareHouse.getIllnessLevel() * Math.pow(distance, (-1/3));
		}

		for(NPC resident : house.getNPCs()){
			if (resident.getStatus().equals("Alive")) {
				if((random.nextInt(100) + 1) < illnessLikelihood){
					resident.infect();
					resident.changeHealth(-10);
				}
			}
			if(resident.getStatus().equals("Sick")) {
				resident.changeHealth(-7.5f);
			}
		}

	}
}