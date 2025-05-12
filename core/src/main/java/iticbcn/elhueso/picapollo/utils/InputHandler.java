package iticbcn.elhueso.picapollo.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import iticbcn.elhueso.picapollo.actors.Player;
import iticbcn.elhueso.picapollo.screens.GameScreen;

public class InputHandler implements InputProcessor {
    private Player player;
    private GameScreen screen;
    private Vector2 stageCoord;
    private Stage stage;

    public InputHandler(GameScreen screen){
        this.screen = screen;
        stage = screen.getStage();
        player = screen.getPlayer();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        float w = stage.getViewport().getScreenWidth();
        if (screenX < w * 0.33f) {
            player.moveLeft();
            System.out.println("left invocat");
        } else if (screenX > w * 0.33f && screenX < w * 0.67f) {
            player.moveRight();
            System.out.println("right invocat");
        } else {
            player.jump();
            System.out.println("JUMP invocat");
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        player.stopX();
        return true;
    }
    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
