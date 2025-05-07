package entity.monster;

import core.GamePanel;

public class MON_GreenSlime extends Monster {

    public MON_GreenSlime(GamePanel gp) {
        super(gp);

        name = "Green Slime";
        skin = "greenslime";
        baseSpeed = 0.2;
        currentSpeed = baseSpeed;
        currentMaxHealth = 300;
        currentHealth = currentMaxHealth;
        currentAttackDamage = 10;
        souls = 15;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        spriteSpeed = 12;
        hasIdleAnimation = true;
        isSingleAnimation = true;
        baseAttackDamage = 30;


        getImage();
    }


    @Override
    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.player.direction;
    }

}
