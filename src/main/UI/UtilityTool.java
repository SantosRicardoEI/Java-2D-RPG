package main.UI;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTool {

    // Scale the image to the width and height passed
    public BufferedImage scaledImage(BufferedImage original, int width, int height) {
        BufferedImage sclaedImage = new BufferedImage(width,height,original.getType());
        Graphics2D g2 = sclaedImage.createGraphics();
        g2.drawImage(original,0,0,width,height,null);
        g2.dispose();
        return sclaedImage;
    }
}
