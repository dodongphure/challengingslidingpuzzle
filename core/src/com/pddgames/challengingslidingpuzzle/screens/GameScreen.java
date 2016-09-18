package com.pddgames.challengingslidingpuzzle.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pddgames.challengingslidingpuzzle.gameworld.GameWorld;
import com.pddgames.challengingslidingpuzzle.helpers.AssetLoader;
import com.pddgames.challengingslidingpuzzle.helpers.DialogProperty;
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
	private GameWorld gameWorld;
	
	public GameScreen() {
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
		
		gameWorld = new GameWorld(recordingData);
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
				recordingData.pause();
				if(recordingData.getMovingCount() > 0) {// Game is already played -> Show confirm pop-up when back to main Menu.
					Dialog dialog = new Dialog(DialogProperty.CONFIRM, AssetLoader.skin, "dialog") {
						@Override
						protected void result(Object object) {
							if(object.equals(1)) {// Press OK (quit game).
								((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
							} else {
								recordingData.resume();
							}
						}
					};
					dialog.text("Game is running. Are you sure to quit?", AssetLoader.skin.get("dialog", LabelStyle.class));
					dialog.button(DialogProperty.CANCEL, 0, AssetLoader.skin.get("dialog", TextButton.TextButtonStyle.class));
					dialog.button(DialogProperty.OK, 1, AssetLoader.skin.get("dialog", TextButton.TextButtonStyle.class));
					dialog.show(stage);
				} else {
					((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
				}
			}
		});
		table.add(backBtn);
		
		TextButton pauseBtn = new TextButton(PAUSE_BTN, AssetLoader.skin);
		pauseBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				recordingData.pause();
				Dialog dialog = new Dialog(DialogProperty.INFO, AssetLoader.skin, "dialog") {
					@Override
					protected void result(Object object) {
						recordingData.resume();
					}
				};
				dialog.text("Game is paused!", AssetLoader.skin.get("dialog", LabelStyle.class));
				dialog.button(DialogProperty.RESUME, null, AssetLoader.skin.get("dialog", TextButton.TextButtonStyle.class));
				dialog.show(stage);
			}
		});
		table.add(pauseBtn);
		
		TextButton resetBtn = new TextButton(RESET_BTN, AssetLoader.skin);
		resetBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				recordingData.pause();
				if(recordingData.getMovingCount() > 0) {// Game is already played -> Show confirm pop-up when reset the game.
					Dialog dialog = new Dialog(DialogProperty.CONFIRM, AssetLoader.skin, "dialog") {
						@Override
						protected void result(Object object) {
							if(object.equals(1)) {// Press OK (reset game).
								recordingData.resetData();
								gameWorld.resetData();
							} else {
								recordingData.resume();
							}
						}
					};
					dialog.text("Game is running. Are you sure to reset?", AssetLoader.skin.get("dialog", LabelStyle.class));
					dialog.button(DialogProperty.CANCEL, 0, AssetLoader.skin.get("dialog", TextButton.TextButtonStyle.class));
					dialog.button(DialogProperty.OK, 1, AssetLoader.skin.get("dialog", TextButton.TextButtonStyle.class));
					dialog.show(stage);
				} else {
					recordingData.resetData();
					gameWorld.resetData();
				}
			}
		});
		table.add(resetBtn);
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
