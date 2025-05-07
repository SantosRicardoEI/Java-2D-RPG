package entity.utils;

import core.GamePanel;
import jdk.swing.interop.SwingInterOpUtils;
import main.UI.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EntityUtils {

    // =============================================== ENTITY IMAGE SETUP ==============================================

    public static BufferedImage setup(GamePanel gp, String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            var stream = EntityUtils.class.getResourceAsStream(imagePath + ".png");
            if (stream != null) {
                image = ImageIO.read(stream);
                image = uTool.scaledImage(image, width, height);
            } else {
                System.out.println("[WARNING] Image not found: " + imagePath + ".png");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public static BufferedImage setup(GamePanel gp,String imagePath) {
        return setup(gp,imagePath, gp.tileSize, gp.tileSize);
    }

    // =============================================== SKIN TOOL MODE ==================================================

    public static BufferedImage setupExternal(String filename, int size) {
        BufferedImage image = null;
        System.out.println("Loading image: " + new File(filename).getAbsolutePath());
        try {
            // Pega caminho absoluto do mesmo diretório onde o .jar está
            File file = new File(filename);
            image = ImageIO.read(file);
            image = new UtilityTool().scaledImage(image, size, size);
        } catch (IOException e) {
            System.out.println("⚠️ Erro ao carregar imagem externa: " + filename);
        }
        return image;
    }

}
