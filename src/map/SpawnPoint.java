package map;

import entity.utils.EntityType;

public class SpawnPoint {
    public int col, row;
    public String id;
    public EntityType type;

    public SpawnPoint(String id,int col, int row, EntityType type) {
        this.id = id;
        this.col = col;
        this.row = row;
        this.type = type;
    }
}