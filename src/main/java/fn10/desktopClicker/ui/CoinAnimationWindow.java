package fn10.desktopClicker.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import fn10.desktopClicker.game.GameManager;
import fn10.desktopClicker.ui.base.TransparentWindow;

public class CoinAnimationWindow extends TransparentWindow {
    /*
     * public static final ImageIcon COIN_ICON;
     * static {
     * COIN_ICON = new
     * ImageIcon(CoinAnimationWindow.class.getResource("/coin2.gif"));
     * }
     */

    public final JLabel image = new JLabel();

    public static void showCoin() {
        new CoinAnimationWindow(null).setVisible(true);
    }

    public static void showCoin(Point pos) {
        new CoinAnimationWindow(pos).setVisible(true);
    }

    private CoinAnimationWindow(Point pos) {
        super(new Dimension(16, 64));

        add(image);

        final int y;
        final int x;
        if (pos == null) {
            final Point basePos = MouseInfo.getPointerInfo().getLocation();
            x = Double.valueOf(basePos.getX()).intValue();
            y = Double.valueOf(basePos.getY()).intValue();
            setLocation(x, y);
        } else {
            x = (int) pos.getX();
            y = (int) pos.getY();
            setLocation(pos);
        }

        setAlwaysOnTop(true);

        new Timer().scheduleAtFixedRate(new TimerTask() {

            private long beforeDespawn = 600;
            private long goUp = 150;
            private int offsetY = -32;
            private JLabel score = new JLabel("+" + GameManager.CurrentGame.CoinsPerClick);
            {
                if (GameManager.CurrentGame.CoinDoubleTimer > 0) {
                    score.setText("+" + (GameManager.CurrentGame.CoinsPerClick * 2) + " (x2)");
                }
                score.setForeground(Color.white);
                score.setMinimumSize(new Dimension(100, 100));
            }

            @Override
            public void run() {
                if (GameManager.Paused)
                    return;
                if (beforeDespawn <= 600 && beforeDespawn > 0) {
                    if (image.isVisible())
                        image.setVisible(false);
                    if (!List.of(getComponents()).contains(score))
                        add(score);
                    if (offsetY != -64)
                        offsetY = -64;
                    if (!getSize().equals(new Dimension(100, 100))) {
                        setSize(100, 100);
                        doLayout();
                    }
                    int y1 = Double.valueOf(y + offsetY).intValue();
                    setLocation(x, y1);

                    beforeDespawn--;
                    return;
                } else if (beforeDespawn <= 0) {
                    setVisible(false);
                    dispose();
                    return;
                }
                if (goUp > 0) {
                    offsetY--;
                } else {
                    offsetY++;
                }

                int y1 = Double.valueOf(y + offsetY).intValue();
                if (pos == null)
                    setLocation(x, y1);

                beforeDespawn--;
                goUp--;
            }

        }, 1, 1);

    }

}
