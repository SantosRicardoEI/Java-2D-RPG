package weapon;

import entity.utils.EntityUtils;
import entity.player.Player;
import core.GamePanel;

public class WPN_LongSword extends Weapon {


    public WPN_LongSword(GamePanel gp) {
        super(gp);

        name = "Long Sword";
        down1 = EntityUtils.setup(gp, "/object/long_sword");
        pickSound = 1;

        hitDamage = 60;
        staminaCost = 20;
        range = 2.5f;
        initSpeed = 8;
        impactFrame = 8;
        attackSpeed = 20;
        recoveryTime = 10;
        scalingAttribute = "Strenght and Dexterity";
        playerSpeed = 4.0;
        soundSet = new int[]{9,10,11,12,13,14,15};

    }


    @Override
    public int scaledDamage(Player player) {
        return hitDamage
                + (int)(Math.sqrt(gp.player.strength) * 1.5)
                + (int)(Math.sqrt(gp.player.dexterity) * 1.5);
    }
}




