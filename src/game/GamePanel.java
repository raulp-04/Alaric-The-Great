package game;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = maxScreenCol * tileSize; // 768
    public final int screenHeight = maxScreenRow * tileSize; // 576

    // WORLD SETTINGS
    public final int maxWorldCol = 64;
    public final int maxWorldRow = 59;

    // FPS
    final double FPS = 60;

    // SYSTEM
    TileManager tileManager = new TileManager(this);
    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Sound music = new Sound();
    public Sound soundEffect = new Sound();
    public UI ui = new UI(this);
    public AssetSetter aSetter = new AssetSetter(this);

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyHandler);
    public SuperObject[] obj = new SuperObject[10];


    public GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(keyHandler);
        setFocusable(true);
    }

    public void setUpGame() {
        aSetter.setObject();
        playMusic(0);
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
       player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        // TILE
        tileManager.draw(g2d);

        // OBJECT
        for (SuperObject superObject : obj) {
            if (superObject != null) {
                superObject.draw(g2d, this);
            }
        }

        // PLAYER
        player.draw(g2d);

        // BAR FOR INFORMATION
        g2d.setColor(Color.BLACK);
        g2d.fillRect(10, 10, screenWidth-20, 80);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(10, 10, screenWidth-20, 5);
        g2d.fillRect(10, 85, screenWidth-20, 5);
        g2d.fillRect(10, 10, 5, 80);
        g2d.fillRect(screenWidth-15, 10, 5, 80);

        ui.draw(g2d);

        g2d.dispose();
    }

    public void playMusic(int i) {

        music.setFile(i);
        music.play();
        music.loop();

    }

    public void stopMusic(int i) {

        music.stop();
    }

    public void playSE(int i) {

        soundEffect.setFile(i);
        soundEffect.play();
    }
}