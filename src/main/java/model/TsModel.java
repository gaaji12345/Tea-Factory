package model;

import db.DBConnection;
import dto.Supplier;
import dto.TsCol_Detail;
import util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;

public class TsModel {

    public static boolean delete(String id) throws SQLException {
        return CrudUtil.execute("DELETE FROM tsCollect_details WHERE supId = ?",id);
    }


    public static boolean add(TsCol_Detail e) throws SQLException {
       // return CrudUtil.execute("INSERT INTO tsCollect_details(supId,c_Id,amount_of_the_tea)VALUES(?, ?, ?)",e.getSupId(),e.getC_Id(),e.getAmount());
        String sql = "INSERT INTO tea_factory.tsCollect_details(c_Id, supId, amount_of_the_tea) " +
                "VALUES(?, ?, ?)";
        return CrudUtil.execute(
                sql,
               e.getSupId(),
                e.getC_Id(),
                e.getAmount());
    }

    public static ArrayList<TsCol_Detail> getAll() throws SQLException {
        ArrayList<TsCol_Detail> all= new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM tsCollect_details");
        while (rst.next()){
            all.add(new TsCol_Detail(rst.getString(1), rst.getString(2), rst.getString(3)));
        }
        return all;
    }

    public static boolean update(String s, String c, String a) throws SQLException {
       // return CrudUtil.execute("UPDATE tsCollect_details SET c_Id = ?, amount_of_the_tea = ? WHERE supId= ?", s.get, entity.getRoomId(), entity.getCheckOut(), entity.getBookingId());
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "UPDATE tsCollect_details SET c_Id = ?, amount_of_the_tea = ? WHERE supId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, s);
        pstm.setString(2, c);
        pstm.setString(3, a);

        return pstm.executeUpdate() > 0;
    }
}
