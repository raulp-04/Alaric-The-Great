package game;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/sound/background.wav");
        soundURL[1] = getClass().getResource("/sound/chest-open.wav");
        soundURL[2] = getClass().getResource("/sound/pick-up.wav");
        soundURL[3] = getClass().getResource("/sound/sword-slash.wav");
        soundURL[4] = getClass().getResource("/sound/menu-music.wav");
        soundURL[5] = getClass().getResource("/sound/skeleton-death.wav");
        soundURL[6] = getClass().getResource("/sound/alaric-hurt.wav");
        soundURL[7] = getClass().getResource("/sound/drinking.wav");
        soundURL[8] = getClass().getResource("/sound/game-over.wav");
        soundURL[9] = getClass().getResource("/sound/walk.wav");
    }
    public void setFile(int i) {

        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void play() {

        clip.start();
    }
    public void loop() {

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop() {

        clip.stop();
    }
}
