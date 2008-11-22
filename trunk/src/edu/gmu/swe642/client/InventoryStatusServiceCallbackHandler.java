
/**
 * InventoryStatusServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4  Built on : Apr 26, 2008 (06:24:30 EDT)
 */

    package edu.gmu.swe642.client;

    /**
     *  InventoryStatusServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class InventoryStatusServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public InventoryStatusServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public InventoryStatusServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for getNumOfEmptySeats method
            * override this method for handling normal response from getNumOfEmptySeats operation
            */
           public void receiveResultgetNumOfEmptySeats(
                    edu.gmu.swe642.client.InventoryStatusServiceStub.GetNumOfEmptySeatsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getNumOfEmptySeats operation
           */
            public void receiveErrorgetNumOfEmptySeats(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getNumOfEmptySeatsForDateRange method
            * override this method for handling normal response from getNumOfEmptySeatsForDateRange operation
            */
           public void receiveResultgetNumOfEmptySeatsForDateRange(
                    edu.gmu.swe642.client.InventoryStatusServiceStub.GetNumOfEmptySeatsForDateRangeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getNumOfEmptySeatsForDateRange operation
           */
            public void receiveErrorgetNumOfEmptySeatsForDateRange(java.lang.Exception e) {
            }
                


    }
    