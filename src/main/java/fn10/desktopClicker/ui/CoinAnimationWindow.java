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

import fn10.desktopClicker.ui.base.TransparentWindow;
import fn10.desktopClicker.util.Various;

public class CoinAnimationWindow extends TransparentWindow {

    public final JLabel image = new JLabel(new ImageIcon(getClass().getResource("/coin2.gif")));

    public static void showCoin() {
        new CoinAnimationWindow().setVisible(true);
        ;
    }

    private CoinAnimationWindow() {
        super(new Dimension(16, 64));

        add(image);

        new Timer().scheduleAtFixedRate(new TimerTask() {

            private long beforeDespawn = 900;
            private long goUp = 150;
            private int offsetY = -32;
            private int offsetX = -8;
            private JLabel score = new JLabel("+1");
            {
                score.setForeground(Color.white);
               // score.setFont(score.getFont().deriveFont(30f));
            }

            @Override
            public void run() {
                if (beforeDespawn <= 600 && beforeDespawn > 0) {
                    if (image.isVisible())
                        image.setVisible(false);
                    if (!List.of(getComponents()).contains(score))
                        add(score);
                    if (offsetY != -64)
                        offsetY = -64;
                    if (getSize().equals(new Dimension(100, 100))) {
                        setSize(100, 100);
                        doLayout();
                    }

                    Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
                    int x = Double.valueOf(mouseLoc.getX() + offsetX).intValue();
                    int y = Double.valueOf(mouseLoc.getY() + offsetY).intValue();
                    setLocation(x, y);

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
                Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
                int x = Double.valueOf(mouseLoc.getX() + offsetX).intValue();
                int y = Double.valueOf(mouseLoc.getY() + offsetY).intValue();
                setLocation(x, y);
                beforeDespawn--;
                goUp--;
            }

        }, 1, 1);

    }

}
