package core.states;


import core.GamePanel;
import core.IGameState;
import entity.utils.EntityRenderer;
import java.awt.*;

public class PlayState implements IGameState {

    GamePanel gp;

    public PlayState(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void update() {
        gp.timeM.updateTime();
        gp.player.update();
        EntityRenderer.updateEntities(gp);
        gp.currentMap.update();


        if (gp.keyH.pPressed) {
            gp.gsm.setState(new PauseState(gp));
            gp.keyH.pPressed = false;
        }

        if (gp.keyH.cPressed) {
            gp.gsm.setState(new CharacterState(gp));
            gp.keyH.cPressed = false;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        long start, elapsed;

        if (gp.tileM != null) {
            start = System.nanoTime();
            gp.tileM.draw(g2);
            elapsed = System.nanoTime() - start;
            gp.tileDrawTime = elapsed / 1_000_000.0;
        }

        start = System.nanoTime();
        EntityRenderer.drawEntities(gp, g2);
        elapsed = System.nanoTime() - start;
        gp.entityDrawTime = elapsed / 1_000_000.0;

        start = System.nanoTime();
        gp.overlay.draw(g2);
        elapsed = System.nanoTime() - start;
        gp.overlayDrawTime = elapsed / 1_000_000.0;

        start = System.nanoTime();
        gp.ui.draw(g2);
        elapsed = System.nanoTime() - start;
        gp.uiDrawTime = elapsed / 1_000_000.0;
    }

}


