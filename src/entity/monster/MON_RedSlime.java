package entity.monster;

import core.GamePanel;

public class MON_RedSlime extends Monster{

    public MON_RedSlime(GamePanel gp) {
        super(gp);

        name = "Boss Slime";
        skin = "redslime";
        baseSpeed = 3.4;
        currentSpeed = baseSpeed;
        currentMaxHealth = 3000;
        currentHealth = currentMaxHealth;
        currentAttackDamage = 10;
        souls = 900;
        hasIdleAnimation = true;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        baseAttackDamage = 50;

        getImage();
    }





    @Override
    public void damageReaction() {
        actionLockCounter = 30;
    }


    @Override
    public void update() {
        super.update(); // mantém toda a lógica normal do Entity

    }

}
