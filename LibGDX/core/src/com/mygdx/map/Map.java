package com.mygdx.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.assets.AssetHandler;
import com.mygdx.renderable.NPC;
import com.mygdx.renderable.Node;
import com.mygdx.shop.Church;
import com.mygdx.story.Note;

/**
 *
 */
public class Map {
	private List<Node> nodes;
	private Church church;
	private Disease disease = new Disease();

	private String[] notes = {
			"THEY ARE NOT WHO YOU THINK THEY ARE DON'T TRUST THEM.",
			"THIS DISEASE WILL NEVER END."
	};
	
	public Map() {
		nodes = new ArrayList<>();
		readMapFile();
		setNotes();
		church = new Church(AssetHandler.manager.get("house/Shop.gif", Texture.class), 900.0f, 470.0f);
		checkIfPlayerExist();
		resetPlayerFile();
		setNeighbours();
		setNotes();
	}
	
	public void resetPlayerFile() {
		FileHandle handle = Gdx.files.local("data/player.txt");
		handle.writeString(100f+","+5.0f+","+0f+","+30f+","+0.10f+","+-0.40f+","+2f+","+100f+","+0.05f+","+0.01f, false);
	}

	public void setNotes() {
		for(String note : notes) {

			Random r = new Random();
			int index = r.nextInt((nodes.size() - 1 - 0) + 1) + 0;

			int[][] map = nodes.get(index).getArray();
			boolean isValidPosition = false;
			int x = 0;
			int y = 0;
			while(!isValidPosition) {
				int maxX = (map[0].length * 32) - 64;
				int maxY = (map.length * 32) - 64;

				x = r.nextInt((maxX - 0) + 1) + 0;
				y = r.nextInt((maxY - 0) + 1) + 0;

				System.out.println(x);
				System.out.println(y);

				int xArray = x/32;
				int yArray = y/32;

				System.out.println(xArray);
				System.out.println(yArray);

				if(!(map[yArray][xArray] == 1)) {
					isValidPosition = true;
				}

				for(Note n : nodes.get(index).getNotes()) {
					if(n.getX() == x && n.getY() == y) {
						isValidPosition = false;
					}
				}

				for(NPC n : nodes.get(index).getNPCs()) {
					if(n.getCoords().x < x && n.getCoords().x+32 > x) {
						if(n.getCoords().y < x && n.getCoords().y+64 > y) {
							isValidPosition = false;
						}
					}
				}
			}


			nodes.get(index).addNotes(new Note(note, x, y));
		}
	}


	
	public List<Node> getNodes() {
		return nodes;
	}

	public Church getChurch() {
		return church;
	}
	
	public void checkIfPlayerExist() {
		if(!Gdx.files.isLocalStorageAvailable()) {
			Gdx.app.log("File Error", "Local Storage not available!");
		}
		if(!(Gdx.files.local("data/player.txt").exists())) {
			FileHandle handle = Gdx.files.local("data/player.txt");
			handle.writeString("0,5", false);
		}
	}

	public void readMapFile() {
		FileHandle handle = Gdx.files.internal("house/files/"+"mapFile.txt");
		String[] nodeFilenames;
		nodeFilenames = handle.readString().split("\\r?\\n");
		for(String fileName : nodeFilenames) {
			handle = Gdx.files.local(String.format("data/%s", fileName));
			String[] nodeProperties = handle.readString().split("\\r?\\n");
			if(!(nodes.size() == 20)) {
				nodes.add(new Node(new Texture(Gdx.files.internal("house/"+nodeProperties[0])), Float.parseFloat(nodeProperties[1]), Float.parseFloat(nodeProperties[2]), nodeProperties));
			}
			else {
				Gdx.app.log("House Error",
						"More than 20 house are shown in the mapFile");
			}	
		}		
	}

	private void setNeighbours(){
		for(Node house : nodes){
			for(Node compareHouse : nodes){
				if(!(house.equals(compareHouse))){
					float distance = house.getCentreCoords().dst(compareHouse.getCentreCoords());
					if(distance <= disease.spreadRadius){
						house.addNeighbour(compareHouse);
					}
				}
			}
		}
	}
	
	public void createPlayerFile() {
		
	}
	
	public void updatePlayerEnergyValue() {
		
	}

	public Disease getDisease() {
		return disease;
	}
}
