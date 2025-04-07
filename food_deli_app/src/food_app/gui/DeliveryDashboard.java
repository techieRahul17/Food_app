package food_app.gui;

import javax.swing.*;

public class DeliveryDashboard extends JFrame {
    private int deliveryId;

    public DeliveryDashboard(int deliveryId) {
        this.deliveryId = deliveryId;

        setTitle("Delivery Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Add components and layout as needed for the delivery dashboard
    }
}