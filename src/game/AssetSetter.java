package game;

import entity.NPC_King;
import monster.MON_Skeleton;
import object.OBJ_Chest;
import object.OBJ_Gems;
import object.OBJ_Key;
import object.OBJ_Sword;
import tile.Tile;

import java.awt.*;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;

    }
    public void setObject() {

        gp.obj[0] = new OBJ_Sword(gp);
        gp.obj[0].worldX = 16 * gp.tileSize;
        gp.obj[0].worldY = 15 * gp.tileSize;

        gp.obj[1] = new OBJ_Key(gp);
        gp.obj[1].worldX = 44 * gp.tileSize;
        gp.obj[1].worldY = 15 * gp.tileSize;

        gp.obj[2] = new OBJ_Gems(gp);
        gp.obj[2].worldX = 31 * gp.tileSize;
        gp.obj[2].worldY = 28 * gp.tileSize;

        gp.obj[3] = new OBJ_Chest(gp);
        gp.obj[3].worldX = 19 * gp.tileSize;
        gp.obj[3].worldY = 35 * gp.tileSize;
    }

    public void setNPC() {
        gp.npc[0] = new NPC_King(gp);
        gp.npc[0].worldX = 32 * gp.tileSize;
        gp.npc[0].worldY = 27 * gp.tileSize;

    }

    public void setMonster() {
        gp.monsterArray[0] = new MON_Skeleton(gp);
        gp.monsterArray[0].worldX = 32 * gp.tileSize;
        gp.monsterArray[0].worldY = 17 * gp.tileSize;

        gp.monsterArray[1] = new MON_Skeleton(gp);
        gp.monsterArray[1].worldX = 33 * gp.tileSize;
        gp.monsterArray[1].worldY = 17 * gp.tileSize;

        gp.monsterArray[2] = new MON_Skeleton(gp);
        gp.monsterArray[2].worldX = 34 * gp.tileSize;
        gp.monsterArray[2].worldY = 17 * gp.tileSize;

        gp.monsterArray[3] = new MON_Skeleton(gp);
        gp.monsterArray[3].worldX = 35 * gp.tileSize;
        gp.monsterArray[3].worldY = 17 * gp.tileSize;
    }
}
