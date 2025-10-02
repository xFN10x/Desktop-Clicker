package fn10.desktopClicker.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.random.RandomGenerator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

import fn10.desktopClicker.game.GameManager;
import fn10.desktopClicker.ui.base.TransparentWindow;
import fn10.desktopClicker.util.ImageUtilites;
import fn10.desktopClicker.util.Various;

public class CoinWindow extends TransparentWindow implements MouseListener {

    public final JLabel image = new JLabel(new ImageIcon(getClass().getResource("/coin.gif")));

    private final TimerTask onTick;

    private final SpringLayout lay = new SpringLayout();

    public static void spawnNew() {
        Rectangle bounds = ImageUtilites.getScreenWorkingArea(null);
        Random random = Random.from(RandomGenerator.getDefault());

        spawnNew(
                random.nextInt(0,
                        Double.valueOf(
                                bounds.getWidth())
                                .intValue() - 100), //subtract 100 cause it goes by top left corner
                random.nextInt(0,
                        Double.valueOf(
                                bounds.getHeight())
                                .intValue() - 100));
    }

    public static void spawnNew(Point loc, long lifetime) {
        spawnNew(Double.valueOf(loc.getX()).intValue(), Double.valueOf(loc.getY()).intValue(), lifetime);
    }

    public static void spawnNew(Point loc) {
        spawnNew(Double.valueOf(loc.getX()).intValue(), Double.valueOf(loc.getY()).intValue());
    }

    public static void spawnNew(int x, int y) {
        spawnNew(x, y, 5000);
    }

    public static void spawnNew(int x, int y, long lifetime) {

        CoinWindow newWin = new CoinWindow(lifetime);
        newWin.setVisible(true);
        newWin.setLocation(x, y);
    }

    private CoinWindow(long lifetime) {
        super(new Dimension(100, 100), false);

        onTick = new TimerTask() {

            public long beforeDespawn = lifetime;
            private int fadeCounter = 1000;

            @Override
            public void run() {
                if (GameManager.Paused)
                    return;
                if (beforeDespawn <= 0) {
                    if (fadeCounter == 1000)
                    GameManager.UpdateCoins(getLocation(), -1);
                    int size = (fadeCounter / 10);
                    image.setSize(size, size);
                    doLayout();
                    fadeCounter--;
                    return;
                }
                if (fadeCounter <= 0) {
                    setVisible(false);
                    dispose();
                    cancel();
                    return;
                }
                GameManager.UpdateCoins(getLocation(), beforeDespawn);
                beforeDespawn--;
            }

        };

        image.addMouseListener(this);
        image.setCursor(new Cursor(Cursor.HAND_CURSOR));

        add(image);

        lay.putConstraint(SpringLayout.HORIZONTAL_CENTER, image, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        lay.putConstraint(SpringLayout.VERTICAL_CENTER, image, 0, SpringLayout.VERTICAL_CENTER, getContentPane());

        setAlwaysOnTop(true);

        new Timer().scheduleAtFixedRate(onTick, 1, 1);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            try {
                onTick.cancel();
                setVisible(false);
                GameManager.CurrentGame.Coins += GameManager.CurrentGame.CoinsPerClick;
                GameManager.UpdateCoins(getLocation(), -1);
                CoinAnimationWindow.showCoin();
                Various.playSound(new File(getClass().getResource("/coin.wav").toURI()));
                dispose();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else if (e.getButton() == MouseEvent.BUTTON2) {
            JLabel labal = new JLabel(getLocation().toString());
            labal.setForeground(Color.white);
            add(labal);

            labal.setLocation(0, 0);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
