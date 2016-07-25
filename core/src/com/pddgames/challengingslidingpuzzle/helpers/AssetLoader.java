package com.pddgames.challengingslidingpuzzle.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class AssetLoader {
	
	private static final String FONT_PATH = "data/font.fnt";
	
	public static BitmapFont font;
	
	public static void load() {
		font = new BitmapFont(Gdx.files.internal(FONT_PATH));
		font.setColor(0, 0, 0, 1);
		//font.getData().setScale(0.5f);
	}
	
	public static void dispose() {
		font.dispose();
	}

}
