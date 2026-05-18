package management_food;

public class TestRunner {
    public static void main(String[] args) {
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        } catch (java.io.UnsupportedEncodingException ignored) {
        }

        System.out.println("Iniciando pruebas automáticas del sistema de pedidos...");

        FoodOrderApp app = new FoodOrderApp();

        // Añadir pedidos de prueba
        app.addOrderFields("Ensalada César", "Entrante", 2, false, "");
        app.addOrderFields("Lasaña", "Plato Principal", 3, true, "Calle Falsa 123");
        app.addOrderFields("Tarta de Queso", "Postre", 1, false, "");

        // Exportar pedidos (ahora también a archivo UTF-8 para evitar problemas en la consola)
        app.exportOrders();
        app.exportOrdersToFile("out/pedidos_utf8.txt");

        // Resultado esperado: 3 pedidos exportados
        System.out.println("Pruebas automáticas completadas. Revisa la salida anterior para comprobar los pedidos.");
    }
}
