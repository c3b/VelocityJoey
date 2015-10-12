package fr.sebastiencaumes.velocityjoey.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.sebastiencaumes.velocityjoey.VelocityJoey;

/**
 * Created by sebastien on 27/09/15.
 */
public class MenuState extends State {

    private Texture background;
    private Texture mainTitle;
    private Texture musicBtn;
    private Texture musicBtnOff;
    private Texture exitBtn;
    private Texture accelTxt;
    private Music musicMenu;
    public static boolean musicOn =true;

    public MenuState(GameStateManager gsm){
        super(gsm);
        musicMenu = Gdx.audio.newMusic(Gdx.files.internal("music_menu.ogg"));
        musicMenu.setLooping(true);
        musicMenu.setVolume(0.9f);
        if(musicOn)
            musicMenu.play();

        //for android
        cam.setToOrtho(false, VelocityJoey.WIDTH, VelocityJoey.HEIGHT);
        background = new Texture("bgmenu.png");
        mainTitle = new Texture("maintitle.png");
        musicBtn = new Texture("musicbtn.png");
        musicBtnOff = new Texture("musicbtnoff.png");
        exitBtn = new Texture("exitbtn.png");
        accelTxt = new Texture(("accel.png"));
    }

    @Override
    public void handleInput() {

        //if upstair part
        if(Gdx.input.getX() > Gdx.graphics.getWidth()/2 && Gdx.input.getY() < Gdx.graphics.getHeight()/2 && Gdx.input.justTouched()){

            musicMenu.stop();

            gsm.set(new PlayState(gsm));


            //if middle touch
        }else

        if (Gdx.input.getY() > Gdx.graphics.getHeight()/2 && Gdx.input.getY() < Gdx.graphics.getHeight()/1.3 && Gdx.input.justTouched()){

            //TODO Make music stop
            if(musicOn == true){
                musicMenu.stop();
                musicOn = false;
            }else {
                musicMenu.play();
                musicOn = true;
            }

        }

        //if bottom  touch
        if (Gdx.input.getY() > Gdx.graphics.getHeight()/1.3 && Gdx.input.getY() < Gdx.graphics.getHeight()/1.2  && Gdx.input.justTouched()){

            //exit app
            Gdx.app.exit();
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
        //sb.draw(background, 0, 0);//for android
        sb.draw(mainTitle, cam.position.x - mainTitle.getWidth() / 2, cam.position.y + 180 );
        sb.draw(exitBtn, cam.position.x -exitBtn.getWidth()/2 , cam.position.y - mainTitle.getHeight());
        sb.draw(accelTxt, cam.position.x - accelTxt.getWidth()/2, cam.position.y -80);

    if(musicOn){
        sb.draw(musicBtn, cam.position.x - musicBtn.getWidth()/2 , cam.position.y - mainTitle.getHeight()+180);
    }else {
        sb.draw(musicBtnOff, cam.position.x - musicBtn.getWidth()/2 , cam.position.y - mainTitle.getHeight()+180);

    }
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        musicMenu.dispose();
        mainTitle.dispose();
        musicBtn.dispose();
        musicBtnOff.dispose();
        exitBtn.dispose();
        accelTxt.dispose();
    }
}
