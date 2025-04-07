package food_app.dao;

import food_app.db.DBUtil;
import food_app.models.Restaurant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDAO {
    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();
        String sql = "SELECT * FROM restaurant";
        try (Connection con = DBUtil.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setRestId(rs.getInt("rest_id"));
                restaurant.setRestName(rs.getString("rest_name"));
                restaurant.setCuisineType(rs.getString("cuisine_type"));
                restaurant.setRestAvail(rs.getInt("rest_avail"));
                restaurant.setClosingTime(rs.getInt("closing_time"));
                restaurant.setOpeningTime(rs.getInt("opening_time"));
                restaurants.add(restaurant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurants;
    }

    public Restaurant getRestaurantById(int restId) {
        String sql = "SELECT * FROM restaurants WHERE rest_id = ?";
        Restaurant restaurant = null;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, restId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    restaurant = new Restaurant();
                    restaurant.setRestId(rs.getInt("rest_id"));
                    restaurant.setRestName(rs.getString("rest_name"));
                    restaurant.setCuisineType(rs.getString("cuisine_type"));
                    restaurant.setRestAvail(rs.getInt("rest_avail"));
                    restaurant.setOpeningTime(rs.getString("opening_time"));
                    restaurant.setClosingTime(rs.getString("closing_time"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurant;
    }
}