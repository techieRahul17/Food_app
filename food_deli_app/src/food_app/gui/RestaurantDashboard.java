package food_app.gui;

import food_app.dao.MenuItemDAO;
import food_app.dao.OrderDAO;
import food_app.dao.RestaurantDAO;
import food_app.models.MenuItem;
import food_app.models.Order;
import food_app.models.Restaurant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RestaurantDashboard extends JFrame {
    private int restId;
    private RestaurantDAO restaurantDAO;
    private Restaurant restaurant;
    private MenuItemDAO menuItemDAO;
    private OrderDAO orderDAO;
    private JTable menuTable;
    private DefaultTableModel menuTableModel;
    private JTable orderTable;
    private DefaultTableModel orderTableModel;

    public RestaurantDashboard(int restId) {
        this.restId = restId;
        this.restaurantDAO = new RestaurantDAO();
        this.menuItemDAO = new MenuItemDAO();
        this.orderDAO = new OrderDAO();
        this.restaurant = restaurantDAO.getRestaurantById(restId);

        setTitle("Restaurant Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Add components and layout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(panel);

        JLabel titleLabel = new JLabel("Restaurant Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Display restaurant details
        JLabel nameLabel = new JLabel("Name: " + restaurant.getRestName());
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(nameLabel);

        JLabel cuisineLabel = new JLabel("Cuisine: " + restaurant.getCuisineType());
        cuisineLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        cuisineLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(cuisineLabel);

        JLabel availabilityLabel = new JLabel("Availability: " + (restaurant.getRestAvail() == 1 ? "Open" : "Closed"));
        availabilityLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        availabilityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(availabilityLabel);

        JLabel hoursLabel = new JLabel("Hours: " + restaurant.getOpeningTime() + " - " + restaurant.getClosingTime());
        hoursLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        hoursLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(hoursLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Menu Management Section
        JLabel menuLabel = new JLabel("Menu Management");
        menuLabel.setFont(new Font("Arial", Font.BOLD, 20));
        menuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(menuLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        menuTableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Price", "Description"}, 0);
        menuTable = new JTable(menuTableModel);
        JScrollPane menuScrollPane = new JScrollPane(menuTable);
        panel.add(menuScrollPane);

        JPanel menuButtonPanel = new JPanel();
        JButton addMenuItemButton = new JButton("Add");
        addMenuItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddMenuItemDialog(RestaurantDashboard.this, restId).setVisible(true);
                refreshMenuTable();
            }
        });
        menuButtonPanel.add(addMenuItemButton);

        JButton updateMenuItemButton = new JButton("Update");
        updateMenuItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = menuTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int menuItemId = (int) menuTableModel.getValueAt(selectedRow, 0);
                    new UpdateMenuItemDialog(RestaurantDashboard.this, restId, menuItemId).setVisible(true);
                    refreshMenuTable();
                } else {
                    JOptionPane.showMessageDialog(RestaurantDashboard.this, "Please select a menu item to update.");
                }
            }
        });
        menuButtonPanel.add(updateMenuItemButton);

        JButton deleteMenuItemButton = new JButton("Delete");
        deleteMenuItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = menuTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int menuItemId = (int) menuTableModel.getValueAt(selectedRow, 0);
                    menuItemDAO.deleteMenuItem(menuItemId);
                    refreshMenuTable();
                } else {
                    JOptionPane.showMessageDialog(RestaurantDashboard.this, "Please select a menu item to delete.");
                }
            }
        });
        menuButtonPanel.add(deleteMenuItemButton);

        panel.add(menuButtonPanel);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Order Tracking Section
        JLabel orderLabel = new JLabel("Order Tracking");
        orderLabel.setFont(new Font("Arial", Font.BOLD, 20));
        orderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(orderLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        orderTableModel = new DefaultTableModel(new Object[]{"Order ID", "Customer ID", "Delivery ID", "Status", "Order Time"}, 0);
        orderTable = new JTable(orderTableModel);
        JScrollPane orderScrollPane = new JScrollPane(orderTable);
        panel.add(orderScrollPane);

        JPanel orderButtonPanel = new JPanel();
        JButton updateOrderStatusButton = new JButton("Update Status");
        updateOrderStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = orderTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int orderId = (int) orderTableModel.getValueAt(selectedRow, 0);
                    String[] statuses = {"Pending", "In Progress", "Completed", "Cancelled"};
                    String status = (String) JOptionPane.showInputDialog(
                            RestaurantDashboard.this,
                            "Select new status",
                            "Update Order Status",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            statuses,
                            statuses[0]
                    );
                    if (status != null) {
                        orderDAO.updateOrderStatus(orderId, status);
                        refreshOrderTable();
                    }
                } else {
                    JOptionPane.showMessageDialog(RestaurantDashboard.this, "Please select an order to update.");
                }
            }
        });
        orderButtonPanel.add(updateOrderStatusButton);

        panel.add(orderButtonPanel);

        // Initialize tables
        refreshMenuTable();
        refreshOrderTable();
    }

    // Method to refresh the menu table
    private void refreshMenuTable() {
        menuTableModel.setRowCount(0);
        List<MenuItem> menuItems = menuItemDAO.getMenuItemsByRestaurantId(restId);
        for (MenuItem menuItem : menuItems) {
            menuTableModel.addRow(new Object[]{menuItem.getId(), menuItem.getName(), menuItem.getPrice(), menuItem.getDescription()});
        }
    }

    // Method to refresh the order table
    private void refreshOrderTable() {
        orderTableModel.setRowCount(0);
        List<Order> orders = orderDAO.getOrdersByRestaurantId(restId);
        for (Order order : orders) {
            orderTableModel.addRow(new Object[]{
                    order.getOrderId(),
                    order.getCustomerId(),
                    order.getDeliveryPersonId(),
                    order.getOrderStatus(),
                    order.getOrderDate()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RestaurantDashboard(1).setVisible(true);
            }
        });
    }
}