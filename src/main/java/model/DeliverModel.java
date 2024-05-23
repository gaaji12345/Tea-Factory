package model;

import db.DBConnection;
import dto.Devliverydto;
import util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeliverModel {
    public static boolean delete(String id) throws SQLException {
        return CrudUtil.execute("DELETE FROM deliver WHERE d_Id = ?",id);
    }

    public static boolean update(String d_Id, String userId, String supId, String deliverDate) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "UPDATE deliver SET userId = ?, supId = ?, deliverDate = ? WHERE d_Id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, userId);
        pstm.setString(2, supId);
        pstm.setDate(3, Date.valueOf(deliverDate));
        pstm.setString(4, d_Id);

        return pstm.executeUpdate() > 0;
    }
    public static boolean add(Devliverydto dt) throws SQLException {
        String sql = "INSERT INTO deliver(userId, d_Id, orderDate, supId, confrmDate, deliverDate) " +
                "VALUES(?, ?, ?, ?, ?, ?)";
        return CrudUtil.execute(
                sql,
                dt.getUserId(),
                dt.getD_Id(),
                dt.getOrderDate(),
                dt.getSupId(),
                dt.getC_date(),
                dt.getD_date());

    }

    public static List<Devliverydto> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM deliver";
        List<Devliverydto> data = new ArrayList<>();

        ResultSet resultSet = con.prepareStatement(sql).executeQuery();

        while (resultSet.next()) {
            data.add(new Devliverydto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            ));
        }
        return data;
    }
    public static Devliverydto searchById(String code) throws SQLException {
        PreparedStatement pstm = DBConnection.getInstance().getConnection()
                .prepareStatement("SELECT * FROM deliver WHERE d_Id = ?");
        pstm.setString(1, code);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return new Devliverydto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)

            );
        }
        return null;
    }
    public static List<String> loadIds() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT d_Id FROM deliver");

        List<String> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

}
