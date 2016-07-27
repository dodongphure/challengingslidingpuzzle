package com.pddgames.challengingslidingpuzzle.objects;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 
 * @author dodongphu
 *
 */
public class RecordingData {
	
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
