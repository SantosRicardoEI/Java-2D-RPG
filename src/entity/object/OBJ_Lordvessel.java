package entity.object;

import core.GamePanel;
import entity.Entity;
import entity.utils.EntityUtils;

public class OBJ_Lordvessel extends Object {

    public OBJ_Lordvessel(GamePanel gp) {

        super(gp);

        name = "Lordvessel";
        down1 = EntityUtils.setup(gp, "/object/shield_blue");
        pickSound = 5;
    }

    @Override
    public void interact(Entity entity) {

    }
}
