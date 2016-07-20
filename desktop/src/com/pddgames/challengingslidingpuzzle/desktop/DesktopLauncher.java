package com.pddgames.challengingslidingpuzzle.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pddgames.challengingslidingpuzzle.ChallengingSlidingPuzzle;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = ChallengingSlidingPuzzle.GAME_NAME;
		config.width = 320;
		config.height = 480;
		config.samples = 3;
		new LwjglApplication(new ChallengingSlidingPuzzle(), config);
	}
}
