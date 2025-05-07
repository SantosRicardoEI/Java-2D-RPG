package entity.object;

import core.GamePanel;
import core.states.LevelUpState;
import entity.Entity;
import entity.utils.EntityUtils;

import java.awt.*;

public class OBJ_Bonfire extends Object {


    public OBJ_Bonfire(GamePanel gp) {

        super(gp);

        name = "Bonfire";
        down1 = EntityUtils.setup(gp, "/object/bonfire_1");
        down2 = EntityUtils.setup(gp, "/object/bonfire_2");
        up1 = EntityUtils.setup(gp, "/object/bonfire_3");
        up2 = EntityUtils.setup(gp, "/object/bonfire_4");
        pickSound = 1;
        isCollidable = true;
        spriteSpeed = 24;

    }

    @Override
    public void interact(Entity entity) {
        if (gp.keyH.enterPressed) {
            gp.keyH.enterPressed = false;
            gp.ui.addLog("[PLAYER] Resting at Bonfire...", Color.white);
            gp.player.setSpawn(entity.worldX, entity.worldY);
            gp.resetLevel();
            gp.gsm.setState(new LevelUpState(gp));
        }

    }

    @Override
    public void update() {

        if (spriteNum == 1) {
            image = down1;
        }
        if (spriteNum == 2) {
            image = down2;
        }
        if (spriteNum == 3) {
            image = up1;
        }
        if (spriteNum == 4) {
            image = up2;
        }

        animationFrameCounter++;
        if (animationFrameCounter > spriteSpeed) {
            spriteNum++;
            if (spriteNum > 4) {
                spriteNum = 1;
            }
            animationFrameCounter = 0;
        }

        emitLight(8);
    }
}
