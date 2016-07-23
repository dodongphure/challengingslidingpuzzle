package com.pddgames.challengingslidingpuzzle.gameworld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.pddgames.challengingslidingpuzzle.objects.Block;
import com.pddgames.challengingslidingpuzzle.screens.GameScreen;

public class GameWorld {
	
	private static final int BLOCKS_NUM_PER_ROW = 5; // This is also number of blocks per column.
	private static final int GAP_BETWEEN_BLOCKS = 5;
	
	private List<Block> blocks;
	private int blockSize;
	
	private Vector2 emptyBlockPosition;
	
	public GameWorld() {
		initailzeBlocks();
	}
	
	private void initailzeBlocks() {
		blocks = new ArrayList<Block>();
		blockSize = (GameScreen.GAME_WIDTH - ((BLOCKS_NUM_PER_ROW-1) * GAP_BETWEEN_BLOCKS)) / BLOCKS_NUM_PER_ROW;
		List<Integer> randomNumbers = getRandomNumbers();
		
		// Draw blocks bottom up, from left to right.
		int count = 0;
		int distance = blockSize + GAP_BETWEEN_BLOCKS;
		for(int i=0; i < BLOCKS_NUM_PER_ROW; i++) {
			int xPos = i * distance;
			for(int j=0; j < BLOCKS_NUM_PER_ROW; j++) {
				int yPos = j * distance;
				
				// We do not draw the bottom-right block.
				if(i == BLOCKS_NUM_PER_ROW-1 && j==0) {
					emptyBlockPosition = new Vector2(xPos, yPos);
					continue;
				}
				Block block = new Block( xPos, yPos, blockSize, randomNumbers.get(count) );
				blocks.add(block);
				count++;
			}
		}
	}
	
	/**
	 * Get random numbers for all blocks
	 * @return
	 */
	private List<Integer> getRandomNumbers() {
		 List<Integer> numbers = IntStream.rangeClosed(1, BLOCKS_NUM_PER_ROW * BLOCKS_NUM_PER_ROW - 1)
				 .boxed().collect(Collectors.toList());
		 Collections.shuffle(numbers);
		 return numbers;
	}
	
	public void update(OrthographicCamera camera, float delta) {
		if (Gdx.input.justTouched()) {
			Vector3 touchPoint = new Vector3();
			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			for(Block block : blocks) {
				float xPosition = block.getX();
				float yPosition = block.getY();
				if(isBlockMovable(xPosition, yPosition) && block.isTouched(touchPoint.x, touchPoint.y)) {
					block.setNewPosition(emptyBlockPosition);
					emptyBlockPosition.set(xPosition, yPosition);
				}
			}
		}
		
		for(Block block : blocks) {
			block.update(delta);
		}
	}
	
	private boolean isBlockMovable(float x, float y) {
		int distance = blockSize + GAP_BETWEEN_BLOCKS;
		return (emptyBlockPosition.x == x && (emptyBlockPosition.y == y - distance || emptyBlockPosition.y == y + distance))
			|| (emptyBlockPosition.y == y && (emptyBlockPosition.x == x - distance || emptyBlockPosition.x == x + distance));
	}
	
	public List<Block> getBlocks() {
		return this.blocks;
	}
}
