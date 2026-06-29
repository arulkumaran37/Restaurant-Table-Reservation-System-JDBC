package com.restaurant.app;

import java.sql.Date;
import java.util.Scanner;
import com.restaurant.service.ReservationService;

public class RestaurantMain {

    private static ReservationService reservationService;

    public static void main(String[] args) {

        reservationService = new ReservationService();
        Scanner sc = new Scanner(System.in);

        System.out.println("--- Restaurant Reservation Console ---");

        try {
        	boolean r = reservationService.reserveTable(
        		    "TBL02",
        		    "Priya Sharma",
        		    2,
        		    java.sql.Date.valueOf("2025-02-15"),
        		    "20:00"
        		);

        		System.out.println(r ? "RESERVED" : "FAILED");
        } catch(Exception e) {
            System.out.println(e);
        }

        try {
            boolean r = reservationService.cancelReservation(90001);
            System.out.println(r ? "CANCELLED" : "FAILED");

        } catch(Exception e) {
            System.out.println(e);
        }

        sc.close();
    }
}