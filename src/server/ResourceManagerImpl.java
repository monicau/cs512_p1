// -------------------------------
// Adapted from Kevin T. Manley
// CSE 593
// -------------------------------

package server;

import java.util.*;

import javax.jws.WebService;


@WebService(endpointInterface = "server.ws.ResourceManager")
public class ResourceManagerImpl implements server.ws.ResourceManager {
    
    protected RMHashtable m_itemHT = new RMHashtable();
    
    
    // Basic operations on RMItem //
    
    // Read a data item.
    private RMItem readData(int id, String key) {
        synchronized(m_itemHT) {
            return (RMItem) m_itemHT.get(key);
        }
    }
    
    private ReservableItem getReservableItem(int id, String key) {
    	synchronized(m_itemHT) {
            return (ReservableItem) m_itemHT.get(key);
        }
    }

    // Write a data item.
    private void writeData(int id, String key, RMItem value) {
        synchronized(m_itemHT) {
            m_itemHT.put(key, value);
        }
    }
    
    // Remove the item out of storage.
    protected RMItem removeData(int id, String key) {
        synchronized(m_itemHT) {
            return (RMItem) m_itemHT.remove(key);
        }
    }
    
    
    // Basic operations on ReservableItem //
    
    // Delete the entire item.
    protected boolean deleteItem(int id, String key) {
        Trace.info("RM::deleteItem(" + id + ", " + key + ") called.");
        //Synchronize delete in case someone reserves same item while we are trying to delete it
        ReservableItem curObj = (ReservableItem) readData(id, key);
        synchronized(curObj) {
	        // Check if there is such an item in the storage.
	        if (curObj == null) {
	            Trace.warn("RM::deleteItem(" + id + ", " + key + ") failed: " 
	                    + " item doesn't exist.");
	            return false;
	        } else {
	            if (curObj.getReserved() == 0) {
	                removeData(id, curObj.getKey());
	                Trace.info("RM::deleteItem(" + id + ", " + key + ") OK.");
	                return true;
	            }
	            else {
	                Trace.info("RM::deleteItem(" + id + ", " + key + ") failed: "
	                        + "some customers have reserved it.");
	                return false;
	            }
	        }
        }
    }
    
    // Query the number of available seats/rooms/cars.
    protected int queryNum(int id, String key) {
        Trace.info("RM::queryNum(" + id + ", " + key + ") called.");
        ReservableItem curObj = (ReservableItem) readData(id, key);
        int value = 0;  
        if (curObj != null) {
            value = curObj.getCount();
        }
        Trace.info("RM::queryNum(" + id + ", " + key + ") OK: " + value);
        return value;
    }    
    
    // Query the price of an item.
    protected int queryPrice(int id, String key) {
        Trace.info("RM::queryPrice(" + id + ", " + key + ") called.");
        ReservableItem curObj = (ReservableItem) readData(id, key);
        int value = 0; 
        if (curObj != null) {
            value = curObj.getPrice();
        }
        Trace.info("RM::queryPrice(" + id + ", " + key + ") OK: $" + value);
        return value;
    }
    
    //Public version
    public int getPrice(int id, String key) {
    	return queryPrice(id, key);
    }
    
    // Flight operations //
    
    // Create a new flight, or add seats to existing flight.
    // Note: if flightPrice <= 0 and the flight already exists, it maintains 
    // its current price.
    @Override
    synchronized public boolean addFlight(int id, int flightNumber, 
                             int numSeats, int flightPrice) {
        Trace.info("RM::addFlight(" + id + ", " + flightNumber 
                + ", $" + flightPrice + ", " + numSeats + ") called.");
        Flight curObj = (Flight) readData(id, Flight.getKey(flightNumber));
        if (curObj == null) {
            // Doesn't exist; add it.
            Flight newObj = new Flight(flightNumber, numSeats, flightPrice);
            writeData(id, newObj.getKey(), newObj);
            Trace.info("RM::addFlight(" + id + ", " + flightNumber 
                    + ", $" + flightPrice + ", " + numSeats + ") OK.");
        } else {
        	// Add seats to existing flight and update the price.
        	curObj.setCount(curObj.getCount() + numSeats);
        	if (flightPrice > 0) {
        		curObj.setPrice(flightPrice);
        	}
        	writeData(id, curObj.getKey(), curObj);
        	Trace.info("RM::addFlight(" + id + ", " + flightNumber 
                    + ", $" + flightPrice + ", " + numSeats + ") OK: "
                    + "seats = " + curObj.getCount() + ", price = $" + flightPrice);
        }
        return(true);
    }

    @Override
    public boolean deleteFlight(int id, int flightNumber) {
        return deleteItem(id, Flight.getKey(flightNumber));
    }

    // Returns the number of empty seats on this flight.
    @Override
    public int queryFlight(int id, int flightNumber) {
        return queryNum(id, Flight.getKey(flightNumber));
    }

    // Returns price of this flight.
    public int queryFlightPrice(int id, int flightNumber) {
        return queryPrice(id, Flight.getKey(flightNumber));
    }

    /*
    // Returns the number of reservations for this flight. 
    public int queryFlightReservations(int id, int flightNumber) {
        Trace.info("RM::queryFlightReservations(" + id 
                + ", #" + flightNumber + ") called.");
        RMInteger numReservations = (RMInteger) readData(id, 
                Flight.getNumReservationsKey(flightNumber));
        if (numReservations == null) {
            numReservations = new RMInteger(0);
       }
        Trace.info("RM::queryFlightReservations(" + id + 
                ", #" + flightNumber + ") = " + numReservations);
        return numReservations.getValue();
    }
    */
    
    /*
    // Frees flight reservation record. Flight reservation records help us 
    // make sure we don't delete a flight if one or more customers are 
    // holding reservations.
    public boolean freeFlightReservation(int id, int flightNumber) {
        Trace.info("RM::freeFlightReservations(" + id + ", " 
                + flightNumber + ") called.");
        RMInteger numReservations = (RMInteger) readData(id, 
                Flight.getNumReservationsKey(flightNumber));
        if (numReservations != null) {
            numReservations = new RMInteger(
                    Math.max(0, numReservations.getValue() - 1));
        }
        writeData(id, Flight.getNumReservationsKey(flightNumber), numReservations);
        Trace.info("RM::freeFlightReservations(" + id + ", " 
                + flightNumber + ") OK: reservations = " + numReservations);
        return true;
    }
    */


    // Car operations //

    // Create a new car location or add cars to an existing location.
    // Note: if price <= 0 and the car location already exists, it maintains 
    // its current price.
    @Override
    synchronized public boolean addCars(int id, String location, int numCars, int carPrice) {
        Trace.info("RM::addCars(" + id + ", " + location + ", " 
                + numCars + ", $" + carPrice + ") called.");
        Car curObj = (Car) readData(id, Car.getKey(location));
        if (curObj == null) {
            // Doesn't exist; add it.
            Car newObj = new Car(location, numCars, carPrice);
            writeData(id, newObj.getKey(), newObj);
            Trace.info("RM::addCars(" + id + ", " + location + ", " 
                    + numCars + ", $" + carPrice + ") OK.");
        } else {
            // Add count to existing object and update price.
        	curObj.setCount(curObj.getCount() + numCars);
        	if (carPrice > 0) {
        		curObj.setPrice(carPrice);
        	}
        	writeData(id, curObj.getKey(), curObj);
            Trace.info("RM::addCars(" + id + ", " + location + ", " 
                    + numCars + ", $" + carPrice + ") OK: " 
                    + "cars = " + curObj.getCount() + ", price = $" + carPrice);
        }
        return(true);
    }

    // Delete cars from a location.
    @Override
    public boolean deleteCars(int id, String location) {
        return deleteItem(id, Car.getKey(location));
    }

    // Returns the number of cars available at a location.
    @Override
    public int queryCars(int id, String location) {
        return queryNum(id, Car.getKey(location));
    }

    // Returns price of cars at this location.
    @Override
    public int queryCarsPrice(int id, String location) {
        return queryPrice(id, Car.getKey(location));
    }
    

    // Room operations //

    // Create a new room location or add rooms to an existing location.
    // Note: if price <= 0 and the room location already exists, it maintains 
    // its current price.
    @Override
    synchronized public boolean addRooms(int id, String location, int numRooms, int roomPrice) {
        Trace.info("RM::addRooms(" + id + ", " + location + ", " 
                + numRooms + ", $" + roomPrice + ") called.");
        Room curObj = (Room) readData(id, Room.getKey(location));
        if (curObj == null) {
            // Doesn't exist; add it.
            Room newObj = new Room(location, numRooms, roomPrice);
            writeData(id, newObj.getKey(), newObj);
            Trace.info("RM::addRooms(" + id + ", " + location + ", " 
                    + numRooms + ", $" + roomPrice + ") OK.");
        } else {
            // Add count to existing object and update price.
            curObj.setCount(curObj.getCount() + numRooms);
            if (roomPrice > 0) {
                curObj.setPrice(roomPrice);
            }
            writeData(id, curObj.getKey(), curObj);
            Trace.info("RM::addRooms(" + id + ", " + location + ", " 
                    + numRooms + ", $" + roomPrice + ") OK: " 
                    + "rooms = " + curObj.getCount() + ", price = $" + roomPrice);
        }
        return(true);
    }

    // Delete rooms from a location.
    @Override
    public boolean deleteRooms(int id, String location) {
        return deleteItem(id, Room.getKey(location));
    }

    // Returns the number of rooms available at a location.
    @Override
    public int queryRooms(int id, String location) {
        return queryNum(id, Room.getKey(location));
    }
    
    // Returns room price at this location.
    @Override
    public int queryRoomsPrice(int id, String location) {
        return queryPrice(id, Room.getKey(location));
    }


    // Customer operations //

    //Method for middleware only.
    @Override
    public int newCustomer(int id) {
        return -1;
    }

    // Method for middleware only.    @Override
    public boolean newCustomerId(int id, int customerId) {
        return false;
    }

    // Method for middleware only.
    @Override
    public boolean deleteCustomer(int id, int customerId) {
        return false;
    }

    // Method for middleware only.
    public RMHashtable getCustomerReservations(int id, int customerId) {
    	return null;
    }

    // Method for middleware only.
    @Override
    public String queryCustomerInfo(int id, int customerId) {
        return "No customer information on this server.";
    }
    
    /**
     * 
     * @param reserveType	flight, car or room
     * @param id			id 
     * @param flightNumber	flight number if applicable
     * @param location		car or room location if applicable
     * @return				true if success, else false
     */
    // Add reservation  
    @Override
    public boolean reserveItem(String reserveType, int id, int flightNumber, String location) {
    	String key = null;
    	if (reserveType.toLowerCase().equals("flight")) {
    		location = String.valueOf(flightNumber);        
    		key = Flight.getKey(flightNumber);
        } else if (reserveType.toLowerCase().equals("car")) {
        	key = Car.getKey(location);
        } else if (reserveType.toLowerCase().equals("room")) {
        	key = Room.getKey(location);
        } else {
        	return false;
        }
    	// Check if the item is available.
        ReservableItem item = (ReservableItem) readData(id, key);
        if (item == null) {
            Trace.warn("RM::rmReserve failed: item doesn't exist.");
            return false;
        } else if (item.getCount() == 0) {
            Trace.warn("RM::rmReserve failed: no more items.");
            return false;
        } else {
            // Do reservation.
            // Decrease the number of available items in the storage.
        	synchronized(item) {
	            item.setCount(item.getCount() - 1);
	            item.setReserved(item.getReserved() + 1);
        	}
            Trace.warn("RM::rmReserve(" + reserveType + ", " + id + ", " + Integer.toString(flightNumber) + ", " + location + ") OK.");
            return true;
        }
    }
    /**
     * 
     * @param id				id
     * @param key				reserved item's key
     * @param reservationCount	number of reservations for this item
     * @return					true if successful, else false
     */
    // Removes a reservation
    public boolean rmUnreserve(int id, String key, int reservationCount) {
    	ReservableItem item = (ReservableItem) readData(id, key);
    	if (item == null) {
    		Trace.info("RM:: Cannot unreserve item, it does not exist: " + key);
    		return false;
    	}
        item.setReserved(item.getReserved() - reservationCount);
        item.setCount(item.getCount() + reservationCount);
        Trace.info("RM:: item unreserved. Reserved count is now " + item.getReserved() + ", available count is now " + item.getCount());
		return true;
    }
    
    // Add flight reservation to this customer.
    @Override
    public boolean reserveFlight(int id, int customerId, int flightNumber) {
    	return false;
    }
    
    // Add car reservation to this customer. 
    @Override
    public boolean reserveCar(int id, int customerId, String location) {
        return false;
    }

    // Add room reservation to this customer. 
    @Override
    public boolean reserveRoom(int id, int customerId, String location) {
        return false;
    }
    

    // Reserve an itinerary.
    @Override
    public boolean reserveItinerary(int id, int customerId, Vector flightNumbers,
                                    String location, boolean car, boolean room) {
        return false;
    }

}
