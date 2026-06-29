package com.restaurant.util;

public class TableUnavailableException extends Exception {
	public String toString() {
		return "Table is unavailable";
	}
}
