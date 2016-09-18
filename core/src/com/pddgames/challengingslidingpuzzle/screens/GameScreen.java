package com.pddgames.challengingslidingpuzzle.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pddgames.challengingslidingpuzzle.gameworld.GameWorld;
import com.pddgames.challengingslidingpuzzle.helpers.AssetLoader;
import com.pddgames.challengingslidingpuzzle.objects.RecordingData;

/**
 * 
 * @author dodongphu
 *
 */
public class GameScreen extends ScreenAdapter {
	
	private static final String BACK_BTN = "B";
	private static final String PAUSE_BTN = "P";
	private static final String RESET_BTN = "R";
	
	private Stage stage;
	private Table table;
	private RecordingData recordingData;
	private GameState gameState;
	
	private enum GameState {
		NEW,
		PAUSED,
		PLAYED
	}
	
	public GameScreen() {
		gameState = GameState.NEW;
	}

	@Override
	public void render(float delta) {
		// Fill the entire screen with black, to prevent potential flickering.
		Gdx.gl.glClearColor(100/255f, 100/255f, 100/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().setScreenSize(width, height);
		table.invalidateHierarchy();
	}

	@Override
	public void show() {
		//TODO: optimize using Singleton.
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		table = new Table();
		table.setDebug(true);
		table.setFillParent(true);
		
		recordingData = new RecordingData();
		recordingData.start();
		table.add(recordingData).colspan(3).row();
		
		GameWorld gameWorld = new GameWorld(recordingData);
		table.add(gameWorld).expand().colspan(3);

		initializeButtons();
		
		stage.addActor(table);
		stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1f)));
	}
	
	private void initializeButtons() {
		table.row().padBottom(20);
		
		TextButton backBtn = new TextButton(BACK_BTN, AssetLoader.skin);
		backBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
			}
		});
		table.add(backBtn);
		
		TextButton pauseBtn = new TextButton(PAUSE_BTN, AssetLoader.skin);
		pauseBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				recordingData.pause();
				Dialog dialog = new Dialog("TEST", AssetLoader.skin);
				dialog.text("Paused!");
				dialog.button("OK");
				dialog.show(stage);
			}
		});
		table.add(pauseBtn);
		
		TextButton resetBtn = new TextButton(RESET_BTN, AssetLoader.skin);
		resetBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println(2);
			}
		});
		table.add(resetBtn);
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
