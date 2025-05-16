package iticbcn.elhueso.picapollo.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import iticbcn.elhueso.picapollo.helpers.AssetManager;

public class EndScreen implements Screen {
    Game game;
    Boolean isWin;

    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont font;
    private String message;
    private Color bgColor;
    private Color fontColor;
    private float spinnerAngle = 0f;
    private Texture buttonSheet;
    private Image retryBtn;

    private Texture backgroundLose;

    private Texture backgroundWin;


    public EndScreen(Game game, Boolean isWin){
        this.game = game;
        this.isWin = isWin;
    }

    @Override
    public void show() {
        backgroundLose = new Texture(Gdx.files.internal("endScreenLose.png"));
        backgroundWin = new Texture(Gdx.files.internal("endScreenWin.png"));
        stage = new Stage();
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/pixel_emulator.fnt"));
        font.getData().setScale(4f);

        if(isWin){
            AssetManager.yeahh.play();
            message = "WIN";
            fontColor = new Color(1f, 0.9f, 0.1f, 1f);
        }else{
            AssetManager.youDied.play();
            message = "DEAD";
            fontColor = new Color(0.8f, 0.05f, 0.05f, 1f);
            buttonSheet = new Texture(Gdx.files.internal("buttons.png"));
            TextureRegion retryRegion = getRegion(buttonSheet, 3, 1);

            retryBtn = new Image(retryRegion);
            retryBtn.setSize(200, 200);
            retryBtn.setPosition(
                Gdx.graphics.getWidth() / 2f - retryBtn.getWidth() / 2f,
                Gdx.graphics.getHeight() / 2f - retryBtn.getHeight() / 2f - 250f
            );

            retryBtn.getColor().a = 0.4f;

            retryBtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    AssetManager.boing.play();
                    goToTitleScreen();
                }
            });

            stage.addActor(retryBtn);
            Gdx.input.setInputProcessor(stage);
        }
        font.setColor(fontColor);
    }

    public void goToTitleScreen(){
        AssetManager.backgroundSong.stop();
        game.setScreen(new TitleScreen(game));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        float width  = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        batch.draw(isWin ? backgroundWin : backgroundLose, 0, 0, width, height);

        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, message);
        float textWidth = layout.width;
        float textHeight = layout.height;

        float time = (float) Math.sin(System.currentTimeMillis() / 300.0);
        float alpha = 0.75f + 0.25f * time;

        font.setColor(fontColor.r, fontColor.g, fontColor.b, alpha);

        float x = (width  - textWidth) / 2f;
        float y = (height + textHeight) / 2f;

        Color shadowColor = isWin ? new Color(0.6f, 0.5f, 0f, alpha) : new Color(0f, 0f, 0f, alpha);
        font.setColor(shadowColor);
        font.draw(batch, message, x + 2, y - 2);

        font.setColor(fontColor.r, fontColor.g, fontColor.b, alpha);
        font.draw(batch, message, x, y);


        font.setColor(fontColor.r, fontColor.g, fontColor.b, 1f);

        batch.end();
        stage.act(delta);
        stage.draw();
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
        batch.dispose();
        font.dispose();
        stage.dispose();
        if(buttonSheet!=null) buttonSheet.dispose();
        if (backgroundLose != null) backgroundLose.dispose();
    }
    private TextureRegion getRegion(Texture sheet, int col, int row) {
        int size = 32;
        TextureRegion region = new TextureRegion(sheet, col * size, row * size, size, size);
        region.flip(false, true);
        return region;
    }
}
