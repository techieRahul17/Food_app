package food_app.gui;

import food_app.dao.OrderDAO;
import food_app.models.Cart;
import food_app.models.Order;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class OrderPanel extends JFrame {
    private Cart cart;
    private int customerId;

    public OrderPanel(Cart cart, int customerId) {
        this.cart = cart;
        this.customerId = customerId;

        setTitle("Order History");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JTextArea orderListArea = new JTextArea();
        orderListArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderListArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        OrderDAO orderDAO = new OrderDAO();
        List<Order> orders = orderDAO.getOrdersByCustomerId(customerId);
        StringBuilder orderListBuilder = new StringBuilder();
        for (Order order : orders) {
            orderListBuilder.append("Order ID: ")
                            .append(order.getOrderId())
                            .append(", Status: ")
                            .append(order.getOrderStatus())
                            .append("\n");
        }
        orderListArea.setText(orderListBuilder.toString());

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            new MainDashboard(cart, customerId).setVisible(true);
            dispose();
        });
        panel.add(backButton, BorderLayout.SOUTH);
    }
}