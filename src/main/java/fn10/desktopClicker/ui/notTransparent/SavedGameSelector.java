package fn10.desktopClicker.ui.notTransparent;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import fn10.desktopClicker.game.SavedGame;
import fn10.desktopClicker.ui.components.SavedGameSelection;
import fn10.desktopClicker.util.ImageUtilites;
import fn10.desktopClicker.util.SaveManager;

public class SavedGameSelector extends JFrame {

    public final JPanel InnerScroll = new JPanel();
    public final JScrollPane ScrollPane = new JScrollPane(InnerScroll, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    public final SpringLayout Lay = new SpringLayout();
    public final BoxLayout InnerLay = new BoxLayout(InnerScroll, BoxLayout.Y_AXIS);

    public SavedGameSelector(Window openAfter) {
        super("Select Game...");
        setLayout(Lay);
        setSize(400, 500);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                openAfter.setVisible(true);
            }
        });

        ScrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        ScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        Lay.putConstraint(SpringLayout.EAST, ScrollPane, -5, SpringLayout.EAST, getContentPane());
        Lay.putConstraint(SpringLayout.WEST, ScrollPane, 5, SpringLayout.WEST, getContentPane());
        Lay.putConstraint(SpringLayout.NORTH, ScrollPane, 5, SpringLayout.NORTH, getContentPane());
        Lay.putConstraint(SpringLayout.SOUTH, ScrollPane, -5, SpringLayout.SOUTH, getContentPane());

        InnerScroll.setLayout(InnerLay);

        try {
            for (SavedGame game : SaveManager.load().games) {
                InnerScroll.add(new SavedGameSelection(game));
                InnerScroll.add(Box.createVerticalStrut(5));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLocation(ImageUtilites.getScreenCenter(this));

        add(ScrollPane);
    }

}
