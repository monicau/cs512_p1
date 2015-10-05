package server;

//-------------------------------
//Adapted from  
//CSE 593
//-------------------------------

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Vector;
import java.net.URL;
import java.net.MalformedURLException;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(endpointInterface = "server.ws.ResourceManager")
public class MiddlewareImpl implements server.ws.ResourceManager {
	ResourceManager proxyFlight;
	ResourceManager proxyCar;
	ResourceManager proxyRoom;
	ResourceManagerImplService service;
	
public MiddlewareImpl() {

	String flightServiceHost = null;
	Integer flightServicePort = null;
	String carServiceHost = null;
	Integer carServicePort = null;
	String roomServiceHost = null;
	Integer roomServicePort = null;
	try {
		BufferedReader reader = new BufferedReader(new FileReader(new File("rm.txt")));
		String[] line = new String[6];
		try {
			for (int i=0; i<6; i++) {
				line[i] = reader.readLine();
				Trace.info("Read: " + line[i]);
			}
			flightServiceHost = line[0];
			flightServicePort = Integer.parseInt(line[1]);
			carServiceHost = line[2];
			carServicePort = Integer.parseInt(line[3]);
			roomServiceHost = line[4];
			roomServicePort = Integer.parseInt(line[5]);
			try {
		        URL wsdlLocation = new URL("http", flightServiceHost, flightServicePort, "/" + "rm" + "/rm?wsdl");
		        service = new ResourceManagerImplService(wsdlLocation);
		        proxyFlight = service.getResourceManagerImplPort();
		        
		        wsdlLocation = new URL("http", carServiceHost, carServicePort, "/" + "rm" + "/rm?wsdl");
		        service = new ResourceManagerImplService(wsdlLocation);
		        proxyCar= service.getResourceManagerImplPort();
		        
		        wsdlLocation = new URL("http", roomServiceHost, roomServicePort, "/" + "rm" + "/rm?wsdl");
		        service = new ResourceManagerImplService(wsdlLocation);
		        proxyRoom= service.getResourceManagerImplPort();
			} catch (MalformedURLException e) {
				Trace.info("ERROR!! Malformed url.");
			}
		} catch (IOException e) {
			Trace.info("ERROR: Reading line failed! IOException.");
		}
	} catch (FileNotFoundException e) {
		Trace.info("ERROR: File not found!");
	}
	
}
 protected RMHashtable m_itemHT = new RMHashtable();
 
 
 // Basic operations on RMItem //
 
 // Read a data item.
 private RMItem readData(int id, String key) {
     synchronized(m_itemHT) {
         return (RMItem) m_itemHT.get(key);
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
     Trace.info("MW::deleteItem(" + id + ", " + key + ") called.");
     ReservableItem curObj = (ReservableItem) readData(id, key);
     // Check if there is such an item in the storage.
     if (curObj == null) {
         Trace.warn("MW::deleteItem(" + id + ", " + key + ") failed: " 
                 + " item doesn't exist.");
         return false;
     } else {
         if (curObj.getReserved() == 0) {
             removeData(id, curObj.getKey());
             Trace.info("MW::deleteItem(" + id + ", " + key + ") OK.");
             return true;
         }
         else {
             Trace.info("MW::deleteItem(" + id + ", " + key + ") failed: "
                     + "some customers have reserved it.");
             return false;
         }
     }
 }
 
 // Query the number of available seats/rooms/cars.
 protected int queryNum(int id, String key) {
     Trace.info("MW::queryNum(" + id + ", " + key + ") called.");
     ReservableItem curObj = (ReservableItem) readData(id, key);
     int value = 0;  
     if (curObj != null) {
         value = curObj.getCount();
     }
     Trace.info("MW::queryNum(" + id + ", " + key + ") OK: " + value);
     return value;
 }    
 
 // Query the price of an item.
 protected int queryPrice(int id, String key) {
     Trace.info("MW::queryCarsPrice(" + id + ", " + key + ") called.");
     ReservableItem curObj = (ReservableItem) readData(id, key);
     int value = 0; 
     if (curObj != null) {
         value = curObj.getPrice();
     }
     Trace.info("MW::queryCarsPrice(" + id + ", " + key + ") OK: $" + value);
     return value;
 }
 
 //Public version
 public int getPrice(int id, String key) {
 	return queryPrice(id, key);
 }

 // Reserve an item.
 protected boolean reserveItem(int id, int customerId, 
                               String key, String location) {
     Trace.info("MW::reserveItem(" + id + ", " + customerId + ", " 
             + key + ", " + location + ") called.");
     // Read customer object if it exists (and read lock it).
     Customer cust = (Customer) readData(id, Customer.getKey(customerId));
     if (cust == null) {
         Trace.warn("MW::reserveItem(" + id + ", " + customerId + ", " 
                + key + ", " + location + ") failed: customer doesn't exist.");
         return false;
     } 
     
     // Check if the item is available.
     ReservableItem item = (ReservableItem) readData(id, key);
     if (item == null) {
         Trace.warn("MW::reserveItem(" + id + ", " + customerId + ", " 
                 + key + ", " + location + ") failed: item doesn't exist.");
         return false;
     } else if (item.getCount() == 0) {
         Trace.warn("MW::reserveItem(" + id + ", " + customerId + ", " 
                 + key + ", " + location + ") failed: no more items.");
         return false;
     } else {
         // Do reservation.
         cust.reserve(key, location, item.getPrice());
         writeData(id, cust.getKey(), cust);
         
         // Decrease the number of available items in the storage.
         item.setCount(item.getCount() - 1);
         item.setReserved(item.getReserved() + 1);
         
         Trace.warn("MW::reserveItem(" + id + ", " + customerId + ", " 
                 + key + ", " + location + ") OK.");
         return true;
     }
 }
 
 
 // Flight operations //
 
 // Create a new flight, or add seats to existing flight.
 // Note: if flightPrice <= 0 and the flight already exists, it maintains 
 // its current price.
 @Override
 public boolean addFlight(int id, int flightNumber, 
                          int numSeats, int flightPrice) {
     Trace.info("MW::addFlight(" + id + ", " + flightNumber 
             + ", $" + flightPrice + ", " + numSeats + ") called.");
     boolean returnValue = proxyFlight.addFlight(id, flightNumber, numSeats, flightPrice);
     Trace.info("MW:: addFlight succeeded:" + Boolean.toString(returnValue));
     return returnValue;
 }

 @Override
 public boolean deleteFlight(int id, int flightNumber) {
     return proxyFlight.deleteFlight(id, flightNumber);
 }

 // Returns the number of empty seats on this flight.
 @Override
 public int queryFlight(int id, int flightNumber) {
	 return proxyFlight.queryFlight(id, flightNumber);
 }

 // Returns price of this flight.
 public int queryFlightPrice(int id, int flightNumber) {
     return proxyFlight.queryFlightPrice(id, flightNumber);
 }

 /*
 // Returns the number of reservations for this flight. 
 public int queryFlightReservations(int id, int flightNumber) {
     Trace.info("MW::queryFlightReservations(" + id 
             + ", #" + flightNumber + ") called.");
     RMInteger numReservations = (RMInteger) readData(id, 
             Flight.getNumReservationsKey(flightNumber));
     if (numReservations == null) {
         numReservations = new RMInteger(0);
    }
     Trace.info("MW::queryFlightReservations(" + id + 
             ", #" + flightNumber + ") = " + numReservations);
     return numReservations.getValue();
 }
 */
 
 /*
 // Frees flight reservation record. Flight reservation records help us 
 // make sure we don't delete a flight if one or more customers are 
 // holding reservations.
 public boolean freeFlightReservation(int id, int flightNumber) {
     Trace.info("MW::freeFlightReservations(" + id + ", " 
             + flightNumber + ") called.");
     RMInteger numReservations = (RMInteger) readData(id, 
             Flight.getNumReservationsKey(flightNumber));
     if (numReservations != null) {
         numReservations = new RMInteger(
                 Math.max(0, numReservations.getValue() - 1));
     }
     writeData(id, Flight.getNumReservationsKey(flightNumber), numReservations);
     Trace.info("MW::freeFlightReservations(" + id + ", " 
             + flightNumber + ") OK: reservations = " + numReservations);
     return true;
 }
 */


 // Car operations //

 // Create a new car location or add cars to an existing location.
 // Note: if price <= 0 and the car location already exists, it maintains 
 // its current price.
 @Override
 public boolean addCars(int id, String location, int numCars, int carPrice) {
     Trace.info("MW::addCars(" + id + ", " + location + ", " 
             + numCars + ", $" + carPrice + ") called.");
     boolean returnValue = proxyCar.addCars(id, location, numCars, carPrice);
     Trace.info("MW::addCar succeeded: " + Boolean.toString(returnValue));
     return returnValue;
 }

 // Delete cars from a location.
 @Override
 public boolean deleteCars(int id, String location) {
     return proxyCar.deleteCars(id, location);
 }

 // Returns the number of cars available at a location.
 @Override
 public int queryCars(int id, String location) {
     return proxyCar.queryCars(id, location);
 }

 // Returns price of cars at this location.
 @Override
 public int queryCarsPrice(int id, String location) {
     return proxyCar.queryCarsPrice(id, location);
 }
 

 // Room operations //

 // Create a new room location or add rooms to an existing location.
 // Note: if price <= 0 and the room location already exists, it maintains 
 // its current price.
 @Override
 public boolean addRooms(int id, String location, int numRooms, int roomPrice) {
     Trace.info("MW::addRooms(" + id + ", " + location + ", " 
             + numRooms + ", $" + roomPrice + ") called.");
     boolean returnValue = proxyRoom.addRooms(id, location, numRooms, roomPrice);
     Trace.info("MW::addRooms succeeded: " + Boolean.toString(returnValue));
     return returnValue;
 }

 // Delete rooms from a location.
 @Override
 public boolean deleteRooms(int id, String location) {
     return proxyRoom.deleteRooms(id, location);
 }

 // Returns the number of rooms available at a location.
 @Override
 public int queryRooms(int id, String location) {
     return proxyRoom.queryRooms(id, location);
 }
 
 // Returns room price at this location.
 @Override
 public int queryRoomsPrice(int id, String location) {
     return proxyRoom.queryRoomsPrice(id, location);
 }


 // Customer operations //

 @Override
 public int newCustomer(int id) {
     Trace.info("INFO: MW::newCustomer(" + id + ") called.");
     // Generate a globally unique Id for the new customer.
     int customerId = Integer.parseInt(String.valueOf(id) +
             String.valueOf(Calendar.getInstance().get(Calendar.MILLISECOND)) +
             String.valueOf(Math.round(Math.random() * 100 + 1)));
     Customer cust = new Customer(customerId);
     writeData(id, cust.getKey(), cust);
     Trace.info("MW::newCustomer(" + id + ") OK: " + customerId);
     return customerId;
 }

 // This method makes testing easier.
 @Override
 public boolean newCustomerId(int id, int customerId) {
     Trace.info("INFO: MW::newCustomer(" + id + ", " + customerId + ") called.");
     Customer cust = (Customer) readData(id, Customer.getKey(customerId));
     if (cust == null) {
         cust = new Customer(customerId);
         writeData(id, cust.getKey(), cust);
         Trace.info("INFO: MW::newCustomer(" + id + ", " + customerId + ") OK.");
         return true;
     } else {
         Trace.info("INFO: MW::newCustomer(" + id + ", " + 
                 customerId + ") failed: customer already exists.");
         return false;
     }
 }

 // Delete customer from the database. 
 @Override
 public boolean deleteCustomer(int id, int customerId) {
     Trace.info("MW::deleteCustomer(" + id + ", " + customerId + ") called.");
     Customer cust = (Customer) readData(id, Customer.getKey(customerId));
     if (cust == null) {
         Trace.warn("MW::deleteCustomer(" + id + ", " 
                 + customerId + ") failed: customer doesn't exist.");
         return false;
     } else {            
         // Increase the reserved numbers of all reservable items that 
         // the customer reserved. 
         RMHashtable reservationHT = cust.getReservations();
         for (Enumeration e = reservationHT.keys(); e.hasMoreElements();) {        
             String reservedKey = (String) (e.nextElement());
             ReservedItem reservedItem = cust.getReservedItem(reservedKey);
             Trace.info("MW::deleteCustomer(" + id + ", " + customerId + "): " 
                     + "deleting " + reservedItem.getCount() + " reservations "
                     + "for item " + reservedItem.getKey());
             //TODO: proxy dis shit
             //Since we don't know what type of item it is, we will try one proxy at a time
             Trace.info("MW::Attempting to unreserve " + reservedItem.getKey());
             if (proxyFlight.rmUnreserve(id, reservedItem.getKey(), reservedItem.getCount()) == false) {
            	 Trace.info("MW::unreserving flight failed. Trying car..");
            	 if (proxyCar.rmUnreserve(id, reservedItem.getKey(), reservedItem.getCount()) == false) {
            		 Trace.info("MW::unreserving car failed. Trying room..");
            		 if (proxyRoom.rmUnreserve(id, reservedItem.getKey(), reservedItem.getCount()) == false) {
            			 Trace.info("MW::fail to cancel reservation for room too. Wah");
            		 }
            	 }
             }
         }
         // Remove the customer from the storage.
         removeData(id, cust.getKey());
         Trace.info("MW::deleteCustomer(" + id + ", " + customerId + ") OK.");
         return true;
     }
 }

 // Return data structure containing customer reservation info. 
 // Returns null if the customer doesn't exist. 
 // Returns empty RMHashtable if customer exists but has no reservations.
 public RMHashtable getCustomerReservations(int id, int customerId) {
     Trace.info("MW::getCustomerReservations(" + id + ", " 
             + customerId + ") called.");
     Customer cust = (Customer) readData(id, Customer.getKey(customerId));
     if (cust == null) {
         Trace.info("MW::getCustomerReservations(" + id + ", " 
                 + customerId + ") failed: customer doesn't exist.");
         return null;
     } else {
         return cust.getReservations();
     }
 }

 // Return a bill.
 @Override
 public String queryCustomerInfo(int id, int customerId) {
     Trace.info("MW::queryCustomerInfo(" + id + ", " + customerId + ") called.");
     Customer cust = (Customer) readData(id, Customer.getKey(customerId));
     if (cust == null) {
         Trace.warn("MW::queryCustomerInfo(" + id + ", " 
                 + customerId + ") failed: customer doesn't exist.");
         // Returning an empty bill means that the customer doesn't exist.
         return "";
     } else {
         String s = cust.printBill();
         Trace.info("MW::queryCustomerInfo(" + id + ", " + customerId + "): \n");
         System.out.println(s);
         return s;
     }
 }

 public boolean rmReserve(String reserveType, int id, int flightNumber, String location) {
	 return false;
 }
 public boolean rmUnreserve(int id, String key, int reservationCount) {
	 return false;
 }
 // Add flight reservation to this customer.  
 @Override
 public boolean reserveFlight(int id, int customerId, int flightNumber) {
	 // Read customer object if it exists (and read lock it).
     Customer cust = (Customer) readData(id, Customer.getKey(customerId));
     if (cust == null) {
         Trace.warn("MW::reserveFlight(" + id + ", " + customerId +  ", " + flightNumber + ") failed: customer doesn't exist.");
         return false;
     } 
     //Reserve!
     
     //Save reservation info to customer object
     cust.reserve(Flight.getKey(flightNumber), String.valueOf(flightNumber), proxyFlight.getPrice(id, Flight.getKey(flightNumber)));
     writeData(id, cust.getKey(), cust);
     
     //Save reservation info to resource manager
     boolean result = proxyFlight.rmReserve("flight", id, flightNumber, null);
     Trace.warn("MW::reserveFlight succeeded: " + result);
     return result;
 }
 
 // Add car reservation to this customer. 
 @Override
 public boolean reserveCar(int id, int customerId, String location) {
	// Read customer object if it exists (and read lock it).
     Customer cust = (Customer) readData(id, Customer.getKey(customerId));
     if (cust == null) {
         Trace.warn("MW::reserveCar(" + id + ", " + customerId +  ", " + location + ") failed: customer doesn't exist.");
         return false;
     } 
     //Reserve!
     
     //Save reservation info to customer object
     cust.reserve(Car.getKey(location), location, proxyCar.getPrice(id, location));
     writeData(id, cust.getKey(), cust);
     
     //Save reservation info to resource manager
     boolean result = proxyCar.rmReserve("car", id, -1, location);
     Trace.warn("MW::reserveCar succeeded: " + result);
     return result;
 }

 // Add room reservation to this customer. 
 @Override
 public boolean reserveRoom(int id, int customerId, String location) {
	// Read customer object if it exists (and read lock it).
     Customer cust = (Customer) readData(id, Customer.getKey(customerId));
     if (cust == null) {
         Trace.warn("MW::reserveRoom(" + id + ", " + customerId +  ", " + location + ") failed: customer doesn't exist.");
         return false;
     } 
     //Reserve!
     
     //Save reservation info to customer object
     cust.reserve(Room.getKey(location), location, proxyRoom.getPrice(id, location));
     writeData(id, cust.getKey(), cust);
     
     //Save reservation info to resource manager
     boolean result = proxyRoom.rmReserve("room", id, -1, location);
     Trace.warn("MW::reserveRoom succeeded: " + result);
     return result;
 }
 

 // Reserve an itinerary.
 @Override
 public boolean reserveItinerary(int id, int customerId, Vector flightNumbers, String location, boolean car, boolean room) {
	 Trace.info("MW::reserve itinerary");
	 for (Object element: flightNumbers) {
		 String flightNumberString= (String) element;
		 int flightNumber = Integer.parseInt(flightNumberString);
		 if (queryFlight(id,flightNumber)<1) {
			 return false;
		 }
	 }
	 if (car && queryCars(id, location)<1) {
		 Trace.info("MW::No free cars at " + location + " to rent.");
		 return false;
	 }
	 if (room && queryRooms(id, location)<1) {
		 Trace.info("MW::No free rooms at " + location + " to rent.");
		 return false;
	 }
	 //Reach here if reservable
	 if (car) {
		 Trace.info("MW::Reserving car at" + location);
		 reserveCar(id, customerId, location);
	 }
	 if (room) {
		 Trace.info("MW::Reserving room at" + location);
		 reserveRoom(id, customerId, location);
	 }
	 for (Object element: flightNumbers) {
		 String flightNumberString= (String) element;
		 int flightNumber = Integer.parseInt(flightNumberString);
		 Trace.info("MW::Reserving flight: " + flightNumber);
		 reserveFlight(id, customerId, flightNumber);
	 }
     return true;
 }

}

