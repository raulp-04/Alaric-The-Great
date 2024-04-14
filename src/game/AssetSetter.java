package game;

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

        gp.obj[0] = new OBJ_Sword();
        gp.obj[0].worldX = 16 * gp.tileSize;
        gp.obj[0].worldY = 15 * gp.tileSize;

        gp.obj[1] = new OBJ_Key();
        gp.obj[1].worldX = 44 * gp.tileSize;
        gp.obj[1].worldY = 15 * gp.tileSize;

        gp.obj[2] = new OBJ_Gems();
        gp.obj[2].worldX = 31 * gp.tileSize;
        gp.obj[2].worldY = 28 * gp.tileSize;

        gp.obj[3] = new OBJ_Chest();
        gp.obj[3].worldX = 19 * gp.tileSize;
        gp.obj[3].worldY = 35 * gp.tileSize;
    }
}
