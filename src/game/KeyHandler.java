package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.lang.System.exit;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    public boolean debugText;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (gp.gameState == gp.MENU_STATE) {
            if (code == KeyEvent.VK_W) {
                gp.ui.command--;
                if (!UI.hasEnteredOnce) {
                    if (gp.ui.command < 1) gp.ui.command = 4;
                } else if (gp.ui.command < 0) gp.ui.command = 4;
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.command++;
                if (gp.ui.command > 4) if (!UI.hasEnteredOnce) gp.ui.command = 1; else gp.ui.command = 0;
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.command == 0) { // resume
                    if (UI.hasEnteredOnce) {
                        gp.stopMusic();
                        gp.gameState = gp.PLAY_STATE;
                        gp.playMusic(0);
                    }
                }
                if (gp.ui.command == 1) {
                    if (!UI.hasEnteredOnce) {UI.hasEnteredOnce = true; gp.ui.command=0;} // newgame
                        gp.stopMusic();
                        gp.gameState = gp.PLAY_STATE;
                        gp.restart();
                }
                if (gp.ui.command == 2) { // load
                    gp.loadData();
                    gp.stopMusic();
                    gp.gameState = gp.PLAY_STATE;
                    gp.playMusic(0);
                    UI.hasEnteredOnce = true;
                }
                if (gp.ui.command == 3) {
                    gp.gameState = gp.CONTROL_STATE;
                }
                if (gp.ui.command == 4) {
                    if (!UI.hasEnteredOnce) {exit(0);}
                    gp.saveData();
                    exit(0);
                }
            }
        }
        else if (gp.gameState == gp.CONTROL_STATE) {
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.MENU_STATE;
            }
        }
        else if (gp.gameState == gp.PLAY_STATE) {

            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_T) {
                debugText = !debugText;
            }
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.PAUSE_STATE;
            }
            if (code == KeyEvent.VK_ESCAPE) {
                gp.stopMusic();
                gp.gameState = gp.MENU_STATE;
                gp.ui.command = 0;
                gp.playMusic(4);
            }

        }
        else if (gp.gameState == gp.PAUSE_STATE) {
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.PLAY_STATE;
            }
        }
        else if (gp.gameState == gp.DIALOG_STATE) {
            if (code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.PLAY_STATE;
            }
        }
        else if (gp.gameState == gp.GAMEOVER_STATE) {
            if (code == KeyEvent.VK_W) {
                gp.ui.command--;
                if (gp.ui.command <= 0) gp.ui.command = 2;
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.command++;
                if (gp.ui.command >=3 || gp.ui.command <=0) gp.ui.command = 1;
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.command == 1) {
                    gp.gameState = gp.PLAY_STATE;
                    gp.retry();
                } else if (gp.ui.command == 2) {
                    exit(1);
                }
            }
        }
        else if (gp.gameState == gp.WIN_STATE) {
            if (code == KeyEvent.VK_ENTER) {
                exit(1);
            }
        }
    }
    @Override public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        // PLAY STATE
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = false;
        }
    }
}
