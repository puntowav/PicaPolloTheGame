package iticbcn.elhueso.picapollo.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import iticbcn.elhueso.picapollo.actors.Collectable;
import iticbcn.elhueso.picapollo.actors.Enemy;
import iticbcn.elhueso.picapollo.actors.Goal;
import iticbcn.elhueso.picapollo.actors.Platform;
import iticbcn.elhueso.picapollo.actors.Player;
import iticbcn.elhueso.picapollo.actors.Spikes;
import iticbcn.elhueso.picapollo.helpers.AssetManager;


public class GameScreen implements Screen {

    private int levelNum;
    private Stage stage;

    private static final int TILE_SIZE = 32;

    public GameScreen(int levelNum) {
        this.levelNum = levelNum;
    }

    public void show() {
        stage = new Stage(new ScreenViewport());
        Pixmap layout = new Pixmap(Gdx.files.internal("level_" + levelNum + "_layout.png"));

        for (int y = 0; y < layout.getHeight(); y++) {
            for (int x = 0; x < layout.getWidth(); x++) {
                Color color = new Color();
                Color.rgba8888ToColor(color, layout.getPixel(x,y));
                float wx = x * TILE_SIZE;
                float wy = y * TILE_SIZE;

                if (isPlatform(color)) {
                    stage.addActor(new Platform(AssetManager.platformTexture, wx, wy));
                } else if (isSpike(color)) {
                    stage.addActor(new Spikes(AssetManager.spikeTexture, wx, wy));
                } else if (isEnemy(color)) {
                    stage.addActor(new Enemy(AssetManager.enemyTexture, wx, wy));
                } else if (isGoal(color)) {
                    stage.addActor(new Goal(AssetManager.goalTexture, wx, wy));
                } else if (isCollectible(color)) {
                    stage.addActor(new Collectable(AssetManager.collectableTexture, wx, wy));
                } else if (isPlayerSpawn(color)) {
                    stage.addActor(new Player(AssetManager.playerTexture, wx, wy));
                }
            }
        }
    }

    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    // Detectors de colors
    private boolean isPlatform(Color c)    { return c.r>.5f&&c.g<.5f&&c.b<.1f; }
    private boolean isSpike(Color c)       { return c.r> .9f&&c.g<.1f&&c.b<.1f; }
    private boolean isEnemy(Color c)       { return c.r>.5f&&c.b>.5f&&c.g<.1f; }
    private boolean isGoal(Color c)        { return c.g> .9f&&c.r<.1f&&c.b<.1f; }
    private boolean isCollectible(Color c) { return c.r> .9f&&c.g> .9f&&c.b<.1f; }
    private boolean isPlayerSpawn(Color c) { return c.b> .7f&&c.g>.1f&&c.g<.7f; }
}
