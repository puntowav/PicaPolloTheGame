package iticbcn.elhueso.picapollo.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Platform extends Actor {

    private Texture texture;
    private float x_size;
    private float y_size;

    public Platform(Texture texture, float x, float y) {
        this.texture = texture;
        this.x_size = x;
        this.y_size = y;
    }
}
