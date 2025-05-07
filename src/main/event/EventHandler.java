package main.event;

import core.GamePanel;

public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][];

    // Exit-event control variables
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    // Creates the Event Handler
    public EventHandler(GamePanel gp) {
        this.gp = gp;

        // Creates a 2D Array with all the positions on the world
        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;

        // Fill the 2D Array (world coordinates) with eventRects of the same type
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

            // Creates the eventRect
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;

            // Its size is 2x2 (pixels)
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;

            // defaultX and defaultY will save its initial x and y
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            // Loop control
            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }

        }
    }

    // Check position-hit-based events
    public void checkEvent() {

        // Checks if the player is more than 1 tile away from the eventRect

        // Check if player has exit the last eventRect, if abs distance > 1 Tile it means he can touch it again
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gp.tileSize) {
            canTouchEvent = true;
        }

        /*
        // Here put the events that need the player to get away from 1 block to work again
        if (canTouchEvent) {
            if (hit(27, 16, "any")) {
                damagePit(27, 16, GameState.PLAY);
            }
        }


        // Instantaneous on-hit
        if (hit(24, 14, "any")) {
            teleport(24, 14, 23, 21, GameState.PLAY);
        }

         */
    }

    // Detects collision with the specified entity direction
    public boolean hit(int col, int row, String reqDirection) {

        boolean hit = false;

        // ?
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;

        // If collision happens, hit = true
        if (gp.player.solidArea.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone) {
            if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;

                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }

        // This resets player and eventRect to its default x's and y's
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }

    // Creates a teleport from eventRect(x,y) pos to Destination(x,y)
    public void teleport(int xFrom, int yFrom, int xDest, int yDest) {
        gp.player.worldX = gp.tileSize * xDest;
        gp.player.worldY = gp.tileSize * yDest;
    }

    // Creates damage pit ont the (x,y) passed
    public void damagePit(int col, int row) {
        gp.player.currentHealth -= 20;
        gp.overlay.triggerDamageOverlay();
        if (gp.player.currentHealth < 0) {
            gp.player.currentHealth = 0;
        }

        canTouchEvent = false;
    }

    // Creates stamina-drain pit ont the (x,y) passed
    public void staminaPit(int col, int row) {
        gp.player.currentStamina -= 7;
        if (gp.player.currentStamina < 0) {
            gp.player.currentStamina = 0;
        }
        canTouchEvent = false;
    }

}
