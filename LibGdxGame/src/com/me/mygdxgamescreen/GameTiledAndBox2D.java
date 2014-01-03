package com.me.mygdxgamescreen;

import javax.rmi.CORBA.Tie;

import net.dermetfan.utils.libgdx.box2d.Autopilot;
import net.dermetfan.utils.libgdx.box2d.Box2DMapObjectParser;
import net.dermetfan.utils.libgdx.box2d.Box2DUtils;
import net.dermetfan.utils.libgdx.box2d.Box2DMapObjectParser.Aliases;
import net.dermetfan.utils.libgdx.graphics.Box2DSprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameTiledAndBox2D implements Screen{

	private TiledMap tileMap;
	private OrthographicCamera camera;
	private OrthogonalTiledMapRenderer tiledMapRenderer;
	private TiledMapTileLayer tiledMapTileLayer;
	
	
	private World world;
	private Box2DDebugRenderer debugRenderer;
	
	private final float TIMESTEP = 1 / 60f;
	private final int VELOCITYITERATIONS = 8, POSITIONINTERATIONS = 3;
	
	private SpriteBatch spriteBatch;
	
	private Box2DMapObjectParser mapObjectParser;
	
	private ShapeRenderer shapeRenderer;
	
	private Vector2 velocidad;
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		
		//mapObjectParser.getBodies().get("jugador").applyForceToCenter(velocidad, true);
		world.step(TIMESTEP, VELOCITYITERATIONS , POSITIONINTERATIONS);
		
		camera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
		camera.update();
		
		// Agregamos nuestra camara al mapa.
		tiledMapRenderer.setView(camera);
		
		// Dibujgamos nuestro mapa
		tiledMapRenderer.render();

		
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		Box2DSprite.draw(spriteBatch, world);
		spriteBatch.end();
		
		debugRenderer.render(world, camera.combined);
		
		
	}

	@Override
	public void resize(int width, int height) {
		
		camera.viewportHeight = height;
		camera.viewportWidth = width;		
		
		//camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void show() {
		
		camera = new OrthographicCamera();
		
		shapeRenderer = new ShapeRenderer();
		
		// creamos mundo
		world = new World(new Vector2(0, -9.81f), true);
		
		debugRenderer = new Box2DDebugRenderer();
		
		velocidad = new Vector2();
		
		// creamos nuestro batch
		spriteBatch = new SpriteBatch();
		
		// obtenemos nuestro mapa tmx
		tileMap = new TmxMapLoader().load("tiledGame/tiles.tmx");
		
		//Parseamos nuestro mapa de objetos.
		mapObjectParser = new Box2DMapObjectParser();
		mapObjectParser.load(world, tileMap);
				
		Sprite sprite = new Sprite(new Texture("img/luigifront.png"));
		
		Box2DSprite DSprite = new Box2DSprite(sprite);
		
		mapObjectParser.getFixtures().get("jugador").getBody().setUserData(DSprite);
		mapObjectParser.getBodies().get("jugador").setGravityScale(5);
		velocidad.x = mapObjectParser.getBodies().get("jugador").getPosition().x;
		velocidad.y = mapObjectParser.getBodies().get("jugador").getPosition().y;
		
		
		// obtenemos un layer
		//tiledMapTileLayer = (TiledMapTileLayer) tileMap.getLayers().get("objetos");

		System.out.println(mapObjectParser.getBodies().get("jugador").getPosition());
		
		// Agregamos nuestro mapa al render
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tileMap, mapObjectParser.getUnitScale());
		
		Gdx.input.setInputProcessor(new InputProcessor() {
			
			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {

				float width = 0;
				float height = 0;
						
				//Cordenada y :::: Default LIBGDX is Bottom-Left (0, 0)
				
				//Convert cordinate y
				screenY = Gdx.graphics.getHeight() - screenY;
				
				//Obtenemos el cuerpo del jugador
				Body jugador = mapObjectParser.getBodies().get("jugador");
				
				// Obtenemos el MapObject del mapa tileMap del layout objetos
				MapObject objJugador = tileMap.getLayers().get("objetos").getObjects().get("jugador");
				
				if(objJugador instanceof RectangleMapObject)
				{
					Rectangle rec = ((RectangleMapObject) objJugador).getRectangle();
					 width = rec.width / 2;
					 height = rec.height / 2;
					
				}
				
				//Transfiere el cuerpo a las cordenadas actuales
				jugador.setTransform(screenX - width, screenY - height, 0);
				
				
				System.out.println("Object x " + jugador.getPosition().x 
						+ " Object y " + jugador.getPosition().y);
				System.out.println("x " + screenX + " y " + screenY);
				return true;
			}
			
			@Override
			public boolean scrolled(int amount) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean mouseMoved(int screenX, int screenY) {
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
			public boolean keyDown(int keycode) {
				// TODO Auto-generated method stub
				return false;
			}
		});
				
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
		tiledMapRenderer.dispose();
		tileMap.dispose();
		world.dispose();
	}	

} // Fin GameTiledAndBox2D
