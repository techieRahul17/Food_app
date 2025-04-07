package food_app.gui;

import food_app.dao.RestaurantDAO;
import food_app.models.Cart;
import food_app.models.Restaurant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class RestaurantPanel extends JFrame {
    private Cart cart;
    private int customerId;

    public RestaurantPanel(Cart cart, int customerId) {
        this.cart = cart;
        this.customerId = customerId;

        setTitle("Browse Restaurants");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JList<String> restaurantList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(restaurantList);
        panel.add(scrollPane, BorderLayout.CENTER);

        RestaurantDAO restaurantDAO = new RestaurantDAO();
        List<Restaurant> restaurants = restaurantDAO.getAllRestaurants();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Restaurant restaurant : restaurants) {
            listModel.addElement(restaurant.getRestName() + " - " + restaurant.getCuisineType());
        }
        restaurantList.setModel(listModel);

        restaurantList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = restaurantList.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        Restaurant selectedRestaurant = restaurants.get(index);
                        new ItemPanel(selectedRestaurant.getRestId(), cart, customerId).setVisible(true);
                        dispose();
                    }
                }
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            new MainDashboard(cart, customerId).setVisible(true);
            dispose();
        });
        panel.add(backButton, BorderLayout.SOUTH);
    }
}