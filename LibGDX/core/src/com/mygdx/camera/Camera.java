package com.mygdx.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Camera {
	
	private OrthographicCamera camera;
	private Viewport viewport;
	
	public Camera(float viewWidth, float height, float width) {
		camera = new OrthographicCamera(viewWidth,viewWidth*(height/width));
		viewport = new FitViewport(viewWidth, viewWidth*(height/width),camera);
		viewport.apply();
		camera.update();
	}

	public OrthographicCamera getCamera() {
		return camera;
	}
	
	public Viewport getViewport() {
		return viewport;
	}
	
	public void updateCamera() {
		camera.update();
	}
	

}
