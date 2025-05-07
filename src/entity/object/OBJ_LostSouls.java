package entity.object;

import core.GamePanel;
import entity.Entity;
import entity.utils.EntityUtils;

public class OBJ_LostSouls extends Object {

    public int souls;

    public OBJ_LostSouls(GamePanel gp, int souls) {

        super(gp);

        name = "Lost souls";
        down1 = EntityUtils.setup(gp, "/object/manacrystal_full");
        pickSound = 1;


        this.souls = souls;
    }

    @Override
    public void interact(Entity entity) {
        if (gp.ui != null) {
            gp.ui.soulsRetrievedScreen = true;
        }
        gp.soundM.playSE("getmysouls");
        gp.player.souls += souls;
        gp.entityList.remove(this);
    }

    @Override
    public void update() {
        spriteNum = 1;
    }
}
