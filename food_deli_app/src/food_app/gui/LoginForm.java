package food_app.gui;

import food_app.dao.CustomerDAO;
import food_app.models.Customer;

import javax.swing.*;
import java.awt.*;
import food_app.models.Cart;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginForm() {
        setTitle("Login");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(panel);

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.X_AXIS));
        usernamePanel.add(new JLabel("Username:"));
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
                Integer _id = Integer.parseInt(username);
                CustomerDAO customerDAO = new CustomerDAO();
                List<Customer> customers = customerDAO.getAllCustomers();
                for (Customer customer : customers) {
                    if (customer.getCustId() == _id && customer.getPassword().equals(password)) {
                        JOptionPane.showMessageDialog(LoginForm.this, "Login Successful");
                        Cart cart = new Cart();
                        new MainDashboard(cart, _id).setVisible(true);
                        dispose();
                        return;
                    }
                }
                JOptionPane.showMessageDialog(LoginForm.this, "Invalid Username or Password");
            }
        });
        panel.add(loginButton);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton registerButton = new JButton("Register");
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegistrationForm().setVisible(true);
                dispose();
            }
        });
        panel.add(registerButton);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton restaurantLoginButton = new JButton("Restaurant Login");
        restaurantLoginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restaurantLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RestaurantLoginForm().setVisible(true);
                dispose();
            }
        });
        panel.add(restaurantLoginButton);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton deliveryLoginButton = new JButton("Delivery Person Login");
        deliveryLoginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deliveryLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeliveryLoginForm().setVisible(true);
                dispose();
            }
        });
        panel.add(deliveryLoginButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }
}