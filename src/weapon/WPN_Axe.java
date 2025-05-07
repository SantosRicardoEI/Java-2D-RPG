package weapon;

import entity.utils.EntityUtils;
import entity.player.Player;
import core.GamePanel;

public class WPN_Axe extends Weapon {

    public WPN_Axe(GamePanel gp) {
        super(gp);

        name = "Axe";
        down1 = EntityUtils.setup(gp, "/object/axe");
        pickSound = 1;

        hitDamage = 40;
        staminaCost = 20;
        range = 2f;
        initSpeed = 15;
        impactFrame = 15;
        attackSpeed = 10;
        recoveryTime = 5;
        scalingAttribute = "Faith";
        playerSpeed = 4.0;
        soundSet = new int[]{9,10,11,12,13,14,15};


    }


    @Override
    public int scaledDamage(Player player) {
        return hitDamage + (int) (Math.sqrt(gp.player.faith) * 3);
    }
}




