package game;

import entity.NPC_King;
import monster.MON_Skeleton;
import object.*;
import tile.Tile;

import java.awt.*;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;

    }
    public void setObject() {
        int mapNum = 1;

//        gp.obj[0] = new OBJ_Sword(gp);
//        gp.obj[0].worldX = 16 * gp.tileSize;
//        gp.obj[0].worldY = 15 * gp.tileSize;
//
//        gp.obj[1] = new OBJ_Key(gp);
//        gp.obj[1].worldX = 44 * gp.tileSize;
//        gp.obj[1].worldY = 15 * gp.tileSize;
//
//        gp.obj[2] = new OBJ_Gems(gp);
//        gp.obj[2].worldX = 31 * gp.tileSize;
//        gp.obj[2].worldY = 28 * gp.tileSize;
//
//        gp.obj[3] = new OBJ_Chest(gp);
//        gp.obj[3].worldX = 19 * gp.tileSize;
//        gp.obj[3].worldY = 35 * gp.tileSize;

        gp.obj[mapNum][0] = new OBJ_Cherry(gp);
        gp.obj[mapNum][0].worldX = 31 * gp.tileSize;
        gp.obj[mapNum][0].worldY = 24 * gp.tileSize;

        gp.obj[mapNum][1] = new OBJ_Banner(gp);
        gp.obj[mapNum][1].worldX = 32 * gp.tileSize;
        gp.obj[mapNum][1].worldY = 6 * gp.tileSize;

        gp.obj[mapNum][2] = new OBJ_Chest(gp);
        gp.obj[mapNum][2].worldX = 30 * gp.tileSize;
        gp.obj[mapNum][2].worldY = 18 * gp.tileSize;

        //for map 2
        mapNum = 2;
        gp.obj[mapNum][0] = new OBJ_Gems(gp);
        gp.obj[mapNum][0].worldX = 31 * gp.tileSize;
        gp.obj[mapNum][0].worldY = 24 * gp.tileSize;

        gp.obj[mapNum][1] = new OBJ_Sword(gp);
        gp.obj[mapNum][1].worldX = 32 * gp.tileSize;
        gp.obj[mapNum][1].worldY = 24 * gp.tileSize;

        gp.obj[mapNum][2] = new OBJ_Banner(gp);
        gp.obj[mapNum][2].worldX = 32 * gp.tileSize;
        gp.obj[mapNum][2].worldY = 35 * gp.tileSize;

        gp.obj[mapNum][3] = new OBJ_Key(gp);
        gp.obj[mapNum][3].worldX = 30 * gp.tileSize;
        gp.obj[mapNum][3].worldY = 24 * gp.tileSize;
    }
    public void setNPC() {
        int mapNum = 1;
        gp.npc[mapNum][0] = new NPC_King(gp);
        gp.npc[mapNum][0].worldX = 32 * gp.tileSize;
        gp.npc[mapNum][0].worldY = 27 * gp.tileSize;

    }
    public void setMonster() {
        int mapNum = 2;
        gp.monsterArray[mapNum][0] = new MON_Skeleton(gp);
        gp.monsterArray[mapNum][0].worldX = 32 * gp.tileSize;
        gp.monsterArray[mapNum][0].worldY = 22 * gp.tileSize;

        gp.monsterArray[mapNum][1] = new MON_Skeleton(gp);
        gp.monsterArray[mapNum][1].worldX = 33 * gp.tileSize;
        gp.monsterArray[mapNum][1].worldY = 22 * gp.tileSize;

        gp.monsterArray[mapNum][2] = new MON_Skeleton(gp);
        gp.monsterArray[mapNum][2].worldX = 34 * gp.tileSize;
        gp.monsterArray[mapNum][2].worldY = 22 * gp.tileSize;

        gp.monsterArray[mapNum][3] = new MON_Skeleton(gp);
        gp.monsterArray[mapNum][3].worldX = 35 * gp.tileSize;
        gp.monsterArray[mapNum][3].worldY = 22 * gp.tileSize;
    }
}
