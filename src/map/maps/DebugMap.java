package map.maps;

import core.GamePanel;
import entity.monster.MonsterSpawner;
import entity.utils.EntityType;
import map.GameMap;
import map.SpawnPoint;

public class DebugMap extends GameMap {

    public DebugMap(GamePanel gp) {
        super(gp);

        // ============================== Map settings =================================

        id = 0;
        mapFile = "/maps/debugWorld/world.txt";
        collisionFile = "/maps/debugWorld/collision.txt";
        tileSetPath = "/tiles/earthgrid.png";
        lightsPath = "/maps/debugWorld/lights.txt";
        displayName = "Village Map";
        musicName = "dark";

        playerSpawnX = 18 * gp.tileSize;
        playerSpawnY = 14 * gp.tileSize;

        startingTime = 0;
        timeFlows =true;
        timeFlowMultiplier = 1;

    }

    @Override
    public void setEntitySpawns() {

        entitySpawns.add(new SpawnPoint("greenSlime", 5, 5, EntityType.MONSTER));
        entitySpawns.add(new SpawnPoint("bonfire", 17, 14, EntityType.OBJECT));

        entitySpawns.add(new SpawnPoint("scarab", 40, 40, EntityType.MONSTER));
        entitySpawns.add(new SpawnPoint("greenSlime", 12, 10, EntityType.MONSTER));

        entitySpawns.add(new SpawnPoint("bonfire", 17, 14, EntityType.OBJECT));
        entitySpawns.add(new SpawnPoint("bonfire", 42, 42,EntityType.OBJECT));

        entitySpawns.add(new SpawnPoint("scarab", 10, 12, EntityType.MONSTER));

        entitySpawns.add(new SpawnPoint("scarab", 14, 18, EntityType.MONSTER));

        entitySpawns.add(new SpawnPoint("bat", 10, 11, EntityType.MONSTER));

        entitySpawns.add(new SpawnPoint("ghost", 2, 2, EntityType.MONSTER));

        entitySpawns.add(new SpawnPoint("redSlime", 14, 15, EntityType.MONSTER));

        entitySpawns.add(new SpawnPoint("oldMan", 16, 16, EntityType.NPC));





        recurringSpawns.add(new MonsterSpawner(gp, "scarab", 30, 30, 1100));
        recurringSpawns.add(new MonsterSpawner(gp, "greenSlime", 30, 30, 900));
        recurringSpawns.add(new MonsterSpawner(gp, "greenSlime", 9, 9, 1050));
        recurringSpawns.add(new MonsterSpawner(gp, "bat", 10, 45, 1100));
        recurringSpawns.add(new MonsterSpawner(gp, "bat", 25, 10, 1000));
        recurringSpawns.add(new MonsterSpawner(gp, "ghost", 8, 8, 1200));
    }
}