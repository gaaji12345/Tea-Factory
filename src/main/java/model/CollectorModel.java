package model;

import db.DBConnection;
import dto.Collectordto;
import dto.Supplier;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CollectorModel {
    public static boolean delete(String id) throws SQLException {

        return CrudUtil.execute("DELETE FROM collector WHERE c_Id = ?",id);

    }
    public static boolean update(Collectordto c) throws SQLException {
        return CrudUtil.execute("UPDATE collector SET name = ?, contact = ?, address = ? WHERE c_Id = ?", c.getName(), c.getContact(), c.getAddress(), c.getId());
    }

    public static boolean add(Collectordto s) throws SQLException {
        return CrudUtil.execute("INSERT INTO collector(c_Id, name,  address,contact) VALUES(?, ?, ?, ?)", s.getId(), s.getName(), s.getAddress(),s.getContact());
    }

    public static Collectordto search(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM collector WHERE c_Id = ?", id);
        if(rst.next()) {
            return new Collectordto(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4));
        }
        return null;
    }
    public static ArrayList<Collectordto> getAll() throws SQLException {
        ArrayList<Collectordto> allc = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM collector");
        while (rst.next()) {
            allc.add(new Collectordto(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4)));
        }
        return allc;
    }

    public static List<String> loadIds() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT c_Id FROM collector");

        List<String> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }
}


