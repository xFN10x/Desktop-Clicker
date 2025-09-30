package fn10.desktopClicker.ui;

import java.awt.Dimension;
import java.nio.file.Files;

import javax.swing.BoxLayout;
import javax.swing.JButton;

import fn10.desktopClicker.game.GameManager;
import fn10.desktopClicker.ui.base.TransparentWindow;
import fn10.desktopClicker.util.SettingsManager;

public class GameBar extends TransparentWindow {

    public final JButton SaveButton = new JButton("Save");
    public final JButton SaveExitButton = new JButton("Save & Exit");
    public final JButton ExitButton = new JButton("Exit");

    public final BoxLayout Lay = new BoxLayout(getContentPane(), BoxLayout.X_AXIS);

    public GameBar() {
        super(new Dimension(300, 100));

        setLayout(Lay);

        ExitButton.addActionListener(ac -> {
            System.exit(0);
        });

        SaveExitButton.addActionListener(ac -> {
            GameManager.SaveCurrentGame();
            
            System.exit(0);
        });

        add(SaveButton);
        add(SaveExitButton);
        add(ExitButton);
    }

}
