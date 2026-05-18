# Management Food

Proyecto Java Swing para la gestión de pedidos de alimentos.

## Estructura

- `src/management_food/Main.java`: clase con el método `main` para iniciar la aplicación.
- `src/management_food/FoodOrderApp.java`: interfaz gráfica y lógica de validación, almacenamiento y exportación de pedidos.
- `src/management_food/Order.java`: modelo de datos de un pedido.

## Abrir en NetBeans

1. Abre NetBeans.
2. Selecciona `File > Open Project`.
3. Navega hasta la carpeta `management_food` y abre el proyecto.

## Ejecutar desde línea de comandos

1. Abre una terminal en la carpeta `management_food`.
2. Compila el proyecto:

```powershell
javac -d out src\management_food\*.java
```

3. Ejecuta la aplicación:

```powershell
java -cp out management_food.Main
```

### Ejecutar pruebas automáticas

Después de compilar, ejecuta:

```powershell
java -cp out management_food.TestRunner
```

## Funcionalidades implementadas

- Validación de datos en el formulario:
  - Nombre del plato obligatorio y con caracteres alfanuméricos.
  - Categoría obligatoria.
  - Cantidad obligatoria y número entero positivo.
  - Selección de método de entrega obligatoria.
  - Dirección válida cuando se elige Domicilio.
- Almacenamiento en memoria de los pedidos con `DefaultTableModel`.
- Tabla dinámica que muestra todos los pedidos confirmados.
- Cancelar pedido seleccionando una fila y pulsando "Cancelar Pedido".
- Exportar pedidos mediante `System.out.println`.
