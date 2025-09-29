package fn10.desktopClicker.ui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;

import fn10.desktopClicker.ui.base.TransparentWindow;

public class GameBar extends TransparentWindow {

    public final JButton SaveButton = new JButton("Save");
    public final JButton SaveExitButton = new JButton("Save & Exit");
    public final JButton ExitButton = new JButton("Exit");

    public final BoxLayout Lay = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);

    public GameBar() {
        super(new Dimension(300, 100));

        setLayout(Lay);

        add(SaveButton);
        add(SaveExitButton);
        add(ExitButton);
    }

}
