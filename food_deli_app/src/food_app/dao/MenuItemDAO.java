package food_app.dao;

import food_app.db.DBUtil;
import food_app.models.MenuItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAO {
    public List<MenuItem> getMenuItemsByRestaurantId(int restId) {
        List<MenuItem> menuItems = new ArrayList<>();
        String sql = "SELECT * FROM menu_items WHERE rest_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, restId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MenuItem menuItem = new MenuItem();
                    menuItem.setId(rs.getInt("id"));
                    menuItem.setRestId(rs.getInt("rest_id"));
                    menuItem.setName(rs.getString("name"));
                    menuItem.setPrice(rs.getDouble("price"));
                    menuItem.setDescription(rs.getString("description"));
                    menuItems.add(menuItem);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuItems;
    }

    public void addMenuItem(MenuItem menuItem) {
        String sql = "INSERT INTO menu_items (rest_id, name, price, description) VALUES (?, ?, ?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, menuItem.getRestId());
            ps.setString(2, menuItem.getName());
            ps.setDouble(3, menuItem.getPrice());
            ps.setString(4, menuItem.getDescription());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMenuItem(MenuItem menuItem) {
        String sql = "UPDATE menu_items SET name = ?, price = ?, description = ? WHERE id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, menuItem.getName());
            ps.setDouble(2, menuItem.getPrice());
            ps.setString(3, menuItem.getDescription());
            ps.setInt(4, menuItem.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMenuItem(int menuItemId) {
        String sql = "DELETE FROM menu_items WHERE id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, menuItemId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}