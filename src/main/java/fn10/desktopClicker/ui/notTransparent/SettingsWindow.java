package fn10.desktopClicker.ui.notTransparent;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import com.google.gson.JsonSyntaxException;

import fn10.desktopClicker.enums.MenuPosition;
import fn10.desktopClicker.game.GameManager;
import fn10.desktopClicker.ui.GameMenu;
import fn10.desktopClicker.util.ImageUtilites;
import fn10.desktopClicker.util.SaveManager;
import fn10.desktopClicker.util.SpringUtilities;

public class SettingsWindow extends JDialog {

    private final JPanel InnerScroll = new JPanel(new SpringLayout());
    private final JScrollPane ScrollPane = new JScrollPane(InnerScroll, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private final SpringLayout Lay = new SpringLayout();

    private final JComboBox<MenuPosition> UILocationSelector = new JComboBox<MenuPosition>(MenuPosition.values());
    private final JComboBox<Boolean> SoundSelector = new JComboBox<Boolean>(
            new Boolean[] { Boolean.TRUE, Boolean.FALSE });

    private final JButton ApplyButton = new JButton("Apply");
    private final JButton ApplyCloseButton = new JButton("Apply & Close");

    public static void showWindow() {
        new SettingsWindow().setVisible(true);
    }

    private SettingsWindow() {
        super(null, "Settings", ModalityType.APPLICATION_MODAL);

        GameManager.Paused = true;

        setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                GameManager.Paused = false;
            }
        });

        setSize(500, 600);
        setResizable(false);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(Lay);

        setAlwaysOnTop(true);
        setLocation(ImageUtilites.getScreenCenter(this));

        ApplyButton.addActionListener(ac -> {
            try {
                SaveManager save = SaveManager.load();

                save.pos = ((MenuPosition) UILocationSelector.getSelectedItem());
                save.sound = ((Boolean) SoundSelector.getSelectedItem()).booleanValue();

                save.save();

                GameMenu.CurrentMenu.ApplySettings();
            } catch (JsonSyntaxException | IOException e) {
                e.printStackTrace();
            }
        });
        
        ApplyCloseButton.addActionListener(ac -> {
            ApplyButton.doClick();
            for (WindowListener windowListeners : getWindowListeners()) {
                windowListeners.windowClosing(null);
            }
            dispose();
        });

        Lay.putConstraint(SpringLayout.EAST, ScrollPane, -5, SpringLayout.EAST, getContentPane());
        Lay.putConstraint(SpringLayout.WEST, ScrollPane, 5, SpringLayout.WEST, getContentPane());
        Lay.putConstraint(SpringLayout.NORTH, ScrollPane, 5, SpringLayout.NORTH, getContentPane());
        Lay.putConstraint(SpringLayout.SOUTH, ScrollPane, -5, SpringLayout.NORTH, ApplyButton);

        Lay.putConstraint(SpringLayout.EAST, ApplyButton, -5, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        Lay.putConstraint(SpringLayout.WEST, ApplyButton, 5, SpringLayout.WEST, getContentPane());
        Lay.putConstraint(SpringLayout.SOUTH, ApplyButton, -5, SpringLayout.SOUTH, getContentPane());

        Lay.putConstraint(SpringLayout.EAST, ApplyCloseButton, -5, SpringLayout.EAST, getContentPane());
        Lay.putConstraint(SpringLayout.WEST, ApplyCloseButton, 5, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        Lay.putConstraint(SpringLayout.SOUTH, ApplyCloseButton, -5, SpringLayout.SOUTH, getContentPane());

        try {
            SaveManager save = SaveManager.load();
            UILocationSelector.setSelectedItem(save.pos);
            SoundSelector.setSelectedItem(save.sound);
        } catch (Exception e) {
            e.printStackTrace();
        }

        InnerScroll.add(new JLabel("UI Position"));
        UILocationSelector.setMaximumSize(new Dimension(500, UILocationSelector.getHeight()));
        InnerScroll.add(UILocationSelector);

        InnerScroll.add(new JLabel("Sound"));
        SoundSelector.setMaximumSize(new Dimension(500, SoundSelector.getHeight()));
        InnerScroll.add(SoundSelector);

        SpringUtilities.makeGrid(InnerScroll,
                2, 2, // rows, cols
                5, 5, // initialX, initialY
                5, 5);// xPad, yPad

        add(ScrollPane);

        add(ApplyButton);
        add(ApplyCloseButton);
    }
}
