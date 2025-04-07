package food_app.gui;

import food_app.models.Cart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainDashboard extends JFrame {
    private Cart cart;
    private int cust_id;

    public MainDashboard(Cart cart, int cust_id) {
        this.cart = cart;
        this.cust_id = cust_id;

        setTitle("Main Dashboard");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1));
        add(panel);

        JButton browseRestaurantsButton = new JButton("Browse Restaurants");
        browseRestaurantsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RestaurantPanel(cart, cust_id).setVisible(true);
                dispose();
            }
        });
        panel.add(browseRestaurantsButton);

        JButton orderHistoryButton = new JButton("Order History");
        orderHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrderPanel(cart, cust_id).setVisible(true);
                dispose();
            }
        });
        panel.add(orderHistoryButton);

        JButton viewCartButton = new JButton("View Cart");
        viewCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CartPanel(cart, cust_id).setVisible(true);
                dispose();
            }
        });
        panel.add(viewCartButton);

        JButton adminDashboardButton = new JButton("Admin Dashboard");
        adminDashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminPanel().setVisible(true);
                dispose();
            }
        });
        panel.add(adminDashboardButton);
    }
}