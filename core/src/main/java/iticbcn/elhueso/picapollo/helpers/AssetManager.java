package iticbcn.elhueso.picapollo.helpers;

import com.badlogic.gdx.graphics.Texture;

public class AssetManager {

    public static Texture playerTexture;
    public static Texture platformTexture;
    public static Texture spikeTexture;
    public static Texture enemyTexture;
    public static Texture goalTexture;
    public static Texture collectableTexture;

    public static void load() {
        playerTexture      = new Texture("player.png");
        platformTexture    = new Texture("platform.png");
        spikeTexture       = new Texture("spike.png");
        enemyTexture       = new Texture("enemy.png");
        goalTexture        = new Texture("goal.png");
        collectableTexture = new Texture("collectible.png");
    }

    public static void dispose() {
        playerTexture.dispose();
        platformTexture.dispose();
        spikeTexture.dispose();
        enemyTexture.dispose();
        goalTexture.dispose();
        collectableTexture.dispose();
    }
}
