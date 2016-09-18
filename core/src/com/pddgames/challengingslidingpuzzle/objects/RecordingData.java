package com.pddgames.challengingslidingpuzzle.objects;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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

		@Override
		public void run() {
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
	
	private static Timer timer;
	private static TimeScheduler timerTask;
	private int movingCount;
	private Label timeLabel;
	private Label moveLabel;
	
	private static final String TIME = "Time: ";
	private static final String MOVE = "Move: ";
	
	public RecordingData() {
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
		timer.cancel();
	}
	
	public void resume() {
		timer = new Timer();
		timer.scheduleAtFixedRate(timerTask, MILISECONDS_PERIOD, MILISECONDS_PERIOD);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		timeLabel.setText(TIME + timerTask.getMinute() + ":" + timerTask.getSecond());
		moveLabel.setText(MOVE + movingCount);
		super.draw(batch, parentAlpha);
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
	
	public String getMovingCount() {
		return movingCount + "";
	}
}
