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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

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
    private boolean playerDead = false;


    public EndScreen(Game game, Boolean isWin){
        this.game = game;
        this.isWin = isWin;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2f);

        if(isWin){
            message = "WIN";
            bgColor = Color.BLUE;
            fontColor = Color.WHITE;
        }else{
            message = "DEAD";
            bgColor = Color.RED;
            fontColor = Color.BLACK;
            buttonSheet = new Texture(Gdx.files.internal("buttons.png"));
            TextureRegion retryRegion = getRegion(buttonSheet, 1, 3);

            retryBtn = new Image(retryRegion);
            retryBtn.setSize(128, 128);
            retryBtn.setPosition(Gdx.graphics.getWidth() / 2f - 64, Gdx.graphics.getHeight() / 2f - 64);

            retryBtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    goToTitleScreen();
                }
            });

            stage = new Stage();
            stage.addActor(retryBtn);
            Gdx.input.setInputProcessor(stage);
        }

        font.setColor(fontColor);

        InputAdapter fallbackClick = new InputAdapter() {
            @Override
            public boolean touchDown(int x, int y, int pointer, int button) {
                goToTitleScreen();
                return true;
            }
        };
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, fallbackClick));
    }

    public void goToTitleScreen(){
        game.setScreen(new TitleScreen(game));
        dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(bgColor.r, bgColor.g, bgColor.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        float width  = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        float textWidth  = font.getRegion().getRegionWidth() * font.getScaleX() * message.length() * 0.5f;
        float textHeight = font.getCapHeight() * font.getScaleY();

        font.draw(batch,
            message,
            (width  - textWidth)  / 2f,
            (height + textHeight) / 2f);
        batch.end();
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
    }
    private TextureRegion getRegion(Texture sheet, int col, int row) {
        int size = 32;
        TextureRegion region = new TextureRegion(sheet, col * size, row * size, size, size);
        region.flip(false, true);
        return region;
    }
}
