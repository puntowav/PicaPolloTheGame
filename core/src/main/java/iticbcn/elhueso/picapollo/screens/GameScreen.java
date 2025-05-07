package iticbcn.elhueso.picapollo.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.List;

import iticbcn.elhueso.picapollo.actors.Collectable;
import iticbcn.elhueso.picapollo.actors.Enemy;
import iticbcn.elhueso.picapollo.actors.Goal;
import iticbcn.elhueso.picapollo.actors.Platform;
import iticbcn.elhueso.picapollo.actors.Player;
import iticbcn.elhueso.picapollo.actors.SpikesPlatform;
import iticbcn.elhueso.picapollo.helpers.AssetManager;
import iticbcn.elhueso.picapollo.utils.PPGRectangle;
import iticbcn.elhueso.picapollo.utils.Settings;


public class GameScreen implements Screen {

    private int levelNum;
    private Pixmap layout;

    private OrthographicCamera camera;
    private FitViewport viewport;
    private Stage stage;

    private Game game;

    private ShapeRenderer shapeRenderer;
    private List<PPGRectangle> debugRectangles;

    private static final Color BACKGROUND_COLOR = new Color(0, 0, 0, 1); // Negre per al fons
    private static final int LAST_LEVEL_NUM = 3;
    private static final float TILE_SIZE = 1f;

    public GameScreen(Game game, int levelNum) {
        this.game = game;
        this.levelNum = levelNum;
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(true, Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        camera.position.set(Settings.GAME_WIDTH / 2f, Settings.GAME_HEIGHT / 2f, 0);
        camera.update();
        viewport = new FitViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);
        stage = new Stage(viewport);
    }

    @Override
    public void show() {

        // Carreguem la imatge
        layout = new Pixmap(Gdx.files.internal("levels/trial_level_" + levelNum + "_layout.png"));
        System.out.println("PIXMAP LOADED: " + layout.getWidth() + "x" + layout.getHeight());
        // Obtenim els rectangles de colors del mapa
        // List<PPGRectangle> blocs = detectBlocksByColor(layout, TILE_SIZE, BACKGROUND_COLOR);

//        for (PPGRectangle rect : blocs) {
//            createActor(rect);
//        }

        // DEBUG
        debugRectangles = detectBlocksByColor(layout);
        System.out.println("RECTANGLES TROBATS: " + debugRectangles.size());
    }

    public void render(float delta) {
        // DEBUG
        Gdx.gl.glClearColor(173f / 255f, 216f / 255f, 230f / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (PPGRectangle rect : debugRectangles) {
            shapeRenderer.setColor(rect.getColor());
            shapeRenderer.rect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        }
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        layout.dispose();
        shapeRenderer.dispose();
        stage.dispose();
    }

    // Algoritme que detecta blocs de colors en un mapa
    // Donat un mapa, el recorre cap a la dreta i cap a baix
    // Busca blocs de pixels d'un mateix color i crea un rectangle per a cada un.
    private List<PPGRectangle> detectBlocksByColor(Pixmap map) {
        System.out.println("COMENCEM A BUSCAR RECTANGLES");
        int mapWidth = map.getWidth();
        int mapHeight = map.getHeight();

        // Matriu per representar quins pixels del mapa ja hem utilitzat
        boolean[][] utilitzat = new boolean[mapWidth][mapHeight];
        List<PPGRectangle> blocs = new ArrayList<>();

        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                if (utilitzat[x][y]) {
                    continue;
                }

                Color baseColor = new Color();
                Color.rgba8888ToColor(baseColor, map.getPixel(x, y));

                // Ignorem pixels del fons
                if (baseColor.equals(BACKGROUND_COLOR)) {
                    continue;
                }

                // Busquem els pixels contigus vertical i horitzontal del mateix color
                // Primer horitzontal. Trobem la primera fila de pixels
                int rectWidth = 0;
                while (x + rectWidth < mapWidth) {
                    Color c = new Color();
                    Color.rgba8888ToColor(c, map.getPixel(x + rectWidth, y));
                    if (!c.equals(baseColor) || utilitzat[x+rectWidth][y]) break;
                    utilitzat[x + rectWidth][y] = true;
                    rectWidth++;
                }
                // Allarguem les columnes
                int rectHeight = 1; // Iniciem en 1 perquè ja hem fet la primera fila
                boolean potExpandir = true;
                while (y + rectHeight < mapHeight && potExpandir) {
                    for (int rx = 0; rx < rectWidth; rx++) {
                        Color c = new Color();
                        Color.rgba8888ToColor(c, map.getPixel(x + rx, y + rectHeight));
                        if (!c.equals(baseColor) || utilitzat[x + rx][y + rectHeight]) {
                            potExpandir = false;
                            break;
                        }
                        utilitzat[x + rx][y + rectHeight] = true;
                    }
                    if (potExpandir) rectHeight++;
                }
                // Posició en pantalla
                float worldX = x * TILE_SIZE;
                float worldY = y * TILE_SIZE;

                // Tamany del rectangle
                float sizeX = rectWidth * TILE_SIZE;
                float sizeY = rectHeight * TILE_SIZE;

                PPGRectangle rectangle = new PPGRectangle(worldX, worldY, sizeX, sizeY, baseColor);
                blocs.add(rectangle);
                System.out.println("RECTANGLE TROBAT");
            }
        }
        System.out.println("ACABA LA BUSQUEDA DE RECTANGLES");
        return blocs;
    }

    // Instancia un actor segons les propietats del rectangle
    private void createActor(PPGRectangle rect) {
        Color c = rect.getColor();

        if (isPlatform(c)) {
            stage.addActor(new Platform(AssetManager.platformTexture, rect));
        } else if (isCollectable(c)) {
            stage.addActor(new Collectable(AssetManager.collectableTexture, rect));
        } else if (isEnemy(c)) {
            stage.addActor(new Enemy(AssetManager.enemyTexture, rect));
        } else if (isGoal(c)) {
            stage.addActor(new Goal(AssetManager.platformTexture, rect));
        } else if (isSpike(c)) {
            stage.addActor(new SpikesPlatform(AssetManager.platformTexture, rect));
        } else if (isPlayerSpawn(c)) {
            stage.addActor(new Player(AssetManager.playerTexture, rect));
        }
    }

    // Detectors de colors
    private boolean isPlatform(Color c)    {
        return c.r>.5f&&c.g<.5f&&c.b<.1f;
    }
    private boolean isSpike(Color c)       {
        return c.r> .9f&&c.g<.1f&&c.b<.1f;
    }
    private boolean isEnemy(Color c)       {
        return c.r>.5f&&c.b>.5f&&c.g<.1f;
    }
    private boolean isGoal(Color c)        {
        return c.g> .9f&&c.r<.1f&&c.b<.1f;
    }
    private boolean isCollectable(Color c) {
        return c.r> .9f&&c.g> .9f&&c.b<.1f;
    }
    private boolean isPlayerSpawn(Color c) {
        return c.b> .7f&&c.g>.1f&&c.g<.7f;
    }
}
