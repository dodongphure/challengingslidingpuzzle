package com.pddgames.challengingslidingpuzzle.objects;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.pddgames.challengingslidingpuzzle.helpers.AssetLoader;

/**
 * 
 * @author dodongphu
 *
 */
public class RecordingData extends Table {
	
	private class TimeScheduler extends TimerTask {
		
		private int second;
		private int minute;
		private boolean isPause = false;

		@Override
		public void run() {
			if(isPause) {
				return;
			}
			
			this.second++;
			if(this.second == 60) {
				this.second = 0;
				this.minute++;
			}
		}
		
		public String getSecond() {
			return this.second < 10 ? "0" + this.second : this.second + "";
		}
		
		public String getMinute() {
			return this.minute < 10 ? "0" + this.minute : this.minute + "";
		}
		
	}
	
	private static final long MILISECONDS_PERIOD = 1000;
	private static final int PREF_WIDTH = 300;
	private static final int PREF_HEIGHT = 50;
	
	private static Timer timer;
	private static TimeScheduler timerTask;
	private int movingCount;
	private Label timeLabel;
	private Label moveLabel;
	private Image background;
	
	private static final String TIME = "Time: ";
	private static final String MOVE = "Move: ";
	
	public RecordingData() {
		background = AssetLoader.recordingDataPanel;
		
		timeLabel = new Label(TIME, AssetLoader.skin);
		add(timeLabel);
		moveLabel = new Label(MOVE, AssetLoader.skin);
		add(moveLabel);
		
	}
	
	public void start() {
		timer = new Timer();
		timerTask = new TimeScheduler();
		timer.scheduleAtFixedRate(timerTask, MILISECONDS_PERIOD, MILISECONDS_PERIOD);
		movingCount = 0;
	}
	
	public void pause() {
		timerTask.isPause = true;
	}
	
	public void resume() {
		timerTask.isPause = false;
	}
	
	public void resetData() {
		timerTask.isPause = false;
		timerTask.minute = 0;
		timerTask.second = 0;
		movingCount = 0;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		background.setBounds(getX(), getY(), getWidth(), getHeight());
		background.draw(batch, parentAlpha);
		
		timeLabel.setText(TIME + timerTask.getMinute() + ":" + timerTask.getSecond());
		moveLabel.setText(MOVE + movingCount);
		super.draw(batch, parentAlpha);
	}

	@Override
	public float getPrefWidth() {
		return PREF_WIDTH;
	}

	@Override
	public float getPrefHeight() {
		return PREF_HEIGHT;
	}

	public void inscreaseMovingCount() {
		movingCount++;
	}
	
	public String getSecond() {
		return timerTask.getSecond();
	}
	
	public String getMinute() {
		return timerTask.getMinute();
	}
	
	public int getMovingCount() {
		return movingCount;
	}
}
