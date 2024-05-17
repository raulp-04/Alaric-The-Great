package tile;

import game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[100];
        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("res/maps/map01.txt", 1);
        loadMap("res/maps/map02.txt", 2);
        loadMap("res/maps/map03.txt", 3);
        loadMap("res/maps/map04.txt", 4);
    }
    public void getTileImage() {

        try {

            BufferedImage img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/tile-sheet.png"));
            BufferedImage water = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/water.png"));

            tile[0] = new Tile(); // GRASS [0]
            tile[0].image = img.getSubimage(32, 94, 16, 16);

            tile[1] = new Tile(); // STONE [1]
            tile[1].image = img.getSubimage(32, 142, 16, 16);

            tile[2] = new Tile(); // DIRT [2]
            tile[2].image = img.getSubimage(86, 144, 16, 16);

            tile[3] = new Tile(); // TREE [3]
            tile[3].image = img.getSubimage(112, 176, 32, 32);
            tile[3].collition = true;

            tile[4] = new Tile(); // MUSHROOM RED [4]
            tile[4].image = img.getSubimage(144, 208, 32, 32);
            tile[4].collition = true;

            tile[5] = new Tile(); // MUSHROOM BROWN [5]
            tile[5].image = img.getSubimage(112, 208, 32, 32);
            tile[5].collition = true;

            // GRASS AND DIRT
                // castle floor
            tile[6] = new Tile();
            tile[6].image = img.getSubimage(16, 192, 16, 16);
                // TOP COMBINATION [7]
            tile[7] = new Tile();
            tile[7].image = img.getSubimage(33, 110, 16, 16);
                // STONE WALL
            tile[8] = new Tile();
            tile[8].image = img.getSubimage(32, 208, 16, 16);
            tile[8].collition = true;

            tile[9] = new Tile();
            tile[9].image = water.getSubimage(80, 16, 16, 16);
            tile[9].collition = true;

            tile[10] = new Tile();
            tile[10].image = water.getSubimage(96, 16, 16, 16);
            tile[10].collition = true;



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadMap(String filePath, int map) {
        try {
            InputStream reader = new FileInputStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(reader));

            int col = 0;
            int row = 0;
            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();

                while (col < gp.maxWorldCol) {
                    String[] split = line.split(" ");
                    int number = Integer.parseInt(split[col]);
                    mapTileNum[map][col][row] = number;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                    if (row == 58) {
                        int i = 0;
                    }
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2d) {

        int worldCol = 0;
        int worldRow = 0;


        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + 2*gp.tileSize> gp.player.worldX-gp.player.screenX &&
                worldY + 2*gp.tileSize> gp.player.worldY-gp.player.screenY &&
                worldX - 2*gp.tileSize< gp.player.worldX+gp.player.screenX &&
                worldY - 2*gp.tileSize< gp.player.worldY+gp.player.screenY) { // END IF
                g2d.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
            worldCol++;


            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;

            }
        }
    }
}
