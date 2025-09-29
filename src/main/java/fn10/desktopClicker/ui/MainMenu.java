package fn10.desktopClicker.ui;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.SpringLayout;

import fn10.desktopClicker.ui.base.TransparentWindow;

public class MainMenu extends TransparentWindow {

    public final JButton ExitButton = new JButton("Exit");

    public final BoxLayout Lay = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);

    public MainMenu() {
        super(new Dimension(200, 300));

        ExitButton.addActionListener(ac -> {

        });

        setLayout(Lay);

        ExitButton.setAlignmentY(0.5f);
        ExitButton.setAlignmentX(0.5f);

        add(Box.createVerticalGlue());
        add(ExitButton);
        add(Box.createVerticalGlue());

    }

}
