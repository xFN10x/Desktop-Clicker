package fn10.desktopClicker.ui.notTransparent;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import fn10.desktopClicker.util.ImageUtilites;
import fn10.desktopClicker.util.SpringUtilities;

public class SettingsWindow extends JDialog {

    private final JPanel InnerScroll = new JPanel(new SpringLayout());
    private final JScrollPane ScrollPane = new JScrollPane(InnerScroll, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private final SpringLayout Lay = new SpringLayout();

    

    public static void showWindow() {
        new SettingsWindow().setVisible(true);
    }

    private SettingsWindow() {
        super(null, "Settings", ModalityType.APPLICATION_MODAL);

        setSize(500, 600);
        setResizable(false);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(Lay);

        setAlwaysOnTop(true);
        setLocation(ImageUtilites.getScreenCenter(this));

        Lay.putConstraint(SpringLayout.EAST, ScrollPane, -5, SpringLayout.EAST, getContentPane());
        Lay.putConstraint(SpringLayout.WEST, ScrollPane, 5, SpringLayout.WEST, getContentPane());
        Lay.putConstraint(SpringLayout.NORTH, ScrollPane, 5, SpringLayout.NORTH, getContentPane());
        Lay.putConstraint(SpringLayout.SOUTH, ScrollPane, -5, SpringLayout.SOUTH, getContentPane());

        InnerScroll.add(new JLabel("test"));
        var button = new JButton("test");
        button.setMaximumSize(new Dimension(500, button.getHeight()));
        InnerScroll.add(button);

        SpringUtilities.makeGrid(InnerScroll,
                4, 2, // rows, cols
                5, 5, // initialX, initialY
                5, 5);// xPad, yPad

        add(ScrollPane);
    }
}
