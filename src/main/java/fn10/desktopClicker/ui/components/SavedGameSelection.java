package fn10.desktopClicker.ui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;

import fn10.desktopClicker.game.GameManager;
import fn10.desktopClicker.game.SavedGame;

public class SavedGameSelection extends JPanel implements MouseListener {

    private final SavedGame Target;

    protected final JLabel name = new JLabel();
    protected final JLabel lastPlayed = new JLabel();

    public final SpringLayout Lay = new SpringLayout();

    public SavedGameSelection(SavedGame target) {
        super();
        this.Target = target;
        setMaximumSize(new Dimension(300, 50));
        setLayout(Lay);

        Lay.putConstraint(SpringLayout.WEST, name, 3, SpringLayout.WEST, this);
        Lay.putConstraint(SpringLayout.EAST, name, -3, SpringLayout.EAST, this);
        Lay.putConstraint(SpringLayout.NORTH, name, 3, SpringLayout.NORTH, this);

        Lay.putConstraint(SpringLayout.WEST, lastPlayed, 3, SpringLayout.WEST, this);
        Lay.putConstraint(SpringLayout.EAST, lastPlayed, -3, SpringLayout.EAST, this);
        Lay.putConstraint(SpringLayout.SOUTH, lastPlayed, -3, SpringLayout.SOUTH, this);

        setBorder(new LineBorder(Color.gray, 1));

        name.setText(Target.GameName);

        if (Target.LastPlayed != null)
            lastPlayed.setText(DateFormat.getInstance().format(new Date(Target.LastPlayed.toEpochMilli())));
        else {
            lastPlayed.setText("Never");
        }

        add(name);
        add(lastPlayed);

        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            GameManager.LoadGame(Target);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setBackground(Color.lightGray);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setBackground(Color.white);
    }

}
