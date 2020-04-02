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

		config.height = 720;
		config.width = 1280;
		config.title = "Plague Doctor";
		config.fullscreen = false;
		config.addIcon("icon.png", FileType.Internal);
		config.vSyncEnabled = true;
		new LwjglApplication(new Main(true), config);
	}
}