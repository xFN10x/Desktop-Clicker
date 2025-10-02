package fn10.desktopClicker.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.jspecify.annotations.NonNull;

/**
 * This class has various utilty functions.
 */
public class Various {

    static {
        try {
            // this preloads all sounds
            AudioSystem.getAudioInputStream(new File(Various.class.getResource("/upgrade.wav").toURI()));
            AudioSystem.getAudioInputStream(new File(Various.class.getResource("/coin.wav").toURI()));
            AudioSystem.getAudioInputStream(new File(Various.class.getResource("/cannotBuy.wav").toURI()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playSound(File audioFile) {
        // do this on another thread so it doesnt slow down
        new Thread(() -> {
            try {
                AudioInputStream ais = AudioSystem.getAudioInputStream(audioFile);
                Clip clip = AudioSystem.getClip();
                clip.open(ais);
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
