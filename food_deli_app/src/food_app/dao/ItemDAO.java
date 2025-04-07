package food_app.dao;

import food_app.db.DBUtil;
import food_app.models.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    public List<Item> getItemsByRestaurantId(int restaurantId) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM item WHERE restaurant_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, restaurantId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item();
                    item.setItemId(rs.getInt("item_id"));
                    item.setItemName(rs.getString("item_name"));
                    item.setAvail(rs.getString("avail"));
                    item.setCurrentPrice(rs.getDouble("current_price"));
                    item.setDescription(rs.getString("description"));
                    item.setRestaurantId(rs.getInt("restaurant_id"));
                    item.setOfferPercentage(rs.getDouble("offer_percentage"));
                    item.setOfferAvailability(rs.getString("offer_availability"));
                    items.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}