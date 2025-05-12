package iticbcn.elhueso.picapollo.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Logger;
import iticbcn.elhueso.picapollo.utils.PPGRectangle;

public class Player extends Actor {

    private static final Logger log = new Logger("Player", Logger.INFO);

    private Vector2 velocity = new Vector2(0, 0);
    private boolean onGround = true;
    private Platform currentPlatform = null;

    private int jumpsRemaining = 1;

    private final float SPEED = 200f;
    private final float JUMP_VELOCITY = -500f;
    private final float GRAVITY = 1000f;
    private static final float MAX_FALL_SPEED = 800f;

    private static final float TOLERANCE = 5f;

    private Texture texture;
    private PPGRectangle bounds;

    public Player(Texture texture, PPGRectangle rect) {
        this.texture = texture;
        this.bounds = rect;
        setBounds(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    public Platform getCurrentPlatform(){return currentPlatform;}

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Boolean getOnGround(){ return onGround; }

    public void setOnGround(Boolean onGround){this.onGround = onGround;}

    public PPGRectangle getBounds() {
        return bounds;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float x = getX(), y = getY(), w = getWidth(), h = getHeight();
        batch.draw(
            texture,
            x, y,
            w / 2f, h / 2f,
            w, h,
            getScaleX(), 1f,
            0,
            0, 0,
            texture.getWidth(), texture.getHeight(),
            false, true
        );
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        bounds.setPosition(getX(), getY());
    }

    public void moveLeft(){
        velocity.x = -SPEED;
        setScaleX(-1f);
    }
    public void moveRight(){
        velocity.x = SPEED;
        setScaleX(1f);
    }
    public void jump() {
        if (jumpsRemaining > 0) {
            Gdx.app.log("Player", "jump() invocado");
            velocity.y = JUMP_VELOCITY;
            jumpsRemaining--;
            onGround = false;
            currentPlatform = null;
        }
    }

    public boolean isLandingOn(Platform plat) {
        PPGRectangle p = bounds;
        PPGRectangle r = plat.getBounds();

        boolean horz = p.x + p.width > r.x && p.x < r.x + r.width;

        float platformTop = r.y + r.height;
        float playerBottom = p.y;

        boolean isFalling = velocity.y <= 0;

        boolean closeVert = playerBottom >= platformTop - TOLERANCE &&
            playerBottom <= platformTop + TOLERANCE;

        log.info(String.format(
            "horz=%b closeVert=%b isFalling=%b vel=%.1f p.y=%.1f platTop=%.1f",
            horz, closeVert, isFalling, velocity.y, playerBottom, platformTop
        ));

        return horz && closeVert && isFalling;
    }

    public void resetJumps() {
        jumpsRemaining = 1;
    }

    public void landOn(Platform plat) {
        Gdx.app.log("Player", "landOn() en platform y=" + plat.getY());
        setY(plat.getY() + plat.getHeight());
        bounds.setPosition(getX(), getY());
        velocity.y = 0;
        onGround = true;
        currentPlatform = plat;
        resetJumps();
    }

    public void fallOffPlatform() {
        Gdx.app.log("Player", "fallOffPlatform()");
        currentPlatform = null;
        onGround = false;
    }

    public void applyGravity(float delta) {
        if (!onGround) {
            velocity.y += GRAVITY * delta;
            velocity.y = Math.min(velocity.y, MAX_FALL_SPEED);
        }
    }

    public void moveX(float delta) {
        moveBy(velocity.x * delta, 0);
        bounds.setPosition(getX(), getY());
    }

    public void moveY(float delta) {
        moveBy(0, velocity.y * delta);
        bounds.setPosition(getX(), getY());
    }

    public void stopX() {
        velocity.x = 0;
    }

    public void resetVertical() {
        velocity.y = 0;
        onGround = false;
    }
}
