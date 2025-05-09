package iticbcn.elhueso.picapollo.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import iticbcn.elhueso.picapollo.screens.GameScreen;
import iticbcn.elhueso.picapollo.utils.PPGRectangle;

public class Collectable extends Actor {

    private Texture texture;
    private PPGRectangle bounds;

    public Collectable(Texture texture, PPGRectangle rect) {
        this.texture = texture;
        this.bounds = rect;
        setBounds(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }
}
