package com.me.mygdxgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.me.mygdxgamescreen.MainMenu;
import com.me.mygdxgamescreen.Play;
import com.me.mygdxgamescreen.Splash;


public class MyGdxGame extends Game {
	
	
	/*
	 * (Primero) Inicializar nuestros recursos. 
	 */
	@Override
	public void create() {	
		
		//Usar libreria log de la clase GDX
		Gdx.app.log("Create()", "create()");
		
		//Lanzamos una escena
		//setScreen(new Play());
		
		setScreen(new Splash());

	}
	
	/*
	 * (Ultimo) liberar recursos.
	 */
	@Override
	public void dispose() {
		super.dispose();
	}
	
	/*
	 * (Loop) Se ejecuta continuamente.
	 */
	@Override
	public void render() {		
		super.render();
	}


	/*
	 * (Se ejecuta despues de create) Basicamente su uso es 
	 * 	para asignar medidas del dispositivo.
	 */
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	
	/*
	 * (Se ejecuta despues de finalizar el método render.
	 */
	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
