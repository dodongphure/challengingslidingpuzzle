package com.pddgames.challengingslidingpuzzle.objects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Block {
	
	private static final int MOVE_VELOCITY = 520;

	private Vector2 position;
	private Vector2 newPosition;
	private int size;
	
	public Block(float x, float y, int size, int number) {
		this.position = new Vector2(x, y);
		this.newPosition = new Vector2(x, y);
		this.size = size;
	}
	
	public boolean isTouched(float x, float y) {
		Rectangle bound = new Rectangle(position.x, position.y, size, size);
		return bound.contains(x, y);
	}
	
	/**
	 * 
	 * @param delta
	 * @return true if this Block is moving, else return false
	 */
	public boolean update(float delta) {
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
	}
	
	public float getX() {
		return this.position.x;
	}
	
	public void setX(float x) {
		this.position.x = x;
	}
	
	public float getY() {
		return this.position.y;
	}
	
	public void setY(float y) {
		this.position.y = y;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public void setNewPosition(Vector2 newPosition) {
		this.newPosition.set(newPosition);
	}
}
