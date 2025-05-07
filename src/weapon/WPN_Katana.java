package weapon;

import entity.utils.EntityUtils;
import entity.player.Player;
import core.GamePanel;

public class WPN_Katana extends Weapon {


    public WPN_Katana(GamePanel gp) {
        super(gp);

        name = "Katana";
        down1 = EntityUtils.setup(gp, "/object/katana");
        pickSound = 1;

        hitDamage = 35;
        staminaCost = 13;
        range = 1.9f;
        initSpeed = 11;
        impactFrame = 11;
        attackSpeed = 8;
        recoveryTime = 4;
        scalingAttribute= "Dexterity";
        playerSpeed = 4.0;
        soundSet = new int[]{9,10,11,12,13,14,15};

    }



    @Override
    public int scaledDamage(Player player) {
        return hitDamage + (int)(Math.sqrt(gp.player.dexterity) * 3);
    }
}




