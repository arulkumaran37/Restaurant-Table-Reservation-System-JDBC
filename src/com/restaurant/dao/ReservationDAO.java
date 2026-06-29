package com.restaurant.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;

import com.restaurant.bean.Reservation;
import com.restaurant.util.DBUtil;

public class ReservationDAO {

    public int generateReservationID() {
        return (int)(Math.random() * 100000);
    }

    public boolean recordReservation(Reservation r) {

        try {
            Connection con = DBUtil.getConnection();

            String sql = "INSERT INTO RESERVATION_TBL " +
                         "(RESERVATION_ID, TABLE_ID, CUSTOMER_NAME, PARTY_SIZE, " +
                         "RESERVATION_DATE, RESERVATION_TIME, STATUS) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, r.getReservationID());
            ps.setString(2, r.getTableID());
            ps.setString(3, r.getCustomerName());
            ps.setInt(4, r.getPartySize());
            ps.setDate(5, (Date) r.getReservationDate());
            ps.setString(6, r.getReservationTime());
            ps.setString(7, r.getStatus());

            int rows = ps.executeUpdate();

            con.close();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean cancelReservation(int reservationID) {

        try {
            Connection con = DBUtil.getConnection();

            String sql = "UPDATE RESERVATION_TBL SET STATUS='CANCELLED' WHERE RESERVATION_ID=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, reservationID);

            int rows = ps.executeUpdate();

            con.close();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean hasActiveReservation(String tableID) {

        try {
            Connection con = DBUtil.getConnection();

            String sql = "SELECT COUNT(*) FROM RESERVATION_TBL WHERE TABLE_ID=? AND STATUS='ACTIVE'";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tableID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean hasSlotConflict(String tableID, Date date, String time) {

        try {
            Connection con = DBUtil.getConnection();

            String sql =
                "SELECT COUNT(*) FROM RESERVATION_TBL WHERE TABLE_ID=? AND RESERVATION_DATE=? " +
                "AND RESERVATION_TIME=? AND STATUS='ACTIVE'";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, tableID);
            ps.setDate(2, date);
            ps.setString(3, time);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public Reservation findReservationById(int reservationID) {

        Reservation res = null;

        try {
            Connection con = DBUtil.getConnection();

            String sql = "SELECT * FROM RESERVATION_TBL WHERE RESERVATION_ID=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, reservationID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                res = new Reservation();
                res.setReservationID(rs.getInt("RESERVATION_ID"));
                res.setTableID(rs.getString("TABLE_ID"));
                res.setCustomerName(rs.getString("CUSTOMER_NAME"));
                res.setPartySize(rs.getInt("PARTY_SIZE"));
                res.setReservationDate(rs.getDate("RESERVATION_DATE"));
                res.setReservationTime(rs.getString("RESERVATION_TIME"));
                res.setStatus(rs.getString("STATUS"));
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }
}