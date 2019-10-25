package com.mygdx.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Camera {
	
	private OrthographicCamera camera;
	private Viewport viewport;
	
	public Camera(float viewWidth, float height, float width) {
		camera = new OrthographicCamera(viewWidth,viewWidth*(height/width));
		/**
		 * To Vanessa:
		 * 
		 * This can be changed, but FitViewport makes sure when the window is resized the ratio is the same
		 * and we also see ~ the same as we were before.
		 * 
		 * The others are shown here: https://github.com/libgdx/libgdx/wiki/Viewports/
		 */
		viewport = new FitViewport(viewWidth, viewWidth*(height/width),camera);
		viewport.apply();
		camera.update();
	}

	//Returns Camera
	public OrthographicCamera getCamera() {
		return camera;
	}
	
	//Returns Camera's Viewport
	public Viewport getViewport() {
		return viewport;
	}
	
	//Updates Camera
	public void updateCamera() {
		camera.update();
	}
	

}
