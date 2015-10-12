package fr.sebastiencaumes.velocityjoey.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import fr.sebastiencaumes.velocityjoey.VelocityJoey;
import fr.sebastiencaumes.velocityjoey.sprites.Joey;

/**
 * Created by sebastien on 27/09/15.
 */
public class PlayState extends State{

    public static int decorChange = 0;
    public static int decorChangeCompteur = 0;
    public static int decorNumber=1;
    public static boolean nuit;
    public static boolean temps;
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_OFFSET = 0;
    private boolean gameOver = false; //seb
    private Joey joey;
    private Array<fr.sebastiencaumes.velocityjoey.sprites.Tube> tubes;
    private Vector2 groundPos1, groundPos2;
    private boolean repo;
    private int score;
    private String playerScore;
    private BitmapFont playrScoreFont;
    private int scoreMax;
    private String playerScoreMAx;
    private BitmapFont playrScoreMaxFont;
    private int birdXSpacing;
    public static Preferences pref;
    private Music music;
    private Sound crashSound;
    private Sound alertAir;
    private Sound fallGround;
    private Texture bg;
    private Texture ground;
    private Sound theGameIsOver;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontParameter parameter;


    /*****
     * general scale is x4 nes style
     *******/
    public PlayState(GameStateManager gsm) {
        super(gsm);

        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("music.ogg"));
        if(!music.isPlaying() && MenuState.musicOn){
            music.setLooping(true);
            music.setVolume(0.6f);
            music.play();

        }

        generator = new FreeTypeFontGenerator(Gdx.files.internal("inversion.otf"));
        parameter = new FreeTypeFontParameter();
        parameter.size = 16;
        crashSound = Gdx.audio.newSound(Gdx.files.internal("metalhit.ogg"));
        alertAir = Gdx.audio.newSound(Gdx.files.internal("fx_air.ogg"));
        fallGround = Gdx.audio.newSound(Gdx.files.internal("fx_sol.ogg"));
        theGameIsOver = Gdx.audio.newSound(Gdx.files.internal("gameover.ogg"));
        //theGameIsOver.stop();

        playrScoreFont = generator.generateFont(parameter);
        playrScoreMaxFont = generator.generateFont(parameter);
        pref = Gdx.app.getPreferences("mypreferences");


        joey = new Joey(birdXSpacing, 220);
        cam.setToOrtho(false, VelocityJoey.WIDTH / 4, VelocityJoey.HEIGHT / 4);
        score = 0;
        playerScore = "0";
        //playrScoreFont = new BitmapFont();

        scoreMax =0;
        playerScoreMAx = "best " +pref.getString("best", "0");
        //playrScoreMaxFont = new BitmapFont();

        repo = false;
        tubes = new Array<fr.sebastiencaumes.velocityjoey.sprites.Tube>();

        if (decorNumber == 1) {

            for (int i = 1; i <= TUBE_COUNT; ++i) {
                tubes.add(new fr.sebastiencaumes.velocityjoey.sprites.Tube(i * (TUBE_SPACING + fr.sebastiencaumes.velocityjoey.sprites.Tube.TUBE_WIDTH)));
            }
            decorNumber =2;
        }

        else if (decorNumber == 2) {

            bg = new Texture("bgnight.png");
            ground = new Texture("groundnight.png");
            for (int i = 1; i <= TUBE_COUNT; ++i) {
                tubes.add(new fr.sebastiencaumes.velocityjoey.sprites.Tube(i * (TUBE_SPACING + fr.sebastiencaumes.velocityjoey.sprites.Tube.TUBE_WIDTH)));
            }
            decorNumber =3;
        }

        else if (decorNumber == 3) {

            bg = new Texture("bg_blackwhite.png");
            ground = new Texture("ground_blackwhite.png");
            for (int i = 1; i <= TUBE_COUNT; ++i) {
                tubes.add(new fr.sebastiencaumes.velocityjoey.sprites.Tube(i * (TUBE_SPACING + fr.sebastiencaumes.velocityjoey.sprites.Tube.TUBE_WIDTH)));
            }
            decorNumber =4;
        }

        else if (decorNumber == 4) {
            bg = new Texture("bgspace.png");
            ground = new Texture("groundspace.png");
            for (int i = 1; i <= TUBE_COUNT; ++i) {
                tubes.add(new fr.sebastiencaumes.velocityjoey.sprites.Tube(i * (TUBE_SPACING + fr.sebastiencaumes.velocityjoey.sprites.Tube.TUBE_WIDTH)));
            }

            decorNumber =1;
        }

        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 4, GROUND_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 4) + ground.getWidth(), GROUND_OFFSET);

    }


    @Override
        protected void handleInput () {
            if (Gdx.input.justTouched()) {
                joey.jump();

            }
        }

    private void scoreUp() {
        score++;
        playerScore = "" + score;
        pref.putString("best", playerScore);
        pref.putInteger("bestint", score);

        //si score est superieur a scoremax
        if( score > pref.getInteger("bestint", 0)){
            playerScoreMAx = "best "+pref.getString("best", "0");
            pref.flush();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        joey.update(dt);
        cam.position.x = joey.getPosition().x + 60;

        for (fr.sebastiencaumes.velocityjoey.sprites.Tube tube : tubes) {
            if (cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPosTopTube().x + (fr.sebastiencaumes.velocityjoey.sprites.Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT);
                scoreUp();
            }
            if (tube.collides(joey.getBounds())) {

                crashSound.play(0.8f);
                theGameIsOver.play();
                music.stop();
                gsm.set(new GameOverState(gsm)); // seb
                dispose();
                tube.dispose();

            }
        }

        //if touch ground
        if (joey.getPosition().y <= ground.getHeight() + GROUND_OFFSET) {

            fallGround.play();
            theGameIsOver.play();
            music.stop();
            gsm.set(new GameOverState(gsm)); // seb


            dispose();

        }

        //if too high in the sky
    if((joey.getPosition().y +10) >= VelocityJoey.HEIGHT / 4) {
        alertAir.play(0.05f);
        if(joey.getPosition().y + 10 < VelocityJoey.HEIGHT /4){
            alertAir.stop();
        }

        if ((joey.getPosition().y -20) >= VelocityJoey.HEIGHT / 4) {

            theGameIsOver.play();
            music.stop();
            gsm.set(new GameOverState(gsm)); // seb

            dispose();

        }
    }
        cam.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(joey.getBird(), joey.getPosition().x, joey.getPosition().y);

        //test
        playrScoreFont.setColor(Color.DARK_GRAY);

        playrScoreFont.draw(sb, playerScore, cam.position.x - (cam.viewportWidth
                / 2) + 15, cam.position.y - (cam.viewportHeight / 2) + 300);
        playrScoreMaxFont.setColor(Color.GOLDENROD);
        playrScoreMaxFont.draw(sb, playerScoreMAx, cam.position.x - (cam.viewportWidth / 2) +105, cam.position.y - (cam.viewportHeight / 2) + 300);

        for (fr.sebastiencaumes.velocityjoey.sprites.Tube tube : tubes) {
            if (decorNumber == 2) {
                sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
                sb.draw(tube.getTopTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
            }else

            if (decorNumber ==3) {
                sb.draw(tube.getTopTubeNight(), tube.getPosTopTube().x, tube.getPosTopTube().y);
                sb.draw(tube.getTopTubeNight(), tube.getPosBotTube().x, tube.getPosBotTube().y);


            }else if (decorNumber ==4){
                sb.draw(tube.getTubeBlackNWhite(), tube.getPosTopTube().x, tube.getPosTopTube().y);
                sb.draw(tube.getTubeBlackNWhite(), tube.getPosBotTube().x, tube.getPosBotTube().y);
            }

            else if(decorNumber == 1){
                sb.draw(tube.getTubeSpace(), tube.getPosTopTube().x, tube.getPosTopTube().y);
                sb.draw(tube.getTubeSpace(), tube.getPosBotTube().x, tube.getPosBotTube().y);
            }

        }

        sb.draw(ground, groundPos1.x, groundPos2.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        generator.dispose();
        music.dispose();
        crashSound.dispose();
        ground.dispose();
        music.dispose();
        alertAir.dispose();
        fallGround.dispose();
        playrScoreFont.dispose();
        playrScoreMaxFont.dispose();
        theGameIsOver.dispose();
        joey.dispose();
    }

    private void updateGround() {
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth()) {
            groundPos1.add(ground.getWidth() * 2, 0);

        }
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth()) {
            groundPos2.add(ground.getWidth() * 2, 0);
        }
    }
}