package entity.monster;

import core.GamePanel;

import java.util.Random;

public class MON_Bat extends Monster{

    public MON_Bat(GamePanel gp) {
        super(gp);

        name = "Bat";
        skin = "bat";
        baseSpeed = 4;
        currentSpeed = baseSpeed;
        currentMaxHealth = 30;
        currentHealth = currentMaxHealth;
        currentAttackDamage = 10;
        souls = 4;
        isPassive = false;
        hasIdleAnimation = true;

        solidArea.x = 9;
        solidArea.y = 9;
        solidArea.width = 30;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        baseAttackDamage = 20;


        getImage();
    }



    @Override
    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.player.direction;
    }


    @Override
    public void update() {
        super.update();
    }

}
