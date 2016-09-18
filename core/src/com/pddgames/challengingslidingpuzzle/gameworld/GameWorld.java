package com.pddgames.challengingslidingpuzzle.gameworld;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.pddgames.challengingslidingpuzzle.objects.Block;
import com.pddgames.challengingslidingpuzzle.objects.RecordingData;

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
	private static final float BLOCK_MOVING_TIME = .2f;
	private static final int GAME_TIME_LIMIT = 60; // in minute.
	
	private Block emptyBlock;
	@SuppressWarnings("rawtypes")
	private Array<Cell> cells;
	private RecordingData recordingData;
	
	public GameWorld(RecordingData recordingData) {
		this.recordingData = recordingData;
		initailzeBlocks();
	}
	
	private void initailzeBlocks() {
		float blockSize = (Gdx.graphics.getWidth() - ((BLOCKS_NUM_PER_ROW-1) * GAP_BETWEEN_BLOCKS)) / BLOCKS_NUM_PER_ROW;
		List<Integer> randomNumbers = getRandomNumbers();
		
		// Draw blocks bottom up, from left to right.
		int count = 0;
		for(int i=0; i < BLOCKS_NUM_PER_ROW; i++) {
			for(int j=0; j < BLOCKS_NUM_PER_ROW; j++) {
				Cell<Block> blockCell;
				
				if(i == BLOCKS_NUM_PER_ROW-1 && j == BLOCKS_NUM_PER_ROW-1) {
					// We do not draw the bottom-right block.
					emptyBlock = new Block(blockSize, -1);
					blockCell = add(emptyBlock);
				} else {
					Block block = new Block(blockSize, randomNumbers.get(count));
					block.addListener(new ClickListener() {
						@Override
						public void clicked(InputEvent event, float x, float y) {
							float distance = blockSize + GAP_BETWEEN_BLOCKS;
							if(Math.round(block.getX()) == Math.round(emptyBlock.getX())) {
								if(Math.round((block.getY() + distance)) == Math.round(emptyBlock.getY())) {// Move up.
									emptyBlock.setPosition(block.getX(), block.getY());
									block.addAction(Actions.moveBy(0, distance, BLOCK_MOVING_TIME));
									recordingData.inscreaseMovingCount();
								} else if(Math.round((block.getY() - distance)) == Math.round(emptyBlock.getY())) {//Move down.
									emptyBlock.setPosition(block.getX(), block.getY());
									block.addAction(Actions.moveBy(0, -distance, BLOCK_MOVING_TIME));
									recordingData.inscreaseMovingCount();
								}
							} else if(Math.round(block.getY()) == Math.round(emptyBlock.getY())) {
								if(Math.round((block.getX() + distance))== Math.round(emptyBlock.getX())) {// Move right.
									emptyBlock.setPosition(block.getX(), block.getY());
									block.addAction(Actions.moveBy(distance, 0, BLOCK_MOVING_TIME));
									recordingData.inscreaseMovingCount();
								} else if(Math.round((block.getX() - distance)) == Math.round(emptyBlock.getX())) {// Move left.
									emptyBlock.setPosition(block.getX(), block.getY());
									block.addAction(Actions.moveBy(-distance, 0, BLOCK_MOVING_TIME));
									recordingData.inscreaseMovingCount();
								}
							}
							
							// Check for finishing game.
							for(int i=0; i<cells.size-1; i++) {
								if(((Block) cells.get(i).getActor()).getNumber() == i+1) {
									// TODO
								}
							}
						}
					});
					blockCell = add(block);
				}
				
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
		cells = getCells();
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
	
	public void resetData() {
		List<Integer> randomNumbers = getRandomNumbers();
		for (int i=0; i<cells.size - 1; i++) {
			((Block) cells.get(i).getActor()).setNumber(randomNumbers.get(i));
		}
		// The last block is an empty block.
		((Block) cells.get(cells.size - 1).getActor()).setNumber(-1);
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

}
