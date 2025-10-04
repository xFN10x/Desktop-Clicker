package fn10.desktopClicker.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.random.RandomGenerator;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.jspecify.annotations.NonNull;

/**
 * This class has various utilty functions.
 */
public class Various {

    private static final Map<String, Clip> CachedAudio = new HashMap<String, Clip>();

    static {
        try {
            preloadAudio(new File(Various.class.getResource("/cannotBuy.wav").toURI()));
            preloadAudio(new File(Various.class.getResource("/coin.wav").toURI()));
            preloadAudio(new File(Various.class.getResource("/upgrade.wav").toURI()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playRandomMiningSound() {
        try {
            playSound(new File(Various.class
                    .getResource(
                            "/miningSounds/mine" + (Random.from(RandomGenerator.getDefault()).nextInt(3) + 1) + ".wav")
                    .toURI()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static Clip preloadAudio(File file) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            return CachedAudio.put(file.getPath(), clip);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void playSound(File audioFile) {
        // do this on another thread so it doesnt slow down
        new Thread(() -> {
            try {
                Clip clip;
                if (CachedAudio.containsKey(audioFile.getPath())) {
                    clip = CachedAudio.get(audioFile.getPath());
                } else {
                    clip = preloadAudio(audioFile);
                }
                if (clip == null) return;
                
                if (clip.isRunning()) {
                    clip.stop();
                }
                clip.setFramePosition(0);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static Font MarioFont = SetupFont("/font.ttf", 14);

    protected static Font SetupFont(@NonNull String Path, int Size) {
        InputStream font_file;
        try {
            font_file = Various.class.getResourceAsStream(Path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Font font = null;
        try { // try setting font
            font = Font.createFont(Font.TRUETYPE_FONT, font_file).deriveFont(1, Size);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font); // reg font if not null
            font_file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return font;
    }

}
