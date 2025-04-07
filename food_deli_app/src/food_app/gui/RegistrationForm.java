package food_app.gui;

import food_app.dao.CustomerDAO;
import food_app.models.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField addressField;

    public RegistrationForm() {
        setTitle("Register");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2));
        add(panel);

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        panel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        panel.add(phoneField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Address:"));
        addressField = new JTextField();
        panel.add(addressField);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Customer customer = new Customer();
                customer.setCustName(usernameField.getText());
                customer.setPassword(new String(passwordField.getPassword()));
                customer.setPhone(phoneField.getText());
                customer.setEmail(emailField.getText());
                customer.setAddress(addressField.getText());

                CustomerDAO customerDAO = new CustomerDAO();
                if (customerDAO.addCustomer(customer)) {
                    JOptionPane.showMessageDialog(RegistrationForm.this, "Registration Successful");
                    new LoginForm().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(RegistrationForm.this, "Registration Failed");
                }
            }
        });
        panel.add(registerButton);
    }
}