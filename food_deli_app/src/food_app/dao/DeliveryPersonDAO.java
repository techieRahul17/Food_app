package food_app.dao;

import food_app.db.DBUtil;
import food_app.models.DeliveryPerson;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryPersonDAO {
    public List<DeliveryPerson> getAllDeliveryPeople() {
        List<DeliveryPerson> deliveryPeople = new ArrayList<>();
        String sql = "SELECT * FROM Delivery_Person";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                DeliveryPerson deliveryPerson = new DeliveryPerson();
                deliveryPerson.setDeliveryId(rs.getInt("Delivery_id"));
                deliveryPerson.setVehicleType(rs.getString("Vehicle_Type"));
                deliveryPerson.setDelGender(rs.getString("del_gender"));
                deliveryPerson.setJoiningDate(rs.getDate("joining_date"));
                deliveryPerson.setAvail(rs.getString("Avail"));
                deliveryPerson.setPhone(rs.getLong("phone"));
                deliveryPerson.setfName(rs.getString("f_name"));
                deliveryPerson.setlName(rs.getString("l_name"));
                deliveryPerson.setDob(rs.getDate("DOB"));
                deliveryPerson.setAddress(rs.getString("address"));

                deliveryPeople.add(deliveryPerson);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deliveryPeople;
    }

    public DeliveryPerson getDeliveryPersonById(int deliveryId) {
        DeliveryPerson deliveryPerson = null;
        String sql = "SELECT * FROM Delivery_Person WHERE Delivery_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, deliveryId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    deliveryPerson = new DeliveryPerson();
                    deliveryPerson.setDeliveryId(rs.getInt("Delivery_id"));
                    deliveryPerson.setVehicleType(rs.getString("Vehicle_Type"));
                    deliveryPerson.setDelGender(rs.getString("del_gender"));
                    deliveryPerson.setJoiningDate(rs.getDate("joining_date"));
                    deliveryPerson.setAvail(rs.getString("Avail"));
                    deliveryPerson.setPhone(rs.getLong("phone"));
                    deliveryPerson.setfName(rs.getString("f_name"));
                    deliveryPerson.setlName(rs.getString("l_name"));
                    deliveryPerson.setDob(rs.getDate("DOB"));
                    deliveryPerson.setAddress(rs.getString("address"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deliveryPerson;
    }
}