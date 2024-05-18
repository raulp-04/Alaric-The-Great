package game;

import entity.NPC_King;
import monster.MON_Boss;
import monster.MON_Goblin;
import monster.MON_Skeleton;
import object.*;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;

    }
    public void setObject() {
        int mapNum = 1;
        gp.obj[mapNum][0] = new OBJ_Cherry(gp);
        gp.obj[mapNum][0].worldX = 31 * gp.tileSize;
        gp.obj[mapNum][0].worldY = 24 * gp.tileSize;

        gp.obj[mapNum][1] = new OBJ_Banner(gp);
        gp.obj[mapNum][1].worldX = 32 * gp.tileSize;
        gp.obj[mapNum][1].worldY = 6 * gp.tileSize;

        //for map 2
        mapNum = 2;
        gp.obj[mapNum][0] = new OBJ_Gems(gp);
        gp.obj[mapNum][0].worldX = 31 * gp.tileSize;
        gp.obj[mapNum][0].worldY = 11 * gp.tileSize;

        gp.obj[mapNum][1] = new OBJ_Sword(gp);
        gp.obj[mapNum][1].worldX = 32 * gp.tileSize;
        gp.obj[mapNum][1].worldY = 11 * gp.tileSize;

        gp.obj[mapNum][2] = new OBJ_Banner(gp);
        gp.obj[mapNum][2].worldX = 32 * gp.tileSize;
        gp.obj[mapNum][2].worldY = 35 * gp.tileSize;

        gp.obj[mapNum][5] = new OBJ_GrassCol(gp);
        gp.obj[mapNum][5].worldX = 32 * gp.tileSize;
        gp.obj[mapNum][5].worldY = 36 * gp.tileSize;

        gp.obj[mapNum][4] = new OBJ_Banner(gp);
        gp.obj[mapNum][4].worldX = 53 * gp.tileSize;
        gp.obj[mapNum][4].worldY = 14 * gp.tileSize;

        gp.obj[mapNum][6] = new OBJ_GrassCol(gp);
        gp.obj[mapNum][6].worldX = 54 * gp.tileSize;
        gp.obj[mapNum][6].worldY = 15 * gp.tileSize;

        mapNum = 3;
        gp.obj[mapNum][0] = new OBJ_Banner(gp);
        gp.obj[mapNum][0].worldX = 8 * gp.tileSize;
        gp.obj[mapNum][0].worldY = 14 * gp.tileSize;

        gp.obj[mapNum][2] = new OBJ_GrassCol(gp);
        gp.obj[mapNum][2].worldX = 7 * gp.tileSize;
        gp.obj[mapNum][2].worldY = 15 * gp.tileSize;

        gp.obj[mapNum][1] = new OBJ_Banner(gp);
        gp.obj[mapNum][1].worldX = 32 * gp.tileSize;
        gp.obj[mapNum][1].worldY = 5 * gp.tileSize;

        gp.obj[mapNum][3] = new OBJ_GrassCol(gp);
        gp.obj[mapNum][3].worldX = 32 * gp.tileSize;
        gp.obj[mapNum][3].worldY = 5 * gp.tileSize;

        mapNum = 4;
        gp.obj[mapNum][0] = new OBJ_Banner(gp);
        gp.obj[mapNum][0].worldX = 32 * gp.tileSize;
        gp.obj[mapNum][0].worldY = 38 * gp.tileSize;

        gp.obj[mapNum][1] = new OBJ_GrassCol(gp);
        gp.obj[mapNum][1].worldX = 32 * gp.tileSize;
        gp.obj[mapNum][1].worldY = 39 * gp.tileSize;

        gp.obj[mapNum][2] = new OBJ_Chest(gp);
        gp.obj[mapNum][2].worldX = 30 * gp.tileSize;
        gp.obj[mapNum][2].worldY = 29 * gp.tileSize;

        gp.obj[mapNum][3] = new OBJ_Cherry(gp);
        gp.obj[mapNum][3].worldX = 34 * gp.tileSize;
        gp.obj[mapNum][3].worldY = 29 * gp.tileSize;

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
        gp.monsterArray[mapNum][0].life = gp.monsterArray[mapNum][0].maxLife;

        gp.monsterArray[mapNum][1] = new MON_Skeleton(gp);
        gp.monsterArray[mapNum][1].worldX = 27 * gp.tileSize;
        gp.monsterArray[mapNum][1].worldY = 19 * gp.tileSize;
        gp.monsterArray[mapNum][1].life = gp.monsterArray[mapNum][0].maxLife;

        gp.monsterArray[mapNum][2] = new MON_Skeleton(gp);
        gp.monsterArray[mapNum][2].worldX = 39 * gp.tileSize;
        gp.monsterArray[mapNum][2].worldY = 24 * gp.tileSize;
        gp.monsterArray[mapNum][2].life = gp.monsterArray[mapNum][0].maxLife;

        gp.monsterArray[mapNum][3] = new MON_Skeleton(gp);
        gp.monsterArray[mapNum][3].worldX = 23 * gp.tileSize;
        gp.monsterArray[mapNum][3].worldY = 22 * gp.tileSize;
        gp.monsterArray[mapNum][3].life = gp.monsterArray[mapNum][0].maxLife;

        gp.monsterArray[mapNum][4] = new MON_Skeleton(gp);
        gp.monsterArray[mapNum][4].worldX = 28 * gp.tileSize;
        gp.monsterArray[mapNum][4].worldY = 18 * gp.tileSize;
        gp.monsterArray[mapNum][4].life = gp.monsterArray[mapNum][0].maxLife;

        gp.monsterArray[mapNum][5] = new MON_Skeleton(gp);
        gp.monsterArray[mapNum][5].worldX = 34 * gp.tileSize;
        gp.monsterArray[mapNum][5].worldY = 25 * gp.tileSize;
        gp.monsterArray[mapNum][5].life = gp.monsterArray[mapNum][0].maxLife;

        gp.monsterArray[mapNum][6] = new MON_Skeleton(gp);
        gp.monsterArray[mapNum][6].worldX = 33 * gp.tileSize;
        gp.monsterArray[mapNum][6].worldY = 22 * gp.tileSize;
        gp.monsterArray[mapNum][6].life = gp.monsterArray[mapNum][0].maxLife;

        gp.monsterArray[mapNum][7] = new MON_Skeleton(gp);
        gp.monsterArray[mapNum][7].worldX = 30 * gp.tileSize;
        gp.monsterArray[mapNum][7].worldY = 13 * gp.tileSize;
        gp.monsterArray[mapNum][7].life = gp.monsterArray[mapNum][0].maxLife;

        gp.monsterArray[mapNum][8] = new MON_Skeleton(gp);
        gp.monsterArray[mapNum][8].worldX = 23 * gp.tileSize;
        gp.monsterArray[mapNum][8].worldY = 13 * gp.tileSize;
        gp.monsterArray[mapNum][8].life = gp.monsterArray[mapNum][0].maxLife;

        gp.monsterArray[mapNum][9] = new MON_Skeleton(gp);
        gp.monsterArray[mapNum][9].worldX = 26 * gp.tileSize;
        gp.monsterArray[mapNum][9].worldY = 15 * gp.tileSize;
        gp.monsterArray[mapNum][9].life = gp.monsterArray[mapNum][0].maxLife;

        mapNum = 3;
        gp.monsterArray[mapNum][0] = new MON_Goblin(gp);
        gp.monsterArray[mapNum][0].worldX = 25 * gp.tileSize;
        gp.monsterArray[mapNum][0].worldY = 11 * gp.tileSize;
        gp.monsterArray[mapNum][0].life = gp.monsterArray[mapNum][0].maxLife;

        gp.monsterArray[mapNum][1] = new MON_Goblin(gp);
        gp.monsterArray[mapNum][1].worldX = 39 * gp.tileSize;
        gp.monsterArray[mapNum][1].worldY = 10 * gp.tileSize;
        gp.monsterArray[mapNum][1].life = gp.monsterArray[mapNum][0].maxLife;

        gp.monsterArray[mapNum][2] = new MON_Goblin(gp);
        gp.monsterArray[mapNum][2].worldX = 33 * gp.tileSize;
        gp.monsterArray[mapNum][2].worldY = 18 * gp.tileSize;
        gp.monsterArray[mapNum][2].life = gp.monsterArray[mapNum][0].maxLife;

        gp.monsterArray[mapNum][3] = new MON_Goblin(gp);
        gp.monsterArray[mapNum][3].worldX = 24 * gp.tileSize;
        gp.monsterArray[mapNum][3].worldY = 28 * gp.tileSize;
        gp.monsterArray[mapNum][3].life = gp.monsterArray[mapNum][0].maxLife;

        mapNum = 4;
        gp.monsterArray[mapNum][0] = MON_Boss.getSingleInstance();
        gp.monsterArray[mapNum][0].worldX = 26 * gp.tileSize;
        gp.monsterArray[mapNum][0].worldY = 12 * gp.tileSize;
        gp.monsterArray[mapNum][0].life = gp.monsterArray[mapNum][0].maxLife;
    }
}
