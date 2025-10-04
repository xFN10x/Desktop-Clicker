package fn10.desktopClicker.game.upgrades;

import javax.swing.ImageIcon;

import fn10.desktopClicker.game.SavedGame;

public class CoinAutoCollectUpgrade implements IUpgrade {

    @Override
    public ImageIcon getUpgradeImage() {
        return new ImageIcon(getClass().getResource("/upgrades/AutoCoinUpgrade.png"));
    }

    @Override
    public String getUpgradeName() {
        return "Auto Coin Collection";
    }

    @Override
    public String getUpgradeDescription() {
        return "Increases the chance of coins being auto collected.";
    }

    @Override
    public int getLevel(SavedGame game) {
        return game.CoinAutoCollectChance / 2;
    }

    @Override
    public void setLevel(int level, SavedGame game) {
        game.CoinAutoCollectChance = level * 2;
    }

    @Override
    public void Upgrade(SavedGame game) {
        setLevel((int) (game.CoinAutoCollectChance / 2) + 1, game);
    }

    @Override
    public int getCoinRequirment(SavedGame game) {
        return 100 + (50 * game.CoinAutoCollectChance);
    }

    @Override
    public boolean isMaxLevel(SavedGame game) {
        if ((game.CoinAutoCollectChance / 2) >= 50) {
            game.CoinAutoCollectChance = 100;
            return true;
        }
        return false;
    }

    @Override
    public void setLevel(SavedGame game) {
        setLevel((int) (game.CoinAutoCollectChance / 2), game);
    }

}
