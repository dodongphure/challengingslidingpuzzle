package com.pddgames.challengingslidingpuzzle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
	
	private Stage stage;
	
	public static final int GAME_WIDTH = 320;
	
	public GameScreen() {
//		gameWorld = new GameWorld();
//		gameRenderer = new GameRenderer(gameWorld, getGameHeight());
	}
	
	private int getGameHeight() {
		int screenWidth = Gdx.graphics.getWidth();
		int screenHeight = Gdx.graphics.getHeight();
		return (int) (screenHeight / (screenWidth / GAME_WIDTH));
	}

	@Override
	public void render(float delta) {
		stage.act(delta);
		stage.draw();
		//gameWorld.update(gameRenderer.getCamera(), delta);
		//gameRenderer.render();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		super.resize(width, height);
	}

	@Override
	public void show() {
		//TODO: optimize using Singleton.
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		gameWorld = new GameWorld();
		gameWorld.setFillParent(true);
		stage.addActor(gameWorld);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}
	

}
