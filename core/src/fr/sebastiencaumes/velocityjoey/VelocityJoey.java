package fr.sebastiencaumes.velocityjoey;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.sebastiencaumes.velocityjoey.states.GameStateManager;
import fr.sebastiencaumes.velocityjoey.states.MenuState;

public class VelocityJoey extends ApplicationAdapter {
	public static final int WIDTH = 720;
	public static final int HEIGHT = 1280;
	public static final String TITLE = "Velocity Joey";
	public static final int SCALE = 4;

	private GameStateManager gsm;
	public SpriteBatch batch;

    @Override
    public void dispose() {
        super.dispose();
	}

    @Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
        Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm.push(new MenuState(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}

}
