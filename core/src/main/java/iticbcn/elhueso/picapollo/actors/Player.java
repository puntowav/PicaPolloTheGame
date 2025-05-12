package iticbcn.elhueso.picapollo.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.QuadTreeFloat;

import java.util.List;
import java.util.Random;

import iticbcn.elhueso.picapollo.screens.GameScreen;
import iticbcn.elhueso.picapollo.utils.PPGRectangle;

public class Player extends Actor {


    private static final Logger log = new Logger("Player", Logger.INFO);

    private Vector2 velocity = new Vector2(0, 0);
    private boolean onGround = true;
    private Platform currentPlatform = null;

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

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float x = getX(), y = getY(), w = getWidth(), h = getHeight();

        batch.draw(
            texture,
            x + w, y + h,
            -w, -h
        );
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        bounds.setPosition(getX(), getY());
    }

    public void moveLeft(){velocity.x = -SPEED;}
    public void moveRight(){velocity.x = SPEED;}
    public void jump(){
        System.out.println(onGround);
        if(onGround){
            Gdx.app.log("Player", "jump() invocado");
            velocity.y = JUMP_VELOCITY;
            onGround = false;
            currentPlatform = null;
        }
    }

    public boolean isLandingOn(Platform plat) {
        PPGRectangle p   = bounds;            // el rectángulo de colisión del jugador
        PPGRectangle r   = plat.getBounds();  // el rectángulo de colisión de la plataforma

        // 1) horz: solapamiento horizontal
        boolean horz = p.x + p.width > r.x && p.x < r.x + r.width;

        // 2) platTop: la coordenada Y del tope de la plataforma
        float platTop = r.y + r.height;
        float playerBottomY = bounds.y + bounds.height;

        // 3) closeVert: qué tan cerca está el pie del jugador (p.y) del tope
        boolean closeVert = Math.abs(platTop - playerBottomY) <= 8f;

        // 4) velocity.y: velocidad vertical actual del jugador, viene de getVelocity().y
        //    p.y: posición Y actual del pie del jugador (bounds.y)

        // ahora hacemos el println justo antes del return:
        log.info(String.format(
            "horz=%b closeVert=%b vel=%.1f p.y=%.1f platTop=%.1f",
            horz, closeVert, velocity.y, p.y, platTop
        ));

        float platY    = plat.getBounds().y;
        float platH    = plat.getBounds().height;

        System.out.println(String.format(
            "PLAT y=%.1f  h=%.1f  platTop=%.1f  |  P.Y(bnd)=%.1f  getY()=%.1f",
            platY, platH, platTop,
            bounds.y, getY()
        ));

        float playerFoot = p.y;
        float platformTop = r.y + r.height;
        float margin = 5f;

        boolean vert =
            playerFoot >= platformTop - margin &&
                playerFoot <= platformTop + margin;

        if (horz && vert) {
            System.out.println("LANDING DETECTED on platform at y=" + platformTop);
            return true;
        }

        return horz && closeVert && velocity.y <= 0;
    }

    public void landOn(Platform plat) {
        // Log para depurar
        Gdx.app.log("Player", "landOn() en platform y=" + plat.getY());
        setY(plat.getY() + plat.getHeight());
        bounds.setPosition(getX(), getY());
        velocity.y = 0;
        onGround = true;
        currentPlatform = plat;
    }

    public void fallOffPlatform() {
        Gdx.app.log("Player", "fallOffPlatform()");
        currentPlatform = null;
        onGround = false;
    }

    public Platform getCurrentPlatform(){return currentPlatform;}

    public Texture getTexture() {
        return texture;
    }

    public Boolean getOnGround(){return onGround;}

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setOnGround(Boolean onGround){this.onGround = onGround;}

    public PPGRectangle getBounds() {
        return bounds;
    }

    public Vector2 getVelocity() {
        return velocity;
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
