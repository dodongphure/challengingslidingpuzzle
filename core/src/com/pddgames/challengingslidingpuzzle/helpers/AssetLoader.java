package com.pddgames.challengingslidingpuzzle.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
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
	private static final String ATLAS = "ui/atlas.pack";
	private static final String BACKGROUND_IMAGE = "img/white_brick_wall.png";
	private static final String BLOCK_BACKGROUND = "img/block.png";
	private static final String RECORDING_DATA_PANEL = "img/panel.png";
	private static final String INFO_DIALOG = "img/dialog1.png";
	private static final String CONFIRM_DIALOG = "img/dialog2.png";
	
	private static final String ADAM_WARREN_PRO_FONT = "font/adamwarrenpro.ttf";
	private static final String MOTION_CONTROL_FONT = "font/MotionControl-Bold.otf";
	
	private static final String PANEL_FONT = "panel-font";
	private static final String MENU_FONT = "menu-font";
	
	public static Skin skin;
	public static Image background;
	public static Image blockBackground;
	public static TextureRegionDrawable recordingDataPanel;
	public static TextureRegionDrawable infoDialogBackground;
	public static TextureRegionDrawable confirmDialogBackground;
	
	public static void load() {
		//font.getData().setScale(0.5f);
		skin = new Skin();
		
		// add needed fonts for skin.
		skin.add(PANEL_FONT, createFont(ADAM_WARREN_PRO_FONT), BitmapFont.class);
		skin.add(MENU_FONT, createFont(MOTION_CONTROL_FONT), BitmapFont.class);
		
		skin.addRegions(new TextureAtlas(ATLAS));
		skin.load(Gdx.files.internal(SKIN_PATH));
		
		Texture imgTexture = new Texture(Gdx.files.internal(BACKGROUND_IMAGE));
		imgTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		TextureRegion imgTextureRegion = new TextureRegion(imgTexture);
        imgTextureRegion.setRegion(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		background = new Image(new TextureRegionDrawable(imgTextureRegion));
		
		imgTexture = new Texture(Gdx.files.internal(BLOCK_BACKGROUND));
		blockBackground = new Image(imgTexture);
		
		imgTexture = new Texture(Gdx.files.internal(RECORDING_DATA_PANEL));
		recordingDataPanel = new TextureRegionDrawable(new TextureRegion(imgTexture));
		
		imgTexture = new Texture(Gdx.files.internal(INFO_DIALOG));
		infoDialogBackground = new TextureRegionDrawable(new TextureRegion(imgTexture));
		
		imgTexture = new Texture(Gdx.files.internal(CONFIRM_DIALOG));
		confirmDialogBackground = new TextureRegionDrawable(new TextureRegion(imgTexture));
	}
	
	private static BitmapFont createFont(String path) {
		FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(path));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 38;
		BitmapFont font = fontGenerator.generateFont(parameter); // font size 12 pixels
		fontGenerator.dispose(); // don't forget to dispose to avoid memory leaks!
		return font;
	}
	
	public static void dispose() {
		skin.dispose();
	}

}
