package iticbcn.elhueso.picapollo.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import iticbcn.elhueso.picapollo.utils.PPGRectangle;

public class Player extends Actor {

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
        bounds.setPosition(getX(), getY());
    }

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
