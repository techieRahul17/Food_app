package food_app.gui;

import food_app.dao.MenuItemDAO;
import food_app.models.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateMenuItemDialog extends JDialog {
    private JTextField nameField;
    private JTextField priceField;
    private JTextArea descriptionArea;
    private MenuItemDAO menuItemDAO;
    private int menuItemId;

    public UpdateMenuItemDialog(JFrame parent, int restId, int menuItemId) {
        super(parent, "Update Menu Item", true);
        this.menuItemId = menuItemId;
        this.menuItemDAO = new MenuItemDAO();

        MenuItem menuItem = menuItemDAO.getMenuItemById(menuItemId);

        setSize(400, 300);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(panel, BorderLayout.CENTER);

        panel.add(new JLabel("Name:"));
        nameField = new JTextField(menuItem.getName(), 15);
        panel.add(nameField);

        panel.add(new JLabel("Price:"));
        priceField = new JTextField(String.valueOf(menuItem.getPrice()), 15);
        panel.add(priceField);

        panel.add(new JLabel("Description:"));
        descriptionArea = new JTextArea(menuItem.getDescription(), 5, 15);
        panel.add(new JScrollPane(descriptionArea));

        JPanel buttonPanel = new JPanel();
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                String description = descriptionArea.getText();

                menuItem.setName(name);
                menuItem.setPrice(price);
                menuItem.setDescription(description);

                menuItemDAO.updateMenuItem(menuItem);
                dispose();
            }
        });
        buttonPanel.add(updateButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}