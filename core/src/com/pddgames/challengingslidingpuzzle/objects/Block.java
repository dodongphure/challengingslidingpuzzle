package com.pddgames.challengingslidingpuzzle.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.pddgames.challengingslidingpuzzle.helpers.AssetLoader;

/**
 * 
 * @author dodongphu
 *
 */
public class Block extends Widget {
	
	/**
	 * Indicate the order of block in GameWord. When block is moved, orderNumber value should be changed.
	 */
	private int orderNumber;
	private float size;
	private Label label;
	private int number;
	private Image backgroundImage;
	
	/**
	 * Generate a rounded Block with number.
	 * If <b>number < 0</b>, this block is considered <b>empty (not visible)</b>.
	 * @param size
	 * @param number
	 */
	public Block(int orderNumber, float size, int number) {
		label = new Label(String.valueOf(number), AssetLoader.skin);
		label.setAlignment(Align.center);
		
		this.orderNumber = orderNumber;
		this.size = size;
		this.number = number;
		
		this.backgroundImage = AssetLoader.blockBackground;
		this.backgroundImage.setScaling(Scaling.fit);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// We do not draw negative Block's number.
		if(number < 0) {
			return;
		}
		
		this.backgroundImage.setBounds(getX(), getY(), getWidth(), getHeight());
		this.backgroundImage.draw(batch, parentAlpha);
		
		label.setColor(Color.BLACK);
		// need to update number when each time draw block's number (in case of pressing Reset button).
		label.setText(number + "");
		label.setBounds(getX(), getY(), getWidth(), getHeight());
		label.draw(batch, parentAlpha);
	}
	
	/*private void drawRoundedBlock(float x, float y, float width, float height, float radius) {
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
	}*/

	@Override
	public float getPrefWidth() {
		return this.size;
	}

	@Override
	public float getPrefHeight() {
		return this.size;
	}
	
	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public int getNumber() {
		return this.number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
}
