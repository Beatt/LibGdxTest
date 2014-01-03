package com.me.mygdxgameentity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite implements InputProcessor {
	
	//Velocidad de movimiento
	private Vector2 velocidad = new Vector2();
	
	private float speed = 60 * 2,
			gravity = 60 * 1.8f;
	
	private TiledMapTileLayer tiledMapLayer;
	
	public Player(Sprite sprite, TiledMapTileLayer tiledMapLayer)
	{
		super(sprite);
		
		this.tiledMapLayer = tiledMapLayer;
		Gdx.input.setInputProcessor(this);
		setSize(43, 42);
	}

	public void draw(SpriteBatch spriteBatch) {
		Update(Gdx.graphics.getDeltaTime());
		super.draw(spriteBatch);
	}

	private void Update(float delta) {
		// TODO Auto-generated method stub
		
		
		setY(getY());
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		setX(getX() + getWidth());
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		
		
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public TiledMapTileLayer getTiledMapLayer() {
		return tiledMapLayer;
	}

	public void setTiledMapLayer(TiledMapTileLayer tiledMapLayer) {
		this.tiledMapLayer = tiledMapLayer;
	}

	public Vector2 getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(Vector2 velocidad) {
		this.velocidad = velocidad;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getGravity() {
		return gravity;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}
	
	
}
