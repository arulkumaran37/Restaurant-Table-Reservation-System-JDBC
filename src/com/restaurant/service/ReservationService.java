package com.restaurant.service;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import com.restaurant.bean.Reservation;
import com.restaurant.bean.Table;
import com.restaurant.dao.ReservationDAO;
import com.restaurant.dao.TableDAO;
import com.restaurant.util.ActiveReservationException;
import com.restaurant.util.DBUtil;
import com.restaurant.util.TableUnavailableException;
import com.restaurant.util.ValidationException;

public class ReservationService {
	TableDAO t = new TableDAO();
	 private ReservationDAO r = new ReservationDAO();
		public Table viewTableDetails(String tableID) {
			if(tableID == null ) {
				return null;
			}
			return t.findTable(tableID);
		}
		
		
		public List<Table> viewAllTables(){
			return t.viewAllTables();
		}
	
		public boolean addNewTable(Table table) throws ValidationException {
			if(table.getTableID() == null|| table.getCapacity() <= 0) {
				 throw new ValidationException();
			}
			Table existing = t.findTable(table.getTableID());
	        if (existing != null)
	            return false;
			return t.insertTable(table);
		}
		
	    public boolean removeTable(String tableID)
	            throws ActiveReservationException, ValidationException {

	        if (tableID == null)
	            throw new ValidationException();

	        boolean hasActive = r.hasActiveReservation(tableID);

	        if (hasActive)
	            throw new ActiveReservationException();

	        return t.deleteTable(tableID);
	    }
	    
	    
	    
	    public boolean reserveTable(String tableID,
                String customerName,
                int partySize,
                Date date,
                String time)
throws Exception {

	if (tableID == null
	|| customerName == null || customerName.trim().isEmpty()
	|| partySize <= 0 || date == null
	|| time == null ) {
	throw new ValidationException();
	}
	
    Connection con = DBUtil.getConnection();
    con.setAutoCommit(false);
	
	try {
	Table table = t.findTable(tableID);
	
	if (table == null)
	return false;
	
	if ("Unavailable".equalsIgnoreCase(table.getStatus()))
	throw new TableUnavailableException();
	
	if (partySize > table.getCapacity())
	return false;
	
	boolean conflict =
	    r.hasSlotConflict(tableID, date, time);
	
	if (conflict)
	throw new TableUnavailableException();
	int reservationID = r.generateReservationID();
	
	Reservation res = new Reservation();
	res.setReservationID(reservationID);
	res.setTableID(tableID);
	res.setCustomerName(customerName);
	res.setPartySize(partySize);
	res.setReservationDate(date);
	res.setReservationTime(time);
	res.setStatus("ACTIVE");
	
	r.recordReservation(res);
	
	t.updateStatus(tableID, "Unavailable");
	
	con.commit();
	return true;
	
 } catch (Exception e) {
	con.rollback();
	throw e;
	}
}
	    
	    public boolean cancelReservation(int reservationID)
	            throws Exception {
	        if (reservationID <= 0)
	            throw new ValidationException();
	        
	        Connection con = DBUtil.getConnection();
	        con.setAutoCommit(false);
	        
	        try {
	        	Reservation res = r.findReservationById(reservationID);

	        	if (res == null) {
	        	    con.rollback();
	        	    return false;
	        	}
	            boolean ok = r.cancelReservation(reservationID);

	            if (!ok) {
	                con.rollback();
	                return false;
	            }

	            t.updateStatus(res.getTableID(), "Available");

	            con.commit();
	            return true;

	        } catch (Exception e) {
	            con.rollback();
	            throw e;
	        }
	    }
	    
		    
	}

























