package iticbcn.elhueso.picapollo.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import iticbcn.elhueso.picapollo.utils.PPGRectangle;

public class Goal extends Actor {

    private Texture texture;
    private PPGRectangle bounds;

    public Goal(Texture texture, PPGRectangle rect) {
        this.texture = texture;
        bounds = rect;
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

    public PPGRectangle getBounds(){return bounds;}
}
