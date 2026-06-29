package com.restaurant.bean;

import java.sql.Date;

public class Reservation {
	private int reservationID;
	private String tableID;
	private String customerName;
	private int partySize;
	private Date reservationDate;
	private String reservationTime;
	  private String status;
	  public int getReservationID() {
		return reservationID;
	  }
	  public void setReservationID(int reservationID) {
		this.reservationID = reservationID;
	  }
	  public String getTableID() {
		return tableID;
	  }
	  public void setTableID(String tableID) {
		this.tableID = tableID;
	  }
	  public String getCustomerName() {
		return customerName;
	  }
	  public void setCustomerName(String customerName) {
		this.customerName = customerName;
	  }
	  public Date getReservationDate() {
		return reservationDate;
	  }
	  public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	  }
	  public int getPartySize() {
		return partySize;
	  }
	  public void setPartySize(int partySize) {
		this.partySize = partySize;
	  }
	  public String getReservationTime() {
		return reservationTime;
	  }
	  public void setReservationTime(String reservationTime) {
		this.reservationTime = reservationTime;
	  }
	  public String getStatus() {
		return status;
	  }
	  public void setStatus(String status) {
		this.status = status;
	  }
	  
}
