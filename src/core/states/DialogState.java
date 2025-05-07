package core.states;

import core.GamePanel;
import core.IGameState;
import entity.utils.EntityRenderer;

import java.awt.*;

public class DialogState implements IGameState {

    GamePanel gp;

    public DialogState(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void update() {


        if (gp.keyH.enterPressed) {
            gp.player.talkingToNpc = false;
            gp.gsm.setState(new PlayState(gp));
            gp.keyH.enterPressed = false;
        }
    }

    @Override
    public void draw(Graphics2D g2) {

        if (gp.tileM != null) gp.tileM.draw(g2);
        EntityRenderer.drawEntities(gp, g2);
        gp.overlay.draw(g2);
        gp.ui.drawDialogScreen(g2);
        gp.ui.drawHUD(g2,5);
        gp.ui.drawControls(g2);

    }

}