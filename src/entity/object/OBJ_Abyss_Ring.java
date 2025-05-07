package entity.object;

import core.GamePanel;
import entity.Entity;
import entity.utils.EntityUtils;

public class OBJ_Abyss_Ring extends Object {

    public OBJ_Abyss_Ring(GamePanel gp) {

        super(gp);

        name = "Abyss Ring";
        down1 = EntityUtils.setup(gp, "/object/coin_bronze");
        pickSound = 1;
    }

    @Override
    public void interact(Entity entity) {

    }
}
