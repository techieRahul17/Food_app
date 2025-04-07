package food_app.gui;

import food_app.dao.RestaurantDAO;
import food_app.models.Restaurant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RestaurantLoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public RestaurantLoginForm() {
        setTitle("Restaurant Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(panel);

        JLabel titleLabel = new JLabel("Restaurant Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.X_AXIS));
        usernamePanel.add(new JLabel("Username (Restaurant ID):"));
        usernamePanel.add(Box.createRigidArea(new Dimension(10, 0)));
        usernameField = new JTextField(15);
        usernamePanel.add(usernameField);
        panel.add(usernamePanel);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
        passwordPanel.add(new JLabel("Password:"));
        passwordPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        passwordField = new JPasswordField(15);
        passwordPanel.add(passwordField);
        panel.add(passwordPanel);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton loginButton = new JButton("Login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                int restId = Integer.parseInt(username);  // Use primitive int type here
                if ("admin".equals(password)) {
                    RestaurantDAO restaurantDAO = new RestaurantDAO();
                    List<Restaurant> restaurants = restaurantDAO.getAllRestaurants();
                    for (Restaurant restaurant : restaurants) {
                        if (restaurant.getRestId() == restId) {
                            JOptionPane.showMessageDialog(RestaurantLoginForm.this, "Login Successful");
                            new RestaurantDashboard(restId).setVisible(true);
                            dispose();
                            return;
                        }
                    }
                }
                JOptionPane.showMessageDialog(RestaurantLoginForm.this, "Invalid Username or Password");
            }
        });
        panel.add(loginButton);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginForm().setVisible(true);
                dispose();
            }
        });
        panel.add(backButton);
    }
}