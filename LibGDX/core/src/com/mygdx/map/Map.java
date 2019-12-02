package com.mygdx.map;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.assets.AssetHandler;
import com.mygdx.renderable.Node;
import com.mygdx.shop.Shop;

public class Map implements Serializable{
	
	private String mapFile;
	private List<Node> nodes;
	private Shop shop; 
	private Disease disease;
	
	public Map(AssetHandler assets) {
		nodes = new ArrayList<Node>();
		readMapFile(assets);
		shop = new Shop(assets.manager.get("house/Shop.gif", Texture.class), 200.0f, 200.0f);
		checkIfPlayerExist();
		resetPlayerFile();
	}
	
	public void resetPlayerFile() {
		FileHandle handle = Gdx.files.local("data/player.txt");
		handle.writeString(1000f+","+5.0f+","+0f+","+10f+","+0.10f+","+-0.40f+","+2f+","+1f, false);
	}
	
	public List<Node> getNodes() {
		return nodes;
	}
	
	
	public Shop getShop() {
		return shop;
	}
	
	public void checkIfPlayerExist() {
		if(!Gdx.files.isLocalStorageAvailable()) {
			Gdx.app.log("File Error", "Local Storage not avaliable!");
		}
		if(!(Gdx.files.local("data/player.txt").exists())) {
			FileHandle handle = Gdx.files.local("data/player.txt");
			handle.writeString("0,5", false);
		}
	}
	
	
	public void readMapFile(AssetHandler assets) {
		FileHandle handle = Gdx.files.internal("house/files/"+"mapFile.txt");
		String nodeFilenames[] = handle.readString().split("\\r?\\n");
		for(String fileName : nodeFilenames) {
			handle = Gdx.files.local("data/"+fileName);
			String[] nodeProperties = handle.readString().split("\\r?\\n");
			if(!(nodes.size() == 20)) {
				nodes.add(new Node(assets.manager.get("house/House1.gif", Texture.class), Float.parseFloat(nodeProperties[0]), Float.parseFloat(nodeProperties[1]), nodeProperties));
			}
			else {
				Gdx.app.log("House Error", "More than 20 house are shown in the mapFile");
			}	
		}		
	}
	
	public void createPlayerFile() {
		
	}
	
	public void updatePlayerEnergyValue() {
		
	}
	
	
	

}
