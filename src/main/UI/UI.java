package main.UI;

import core.GamePanel;

import java.awt.*;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;

public class UI {

    GamePanel gp;
    Graphics2D g2;

    Font optimus;
    public Font optima;
    Font helvetica;

    public String currentDialog = "";
    public int commandNum = 0;
    private int statusScale = 5;

    public boolean fadingToBlack = false;
    public int fadeAlpha = 0;
    private final int maxAlpha = 255; // 255 para escurecer o maximo
    private int logDuration = 300;

    public boolean soulsRetrievedScreen = false;
    public int soulsRetrievedTimer = 0;

    private class GameLog {
        String text;
        int timer;
        Color color;

        public GameLog(String text, int duration, Color color) {
            this.text = text;
            this.timer = duration;
            this.color = color;
        }
    }

    private final List<GameLog> gameLogs = new ArrayList<>();


    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is;

            is = getClass().getResourceAsStream("/font/optimusprinceps.ttf");
            if (is != null) {
                optimus = Font.createFont(Font.TRUETYPE_FONT, is);
            } else {
                System.out.println("⚠️ Falha ao carregar fonte: optimusprinceps.ttf");
                optimus = new Font("Arial", Font.PLAIN, 14);
            }

            is = getClass().getResourceAsStream("/font/optima_medium.ttf");
            if (is != null) {
                optima = Font.createFont(Font.TRUETYPE_FONT, is);
            } else {
                System.out.println("⚠️ Falha ao carregar fonte: optima_medium.ttf");
                optima = new Font("Arial", Font.PLAIN, 14);
            }

        } catch (Exception e) {
            e.printStackTrace();
            optimus = new Font("Arial", Font.PLAIN, 14);
            helvetica = new Font("Arial", Font.PLAIN, 14);
            optima = new Font("Arial", Font.PLAIN, 14);
        }
    }

    public void addLog(String text, Color color) {
        gameLogs.add(new GameLog(text, logDuration, color)); // ~2 segundos
    }

    private void drawGameLogs(Graphics2D g2) {
        int x = 20;
        int y = gp.screenHeight - gp.tileSize * 4;
        int lineSpacing = 20;

        for (int i = gameLogs.size() - 1; i >= 0; i--) {
            GameLog log = gameLogs.get(i);

            g2.setFont(optima.deriveFont(Font.PLAIN, 16f));
            g2.setColor(log.color);

            String[] lines = log.text.split("\n");
            for (String line : lines) {
                g2.drawString(line, x, y);
                y -= lineSpacing;
            }

            log.timer--;

            if (log.timer <= 0) {
                gameLogs.remove(i);
            }
        }
    }


    public void drawFadeToBlack(Graphics2D g2) {
        if (fadingToBlack) {
            if (fadeAlpha < maxAlpha) {
                fadeAlpha += 1; // fade velocity
            }

            Color fadeColor = new Color(0, 0, 0, fadeAlpha);
            g2.setColor(fadeColor);
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        }
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(optima);
        g2.setColor(Color.white);

        drawFadeToBlack(g2);
        drawControls(g2);
        drawHUD(g2,statusScale);

        if (gp.player.youDiedScreen) {
            drawYouDiedScreen(g2);
        }

        drawSoulsRetrievedScreen(g2);

    }


    public void drawHUD(Graphics2D g2, int scale) {
        drawPlayerLifeBar(g2);
        drawPlayerStaminaBar(g2);
        drawPlayerSouls(g2);
        drawSelectedWeapon(g2);
        drawItem(g2);
        drawClock(g2, gp.timeM.time, gp.timeM.maxTime, gp.screenWidth, gp.timeM.getCurrentDay());
        showFPS(g2);
        drawGameLogs(g2);
    }

    public void drawClock(Graphics2D g2, int time, int maxTime, int screenWidth, String currentDay) {
        int totalMinutes = (int) ((time / (float) maxTime) * 1440);
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;

        String timeStr = String.format("%s, %02dh%02d", currentDay, hours, minutes);

        g2.setFont(optima.deriveFont(Font.PLAIN, 16f));
        g2.setColor(Color.white);

        int x = gp.tileSize * 3;
        int y = gp.tileSize + gp.tileSize / 2 + 70;

        g2.drawString(timeStr, x, y);
    }

    public void drawSelectedWeapon(Graphics2D g2) {
        int x = gp.tileSize - gp.tileSize / 2;
        int y = gp.tileSize - gp.tileSize / 2;
        Image image = gp.player.weapon.down1;
        int scaleImage = 2;

        g2.drawImage(image, x, y, gp.tileSize * scaleImage, gp.tileSize * scaleImage, null);

        g2.setFont(optima.deriveFont(Font.PLAIN, 16f));
        g2.setColor(Color.white);
        String weaponName = gp.player.weapon.name;
        g2.drawString(weaponName, x, y + gp.tileSize * 2 + gp.tileSize/2);

    }

    public void drawPlayerLifeBar(Graphics2D g2) {
        int x = gp.tileSize * 3;
        int y = gp.tileSize;
        int unitWidth = 2;
        int height = 25;

        int max = gp.player.currentMaxHealth;
        int current = gp.player.currentHealth;

        int width = max * unitWidth;
        int currentWidth = current * unitWidth;

        Composite original = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));

        g2.setColor(new Color(35, 35, 35));
        g2.fillRect(x - 1, y - 1, width + 2, height + 2);

        g2.setColor(new Color(200, 0, 30));
        g2.fillRect(x, y, currentWidth, height);

        g2.setComposite(original);
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(x - 1, y - 1, width + 2, height + 2);
    }

    public void drawPlayerStaminaBar(Graphics2D g2) {
        int x = gp.tileSize * 3;
        int y = gp.tileSize + gp.tileSize / 2 + 10;
        int unitWidth = 2;
        int height = 25;

        int max = gp.player.currentMaxStamina;
        int current = gp.player.currentStamina;

        int width = max * unitWidth;
        int currentWidth = current * unitWidth;

        Composite original = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));

        g2.setColor(new Color(35, 35, 35));
        g2.fillRect(x - 1, y - 1, width + 2, height + 2);

        g2.setColor(new Color(0, 160, 0));
        g2.fillRect(x, y, currentWidth, height);

        g2.setComposite(original);
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(x - 1, y - 1, width + 2, height + 2);
    }


    public void drawItem(Graphics2D g2) {

        drawItemName(g2);

        int x = gp.tileSize;
        int y = gp.tileSize * 15;

        int quantity = gp.player.item.quantity;

        Image image;
        if (quantity >= 5) {
            image = gp.player.item.image6;
        } else if (quantity == 4) {
            image = gp.player.item.image5;
        } else if (quantity == 3) {
            image = gp.player.item.image4;
        } else if (quantity == 2) {
            image = gp.player.item.image3;
        } else if (quantity == 1) {
            image = gp.player.item.image2;
        } else {
            image = gp.player.item.image;
        }

        int scaleImage = 2;
        g2.drawImage(image, x, y, gp.tileSize * scaleImage, gp.tileSize * scaleImage, null);

        g2.setFont(optima.deriveFont(Font.PLAIN, 32));
        String text = "x" + quantity;
        x += 50;
        y += 100;
        g2.drawString(text, x, y);
    }

    public void drawPlayerSouls(Graphics2D g2) {
        int padding = 10;
        int lineHeight = 24;

        g2.setFont(optima.deriveFont(Font.PLAIN, 20f));
        FontMetrics fm = g2.getFontMetrics();

        int ascent = fm.getAscent();
        int descent = fm.getDescent();

        String text = "Souls: " + gp.player.souls;

        int textWidth = fm.stringWidth(text);
        int boxWidth = textWidth + padding * 2;
        int boxHeight = ascent + descent + padding * 2;

        int x = gp.screenWidth - boxWidth - 20;
        int y = gp.screenHeight - boxHeight - 20;

        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRoundRect(x, y, boxWidth, boxHeight, 15, 15);

        g2.setColor(Color.white);
        int textX = x + padding;
        int textY = y + padding + ascent;
        g2.drawString(text, textX, textY);
    }



    public void drawDialogScreen(Graphics2D g2) {

        int x = gp.tileSize * gp.maxScreenCol - gp.tileSize * 10;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - gp.tileSize * 4;
        int height = gp.tileSize * 6;

        drawSubWindow(g2,x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20
        ));
        x += gp.tileSize / 2;
        y += gp.tileSize / 1.5;

        for (String line : currentDialog.split("\n")) {
            g2.drawString(line, x, y);
            y += 30;
        }
    }

    public void drawSubWindow(Graphics2D g2,int x, int y, int width, int height) {

        Color c = new Color(0, 0, 0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width / 3, height / 2, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width / 3 - 10, height / 2 - 10, 25, 25);
    }


    public void drawYouDiedScreen(Graphics2D g2) {
        g2.setFont(optima.deriveFont(Font.BOLD, 96f));
        g2.setColor(new Color(150, 0, 0));
        String text = "YOU DIED";
        int x = getXforCenteredText(g2,text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
    }

    public int getXforCenteredText(Graphics2D g2, String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }

    public void drawItemName(Graphics2D g2) {
        g2.setFont(optima.deriveFont(Font.PLAIN, 20f));
        g2.setColor(Color.white);

        String name = gp.player.item.name;
        int x = gp.tileSize;
        int y = gp.tileSize * 18 - gp.tileSize/3;

        g2.drawString(name, x, y);
    }

    public void drawControls(Graphics2D g2) {
        int padding = 10;
        int lineHeight = 20;

        g2.setFont(new Font("Optima", Font.PLAIN, 13));
        FontMetrics fm = g2.getFontMetrics();

        int ascent = fm.getAscent();
        int descent = fm.getDescent();

        String title = "Controls";
        String[][] controls = {
                {"Move:", "[W] [A] [S] [D]"},
                {"Attack:", "[ENTER]"},
                {"Dodge:", "[SHIFT]"},
                {"Switch Weapon:", "[1] [2] [3] [4] [5]"},
                {"Switch Item:", "[Q]"},
                {"Use Item:", "[E]"},
                {"Toggle Debug:", "[O]"}
        };

        String[] debugLines = null;
        if (gp.debug) {
            debugLines = new String[]{
                    "Weapon:",
                    "Base Damage: " + gp.player.weapon.hitDamage,
                    "Stamina Cost: " + gp.player.weapon.staminaCost,
                    "Attack Speed: " + gp.player.weapon.attackSpeed,
                    "Range: " + gp.player.weapon.range,
                    "Scalings: " + gp.player.weapon.scalingAttribute,
                    "",
                    "Potions",
                    "Health Potions: " + gp.player.itemList[0].quantity,
                    "Stamina Potions: " + gp.player.itemList[1].quantity,
                    "",
                    "Player:",
                    "X: " + gp.player.worldX + "  Y: " + gp.player.worldY,
                    "Attack: " + gp.player.currentAttackDamage,
                    "Speed: " + gp.player.currentSpeed,
                    "",
                    "Update Time: " + String.format("%.2f", gp.lastUpdateTime) + " ms",
                    "Draw Time: " + String.format("%.2f", gp.lastDrawTime) + " ms",
                    "",
                    "Tile Draw: " + String.format("%.2f", gp.tileDrawTime) + " ms",
                    "Entity Draw: " + String.format("%.2f", gp.entityDrawTime) + " ms",
                    "Overlay Draw: " + String.format("%.2f", gp.overlayDrawTime) + " ms",
                    "UI Draw: " + String.format("%.2f", gp.uiDrawTime) + " ms",
            };
        }

        int labelWidth = 0;
        for (String[] pair : controls) {
            int width = fm.stringWidth(pair[0]);
            if (width > labelWidth) labelWidth = width;
        }

        int boxWidth = 0;
        for (String[] pair : controls) {
            int lineWidth = labelWidth + 20 + fm.stringWidth(pair[1]);
            if (lineWidth > boxWidth) boxWidth = lineWidth;
        }
        if (debugLines != null) {
            for (String line : debugLines) {
                int lineWidth = fm.stringWidth(line);
                if (lineWidth > boxWidth) boxWidth = lineWidth;
            }
        }
        boxWidth += padding * 2;

        int totalLines = controls.length + 1;
        if (debugLines != null) totalLines += debugLines.length;
        int boxHeight = padding + ascent + (totalLines * lineHeight) + descent + padding;

        int x = gp.screenWidth - boxWidth - 20;
        int y = gp.tileSize * 5;

        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRoundRect(x - padding, y - ascent - padding, boxWidth, boxHeight, 15, 15);

        g2.setColor(Color.white);
        g2.drawString(title, x, y);
        y += lineHeight;

        for (String[] pair : controls) {
            g2.drawString(pair[0], x, y);
            g2.drawString(pair[1], x + labelWidth + 20, y);
            y += lineHeight;
        }

        if (debugLines != null) {
            y += lineHeight / 2;
            for (String line : debugLines) {
                g2.drawString(line, x, y);
                y += lineHeight;
            }
        }
    }

    public void showFPS(Graphics2D g2) {
        g2.setFont(optima.deriveFont(Font.PLAIN, 14f));
        g2.setColor(new Color(255, 255, 255, 200));

        String text = "FPS: " + gp.currentFPS;

        int x = 10;
        int y = 20;

        g2.drawString(text, x, y);
    }

    public void drawSoulsRetrievedScreen(Graphics2D g2) {
        if (soulsRetrievedScreen) {
            g2.setFont(optima.deriveFont(Font.PLAIN, 96f));
            g2.setColor(new Color(0, 200, 0));
            String text = "SOULS RETRIEVED";
            int x = getXforCenteredText(g2, text);
            int y = gp.screenHeight / 2;
            g2.drawString(text, x, y);

            soulsRetrievedTimer++;
            if (soulsRetrievedTimer > 300) {
                soulsRetrievedScreen = false;
                soulsRetrievedTimer = 0;
            }
        }
    }

}

