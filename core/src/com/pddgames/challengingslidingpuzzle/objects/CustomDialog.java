package com.pddgames.challengingslidingpuzzle.objects;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.pddgames.challengingslidingpuzzle.helpers.AssetLoader;

/**
 * A custom of {@link com.badlogic.gdx.scenes.scene2d.ui.Dialog}
 * @author PhuDoDong
 *
 */
public class CustomDialog extends Dialog {
	
	private TextButton closeDialogButton;
	
	public enum Type {
		INFO,
		CONFIRM
	}
	
	public CustomDialog(Type type, Stage stage, String content) {
		super("", AssetLoader.skin, "dialog");
		
		// Close Dialog button
		closeDialogButton = new TextButton("", AssetLoader.skin) {
			@Override
			public float getPrefWidth() {
				return 30;
			}
			
			@Override
			public float getPrefHeight() {
				return 30;
			}
		};
		closeDialogButton.setDebug(true);
		closeDialogButton.setPosition(220, 100);
		closeDialogButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				hide();
				AssetLoader.buttonSound.play();
			}
		});
		this.getContentTable().addActor(closeDialogButton);
		
		// Set content
		this.getContentTable().setBackground(type == Type.INFO ? AssetLoader.infoDialogBackground : AssetLoader.confirmDialogBackground);
		this.getContentTable().padTop(-30);
		Label text = new Label(content, AssetLoader.skin.get("dialog", LabelStyle.class));
		text.setFontScale(.6f);
		text.setAlignment(Align.center);
		text.setDebug(true);
		this.text(text);
		
		// Main button
		this.button(createActionButton(), 0);
		
		// Second button
		if(type == Type.CONFIRM) {
			this.button(createActionButton(), 1);
		}
		this.getButtonTable().padTop(-50);
		this.getButtonTable().padLeft(-20);
		
		// Set showing
		this.show(stage, Actions.scaleBy(.1f, .1f, .05f));
		this.setPosition(Math.round((stage.getWidth() - this.getWidth()) / 2), Math.round((stage.getHeight() - this.getHeight()) / 2));
		
		setDebug(true);
	}
	
	@Override
	public void hide() {
		this.hide(Actions.scaleBy(-.05f, -.05f, .03f));
		closeDialogButton.addAction(Actions.removeActor());
	}

	@Override
	public float getPrefWidth() {
		return 260;
	}
	
	@Override
	public float getPrefHeight() {
		return 130;
	}
	
	private TextButton createActionButton() {
		TextButton button = new TextButton("", AssetLoader.skin) {
			@Override
			public float getPrefWidth() {
				return 50;
			}
		};
		button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				AssetLoader.buttonSound.play();
			}
		});
		return button;
	}
	
}
