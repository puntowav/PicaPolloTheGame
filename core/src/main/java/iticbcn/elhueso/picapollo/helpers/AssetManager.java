package iticbcn.elhueso.picapollo.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class AssetManager {

    public static Texture playerTexture;
    public static Texture platformTexture;
    public static Texture spikeTexture;
    public static Texture enemyTexture;
    public static Texture goalTexture;
    public static Texture collectableTexture;
    public static Texture grass, playerHangTexture, playerDeath;
    public static Music titleSong;
    public static Music backgroundSong;
    public static Sound fartSound;
    public static Sound goalReached;
    public static Sound grabCollectable;

    public static Sound youDied;

    public static Sound boing;

    public static void load() {
        playerTexture      = new Texture("player.png");
        playerHangTexture      = new Texture("playerHang.png");
        playerDeath      = new Texture("dead.png");
        platformTexture    = new Texture("levels/platform.png");
        spikeTexture       = new Texture("levels/spike.png");
        enemyTexture       = new Texture("levels/enemy.png");
        goalTexture        = new Texture("levels/goal.png");
        collectableTexture = new Texture("levels/collectible.png");
        grass = new Texture("levels/grass.png");

        // music and sounds
        titleSong = Gdx.audio.newMusic(Gdx.files.internal("music/title_song.mp3"));
        backgroundSong = Gdx.audio.newMusic(Gdx.files.internal("music/background_music.mp3"));
        fartSound = Gdx.audio.newSound(Gdx.files.internal("sounds/fart.mp3"));
        goalReached = Gdx.audio.newSound(Gdx.files.internal("sounds/goal_reached.mp3"));
        grabCollectable = Gdx.audio.newSound(Gdx.files.internal("sounds/grab_collectable.mp3"));
        youDied = Gdx.audio.newSound(Gdx.files.internal("sounds/you-died.mp3"));
        boing = Gdx.audio.newSound(Gdx.files.internal("sounds/boing.mp3"));

    }

    public static void dispose() {
        playerTexture.dispose();
        platformTexture.dispose();
        spikeTexture.dispose();
        enemyTexture.dispose();
        goalTexture.dispose();
        collectableTexture.dispose();
        grass.dispose();
        titleSong.dispose();
        backgroundSong.dispose();
        fartSound.dispose();
        goalReached.dispose();
        grabCollectable.dispose();
        youDied.dispose();
        boing.dispose();
    }
}
