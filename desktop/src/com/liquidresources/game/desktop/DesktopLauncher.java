package com.liquidresources.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.liquidresources.game.LiquidResources;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.useGL30 = false;
		config.title = "LiquidResources";
		config.height = 600;
		config.width = 1000;
		new LwjglApplication(new LiquidResources(), config);
	}
}
