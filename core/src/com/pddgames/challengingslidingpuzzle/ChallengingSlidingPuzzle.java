package com.pddgames.challengingslidingpuzzle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pddgames.challengingslidingpuzzle.helpers.AssetLoader;
import com.pddgames.challengingslidingpuzzle.screens.MenuScreen;

public class ChallengingSlidingPuzzle extends Game {
	
	public static final String GAME_NAME = "Challenging Sliding Puzzle";
	
	private SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		AssetLoader.load();
		setScreen(new MenuScreen());
	}
	
	@Override
	public void dispose () {
		super.dispose();
		AssetLoader.dispose();
	}
}
