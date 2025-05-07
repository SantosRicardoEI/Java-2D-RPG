package entity.object;

import core.GamePanel;
import entity.Entity;
import entity.utils.EntityUtils;

public class OBJ_Bell1 extends Object {

    public OBJ_Bell1(GamePanel gp) {

        super(gp);

        name = "Bell1";
        down1 = EntityUtils.setup(gp, "/object/manacrystal_blank");
        pickSound = 5;

    }

    @Override
    public void interact(Entity entity) {

    }

}
