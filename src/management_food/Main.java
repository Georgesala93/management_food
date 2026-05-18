package management_food;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FoodOrderApp app = new FoodOrderApp();
            app.setVisible(true);
        });
    }
}
