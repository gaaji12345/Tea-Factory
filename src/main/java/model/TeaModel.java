package model;

import db.DBConnection;
import dto.Tea;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeaModel {
    public static boolean delete(String id) throws SQLException {
        return CrudUtil.execute("DELETE FROM tea WHERE teaId = ?", id);
    }

    public static ArrayList<Tea> getAll() throws SQLException {
        ArrayList<Tea> allTeaDetails = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM tea");
        while (rst.next()) {
            allTeaDetails.add(new Tea(rst.getString(1), rst.getString(2), rst.getString(3), rst.getDouble(4)));
        }
        return allTeaDetails;
    }

    public static boolean update(String id, String name, String details, Double price) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "UPDATE tea SET teaName = ?, teaDetails = ?, teaPrice = ? WHERE teaId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, name);
        pstm.setString(2, details);
        pstm.setDouble(3, price);
        pstm.setString(4, id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean add(Tea tea) throws SQLException {
        String sql = "INSERT INTO tea(teaId, teaName, teaDetails, teaPrice) " +
                "VALUES(?, ?, ?, ?)";
        return CrudUtil.execute(
                sql,
                tea.getId(),
                tea.getName(),
                tea.getDetails(),
                tea.getPrice());
    }

    public static List<String> loadIds() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT teaId FROM tea");

        List<String> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static Tea searchById(String code) throws SQLException {
        PreparedStatement pstm = DBConnection.getInstance().getConnection()
                .prepareStatement("SELECT * FROM tea WHERE teaId = ?");
        pstm.setString(1, code);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return new Tea(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)


            );
        }
        return null;
    }
    public static Tea search(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM tea WHERE teaId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Tea(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            );
        }
        return null;
    }

}
