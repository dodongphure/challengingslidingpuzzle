package com.pddgames.challengingslidingpuzzle.gameworld;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pddgames.challengingslidingpuzzle.objects.Block;
import com.pddgames.challengingslidingpuzzle.objects.RecordingData;
import com.pddgames.challengingslidingpuzzle.screens.GameScreen;

/**
 * 
 * @author PhuDoDong
 * 
 * Provide all needed objects for playing game.
 *
 */
public class GameWorld extends Table {
	
	private static final int BLOCKS_NUM_PER_ROW = 5; // This is also number of blocks per column.
	private static final int GAP_BETWEEN_BLOCKS = 5;
	private static final int GAME_TIME_LIMIT = 60; // in minute.
	
	//private List<Block> blocks;
	private float blockSize;
	private static RecordingData recordingData;
	
	private Vector2 emptyBlockPosition;
	
	public GameWorld() {
		
		setTransform(false);
		
		initailzeBlocks();
		recordingData = new RecordingData();
		recordingData.start();
	}
	
	private void initailzeBlocks() {
		//blocks = new ArrayList<Block>();
		blockSize = (GameScreen.GAME_WIDTH - ((BLOCKS_NUM_PER_ROW-1) * GAP_BETWEEN_BLOCKS)) / BLOCKS_NUM_PER_ROW;
		List<Integer> randomNumbers = getRandomNumbers();
		
		// Draw blocks bottom up, from left to right.
		int count = 0;
		for(int i=0; i < BLOCKS_NUM_PER_ROW; i++) {
			for(int j=0; j < BLOCKS_NUM_PER_ROW; j++) {
				// We do not draw the bottom-right block.
				if(i == BLOCKS_NUM_PER_ROW-1 && j == BLOCKS_NUM_PER_ROW-1) {
					continue;
				}
				Block block = new Block(blockSize, randomNumbers.get(count));
				block.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						System.out.println(block.getNumber());
					}
				});
				Cell<Block> blockCell = add(block);
				// add spaces between blocks.
				if(i < BLOCKS_NUM_PER_ROW - 1) {
					blockCell.padBottom(GAP_BETWEEN_BLOCKS);
				}
				if(j < BLOCKS_NUM_PER_ROW - 1) {
					blockCell.padRight(GAP_BETWEEN_BLOCKS);
				}
				count++;
			}
			row();
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
	
	/*public void update(OrthographicCamera camera, float delta) {
		// Check whether a block is moving
		boolean isBlockMoving = false;
		for(Block block : blocks) {
			isBlockMoving |= block.update(delta);
		}
		
		if (Gdx.input.justTouched()) {
			Vector3 touchPoint = new Vector3();
			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			for(Block block : blocks) {
				float xPosition = block.getX();
				float yPosition = block.getY();
				if(!isBlockMoving && isBlockMovable(xPosition, yPosition) && block.isTouched(touchPoint.x, touchPoint.y)) {
					block.setNewPosition(emptyBlockPosition);
					emptyBlockPosition.set(xPosition, yPosition);
					recordingData.inscreaseMovingCount();
					break;
				}
			}
		}
		
		if(Integer.valueOf(recordingData.getMinute()) >= GAME_TIME_LIMIT) {
			//TODO: return to the main menu.
		}
		
	}*/
	
	public void draw(SpriteBatch batch, float parentAlpha) {
		
	}
	
	private boolean isBlockMovable(float x, float y) {
		float distance = blockSize + GAP_BETWEEN_BLOCKS;
		return (emptyBlockPosition.x == x && (emptyBlockPosition.y == y - distance || emptyBlockPosition.y == y + distance))
			|| (emptyBlockPosition.y == y && (emptyBlockPosition.x == x - distance || emptyBlockPosition.x == x + distance));
	}
	
	/*public List<Block> getBlocks() {
		return this.blocks;
	}*/
	
	public static RecordingData getRecordingData() {
		return recordingData;
	}
}
