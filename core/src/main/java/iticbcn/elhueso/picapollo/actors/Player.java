package iticbcn.elhueso.picapollo.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.List;

import iticbcn.elhueso.picapollo.screens.GameScreen;
import iticbcn.elhueso.picapollo.utils.PPGRectangle;

public class Player extends Actor {

    private Vector2 velocity = new Vector2(0, 0);
    private boolean onGround = true;
    private Platform currentPlatform = null;

    private final float SPEED = 200f;
    private final float JUMP_VELOCITY = 500f;
    private final float GRAVITY = -1000f;
    private static final float MAX_FALL_SPEED = -800f;

    private Texture texture;
    private PPGRectangle bounds;

    public Player(Texture texture, PPGRectangle rect) {
        this.texture = texture;
        this.bounds = rect;
        setBounds(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if(!onGround){
            velocity.y += GRAVITY * delta;
            if(velocity.y < MAX_FALL_SPEED) velocity.y = MAX_FALL_SPEED;
        }

        moveBy(velocity.x * delta, velocity.y * delta);
        bounds.setPosition(getX(), getY());
    }

    public void moveLeft(){velocity.x = -SPEED;}
    public void moveRight(){velocity.x = SPEED;}
    public void stop(){velocity.x = 0;}
    public void jump(){
        if(onGround){
            velocity.y = JUMP_VELOCITY;
            onGround = false;
            currentPlatform = null;
        }
    }

    public boolean isLandingOn(Platform platform){
        PPGRectangle p = bounds;
        PPGRectangle plat = platform.getBounds();

        return p.overlaps(plat)
            && velocity.y <= 0
            && p.y >= plat.y + plat.height - 5
            && p.y <= plat.y + plat.height + 5;
    }

    public void landOn(Platform platform){
        setY(platform.getBounds().getY() + platform.getBounds().getHeight());
        bounds.setPosition(getX(), getY());
        velocity.y = 0;
        onGround = true;
        currentPlatform = platform;
    }

    public void fallOffPlatform() {
        currentPlatform = null;
        onGround = false;
    }

    public Platform getCurrentPlatform(){return currentPlatform;}

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public PPGRectangle getBounds() {
        return bounds;
    }
}
