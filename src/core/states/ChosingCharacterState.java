package core.states;

import core.GamePanel;
import core.IGameState;
import entity.player.PlayerClass;
import map.maps.DebugMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ChosingCharacterState implements IGameState {

    private GamePanel gp;

    public ChosingCharacterState(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void update() {
        if (gp.keyH.wPressed) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) gp.ui.commandNum = 3;
            gp.keyH.wPressed = false;
        }

        if (gp.keyH.sPressed) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 4) gp.ui.commandNum = 0;
            gp.keyH.sPressed = false;
        }

        if (gp.keyH.enterPressed) {
            switch (gp.ui.commandNum) {
                case 0 -> {
                    gp.soundM.stopMusic();
                    gp.startLevel(new DebugMap(gp), PlayerClass.KNIGHT);
                    gp.gsm.setState(new PlayState(gp));
                }
                case 1 -> {
                    gp.soundM.stopMusic();
                    gp.startLevel(new DebugMap(gp), PlayerClass.BANDIT);
                    gp.gsm.setState(new PlayState(gp));
                }
                case 2 -> {
                    gp.soundM.stopMusic();
                    gp.startLevel(new DebugMap(gp), PlayerClass.WARRIOR);
                    gp.gsm.setState(new PlayState(gp));
                }
                case 3 -> {
                    gp.soundM.stopMusic();
                    gp.startLevel(new DebugMap(gp), PlayerClass.BLADEMAN);
                    gp.gsm.setState(new PlayState(gp));
                }
                case 4 -> {
                    gp.soundM.stopMusic();
                    gp.startLevel(new DebugMap(gp), PlayerClass.SWORDMAN);
                    gp.gsm.setState(new PlayState(gp));
                }
            }
            gp.keyH.enterPressed = false;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage background = null;

        try {
            InputStream is = getClass().getResourceAsStream("/img/darkfog.png");
            background = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (background != null) {
            g2.drawImage(background, 0, 0, gp.screenWidth, gp.screenHeight, null);
        }

        // Title
        g2.setFont(gp.ui.optima.deriveFont(Font.PLAIN, 96));
        String text = "Dark Souls Dmake";
        int x = gp.ui.getXforCenteredText(g2,text);
        int y = gp.tileSize * 3;

        // Title shadow
        Color c = new Color(0, 0, 0, 255);
        g2.setFont(gp.ui.optima.deriveFont(Font.PLAIN, 96));
        g2.setColor(c);
        g2.drawString(text, x + 3, y + 3);
        // main color
        g2.setFont(gp.ui.optima.deriveFont(Font.PLAIN, 96));
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // Bonfire Image
        x = gp.screenWidth - gp.tileSize * 10;
        y += gp.tileSize * 3;
        Image image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/img/bonfire2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int scaleImage = 10;
        g2.drawImage(image, x, y, gp.tileSize * scaleImage, gp.tileSize * scaleImage, null);

        g2.setFont(gp.ui.optima.deriveFont(Font.PLAIN, 32));
        text = "Choose your class:";
        x = gp.ui.getXforCenteredText(g2,text);
        y += gp.tileSize * 2;
        g2.drawString(text, x, y);

        g2.setFont(gp.ui.optima.deriveFont(Font.PLAIN, 32));
        text = "Knight";
        x = gp.ui.getXforCenteredText(g2,text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);
        if (gp.ui.commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        g2.setFont(gp.ui.optima.deriveFont(Font.PLAIN, 32));
        text = "Bandit";
        x = gp.ui.getXforCenteredText(g2,text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (gp.ui.commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        g2.setFont(gp.ui.optima.deriveFont(Font.PLAIN, 32));
        text = "Warrior";
        x = gp.ui.getXforCenteredText(g2,text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (gp.ui.commandNum == 2) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        g2.setFont(gp.ui.optima.deriveFont(Font.PLAIN, 32));
        text = "Blademan";
        x = gp.ui.getXforCenteredText(g2,text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (gp.ui.commandNum == 3) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        g2.setFont(gp.ui.optima.deriveFont(Font.PLAIN, 32));
        text = "Swordman";
        x = gp.ui.getXforCenteredText(g2,text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (gp.ui.commandNum == 4) {
            g2.drawString(">", x - gp.tileSize, y);
        }
    }
}


