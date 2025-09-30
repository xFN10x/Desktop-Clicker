package fn10.desktopClicker.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.random.RandomGenerator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import fn10.desktopClicker.ui.base.TransparentWindow;

public class CoinWindow extends TransparentWindow implements MouseListener {

    public final JLabel image = new JLabel(new ImageIcon(getClass().getResource("/coin.gif")));

    public static void spawnNew() {
        CoinWindow newWin = new CoinWindow();
        newWin.setVisible(true);
    }

    private CoinWindow() {
        super(new Dimension(100, 100));

        image.addMouseListener(this);
        add(image);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Random random = Random.from(RandomGenerator.getDefault());
        setLocation(random.nextInt(0, Double.valueOf(screenSize.getWidth()).intValue()), random.nextInt(0, Double.valueOf(screenSize.getHeight()).intValue()));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseClicked'");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }
}
