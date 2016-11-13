package com.pddgames.challengingslidingpuzzle.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pddgames.challengingslidingpuzzle.gameworld.GameWorld;
import com.pddgames.challengingslidingpuzzle.helpers.AssetLoader;
import com.pddgames.challengingslidingpuzzle.objects.CustomDialog;
import com.pddgames.challengingslidingpuzzle.objects.CustomDialog.Type;
import com.pddgames.challengingslidingpuzzle.objects.RecordingData;

/**
 * 
 * @author dodongphu
 *
 */
public class GameScreen extends ScreenAdapter {
	
	private static final int BUTTON_SIZE = 50;
	
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
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		stage.addActor(AssetLoader.background);
		
		table = new Table();
//		table.setDebug(true);
		table.setFillParent(true);
		
		recordingData = new RecordingData();
		recordingData.start();
		recordingData.addAction(Actions.moveBy(0, -8, .3f));
		table.add(recordingData).colspan(3).row();
		
		gameWorld = new GameWorld(recordingData);
		gameWorld.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1f)));
		table.add(gameWorld).expand().colspan(3);

		initializeButtons();
		
		stage.addActor(table);
		AssetLoader.gamePlaySound.play();
	}
	
	private void initializeButtons() {
		table.row().padBottom(10);
		
		ImageTextButton backBtn = new ImageTextButton("", AssetLoader.skin.get("backBtn", ImageTextButtonStyle.class));
		backBtn.addAction(Actions.moveBy(0, 10, .3f));
		backBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				recordingData.pause();
				if(recordingData.getMovingCount() > 0 || AssetLoader.prefs.getInteger("second") > 0) {// Game is already played -> Show confirm pop-up when back to main Menu.
					new CustomDialog(Type.CONFIRM, stage, "Game is running.\nAre you sure to quit?") {
						@Override
						protected void result(Object object) {
							if(object.equals(CustomDialog.STATUS_OK)) {// Press OK (quit game).
								// save game state before quit game
								gameWorld.saveGameData();
								((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
							} else {
								recordingData.resume();
							}
						}
					};
				} else {
					((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
				}
				
				AssetLoader.buttonSound.play();
			}
		});
		table.add(backBtn).size(BUTTON_SIZE);
		
		ImageTextButton pauseBtn = new ImageTextButton("", AssetLoader.skin.get("pauseBtn", ImageTextButtonStyle.class));
		pauseBtn.addAction(Actions.moveBy(0, 10, .3f));
		pauseBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				recordingData.pause();
				new CustomDialog(CustomDialog.Type.INFO, stage, "Game is paused!") {
					@Override
					protected void result(Object object) {
						recordingData.resume();
					}
				};
				
				AssetLoader.buttonSound.play();
			}
		});
		table.add(pauseBtn).size(BUTTON_SIZE);
		
		ImageTextButton resetBtn = new ImageTextButton("", AssetLoader.skin.get("resetBtn", ImageTextButtonStyle.class));
		resetBtn.addAction(Actions.moveBy(0, 10, .3f));
		resetBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				recordingData.pause();
				if(recordingData.getMovingCount() > 0) {// Game is already played -> Show confirm pop-up when reset the game.
					new CustomDialog(CustomDialog.Type.CONFIRM, stage, "Game is running.\nAre you sure to reset?") {
						@Override
						protected void result(Object object) {
							if(object.equals(CustomDialog.STATUS_OK)) {// Press OK (reset game).
								recordingData.resetData();
								gameWorld.resetData();
							} else {
								recordingData.resume();
							}
						}
					};
				} else {
					recordingData.resetData();
					gameWorld.resetData();
				}
				
				AssetLoader.buttonSound.play();
			}
		});
		table.add(resetBtn).size(BUTTON_SIZE);
	}
	
	@Override
	public void dispose() {
		stage.dispose();
	}

}
