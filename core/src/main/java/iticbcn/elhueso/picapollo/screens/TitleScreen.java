package iticbcn.elhueso.picapollo.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import iticbcn.elhueso.picapollo.helpers.AssetManager;
import iticbcn.elhueso.picapollo.utils.Settings;

public class TitleScreen implements Screen {

    private OrthographicCamera camera;
    private FitViewport viewport;
    private Stage stage;
    private Batch batch;
    private Game game;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private GlyphLayout layout;


    public TitleScreen(Game game) {
        this.game = game;
        // AssetManager.music.play() -> la música del jogo
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(true, Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        camera.position.set(Settings.GAME_WIDTH / 2f, Settings.GAME_HEIGHT / 2f, 0);
        camera.update();
        viewport = new FitViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);
        stage = new Stage(viewport);
        batch = stage.getBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/pixel_emulator.fnt"));
        layout = new GlyphLayout(font, "El huesaso loco");
    }
    @Override public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        stage.dispose();
        shapeRenderer.dispose();
        font.dispose();
    }
    @Override public void show() {}
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        font.getData().setScale(1f, -1f);

        viewport.apply();
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(173f / 255f, 216f / 255f, 230f / 255f, 1f);
        shapeRenderer.rect(0, 0, Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        shapeRenderer.end();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        float x = (Settings.GAME_WIDTH - layout.width) / 2;
        float y = (Settings.GAME_HEIGHT + layout.height) / 2 + 100;
        font.draw(batch, layout, x,y);
        batch.end();

        if(Gdx.input.justTouched()) {
            game.setScreen(new GameScreen(game, 1));
            dispose();
        }
    }
}
