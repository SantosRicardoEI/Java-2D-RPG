package item;

import core.GamePanel;
import entity.utils.EntityUtils;

public class ITM_StaminaPotion extends Item {


    public ITM_StaminaPotion(GamePanel gp, int quantity) {

        super(gp);

        name = "Stamina Potion";
        image = EntityUtils.setup(gp, "/object/potion_green_0");
        image2 = EntityUtils.setup(gp, "/object/potion_green_1");
        image3 = EntityUtils.setup(gp, "/object/potion_green_2");
        image4 = EntityUtils.setup(gp, "/object/potion_green_3");
        image5 = EntityUtils.setup(gp, "/object/potion_green_4");
        image6 = EntityUtils.setup(gp, "/object/potion_green_5");
        pickSound = 1;
        usable = true;
    }

    public void use(GamePanel gp) {
        if (gp.player.currentStamina < gp.player.currentMaxStamina && quantity > 0) {
            gp.player.currentStamina += gp.player.currentMaxStamina/2;
            quantity--;
            gp.soundM.playSE("estus");

        }
        if (gp.player.currentStamina > gp.player.currentMaxStamina) {
            gp.player.currentStamina = gp.player.currentMaxStamina;
        }
    }
}
