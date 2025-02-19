package views;

import javax.swing.*;

public class Utils {
    public static final String USER_PROFILES_PATH = "src/main/java/data/userProfiles.csv";

    static void setupModernUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            throw new RuntimeException("Failed to set modern look and feel", e);
        }
    }
}
