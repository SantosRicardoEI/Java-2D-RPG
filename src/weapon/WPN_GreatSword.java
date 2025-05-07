package weapon;

import entity.utils.EntityUtils;
import entity.player.Player;
import core.GamePanel;

public class WPN_GreatSword extends Weapon {


    public WPN_GreatSword(GamePanel gp) {
        super(gp);

        name = "Great Sword";
        down1 = EntityUtils.setup(gp, "/object/great_sword");
        pickSound = 1;

        hitDamage = 110;
        staminaCost = 50;
        range = 2.5f;
        initSpeed = 18;
        impactFrame = 18;
        attackSpeed = 30;
        recoveryTime = 16;
        scalingAttribute = "Strenght";
        playerSpeed = 3.0;
        soundSet = new int[]{9,10,11,12,13,14,15};

    }


    @Override
    public int scaledDamage(Player player) {
        return hitDamage + (int)(Math.sqrt(gp.player.strength) * 4);
    }
}




