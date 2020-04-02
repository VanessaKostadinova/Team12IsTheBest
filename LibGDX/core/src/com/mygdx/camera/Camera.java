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
	

	/** The delta y value of the camera */
	private float dx = 0;
	/** The delta y value of the camera */
	private float dy = 0;

	/** The max X value the camera can move to */
	private float maxDx = 0;
	/** The max Y value the camera can move to */
	private float maxDy = 0;


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
		viewport = new FitViewport(viewWidth, viewWidth*(height/width),camera);
		viewport.apply();
		camera.update();
	}

	/**
	 * Sets the max bounds of the camera.
	 * @param maxDx MAX DX of the camera. (maximum x value)
	 * @param maxDy MAX DY of the camera. (maximum y value)
	 */
	public void setMaxValues (float maxDx, float maxDy) {
		this.maxDx = maxDx;
		this.maxDy = maxDy;
	}


	/**
	 * Updates the position of the camera.
	 * @param dx DX of the movement. The movement of the camera along the x-axis.
	 * @param dy DY of the movement. The movement of the camera along the y-axis.
	 */
	public void updateCameraPosition(float dx, float dy) {
		if(this.dx <= this.maxDx && this.dy < this.maxDy) {
			this.dx += dx;
			this.dy += dy;
			camera.translate(dx, dy);
		}
		camera.update();
	}

	/**
	 * Zooming the camera in.
	 * @param dZoom The amount of zoom
	 */
	public void zoomIn(float dZoom) {
		if(camera.zoom+dZoom > 0 && camera.zoom+dZoom < 7) {
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

	

}