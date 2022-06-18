package com.snatik.matches.utils;

import android.util.Log;

public class Clock {
	private static PauseTimer mPauseTimer = null;
	private static Clock mInstance = null;

	private Clock() {
		Log.i("my_tag", "NEW INSTANCE OF CLOCK");
	}

	public static class PauseTimer extends CountDownClock {
		private OnTimerCount mOnTimerCount = null;

		public PauseTimer(long millisOnTimer, long countDownInterval, boolean runAtStart, OnTimerCount onTimerCount) {
			super(millisOnTimer, countDownInterval, runAtStart);
			mOnTimerCount = onTimerCount;
		}

		@Override
		public void onTick(long millisUntilFinished) {
			if (mOnTimerCount != null) {
				mOnTimerCount.onTick(millisUntilFinished);
			}
		}

		@Override
		public void onFinish() {
			if (mOnTimerCount != null) {
				mOnTimerCount.onFinish();
			}
		}

	}

	public static Clock getInstance() {
		if (mInstance == null) {
			mInstance = new Clock();
		}
		return mInstance;
	}

	/**
	 * This will Start timer
	 * @param millisOnTimer
	 * @param countDownInterval
	 */
	public void startTimer(long millisOnTimer, long countDownInterval, OnTimerCount onTimerCount) {
		if (mPauseTimer != null) {
			mPauseTimer.cancel();
		}
		mPauseTimer = new PauseTimer(millisOnTimer, countDownInterval, true, onTimerCount);
		mPauseTimer.create();
	}


	public void pause() {				// pauses the game.
		if (mPauseTimer != null) {
			mPauseTimer.pause();
		}
	}


	public void resume() {				// resume the game
		if (mPauseTimer != null) {
			mPauseTimer.resume();
		}
	}


	public void cancel() {
		if (mPauseTimer != null) {
			mPauseTimer.mOnTimerCount = null;
			mPauseTimer.cancel();
		}
	}

	public long getPassedTime() {
		return mPauseTimer.timePassed();
	}

	public interface OnTimerCount {
		public void onTick(long millisUntilFinished);

		public void onFinish();
	}

}
