package map;

import entity.Entity;
import entity.utils.EntityFactory;
import entity.utils.EntityRenderer;
import entity.utils.EntityType;
import core.GamePanel;
import entity.monster.Monster;
import entity.monster.MonsterSpawner;
import entity.npc.Npc;
import main.Light;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class GameMap {
    protected GamePanel gp;

    // Map tiles, collision and lights
    protected String mapFile;
    protected String collisionFile;
    protected String tileSetPath;
    protected int tileSizeOriginal = 16;
    protected String lightsPath;

    // Settings
    protected int id;
    protected String displayName;
    protected String musicName;
    protected int startingTime;
    protected boolean timeFlows;
    public int timeFlowMultiplier = 1;
    protected boolean fixedTime;
    protected boolean weatherType;

    // Spawns
    protected List<SpawnPoint> entitySpawns = new ArrayList<>();
    public int playerSpawnX = 2;
    public int playerSpawnY = 2;
    public List<Light> lights = new ArrayList<>();
    public List<MonsterSpawner> recurringSpawns = new ArrayList<>();

    public GameMap(GamePanel gp) {
        this.gp = gp;
    }

    public void setup() {

        System.out.println("\n[MAP] Setting up Map:");

        System.out.println("[MAP] Current map: " + displayName);

        // Load map tiles, map structure, collisions, light sources, music
        if (tileSetPath != null) gp.tileM.loadTileSet(tileSetPath, tileSizeOriginal);

        if (mapFile != null) gp.tileM.loadMap(mapFile);

        if (collisionFile != null) gp.collisionMap.loadFromFile(collisionFile);

        gp.collisionMap.addBorderCollision(gp.maxWorldCol, gp.maxWorldRow);

        loadLightsFromFile(lightsPath);

        // Time
        System.out.println("[MAP] Setting map time");
        gp.timeM.time = startingTime;
        gp.timeM.timeSpeed = gp.timeM.timeSpeed * timeFlowMultiplier;
        gp.timeM.timeFlows = timeFlows;

        reset();

        System.out.println("[MAP] Map setup complete!\n");
    }

    public void reset() {
        System.out.println("[MAP] Reseting map...");
        System.out.println("[MAP] Clearing entity list");
        gp.entityLights.clear();
        gp.entityList.clear();
        System.out.println("[MAP] Clearing spawn points");
        entitySpawns.clear();

        System.out.println("[MAP] Creating spawn points..");
        setEntitySpawns();
        spawnMapEntities();

        gp.soundM.stopMusic();
        if (musicName != null) {
            gp.soundM.playMusic(musicName);
        } else {
            System.err.println("[MAP] Map music name is null.");
        }
    }

    // Pega na lista de spawns do mapa e, atarves de place entity, coloca na entityList
    public void spawnMapEntities() {
        System.out.println("[MAP] Spawning entities...");
        for (SpawnPoint spawn : entitySpawns) {
            Entity entity = EntityFactory.create(gp, spawn.id, spawn.type);
            System.out.println("[MAP] " + entity.name + " created");
            if (entity != null) {
                System.out.println("[MAP] " + entity.name + " added to spawn list");
                placeEntity(spawn.col, spawn.row, entity);
            } else {
                System.out.println("[MAP] Couldn't add this entity to spawn list!");
            }
        }
    }

    public void updateRecurringSpawns(GamePanel gp) {
        for (MonsterSpawner spawn : recurringSpawns) {
            spawn.update(this);
        }
    }

    protected void addCollisionPoint(int col, int row) {
        gp.collisionMap.addCollision(col, row);
    }

    public void loadLightsFromFile(String filePath) {
        System.out.println("[MAP] Loading map light sources :" + lightsPath);
        lights.clear();

        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            if (is == null) {
                System.err.println("[MAP] Map lights file not found: " + filePath);
                return;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");
                if (tokens.length == 4) {
                    String id = tokens[0];
                    int col = Integer.parseInt(tokens[1]);
                    int row = Integer.parseInt(tokens[2]);
                    int radius = Integer.parseInt(tokens[3]);
                    lights.add(new Light(id, col, row, radius, gp.overlay.staticDist, gp.overlay.staticColors));
                }
            }

            br.close();
        } catch (Exception e) {
            System.err.println("[MAP] Error loading map lights file: " + filePath);
            e.printStackTrace();
        }
    }

    public boolean canSpawnAt(int worldX, int worldY) {

        // Verificar se o ponto é uma colisão de mapa
        int col = worldX / gp.tileSize;
        int row = worldY / gp.tileSize;
        if (gp.collisionMap.collisionPoints.contains(new Point(col, row))) {
            return false; // Tem parede, não pode spawnar
        }

        Rectangle tempArea = new Rectangle(worldX, worldY, gp.tileSize, gp.tileSize);

        // Verificar colisão com entidades existentes (incluindo player)
        for (Entity entity : gp.entityList) {
            if (entity != null && entity.isCollidable) {
                if (tempArea.intersects(entity.getSolidAreaWorld())) {
                    return false;
                }
            }
        }
        // se passou tudo a entity pode spawnar
        return true;
    }

    // Adiciona á entityList
    public boolean placeEntity(int col, int row, Entity entity) {
        entity.realX = gp.tileSize * col;
        entity.realY = gp.tileSize * row;

        entity.worldX = (int) entity.realX;
        entity.worldY = (int) entity.realY;


        for (int i = 0; i < gp.entityList.size(); i++) {
            if (gp.entityList.get(i) == null) {
                gp.entityList.set(i, entity);
                return true;
            }
        }
        gp.entityList.add(entity);
        System.out.println("[MAP] " + entity.name + " spawned!");
        return true;
    }

    public void setEntitySpawns() {


    }

    public void update() {
        updateRecurringSpawns(gp);
    }

}