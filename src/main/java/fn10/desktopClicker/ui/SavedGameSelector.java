package fn10.desktopClicker.ui;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Spring;
import javax.swing.SpringLayout;

import fn10.desktopClicker.game.SavedGame;
import fn10.desktopClicker.ui.components.SavedGameSelection;
import fn10.desktopClicker.util.SettingsManager;

public class SavedGameSelector extends JFrame {

    public final JPanel InnerScroll = new JPanel();
    public final JScrollPane ScrollPane = new JScrollPane(InnerScroll, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    public final SpringLayout Lay = new SpringLayout();
    public final BoxLayout InnerLay = new BoxLayout(InnerScroll, BoxLayout.Y_AXIS);

    public SavedGameSelector() {
        super("Select Game...");
        setLayout(Lay);
        setSize(400, 500);

        Lay.putConstraint(SpringLayout.EAST, ScrollPane, 5, SpringLayout.EAST, getContentPane());
        Lay.putConstraint(SpringLayout.WEST, ScrollPane, 5, SpringLayout.WEST, getContentPane());
        Lay.putConstraint(SpringLayout.NORTH, ScrollPane, 5, SpringLayout.NORTH, getContentPane());
        Lay.putConstraint(SpringLayout.SOUTH, ScrollPane, 5, SpringLayout.SOUTH, getContentPane());

        InnerScroll.setLayout(InnerLay);

        try {
            for (SavedGame game : SettingsManager.load().games) {
                InnerScroll.add(new SavedGameSelection(game));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        add(ScrollPane);
    }

}
