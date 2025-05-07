package item;

import core.GamePanel;
import entity.utils.EntityUtils;

public class ITM_Torch extends Item {

    public ITM_Torch(GamePanel gp, int quantity) {

        super(gp);

        name = "Torch";
        image = EntityUtils.setup(gp, "/object/torch");
        image2 = EntityUtils.setup(gp, "/object/torch");
        image3 = EntityUtils.setup(gp, "/object/torch");
        image4 = EntityUtils.setup(gp, "/object/torch");
        image5 = EntityUtils.setup(gp, "/object/torch");
        image6 = EntityUtils.setup(gp, "/object/torch");
        pickSound = 1;
        usable = true;
        alwaysOn = true;
        maxItemQuantity = 1;
    }

    @Override
    public void use(GamePanel gp) {

    }

    @Override
    public void enable(GamePanel gp) {
        enable = true;
        gp.player.emitLight = true;
    }

    @Override
    public void disable(GamePanel gp) {
        enable = false;
        gp.player.emitLight = false;
        gp.player.disableLight();
    }
}
