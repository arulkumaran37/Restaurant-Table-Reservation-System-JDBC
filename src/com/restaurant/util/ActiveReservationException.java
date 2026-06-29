package com.restaurant.util;

public class ActiveReservationException extends Exception {
    public String toString() {
        return "Cannot delete table with active reservations";
    }
}
