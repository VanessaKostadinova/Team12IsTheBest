package com.mygdx.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.assets.AssetHandler;
import com.mygdx.extras.PermanetPlayer;
import com.mygdx.renderable.NPC;
import com.mygdx.renderable.Node;
import com.mygdx.shop.Church;
import com.mygdx.shop.Shop;
import com.mygdx.story.Note;

/**
 * Holds the information about every entity which can be represented by the map
 * @author Inder, Vanessa, Max
 */
public class Map {
	/** Holds a list of nodes which are on the map */
	private List<Node> nodes;
	/** Holds an instance of the church */
	private Church church;
	/** Holds an instance of the shop */
	private Shop shop;
	/** Holds an instance of the disease */
	private Disease disease = new Disease();

	/** Holds the current set of notes */
	private String[] notes = {
			"MY MASK FILTER IS RUNNING LOW, I NEED TO SOURCE A REPLACEMENT SOON",
			"ANTONIO HAS FIGURED OUT HOW TO MAKE EXTRA MASKS, I MAY NOT NEED ORGANIZE ANY SUPPLY RUNS SOON",
			"WEIRD FOOTPRINTS, I HAVE SEEN THIS TREAD BEFORE.",
			"CANNOT BELIEVE THIS, THEY COULD NEVER DO SUCH A THING -",
			"DO THEY EVEN CARE? WHY COULD THEY DOING THIS, CONTACTING ANYONE AT THIS STAGE MAY BE UNWISE",
			"THEY MAY BE ON TO ME. I NEED TO LEAVE IT IS NOT SAFE. BUT WHERE WOULD THEY NOT SEARCH?"
	};

	/**
	 * Constructs the map, this is used when a new game is started.
	 */
	public Map() {
		nodes = new ArrayList<>();
		readMapFile();
		setNotes();
		church = new Church(AssetHandler.manager.get("house/Shop.gif", Texture.class), 900.0f, 470.0f);
		shop = new Shop(AssetHandler.manager.get("house/Shop.gif", Texture.class), 760.0f, 500.0f);
		checkIfPlayerExist();
		resetPlayerFile();
		setNeighbours();
		if(!notesAlreadyExists()) {
			setNotes();
		}
	}

	/**
	 * Constructs the map, this is used when continuing a game.
	 */
	public Map(List<Node> nodes) {
		this.nodes = nodes;
		church = new Church(AssetHandler.manager.get("house/Shop.gif", Texture.class), 900.0f, 470.0f);
		shop = new Shop(AssetHandler.manager.get("house/Shop.gif", Texture.class), 760.0f, 500.0f);
		checkIfPlayerExist();
		setNeighbours();
	}

	/**
	 * Check if note already exists inside a node
	 * @return if note exists
	 */
	private Boolean notesAlreadyExists() {
		for(Node n : nodes) {
			if(n.getNotes().size() > 0) {
				return true;
			}
		}
		return false;
	}

	/** Reset the player text file */
	public void resetPlayerFile() {
		FileHandle handle = Gdx.files.local("data/player.txt");
		handle.writeString(100f+","+5.0f+","+0f+","+30f+","+0.10f+","+-0.40f+","+2f+","+100f+","+0.05f+","+0.01f, false);
	}

	/**
	 * Set the notes randomly into a bunch of nodes.
	 */
	public void setNotes() {
		int i = 0;
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


				int xArray = x/32;
				int yArray = y/32;


				if((map[yArray][xArray] == 0)) {
					isValidPosition = true;
				}

				if(xArray + 1 < maxX  || yArray + 1 < maxY) {
					if((map[yArray+1][xArray+1] == 0)) {
						isValidPosition = false;
					}
				}
				else {
					isValidPosition = false;
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

			i++;
			nodes.get(index).addNotes(new Note(note, x, y));
		}
		System.out.println("NOTES GENERATED: "  + i);
	}

	/**
	 * Set the current set of notes
	 * @param notes The array of note information
	 */
	public void nextNoteLevel (String[] notes) {
		this.notes = notes;
	}

	/**
	 * The list of nodes on a map.
	 * @return The nodes on the map.
	 */
	public List<Node> getNodes() {
		return nodes;
	}

	/**
	 * Returns the current instance of the church screen
	 * @return church screen
	 */
	public Church getChurch() {
		return church;
	}

	/**
	 * Returns the current instance of the shop screen
	 * @return shop screen
	 */
	public Shop getShop() {
		return shop;
	}

	/**
	 * Check if the player file exists.
	 */
	public void checkIfPlayerExist() {
		if(!Gdx.files.isLocalStorageAvailable()) {
			Gdx.app.log("File Error", "Local Storage not available!");
		}
		if(!(Gdx.files.local("data/player.txt").exists())) {
			FileHandle handle = Gdx.files.local("data/player.txt");
			handle.writeString("0,5", false);
		}
	}

	/**
	 * Read the map file to define the map.
	 */
	public void readMapFile() {
		FileHandle handle = Gdx.files.internal("house/files/"+"mapFile.txt");
		String[] nodeFilenames;
		nodeFilenames = handle.readString().split("\\r?\\n");
		for(String fileName : nodeFilenames) {
			handle = Gdx.files.local(String.format("data/%s", fileName));
			String[] nodeProperties = handle.readString().split("\\r?\\n");
			if(!(nodes.size() == 20)) {
				nodes.add(new Node("house/"+nodeProperties[0], Float.parseFloat(nodeProperties[1]), Float.parseFloat(nodeProperties[2]), nodeProperties));
			}
			else {
				Gdx.app.log("House Error",
						"More than 20 house are shown in the mapFile");
			}	
		}		
	}

	/**
	 * Set the neighbours for each of the nodes.
	 */
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


	/**
	 * Gets the current instance of the disease.
	 * @return disease instance
	 */
	public Disease getDisease() {
		return disease;
	}
}
