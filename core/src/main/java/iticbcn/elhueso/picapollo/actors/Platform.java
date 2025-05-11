package iticbcn.elhueso.picapollo.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import iticbcn.elhueso.picapollo.utils.PPGRectangle;

/**
 * Classe base de plataforma.
 * Comportament bàsic d'una plataforma amb col·lisió.
 *
 */
public class Platform extends Actor {

    private Texture texture;
    private PPGRectangle bounds;

    public Platform(Texture texture, PPGRectangle rect) {
        this.texture = texture;
        bounds = rect;

        setBounds(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
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

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float x = getX(), y = getY(), w = getWidth(), h = getHeight();

        batch.draw(
            texture,
            x + w, y + h,
            -w, -h
        );
    }
}
