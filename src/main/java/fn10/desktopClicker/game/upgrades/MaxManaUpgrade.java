package fn10.desktopClicker.game.upgrades;

import javax.swing.ImageIcon;

import fn10.desktopClicker.game.SavedGame;

public class MaxManaUpgrade implements IUpgrade {

    @Override
    public ImageIcon getUpgradeImage() {
        return new ImageIcon(getClass().getResource("/upgrades/ManaSpeed.png"));
    }

    @Override
    public String getUpgradeName() {
        return "Max Mana";
    }

    @Override
    public String getUpgradeDescription() {
        return "Increases the amount of mana that you can store/use at one time";
    }

    @Override
    public int getLevel(SavedGame game) {
        return (int) Math.max(game.MaxMana / 5f, 1);
    }

    @Override
    public void setLevel(int level, SavedGame game) {
        game.MaxMana = (float) (level * 5);
    }

    @Override
    public void Upgrade(SavedGame game) {
        setLevel((int) getLevel(game) + 1, game);
    }

    @Override
    public int getCoinRequirment(SavedGame game) {
        return 100 + (100 * getLevel(game));
    }

    @Override
    public boolean isMaxLevel(SavedGame game) {
        if (getLevel(game) >= 60) {
            setLevel(60, game);
            return true;
        }
        return false;
    }

    @Override
    public void setLevel(SavedGame game) {
        setLevel((int) getLevel(game), game);
    }
}
