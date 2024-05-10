package model;

import db.DBConnection;
import dto.Employeedto;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    public static boolean delete(String id) throws SQLException {
        return CrudUtil.execute("DELETE FROM employee WHERE e_Id = ?", id);
    }
    public static boolean update(String id, String name, String gender, String address, String email) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "UPDATE employee SET name = ?, gender = ?, salary = ?, c_Id = ?,  WHERE e_Id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, name);
        pstm.setString(2, gender);
        pstm.setString(3, email);
        pstm.setString(4, address);
        pstm.setString(5, id);

        return pstm.executeUpdate() > 0;
    }
    public static boolean add(Employeedto employee) throws SQLException {
        String sql = "INSERT INTO employee(e_Id,name,salary,gender,c_Id) " +
                "VALUES(?, ?, ?, ?, ?)";
        return CrudUtil.execute(
                sql,
                employee.getE_Id(),
                employee.getName(),
                employee.getSalary(),
                employee.getGender(),
                employee.getC_Id());
    }

    public static Employeedto search(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM employee WHERE e_Id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return new Employeedto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)

            );
        }
        return null;
    }
    public static List<Employeedto> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM employee";
        List<Employeedto> data = new ArrayList<>();

        ResultSet resultSet = con.prepareStatement(sql).executeQuery();
        // ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            data.add(new Employeedto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)

            ));
        }
        return data;
    }
}
