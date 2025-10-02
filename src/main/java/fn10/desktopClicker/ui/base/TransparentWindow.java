package fn10.desktopClicker.ui.base;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JWindow;

import fn10.desktopClicker.util.ImageUtilites;

public class TransparentWindow extends JWindow {

    public TransparentWindow(Dimension size) {
        this(size, true);
    }

    public TransparentWindow(Dimension size, boolean goToCenter) {
        super();

        //setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0f));

        //setResizable(false);
        setSize(size);
        if (goToCenter)
            setLocation(ImageUtilites.getScreenCenter(this));
    }

}
