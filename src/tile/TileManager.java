package tile;

import core.GamePanel;
import main.UI.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[2000];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
    }

    public void setup(int index, String imageName, boolean collision) {

        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
            tile[index].image = uTool.scaledImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadMap(String filePath) {
        System.out.println("[TILE] Loading tile map: " + filePath);
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for (int row = 0; row < gp.maxWorldRow; row++) {
                // Inicializa toda a linha com -1
                for (int col = 0; col < gp.maxWorldCol; col++) {
                    mapTileNum[col][row] = -1;
                }

                String line = br.readLine();
                if (line == null) break;

                String[] numbers = line.trim().split("\\s+");

                for (int col = 0; col < numbers.length && col < gp.maxWorldCol; col++) {
                    try {
                        int parsed = Integer.parseInt(numbers[col]);
                        if (parsed >= 0 && parsed < tile.length && tile[parsed] != null) {
                            mapTileNum[col][row] = parsed;
                        }
                    } catch (NumberFormatException e) {
                        // valor inválido ignorado
                    }
                }
            }

            br.close();
        } catch (Exception e) {
            System.err.println("[TILE] Error loading tile map: " + filePath);
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        int playerLeftWorldX = gp.player.worldX - gp.player.screenX;
        int playerRightWorldX = gp.player.worldX + gp.player.screenX;
        int playerTopWorldY = gp.player.worldY - gp.player.screenY;
        int playerBottomWorldY = gp.player.worldY + gp.player.screenY;

        int startCol = Math.max(0, (gp.player.worldX - gp.player.screenX) / gp.tileSize);
        int endCol = Math.min(gp.maxWorldCol, (gp.player.worldX + gp.player.screenX) / gp.tileSize + 2);

        int startRow = Math.max(0, (gp.player.worldY - gp.player.screenY) / gp.tileSize);
        int endRow = Math.min(gp.maxWorldRow, (gp.player.worldY + gp.player.screenY) / gp.tileSize + 2);

        for (int worldRow = startRow; worldRow < endRow; worldRow++) {
            for (int worldCol = startCol; worldCol < endCol; worldCol++) {

                int tileNum = mapTileNum[worldCol][worldRow];

                int worldX = worldCol * gp.tileSize;
                int worldY = worldRow * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                // just draw the tiles on camera
                if (worldX + gp.tileSize > playerLeftWorldX &&
                        worldX < playerRightWorldX + gp.tileSize &&
                        worldY + gp.tileSize > playerTopWorldY &&
                        worldY < playerBottomWorldY + gp.tileSize){

                    if (tileNum >= 0) {
                        g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                    }
                }
            }

            if (gp.debug) {
                g2.setColor(Color.RED);
                g2.setStroke(new BasicStroke(1));

                for (Point p : gp.collisionMap.collisionPoints) {
                    int worldX = p.x * gp.tileSize;
                    int worldY = p.y * gp.tileSize;

                    int screenX = (int) Math.round(worldX - gp.player.worldX + gp.player.screenX);
                    int screenY = (int) Math.round(worldY - gp.player.worldY + gp.player.screenY);

                    g2.drawRect(screenX, screenY, gp.tileSize, gp.tileSize);
                }
            }
        }
    }


    public void loadTileSet(String imagePath, int tileSize) {
        System.out.println("[TILE] Loading tile set: " + imagePath);
        try {
            BufferedImage tileset = ImageIO.read(getClass().getResourceAsStream(imagePath));
            int columns = tileset.getWidth() / tileSize;
            int rows = tileset.getHeight() / tileSize;

            int index = 0;
            tile = new Tile[columns * rows];

            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < columns; x++) {
                    Tile t = new Tile();
                    t.image = tileset.getSubimage(x * tileSize, y * tileSize, tileSize, tileSize);
                    t.image = new UtilityTool().scaledImage(t.image, gp.tileSize, gp.tileSize);
                    t.collision = false; // <-- definir manualmente os que colidem depois
                    tile[index] = t;
                    index++;
                }
            }
            System.out.println("[TILE] Tile set loaded!");

        } catch (IOException e) {
            System.err.println("[TILE] Error loading tile set!");
            e.printStackTrace();
        }
    }
}
