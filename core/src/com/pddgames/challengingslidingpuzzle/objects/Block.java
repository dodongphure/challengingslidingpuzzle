package com.pddgames.challengingslidingpuzzle.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.pddgames.challengingslidingpuzzle.helpers.AssetLoader;

/**
 * 
 * @author dodongphu
 *
 */
public class Block extends Widget {
	
	private static final int MOVE_VELOCITY = 520;

	private float size;
	private Label label;
	private int number;
	
	private ShapeRenderer shapeRender;
	private Batch batch;
	
	public Block(float size, int number) {
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = AssetLoader.font;
		label = new Label(String.valueOf(number), labelStyle);
		label.setAlignment(Align.center);
		
		this.size = size;
		this.number = number;
		
		shapeRender = new ShapeRenderer();
		batch = new SpriteBatch();// create a new SpriteBatch to draw number.
	}
	
	/**
	 * 
	 * @param delta
	 * @return true if this Block is moving, else return false
	 */
	/*public boolean update(float delta) {
		if(newPosition.x == position.x) {
			if(newPosition.y > position.y) {
				position.add(0, delta * MOVE_VELOCITY);// Move Up.
				if(position.y > newPosition.y) {
					position.y = newPosition.y;
				}
				return true;
			} else if(newPosition.y < position.y) {
				position.add(0, -delta * MOVE_VELOCITY);// Move Down.
				if(position.y < newPosition.y) {
					position.y = newPosition.y;
				}
				return true;
			}
		} else if(newPosition.y == position.y) {
			if(newPosition.x > position.x) {
				position.add(delta * MOVE_VELOCITY, 0);// Move Right
				if(position.x > newPosition.x) {
					position.x = newPosition.x;
				}
				return true;
			} else if(newPosition.x < position.x) {
				position.add(-delta * MOVE_VELOCITY, 0);// Move Left
				if(position.x < newPosition.x) {
					position.x = newPosition.x;
				}
				return true;
			}
		}
		return false;
	}*/
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// Draw rounded block by using ShapeRender.
		shapeRender.begin(ShapeType.Filled);
		shapeRender.setColor(1, 1, 1, 1);
		drawRoundedBlock(getX(), getY(), getWidth(), getHeight(), 4);
		shapeRender.end();
		
		label.setColor(Color.BLACK);
		label.setBounds(getX(), getY(), getWidth(), getHeight());
		this.batch.begin();
		label.draw(this.batch, parentAlpha);
		this.batch.end();
	}
	
	private void drawRoundedBlock(float x, float y, float width, float height, float radius) {
		// Central rectangle
		shapeRender.rect(x + radius, y + radius, width - 2*radius, height - 2*radius);

        // Four side rectangles, in clockwise order
		shapeRender.rect(x + radius, y, width - 2*radius, radius);
		shapeRender.rect(x + width - radius, y + radius, radius, height - 2*radius);
		shapeRender.rect(x + radius, y + height - radius, width - 2*radius, radius);
		shapeRender.rect(x, y + radius, radius, height - 2*radius);

        // Four arches, clockwise too
		shapeRender.arc(x + radius, y + radius, radius, 180f, 90f);
		shapeRender.arc(x + width - radius, y + radius, radius, 270f, 90f);
		shapeRender.arc(x + width - radius, y + height - radius, radius, 0f, 90f);
		shapeRender.arc(x + radius, y + height - radius, radius, 90f, 90f);
	}

	@Override
	public float getPrefWidth() {
		return this.size;
	}

	@Override
	public float getPrefHeight() {
		return this.size;
	}
	
	public int getNumber() {
		return this.number;
	}

}
