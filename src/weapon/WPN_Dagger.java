package weapon;

import entity.utils.EntityUtils;
import entity.player.Player;
import core.GamePanel;

public class WPN_Dagger extends Weapon {

    public WPN_Dagger(GamePanel gp) {
        super(gp);

        name = "Dagger";
        down1 = EntityUtils.setup(gp, "/object/dagger");
        pickSound = 1;

        hitDamage = 25;
        staminaCost = 10;
        range = 1f;
        initSpeed = 5;
        impactFrame = 5;
        attackSpeed = 8;
        recoveryTime = 3;
        scalingAttribute = "Intelligence";
        playerSpeed = 5.0;
        soundSet = new int[]{9,10,11,12,13,14,15};

    }


    @Override
    public int scaledDamage(Player player) {
        return hitDamage + (int)(Math.sqrt(gp.player.intelligence) * 3);
    }
}




