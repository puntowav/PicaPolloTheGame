package iticbcn.elhueso.picapollo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import iticbcn.elhueso.picapollo.helpers.AssetManager;
import iticbcn.elhueso.picapollo.screens.GameScreen;
import iticbcn.elhueso.picapollo.screens.TitleScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends Game {

    @Override
    public void create() {
        //AssetManager.load();
        setScreen(new TitleScreen(this));

    }

    @Override
    public void dispose() {
        super.dispose();
        //AssetManager.dispose();
    }
}
