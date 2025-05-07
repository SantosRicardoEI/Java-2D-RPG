package main.UI;

import java.util.List;

import core.GamePanel;
import main.Light;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OverlayManager {
    private boolean showDamageOverlay = false;
    private int damageOverlayCounter = 0;
    private GamePanel gp;
    private Graphics2D gDark;
    private BufferedImage darkOverlay;
    private int darknessFrameSkip = 0;
    private BufferedImage playerLightImage;
    public boolean flicker = false;
    public float innerDarkness = 0.8f;


    private int prevTime = -1;
    private int prevPlayerX = -1;
    private int prevPlayerY = -1;

    public OverlayManager(GamePanel gp) {
        this.gp = gp;
        darkOverlay = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
    }

    public void triggerDamageOverlay() {
        showDamageOverlay = true;
        damageOverlayCounter = 5;
        gp.soundM.playSE("damage");
    }

    public void draw(Graphics2D g2) {
        drawDayCycle(g2);

        if (showDamageOverlay) {
            Composite original = g2.getComposite();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
            g2.setColor(new Color(255, 0, 0));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            g2.setComposite(original);
            damageOverlayCounter--;
            if (damageOverlayCounter <= 0) {
                showDamageOverlay = false;
            }
        }
    }

    private void generatePlayerLightImage(int radius, float innerDarkness, float outerDarkness) {
        playerLightImage = new BufferedImage(radius * 2, radius * 2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = playerLightImage.createGraphics();

        float[] dist = {0f, 0.7f, 1f};
        Color[] colors = {
                new Color(0f, 0f, 0f, innerDarkness),
                new Color(0f, 0f, 0f, (innerDarkness + outerDarkness) / 2),
                new Color(0f, 0f, 0f, outerDarkness)
        };

        RadialGradientPaint gradient = new RadialGradientPaint(
                new Point(radius, radius), radius, dist, colors
        );

        g2.setPaint(gradient);
        g2.fillRect(0, 0, radius * 2, radius * 2);
        g2.dispose();
    }


    public void drawDayCycle(Graphics2D g2) {
        drawDarkness(g2, innerDarkness, 1f, 700, 0.5f);
    }

    public void drawDarkness(Graphics2D g2, float innerDarkness, float outerDarkness, int lightRadius, float nightColor) {
        float timeDarkness = calculateTimeDarkness(gp.timeM.time, gp.timeM.maxTime);
        if (timeDarkness <= 0f) return;

        if (prevTime == gp.timeM.time && prevPlayerX == gp.player.worldX && prevPlayerY == gp.player.worldY) {
            g2.drawImage(darkOverlay, 0, 0, null);
            return;
        }

        prevTime = gp.timeM.time;
        prevPlayerX = gp.player.worldX;
        prevPlayerY = gp.player.worldY;

        Composite originalComposite = g2.getComposite();

        float adjustedInner = innerDarkness * timeDarkness;
        float adjustedOuter = outerDarkness * timeDarkness;

        if (playerLightImage == null) {
            generatePlayerLightImage(Math.max(gp.screenWidth, gp.screenHeight) / 2, adjustedInner, adjustedOuter);
        }

        gDark = darkOverlay.createGraphics();
        gDark.setComposite(AlphaComposite.Clear);
        gDark.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        gDark.setComposite(AlphaComposite.SrcOver);

        int centerX = gp.player.screenX + gp.tileSize / 2 - playerLightImage.getWidth() / 2;
        int centerY = gp.player.screenY + gp.tileSize / 2 - playerLightImage.getHeight() / 2;
        gDark.drawImage(playerLightImage, centerX, centerY, null);

        applyAllLights(gDark, darkOverlay, gp.currentMap.lights, gp.entityLights);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g2.drawImage(darkOverlay, 0, 0, null);
        g2.setComposite(originalComposite);

        gDark.dispose();
    }


    public float calculateTimeDarkness(int time, int maxTime) {


        float normalized = (float) time / maxTime;
        float totalMinutes = normalized * 1440f;

        float dawnStart = 270;
        float dayStart = 480;
        float dayEnd = 1080;
        float duskEnd = 1260;


        if (totalMinutes < dawnStart || totalMinutes >= duskEnd) return 1f;
        else if (totalMinutes < dayStart) return 1f - ((totalMinutes - dawnStart) / (dayStart - dawnStart));
        else if (totalMinutes >= dayEnd && totalMinutes < duskEnd) return (totalMinutes - dayEnd) / (duskEnd - dayEnd);
        else return 0f;
    }


    public static final float[] staticDist = {0.0f, 0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 0.95f, 0.98f, 1f};
    public static final Color[] staticColors = {
            new Color(0f, 0f, 0f, 1.00f), new Color(0f, 0f, 0f, 0.90f), new Color(0f, 0f, 0f, 0.80f),
            new Color(0f, 0f, 0f, 0.70f), new Color(0f, 0f, 0f, 0.60f), new Color(0f, 0f, 0f, 0.50f),
            new Color(0f, 0f, 0f, 0.35f), new Color(0f, 0f, 0f, 0.20f), new Color(0f, 0f, 0f, 0.10f),
            new Color(0f, 0f, 0f, 0.05f), new Color(0f, 0f, 0f, 0.02f), new Color(0f, 0f, 0f, 0.01f),
            new Color(0f, 0f, 0f, 0.00f)
    };

    public static final float[] entityDist = {0.0f, 0.25f, 0.5f, 0.75f, 1f};
    public static final Color[] entityColors = {
            new Color(0f, 0f, 0f, 1.00f), new Color(0f, 0f, 0f, 0.75f),
            new Color(0f, 0f, 0f, 0.30f), new Color(0f, 0f, 0f, 0.02f),
            new Color(0f, 0f, 0f, 0.00f)
    };

    public void applyAllLights(Graphics2D gDark, BufferedImage darknessOverlay, List<Light> staticLights, List<Light> dynamicLights) {
        gDark.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OUT, 1f));

        for (Light light : staticLights) {
            drawLight(gDark, light, staticDist, staticColors, true);
        }

        for (Light light : dynamicLights) {
            if (light != null) {
                drawLight(gDark, light, entityDist, entityColors, false);
            }
        }
    }

    private void drawLight(Graphics2D gDark, Light light, float[] dist, Color[] colors, boolean isTileBased) {
        if (light.cachedLightImage == null) {
            light.generateLightImage(dist, colors);
        }

        if (light.flicker) {
            light.flickerCounter++;
            if (light.flickerCounter % 5 == 0) {
                light.flickerAlpha = 0.85f + (float) (Math.random() * 0.15);
            }
            gDark.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OUT, light.flickerAlpha));
        } else {
            gDark.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OUT, 1f));
        }

        int worldX = isTileBased ? light.col * gp.tileSize + gp.tileSize / 2 : light.col;
        int worldY = isTileBased ? light.row * gp.tileSize + gp.tileSize / 2 : light.row;

        int screenX = worldX - gp.player.worldX + gp.player.screenX - light.radius;
        int screenY = worldY - gp.player.worldY + gp.player.screenY - light.radius;

        if (screenX + light.radius * 2 < 0 || screenX > gp.screenWidth ||
                screenY + light.radius * 2 < 0 || screenY > gp.screenHeight) return;

        gDark.drawImage(light.cachedLightImage, screenX, screenY, null);
    }
}