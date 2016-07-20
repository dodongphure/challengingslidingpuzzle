package com.pddgames.challengingslidingpuzzle.gameworld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.pddgames.challengingslidingpuzzle.objects.Block;
import com.pddgames.challengingslidingpuzzle.screens.GameScreen;

public class GameWorld {
	
	private static final int BLOCKS_NUM_PER_ROW = 5; // This is also number of blocks per column.
	private static final int GAP_BETWEEN_BLOCKS = 5;

	private List<Block> blocks;
	
	public GameWorld() {
		initailzeBlocks();
	}
	
	private void initailzeBlocks() {
		blocks = new ArrayList<Block>();
		int blockSize = (GameScreen.GAME_WIDTH - ((BLOCKS_NUM_PER_ROW-1) * GAP_BETWEEN_BLOCKS)) / BLOCKS_NUM_PER_ROW;
		List<Integer> randomNumbers = getRandomNumbers();
		
		// Draw blocks from left to right, bottom up.
		int count = 0;
		for(int i=0; i < BLOCKS_NUM_PER_ROW; i++) {
			int xPos = i * (blockSize + GAP_BETWEEN_BLOCKS);
			for(int j=0; j < BLOCKS_NUM_PER_ROW; j++) {
				if(i == BLOCKS_NUM_PER_ROW-1 && j == 0) { // We do not draw the bottom-right block.
					continue;
				}
				int yPos = j * (blockSize + GAP_BETWEEN_BLOCKS);
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
	
	public List<Block> getBlocks() {
		return this.blocks;
	}
}
