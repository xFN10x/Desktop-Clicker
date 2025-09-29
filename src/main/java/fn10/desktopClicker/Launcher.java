package fn10.desktopClicker;

import com.formdev.flatlaf.FlatLightLaf;

import fn10.desktopClicker.ui.MainMenu;

public class Launcher {

    public static void main(String[] args) {
        try {
            FlatLightLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        new MainMenu().setVisible(true);
    }
}
