package core;

import core.input.KeyHandler;
import core.states.PlayState;
import core.states.TitleState;
import entity.*;
import entity.object.Object;
import entity.player.Player;
import entity.player.PlayerClass;
import main.Light;
import main.UI.OverlayManager;
import main.UI.UI;
import main.collision.CollisionChecker;
import main.collision.CollisionMap;
import main.event.EventHandler;
import main.sound.SoundManager;
import main.time.TimeManager;
import map.GameMap;
import map.*;
import entity.monster.*;
import entity.npc.Npc;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {

    // Debug
    public boolean debug = false;
    public boolean skinToolMode = false;

    // Screen Settings
    final int originalTileSize = 16;
    final int scale = 3;
    public int tileSize = originalTileSize * scale; // 48x48
    public final int maxScreenCol = 32;
    public final int maxScreenRow = 18;
    public int screenWidth = tileSize * maxScreenCol;
    public int screenHeight = tileSize * maxScreenRow;

    int FPS = 60;
    public int currentFPS = 0;
    public double lastUpdateTime;
    public double lastDrawTime;
    public int drawCount;
    public double tileDrawTime;
    public double entityDrawTime;
    public double overlayDrawTime;
    public double uiDrawTime;

    // World settings
    public final int maxWorldRow = 50;
    public final int maxWorldCol = maxWorldRow;
    public GameMap currentMap;

    // System
    public TileManager tileM = new TileManager(this);
    public TimeManager timeM = new TimeManager();
    public KeyHandler keyH = new KeyHandler(this);
    public Thread gameThread;
    public SoundManager soundM = new SoundManager();
    public UI ui = new UI(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public EventHandler eHandler = new EventHandler(this);
    public OverlayManager overlay = new OverlayManager(this);
    public CollisionMap collisionMap = new CollisionMap();

    // Game States
    public GameStateManager gsm = new GameStateManager(this);

    // Entities and objects
    public Player player;
    public List<Light> entityLights = new ArrayList<>();
    public ArrayList<Entity> entityList = new ArrayList<>();



    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        startTitleScreen();
    }


    // ============================================ SYSTEM ================================================
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1_000_000_000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            timer += (currentTime - lastTime);
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                long updateStart = System.nanoTime();
                update();
                long updateEnd = System.nanoTime();
                lastUpdateTime = (updateEnd - updateStart) / 1_000_000.0;

                repaint();
                delta--;
            }

            if (timer >= 1_000_000_000) {
                currentFPS = drawCount;
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        gsm.update();
    }

    // ============================================ PLAY STATE =============================================

    public void startLevel(GameMap map, PlayerClass playerClass) {

        // New Map & Setup
        currentMap = map;
        currentMap.setup();

        // New Player & Setup (NEEDS MAP LOADED)
        player = new Player(this, keyH, playerClass);
        entityList.add(player);

        // Start PlayState
        soundM.playSE("startbutton");

        gsm.setState(new PlayState(this));
    }


    public void resetLevel() {
        currentMap.reset();
        player.reset();
    }

    // ============================================ START SCREEN =============================================

    public void startTitleScreen() {
        gsm.setState(new TitleState(this));
    }

    // ========================================= PAINT COMPONENT / DRAW =====================================

    @Override
    public void paintComponent(Graphics g) {
        long paintStart = System.nanoTime();

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        gsm.draw(g2);

        long paintEnd = System.nanoTime();
        lastDrawTime = (paintEnd - paintStart) / 1_000_000.0;
        drawCount++;
    }

    public void exitGame() {
        System.exit(0);
    }
}
