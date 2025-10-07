package fn10.desktopClicker.game.spells;

import fn10.desktopClicker.game.SavedGame;

public interface ICharageableSpell extends IBaseSpell {

    void cast(SavedGame game, int manaSpent);

    float getManaRequirment(SavedGame game, float timeHeld);

    float maxChargeTime();
    
}
