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
	private String playerFile;
	
	public Map(AssetHandler assets) {
		nodes = new ArrayList<Node>();
		readMapFile(assets);
		shop = new Shop(assets.manager.get("house/Shop.gif", Texture.class), 200.0f, 200.0f);
	}
	
	public List<Node> getNodes() {
		return nodes;
	}
	
	public Shop getShop() {
		return shop;
	}
	
	
	public void readMapFile(AssetHandler assets) {
		FileHandle handle = Gdx.files.internal("house/files/"+"mapFile.txt");
		String nodeFilenames[] = handle.readString().split("\\r?\\n");
		for(String fileName : nodeFilenames) {
			handle = Gdx.files.internal("house/files/"+fileName);
			String[] nodeProperties = handle.readString().split("\\r?\\n");
			if(!(nodes.size() == 20)) {
				nodes.add(new Node(assets.manager.get("house/House1.gif", Texture.class), Float.parseFloat(nodeProperties[0]), Float.parseFloat(nodeProperties[1])));
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
