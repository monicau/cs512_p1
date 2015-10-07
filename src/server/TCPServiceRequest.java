package server;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Vector;
import java.util.function.Consumer;

import server.ws.ResourceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TCPServiceRequest{
	protected Messenger flightIn;
	protected OutputStream flightOut;
	protected Messenger carIn;
	protected OutputStream carOut;
	protected Messenger roomIn;
	protected OutputStream roomOut;
	protected Messenger middlewareIn;
	protected OutputStream middlewareOut;

	public static Gson gg = new Gson();
	
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

	
	public void addFlight(Integer id, Integer flightNumber, Integer numSeats, Integer flightPrice, Consumer<Boolean> callback) {
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		Method[] methods = ResourceManager.class.getMethods();
		Method m = Arrays.stream(methods)
							.filter(method->method.getName().equals(methodName))
							.findAny().get();
		
		flightIn.eventHandlers.put( name -> name.startsWith(methodName),
				msg -> callback.accept(Boolean.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(flightOut)) {
			writer.println( methodName+"("+ Arrays.stream( m.getParameterTypes())
					.map(t->t.getCanonicalName())
					.reduce((x,y)->x+","+y)
					.orElse("") +")"+id+","+flightNumber+","+numSeats+","+flightPrice);
		}
	}
	public void deleteFlight(Integer id, Integer flightNumber, Consumer<Boolean> callback) {
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		flightIn.eventHandlers.put( name -> name.startsWith(methodName),
				msg -> callback.accept(Boolean.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(flightOut)) {
			writer.println( methodName+"("+ Arrays.stream( new Object(){}.getClass().getEnclosingMethod().getParameterTypes())
					.map(t->t.getCanonicalName())
					.reduce((x,y)->x+","+y)
					.orElse("") +")"+id+","+flightNumber);
		}
	}

	public void queryFlight(Integer id, Integer flightNumber, Consumer<Integer> callback) {
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		flightIn.eventHandlers.put( name -> name.startsWith(methodName),
				msg -> callback.accept(Integer.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(flightOut)) {
			writer.println( methodName+"("+ Arrays.stream( new Object(){}.getClass().getEnclosingMethod().getParameterTypes())
					.map(t->t.getCanonicalName())
					.reduce((x,y)->x+","+y)
					.orElse("") +")"+id+","+flightNumber);
		}
	}

	/**
	 * Waits for an event called "flightprice" from the messenger
	 * 
	 * @param id
	 * @param flightNumber
	 * @param callback 		Callbacks with the price
	 */
	public void queryFlightPrice(Integer id, Integer flightNumber, Consumer<Integer> callback) {
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		flightIn.eventHandlers.put( name -> name.startsWith(methodName), 
				msg  -> callback.accept( Integer.valueOf( parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(flightOut)) {
			writer.println( methodName+"("+ Arrays.stream( new Object(){}.getClass().getEnclosingMethod().getParameterTypes())
					.map(t->t.getCanonicalName())
					.reduce((x,y)->x+","+y)
					.orElse("") +")"+id+","+flightNumber);
		}
	}

	public void addCars(Integer id, String location, Integer numCars, Integer carPrice, Consumer<Boolean> callback) {
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		carIn.eventHandlers.put( name -> name.startsWith(methodName),
				msg -> callback.accept(Boolean.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(carOut)) {
			writer.println( methodName+"("+ Arrays.stream( new Object(){}.getClass().getEnclosingMethod().getParameterTypes())
					.map(t->t.getCanonicalName())
					.reduce((x,y)->x+","+y)
					.orElse("") +")"+id+","+location+","+numCars+","+carPrice);
		}
	}

	public void deleteCars(Integer id, String location, Consumer<Boolean> callback) {
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		carIn.eventHandlers.put( name -> name.startsWith(methodName),
				msg -> callback.accept(Boolean.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(carOut)) {
			writer.println( methodName+"("+ Arrays.stream( new Object(){}.getClass().getEnclosingMethod().getParameterTypes())
					.map(t->t.getCanonicalName())
					.reduce((x,y)->x+","+y)
					.orElse("") +")"+id+","+location);
		}
	}

	public void queryCars(Integer id, String location, Consumer<Integer> callback) {
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		carIn.eventHandlers.put( name -> name.startsWith(methodName),
				msg -> callback.accept(Integer.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(carOut)) {
			writer.println( methodName+"("+ Arrays.stream( new Object(){}.getClass().getEnclosingMethod().getParameterTypes())
					.map(t->t.getCanonicalName())
					.reduce((x,y)->x+","+y)
					.orElse("") +")"+id+","+location);
		}
	}

	/**
	 * Waits for an event called "carprice" from the messenger
	 * 
	 * @param id
	 * @param location
	 * @param callback 		Callbacks with the price
	 */
	public void queryCarPrice(Integer id, String location, Consumer<Integer> callback) {
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		carIn.eventHandlers.put( name -> name.startsWith(methodName), 
				msg  -> callback.accept( Integer.valueOf( msg.substring(msg.indexOf(':') + 1))));
		try(PrintWriter writer = new PrintWriter(carOut)) {
			writer.println( methodName+"("+ Arrays.stream( new Object(){}.getClass().getEnclosingMethod().getParameterTypes())
					.map(t->t.getCanonicalName())
					.reduce((x,y)->x+","+y)
					.orElse("") +")"+id+","+location);
		}
	}

	public void addRooms(Integer id, String location, Integer numRooms, Integer roomPrice, Consumer<Boolean> callback) {
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		roomIn.eventHandlers.put( name -> name.startsWith(methodName),
				msg -> callback.accept(Boolean.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(roomOut)) {
			writer.println( methodName+"("+ Arrays.stream( new Object(){}.getClass().getEnclosingMethod().getParameterTypes())
					.map(t->t.getCanonicalName())
					.reduce((x,y)->x+","+y)
					.orElse("") +")"+id+","+location+","+numRooms+","+roomPrice);
		}
	}

	public void deleteRooms(Integer id, String location, Consumer<Boolean> callback) {
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		roomIn.eventHandlers.put( name -> name.startsWith(methodName),
				msg -> callback.accept(Boolean.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(roomOut)) {
			writer.println( methodName+"("+ Arrays.stream( new Object(){}.getClass().getEnclosingMethod().getParameterTypes())
					.map(t->t.getCanonicalName())
					.reduce((x,y)->x+","+y)
					.orElse("") +")"+id+","+location);
		}
	}

	public void queryRooms(Integer id, String location, Consumer<Integer> callback) {
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		roomIn.eventHandlers.put( name -> name.startsWith(methodName),
				msg -> callback.accept(Integer.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(roomOut)) {
			writer.println( methodName+"("+ Arrays.stream( new Object(){}.getClass().getEnclosingMethod().getParameterTypes())
					.map(t->t.getCanonicalName())
					.reduce((x,y)->x+","+y)
					.orElse("") +")"+id+","+location);
		}
	}

	/**
	 * Waits for an event called "roomPrice" from the messenger
	 * 
	 * @param id
	 * @param location
	 * @param callback 		Callbacks with the price
	 */
	public void queryRoomPrice(Integer id, String location, Consumer<Integer> callback) {
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		roomIn.eventHandlers.put( name -> name.startsWith(methodName), 
				msg  -> callback.accept( Integer.valueOf( msg.substring(msg.indexOf(':') + 1))));
		try(PrintWriter writer = new PrintWriter(roomOut)) {
			writer.println( methodName+"("+ Arrays.stream( new Object(){}.getClass().getEnclosingMethod().getParameterTypes())
					.map(t->t.getCanonicalName())
					.reduce((x,y)->x+","+y)
					.orElse("") +")"+id+","+location);
		}
	}

	public void newCustomer(Integer id, Consumer<Integer> callback) {
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		middlewareIn.eventHandlers.put( name -> name.startsWith(methodName),
				msg -> callback.accept(Integer.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(middlewareOut)) {
			writer.println( methodName+"("+ Arrays.stream( new Object(){}.getClass().getEnclosingMethod().getParameterTypes())
					.map(t->t.getCanonicalName())
					.reduce((x,y)->x+","+y)
					.orElse("") +")"+id);
		}
	}

	public void newCustomerId(Integer id, Integer customerId, Consumer<Boolean> callback) {
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		middlewareIn.eventHandlers.put( name -> name.startsWith(methodName),
				msg -> callback.accept(Boolean.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(middlewareOut)) {
			writer.println( methodName+"("+ Arrays.stream( new Object(){}.getClass().getEnclosingMethod().getParameterTypes())
					.map(t->t.getCanonicalName())
					.reduce((x,y)->x+","+y)
					.orElse("") +")"+id+","+customerId);
		}
	}

	public void deleteCustomer(Integer id, Integer customerId, Consumer<Boolean> callback) {
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		middlewareIn.eventHandlers.put( name -> name.startsWith(methodName),
				msg -> callback.accept(Boolean.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(middlewareOut)) {
			writer.println( methodName+"("+ Arrays.stream( new Object(){}.getClass().getEnclosingMethod().getParameterTypes())
					.map(t->t.getCanonicalName())
					.reduce((x,y)->x+","+y)
					.orElse("") +")"+id+","+customerId);
		}
	}

	public void queryCustomerInfo(Integer id, Integer customerId, Consumer<String> callback) {
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		middlewareIn.eventHandlers.put( name -> name.startsWith(methodName),
				msg -> callback.accept(parseResult(msg)));
		try(PrintWriter writer = new PrintWriter(middlewareOut)) {
			writer.println( methodName+"("+ Arrays.stream( new Object(){}.getClass().getEnclosingMethod().getParameterTypes())
					.map(t->t.getCanonicalName())
					.reduce((x,y)->x+","+y)
					.orElse("") +")"+id+","+customerId);
		}
	}

	public void reserveFlight(Integer id, Integer customerId, Integer flightNumber, Consumer<Boolean> callback) {
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		flightIn.eventHandlers.put( name -> name.startsWith(methodName),
				msg -> callback.accept(Boolean.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(flightOut)) {
			writer.println( methodName+"("+ Arrays.stream( new Object(){}.getClass().getEnclosingMethod().getParameterTypes())
					.map(t->t.getCanonicalName())
					.reduce((x,y)->x+","+y)
					.orElse("") +")"+id+","+customerId+","+flightNumber);

		}
	}

	public void reserveCar(Integer id, Integer customerId, String location, Consumer<Boolean> callback) {
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		carIn.eventHandlers.put( name -> name.startsWith(methodName),
				msg -> callback.accept(Boolean.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(carOut)) {
			writer.println( methodName+"("+ Arrays.stream( new Object(){}.getClass().getEnclosingMethod().getParameterTypes())
					.map(t->t.getCanonicalName())
					.reduce((x,y)->x+","+y)
					.orElse("") +")"+id+","+customerId+","+location);
		}
	}

	public void reserveRoom(Integer id, Integer customerId, String location, Consumer<Boolean> callback) {
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		roomIn.eventHandlers.put( name -> name.startsWith(methodName),
				msg -> callback.accept(Boolean.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(roomOut)) {
			writer.println( methodName+"("+ Arrays.stream( new Object(){}.getClass().getEnclosingMethod().getParameterTypes())
					.map(t->t.getCanonicalName())
					.reduce((x,y)->x+","+y)
					.orElse("") +")"+id+","+customerId+","+location);
		}
	}

	public void reserveItinerary(Integer id, Integer customerId, Vector flightNumbers, String location, Boolean car, Boolean room, Consumer<Boolean> callback) {
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		middlewareIn.eventHandlers.put( name -> name.startsWith(methodName),
				msg -> callback.accept(Boolean.valueOf(parseResult(msg))));
		try(PrintWriter writer = new PrintWriter(middlewareOut)) {
			writer.println( methodName+"("+ Arrays.stream( new Object(){}.getClass().getEnclosingMethod().getParameterTypes())
					.map(t->t.getCanonicalName())
					.reduce((x,y)->x+","+y)
					.orElse("") +")"+id+","+customerId+","+flightNumbers+","+location+","+car+","+room);
		}
	}

	public static String testING(String one, int two, float three){
		return new Object(){}.getClass().getEnclosingMethod().getName();
	}
	
	public static void main(String[] args) {
		Object[] os = new Object[]{new Integer(1),2,3f,"Hello", true};
		String json = gg.toJson(os);
		System.out.println(json);
		Object[] fromJson = gg.fromJson(json, Object[].class);
		for (Object object : fromJson) {
			System.out.println(object);
		}
		Parameter[] parameters = Arrays.stream(TCPServiceRequest.class.getMethods()).filter(m->m.getName().equals("testING")).findFirst().get().getParameters();
		for (Parameter parameter : parameters) {
			System.out.println(parameter.getType());
		}
		System.out.println();
		Double d = new Double(1.0);
		double dd = (double) d;
		System.out.println(int.class.cast(dd));
	}
}
