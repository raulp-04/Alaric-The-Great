package game;

import entity.Entity;

public class CollisionChecker {
    public boolean contactPlayer = false;
    private final GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }
    public void collisionCheckTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                if (gp.tileManager.tile[tileNum1].collition || gp.tileManager.tile[tileNum2].collition){
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if (gp.tileManager.tile[tileNum1].collition || gp.tileManager.tile[tileNum2].collition){
                    entity.collisionOn = true;
                }
            break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                if (gp.tileManager.tile[tileNum1].collition || gp.tileManager.tile[tileNum2].collition){
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if (gp.tileManager.tile[tileNum1].collition || gp.tileManager.tile[tileNum2].collition){
                    entity.collisionOn = true;
                }
                break;

        }
    }
    public int collisionCheckObject(Entity entity, boolean player) {

        int index = gp.NO_COLLISION;

        for(int i = 0; i < gp.obj[gp.currentMap].length; i++) {
            if (gp.obj[gp.currentMap][i] != null) {
                // get entity solid area pos.
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // get object solid area pos.
                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if(entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
                            if (gp.obj[gp.currentMap][i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if(entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
                            if (gp.obj[gp.currentMap][i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if(entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
                            if (gp.obj[gp.currentMap][i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if(entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
                            if (gp.obj[gp.currentMap][i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].solidAreaDefaultX;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].solidAreaDefaultY;
            }
        }

        return index;
    }
    /*NPC*/ public int collisionCheckEntity(Entity entity, Entity[][] target ) {

        int index = gp.NO_COLLISION;

        for(int i = 0; i < target[gp.currentMap].length; i++) {
            if (target[gp.currentMap][i] != null) {
                if (entity == target[gp.currentMap][i]){
                    continue;
                }
                // get entity solid area pos.
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // get npc solid area pos.
                target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].solidArea.x;
                target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if(entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
                                entity.collisionOn = true;
                               index = i;
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if(entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
                                entity.collisionOn = true;
                                index = i;
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if(entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
                                entity.collisionOn = true;
                                index = i;
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if(entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
                                entity.collisionOn = true;
                                index = i;
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].solidAreaDefaultX;
                target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].solidAreaDefaultY;

            }
        }

        return index;
   }
    public boolean checkPlayer(Entity entity) {

        contactPlayer = false;
       // get entity solid area pos.
       entity.solidArea.x = entity.worldX + entity.solidArea.x;
       entity.solidArea.y = entity.worldY + entity.solidArea.y;

       // get npc solid area pos.
       gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
       gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

       switch (entity.direction) {
           case "up":
               entity.solidArea.y -= entity.speed;
               break;
           case "down":
               entity.solidArea.y += entity.speed;
               break;
           case "left":
               entity.solidArea.x -= entity.speed;
               break;
           case "right":
               entity.solidArea.x += entity.speed;
               break;
       }

       if(entity.solidArea.intersects(gp.player.solidArea)) {
           entity.collisionOn = true;
           contactPlayer = true;
       }

       entity.solidArea.x = entity.solidAreaDefaultX;
       entity.solidArea.y = entity.solidAreaDefaultY;
       gp.player.solidArea.x = gp.player.solidAreaDefaultX;
       gp.player.solidArea.y = gp.player.solidAreaDefaultY;

       return contactPlayer;
   }
}
