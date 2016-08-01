package com.pddgames.challengingslidingpuzzle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import com.pddgames.challengingslidingpuzzle.ChallengingSlidingPuzzle;
import com.pddgames.challengingslidingpuzzle.helpers.AssetLoader;
import com.pddgames.challengingslidingpuzzle.objects.MenuItem;

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
	
	private OrthographicCamera camera;
	private SpriteBatch batcher;
	private ShapeRenderer shapeRender;
	
	private ChallengingSlidingPuzzle game;
	private MenuItem[] menuItems;

	public MenuScreen() {
		//this.game = game;
		initializeMenu();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameScreen.GAME_WIDTH, getGameHeight());
		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(camera.combined);
		shapeRender = new ShapeRenderer();
		shapeRender.setProjectionMatrix(camera.combined);
	}
	
	private int getGameHeight() {
		int screenWidth = Gdx.graphics.getWidth();
		int screenHeight = Gdx.graphics.getHeight();
		return (int) (screenHeight / (screenWidth / GameScreen.GAME_WIDTH));
	}
	
	private void initializeMenu() {
		MenuItem startGameBtn = new MenuItem(GameScreen.GAME_WIDTH/2 - MenuItem.MENU_ITEM_WIDTH/2 - MOVING_DISTANCE, 300, START_GAME_LABEL);
		MenuItem settingsBtn = new MenuItem(GameScreen.GAME_WIDTH/2 - MenuItem.MENU_ITEM_WIDTH/2 + MOVING_DISTANCE, 240, SETTINGS_LABEL);
		MenuItem exitGameBtn = new MenuItem(GameScreen.GAME_WIDTH/2 - MenuItem.MENU_ITEM_WIDTH/2 - MOVING_DISTANCE, 180, EXIT_GAME_LABEL);
		menuItems = new MenuItem[]{startGameBtn, settingsBtn, exitGameBtn};
	}

	@Override
	public void render(float delta) {
		// Fill the entire screen with black, to prevent potential flickering.
		Gdx.gl.glClearColor(100/255f, 100/255f, 100/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
		for(MenuItem menuItem : menuItems) {
			menuItem.update(delta);
		}
		drawMenuItems();
	}
	
	private void drawMenuItems() {
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
	}
	
}
