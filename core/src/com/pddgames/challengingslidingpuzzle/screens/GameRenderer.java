package com.pddgames.challengingslidingpuzzle.screens;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.pddgames.challengingslidingpuzzle.gameworld.GameWorld;
import com.pddgames.challengingslidingpuzzle.objects.Block;

public class GameRenderer {
	
	private List<Block> blocks;
	private GameWorld gameWorld;
	
	private OrthographicCamera camera;
	private SpriteBatch batcher;
	private ShapeRenderer shapeRender;
	
	public GameRenderer(GameWorld gameWorld, int gameHeight) {
		this.gameWorld = gameWorld;
		this.blocks = gameWorld.getBlocks();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameScreen.GAME_WIDTH, gameHeight);
		
		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(camera.combined);
		
		shapeRender = new ShapeRenderer();
		shapeRender.setProjectionMatrix(camera.combined);
	}
	
	public void render() {
		// Fill the entire screen with black, to prevent potential flickering.
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		drawBlocks();
	}
	
	private void drawBlocks() {
		shapeRender.begin(ShapeType.Filled);
		shapeRender.setColor(1, 1, 1, 1);
		for(Block block : blocks) {
			drawRoundedBlock(block.getX(), block.getY(), block.getSize(), block.getSize(), 4);
		}
		shapeRender.end();
	}
	
	private void drawRoundedBlock(float x, float y, int width, int height, float radius) {
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
	
	public OrthographicCamera getCamera() {
		return camera;
	}

}
