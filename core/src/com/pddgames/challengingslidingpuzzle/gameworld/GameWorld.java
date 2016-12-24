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
import com.pddgames.challengingslidingpuzzle.helpers.AssetLoader;
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
	
	private static final int GAP_BETWEEN_BLOCKS = 5;
	private static final float BLOCK_MOVING_TIME = .2f;
	private static final float EFFECT_TIME = .1f;
	
	private Block emptyBlock;
	@SuppressWarnings("rawtypes")
	private Array<Cell> cells;
	private RecordingData recordingData;
	
	public static final int BLOCKS_NUM_PER_ROW = 5; // This is also number of blocks per column.
	
	public GameWorld(RecordingData recordingData) {
		this.recordingData = recordingData;
		initailzeBlocks();
	}
	
	private void initailzeBlocks() {
		float blockSize = (Gdx.graphics.getWidth() - ((BLOCKS_NUM_PER_ROW-1) * GAP_BETWEEN_BLOCKS)) / BLOCKS_NUM_PER_ROW;
		List<Integer> randomNumbers = getRandomNumbers();
		
		// Draw blocks up - bottom, from left to right.
		int numberIndex = 0;// Index for getting numbers for blocks from RandomNumbers.
		for(int i=0; i < BLOCKS_NUM_PER_ROW; i++) {
			for(int j=0; j < BLOCKS_NUM_PER_ROW; j++) {
				int index = i*BLOCKS_NUM_PER_ROW + j;
				Cell<Block> blockCell;
				Block block;
				
				if (i == BLOCKS_NUM_PER_ROW-1 && j == BLOCKS_NUM_PER_ROW-1 && !AssetLoader.hasSavedGame()) {
					// This is an empty block. Default position (when start new game) is at the right bottom.
					block = new Block(index + 1, blockSize, -1);
					emptyBlock = block;
				} else {
					// Try to get saved Block number if it exists.
					int blockNumer = AssetLoader.prefs.getInteger("block" + (index+1), randomNumbers.get(numberIndex));
					block = new Block(index + 1, blockSize, blockNumer);
					if(blockNumer == -1) {// If there is an old game data is saved, load empty block from saved file.
						emptyBlock = block;
					} else {
						numberIndex++;
					}
				}
				
				block.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						float distance = blockSize + GAP_BETWEEN_BLOCKS;
						if(Math.round(block.getX()) == Math.round(emptyBlock.getX())) {
							if(Math.round((block.getY() + distance)) == Math.round(emptyBlock.getY())) {// Move up.
								emptyBlock.setPosition(block.getX(), block.getY());
								block.addAction(Actions.moveBy(0, distance, BLOCK_MOVING_TIME));
								swapOrderNumber(emptyBlock, block);
								recordingData.inscreaseMovingCount();
							} else if(Math.round((block.getY() - distance)) == Math.round(emptyBlock.getY())) {//Move down.
								emptyBlock.setPosition(block.getX(), block.getY());
								block.addAction(Actions.moveBy(0, -distance, BLOCK_MOVING_TIME));
								swapOrderNumber(emptyBlock, block);
								recordingData.inscreaseMovingCount();
							}
						} else if(Math.round(block.getY()) == Math.round(emptyBlock.getY())) {
							if(Math.round((block.getX() + distance))== Math.round(emptyBlock.getX())) {// Move right.
								emptyBlock.setPosition(block.getX(), block.getY());
								block.addAction(Actions.moveBy(distance, 0, BLOCK_MOVING_TIME));
								swapOrderNumber(emptyBlock, block);
								recordingData.inscreaseMovingCount();
							} else if(Math.round((block.getX() - distance)) == Math.round(emptyBlock.getX())) {// Move left.
								emptyBlock.setPosition(block.getX(), block.getY());
								block.addAction(Actions.moveBy(-distance, 0, BLOCK_MOVING_TIME));
								swapOrderNumber(emptyBlock, block);
								recordingData.inscreaseMovingCount();
							}
						}
						
						// Check for finishing game.
						if(checkFinishGame()) {
							//TODO
						}
					}
				});
				blockCell = add(block);
				
				// add spaces between blocks.
				if(i < BLOCKS_NUM_PER_ROW - 1) {
					blockCell.padBottom(GAP_BETWEEN_BLOCKS);
				}
				if(j < BLOCKS_NUM_PER_ROW - 1) {
					blockCell.padRight(GAP_BETWEEN_BLOCKS);
				}
			}
			row();
		}
		cells = getCells();
	}
	
	private void swapOrderNumber(Block blockA, Block blockB) {
		int orderNumberA = blockA.getOrderNumber();
		blockA.setOrderNumber(blockB.getOrderNumber());
		blockB.setOrderNumber(orderNumberA);
	}
	
	/**
	 * Get list of random numbers for all blocks. There are all 24 numbers.
	 * @return
	 */
	private List<Integer> getRandomNumbers() {
		 List<Integer> numbers = IntStream.rangeClosed(1, BLOCKS_NUM_PER_ROW * BLOCKS_NUM_PER_ROW - 1)
				 .boxed().collect(Collectors.toList());
		 Collections.shuffle(numbers);
		 return numbers;
	}
	
	private boolean checkFinishGame() {
		for(int i=0; i<cells.size-1; i++) {
			Block block = (Block) cells.get(i).getActor();
			if(block.getNumber() != block.getOrderNumber()) {
				return false;
			}
		}
		return true;
	}
	
	public void resetData() {
		List<Integer> randomNumbers = getRandomNumbers();
		int count = 0;
		for (int i=0; i<cells.size; i++) {
			Block block = (Block) cells.get(i).getActor();
			if(block.getOrderNumber() == BLOCKS_NUM_PER_ROW * BLOCKS_NUM_PER_ROW) {// The last block.
				block.setNumber(-1);// This is the empty block.
				emptyBlock = block;
			} else {
				block.setNumber(randomNumbers.get(count));
				count++;
			}
		}
	}
	
	public void saveGameData() {
		AssetLoader.prefs.putInteger("second", Integer.valueOf(this.recordingData.getSecond()));
		AssetLoader.prefs.putInteger("minute", Integer.valueOf(this.recordingData.getMinute()));
		AssetLoader.prefs.putInteger("movingCount", this.recordingData.getMovingCount());
		for(int i=0; i<cells.size; i++) {
			Block block = (Block) cells.get(i).getActor();
			AssetLoader.prefs.putInteger("block"+block.getOrderNumber(), block.getNumber());
		}
		AssetLoader.prefs.flush();
	}
	
	/**
	 	0 -> 21
		1 -> 16 22
		2 -> 11 17 23
		3 -> 6 12 18 24
		4 -> 1 7 13 19 25
		5 -> 2 8 14 20
		6 -> 3 9 15
		7 -> 4 10
		8 -> 5
	 */
	public void runEffect() {
		int startIndex = BLOCKS_NUM_PER_ROW * (BLOCKS_NUM_PER_ROW-1) + 1;
		for (int i=0; i<BLOCKS_NUM_PER_ROW * 2 - 1; i++) {
			int runIndex = i < BLOCKS_NUM_PER_ROW ? startIndex + i : BLOCKS_NUM_PER_ROW * ( BLOCKS_NUM_PER_ROW - (i % BLOCKS_NUM_PER_ROW + 1) );
			int runCount = i < BLOCKS_NUM_PER_ROW ? i + 1 : BLOCKS_NUM_PER_ROW - (i % BLOCKS_NUM_PER_ROW + 1);
			while (runIndex > 0 && runCount > 0) {
				addEffect(runIndex, i*EFFECT_TIME);
				runIndex = runIndex - BLOCKS_NUM_PER_ROW -1;
				runCount--;
			}
		}
	}
	
	private void addEffect(int orderNumber, float delay) {
		for (int i=0; i<cells.size; i++) {
			Block block = (Block) cells.get(i).getActor();
			if(block.getOrderNumber() == orderNumber) {
				block.addAction(Actions.sequence(Actions.delay(delay), Actions.sizeBy(1, 1, EFFECT_TIME), Actions.sizeBy(-1, -1, EFFECT_TIME)));
			}
		}
	}
	
}
