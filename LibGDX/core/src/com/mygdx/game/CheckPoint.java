package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.assets.AssetHandler;
import com.mygdx.renderable.Node;
import com.mygdx.renderable.Player;

/**
 * Used as a CheckPoint, so it is able to reset to the point to where
 * you entered into the house. Ensuring that the villagers have been properly
 * reset.
 * @author Inder Panesar
 * @version 1.0
 */
public class CheckPoint implements Screen {

	/** Amount of time within this Screen */
	float stateTime = 0f;

	/** The main class */
	Main main;

	/** initial node, is the node which the player is already in */
	Node initialNode;

	/** The map screen instance of the game */
	MapScreen mapScreen;

	/** Label to show someone is dead*/
	Label dead;

	/** Set the mask durability of the current mask */
	private float maskDurablity;

	/** Create an instance of the checkpoint
	 *
	 * @param main The main class
	 * @param initialNode The initial node
	 * @param mapScreen The current map screen
	 * @param maskDurabilty The initial mask durabilty of the player on the last checkpoint.
	 */
	public CheckPoint(Main main, Node initialNode, MapScreen mapScreen, float maskDurabilty) {
		this.main = main;
		this.initialNode = initialNode;
		this.mapScreen = mapScreen;
		this.maskDurablity = maskDurabilty;

		
		dead = new Label("YOU'RE DEAD...", AssetHandler.fontSize128White);
		dead.setPosition(main.ui.getWidth()/2-dead.getWidth()/2, main.ui.getHeight()/2-dead.getHeight()/2);
		main.ui.addActor(dead);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);	
		stateTime = stateTime + delta;
		
		

		main.ui.draw();
		
		
		if(stateTime > 3 && dead.isVisible()) {
			stateTime = 0;
			dead.setVisible(false);
			changeScreen();
		}
	}




	/**
	 * Change the screen the current player is on.
	 */
	public void changeScreen() {
		main.ui.clear();
		System.out.println("MASK DURABILITY: " + maskDurablity);
		Player.getInstance().setCurrentMaskDuration(maskDurablity);
		main.setScreen(new HouseScreen(main, initialNode, mapScreen));
	}

	@Override
	public void resize(int width, int height) {
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
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
