package fn10.desktopClicker.game.spells;

import javax.swing.ImageIcon;

import fn10.desktopClicker.game.SavedGame;

public interface ISpell extends IBaseSpell {
    ImageIcon getSpellImage();

    String getSpellName();

    String getSpellDescription();

    void cast(SavedGame game);

    int getManaRequirment();
}
