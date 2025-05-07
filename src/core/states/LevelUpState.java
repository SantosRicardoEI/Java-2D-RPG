package core.states;

import core.GamePanel;
import core.IGameState;
import entity.utils.EntityRenderer;

import java.awt.*;

public class LevelUpState implements IGameState {

    GamePanel gp;
    int selectedIndex = 0;
    String[] attributes = {"Vigor", "Endurance", "Strength", "Dexterity", "Intelligence", "Faith"};

    public LevelUpState(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void update() {

        if (gp.keyH.wPressed) {
            selectedIndex = (selectedIndex + attributes.length - 1) % attributes.length;
            gp.keyH.wPressed = false;
        }

        if (gp.keyH.sPressed) {
            selectedIndex = (selectedIndex + 1) % attributes.length;
            gp.keyH.sPressed = false;
        }

        if (gp.keyH.enterPressed) {
            gp.player.levelUp(attributes[selectedIndex].toLowerCase());
            gp.keyH.enterPressed = false;
        }

        if (gp.keyH.escPressed || gp.keyH.hPressed) {
            gp.gsm.setState(new PlayState(gp));
            gp.keyH.escPressed = false;
            gp.keyH.hPressed = false;
        }
    }

    @Override
    public void draw(Graphics2D g2) {

        if (gp.tileM != null) gp.tileM.draw(g2);
        EntityRenderer.drawEntities(gp, g2);
        gp.overlay.draw(g2);

        // Fonte e Métricas
        Font font = new Font("Optima", Font.PLAIN, 20);
        g2.setFont(font);
        FontMetrics fm = g2.getFontMetrics();
        int lineHeight = fm.getHeight();
        int ascent = fm.getAscent();
        int padding = 20;

        // Cálculo de largura e altura do menu
        int maxWidth = 0;
        String[] extraLines = {
                "[ENTER] Increase | [ESC] Quit",
                "Souls: " + gp.player.souls,
                "Next Level: " + gp.player.levelUpCost
        };

        for (String attr : attributes) {
            int value = switch (attr.toLowerCase()) {
                case "vigor" -> gp.player.vigor;
                case "endurance" -> gp.player.endurance;
                case "strength" -> gp.player.strength;
                case "dexterity" -> gp.player.dexterity;
                case "intelligence" -> gp.player.intelligence;
                case "faith" -> gp.player.faith;
                default -> 0;
            };
            int width = fm.stringWidth(attr + ": " + value);
            if (width > maxWidth) maxWidth = width;
        }

        for (String line : extraLines) {
            int width = fm.stringWidth(line);
            if (width > maxWidth) maxWidth = width;
        }

        int menuWidth = maxWidth + 80;
        int menuHeight = (attributes.length + 4) * lineHeight + padding * 2;

        // Centro da tela
        int x = gp.tileSize;
        int y = (gp.screenHeight - menuHeight) / 2;

        // Fundo escuro com cantos arredondados
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRoundRect(x, y, menuWidth, menuHeight, 20, 20);

        // Título
        g2.setColor(Color.white);
        g2.setFont(new Font("Optima", Font.BOLD, 26));
        g2.drawString("LEVEL UP", x + padding, y + ascent + 5);

        // Atributos
        g2.setFont(font);
        int textY = y + lineHeight * 2;
        for (int i = 0; i < attributes.length; i++) {
            String attr = attributes[i];
            int value = switch (attr.toLowerCase()) {
                case "vigor" -> gp.player.vigor;
                case "endurance" -> gp.player.endurance;
                case "strength" -> gp.player.strength;
                case "dexterity" -> gp.player.dexterity;
                case "intelligence" -> gp.player.intelligence;
                case "faith" -> gp.player.faith;
                default -> 0;
            };

            String text = (i == selectedIndex ? "➤ " : "   ") + attr + ": " + value;
            g2.setColor(i == selectedIndex ? Color.yellow : Color.white);
            g2.drawString(text, x + padding, textY);
            textY += lineHeight;
        }

        // Souls info e instruções
        g2.setColor(Color.white);
        textY += lineHeight / 2;
        g2.drawString("Souls: " + gp.player.souls, x + padding, textY);
        textY += lineHeight;
        g2.drawString("Next Level: " + gp.player.levelUpCost, x + padding, textY);
        textY += lineHeight;
        textY += lineHeight/2;

        g2.drawString("[ENTER] Increase | [ESC] Quit", x + padding, textY);
    }
}