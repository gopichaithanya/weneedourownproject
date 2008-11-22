package edu.gmu.swe642;

import java.util.Date;

/**
 * Inventory Status on number of available seats on flights
 */
public class InventoryStatusService {

    /**
     * Return the number of empty seats on today's flights
     */
    public int getNumOfEmptySeats(Date date) {
           return 90;
    }
    /**
     * Return the number of empty seats for the date range btw today and a future date
     */
    public int getNumOfEmptySeatsForDateRange(Date date) {
        return 55;
    }
}