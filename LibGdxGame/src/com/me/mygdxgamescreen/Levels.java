package com.me.mygdxgamescreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Levels implements Screen {

	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private List list;
	private ScrollPane scroll;
	private TextButton play, back;
	
	@Override
	public void render(float delta) {
		// Pintamos pantalla que sobre
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act();
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, false);
		
	}

	@Override
	public void show() {
		
		//Creamos escenario
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		atlas = new TextureAtlas(Gdx.files.internal("ui/atlas1.pack"));
		skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), atlas);
				
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		table.debug();
		
		list = new List(new String[] {"Uno", "Dos", "Tres", "Uno", "Dos", "Tressadsadsadasdsa", "Uno", "Dos", "Tres", "Uno", "Dos", "Tres", "Uno", "Dos", "Tres", "Uno", "Dos", "Tres"}, skin);
		
		play = new TextButton("Play", skin);
		play.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new GameTiledAndBox2D());
			}
		});
		play.pad(10);
		
		back = new TextButton("Back", skin, "small");
		
		back.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
		});
		back.pad(10);
		
		scroll = new ScrollPane(list, skin);
		
		//Agregamos actores a la tabla
		table.add().width(table.getWidth() / 3);
	
		//Se agrega un label a la tabla
		table.add("Niveles").width(table.getWidth() / 3).center();
		
		table.add().width(table.getWidth() / 3).row();
		table.add(scroll).left().expandY();
		table.add(play);
		table.add(back).bottom().right();
		
		
		stage.addActor(table);
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
	}

}
