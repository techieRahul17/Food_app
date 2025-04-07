package food_app.gui;

import food_app.dao.ItemDAO;
import food_app.models.Cart;
import food_app.models.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ItemPanel extends JFrame {
    private Cart cart;
    private int customerId;

    public ItemPanel(int restaurantId, Cart cart,int customerId) {
        this.cart = cart;
        this.customerId = customerId;

        setTitle("Items");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JPanel itemsPanel = new JPanel(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        ItemDAO itemDAO = new ItemDAO();
        List<Item> items = itemDAO.getItemsByRestaurantId(restaurantId);

        for (Item item : items) {
            JPanel itemPanel = new JPanel(new FlowLayout());
            JLabel itemLabel = new JLabel(item.getItemName() + " - " + item.getCurrentPrice() + " - " + item.getDescription());
            JTextField quantityField = new JTextField(5);
            JButton addButton = new JButton("Add to Cart");

            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int quantity;
                    try {
                        quantity = Integer.parseInt(quantityField.getText());
                        if (quantity > 0) {
                            cart.addItem(item, quantity);
                            JOptionPane.showMessageDialog(ItemPanel.this, "Item added to cart!");
                        } else {
                            JOptionPane.showMessageDialog(ItemPanel.this, "Quantity must be greater than 0");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(ItemPanel.this, "Invalid quantity");
                    }
                }
            });

            itemPanel.add(itemLabel);
            itemPanel.add(quantityField);
            itemPanel.add(addButton);
            itemsPanel.add(itemPanel);
        }

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            new RestaurantPanel(cart,customerId).setVisible(true);
            dispose();
        });
        panel.add(backButton, BorderLayout.SOUTH);
    }
}