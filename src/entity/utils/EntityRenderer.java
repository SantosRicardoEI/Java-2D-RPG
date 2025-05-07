package entity.utils;

import core.GamePanel;
import entity.Entity;

import java.awt.*;
import java.util.Collections;
import java.util.Comparator;

public class EntityRenderer {

    // ========================================= ENTITY UPDATES =============================================

    public static void updateEntities(GamePanel gp) {
        // Atualiza todas as entidades na lista
        for (int i = 0; i < gp.entityList.size(); i++) {
            Entity entity = gp.entityList.get(i);
            if (entity != null && entity.entityType != EntityType.PLAYER && entity.isAlive && !entity.isDying) {
                entity.update();
            } else if (entity != null && !entity.isAlive && !entity.isDying) {
                if (gp.entityList.get(i).emitLight) {
                    gp.entityList.get(i).disableLight();
                }
                gp.entityList.remove(i); // Remove as entidades mortas
                i--;
            }
        }
        Collections.sort(
                gp.entityList,
                Comparator.nullsLast(Comparator.comparingDouble(e -> e.worldY))
        );
    }

    // ========================================= DRAW ENTITIES =============================================

    public static void drawEntities(GamePanel gp, Graphics2D g2) {

        for (int i = 0; i < gp.entityList.size(); i++) {
            Entity entity = gp.entityList.get(i);
            if (entity != null) {
                entity.draw(g2);

                if (gp.debug) {
                    // Calcula posição de sprite para desenhar
                    int spriteX = (int) Math.round(entity.worldX - gp.player.worldX + gp.player.screenX);
                    int spriteY = (int) Math.round(entity.worldY - gp.player.worldY + gp.player.screenY);

                    g2.setColor(Color.YELLOW);
                    g2.drawRect(spriteX, spriteY, gp.tileSize, gp.tileSize);

                    // Corrige para a hitbox (solidArea)
                    int screenX = (int) Math.round(entity.realX - gp.player.realX + gp.player.screenX + entity.solidArea.x);
                    int screenY = (int) Math.round(entity.realY - gp.player.realY + gp.player.screenY + entity.solidArea.y);
                    int width = (int) Math.round(entity.solidArea.width);
                    int height = (int) Math.round(entity.solidArea.height);

                    g2.setColor(Color.RED);
                    g2.drawRect(screenX, screenY, width, height);
                }
            }
        }
    }

}