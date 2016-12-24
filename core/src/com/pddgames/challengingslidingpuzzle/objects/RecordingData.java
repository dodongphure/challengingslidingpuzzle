package com.pddgames.challengingslidingpuzzle.objects;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
		
		public TimeScheduler(int second, int minute) {
			this.second = second;
			this.minute = minute;
		}

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
	
	private static final String TIME = "Time: ";
	private static final String MOVE = "Move: ";
	
	public RecordingData() {
		setBackground(AssetLoader.recordingDataPanel);
		
		timeLabel = new Label(TIME, AssetLoader.skin.get("panel", LabelStyle.class));
		timeLabel.setFontScale(.4f);
		timeLabel.setLayoutEnabled(false);// make sure invalidateHierarchy() is not called when call setText().
		add(timeLabel).width(150);
		moveLabel = new Label(MOVE, AssetLoader.skin.get("panel", LabelStyle.class));
		moveLabel.setFontScale(.4f);
		moveLabel.setLayoutEnabled(false);
		add(moveLabel).width(70);
		
	}
	
	public void start() {
		timer = new Timer();
		timerTask = new TimeScheduler(AssetLoader.prefs.getInteger("second", 0), AssetLoader.prefs.getInteger("minute", 0));
		timer.scheduleAtFixedRate(timerTask, MILISECONDS_PERIOD, MILISECONDS_PERIOD);
		movingCount = AssetLoader.prefs.getInteger("movingCount", 0);
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
		timeLabel.setText(TIME + timerTask.getMinute() + ":" + timerTask.getSecond());
		timeLabel.layout();// draw new text.
		moveLabel.setText(MOVE + movingCount);
		moveLabel.layout();
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
