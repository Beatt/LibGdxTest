package com.me.mygdxgamescreen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import javax.sound.midi.Sequence;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.me.mygdx.tween.ActorAccessor;

public class MainMenu implements Screen {
	
	//Creamos un escenario
	private Stage stage;
	
	private TextureAtlas atlas;
	
	private Skin skin;
	
	//Creamos una tabla
	private Table table;
	
	//Creamos botones de texto
	private TextButton buttonExit,
					   buttonPlay,
					   buttonSetting;
		
	private Label heading;
	
	private TweenManager tweenManager;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		stage.act(delta);
		stage.draw();
		
		tweenManager.update(delta);
		/*
		 * Muestra cuadricula de la tabla
			Table.drawDebug(stage);
		 */
		
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, false);
	}

	@Override
	public void show() {
		
		stage = new Stage();
		
		//Permitimos al escenario procesos de entrada.
		Gdx.input.setInputProcessor(stage);
		
		atlas = new TextureAtlas("ui/atlas1.pack");
		
		skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), atlas);
			
		//Creamos una tabla y en ella le agregamos nuestro Texture Atlas.
		table = new Table(skin);
		table.setFillParent(true);
		
		//Determinamos la medida de nuestra table. lo mismo que setViewport
		//table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		/*
		//Instanciamos el BitmapFont
		white = new BitmapFont(Gdx.files.internal("font/white64.fnt"), false);
		
		//Determinamos estilo para nuestro textbutton
		TextButtonStyle style = new TextButtonStyle();
		
		//Asignamos imagen
		style.up = skin.getDrawable("button.up");
		//style.down = skin.getDrawable("button.down");
		style.pressedOffsetX = 1;
		style.pressedOffsetY = -1;
		style.font = white; */
		
		//Determinamos estilo para nuestro heading
		heading = new Label(Splash.TITLE, skin);
		heading.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		heading.setFontScale(0.40f);
	
		
		//Instaciamos TextButton Exit
		buttonExit = new TextButton("Exit", skin);
		
		buttonExit.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
			
		});
		buttonExit.pad(10); 
		
		//Instaciamos TextButton Play
		buttonPlay = new TextButton("Play", skin);
		buttonPlay.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
	
				stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {

					@Override
					public void run() {
						((Game) Gdx.app.getApplicationListener()).setScreen(new Levels());
					}
				})));
				
			}
		});
		buttonPlay.pad(10); 
		
		buttonSetting = new TextButton("Setting", skin, "small");
		buttonSetting.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new Settings());
			}
		});
		buttonSetting.pad(10);
		
		//Agregamos el button a la tabla
		table.add(heading);
		table.getCell(heading).spaceBottom(80);
		table.row();
		table.add(buttonPlay);
		table.getCell(buttonPlay).spaceBottom(20);
		table.row();
		table.add(buttonSetting);
		table.getCell(buttonSetting).spaceBottom(20).row();
		table.add(buttonExit);
		
		//table.debug(); //Remueve todo
		
		//Agregamos al escenario la tabla
		stage.addActor(table);
		
		
		//Animacion para la clase Actor
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());
			
		// heading animación de color
		Timeline.createSequence().beginSequence()
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 0, 1))
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 1, 0))
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 0, 0))
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 1, 0))
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 0, 1))
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 1, 1))
				.end().repeat(Tween.INFINITY, 0).start(tweenManager);
		
		//fade in de botones y heading
		Timeline.createSequence().beginSequence()
			.push(Tween.set(buttonPlay, ActorAccessor.ALPHA).target(0))
			.push(Tween.set(buttonExit, ActorAccessor.ALPHA).target(0))
			.push(Tween.from(heading, ActorAccessor.ALPHA, 0.5f).target(0))
			.push(Tween.to(buttonPlay, ActorAccessor.ALPHA, .5f).target(1))
			.push(Tween.to(buttonExit, ActorAccessor.ALPHA, .5f).target(1))
			.end().start(tweenManager);
		
		//fade in table
		Tween.from(table, ActorAccessor.ALPHA, .4f).target(0).start(tweenManager);
		Tween.from(table, ActorAccessor.Y, .5f).target(Gdx.graphics.getHeight() / 8).start(tweenManager);
		
		tweenManager.update(Gdx.graphics.getDeltaTime());
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
		//Liberamos recursos
		stage.dispose();
		atlas.dispose();
		skin.dispose();
	}

}
