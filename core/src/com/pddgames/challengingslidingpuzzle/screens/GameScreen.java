package com.pddgames.challengingslidingpuzzle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.pddgames.challengingslidingpuzzle.gameworld.GameWorld;

public class GameScreen implements Screen {
	
	public static final int GAME_WIDTH = 320;
	
	private GameWorld gameWorld;
	private GameRenderer gameRenderer;
	
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
	public void show() {
	}

	@Override
	public void render(float delta) {
		gameRenderer.render();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}

}
