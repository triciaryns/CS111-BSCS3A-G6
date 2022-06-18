package com.snatik.matches.fragments;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.snatik.matches.R;
import com.snatik.matches.common.Memory;
import com.snatik.matches.common.Shared;
import com.snatik.matches.events.ui.ThemeSelectedEvent;
import com.snatik.matches.themes.Theme;
import com.snatik.matches.themes.Themes;

import java.util.Locale;

public class ThemeSelectFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = LayoutInflater.from(Shared.context).inflate(R.layout.theme_select_fragment, container, false);
		View drink = view.findViewById(R.id.theme_drink_container);
		View fandv = view.findViewById(R.id.theme_fandv_container);
		View mands = view.findViewById(R.id.theme_mands_container);

		final Theme themeDrink = Themes.createDrinksTheme();
		setStars((ImageView) drink.findViewById(R.id.theme_drink), themeDrink, "drink");
		final Theme themeFandv = Themes.createFandVTheme();
		setStars((ImageView) fandv.findViewById(R.id.theme_fandv), themeFandv, "fandv");
		final Theme themeMands = Themes.createMandSTheme();
		setStars((ImageView) mands.findViewById(R.id.theme_mands), themeMands, "mands");

		drink.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Shared.eventBus.notify(new ThemeSelectedEvent(themeDrink));
			}
		});

		fandv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Shared.eventBus.notify(new ThemeSelectedEvent(themeFandv));
			}
		});

		mands.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Shared.eventBus.notify(new ThemeSelectedEvent(themeMands));
			}
		});


		animateShow(drink);
		animateShow(fandv);
		animateShow(mands);

		return view;
	}

	private void animateShow(View view) {
		ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 1f);
		ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.5f, 1f);
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.setDuration(300);
		animatorSet.playTogether(animatorScaleX, animatorScaleY);
		animatorSet.setInterpolator(new DecelerateInterpolator(2));
		view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		animatorSet.start();
	}

	private void setStars(ImageView imageView, Theme theme, String type) {
		int sum = 0;
		for (int difficulty = 1; difficulty <= 6; difficulty++) {
			sum += Memory.getHighStars(theme.id, difficulty);
		}
		int num = sum / 6;
		if (num != 0) {
			String drawableResourceName = String.format(Locale.US, type + "_theme_star_%d", num);
			int drawableResourceId = Shared.context.getResources().getIdentifier(drawableResourceName, "drawable", Shared.context.getPackageName());
			imageView.setImageResource(drawableResourceId);
		}
	}
}
