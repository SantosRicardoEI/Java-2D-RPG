package entity.object;

import core.GamePanel;
import entity.Entity;
import entity.utils.EntityUtils;

public class OBJ_Bell2 extends Object {

    public OBJ_Bell2(GamePanel gp) {

        super(gp);

        name = "Bell2";
        down1 = EntityUtils.setup(gp, "/object/manacrystal_full");
        pickSound = 5;
    }

    @Override
    public void interact(Entity entity) {

    }
}
