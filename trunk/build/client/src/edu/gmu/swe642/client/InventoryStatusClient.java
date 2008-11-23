
package edu.gmu.swe642.client;

import edu.gmu.swe642.client.InventoryStatusServiceStub;
import java.util.Calendar;

public class InventoryStatusClient{
    public static void main(java.lang.String args[]){
        try{
            InventoryStatusServiceStub stub =
                new InventoryStatusServiceStub
                ("http://localhost:8080/axis2/services/InventoryStatusService");

            getNumSeats(stub);
            getNumSeatsRange(stub);

        } catch(Exception e){
            e.printStackTrace();
            System.err.println("\n\n\n");
        }
    }

    /* two way call/receive */
    public static void getNumSeats(InventoryStatusServiceStub stub){
        try{
            InventoryStatusServiceStub.GetNumOfEmptySeats req = new InventoryStatusServiceStub.GetNumOfEmptySeats();

            req.setDate(Calendar.getInstance());

            InventoryStatusServiceStub.GetNumOfEmptySeatsResponse res =
                stub.getNumOfEmptySeats(req);

            System.err.println("Number of empty seats: " + res.get_return());
        } catch(Exception e){
            e.printStackTrace();
            System.err.println("\n\n\n");
        }
    }

    /* two way call/receive */
    public static void getNumSeatsRange(InventoryStatusServiceStub stub){
        try{
            InventoryStatusServiceStub.GetNumOfEmptySeatsForDateRange req = new InventoryStatusServiceStub.GetNumOfEmptySeatsForDateRange();

            req.setDate(Calendar.getInstance());

            InventoryStatusServiceStub.GetNumOfEmptySeatsForDateRangeResponse res =
                stub.getNumOfEmptySeatsForDateRange(req);

            System.err.println("Number of empty seats for date range: " + res.get_return());
        } catch(Exception e){
            e.printStackTrace();
            System.err.println("\n\n\n");
        }
    }

}
