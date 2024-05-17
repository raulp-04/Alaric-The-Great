package game;

public class EventHandler {
    GamePanel gp;
    EventRect[][][] eventRect;
    int prevEventX, prevEventY;
    boolean canTouchEvent = true;
    boolean defeatedEnemies;

    public EventHandler(GamePanel gp) {

        this.gp = gp;
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;
        while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 10;
            eventRect[map][col][row].y = 10;
            eventRect[map][col][row].width = 28;
            eventRect[map][col][row].height = 28;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
                if (row == gp.maxWorldRow) {
                    row = 0;
                    map++;
                }
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

        if (canTouchEvent) { //32 6
//            if (hit(49, 19, "any", 1)) {
//                damagePit(gp.DIALOG_STATE);
//            }
//            if (hit(37, 22, "down", 1)) {
//                healingPit(gp.DIALOG_STATE);
//            }
            if (hit(32, 8, "any", 1)) {
                teleport(2, 33, 31);
            }
            if (hit(32, 35, "any",2)) {
                defeatedEnemies = true;
                for (int i = 0; i < gp.monsterArray[gp.currentMap].length; i++) {
                    if (gp.monsterArray[gp.currentMap][i] != null) {
                        defeatedEnemies = false;
                    }
                }
                if (defeatedEnemies) {
                    teleport(1, 8, 31);
                } else gp.ui.showMessage("DEFEAT ALL ENEMIES TO TELEPORT");

            }//1&2
            if (hit(53, 15, "any",2)) {
                defeatedEnemies = true;
                for (int i = 0; i < gp.monsterArray[gp.currentMap].length; i++) {
                    if (gp.monsterArray[gp.currentMap][i] != null) {
                        defeatedEnemies = false;
                    }
                }
                if (defeatedEnemies) {
                    teleport(3, 14, 9);
                } else gp.ui.showMessage("DEFEAT ALL ENEMIES TO TELEPORT");
            }
            if (hit(8, 15, "any",3)) {
                defeatedEnemies = true;
                for (int i = 0; i < gp.monsterArray[gp.currentMap].length; i++) {
                    if (gp.monsterArray[gp.currentMap][i] != null) {
                        defeatedEnemies = false;
                    }
                }
                if (defeatedEnemies) {
                    teleport(2, 14, 51);
                } else gp.ui.showMessage("DEFEAT ALL ENEMIES TO TELEPORT");
            }//2&3
            if (hit(32, 6, "any",3)) {
                defeatedEnemies = true;
                for (int i = 0; i < gp.monsterArray[gp.currentMap].length; i++) {
                    if (gp.monsterArray[gp.currentMap][i] != null) {
                        defeatedEnemies = false;
                    }
                }
                if (defeatedEnemies) {
                    teleport(4, 32, 31);
                } else gp.ui.showMessage("DEFEAT ALL ENEMIES TO TELEPORT");
            }
            if (hit(32, 38, "any",4)) {
                defeatedEnemies = true;
                for (int i = 0; i < gp.monsterArray[gp.currentMap].length; i++) {
                    if (gp.monsterArray[gp.currentMap][i] != null) {
                        defeatedEnemies = false;
                    }
                }
                if (defeatedEnemies) {
                    teleport(3, 7, 31);
                } else gp.ui.showMessage("DEFEAT ALL ENEMIES TO TELEPORT");
            }//3&4
        }
    }
    public boolean hit(int col, int row, String reqDirection, int map) {

        boolean hit = false;
        if (map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][col][row].x = eventRect[map][col][row].x + col * gp.tileSize;
            eventRect[map][col][row].y = eventRect[map][col][row].y + row * gp.tileSize;

            if (gp.player.solidArea.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone) {
                if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;

                    prevEventX = gp.player.worldX;
                    prevEventY = gp.player.worldY;
                }
            }

            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }
        return hit;
    }
    public void damagePit(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialog = "You fell into a pit";
        gp.player.life--;
        gp.playSE(6);
//        eventRect[col][row].eventDone = true;
        canTouchEvent = false;
    }
    public void healingPit(int gameState) {
        if (gp.keyHandler.enterPressed) {
            gp.gameState = gameState;
            gp.ui.currentDialog = "You drink water.. Life has been replenished";
            gp.playSE(7);
            gp.player.life = gp.player.maxLife;
        }
    }
    public void teleport(int map, int row, int col) {
        gp.currentMap = map;
        gp.player.worldX = gp.tileSize * col + 23;
        gp.player.worldY = gp.tileSize * row;
        prevEventX = gp.player.worldX;
        prevEventY = gp.player.worldY;
        canTouchEvent = false;
        gp.playSE(9);
    }
}
