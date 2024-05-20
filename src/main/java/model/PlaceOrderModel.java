package model;

import db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceOrderModel {

    public static Double getOrderAm(String code) throws SQLException {

        String sql = "SELECT orderId FROM teaOrders WHERE d_Id = ?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, code);
        ResultSet rs = pstm.executeQuery();


        ArrayList<String> orderIds = new ArrayList<>();
        while (rs.next()) {
            String orderId = rs.getString("orderId");
            orderIds.add(orderId);
        }

        double totalAmount = 0.0;
        String sql2 = "SELECT amount FROM teaOrderDetail WHERE orderId = ?";
        PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement(sql2);
        for (String orderId : orderIds) {
            pst.setString(1, orderId);
            ResultSet rst = pst.executeQuery();
            double orderAmount = 0.0;
            while (rst.next()) {
                orderAmount += rst.getDouble("amount");
            }

            totalAmount += orderAmount;
        }
        return totalAmount;
    }
}
