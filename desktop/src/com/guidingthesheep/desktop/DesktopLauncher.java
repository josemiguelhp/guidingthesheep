package com.guidingthesheep.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.guidingthesheep.Core;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height=480;
		config.width=320;
		config.vSyncEnabled=true;
		new LwjglApplication(new Core(), config);
	}
}
