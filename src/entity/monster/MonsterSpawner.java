package entity.monster;

import entity.utils.EntityType;
import core.GamePanel;
import entity.utils.EntityFactory;
import map.GameMap;


public class MonsterSpawner {

    private final GamePanel gp;
    private final String monsterId;
    private final int col, row;
    private final int interval;
    private int timer;


    public MonsterSpawner(GamePanel gp, String monsterId, int col, int row, int interval) {
        this.gp = gp;
        this.monsterId = monsterId;
        this.col = col;
        this.row = row;
        this.interval = interval;
        this.timer = 0;
    }

    public void update(GameMap map) {
        timer++;
        if (timer >= interval) {
            timer = 0;

            int x = col * gp.tileSize;
            int y = row * gp.tileSize;

            System.out.println("\n[MSPAWNER] " + monsterId + " tried to spawn at (" + col + ", " + row + ")");
            if (map.canSpawnAt(x, y)) {
                Monster monster = EntityFactory.createMonster(gp, monsterId);
                if (monster != null) {

                    monster.spawn(col, row);
                    gp.currentMap.placeEntity(col, row, monster);
                }
            } else {
                System.out.println("[MSPAWNER] " + monsterId + " cant spawn at (" + col + ", " + row + ")\n");
            }
        }
    }
}