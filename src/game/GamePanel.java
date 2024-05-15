package game;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = maxScreenCol * tileSize; // 768
    public final int screenHeight = maxScreenRow * tileSize; // 576
    public final int NO_COLLISION = -1;


    // WORLD SETTINGS
    public final int maxWorldCol = 64;
    public final int maxWorldRow = 59;

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
    public Entity[] obj = new Entity[10];
    public Entity[] npc = new Entity[10];
    public Entity[] monsterArray = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<Entity>();

    // GAME STATE
    public final int MENU_STATE = 0;
    public final int PLAY_STATE = 1;
    public final int PAUSE_STATE = 2;
    public final int DIALOG_STATE = 3;
    public final int CONTROL_STATE = 4;

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

    @Override
    public void run() {

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
            for (Entity entity : npc) {
                if (entity != null) {
                    entity.update();
                }
            }

            for (Entity entity : monsterArray) {
                if (entity != null) {
                    entity.update();
                }
            }
        }
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // DEGUB
        long drawTime = System.nanoTime();
        if (keyHandler.drawTime) {drawTime = System.nanoTime();}

        // TITLE SCREEN
        if (gameState == MENU_STATE) {
            ui.draw(g2d);
        } else {
            // TILE
            tileManager.draw(g2d);

            // ADD ENTITIES TO LIST
            for (Entity NPC : npc)
                if (NPC != null)
                    entityList.add(NPC);
            for (Entity OBJ : obj)
                if (OBJ != null)
                    entityList.add(OBJ);
            for (Entity MONSTER : monsterArray)
                if (MONSTER != null)
                    entityList.add(MONSTER);

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
        if (keyHandler.drawTime) {
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
            g2d.drawString("Draw Time " + passedTime, screenWidth - 283, screenHeight - 13);
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
}