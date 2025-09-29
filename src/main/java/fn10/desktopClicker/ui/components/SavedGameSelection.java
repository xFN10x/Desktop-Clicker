package fn10.desktopClicker.ui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;

import fn10.desktopClicker.game.GameManager;
import fn10.desktopClicker.game.SavedGame;

public class SavedGameSelection extends JPanel implements MouseListener {

    private final SavedGame Target;

    protected final JLabel name = new JLabel();

    public final SpringLayout Lay = new SpringLayout();

    public SavedGameSelection(SavedGame target) {
        super();
        this.Target = target;
        setMaximumSize(new Dimension(300, 50));
        setLayout(Lay);

        Lay.putConstraint(SpringLayout.WEST, name, 3, SpringLayout.WEST, this);
        Lay.putConstraint(SpringLayout.EAST, name, 3, SpringLayout.EAST, this);
        Lay.putConstraint(SpringLayout.NORTH, name, 3, SpringLayout.NORTH, this);
        Lay.putConstraint(SpringLayout.SOUTH, name, 3, SpringLayout.SOUTH, this);

        setBorder(new LineBorder(Color.gray, 3));

        name.setText(Target.GameName);

        add(name);

        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            GameManager.LoadGame(Target);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        setBackground(Color.lightGray);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setBackground(Color.white);
    }

}
