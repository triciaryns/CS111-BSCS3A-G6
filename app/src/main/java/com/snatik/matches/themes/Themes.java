package com.snatik.matches.themes;

import android.graphics.Bitmap;

import com.snatik.matches.common.Shared;
import com.snatik.matches.utils.Utils;

import java.util.ArrayList;

public class Themes {

	public static String URI_DRAWABLE = "drawable://";

	public static Theme createDrinksTheme() {
		Theme theme = new Theme();
		theme.id = 1;
		theme.name = "Drinks";
		theme.backgroundImageUrl = URI_DRAWABLE + "drink_section";
		theme.tileImageUrls = new ArrayList<String>();
		// 40 drawables
		for (int i = 1; i <= 28; i++) {
			theme.tileImageUrls.add(URI_DRAWABLE + String.format("drink_%d", i));
		}
		return theme;
	}

	public static Theme createFandVTheme() {
		Theme theme = new Theme();
		theme.id = 2;
		theme.name = "FandV";
		theme.backgroundImageUrl = URI_DRAWABLE + "fandv_section";
		theme.tileImageUrls = new ArrayList<String>();
		// 40 drawables
		for (int i = 1; i <= 40; i++) {
			theme.tileImageUrls.add(URI_DRAWABLE + String.format("fandv_%d", i));
		}
		return theme;
	}

	public static Theme createMandSTheme() {
		Theme theme = new Theme();
		theme.id = 3;
		theme.name = "MandS";
		theme.backgroundImageUrl = URI_DRAWABLE + "mands_section";
		theme.tileImageUrls = new ArrayList<String>();

		for (int i = 1; i <= 40; i++) {
			theme.tileImageUrls.add(URI_DRAWABLE + String.format("mands_%d", i));
		}
		return theme;
	}
	
	public static Bitmap getBackgroundImage(Theme theme) {
		String drawableResourceName = theme.backgroundImageUrl.substring(Themes.URI_DRAWABLE.length());
		int drawableResourceId = Shared.context.getResources().getIdentifier(drawableResourceName, "drawable", Shared.context.getPackageName());
		Bitmap bitmap = Utils.scaleDown(drawableResourceId, Utils.screenWidth(), Utils.screenHeight());
		return bitmap;
	}

}
