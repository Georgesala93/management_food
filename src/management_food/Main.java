package management_food;

import javax.swing.SwingUtilities;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class Main {
    public static void main(String[] args) {
        // Force UTF-8 for console output to avoid mojibake on Windows terminals
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            FoodOrderApp app = new FoodOrderApp();
            app.setVisible(true);
        });
    }
}
