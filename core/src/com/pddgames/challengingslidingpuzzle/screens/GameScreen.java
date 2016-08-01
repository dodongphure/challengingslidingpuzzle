package com.pddgames.challengingslidingpuzzle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.pddgames.challengingslidingpuzzle.gameworld.GameRenderer;
import com.pddgames.challengingslidingpuzzle.gameworld.GameWorld;

/**
 * 
 * @author dodongphu
 *
 */
public class GameScreen extends ScreenAdapter {
	
	private GameWorld gameWorld;
	private GameRenderer gameRenderer;
	
	public static final int GAME_WIDTH = 320;
	
	public GameScreen() {
		gameWorld = new GameWorld();
		gameRenderer = new GameRenderer(gameWorld, getGameHeight());
	}
	
	private int getGameHeight() {
		int screenWidth = Gdx.graphics.getWidth();
		int screenHeight = Gdx.graphics.getHeight();
		return (int) (screenHeight / (screenWidth / GAME_WIDTH));
	}

	@Override
	public void render(float delta) {
		gameWorld.update(gameRenderer.getCamera(), delta);
		gameRenderer.render();
	}

}
