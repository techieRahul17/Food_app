package food_app.dao;

import food_app.db.DBUtil;
import food_app.models.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO Customer (cust_id, cust_name, b_date, gender, password, phone, email, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customer.getCustId());
            ps.setString(2, customer.getCustName());
            ps.setString(3, customer.getBirthDate());
            ps.setString(4, customer.getGender());
            ps.setString(5, customer.getPassword());
            ps.setString(6, customer.getPhone());
            ps.setString(7, customer.getEmail());
            ps.setString(8, customer.getAddress());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customer";
        try (Connection con = DBUtil.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustId(rs.getInt("cust_id"));
                customer.setCustName(rs.getString("cust_name"));
                customer.setBirthDate(rs.getString("b_date"));
                customer.setGender(rs.getString("gender"));
                customer.setPassword(rs.getString("password"));
                customer.setPhone(rs.getString("phone"));
                customer.setEmail(rs.getString("email"));
                customer.setAddress(rs.getString("address"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
}