package com.restaurant.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.restaurant.bean.Table;
import com.restaurant.util.DBUtil;

public class TableDAO {
	public Table findTable(String tableID) {

	    Table table = null;

	    try {
	        Connection con = DBUtil.getConnection();

	        String sql = "SELECT * FROM TABLE_TBL WHERE TABLE_ID = ?";

	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setString(1, tableID);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            table = new Table();
	            table.setTableID(rs.getString("TABLE_ID"));
	            table.setCapacity(rs.getInt("CAPACITY"));
	            table.setLocation(rs.getString("LOCATION"));
	            table.setStatus(rs.getString("STATUS"));
	        }

	        con.close();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return table;
	}
	
	public List<Table> viewAllTables() {

	    List<Table> list = new ArrayList<>();

	    try {
	        Connection con = DBUtil.getConnection();

	        String sql = "SELECT * FROM TABLE_TBL";

	        PreparedStatement ps = con.prepareStatement(sql);

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            Table t = new Table();
	            t.setTableID(rs.getString("TABLE_ID"));
	            t.setCapacity(rs.getInt("CAPACITY"));
	            t.setLocation(rs.getString("LOCATION"));
	            t.setStatus(rs.getString("STATUS"));

	            list.add(t);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}
	 	
	public boolean insertTable(Table table) {

	    try {
	        Connection con = DBUtil.getConnection();

	        String sql = "INSERT INTO TABLE_TBL VALUES (?, ?, ?, ?)";

	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setString(1, table.getTableID());
	        ps.setInt(2, table.getCapacity());
	        ps.setString(3, table.getLocation());
	        ps.setString(4, table.getStatus());

	        return ps.executeUpdate() > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return false;
	}
	 public boolean updateStatus(String tableID, String status) {

		    try {
		        Connection con = DBUtil.getConnection();

		        String sql = "UPDATE TABLE_TBL SET STATUS=? WHERE TABLE_ID=?";

		        PreparedStatement ps = con.prepareStatement(sql);
		        ps.setString(1, status);
		        ps.setString(2, tableID);

		        return ps.executeUpdate() > 0;

		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return false;
		}

	 public boolean deleteTable(String tableID) {

		    try {
		        Connection con = DBUtil.getConnection();

		        String sql = "DELETE FROM TABLE_TBL WHERE TABLE_ID=?";

		        PreparedStatement ps = con.prepareStatement(sql);
		        ps.setString(1, tableID);

		        return ps.executeUpdate() > 0;

		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return false;
		}
	 
}
