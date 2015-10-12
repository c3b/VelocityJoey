package fr.sebastiencaumes.velocityjoey.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by sebastien on 27/09/15.
 */
public class Tube {
    public static final int TUBE_WIDTH = 16 ;
    private static final int FLUCTUATION = 160;
    private static final int TUBE_FOR_SCORE = 38;
    //vertical gap
    private static final int TUBE_GAP =80;
    private static final int LOWEST_OPENING = 80;
    private Texture topTube, topTubeNight, tubeBlackNWhite, tubeSpace, tubeTron;
    private Vector2 posTopTube, posBotTube, posTopTubeDecale;
    private Rectangle boundsTop, boundsBot;
    private Random rand;


    public Tube(float x){
        topTube = new Texture("toptube.png");
        topTubeNight = new Texture("toptubenight.png");
        tubeBlackNWhite = new Texture("tube_blackwhite.png");
        tubeSpace = new Texture("tubespace.png");
        tubeTron = new Texture("tubetron.png");

        rand = new Random();

        posTopTube = new Vector2(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);

        posBotTube = new Vector2(x, posTopTube.y - TUBE_GAP - (topTube.getHeight()));

        boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
        boundsBot = new Rectangle(posBotTube.x, posBotTube.y, topTube.getWidth(), topTube.getHeight());
    }

    public Texture getTopTube() {
        return topTube;       }

    public Texture getTopTubeNight() {
        return topTubeNight;       }

    public Texture getTubeBlackNWhite() {
        return tubeBlackNWhite;
    }

    public Texture getTubeTron() {
        return tubeTron;
    }

    public Texture getTubeSpace() {
        return tubeSpace;
    }



    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public Vector2 getPosBotTube() {
        return posBotTube;
    }


    public void reposition(float x){
        posTopTube.set(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotTube.set(x, posTopTube.y - TUBE_GAP - topTube.getHeight());
        boundsTop.setPosition(posTopTube.x, posTopTube.y);
        boundsBot.setPosition(posBotTube.x, posBotTube.y);

    }

    public boolean collides (Rectangle player){

        return player.overlaps(boundsTop) || player.overlaps(boundsBot);
    }

    public void dispose(){
        topTube.dispose();

        topTubeNight.dispose();
        tubeBlackNWhite.dispose();
        tubeTron.dispose();
        tubeSpace.dispose();

    }
}
