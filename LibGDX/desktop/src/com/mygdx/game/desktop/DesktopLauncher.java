package com.mygdx.game.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Main;

import java.awt.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

		config.height = (int) dimension.getHeight();
		config.width = (int) dimension.getWidth();
		config.title = "Plague Doctor";
		config.fullscreen = true;
		config.addIcon("icon.png", FileType.Internal);
		config.resizable = false;
		config.vSyncEnabled = true;
		new LwjglApplication(new Main(true), config);
	}
}