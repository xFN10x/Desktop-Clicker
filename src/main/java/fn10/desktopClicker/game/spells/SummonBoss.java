package fn10.desktopClicker.game.spells;

import java.awt.Color;
import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import fn10.desktopClicker.game.GameManager;
import fn10.desktopClicker.game.SavedGame;
import fn10.desktopClicker.game.spells.interfaces.ISpell;
import fn10.desktopClicker.game.spells.interfaces.ISpellWithActiveText;

public class SummonBoss implements ISpell {

    @Override
    public ImageIcon getSpellImage() {
        return new ImageIcon(getClass().getResource("/spells/SummonGod.png"));
    }

    @Override
    public String getSpellName() {
        return "Summon... something";
    }

    @Override
    public String getSpellDescription() {
        return "I wonder what happens when you do this...";
    }

    @Override
    public void cast(SavedGame game) {
        for (Window windows : Window.getWindows()) {
            if (windows.isAlwaysOnTop()) {
                windows.setBackground(Color.BLUE);
            }
            windows.setAlwaysOnTop(false);
        }
        int option = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to do this? You should pause/finish anything that you are doing.", "Cast Spell?",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            game.Mana -= getManaRequirment(game);
        }

        for (Window windows : Window.getWindows()) {
            if (windows.getBackground().equals(Color.BLUE)) {
                windows.setAlwaysOnTop(true);
            }
        }
    }

    @Override
    public float getManaRequirment(SavedGame game) {
        return 300f;
    }

    @Override
    public boolean canCast(SavedGame game) {
        return true;
    }
}
