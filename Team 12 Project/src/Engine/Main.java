package Engine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

public class Main implements Runnable {

	//window dimensions
	private static int width = 1500;
	private static int height = 900;
	
	//setting up threads
	private Thread thread;
	private boolean running = true;
	
	//creating window
	private long window;
	
	//making thread
	public void start() {
		thread = new Thread(this, "Game");
		thread.start();
	}

	private void init() {
		
		if(!glfwInit()) {
			throw new IllegalStateException("GLFW failed to init");
		}
		
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		window = glfwCreateWindow(width, height, "Plague Doctor", 0, 0);
		if (window == 0) {
			throw new IllegalStateException("Failed to create window");
		}
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
	    glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2); 
	    glfwMakeContextCurrent(window);
	    glfwShowWindow(window);
	    
	}
	
	@Override
	public void run() {
		init();
		while(running) {
			update();
			render();
			
			if(glfwWindowShouldClose(window)) {
				running = false;
			}
		}
		glfwTerminate();
	}
	
	private void update() {
		glfwPollEvents();
	}
	
	private void render() {
		glfwSwapBuffers(window);
	}
	
	//making window
	public static void main(String[] args) {
		new Main().start();
	}
}
