package com.mygdx.map;

import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.renderable.Node;
import com.mygdx.renderable.NodeConnection;

public class Disease {
	
	private final float spreadRadius = 300.0f;
	private final float probabilty = 30.0f;
	

	
	public void draw(List<Node> disease, Node spreader, ShapeRenderer shapeRenderer) {
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		for(Node reciever : disease) {
			if(!(spreader.equals(reciever))) {
				
				float distance = spreader.getCentreCoords().dst(reciever.getCentreCoords());
				if(distance < spreadRadius) {
					if(spreader.isDiseased() && reciever.isDiseased()) {
						shapeRenderer.setColor(0, 1, 0, 0.1f); // Red line
					}
					if(spreader.isDiseased() && !reciever.isDiseased()) {
						shapeRenderer.setColor(0, 0, 1, 0.1f); // Red line
					}
					if(!spreader.isDiseased() && reciever.isDiseased()) {
						shapeRenderer.setColor(1, 0, 0, 0.1f); 
					}
					if(!spreader.isDiseased() && !reciever.isDiseased()) {
						shapeRenderer.setColor(1, 1, 1, 0.1f);
					}
					shapeRenderer.rectLine(spreader.getCentreCoords(),reciever.getCentreCoords(),8);				
				}
			}
		}
		shapeRenderer.end();
	}


	
	
	
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





	public void clear(ShapeRenderer shapeRenderer) {
		//shapeRenderer.
	}
	
	
}
