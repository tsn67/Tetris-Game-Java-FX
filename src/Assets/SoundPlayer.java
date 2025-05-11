package Assets;

import javax.sound.sampled.*;

public class SoundPlayer {
    public static void play(String filePath) {
        try (AudioInputStream audioIn = AudioSystem.getAudioInputStream(
                SoundPlayer.class.getResource(filePath))) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
