package com.pddgames.challengingslidingpuzzle.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pddgames.challengingslidingpuzzle.helpers.AssetLoader;

/**
 * 
 * @author dodongphu
 *
 */
public class MenuScreen extends ScreenAdapter {
	
	private static final float MOVING_DISTANCE = 40;
	private static final String START_GAME_LABEL = "START";
	private static final String SETTINGS_LABEL = "SETTINGS";
	private static final String EXIT_GAME_LABEL = "EXIT";
	
	private Stage stage;
	private Table table;

	/*private int getGameHeight() {
		int screenWidth = Gdx.graphics.getWidth();
		int screenHeight = Gdx.graphics.getHeight();
		return (int) (screenHeight / (screenWidth / GameScreen.GAME_WIDTH));
	}
	
	private void initializeMenu() {
		MenuItem startGameBtn = new MenuItem(GameScreen.GAME_WIDTH/2 - MenuItem.MENU_ITEM_WIDTH/2 - MOVING_DISTANCE, 300, START_GAME_LABEL);
		MenuItem settingsBtn = new MenuItem(GameScreen.GAME_WIDTH/2 - MenuItem.MENU_ITEM_WIDTH/2 + MOVING_DISTANCE, 240, SETTINGS_LABEL);
		MenuItem exitGameBtn = new MenuItem(GameScreen.GAME_WIDTH/2 - MenuItem.MENU_ITEM_WIDTH/2 - MOVING_DISTANCE, 180, EXIT_GAME_LABEL);
		menuItems = new MenuItem[]{startGameBtn, settingsBtn, exitGameBtn};
	}*/

	@Override
	public void render(float delta) {
		// Fill the entire screen with black, to prevent potential flickering.
		Gdx.gl.glClearColor(100/255f, 100/255f, 100/255f, 1);
		
		stage.act(delta);
		stage.draw();
	}
	
	@Override
	public void show() {
		//TODO: optimize using Singleton.
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		stage.addActor(AssetLoader.background);
	
		table = new Table();
		table.setFillParent(true);
		
		initMenuButtons();
		
		stage.addActor(table);
		stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(2f)));
	}
	
	@Override
	public void resize(int width, int height) {
		stage.getViewport().setScreenSize(width, height);
		table.invalidateHierarchy();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
	
	private void initMenuButtons() {
		TextButton startBtn = new TextButton(START_GAME_LABEL, AssetLoader.skin);
		startBtn.padLeft(-60);
		startBtn.addAction(Actions.moveBy(30, 0, .3f));
		startBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				startBtn.scaleBy(50);// TODO: not working now.
				stage.addAction(Actions.sequence(
						Actions.moveTo(0, stage.getHeight(), .5f),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
							}
						}
					)));
			}
		});
		
		TextButton exitBtn = new TextButton(EXIT_GAME_LABEL, AssetLoader.skin);
		exitBtn.padLeft(60);
		exitBtn.addAction(Actions.moveBy(-30, 0, .3f));
		exitBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				exitBtn.scaleBy(50);// TODO: not working now.
				stage.addAction(Actions.parallel(
						Actions.alpha(0, .5f),
						Actions.moveTo(0, stage.getHeight(), .75f),
						Actions.delay(.5f, Actions.run(new Runnable() {
							@Override
							public void run() {
								Gdx.app.exit();
							}
						}))
					));
			}
		});
		
		table.add(startBtn).row();
		table.add(exitBtn).row();
	}

	/*private void drawMenuItems() {
		shapeRender.begin(ShapeType.Filled);
		shapeRender.setColor(1, 1, 1, 1);
		for(MenuItem menuItem : menuItems) {
			drawRoundedMenuItem(menuItem.getPosition().x, menuItem.getPosition().y,
					menuItem.getMenuButton().getWidth(), menuItem.getMenuButton().getHeight());
		}
		shapeRender.end();
		
		batcher.begin();
		AssetLoader.font.getData().setScale(.5f);
		for(MenuItem menuItem : menuItems) {
			AssetLoader.font.draw(batcher, menuItem.getLabel(), menuItem.getPosition().x, menuItem.getPosition().y + menuItem.getMenuButton().getHeight()/2 + 5,
					menuItem.getMenuButton().getWidth(), Align.center, false);
		}
		batcher.end();
	}
	
	private void drawRoundedMenuItem(float x, float y, float width, float height) {
		shapeRender.rect(x, y, width, height);
		shapeRender.arc(x, y + height / 2, height / 2, 90f, 180);
		shapeRender.arc(x + width, y + height / 2, height / 2, 270f, 180);
	}*/
	
}
