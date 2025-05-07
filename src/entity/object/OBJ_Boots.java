package entity.object;

import core.GamePanel;
import entity.Entity;
import entity.utils.EntityUtils;

public class OBJ_Boots extends Object {

    public OBJ_Boots(GamePanel gp) {

        super(gp);

        name = "Boots";
        down1 = EntityUtils.setup(gp, "/object/boots");
        pickSound = 2;
    }

    @Override
    public void interact(Entity entity) {

    }
}
