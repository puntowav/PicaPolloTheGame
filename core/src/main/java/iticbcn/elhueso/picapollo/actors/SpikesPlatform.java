package iticbcn.elhueso.picapollo.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import iticbcn.elhueso.picapollo.utils.PPGRectangle;

/**
 * Subclasse de Platform (I per tant de Actor).
 * L'utilitzem per reutilitzar les físiques de col·lisió de Platform
 * I afegim el comportament de fer mal al tocar-les.
 *
 */
public class SpikesPlatform extends Platform {

    public SpikesPlatform(Texture texture, PPGRectangle rect) {
        super(texture, rect);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(this.getTexture(), getX(), getY(), getWidth(), getHeight());
    }
}
