package com.pddgames.challengingslidingpuzzle.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * 
 * @author PhuDoDong
 *
 */
public class AssetLoader {
	
	private static final String SKIN_PATH = "ui/uiSkin.json";
	private static final String BACKGROUND_IMAGE = "img/white_brick_wall.png";
	
	public static Skin skin;
	public static Image background;
	
	public static void load() {
		//font.getData().setScale(0.5f);
		skin = new Skin(Gdx.files.internal(SKIN_PATH));
		
		Texture imgTexture = new Texture(Gdx.files.internal(BACKGROUND_IMAGE));
		imgTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		TextureRegion imgTextureRegion = new TextureRegion(imgTexture);
        imgTextureRegion.setRegion(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		background = new Image(new TextureRegionDrawable(imgTextureRegion));
	}
	
	public static void dispose() {
		skin.dispose();
	}

}
