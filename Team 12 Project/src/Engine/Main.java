package Engine;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWVidMode;

public class Main implements Runnable {

	//window dimensions
	private static int width = 1500;
	private static int height = 900;
	
	//setting up threads
	private Thread thread;
	private boolean running = false;
	
	//making thread
	public void start() {
		thread = new Thread(this, "Game");
		thread.start();
	}

	private void init() {
		
	}
	
	@Override
	public void run() {
		init();
		while(running) {
			update();
			render();
		}
	}
	
	private void update() {

	}
	
	private void render() {
		
	}
	
	//making window
	public static void main(String[] args) {
		if(!glfwInit()) {
			throw new IllegalStateException("GLFW failed to init");
		}
		
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		long window = glfwCreateWindow(width, height, "Plague Doctor", 0, 0);
		if (window == 0) {
			throw new IllegalStateException("Failed to create window");
		}
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
	    glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2); 
	    
	    glfwShowWindow(window);
	    
	    while(!glfwWindowShouldClose(window)) {
	    	glfwPollEvents();
	    }
	    
	    glfwTerminate();
	}
}
