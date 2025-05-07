package item;

import core.GamePanel;
import entity.utils.EntityUtils;

public class ITM_HomewardBone extends Item {

    public ITM_HomewardBone(GamePanel gp, int quantity) {

        super(gp);

        name = "Homeward Bone";
        image = EntityUtils.setup(gp, "/object/potion_orange_0");
        image2 = EntityUtils.setup(gp, "/object/potion_orange_1");
        image3 = EntityUtils.setup(gp, "/object/potion_orange_2");
        image4 = EntityUtils.setup(gp, "/object/potion_orange_3");
        image5 = EntityUtils.setup(gp, "/object/potion_orange_4");
        image6 = EntityUtils.setup(gp, "/object/potion_orange_5");
        pickSound = 1;
        usable = true;
    }

    @Override
    public void use(GamePanel gp) {
        if (gp.player.currentHealth < gp.player.currentMaxHealth && quantity > 0) {
            gp.player.currentHealth += gp.player.currentMaxHealth/2;
            quantity--;
            gp.soundM.playSE("estus");
        }


        if (gp.player.currentHealth > gp.player.currentMaxHealth) {
            gp.player.currentHealth = gp.player.currentMaxHealth;
        }
    }
}
