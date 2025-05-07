package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Light {
    public final String id;
    public int col;
    public int row;
    public int radius;
    public boolean flicker = true;
    public float flickerAlpha = 1f;
    public int flickerCounter = 0;

    public BufferedImage cachedLightImage;

    public Light(String id, int col, int row, int radius, float[] dist, Color[] colors) {
        this.id = id;
        this.col = col;
        this.row = row;
        this.radius = radius;
        generateLightImage(dist, colors);
    }

    public void generateLightImage(float[] dist, Color[] colors) {
        int size = radius * 2;
        cachedLightImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = cachedLightImage.createGraphics();

        RadialGradientPaint paint = new RadialGradientPaint(
                new Point(radius, radius),
                radius,
                dist,
                colors
        );

        g.setPaint(paint);
        g.fillOval(0, 0, size, size);
        g.dispose();
    }
}