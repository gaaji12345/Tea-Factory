package model;

import db.DBConnection;
import dto.OrderDetails;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public static boolean save(String oId, LocalDate now, String dId) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO teaOrders(orderId, date , d_Id ) VALUES(?, ?, ?)";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, oId);
        pstm.setDate(2, Date.valueOf(now));
        pstm.setString(3, dId);

        return pstm.executeUpdate() > 0;
    }

    public static boolean saveOrderDetails(List<OrderDetails> cartDTOList) throws SQLException {
        for(OrderDetails orderDetails : cartDTOList) {
            if(!updateQty(orderDetails)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(OrderDetails orderDetails) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO teaOrderDetail(orderId , teaId   , qty   ,amount  ,date ) VALUES(?, ?, ?, ?, ?)";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, orderDetails.getOrderID());
        pstm.setString(2, orderDetails.getTeaID());
        pstm.setInt(3, orderDetails.getQty());
        pstm.setDouble(4, orderDetails.getTotal());
        pstm.setDate(5, Date.valueOf(orderDetails.getDate()));

        return pstm.executeUpdate() > 0;
    }


}
