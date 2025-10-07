package fn10.desktopClicker.ui.notTransparent;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import fn10.desktopClicker.game.GameManager;
import fn10.desktopClicker.game.spells.IBaseSpell;
import fn10.desktopClicker.game.spells.SummonCoins;
import fn10.desktopClicker.util.ImageUtilites;

public class SpellWindow extends JDialog {

    private final JPanel InnerScroll = new JPanel();
    private final JScrollPane ScrollPane = new JScrollPane(InnerScroll, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    private final SpringLayout Lay = new SpringLayout();
    private final BoxLayout InnerLay = new BoxLayout(InnerScroll, BoxLayout.Y_AXIS);

    private final IBaseSpell[] spells = new IBaseSpell[] {
            new SummonCoins()
    };

    public static void showSpells() {
        new SpellWindow().setVisible(true);
    }

    private SpellWindow() {
        super(null, "Spells", ModalityType.APPLICATION_MODAL);

        GameManager.Paused = true;

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                GameManager.Paused = false;
            }
        });

        setSize(601, 500);
        setResizable(false);

        setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setAlwaysOnTop(true);
        setLayout(Lay);

        ScrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        ScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        setLocation(ImageUtilites.getScreenCenter(this));

        Lay.putConstraint(SpringLayout.EAST, ScrollPane, -5, SpringLayout.EAST, getContentPane());
        Lay.putConstraint(SpringLayout.WEST, ScrollPane, 5, SpringLayout.WEST, getContentPane());
        Lay.putConstraint(SpringLayout.NORTH, ScrollPane, 5, SpringLayout.NORTH, getContentPane());
        Lay.putConstraint(SpringLayout.SOUTH, ScrollPane, -5, SpringLayout.SOUTH, getContentPane());

        InnerScroll.setLayout(InnerLay);

        for (IBaseSpell spell : spells) {
            try {
                SpringLayout lay = new SpringLayout();
                JPanel panel = new JPanel();
                JLabel image = new JLabel(spell.getSpellImage());

                panel.setLayout(lay);

                panel.add(image);

                InnerScroll.add(panel);
                InnerScroll.add(Box.createHorizontalStrut(10));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        add(ScrollPane);
    }

}
