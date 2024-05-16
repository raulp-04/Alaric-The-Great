package game;

import java.awt.*;

public class EventHandler {

    GamePanel gp;
    EventRect[][] eventRect;
    int prevEventX, prevEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gp) {

        this.gp = gp;
        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];


        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 10;
            eventRect[col][row].y = 10;
            eventRect[col][row].width = 28;
            eventRect[col][row].height = 28;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    public void checkEvent() {

        // CHECK 1 TILE AWAY
        int xDistance = Math.abs(gp.player.worldX - prevEventX);
        int yDistance = Math.abs(gp.player.worldY - prevEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gp.tileSize) {
            canTouchEvent = true;
        }

        if (canTouchEvent) {
            if (hit(49, 19, "any")) {
                damagePit(49, 19, gp.DIALOG_STATE);
            }
            if (hit(37, 22, "down")) {
                healingPit(37, 22, gp.DIALOG_STATE);
            }
        }
    }

    public boolean hit(int col, int row, String reqDirection) {

        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[col][row].x = eventRect[col][row].x + col*gp.tileSize;
        eventRect[col][row].y = eventRect[col][row].y + row*gp.tileSize;

        if (gp.player.solidArea.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone) {
            if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;

                prevEventX = gp.player.worldX;
                prevEventY = gp.player.worldY;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;
        return hit;
    }

    public void damagePit(int col, int row, int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialog = "You fell into a pit";
        gp.player.life--;
        gp.playSE(6);
//        eventRect[col][row].eventDone = true;
        canTouchEvent = false;
    }
    public void healingPit(int col, int row, int gameState) {
        if (gp.keyHandler.enterPressed) {
            gp.gameState = gameState;
            gp.ui.currentDialog = "You drink water.. Life has been replenished";
            gp.playSE(7);
            gp.player.life = gp.player.maxLife;
        }
    }

}
