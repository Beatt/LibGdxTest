package com.me.mygdxgamescreen;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;   
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.me.mygdx.tween.SpriteAccessor;


public class Splash implements Screen {
	
	//Versión y titulo de nuestro juego
	public static final String TITLE = "Mi primer juego con LibGdx",
			VERSION = "0.0.0.1";
	
	private Sprite sprite;
	private SpriteBatch batch;
	private TweenManager tweenManager;
	
	/*
	 * (Loop) Se ejecuta continuamente.
	 */
	@Override
	public void render(float delta) {
		
		//Para pintar la pantalla
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		sprite.draw(batch);
		batch.end();
		
		//Tiempo de frame por segundo
		tweenManager.update(delta);
	}
	
	/*
	 * (Se ejecuta despues de create) Basicamente su uso es 
	 * 	para asignar medidas del dispositivo.
	 */
	@Override
	public void resize(int width, int height) {
		sprite.setSize(width, height);
	}
	
	/*
	 * (Primer método que se lanza (similar al método create())
	 */
	@Override
	public void show() {
		
		Gdx.graphics.setVSync(Settings.vSync());
		
		batch = new SpriteBatch();
		
		//Creamos un manejador de tween
		tweenManager = new TweenManager();
		
		//Registramos un Tween accessor
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		
		//Crea una textura para ser agregada a un sprite 
		Texture texture = new Texture("img/pacman_sprite.png");
		sprite = new Sprite(texture);
		
		/*
		 * Determina medidas de la imagen y la coloca de forma
		 * 	adecuada en cualquier dispositivo. 
		 * 	
		 */
		sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		Tween.set(sprite, SpriteAccessor.ALPHA).target(0).start(tweenManager);
		
		/*
		 * Decremento o incremento del fading. 
		 * 	repeatYoyo susituye a las 2 líneas siguientes.
		 *  callBack , determinar cuando la animación haya finalizado
		 */
		Tween.to(sprite, SpriteAccessor.ALPHA, .5f).target(1).repeatYoyo(1, 2).setCallback(new TweenCallback() {
			
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				
				  //Lanzamos un Screen
				 ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
				
			}
		}).start(tweenManager);
		
		//Tween.to(sprite, SpriteAccessor.ALPHA, 2).target(1).start(tweenManager); 
		//Delay
		//Tween.to(sprite, SpriteAccessor.ALPHA, 2).target(0).delay(2).start(tweenManager);
		
		
		tweenManager.update(Float.MIN_VALUE);
		   
	}

	@Override
	public void hide() {
		dispose();
	}
	
	/*
	 * (Se ejecuta despues de finalizar el método render.
	 */
	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}
	

	/*
	 * (Ultimo) liberar recursos.
	 */
	@Override
	public void dispose() {
		batch.dispose();
		sprite.getTexture().dispose();
	}

}//Fin class Splash
