package com.snatik.matches.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.snatik.matches.R;
import com.snatik.matches.common.Music;
import com.snatik.matches.common.Shared;
import com.snatik.matches.events.ui.StartEvent;
import com.snatik.matches.ui.PopupManager;
import com.snatik.matches.utils.Utils;

public class MenuFragment extends Fragment {

	private ImageView mTitleEdition;
	private ImageView mPlayGameButton;
	private ImageView mStartButtonLights;
	private ImageView mStartTip;
	private ImageView mSettingsButton;
	private ImageView mGooglePlayButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.menu_fragment, container, false);
		mTitleEdition = (ImageView) view.findViewById(R.id.title);
		mPlayGameButton = (ImageView) view.findViewById(R.id.start_game_button);
		mSettingsButton = (ImageView) view.findViewById(R.id.settings_game_button);
		mSettingsButton.setSoundEffectsEnabled(false);
		mSettingsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PopupManager.showPopupSettings();
			}
		});
		mGooglePlayButton = (ImageView) view.findViewById(R.id.google_play_button);
		mGooglePlayButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Leaderboards will be available in the next game updates", Toast.LENGTH_LONG).show();
			}
		});
		mStartButtonLights = (ImageView) view.findViewById(R.id.start_game_button_lights);
		mStartTip = (ImageView) view.findViewById(R.id.tooltip);
		mPlayGameButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {


				animateAllAssetsOff(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						Shared.eventBus.notify(new StartEvent());
					}
				});
			}
		});

		startLightsAnimation();
		starttipAnimation();

		// play background music
		Music.playBackgroundMusic();
		return view;
	}

	protected void animateAllAssetsOff(AnimatorListenerAdapter adapter) {
		// title

		ObjectAnimator titleAnimator = ObjectAnimator.ofFloat(mTitleEdition, "translationY", Utils.px(-200));
		titleAnimator.setInterpolator(new AccelerateInterpolator(2));
		titleAnimator.setDuration(300);

		// lights
		ObjectAnimator lightsAnimatorX = ObjectAnimator.ofFloat(mStartButtonLights, "scaleX", 0f);
		ObjectAnimator lightsAnimatorY = ObjectAnimator.ofFloat(mStartButtonLights, "scaleY", 0f);

		// tooltip
		ObjectAnimator tooltipAnimator = ObjectAnimator.ofFloat(mStartTip, "alpha", 0f);
		tooltipAnimator.setDuration(100);

		// settings button
		ObjectAnimator settingsAnimator = ObjectAnimator.ofFloat(mSettingsButton, "translationY", Utils.px(120));
		settingsAnimator.setInterpolator(new AccelerateInterpolator(2));
		settingsAnimator.setDuration(300);

		// google play button
		ObjectAnimator googlePlayAnimator = ObjectAnimator.ofFloat(mGooglePlayButton, "translationY", Utils.px(120));
		googlePlayAnimator.setInterpolator(new AccelerateInterpolator(2));
		googlePlayAnimator.setDuration(300);

		// start button
		ObjectAnimator startButtonAnimator = ObjectAnimator.ofFloat(mPlayGameButton, "translationY", Utils.px(130));
		startButtonAnimator.setInterpolator(new AccelerateInterpolator(2));
		startButtonAnimator.setDuration(300);

		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.playTogether(titleAnimator, lightsAnimatorX, lightsAnimatorY, tooltipAnimator, settingsAnimator, googlePlayAnimator, startButtonAnimator);
		animatorSet.addListener(adapter);
		animatorSet.start();
	}

	private void starttipAnimation() {
		ObjectAnimator scaleY = ObjectAnimator.ofFloat(mStartTip, "scaleY", 0.8f);
		scaleY.setDuration(200);
		ObjectAnimator scaleYBack = ObjectAnimator.ofFloat(mStartTip, "scaleY", 1f);
		scaleYBack.setDuration(500);
		scaleYBack.setInterpolator(new BounceInterpolator());
		final AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.setStartDelay(1000);
		animatorSet.playSequentially(scaleY, scaleYBack);
		animatorSet.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				animatorSet.setStartDelay(2000);
				animatorSet.start();
			}
		});
		mStartTip.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		animatorSet.start();
	}

	private void startLightsAnimation() {
		ObjectAnimator animator = ObjectAnimator.ofFloat(mStartButtonLights, "rotation", 0f, 360f);
		animator.setInterpolator(new AccelerateDecelerateInterpolator());
		animator.setDuration(6000);
		animator.setRepeatCount(ValueAnimator.INFINITE);
		mStartButtonLights.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		animator.start();
	}

}
