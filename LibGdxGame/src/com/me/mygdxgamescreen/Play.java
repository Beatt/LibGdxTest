package com.me.mygdxgamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.me.mygdxgameentity.Player;

public class Play implements Screen{
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer render;
	private OrthographicCamera camera;
	private TiledMapTileLayer tileMapTileLayer;
	private Texture texture;
	private int cordenadaX,
				cordenadaY;
	private boolean colisionX,
					colisionY;
	
	private int tileWidth,
			 tileHeight;
	
	private TiledMapTileLayer layer;
	
	private int conta = 2;
	
	private Player player;
	
	@Override
	public void render(float delta) {
		
		//Pintamos la pantalla
		Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.position.set((player.getX() + player.getWidth()/2 ), (player.getY() + player.getHeight()/2), 0);
		camera.update();
		
		render.setView(camera);
		render.render();
		
		render.getSpriteBatch().begin();
		player.draw(render.getSpriteBatch());
		render.getSpriteBatch().end();
		
		
		
	}

	@Override
	public void resize(int width, int height) {
		
			camera.viewportWidth = width;
			camera.viewportHeight = height;
			
	}

	@Override
	public void show() {
		
		map = new TmxMapLoader().load("maps/map.tmx");
		layer = (TiledMapTileLayer) map.getLayers().get(0);
		
		//Se obtiene el ancho y alto del tile
		tileWidth = layer.getWidth();
		tileHeight = layer.getHeight();
		
		render = new OrthogonalTiledMapRenderer(map);
		
		camera = new OrthographicCamera();
		
		player = new Player(new Sprite(new Texture("img/player.png")), (TiledMapTileLayer) map.getLayers().get(0));
		player.setPosition( 250 , 300);
		
	}
	
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		map.dispose();
		render.dispose();

	}
}
