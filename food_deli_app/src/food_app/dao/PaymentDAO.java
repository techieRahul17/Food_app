package food_app.dao;

import food_app.db.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentDAO {
    public int createPayment(double amount, String payType, String payStatus) {
        String sql = "INSERT INTO payment (payment_id, amount, pay_date, pay_type, pay_status) VALUES (payment_seq.NEXTVAL, ?, SYSDATE, ?, ?)";
        String getIdSql = "SELECT payment_seq.CURRVAL FROM dual";
        
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             PreparedStatement getIdPs = con.prepareStatement(getIdSql)) {
            
            ps.setDouble(1, amount);
            ps.setString(2, payType);
            ps.setString(3, payStatus);
            ps.executeUpdate();
            
            try (ResultSet rs = getIdPs.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Indicate failure
    }
}