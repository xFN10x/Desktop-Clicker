package fn10.desktopClicker.game.upgrades;

import javax.swing.ImageIcon;

import fn10.desktopClicker.game.SavedGame;

public class CoinsPerClickUpgrade implements IUpgrade {

    @Override
    public ImageIcon getUpgradeImage() {
        return new ImageIcon(getClass().getResource("/upgrades/CoinsPerClick.png"));
    }

    @Override
    public String getUpgradeName() {
        return "Coins Per Click";
    }

    @Override
    public String getUpgradeDescription() {
        return "Upgrade how many coins you get, every coin!";
    }

    @Override
    public int getLevel(SavedGame game) {
        return game.CoinsPerClick;
    }

    @Override
    public void setLevel(int level, SavedGame game) {
        game.CoinsPerClick = level;
    }

    @Override
    public void Upgrade(SavedGame game) {
        setLevel(game.CoinsPerClick + 1, game);
    }

    @Override
    public int getCoinRequirment(SavedGame game) {
        return (15 * (game.CoinsPerClick / 3)) + (30 * game.CoinsPerClick);
    }

    @Override
    public boolean isMaxLevel(SavedGame game) {
        if (game.CoinsPerClick >= Integer.MAX_VALUE) {
            return true;
        }
        return false;
    }

    @Override
    public void setLevel(SavedGame game) {
        setLevel(game.CoinsPerClick, game);
    }

}
