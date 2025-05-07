package entity.monster;

import core.GamePanel;
import main.Light;

public class MON_Ghost extends Monster {

    public MON_Ghost(GamePanel gp) {
        super(gp);

        name = "Ghost";
        skin = "Fantasma";
        baseSpeed = 1;
        currentSpeed = baseSpeed;
        currentMaxHealth = 500;
        currentHealth = currentMaxHealth;
        currentAttackDamage = 5;
        souls = 50;
        hasIdleAnimation = true;

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 48;
        solidArea.height = 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        emitLight = true;
        lightRadius = 1;
        baseAttackDamage = 10;


        getImage();
    }




    @Override
    public void damageReaction() {

    }


    @Override
    public void update() {
        super.update();

    }

}
