package iticbcn.elhueso.picapollo.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EndScreen implements Screen {
    Game game;
    Boolean isWin;

    private SpriteBatch batch;
    private BitmapFont font;
    private String message;
    private Color bgColor;
    private Color fontColor;

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
        }

        font.setColor(fontColor);

        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean touchDown(int x, int y, int pointer, int button){
                goToTitleScreen();
                return true;
            }
        });
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
}
