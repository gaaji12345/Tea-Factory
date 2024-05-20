package model;

import db.DBConnection;
import dto.Paymentdto;
import util.CrudUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentModel {
    public static boolean add(Paymentdto payment) throws SQLException {
        String sql = "INSERT INTO payment(paymentId , userId  , userName  , d_Id  , supId  ,checkInDate  ,checkOutDate  ,ordersAmount    )" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, payment.getPaymentId());
        pstm.setString(2, payment.getUserId());
        pstm.setString(3, payment.getUserName());
        pstm.setString(4, payment.getDId());
        pstm.setString(5, payment.getSupId());
        pstm.setString(6, payment.getCDate());
        pstm.setString(7, payment.getDDate());
        pstm.setDouble(8, payment.getOrderAm());

        int affectedRows = pstm.executeUpdate();

      return affectedRows > 0;

          //  return CrudUtil.execute("INSERT INTO payment(paymentId , userId  , userName  , d_Id  , supId  ,checkInDate  ,checkOutDate  ,ordersAmount  , totalPrice  ) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)", entity.getPaymentId(), entity.getUserId(), entity.getUserName(), entity.getDId(), entity.getSupId(), entity.getCDate(), entity.getDDate(), entity.getOrderAm(), entity.getTotal());

    }
    public static boolean update(Paymentdto entity) throws SQLException {
        return CrudUtil.execute("UPDATE payment SET userId = ?, userName = ?, d_Id = ?, supId = ?, checkInDate = ?, checkOutDate = ?, ordersAmount  = ? WHERE paymentId = ?", entity.getUserId(), entity.getUserName(), entity.getDId(), entity.getSupId(), entity.getCDate(), entity.getDDate(), entity.getOrderAm(), entity.getPaymentId());
    }

    public static boolean delete(String id) throws SQLException {
        return CrudUtil.execute("DELETE FROM payment WHERE paymentId = ?", id);
    }

    public static ArrayList<Paymentdto> getAll() throws SQLException {
        ArrayList<Paymentdto> allPaymentDetails = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM payment");
        while (rst.next()) {
            allPaymentDetails.add(new Paymentdto(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6), rst.getString(7), rst.getDouble(8)));
        }
        return allPaymentDetails;    }



}
