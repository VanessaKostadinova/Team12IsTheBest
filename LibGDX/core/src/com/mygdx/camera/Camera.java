package com.mygdx.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Represents the camera which can be created in any scene.
 * One Screen may only have many cameras.
 * @author Team 12
 *
 */
public class Camera {
	
	/**
	 * The instance of the camera.
	 */
	private OrthographicCamera camera;
	
	/**
	 * Used to set the view of the camera. 
	 * Changes the view of the project when window is resized.
	 */
	private Viewport viewport;
	
	/**
	 * Constructor for Camera.
	 * Sets initialises the camera. 
	 * 
	 * @param viewWidth The width which the camera can see.  
	 * @param height The height of the window.
	 * @param width The width of the window.
	 */
	public Camera(float viewWidth, float height, float width) {
		camera = new OrthographicCamera(viewWidth,viewWidth*(height/width));
		/*
		 * This can be changed, but FitViewport makes sure when the window is resized the ratio is the same
		 * and we also see ~ the same as we were before.
		 * 
		 * The others are shown here: https://github.com/libgdx/libgdx/wiki/Viewports/
		 */
		viewport = new FitViewport(viewWidth, viewWidth*(height/width),camera);
		viewport.apply();
		camera.update();
	}

	/**
	 * Returns the camera.
	 * @return The instance of the Orthographic Camera.
	 */
	public OrthographicCamera getCamera() {
		return camera;
	}
	
	/**
	 * Returns Camera's Viewport
	 * @return The Orthographic Camera's Viewport.
	 */
	public Viewport getViewport() {
		return viewport;
	}
	
	/**
	 * Updates Camera, without having to retrieve Camera.
	 */
	public void updateCamera() {
		camera.update();
	}
	

}
