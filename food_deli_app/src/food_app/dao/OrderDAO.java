package food_app.dao;

import food_app.db.DBUtil;
import food_app.models.Item;
import food_app.models.Order;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDAO {
    public void placeOrder(Order order) {
        String orderSql = "INSERT INTO Orders (order_id, order_date, rest_id, cust_id, delivery_id, address, order_status, payment) VALUES (?, SYSDATE, ?, ?, ?, ?, ?, ?)";
        String orderItemSql = "INSERT INTO OrderItem (order_id, item_id, quantity) VALUES (?, ?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement orderPs = con.prepareStatement(orderSql);
             PreparedStatement orderItemPs = con.prepareStatement(orderItemSql)) {
            
            // Save order details
            orderPs.setInt(1, order.getOrderId());
            orderPs.setInt(2, order.getRestaurantId());
            orderPs.setInt(3, order.getCustomerId());
            if (order.getDeliveryPersonId() != null) {
                orderPs.setInt(4, order.getDeliveryPersonId());
            } else {
                orderPs.setNull(4, java.sql.Types.INTEGER);
            }
            orderPs.setString(5, order.getAddress());
            orderPs.setString(6, order.getOrderStatus());
            orderPs.setInt(7, order.getPaymentId());
            orderPs.executeUpdate();

            // Save order items
            for (Map.Entry<Item, Integer> entry : order.getItems().entrySet()) {
                Item item = entry.getKey();
                int quantity = entry.getValue();

                orderItemPs.setInt(1, order.getOrderId());
                orderItemPs.setInt(2, item.getItemId());
                orderItemPs.setInt(3, quantity);
                orderItemPs.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Order> getOrdersByRestaurantId(int restId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE rest_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, restId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setOrderId(rs.getInt("order_id"));
                    order.setRestId(rs.getInt("rest_id"));
                    order.setCustId(rs.getInt("cust_id"));
                    order.setDeliveryId(rs.getInt("delivery_id"));
                    order.setOrderStatus(rs.getString("order_status"));
                    order.setOrderTime(rs.getTimestamp("order_time"));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public void updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE orders SET order_status = ? WHERE order_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Order> getOrdersByCustomerId(int customerId) {
        List<Order> orders = new ArrayList<>();
        String orderSql = "SELECT * FROM Orders WHERE cust_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(orderSql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setOrderId(rs.getInt("order_id"));
                    order.setOrderDate(rs.getDate("order_date"));
                    order.setRestaurantId(rs.getInt("rest_id"));
                    order.setCustomerId(rs.getInt("cust_id"));
                    order.setDeliveryPersonId(rs.getInt("delivery_id"));
                    order.setAddress(rs.getString("address"));
                    order.setOrderStatus(rs.getString("order_status"));
                    order.setPaymentId(rs.getInt("payment"));

                    // Fetch items for the order
                    String orderItemSql = "SELECT * FROM OrderItem WHERE order_id = ?";
                    try (PreparedStatement orderItemPs = con.prepareStatement(orderItemSql)) {
                        orderItemPs.setInt(1, order.getOrderId());
                        try (ResultSet orderItemRs = orderItemPs.executeQuery()) {
                            while (orderItemRs.next()) {
                                // Create Item object and add it to order
                                Item item = new Item();
                                item.setItemId(orderItemRs.getInt("item_id"));
                                int quantity = orderItemRs.getInt("quantity");
                                order.addItem(item, quantity);
                            }
                        }
                    }

                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}