package model;

import db.DBConnection;
import dto.Supplier;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {

    public static boolean delete(String id) throws SQLException {

   return CrudUtil.execute("DELETE FROM supplier WHERE supId = ?",id);

    }
    public static boolean update(Supplier supplier) throws SQLException {
        return CrudUtil.execute("UPDATE supplier SET supName = ?, supContact = ?, supplyAddress = ? WHERE supId = ?", supplier.getName(), supplier.getContact(), supplier.getAddress(), supplier.getId());
    }

    public static boolean add(Supplier s) throws SQLException {
        return CrudUtil.execute("INSERT INTO supplier(supId, supName, supContact, supplyAddress) VALUES(?, ?, ?, ?)", s.getId(), s.getName(), s.getContact(), s.getAddress());
    }

    public static Supplier search(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM supplier WHERE supId = ?", id);
        if(rst.next()) {
            return new Supplier(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4));
        }
        return null;
    }
    public static ArrayList<Supplier> getAll() throws SQLException {
        ArrayList<Supplier> allSupplierDetails = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM supplier");
        while (rst.next()) {
            allSupplierDetails.add(new Supplier(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4)));
        }
        return allSupplierDetails;
    }

    public static List<String> loadIds() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT supId FROM supplier");

        List<String> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static Supplier searchById(String code) throws SQLException {
        PreparedStatement pstm = DBConnection.getInstance().getConnection()
                .prepareStatement("SELECT * FROM supplier WHERE supId = ?");
        pstm.setString(1, code);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return new Supplier(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)

            );
        }
        return null;
    }
}


