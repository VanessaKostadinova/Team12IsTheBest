 package com.mygdx.camera;

import java.io.Serializable;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
* Represents the camera which can be created in any scene.
* One Screen may only have many cameras.
* @author Inder Panesar
*
*/
public class Camera implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	
	private float dx = 0;
	private float dy = 0;
	
	private float maxDx = 0;
	private float maxDy = 0;
	
	
	
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
	
	public void setMaxValues (float maxDx, float maxDy) {
		this.maxDx = maxDx;
		this.maxDy = maxDy;
	}
	
	
	public void updateCameraPosition(float dx, float dy) {
		if(this.dx <= this.maxDx && this.dy < this.maxDy) {
			this.dx += dx;
			this.dy += dy;
			camera.translate(dx, dy);
		}
		camera.update();
	}
	
	public void zoomOut(float dZoom) {
		camera.update();
	}
	
	public void zoomIn(float dZoom) {
		if(camera.zoom+dZoom > 0 && camera.zoom+dZoom < 8) {
			camera.zoom += dZoom;
			camera.update();
		}
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
	
	public float getdx() {
		return dx;
	}
	
	public float getdy() {
		return dy;
	}
	

}