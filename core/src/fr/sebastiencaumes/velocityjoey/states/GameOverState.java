package fr.sebastiencaumes.velocityjoey.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import fr.sebastiencaumes.velocityjoey.VelocityJoey;

/**
 * Created by sebastien on 27/09/15.
 */
public class GameOverState extends State {
    private static final int GAP_BUTTONS = 30;
    private Texture gameIsOver;
    private Texture background;
    private Texture playAgain;
    private Texture backToMenu;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont playrScoreMaxFont;
    private String playerScoreMAx;
    public GameOverState(GameStateManager gsm){
        super(gsm);

        //for android
        cam.setToOrtho(false, VelocityJoey.WIDTH , VelocityJoey.HEIGHT );
        background = new Texture("bggameover.png");
        gameIsOver = new Texture("gameisover.png");
        playAgain = new Texture("playbtn.png");
        backToMenu = new Texture("backmenu.png");
        generator = new FreeTypeFontGenerator(Gdx.files.internal("inversion.otf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 46;
        playrScoreMaxFont = generator.generateFont(parameter);
        playerScoreMAx = "best "+ PlayState.pref.getString("best", "0");
        playrScoreMaxFont.setColor(Color.GOLDENROD);

    }

    @Override
    protected void handleInput() {

        //if replay
        if(Gdx.input.getX() > Gdx.graphics.getWidth()/2 && Gdx.input.justTouched()){

            gsm.set(new PlayState(gsm));
            PlayState.decorChange ++;
            PlayState.decorChangeCompteur++;
            dispose();

        //if back to main menu
        }

        if (Gdx.input.getX() < Gdx.graphics.getWidth() /2 && Gdx.input.justTouched()){

            gsm.set(new MenuState(gsm));
            PlayState.decorChange ++;
            PlayState.decorChangeCompteur++;
            dispose();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined); // for android
        sb.begin();
        sb.draw(background, 0, 0, VelocityJoey.WIDTH, VelocityJoey.HEIGHT);
        sb.draw(gameIsOver, cam.position.x - gameIsOver.getWidth() /2, cam.position.y +250) ;
        sb.draw(playAgain, cam.position.x -5 , cam.position.y - gameIsOver.getHeight() + 300) ;
        sb.draw(backToMenu, cam.position.x - backToMenu.getWidth() -5 , cam.position.y - gameIsOver.getHeight() + 300);
        playrScoreMaxFont.draw(sb, playerScoreMAx, cam.position.x - 300 , cam.position.y +560);
        sb.end();
    }

    @Override
    public void dispose() {

        background.dispose();
        gameIsOver.dispose();
        playAgain.dispose();
        backToMenu.dispose();

    }


}
