package com.pddgames.challengingslidingpuzzle;

import com.badlogic.gdx.Game;
import com.pddgames.challengingslidingpuzzle.helpers.AssetLoader;
import com.pddgames.challengingslidingpuzzle.screens.MenuScreen;

public class ChallengingSlidingPuzzle extends Game {
	
	public static final String GAME_NAME = "Challenging Sliding Puzzle";
	
	@Override
	public void create () {
		AssetLoader.load();
		setScreen(new MenuScreen());
	}
	
	@Override
	public void dispose () {
		super.dispose();
		AssetLoader.dispose();
	}
}
