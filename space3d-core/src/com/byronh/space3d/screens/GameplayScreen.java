package com.byronh.space3d.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.byronh.space3d.Space3DGame;
import com.byronh.space3d.entities.Simulation;
import com.byronh.space3d.graphics.Renderer3D;
import com.byronh.space3d.input.KeyboardController;


public class GameplayScreen extends AbstractScreen {
	
	Simulation sim;
	Renderer3D renderer;

	private InputMultiplexer inputMultiplexer;
	private PerspectiveCamera cam;
	private CameraInputController camController;
	
	private Stage stage;
	private Skin skin;
	private TextButton button;

	public GameplayScreen(Space3DGame game) {
		super(game);
	}

	@Override
	public void show() {
		super.show();

		game.log("Initializing 3D game world");
		
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(7.5f, 1.5f, 7.5f);
		cam.lookAt(0, 0, 0);
		cam.near = 0.1f;
		cam.far = 25000f;
		cam.update();
		
		renderer = new Renderer3D(game.assets);
		
		
		sim = new Simulation();
		sim.addListener(renderer);
		
		
		renderer.init();
		sim.init();
		
		game.log("Initializing game controller");

		KeyboardController keyboardController = new KeyboardController(game);
		camController = new CameraInputController(cam);
		camController.scrollFactor = -0.05f;

		stage = new Stage();
		skin = game.assets.get("ui/Holo-dark-hdpi.json", Skin.class);
		button = new TextButton("Click me", skin, "default");
		button.setPosition(10, 10);
		button.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				button.setText("Clicked!");
			}
		});
		stage.addActor(button);

		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(keyboardController);
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(camController);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		
		sim.mainLoop(delta);
		
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT//);
		 | (Gdx.graphics.getBufferFormat().coverageSampling ?
		 GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
		
		camController.update();
		renderer.render(cam);

		button.setText(Integer.toString(Gdx.graphics.getFramesPerSecond()));

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void dispose() {
		super.dispose();
		skin.dispose();
		stage.dispose();
	}
}
