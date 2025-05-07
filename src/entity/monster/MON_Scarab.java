package entity.monster;

import core.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MON_Scarab extends Monster {

    public MON_Scarab(GamePanel gp) {
        super(gp);

        name = "Cunana Scarab";
        skin = "Scarab";
        baseSpeed = 1.9;
        currentSpeed = baseSpeed;
        currentMaxHealth = 200;
        currentHealth = currentMaxHealth;
        currentAttackDamage = 16;
        souls = 200;
        hasIdleAnimation = false;
        isPassive = true;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        hasIdleAnimation = false;

        spriteSpeed = 12;
        emitLight = true;
        lightRadius = 4;

        chanceToMove = 100;
        getImage();
    }


    @Override
    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.player.direction;

    }

    @Override
    public void deathReaction() {
        gp.ui.addLog("Scarab heals the player!", Color.ORANGE);
        gp.player.currentHealth = gp.player.currentMaxHealth;
    }

}
