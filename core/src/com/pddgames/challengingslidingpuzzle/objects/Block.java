package com.pddgames.challengingslidingpuzzle.objects;

import com.badlogic.gdx.math.Vector2;

public class Block {

	private Vector2 position;
	private int size;
	
	private int number;
	
	public Block(float x, float y, int size, int number) {
		position = new Vector2(x, y);
		this.size = size;
		this.number = number;
	}
	
	public void onTouched() {
		
	}
	
	public float getX() {
		return this.position.x;
	}
	
	public float getY() {
		return this.position.y;
	}
	
	public int getSize() {
		return this.size;
	}
}
