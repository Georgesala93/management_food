package management_food;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class FoodOrderApp extends JFrame {
    private final JTextField txtDishName;
    private final JComboBox<String> cbCategory;
    private final JTextField txtQuantity;
    private final JRadioButton rbDelivery;
    private final JRadioButton rbPickup;
    private final JTextField txtAddress;
    private final DefaultTableModel orderTableModel;
    private final JTable orderTable;
    private final List<Order> orders;
    private int nextOrderId = 1;
    private final Border defaultTextFieldBorder;
    private final Border defaultComboBorder;

    public FoodOrderApp() {
        super("Gestión de Pedidos de Alimentos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        orders = new ArrayList<>();

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Formulario de Pedido"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblDishName = new JLabel("Nombre del Plato:");
        txtDishName = new JTextField(20);
        defaultTextFieldBorder = txtDishName.getBorder();
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(lblDishName, gbc);
        gbc.gridx = 1;
        formPanel.add(txtDishName, gbc);

        JLabel lblCategory = new JLabel("Categoría del Plato:");
        cbCategory = new JComboBox<>(new String[]{"Seleccione...", "Entrante", "Plato Principal", "Postre"});
        defaultComboBorder = cbCategory.getBorder();
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(lblCategory, gbc);
        gbc.gridx = 1;
        formPanel.add(cbCategory, gbc);

        JLabel lblQuantity = new JLabel("Cantidad de Platos:");
        txtQuantity = new JTextField(8);
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(lblQuantity, gbc);
        gbc.gridx = 1;
        formPanel.add(txtQuantity, gbc);

        JLabel lblDeliveryMethod = new JLabel("Método de Entrega:");
        rbDelivery = new JRadioButton("Domicilio");
        rbPickup = new JRadioButton("Recogida");
        ButtonGroup deliveryGroup = new ButtonGroup();
        deliveryGroup.add(rbDelivery);
        deliveryGroup.add(rbPickup);
        JPanel deliveryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        deliveryPanel.add(rbDelivery);
        deliveryPanel.add(rbPickup);
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(lblDeliveryMethod, gbc);
        gbc.gridx = 1;
        formPanel.add(deliveryPanel, gbc);

        JLabel lblAddress = new JLabel("Dirección de Entrega:");
        txtAddress = new JTextField(20);
        txtAddress.setEnabled(false);
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(lblAddress, gbc);
        gbc.gridx = 1;
        formPanel.add(txtAddress, gbc);

        JButton btnClear = new JButton("Borrar");
        JButton btnConfirm = new JButton("Confirmar");
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        actionPanel.add(btnClear);
        actionPanel.add(btnConfirm);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        formPanel.add(actionPanel, gbc);

        rbDelivery.addActionListener(e -> txtAddress.setEnabled(true));
        rbPickup.addActionListener(e -> {
            txtAddress.setEnabled(false);
            txtAddress.setText("");
        });

        btnClear.addActionListener(e -> clearForm());
        btnConfirm.addActionListener(e -> confirmOrder());

        orderTableModel = new DefaultTableModel(new String[]{"N° Pedido", "Nombre del Plato", "Categoría", "Cantidad", "Método de Entrega", "Dirección"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        orderTable = new JTable(orderTableModel);
        orderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScrollPane = new JScrollPane(orderTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Pedidos Confirmados"));

        JButton btnCancelOrder = new JButton("Cancelar Pedido");
        JButton btnExport = new JButton("Exportar Pedidos");
        JPanel tableButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        tableButtonPanel.add(btnCancelOrder);
        tableButtonPanel.add(btnExport);

        btnCancelOrder.addActionListener(e -> cancelSelectedOrder());
        btnExport.addActionListener(e -> exportOrders());

        JPanel tablePanel = new JPanel(new BorderLayout(10, 10));
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);
        tablePanel.add(tableButtonPanel, BorderLayout.SOUTH);

        add(formPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);

        setSize(780, 520);
        setLocationRelativeTo(null);
    }

    private void confirmOrder() {
        resetFieldBorders();
        String dishName = txtDishName.getText().trim();
        String category = (String) cbCategory.getSelectedItem();
        String quantityText = txtQuantity.getText().trim();
        boolean deliverySelected = rbDelivery.isSelected();
        boolean pickupSelected = rbPickup.isSelected();
        String address = txtAddress.getText().trim();

        boolean valid = true;

        if (dishName.isEmpty() || !dishName.matches("[\\p{L}0-9 ]+")) {
            highlightComponent(txtDishName);
            valid = false;
        }
        if (category == null || category.equals("Seleccione...")) {
            highlightComponent(cbCategory);
            valid = false;
        }

        int quantity = 0;
        if (quantityText.isEmpty()) {
            highlightComponent(txtQuantity);
            valid = false;
        } else {
            try {
                quantity = Integer.parseInt(quantityText);
                if (quantity <= 0) {
                    highlightComponent(txtQuantity);
                    valid = false;
                }
            } catch (NumberFormatException e) {
                highlightComponent(txtQuantity);
                valid = false;
            }
        }

        if (!deliverySelected && !pickupSelected) {
            highlightComponent(rbDelivery);
            highlightComponent(rbPickup);
            valid = false;
        }

        if (deliverySelected) {
            if (address.isEmpty()) {
                highlightComponent(txtAddress);
                valid = false;
            }
        }

        if (!valid) {
            JOptionPane.showMessageDialog(this, "Por favor, corrige los campos marcados en rojo.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String deliveryMethod = deliverySelected ? "Domicilio" : "Recogida";
        Order order = new Order(nextOrderId++, dishName, category, quantity, deliveryMethod, deliverySelected ? address : "");
        orders.add(order);
        orderTableModel.addRow(new Object[]{order.getId(), order.getDishName(), order.getCategory(), order.getQuantity(), order.getDeliveryMethod(), order.getAddress()});

        JOptionPane.showMessageDialog(this, "Pedido confirmado correctamente.", "Pedido Guardado", JOptionPane.INFORMATION_MESSAGE);
        clearForm();
    }

    private void clearForm() {
        txtDishName.setText("");
        cbCategory.setSelectedIndex(0);
        txtQuantity.setText("");
        rbDelivery.setSelected(false);
        rbPickup.setSelected(false);
        txtAddress.setText("");
        txtAddress.setEnabled(false);
        resetFieldBorders();
    }

    private void resetFieldBorders() {
        txtDishName.setBorder(defaultTextFieldBorder);
        txtQuantity.setBorder(defaultTextFieldBorder);
        txtAddress.setBorder(defaultTextFieldBorder);
        cbCategory.setBorder(defaultComboBorder);
        rbDelivery.setBorder(null);
        rbPickup.setBorder(null);
    }

    private void highlightComponent(JComponent component) {
        component.setBorder(new LineBorder(Color.RED, 2));
    }

    private void cancelSelectedOrder() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un pedido para cancelar.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int modelRow = orderTable.convertRowIndexToModel(selectedRow);
        int orderId = (int) orderTableModel.getValueAt(modelRow, 0);
        orderTableModel.removeRow(modelRow);
        orders.removeIf(order -> order.getId() == orderId);
        JOptionPane.showMessageDialog(this, "Pedido cancelado correctamente.", "Pedido Cancelado", JOptionPane.INFORMATION_MESSAGE);
    }

    public void exportOrders() {
        if (orders.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay pedidos para exportar.", "Exportar Pedidos", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("[EXPORTACIÓN] No hay pedidos para exportar.");
            return;
        }
        System.out.println("===== EXPORTACIÓN DE PEDIDOS =====");
        for (Order order : orders) {
            System.out.println("Pedido #" + order.getId());
            System.out.println("  Plato: " + order.getDishName());
            System.out.println("  Categoría: " + order.getCategory());
            System.out.println("  Cantidad: " + order.getQuantity());
            System.out.println("  Método de Entrega: " + order.getDeliveryMethod());
            System.out.println("  Dirección: " + (order.getDeliveryMethod().equals("Domicilio") ? order.getAddress() : "N/A"));
            System.out.println("--------------------------------");
        }
        JOptionPane.showMessageDialog(this, "Pedidos exportados a la consola.", "Exportación", JOptionPane.INFORMATION_MESSAGE);
    }

    // Método público para añadir pedidos desde código (útil para pruebas automatizadas)
    public void addOrderFields(String dishName, String category, int quantity, boolean deliverySelected, String address) {
        String deliveryMethod = deliverySelected ? "Domicilio" : "Recogida";
        Order order = new Order(nextOrderId++, dishName, category, quantity, deliveryMethod, deliverySelected ? address : "");
        orders.add(order);
        orderTableModel.addRow(new Object[]{order.getId(), order.getDishName(), order.getCategory(), order.getQuantity(), order.getDeliveryMethod(), order.getAddress()});
    }
}
