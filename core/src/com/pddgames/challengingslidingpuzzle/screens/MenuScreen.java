package com.pddgames.challengingslidingpuzzle.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pddgames.challengingslidingpuzzle.helpers.AssetLoader;
import com.pddgames.challengingslidingpuzzle.objects.CustomDialog;
import com.pddgames.challengingslidingpuzzle.objects.CustomDialog.Type;

/**
 * 
 * @author dodongphu
 *
 */
public class MenuScreen extends ScreenAdapter {
	
	private static final float MOVING_DISTANCE = 40;
	private static final String START_GAME_LABEL = "START";
	private static final String RESUME_GAME_LABEL = "RESUME";
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
		
		AssetLoader.gamePlaySound.stop();
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
		createMenuButton(START_GAME_LABEL, Actions.sequence(
				Actions.moveTo(0, stage.getHeight(), .5f),
				Actions.run(new Runnable() {
					@Override
					public void run() {
						AssetLoader.clearSavedGame();
						((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
					}
				}
			)));
		
		if(AssetLoader.hasSavedGame()) {// Show RESUME button.
			createMenuButton(RESUME_GAME_LABEL, Actions.sequence(
					Actions.moveTo(0, stage.getHeight(), .5f),
					Actions.run(new Runnable() {
						@Override
						public void run() {
							((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
						}
					}
				)));
		}
		
		createMenuButton(EXIT_GAME_LABEL, Actions.parallel(
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
	
	private TextButton createMenuButton(String label, Action action) {
		TextButton button = new TextButton(label, AssetLoader.skin.get("menu", TextButtonStyle.class));
		button.padLeft(60);
		button.addAction(Actions.moveBy(-30, 0, .3f));
		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				button.scaleBy(50);// TODO: not working now.
				if(label == START_GAME_LABEL) {// Handle special case for New Game.
					if(AssetLoader.hasSavedGame()) {
						new CustomDialog(Type.CONFIRM, stage, "Are you sure to play new game?") {
							@Override
							protected void result(Object object) {
								if(object.equals(CustomDialog.STATUS_OK)) {// Press OK.
									stage.addAction(action);
								}
							}
						};
					} else {
						stage.addAction(action);
					}
				} else {
					stage.addAction(action);
				}
				AssetLoader.menuSound.play();
			}
		});
		table.add(button).row();
		return button;
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
