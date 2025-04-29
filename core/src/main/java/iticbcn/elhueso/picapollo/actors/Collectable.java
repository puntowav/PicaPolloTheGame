package iticbcn.elhueso.picapollo.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;

import org.w3c.dom.Text;

public class Collectable extends Actor {

    private Texture texture;
    private float x_size;
    private float y_size;

    public Collectable(Texture texture, float x, float y) {
        this.texture = texture;
        this.x_size = x;
        this.y_size = y;
    }
}
