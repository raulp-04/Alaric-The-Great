package game;

import entity.Entity;

public class CollisionChecker {

    private final GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void collisionCheck(Entity entity) {
        int entityLeftWolrldX = entity.worldX + entity.solidArea.x;
        int entityRightWolrldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWolrldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWolrldX/gp.tileSize;
        int entityRightCol = entityRightWolrldX/gp.tileSize;
        int entityTopRow = entityTopWolrldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWolrldY - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileManager.tile[tileNum1].collition == true || gp.tileManager.tile[tileNum2].collition == true){
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileManager.tile[tileNum1].collition == true || gp.tileManager.tile[tileNum2].collition == true){
                    entity.collisionOn = true;
                }
            break;
            case "left":
                entityLeftCol = (entityLeftWolrldX - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileManager.tile[tileNum1].collition == true || gp.tileManager.tile[tileNum2].collition == true){
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWolrldX + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileManager.tile[tileNum1].collition == true || gp.tileManager.tile[tileNum2].collition == true){
                    entity.collisionOn = true;
                }
                break;

        }
    }
}
