package entity.object;

import core.GamePanel;
import entity.Entity;
import entity.utils.EntityUtils;

public class OBJ_Key extends Object {

    public OBJ_Key(GamePanel gp) {

        super(gp);

        name = "Key";
        down1 = EntityUtils.setup(gp, "/object/key");
        pickSound = 1;
    }

    @Override
    public void interact(Entity entity) {

    }
}
