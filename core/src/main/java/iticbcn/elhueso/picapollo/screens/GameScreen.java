package iticbcn.elhueso.picapollo.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Predicate;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.List;

import iticbcn.elhueso.picapollo.actors.Collectable;
import iticbcn.elhueso.picapollo.actors.Enemy;
import iticbcn.elhueso.picapollo.actors.Goal;
import iticbcn.elhueso.picapollo.actors.Platform;
import iticbcn.elhueso.picapollo.actors.Player;
import iticbcn.elhueso.picapollo.actors.Spikes;
import iticbcn.elhueso.picapollo.helpers.AssetManager;
import iticbcn.elhueso.picapollo.utils.PPGRectangle;


public class GameScreen implements Screen {

    private int levelNum;
    private Stage stage;

    private static final Color BACKGROUND_COLOR = new Color(0, 0, 0, 1); // Negre per al fons
    private static final int LAST_LEVEL_NUM = 3;
    private static final float TILE_SIZE = 32;

    public GameScreen(int levelNum) {
        this.levelNum = levelNum;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());

        // Carreguem la imatge
        Pixmap layout = new Pixmap(Gdx.files.internal("level_" + levelNum + "_layout.png"));
        // Obtenim els rectangles de colors del mapa
        List<PPGRectangle> blocs = detectBlocksByColor(layout, TILE_SIZE, BACKGROUND_COLOR);

        for (PPGRectangle rect : blocs) {
            createActor(rect);
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

    // Instancia un actor segons les propietats del rectangle
    private void createActor(PPGRectangle rect) {
        Color c = rect.getColor();

        if (isPlatform(c)) {

        } else if (isCollectable(c)) {

        } else if (isEnemy(c)) {

        } else if (isGoal(c)) {

        } else if (isSpike(c)) {

        } else if (isPlayerSpawn(c)) {

        }
    }

    // Algoritme que detecta blocs de colors en un mapa
    // Donat un mapa, el recorre cap a la dreta i cap a baix
    // Busca blocs de pixels d'un mateix color i crea un rectangle per a cada un.
    private List<PPGRectangle> detectBlocksByColor(Pixmap map, float tileSize, Color backgroundColor) {
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
                if (baseColor.equals(backgroundColor)) {
                    continue;
                }

                // Busquem els pixels contigus vertical i horitzontal del mateix color
                // Primer horitzontal. Trobem la primera fila de pixels
                int rectWidth = 0;
                while (x + rectWidth < mapWidth) {
                    Color c = new Color();
                    Color.rgba8888ToColor(c, map.getPixel(x + rectWidth, y));
                    if (!c.equals(baseColor) || utilitzat[x+rectWidth][y]) continue;
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
                float worldX = x * tileSize;
                float worldY = y * tileSize;

                PPGRectangle rectangle = new PPGRectangle(worldX, worldY, rectWidth * tileSize, rectHeight * tileSize, baseColor);
                blocs.add(rectangle);
            }
        }
        return blocs;
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
