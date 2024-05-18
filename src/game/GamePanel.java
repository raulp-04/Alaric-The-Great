package game;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class GamePanel extends JPanel implements Runnable {
    DataBase dataBase = new DataBase("Baza de Date", this);
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    public final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = maxScreenCol * tileSize; // 768
    public final int screenHeight = maxScreenRow * tileSize; // 576
    public final int NO_COLLISION = -1;


    // WORLD SETTINGS
    public final int maxWorldCol = 64;
    public final int maxWorldRow = 59;
    public final int maxMap = 10;
    public int currentMap = 1;

    // FPS
    final double FPS = 60;

    // SYSTEM
    TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Sound music = new Sound();
    public Sound soundEffect = new Sound();
    public EventHandler eventHandler = new EventHandler(this);
    public UI ui = new UI(this);
    public AssetSetter aSetter = new AssetSetter(this);

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyHandler);
    public Entity[][] obj = new Entity[maxMap][20];
    public Entity[][] npc = new Entity[maxMap][10];
    public Entity[][] monsterArray = new Entity[maxMap][20];
    ArrayList<Entity> entityList = new ArrayList<Entity>();

    // GAME STATE
    public final int MENU_STATE = 0;
    public final int PLAY_STATE = 1;
    public final int PAUSE_STATE = 2;
    public final int DIALOG_STATE = 3;
    public final int CONTROL_STATE = 4;
    public final int GAMEOVER_STATE = 5;
    public final int WIN_STATE = 6;
    public int gameState = MENU_STATE;

    public GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(keyHandler);
        setFocusable(true);
    }
    public void setUpGame() {

        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        playMusic(4);
    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override public void run() {

        double drawInterval = 1000000000 / FPS; // 0.01666 sec
        double delta = 0;
        double lastTime = System.nanoTime();
        long timer = 0;
        long currentTime;
        int drawCount = 0;

        while (gameThread.isAlive()) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;

            }
        }
    }
    public void update() {

        if (gameState == PLAY_STATE) {
           // PLAYER
           player.update();

           // NPC
            for (int i = 0; i < npc[currentMap].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }

            for (int i = 0; i < Objects.requireNonNull(monsterArray[currentMap]).length; i++) {
                if (monsterArray[currentMap][i] != null)
                    if (monsterArray[currentMap][i].alive) monsterArray[currentMap][i].update();
                    else {
                        monsterArray[currentMap][i].checkDrop();
                        monsterArray[currentMap][i] = null;
                        player.hasGem += 200;
                    }
            }
        }
    }
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // DEGUB
        long drawTime = System.nanoTime();
        if (keyHandler.debugText) {drawTime = System.nanoTime();}

        // TITLE SCREEN
        if (gameState == MENU_STATE) {
            ui.draw(g2d);
        } else {
            // TILE
            tileManager.draw(g2d);

            // ADD ENTITIES TO LIST
            for (int i = 0; i < npc[currentMap].length; i++) {
                if (npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                }
            }
            for (int i = 0; i < obj[currentMap].length; i++) {
                if (obj[currentMap][i] != null) {
                    entityList.add(obj[currentMap][i]);
                }
            }
            for (int i = 0; i < Objects.requireNonNull(monsterArray[currentMap]).length; i++) {
                if (monsterArray[currentMap][i] != null) {
                    entityList.add(monsterArray[currentMap][i]);
                }
            }
            // SORT
            entityList.sort(new Comparator<Entity>() {
                @Override
                public int compare(Entity o1, Entity o2) {
                    return (o1.worldY < o2.worldY) ? 1 : -1;
                }
            });

            //DRAW ENTITIES
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2d);
            }
            player.draw(g2d);
            // EMPTY LIST
            entityList.clear();

            // BAR FOR INFORMATION
            g2d.setColor(Color.BLACK);
            g2d.fillRoundRect(10, 10, screenWidth - 20, 80, 10, 10);
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(5));
            g2d.drawRoundRect(10, 10, screenWidth - 20, 80, 10, 10);

            ui.draw(g2d);
        }
        if (keyHandler.debugText) {
            long drawTime2 = System.nanoTime();
            long passedTime = drawTime2 - drawTime;
            //BAR
            g2d.setColor(Color.BLACK);
            g2d.fillRoundRect(screenWidth-310, screenHeight-40, 300, 35, 10, 10);
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(5));
            g2d.drawRoundRect(screenWidth-310, screenHeight-40, 300, 35, 10, 10);

            g2d.setFont(ui.mPixel.deriveFont(30F));
            g2d.setColor(Color.BLACK);
            g2d.drawString("Draw Time " + passedTime, screenWidth - 283, screenHeight - 13);
            g2d.setColor(Color.WHITE);

            g2d.setColor(Color.BLACK);
            g2d.fillRoundRect(screenWidth-220, screenHeight*3/4-35, 210, 130, 10, 10);
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(5));
            g2d.drawRoundRect(screenWidth-220, screenHeight*3/4-35, 210, 130, 10, 10);
            g2d.drawString("Draw Time " + passedTime, screenWidth - 283, screenHeight - 13);
            g2d.drawString("WorldX  " + player.worldX, screenWidth-215, screenHeight*3/4-5);
            g2d.drawString("WorldY  " + player.worldY, screenWidth-215, screenHeight*3/4+25);
            g2d.drawString("Col  " + (player.worldX+player.solidArea.x)/tileSize, screenWidth-215, screenHeight*3/4+55);
            g2d.drawString("Row  " + (player.worldY+player.solidArea.y)/tileSize, screenWidth-215, screenHeight*3/4+85);
        }
        g2d.dispose();
    }
    public void playMusic(int i) {

        music.setFile(i);
        music.play();
        music.loop();

    }
    public void stopMusic() {

        music.stop();
    }
    public void playSE(int i) {

        soundEffect.setFile(i);
        soundEffect.play();
    }
    public void retry() {
        player.setDefaultPos();
        aSetter.setNPC();
        aSetter.setMonster();
        player.hasGem = 0;
        stopMusic();
        if (currentMap == 4)playMusic(13); else playMusic(0);
        UI.hasEnteredOnce = true;
    }
    public void restart() {
        player.setDefaultValues();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        stopMusic();
        playMusic(0);
        currentMap = 1;
    }
    public void loadData() {

        // Player Settings
        String tableName = "PLAYER_SETTINGS";

        // Extragere date din tabel
        dataBase.selectPlayerTable(tableName);
    }
    public void saveData() {

        // Entities
        String monsters = "";
        for(int i = 0; i < maxMap; ++i) {
            for(int j = 0; j < monsterArray[i].length; ++j) {
                if(monsterArray[i][j] != null) {
                    monsters += monsterArray[i][j].worldX + ", " + monsterArray[i][j].worldY + ", " + monsterArray[i][j].life + ", ";
                } else {
                    monsters += -1 + ", " + -1 + ", " + -1 + ", ";
                }
            }
        }
        if(!monsters.isEmpty()) {
            monsters = monsters.substring(0, monsters.length() - 2);
        }

        String NPC = "";
        for(int i = 0; i < maxMap; ++i) {
            for(int j = 0; j < npc[i].length; ++j) {
                if(npc[i][j] != null) {
                    NPC += npc[i][j].worldX + ", " + npc[i][j].worldY + ", ";
                } else {
                    NPC += -1 + ", " + -1 + ", ";
                }
            }
        }
        if(!NPC.isEmpty()) {
            NPC = NPC.substring(0, NPC.length() - 2);
        }

        String objects = "";
        for(int i = 0; i < maxMap; ++i) {
            for(int j = 0; j < obj[i].length; ++j) {
                if(obj[i][j] != null) {
                    objects += obj[i][j].worldX + ", " + obj[i][j].worldY + ", ";
                } else {
                    objects += -1 + ", " + -1 + ", ";
                }
            }
        }
        if(!objects.isEmpty()) {
            objects = objects.substring(0, objects.length() - 2);
        }

        // Player Settings
        String tableName = "PLAYER_SETTINGS";

        // Creare tabel daca nu exista
        ArrayList<String> fields = new ArrayList<>();
        fields.add("PLAYERPOSX");
        fields.add("TEXT");
        fields.add("PLAYERPOSY");
        fields.add("TEXT");
        fields.add("CURRENTMAP");
        fields.add("TEXT");
        fields.add("DIRECTION");
        fields.add("TEXT");
        fields.add("LIFE");
        fields.add("TEXT");
        fields.add("SCORE");
        fields.add("TEXT");
        fields.add("HASWEAPON");
        fields.add("TEXT");
        fields.add("HASKEY");
        fields.add("TEXT");
        fields.add("MONSTERS");
        fields.add("TEXT");
        fields.add("NPC");
        fields.add("TEXT");
        fields.add("OBJECTS");
        fields.add("TEXT");
        dataBase.createPlayerTable(tableName, fields);

        // Adaugare date in tabel
        fields.clear();
        fields.add("PLAYERPOSX"); // 1
        fields.add("PLAYERPOSY"); // 2
        fields.add("CURRENTMAP"); // 3
        fields.add("DIRECTION"); // 4
        fields.add("LIFE"); // 5
        fields.add("SCORE"); // 6
        fields.add("HASWEAPON");
        fields.add("HASKEY");
        fields.add("MONSTERS"); // 7
        fields.add("NPC"); // 8
        fields.add("OBJECTS"); // 9
        ArrayList<String> values = new ArrayList<>();
        values.add(String.valueOf(player.worldX)); // 1
        values.add(String.valueOf(player.worldY)); // 2
        values.add(String.valueOf(currentMap)); // 3
        values.add(player.direction); // 4
        values.add(String.valueOf(player.life)); // 5
        values.add(String.valueOf(player.hasGem)); // 6
        if (player.hasSword) values.add("1"); else values.add("0");
        if (player.hasKey) values.add("1"); else values.add("0");
        values.add(monsters); // 7
        values.add(NPC); // 8
        values.add(objects); // 9
        dataBase.insertPlayerTable(tableName, fields, values);
    }
}