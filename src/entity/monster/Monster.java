package entity.monster;

import entity.Entity;
import core.GamePanel;
import entity.utils.EntityType;

public class Monster extends Entity {


    public Monster(GamePanel gp) {

        super(gp);
        this.entityType = EntityType.MONSTER;
        isCollidable = true;
        spriteSpeed = 12;
    }

    @Override
    public void setAction() {
            if (move(direction, false)) return;

    }

    @Override
    public void tryStartAttack() {
        // Logica que inicia tentativa de ataque
    }

    @Override
    public void damageReaction() {
        // O que acontece ao levar dano
    }

    @Override
    public void deathReaction() {
        // O que acontece na morte
    }


    @Override
    public void update() {

        updateInvincibility();

        setAction();
        if (emitLight) {
            emitLight(lightRadius);
        }

        if (!isPassive) {
            attackIfInContact(this, EntityType.PLAYER);
        }
    }


    public void spawn(int col, int row) {
        this.worldX = col * gp.tileSize;
        this.worldY = row * gp.tileSize;
    }
}

