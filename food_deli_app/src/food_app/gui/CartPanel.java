package food_app.gui;

import food_app.dao.OrderDAO;
import food_app.dao.PaymentDAO;
import food_app.models.Cart;
import food_app.models.Item;
import food_app.models.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;
import java.util.Random;

public class CartPanel extends JFrame {
    private int customerId;

    public CartPanel(Cart cart, int customerId) {
        this.customerId = customerId;

        setTitle("Cart");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        String[] columnNames = {"Item Name", "Price", "Quantity", "Total"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable cartTable = new JTable(tableModel);

        for (Map.Entry<Item, Integer> entry : cart.getItems().entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();
            double total = item.getCurrentPrice() * quantity;
            Object[] row = {item.getItemName(), item.getCurrentPrice(), quantity, total};
            tableModel.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(cartTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JLabel totalAmountLabel = new JLabel("Total Amount: " + cart.getTotalAmount());
        panel.add(totalAmountLabel, BorderLayout.NORTH);

        JPanel paymentPanel = new JPanel(new GridLayout(5, 1));
        JRadioButton codButton = new JRadioButton("Cash on Delivery");
        JRadioButton ccButton = new JRadioButton("Credit Card");
        JRadioButton upiButton = new JRadioButton("UPI");
        ButtonGroup paymentGroup = new ButtonGroup();
        paymentGroup.add(codButton);
        paymentGroup.add(ccButton);
        paymentGroup.add(upiButton);

        JTextField amountField = new JTextField();
        amountField.setEnabled(false);
        ccButton.addActionListener(e -> amountField.setEnabled(true));
        upiButton.addActionListener(e -> amountField.setEnabled(true));
        codButton.addActionListener(e -> amountField.setEnabled(false));

        paymentPanel.add(codButton);
        paymentPanel.add(ccButton);
        paymentPanel.add(upiButton);
        paymentPanel.add(new JLabel("Payment Amount:"));
        paymentPanel.add(amountField);

        panel.add(paymentPanel, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel();
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            new MainDashboard(cart, customerId).setVisible(true);
            dispose();
        });
        buttonPanel.add(backButton);

        JButton placeOrderButton = new JButton("Place Order");
        placeOrderButton.addActionListener(e -> {
            if (codButton.isSelected() || ccButton.isSelected() || upiButton.isSelected()) {
                double amount = codButton.isSelected() ? cart.getTotalAmount() : Double.parseDouble(amountField.getText());
                String payType = codButton.isSelected() ? "COD" : (ccButton.isSelected() ? "CC" : "UPI");
                String payStatus = codButton.isSelected() ? "N" : "P";
                placeOrder(cart, amount, payType, payStatus);
                JOptionPane.showMessageDialog(this, "Order placed successfully!");
                cart.getItems().clear(); // Clear the cart after placing the order
                new MainDashboard(cart, customerId).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a payment option.");
            }
        });
        buttonPanel.add(placeOrderButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void placeOrder(Cart cart, double amount, String payType, String payStatus) {
        // Generate a random order ID for simplicity
        int orderId = new Random().nextInt(100000);

        // Create the payment entry
        PaymentDAO paymentDAO = new PaymentDAO();
        int paymentId = paymentDAO.createPayment(amount, payType, payStatus);

        if (paymentId == -1) {
            JOptionPane.showMessageDialog(this, "Failed to create payment.");
            return;
        }

        // Create the order object
        Order order = new Order();
        order.setOrderId(orderId);
        order.setRestaurantId(1); // Example restaurant ID, replace with actual value
        order.setCustomerId(customerId); // Use the logged-in customer's ID
        order.setDeliveryPersonId(null); // Set to null
        order.setAddress("Example Address"); // Replace with actual address
        order.setOrderStatus("Placed");
        order.setPaymentId(paymentId); // Use the generated payment ID
        order.setItems(cart.getItems());

        // Place the order using OrderDAO
        OrderDAO orderDAO = new OrderDAO();
        orderDAO.placeOrder(order);
    }
}