package fn10.desktopClicker.game.upgrades;

import javax.swing.ImageIcon;

import fn10.desktopClicker.game.SavedGame;

public class ManaSpeedUpgrade implements IUpgrade {

    @Override
    public ImageIcon getUpgradeImage() {
        return new ImageIcon(getClass().getResource("/upgrades/ManaSpeed.png"));
    }

    @Override
    public String getUpgradeName() {
        return "Mana Recharge Speed";
    }

    @Override
    public String getUpgradeDescription() {
        return "Increases the speed of which you get mana over time.";
    }

    @Override
    public int getLevel(SavedGame game) {
        return (int) Math.max(game.ManaRecharge / 0.001f, 1);
    }

    @Override
    public void setLevel(int level, SavedGame game) {
        game.ManaRecharge = (float) (level * 0.001);
    }

    @Override
    public void Upgrade(SavedGame game) {
        setLevel((int) getLevel(game) + 1, game);
    }

    @Override
    public int getCoinRequirment(SavedGame game) {
        return 50 + (50 * getLevel(game));
    }

    @Override
    public boolean isMaxLevel(SavedGame game) {
        if (getLevel(game) >= 100) {
            game.CoinAutoCollectChance = 100;
            return true;
        }
        return false;
    }

    @Override
    public void setLevel(SavedGame game) {
        setLevel((int) getLevel(game), game);
    }

}
