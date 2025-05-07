package main.collision;

import core.GamePanel;
import entity.Entity;
import entity.utils.EntityType;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import java.awt.*;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {

        double entityLeftWorldX = entity.realX + entity.solidArea.x;
        double entityRightWorldX = entity.realX + entity.solidArea.x + entity.solidArea.width;
        double entityTopWorldY = entity.realY + entity.solidArea.y;
        double entityBottomWorldY = entity.realY + entity.solidArea.y + entity.solidArea.height;

        entity.collisionOn = false;

        switch (entity.direction) {
            case "up" -> {
                double futureTop = entityTopWorldY - entity.currentSpeed;
                int leftCol = (int) (entityLeftWorldX / gp.tileSize);
                int rightCol = (int) (entityRightWorldX / gp.tileSize);
                int futureTopRow = (int) (futureTop / gp.tileSize);

                if (gp.collisionMap.collisionPoints.contains(new Point(leftCol, futureTopRow)) ||
                        gp.collisionMap.collisionPoints.contains(new Point(rightCol, futureTopRow))) {
                    entity.collisionOn = true;
                }
            }
            case "down" -> {
                double futureBottom = entityBottomWorldY + entity.currentSpeed;
                int leftCol = (int) (entityLeftWorldX / gp.tileSize);
                int rightCol = (int) (entityRightWorldX / gp.tileSize);
                int futureBottomRow = (int) (futureBottom / gp.tileSize);

                if (gp.collisionMap.collisionPoints.contains(new Point(leftCol, futureBottomRow)) ||
                        gp.collisionMap.collisionPoints.contains(new Point(rightCol, futureBottomRow))) {
                    entity.collisionOn = true;
                }
            }
            case "left" -> {
                double futureLeft = entityLeftWorldX - entity.currentSpeed;
                int topRow = (int) (entityTopWorldY / gp.tileSize);
                int bottomRow = (int) (entityBottomWorldY / gp.tileSize);
                int futureLeftCol = (int) (futureLeft / gp.tileSize);

                if (gp.collisionMap.collisionPoints.contains(new Point(futureLeftCol, topRow)) ||
                        gp.collisionMap.collisionPoints.contains(new Point(futureLeftCol, bottomRow))) {
                    entity.collisionOn = true;
                }
            }
            case "right" -> {
                double futureRight = entityRightWorldX + entity.currentSpeed;
                int topRow = (int) (entityTopWorldY / gp.tileSize);
                int bottomRow = (int) (entityBottomWorldY / gp.tileSize);
                int futureRightCol = (int) (futureRight / gp.tileSize);

                if (gp.collisionMap.collisionPoints.contains(new Point(futureRightCol, topRow)) ||
                        gp.collisionMap.collisionPoints.contains(new Point(futureRightCol, bottomRow))) {
                    entity.collisionOn = true;
                }
            }
        }
    }

    public boolean isTileBlocked(Rectangle2D.Double area) {
        int tileSize = gp.tileSize;

        int leftCol = Math.max((int) (area.x / tileSize), 0);
        int rightCol = Math.max((int) ((area.x + area.width - 1) / tileSize), 0);
        int topRow = Math.max((int) (area.y / tileSize), 0);
        int bottomRow = Math.max((int) ((area.y + area.height - 1) / tileSize), 0);

        for (int col = leftCol; col <= rightCol; col++) {
            if (gp.collisionMap.collisionPoints.contains(new Point(col, topRow)) ||
                    gp.collisionMap.collisionPoints.contains(new Point(col, bottomRow))) {
                return true;
            }
        }

        for (int row = topRow + 1; row < bottomRow; row++) {
            if (gp.collisionMap.collisionPoints.contains(new Point(leftCol, row)) ||
                    gp.collisionMap.collisionPoints.contains(new Point(rightCol, row))) {
                return true;
            }
        }
        return false;
    }

    public Entity getEntityCollision(Entity entity, List<Entity> targets, boolean checkCollision) {

        Rectangle2D.Double entityArea = entity.getSolidAreaWorld();

        switch (entity.direction) {
            case "up" -> entityArea.y -= entity.currentSpeed;
            case "down" -> entityArea.y += entity.currentSpeed;
            case "left" -> entityArea.x -= entity.currentSpeed;
            case "right" -> entityArea.x += entity.currentSpeed;
        }

        for (Entity target : targets) {
            if (target != null && target != entity) {

                Rectangle2D.Double targetArea = target.getSolidAreaWorld();

                if (entityArea.intersects(targetArea)) {
                    if (checkCollision && target.isCollidable) {
                        entity.collisionOn = true;
                    }
                    return target;
                }
            }
        }
        return null;
    }

    public List<Entity> getAllTargetsHitByAttack(Entity attacker, List<Entity> targets, EntityType targetType) {
        Rectangle2D.Double hitbox = attacker.getAttackBox();
        List<Entity> hitTargets = new ArrayList<>();

        int viewLeft = gp.player.worldX - gp.player.screenX;
        int viewRight = gp.player.worldX + gp.player.screenX;
        int viewTop = gp.player.worldY - gp.player.screenY;
        int viewBottom = gp.player.worldY + gp.player.screenY;

        int maxAttackDistance = gp.tileSize * 3;

        for (Entity target : targets) {
            if (target == null || target == attacker || target.entityType != targetType) continue;

            int dx = Math.abs(attacker.worldX - target.worldX);
            int dy = Math.abs(attacker.worldY - target.worldY);
            if (dx > maxAttackDistance || dy > maxAttackDistance) continue;

            if (target.worldX + (int) target.solidArea.width < viewLeft ||
                    target.worldX > viewRight ||
                    target.worldY + (int) target.solidArea.height < viewTop ||
                    target.worldY > viewBottom) {
                continue;
            }

            Rectangle2D.Double targetBox = target.getSolidAreaWorld();

            if (hitbox.intersects(targetBox)) {
                hitTargets.add(target);
            }
        }
        return hitTargets;
    }
}
