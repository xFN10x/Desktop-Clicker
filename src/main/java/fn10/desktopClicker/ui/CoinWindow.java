package fn10.desktopClicker.ui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.random.RandomGenerator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import fn10.desktopClicker.game.GameManager;
import fn10.desktopClicker.ui.base.TransparentWindow;
import fn10.desktopClicker.util.Various;

public class CoinWindow extends TransparentWindow implements MouseListener {

    public final JLabel image = new JLabel(new ImageIcon(getClass().getResource("/coin.gif")));

    public static void spawnNew() {
        CoinWindow newWin = new CoinWindow();
        newWin.setVisible(true);
    }

    private CoinWindow() {
        super(new Dimension(100, 100), false);

        image.addMouseListener(this);
        image.setCursor(new Cursor(Cursor.HAND_CURSOR));

        add(image);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Random random = Random.from(RandomGenerator.getDefault());
        setLocation(random.nextInt(0, Double.valueOf(screenSize.getWidth()).intValue()),
                random.nextInt(0, Double.valueOf(screenSize.getHeight()).intValue()));

        setAlwaysOnTop(true);

        new Timer().scheduleAtFixedRate(new TimerTask() {

            private long beforeDespawn = 30000;
            private long fadeCounter = 1000;

            @Override
            public void run() {
                if (beforeDespawn <= 0) {
                    setVisible(false);
                    dispose();
                    // fadeCounter--;
                }
                beforeDespawn--;
            }

        }, 1, 1);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            try {
                setVisible(false);
                GameManager.CurrentGame.Coins += 1;
                CoinAnimationWindow.showCoin();
                Various.playSound(new File(getClass().getResource("/coin.wav").toURI()));
                dispose();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
