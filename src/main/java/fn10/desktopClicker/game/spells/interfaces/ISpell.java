package fn10.desktopClicker.game.spells.interfaces;

import fn10.desktopClicker.game.SavedGame;

public interface ISpell extends IBaseSpell {
    void cast(SavedGame game);

    float getManaRequirment(SavedGame game);

    boolean canCast(SavedGame game);
}
