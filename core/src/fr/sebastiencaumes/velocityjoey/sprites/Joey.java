package fr.sebastiencaumes.velocityjoey.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by sebastien on 27/09/15.
 */
public class Joey {
    private static final int GRAVITY = -15;
    private float movement;
    private Vector3 position;
    private Vector3 velocity;
    private Texture texture;
    private Rectangle bounds;
    private Animation birdAnimation;
    private Sound flap;
    private static boolean firstJump;

    public TextureRegion getBird() {
        return birdAnimation.getFrame();
    }

    public Vector3 getPosition() {
        return position;
    }

    public Joey(int x, int y){
        firstJump = false;
        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_jump.ogg"));
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        texture = new Texture("bird2.png");
        movement =70;
;
        birdAnimation = new Animation(new TextureRegion(texture), 5, 0.5f);
        bounds = new Rectangle(x, y, texture.getWidth() / 5, texture.getHeight());
    }

    public void update(float dt){
        birdAnimation.update(dt);

        if(firstJump) {
            if (position.y > 0)
                velocity.add(0, GRAVITY, 0);
        }
            velocity.scl(dt);
            position.add(movement * dt, velocity.y, 0);
            if (position.y < 0)
                position.y = 0;


            velocity.scl(1 / dt);

        bounds.setPosition(position.x, position.y);
    }


    public void jump(){
        flap.play(0.3f);
        velocity.y = 250;
        if(firstJump == false)
            firstJump = true;

        if(movement < 90){
            movement = movement +4f;
        }

        if(movement >= 90 && movement < 110){
            movement = movement + 2f;
        }

        if (movement >= 110 && movement <120){
            movement = movement + 1f;
        }

        if (movement >= 120 && movement <130){
            movement = movement +0.2f;
        }
        if (movement >= 130 && movement <150){
            movement = movement +0.1f;
        }

    }

    public boolean isFirstJump() {
        return firstJump;
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public void dispose(){

        texture.dispose();
        flap.dispose();
    }
}
