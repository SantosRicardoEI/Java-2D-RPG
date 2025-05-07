package entity.monster;

import core.GamePanel;

import java.util.Random;

public class MON_Dummy extends Monster {

    public MON_Dummy(GamePanel gp) {
        super(gp);

        name = "Enemy Dummy";
        skin = "dummy";
        hasIdleAnimation = true;
        currentSpeed = 0;
        currentMaxHealth = 150;
        currentHealth = currentMaxHealth;
        currentAttackDamage = 0;
        souls = 0;
        nameDisplay = true;
        isPassive = true;
        hasIdleAnimation = false;
        baseHealthRegen = 10;
        isCollidable = true;

        solidArea.x = 3;
        solidArea.y = 1;
        solidArea.width = 42;
        solidArea.height = 46;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }


    public void setAction() {

        actionLockCounter++;


        if (actionLockCounter == 120) {
            Random random = new Random();
            // 1 to 100
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }

    @Override
    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.player.direction;
    }



    @Override
    public void spawn(int col, int row) {
    }

    @Override
    public void update() {
        super.update();
        hpRecover();
        emitLight(5);

        if (isAlive) {
            isAlive = true;
        }
    }

}
