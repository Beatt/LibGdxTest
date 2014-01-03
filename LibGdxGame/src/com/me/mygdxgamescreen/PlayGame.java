package com.me.mygdxgamescreen;



import net.dermetfan.utils.libgdx.graphics.Box2DSprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class PlayGame implements Screen {

	//Es una colección de cuerpos, texturas y restricciones.
	private World world;
	
	//Box2D para renderizar
	private Box2DDebugRenderer debugRenderer;
	
	//Camara
	private OrthographicCamera camera;
	
	//Cuerpo
	private Body box;
	private Vector2 movement = new Vector2();
	
	private SpriteBatch batch;
	
	private final float TIMESTEP = 1 / 60f;
	private final int VELOCITYITERATIONS = 8, POSITIONINTERATIONS = 3;
	
	private Skin skin;
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		world.step(TIMESTEP, VELOCITYITERATIONS , POSITIONINTERATIONS);
		//body.applyForceToCenter(movement, true);
		
		camera.position.set(box.getPosition().x / 30, box.getPosition().y / 30, 0);
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		Box2DSprite.draw(batch, world);
		batch.end();
		
		//debugRenderer.render(world, camera.combined);
	}

	@Override
	public void resize(int width, int height) {
		
		//Tamaño de la camera
		camera.viewportWidth = width / 30;
		camera.viewportHeight = height / 30;
	}

	@Override
	public void show() {
		
		//Instanciamos objetos
		world = new World(new Vector2(0, -9.81f), true);
		debugRenderer = new Box2DDebugRenderer();
		
		//Deshabilita las lineas de los cuerpos que dibujamos.
		debugRenderer.setDrawBodies(false);
		
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), new TextureAtlas("ui/atlas1.pack"));
		
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		
		/*
		 * CIRCULO
		 * Definición del cuerpo
		 */
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(5, 10);
		
		// Definición del shape
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(2);
		
		// Definición de la textura
		fixtureDef.shape = circleShape;
		fixtureDef.density = 2.5f;
		fixtureDef.friction = .25f;
		fixtureDef.restitution = .75f;
		
		//Creamos el cuerpo
		world.createBody(bodyDef).createFixture(fixtureDef).setUserData(new Box2DSprite(
				new Sprite(new Texture("img/images.png"))));
		
		//Liberamos de la memoria
		circleShape.dispose();
		
		/*
		 * LINEA
		 * Definición del cuerpo
		 */
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(0, 0);
		
		// Definición de shape
		ChainShape chainShape = new ChainShape();
		chainShape.createChain(new Vector2[]{new Vector2(-50, 0), new Vector2(50, 0)});
		
		// Definición de textura
		fixtureDef.shape = chainShape;
		fixtureDef.friction = .5f;
		fixtureDef.restitution = 0;
		
		world.createBody(bodyDef).createFixture(fixtureDef);
		
		// liberamos memoria
		chainShape.dispose();
		
		/*
		 * Caja
		 * Definición del cuerpo
		 */
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(0, 10);
		
		// Definición del shape
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(.5f, 1);
		
		// Definición de la textura
		fixtureDef.shape = boxShape;
		fixtureDef.density = 2.4f;
		fixtureDef.friction = .25f;
		fixtureDef.restitution = .75f;
		
		// Creamos objeto en el mundo
		box = world.createBody(bodyDef);
		box.createFixture(fixtureDef).setUserData(new Box2DSprite(
				new Sprite(new Texture("img/luigifront.png"))));
		
		// Liberar memoria
		boxShape.dispose();
		
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
		world.dispose();
		debugRenderer.dispose();
		batch.dispose();
	}

} //Fin class PlayGame
