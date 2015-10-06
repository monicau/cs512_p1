package server;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.function.Consumer;

public class TCPServiceRequest{
	private Messenger flightIn;
	private OutputStream flightOut;
	private Messenger carIn;
	private OutputStream carOut;
	private Messenger roomIn;
	private OutputStream roomOut;
	private Messenger middlewareIn;
	private OutputStream middlewareOut;

	public TCPServiceRequest(Messenger flightIn, OutputStream flightOut, 
			Messenger carIn, OutputStream carOut, 
			Messenger roomIn, OutputStream roomOut,
			Messenger middlewareIn, OutputStream middlewareOut) {
		this.flightIn = flightIn;
		this.flightOut = flightOut;
		this.carIn = carIn;
		this.carOut = carOut;
		this.roomIn = roomIn;
		this.roomOut = roomOut;
	}

	public TCPServiceRequest(Messenger middlewareIn, OutputStream middlewareOut) {
		this.flightIn = middlewareIn;
		this.flightOut = middlewareOut;
		this.carIn = middlewareIn;
		this.carOut = middlewareOut;
		this.roomIn = middlewareIn;
		this.roomOut = middlewareOut;
		this.middlewareIn = middlewareIn;
		this.middlewareOut = middlewareOut;
	}

	public static String parseResult(String raw){
		return raw.substring(raw.indexOf(':') + 1);
	}

	public void addFlight(int id, int flightNumber, int numSeats, int flightPrice, Consumer<Boolean> callback) {
		flightIn.eventHandlers.put( name -> name.startsWith("addflight"),
				msg -> callback.accept(Boolean.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(flightOut)) {
			writer.println("addflight,"+id+","+flightNumber+","+numSeats+","+flightPrice);
		}
	}
	public void deleteFlight(int id, int flightNumber, Consumer<Boolean> callback) {
		flightIn.eventHandlers.put( name -> name.startsWith("deleteflight"),
				msg -> callback.accept(Boolean.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(flightOut)) {
			writer.println("deleteflight,"+id+","+flightNumber);
		}
	}

	public void queryFlight(int id, int flightNumber, Consumer<Integer> callback) {
		flightIn.eventHandlers.put( name -> name.startsWith("queryflight"),
				msg -> callback.accept(Integer.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(flightOut)) {
			writer.println("queryflight,"+id+","+flightNumber);
		}
	}

	/**
	 * Waits for an event called "flightprice" from the messenger
	 * 
	 * @param id
	 * @param flightNumber
	 * @param callback 		Callbacks with the price
	 */
	public void queryFlightPrice(int id, int flightNumber, Consumer<Integer> callback) {
		flightIn.eventHandlers.put( name -> name.startsWith("queryflightprice"), 
				msg  -> callback.accept( Integer.valueOf( parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(flightOut)) {
			writer.println("queryflightprice,"+id+","+flightNumber);
		}
	}

	public void addCars(int id, String location, int numCars, int carPrice, Consumer<Boolean> callback) {
		carIn.eventHandlers.put( name -> name.startsWith("addcars"),
				msg -> callback.accept(Boolean.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(carOut)) {
			writer.println("addcars,"+id+","+location+","+numCars+","+carPrice);
		}
	}

	public void deleteCars(int id, String location, Consumer<Boolean> callback) {
		carIn.eventHandlers.put( name -> name.startsWith("deletecars"),
				msg -> callback.accept(Boolean.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(carOut)) {
			writer.println("deletecars,"+id+","+location);
		}
	}

	public void queryCars(int id, String location, Consumer<Integer> callback) {
		carIn.eventHandlers.put( name -> name.startsWith("querycars"),
				msg -> callback.accept(Integer.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(carOut)) {
			writer.println("querycars,"+id+","+location);
		}
	}

	/**
	 * Waits for an event called "carprice" from the messenger
	 * 
	 * @param id
	 * @param location
	 * @param callback 		Callbacks with the price
	 */
	public void queryCarPrice(int id, String location, Consumer<Integer> callback) {
		carIn.eventHandlers.put( name -> name.startsWith("carprice"), 
				msg  -> callback.accept( Integer.valueOf( msg.substring(msg.indexOf(':') + 1))));
		try(PrintWriter writer = new PrintWriter(carOut)) {
			writer.println("price,"+id+","+location);
		}
	}

	public void addRooms(int id, String location, int numRooms, int roomPrice, Consumer<Boolean> callback) {
		roomIn.eventHandlers.put( name -> name.startsWith("addrooms"),
				msg -> callback.accept(Boolean.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(roomOut)) {
			writer.println("addrooms,"+id+","+location+","+numRooms+","+roomPrice);
		}
	}

	public void deleteRooms(int id, String location, Consumer<Boolean> callback) {
		roomIn.eventHandlers.put( name -> name.startsWith("deleterooms"),
				msg -> callback.accept(Boolean.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(roomOut)) {
			writer.println("deleteroom,"+id+","+location);
		}
	}

	public void queryRooms(int id, String location, Consumer<Integer> callback) {
		roomIn.eventHandlers.put( name -> name.startsWith("queryrooms"),
				msg -> callback.accept(Integer.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(roomOut)) {
			writer.println("addrooms,"+id+","+location);
		}
	}

	/**
	 * Waits for an event called "roomPrice" from the messenger
	 * 
	 * @param id
	 * @param location
	 * @param callback 		Callbacks with the price
	 */
	public void queryRoomPrice(int id, String location, Consumer<Integer> callback) {
		roomIn.eventHandlers.put( name -> name.startsWith("roomprice"), 
				msg  -> callback.accept( Integer.valueOf( msg.substring(msg.indexOf(':') + 1))));
		try(PrintWriter writer = new PrintWriter(roomOut)) {
			writer.println("price,"+id+","+location);
		}
	}

	public void newCustomer(int id, Consumer<Integer> callback) {
		middlewareIn.eventHandlers.put( name -> name.startsWith("newcustomer"),
				msg -> callback.accept(Integer.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(middlewareOut)) {
			writer.println("newcustomer,"+id);
		}
	}

	public void newCustomerId(int id, int customerId, Consumer<Boolean> callback) {
		middlewareIn.eventHandlers.put( name -> name.startsWith("newcustomerid"),
				msg -> callback.accept(Boolean.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(middlewareOut)) {
			writer.println("newcustomerid,"+id+","+customerId);
		}
	}

	public void deleteCustomer(int id, int customerId, Consumer<Boolean> callback) {
		middlewareIn.eventHandlers.put( name -> name.startsWith("newcustomer"),
										msg -> callback.accept(Boolean.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(middlewareOut)) {
			writer.println("deletecustomer,"+id+","+customerId);
		};
	}

	public void queryCustomerInfo(int id, int customerId, Consumer<String> callback) {
		middlewareIn.eventHandlers.put( name -> name.startsWith("newcustomer"),
				msg -> callback.accept(parseResult(msg)));
		try(PrintWriter writer = new PrintWriter(middlewareOut)) {
			writer.println("querycustomerinfo,"+id+","+customerId);
		}
	}

	public void reserveFlight(int id, int customerId, int flightNumber, Consumer<Boolean> callback) {
		flightIn.eventHandlers.put( name -> name.startsWith("reserveflight"),
				msg -> callback.accept(Boolean.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(flightOut)) {
			writer.println("reserveflight,"+id+","+customerId+","+flightNumber);
		}
	}

	public void reserveCar(int id, int customerId, String location, Consumer<Boolean> callback) {
		carIn.eventHandlers.put( name -> name.startsWith("reservecar"),
				msg -> callback.accept(Boolean.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(carOut)) {
			writer.println("reservecar,"+id+","+customerId+","+location);
		}
	}

	public void reserveRoom(int id, int customerId, String location, Consumer<Boolean> callback) {
		roomIn.eventHandlers.put( name -> name.startsWith("reserveroom"),
				msg -> callback.accept(Boolean.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(roomOut)) {
			writer.println("reserveroom,"+id+","+customerId+","+location);
		}
	}

	public void reserveItinerary(int id, int customerId, Vector flightNumbers, String location, boolean car, boolean room, Consumer<Boolean> callback) {
		middlewareIn.eventHandlers.put( name -> name.startsWith("reserveitinerary"),
				msg -> callback.accept(Boolean.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(middlewareOut)) {
			writer.println("reserveitinerary,"+id+","+customerId+","+flightNumbers+","+location+","+car+","+room);
		}
	}

}
