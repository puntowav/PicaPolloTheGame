package iticbcn.elhueso.picapollo.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import iticbcn.elhueso.picapollo.helpers.AssetManager;
import iticbcn.elhueso.picapollo.utils.Settings;

public class TitleScreen implements Screen {

    private Batch batch;
    private Game game;
    private BitmapFont font;
    private GlyphLayout layout;
    private Animation<TextureRegion> flagAnimation;
    private float stateTime;


    public TitleScreen(Game game) {
        this.game = game;
        AssetManager.titleSong.play();
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/pixel_emulator.fnt"));
        layout = new GlyphLayout(font, "El huesaso loco");

        // Carga los frames de la bandera
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 25; i++) {
            Texture texture = new Texture(Gdx.files.internal("flag_animation/flag_" + i + ".png"));
            frames.add(new TextureRegion(texture));
        }

        flagAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
        stateTime = 0f;
    }
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        font.dispose();
        for (TextureRegion region : flagAnimation.getKeyFrames()) {
            region.getTexture().dispose();
        }
        batch.dispose();
    }
    @Override public void show() {}
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stateTime += delta;
        TextureRegion currentFrame = flagAnimation.getKeyFrame(stateTime, true);

        font.getData().setScale(Gdx.graphics.getWidth() / 480f);
        layout.setText(font, "El huesaso loco");

        float x = (Gdx.graphics.getWidth() - layout.width) / 2f;
        float y = (Gdx.graphics.getHeight() + layout.height) / 2f;

        batch.begin();
        batch.draw(currentFrame, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        font.setColor(1f, 1f, 1f, 1f);
        font.draw(batch, layout, x,y);
        batch.end();

        if(Gdx.input.justTouched()) {
            AssetManager.titleSong.stop();
            game.setScreen(new GameScreen(game, 1));
        }
    }
}
