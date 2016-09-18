package com.pddgames.challengingslidingpuzzle.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pddgames.challengingslidingpuzzle.screens.GameScreen;

/**
 * 
 * @author dodongphu
 *
 */
public class MenuItem {
	
	private static int MENU_ITEM_HEIGHT = 40;
	private static int MENU_ITEM_MOVING_SPEED = 300;
	
	private String label;
	private Vector2 position;
	private Rectangle menuButton;
	
	public static int MENU_ITEM_WIDTH = 100;
	
	public MenuItem(float xPos, float yPos, String label) {
		this.label = label;
		this.position = new Vector2(xPos, yPos);
		menuButton = new Rectangle(xPos, yPos, MENU_ITEM_WIDTH, MENU_ITEM_HEIGHT);
	}
	
	public void update(float delta) {
		float expectedXPosition = Gdx.graphics.getWidth() / 2 - MENU_ITEM_WIDTH / 2;
		if(this.position.x < expectedXPosition) {
			this.position.add(delta * MENU_ITEM_MOVING_SPEED, 0);
			if(this.position.x >= expectedXPosition) {
				this.position.x = expectedXPosition;
			}
		}
		if(this.position.x > expectedXPosition) {
			this.position.add(-delta * MENU_ITEM_MOVING_SPEED, 0);
			if(this.position.x <= expectedXPosition) {
				this.position.x = expectedXPosition;
			}
		}
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public Vector2 getPosition() {
		return this.position;
	}

	public Rectangle getMenuButton() {
		return this.menuButton;
	}

}
