package core.states;

import core.GamePanel;
import core.IGameState;
import entity.utils.EntityRenderer;
import java.awt.*;

public class PauseState implements IGameState {

    GamePanel gp;

    public PauseState(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void update() {
        if (gp.keyH.pPressed) {
            gp.gsm.setState(new PlayState(gp));
            gp.keyH.pPressed = false;
        }
    }

    @Override
    public void draw(Graphics2D g2) {

        if (gp.tileM != null) gp.tileM.draw(g2);
        EntityRenderer.drawEntities(gp, g2);
        gp.overlay.draw(g2);

        g2.setFont(gp.ui.optima.deriveFont(Font.PLAIN, 80f));
        g2.setColor(Color.WHITE);
        String text = "PAUSED";
        int x = gp.ui.getXforCenteredText(g2, text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);

        gp.ui.drawHUD(g2, 5);
        gp.ui.drawControls(g2);

    }

}