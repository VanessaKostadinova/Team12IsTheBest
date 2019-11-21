package com.mygdx.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.assets.AssetHandler;
import com.mygdx.renderable.Node;

public class TestMain implements ApplicationListener {
	List<Node> value = new ArrayList<>();

	
  /*public static void main(String[] args) {
		Main m = new Main();
		m.start();
	}*/
	
	public void start() {
		Disease d = new Disease();
		addNodes();
		for(int i = 0; i < 10; i++) {
			d.update(value);
		}
	
		for(Node n : value) {
			System.out.println("Node Is Diseased: " + n.isDiseased());
		}

	}
	
	
	public void addNodes() {
		Texture t = new Texture(Gdx.files.internal("house/House1.gif"));
		Node n = new Node(t, 20, 20);
		n.setDiseased(true);
		value.add(n);
		value.add(new Node(t, 40, 20));
		value.add(new Node(t, 60, 20));
		value.add(new Node(t, 80, 20));
		value.add(new Node(t, 100, 20));

	}

	@Override
	public void create() {
		start();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
