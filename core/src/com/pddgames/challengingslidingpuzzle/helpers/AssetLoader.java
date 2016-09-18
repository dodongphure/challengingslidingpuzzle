package com.pddgames.challengingslidingpuzzle.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * 
 * @author PhuDoDong
 *
 */
public class AssetLoader {
	
	private static final String FONT_PATH = "data/font.fnt";
	private static final String SKIN_PATH = "ui/uiSkin.json";
	
	public static Skin skin;
	
	public static void load() {
		//font.getData().setScale(0.5f);
		skin = new Skin(Gdx.files.internal(SKIN_PATH));
	}
	
	public static void dispose() {
		skin.dispose();
	}

}
