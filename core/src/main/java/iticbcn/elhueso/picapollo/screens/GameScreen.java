package iticbcn.elhueso.picapollo.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.InputMultiplexer;

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

    // Constants
    private static final Color BACKGROUND_COLOR = new Color(0, 0, 0, 1); // Negre per al fons
    private static final int LAST_LEVEL_NUM = 3;
    private static final float TILE_SIZE = 1f;

    private int levelNum;
    private Pixmap layout;

    private OrthographicCamera camera;
    private FitViewport viewport;
    private Stage stage;
    private Stage hudStage;

    private Game game;

    // Actors
    private List<PPGRectangle> allLevelActors;
    private List<Platform> levelPlatforms;
    private List<SpikesPlatform> levelSpikes;
    private List<Collectable> levelCollectables;
    private Player player;
    private Goal goal;

    // DEBUG TOOLS
    private ShapeRenderer shapeRenderer;
    private List<PPGRectangle> debugRectangles;

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

        // Llistes d'actors
        levelPlatforms = new ArrayList<>();
        levelSpikes = new ArrayList<>();
        levelCollectables = new ArrayList<>();

        // Obrim el mapa del nivell com a un  Bitmap
        layout = new Pixmap(Gdx.files.internal("levels/trial_level_" + levelNum + "_layout.png"));
        List<PPGRectangle> blocs = detectBlocksByColor(layout);

        for (PPGRectangle rect : blocs) {
            createActor(rect);
        }
        System.out.println("Fi del constructor");
    }

    @Override
    public void show() {
        hudStage = new Stage(viewport);
        InputMultiplexer mux = new InputMultiplexer(hudStage, stage);
        Gdx.input.setInputProcessor(mux);



        Texture sheet = new Texture(Gdx.files.internal("buttons.png"));

        // Extraer regiones del spritesheet (col, fila)
        TextureRegion left = getRegion(sheet, 0, 7);   // col 0, fila 7
        TextureRegion right = getRegion(sheet, 1, 6); // col 1, fila 6
        TextureRegion jump = getRegion(sheet, 0, 5);   // col 0, fila 5

        // Crear botones y escalarlos para que se vean más grandes (64x64)
        Image leftBtn = new Image(left);
        Image rightBtn = new Image(right);
        Image jumpBtn = new Image(jump);

        leftBtn.setSize(128, 128);
        rightBtn.setSize(128, 128);
        jumpBtn.setSize(128, 128);

        float btnSize = 128;
        float margin = 20;
        float btnY = Settings.GAME_HEIGHT - btnSize - margin;

        leftBtn.setPosition(-10, btnY);
        rightBtn.setPosition(200, btnY);
        jumpBtn.setPosition(Settings.GAME_WIDTH - btnSize - 40, btnY);

        leftBtn.getColor().a = 0.4f;
        rightBtn.getColor().a = 0.4f;
        jumpBtn.getColor().a = 0.4f;

        // Añadir listeners
        leftBtn.addListener(new ClickListener() {
            public boolean touchDown(InputEvent e, float x, float y, int p, int b) {
                player.moveLeft(); return true;
            }
            public void touchUp(InputEvent e, float x, float y, int p, int b) {
                player.stopX();
            }
        });
        rightBtn.addListener(new ClickListener() {
            public boolean touchDown(InputEvent e, float x, float y, int p, int b) {
                player.moveRight(); return true;
            }
            public void touchUp(InputEvent e, float x, float y, int p, int b) {
                player.stopX();
            }
        });
        jumpBtn.addListener(new ClickListener() {
            public boolean touchDown(InputEvent e, float x, float y, int p, int b) {
                player.jump(); return true;
            }
        });

        // Añadir al stage
        hudStage.addActor(leftBtn);
        hudStage.addActor(rightBtn);
        hudStage.addActor(jumpBtn);

    }

    public void render(float delta) {
        // DEBUG
        Gdx.gl.glClearColor(173f / 255f, 216f / 255f, 230f / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        stage.act(delta);
        checkPlatform(delta);
        checkSpikes();
        checkCollectables();
        checkGoal();
        stage.draw();
        hudStage.act(delta);
        hudStage.draw();

//        shapeRenderer.setProjectionMatrix(camera.combined);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//
//        PPGRectangle pb = player.getBounds();
//        shapeRenderer.setColor(Color.RED);
//        shapeRenderer.rect(pb.x, pb.y, pb.width, pb.height);
//
//        shapeRenderer.setColor(Color.GREEN);
//        for (Platform plat : levelPlatforms) {
//            PPGRectangle b = plat.getBounds();
//            shapeRenderer.rect(b.x, b.y, b.width, b.height);
//        }
//
//        shapeRenderer.end();
    }

    public void checkPlatform(float delta) {
        if (player == null) return;

        // Guarda la Y anterior per comparar
        float oldY = player.getY();

        // Aplica gravetat i mou X
        player.applyGravity(delta);
        player.moveX(delta);

        // Resolució de colisió horizontal
        for (Platform plat : levelPlatforms) {
            if (player.getBounds().overlaps(plat.getBounds())) {
                float platBottom = plat.getY();
                float platTop    = platBottom + plat.getHeight();
                boolean vertOverlap =
                    player.getY() + player.getHeight() > platBottom &&
                        player.getY()             < platTop;
                if (vertOverlap) {
                    if (player.getVelocity().x > 0) {
                        player.setX(plat.getX() - player.getWidth());
                    } else if (player.getVelocity().x < 0) {
                        player.setX(plat.getX() + plat.getWidth());
                    }
                    player.stopX();
                }
            }
        }

        // 3) Mueve Y y comprueba aterrizajes/techo
        player.moveY(delta);
        boolean landed = false;
        for (Platform plat : levelPlatforms) {
            // aterrizaje usando isLandingOn
            if (player.isLandingOn(plat)) {
                player.landOn(plat);
                landed = true;
                break;
            }
            // choque con techo (subiendo)
            if (player.getBounds().overlaps(plat.getBounds())
                && player.getVelocity().y > 0) {
                // reposiciona justo debajo de la plataforma
                player.setY(plat.getY() - player.getHeight());
                // anula velocidad vertical
                player.getVelocity().y = 0;
                player.getBounds().setPosition(player.getX(), player.getY());
                player.resetJumps(); // ← aquí recuperas el salto al golpear por debajo
                break;
            }
        }

        // 4) Si no aterrizamos, estamos en el aire
        if (!landed && player.getVelocity().y != 0) {
            player.fallOffPlatform();
        }

        Gdx.app.log("Player",
            "checkPlatform: landed=" + landed +
                "  onGround=" + player.getOnGround()
        );
    }


    private void checkSpikes() {
        for (SpikesPlatform spike : levelSpikes) {
            if (player.getBounds().overlaps(spike.getBounds())) {
                onPlayerDeath();
                return;
            }
        }
    }

    public  void checkCollectables(){
        if (player == null) return;
        for(int i = 0; i < levelCollectables.size(); i++){
            Collectable coll = levelCollectables.get(i);
            if(player.getBounds().overlaps(coll.getBounds())){
                coll.remove();
                levelCollectables.remove(i);
            }

        }

        if(levelCollectables.isEmpty() && goal != null){
            goal.setVisible(true);
        }
    }

    public void checkGoal(){
        if (player == null) return;
        if(goal != null && goal.isVisible() && player.getBounds().overlaps(goal.getBounds())){
            game.setScreen(new EndScreen(game, true));
        }
    }

    public void onPlayerDeath(){
        game.setScreen(new EndScreen(game,false));
        dispose();
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

    public Stage getStage() {
        return stage;
    }

    public Player getPlayer(){return player;}

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
            Platform p = new Platform(AssetManager.platformTexture, rect);
            levelPlatforms.add(p);
            stage.addActor(p);

        } else if (isSpike(c)) {
            SpikesPlatform sp = new SpikesPlatform(AssetManager.spikeTexture, rect);
            levelSpikes.add(sp);
            stage.addActor(sp);

        } else if (isCollectable(c)) {
            Collectable coll = new Collectable(AssetManager.collectableTexture, rect);
            levelCollectables.add(coll);
            stage.addActor(coll);

        } else if (isEnemy(c)) {
            Enemy e = new Enemy(AssetManager.enemyTexture, rect);
            stage.addActor(e);

        } else if (isGoal(c)) {
            goal = new Goal(AssetManager.goalTexture, rect);
            goal.setVisible(false);
            stage.addActor(goal);

        } else if (isPlayerSpawn(c)) {
            player = new Player(AssetManager.playerTexture, rect);
            stage.addActor(player);
        }
    }

    // Detectors de colors
    private boolean isPlatform(Color c) {
        return c.r > 0.5f && c.g < 0.4f && c.b < 0.1f;
    }

    private boolean isSpike(Color c) {
        return c.r > 0.9f && c.g < 0.1f && c.b < 0.1f;
    }

    private boolean isEnemy(Color c) {
        return c.r > 0.5f && c.b > 0.5f && c.g < 0.1f;
    }

    private boolean isCollectable(Color c) {
        return c.r > 0.9f && c.g > 0.9f && c.b < 0.1f;
    }

    private boolean isGoal(Color c) {
        return c.g > 0.9f && c.r < 0.1f && c.b < 0.1f;
    }

    private boolean isPlayerSpawn(Color c) {
        return c.r < 0.1f && c.g > 0.2f && c.b > 0.5f;
    }
    private TextureRegion getRegion(Texture sheet, int col, int row) {
        int size = 32;
        TextureRegion region = new TextureRegion(sheet, col * size, row * size, size, size);
        region.flip(false, true);
        return region;
    }
}
