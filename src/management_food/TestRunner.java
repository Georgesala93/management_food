package management_food;

public class TestRunner {
    public static void main(String[] args) {
        System.out.println("Iniciando pruebas automáticas del sistema de pedidos...");

        FoodOrderApp app = new FoodOrderApp();

        // Añadir pedidos de prueba
        app.addOrderFields("Ensalada César", "Entrante", 2, false, "");
        app.addOrderFields("Lasaña", "Plato Principal", 3, true, "Calle Falsa 123");
        app.addOrderFields("Tarta de Queso", "Postre", 1, false, "");

        // Exportar pedidos (debe imprimir en la consola)
        app.exportOrders();

        // Resultado esperado: 3 pedidos exportados
        System.out.println("Pruebas automáticas completadas. Revisa la salida anterior para comprobar los pedidos.");
    }
}
