
package server;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.10
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ResourceManager", targetNamespace = "http://ws.server/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ResourceManager {


    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "deleteFlight", targetNamespace = "http://ws.server/", className = "server.DeleteFlight")
    @ResponseWrapper(localName = "deleteFlightResponse", targetNamespace = "http://ws.server/", className = "server.DeleteFlightResponse")
    @Action(input = "http://ws.server/ResourceManager/deleteFlightRequest", output = "http://ws.server/ResourceManager/deleteFlightResponse")
    public boolean deleteFlight(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "queryFlight", targetNamespace = "http://ws.server/", className = "server.QueryFlight")
    @ResponseWrapper(localName = "queryFlightResponse", targetNamespace = "http://ws.server/", className = "server.QueryFlightResponse")
    @Action(input = "http://ws.server/ResourceManager/queryFlightRequest", output = "http://ws.server/ResourceManager/queryFlightResponse")
    public int queryFlight(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "queryFlightPrice", targetNamespace = "http://ws.server/", className = "server.QueryFlightPrice")
    @ResponseWrapper(localName = "queryFlightPriceResponse", targetNamespace = "http://ws.server/", className = "server.QueryFlightPriceResponse")
    @Action(input = "http://ws.server/ResourceManager/queryFlightPriceRequest", output = "http://ws.server/ResourceManager/queryFlightPriceResponse")
    public int queryFlightPrice(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "addCars", targetNamespace = "http://ws.server/", className = "server.AddCars")
    @ResponseWrapper(localName = "addCarsResponse", targetNamespace = "http://ws.server/", className = "server.AddCarsResponse")
    @Action(input = "http://ws.server/ResourceManager/addCarsRequest", output = "http://ws.server/ResourceManager/addCarsResponse")
    public boolean addCars(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        int arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        int arg3);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "deleteCars", targetNamespace = "http://ws.server/", className = "server.DeleteCars")
    @ResponseWrapper(localName = "deleteCarsResponse", targetNamespace = "http://ws.server/", className = "server.DeleteCarsResponse")
    @Action(input = "http://ws.server/ResourceManager/deleteCarsRequest", output = "http://ws.server/ResourceManager/deleteCarsResponse")
    public boolean deleteCars(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "queryCars", targetNamespace = "http://ws.server/", className = "server.QueryCars")
    @ResponseWrapper(localName = "queryCarsResponse", targetNamespace = "http://ws.server/", className = "server.QueryCarsResponse")
    @Action(input = "http://ws.server/ResourceManager/queryCarsRequest", output = "http://ws.server/ResourceManager/queryCarsResponse")
    public int queryCars(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "queryCarsPrice", targetNamespace = "http://ws.server/", className = "server.QueryCarsPrice")
    @ResponseWrapper(localName = "queryCarsPriceResponse", targetNamespace = "http://ws.server/", className = "server.QueryCarsPriceResponse")
    @Action(input = "http://ws.server/ResourceManager/queryCarsPriceRequest", output = "http://ws.server/ResourceManager/queryCarsPriceResponse")
    public int queryCarsPrice(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

    /**
     * 
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "addRooms", targetNamespace = "http://ws.server/", className = "server.AddRooms")
    @ResponseWrapper(localName = "addRoomsResponse", targetNamespace = "http://ws.server/", className = "server.AddRoomsResponse")
    @Action(input = "http://ws.server/ResourceManager/addRoomsRequest", output = "http://ws.server/ResourceManager/addRoomsResponse")
    public boolean addRooms(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        int arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        int arg3);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "deleteRooms", targetNamespace = "http://ws.server/", className = "server.DeleteRooms")
    @ResponseWrapper(localName = "deleteRoomsResponse", targetNamespace = "http://ws.server/", className = "server.DeleteRoomsResponse")
    @Action(input = "http://ws.server/ResourceManager/deleteRoomsRequest", output = "http://ws.server/ResourceManager/deleteRoomsResponse")
    public boolean deleteRooms(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "queryRooms", targetNamespace = "http://ws.server/", className = "server.QueryRooms")
    @ResponseWrapper(localName = "queryRoomsResponse", targetNamespace = "http://ws.server/", className = "server.QueryRoomsResponse")
    @Action(input = "http://ws.server/ResourceManager/queryRoomsRequest", output = "http://ws.server/ResourceManager/queryRoomsResponse")
    public int queryRooms(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "queryRoomsPrice", targetNamespace = "http://ws.server/", className = "server.QueryRoomsPrice")
    @ResponseWrapper(localName = "queryRoomsPriceResponse", targetNamespace = "http://ws.server/", className = "server.QueryRoomsPriceResponse")
    @Action(input = "http://ws.server/ResourceManager/queryRoomsPriceRequest", output = "http://ws.server/ResourceManager/queryRoomsPriceResponse")
    public int queryRoomsPrice(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

    /**
     * 
     * @param arg0
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "newCustomer", targetNamespace = "http://ws.server/", className = "server.NewCustomer")
    @ResponseWrapper(localName = "newCustomerResponse", targetNamespace = "http://ws.server/", className = "server.NewCustomerResponse")
    @Action(input = "http://ws.server/ResourceManager/newCustomerRequest", output = "http://ws.server/ResourceManager/newCustomerResponse")
    public int newCustomer(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "newCustomerId", targetNamespace = "http://ws.server/", className = "server.NewCustomerId")
    @ResponseWrapper(localName = "newCustomerIdResponse", targetNamespace = "http://ws.server/", className = "server.NewCustomerIdResponse")
    @Action(input = "http://ws.server/ResourceManager/newCustomerIdRequest", output = "http://ws.server/ResourceManager/newCustomerIdResponse")
    public boolean newCustomerId(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "queryCustomerInfo", targetNamespace = "http://ws.server/", className = "server.QueryCustomerInfo")
    @ResponseWrapper(localName = "queryCustomerInfoResponse", targetNamespace = "http://ws.server/", className = "server.QueryCustomerInfoResponse")
    @Action(input = "http://ws.server/ResourceManager/queryCustomerInfoRequest", output = "http://ws.server/ResourceManager/queryCustomerInfoResponse")
    public String queryCustomerInfo(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "rmUnreserve", targetNamespace = "http://ws.server/", className = "server.RmUnreserve")
    @ResponseWrapper(localName = "rmUnreserveResponse", targetNamespace = "http://ws.server/", className = "server.RmUnreserveResponse")
    @Action(input = "http://ws.server/ResourceManager/rmUnreserveRequest", output = "http://ws.server/ResourceManager/rmUnreserveResponse")
    public boolean rmUnreserve(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        int arg2);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "reserveFlight", targetNamespace = "http://ws.server/", className = "server.ReserveFlight")
    @ResponseWrapper(localName = "reserveFlightResponse", targetNamespace = "http://ws.server/", className = "server.ReserveFlightResponse")
    @Action(input = "http://ws.server/ResourceManager/reserveFlightRequest", output = "http://ws.server/ResourceManager/reserveFlightResponse")
    public boolean reserveFlight(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        int arg2);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "reserveCar", targetNamespace = "http://ws.server/", className = "server.ReserveCar")
    @ResponseWrapper(localName = "reserveCarResponse", targetNamespace = "http://ws.server/", className = "server.ReserveCarResponse")
    @Action(input = "http://ws.server/ResourceManager/reserveCarRequest", output = "http://ws.server/ResourceManager/reserveCarResponse")
    public boolean reserveCar(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "reserveRoom", targetNamespace = "http://ws.server/", className = "server.ReserveRoom")
    @ResponseWrapper(localName = "reserveRoomResponse", targetNamespace = "http://ws.server/", className = "server.ReserveRoomResponse")
    @Action(input = "http://ws.server/ResourceManager/reserveRoomRequest", output = "http://ws.server/ResourceManager/reserveRoomResponse")
    public boolean reserveRoom(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2);

    /**
     * 
     * @param arg3
     * @param arg2
     * @param arg5
     * @param arg4
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "reserveItinerary", targetNamespace = "http://ws.server/", className = "server.ReserveItinerary")
    @ResponseWrapper(localName = "reserveItineraryResponse", targetNamespace = "http://ws.server/", className = "server.ReserveItineraryResponse")
    @Action(input = "http://ws.server/ResourceManager/reserveItineraryRequest", output = "http://ws.server/ResourceManager/reserveItineraryResponse")
    public boolean reserveItinerary(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        List<Object> arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        String arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        boolean arg4,
        @WebParam(name = "arg5", targetNamespace = "")
        boolean arg5);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "deleteCustomer", targetNamespace = "http://ws.server/", className = "server.DeleteCustomer")
    @ResponseWrapper(localName = "deleteCustomerResponse", targetNamespace = "http://ws.server/", className = "server.DeleteCustomerResponse")
    @Action(input = "http://ws.server/ResourceManager/deleteCustomerRequest", output = "http://ws.server/ResourceManager/deleteCustomerResponse")
    public boolean deleteCustomer(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "reserveItem", targetNamespace = "http://ws.server/", className = "server.ReserveItem")
    @ResponseWrapper(localName = "reserveItemResponse", targetNamespace = "http://ws.server/", className = "server.ReserveItemResponse")
    @Action(input = "http://ws.server/ResourceManager/reserveItemRequest", output = "http://ws.server/ResourceManager/reserveItemResponse")
    public boolean reserveItem(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        int arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        String arg3);

    /**
     * 
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "addFlight", targetNamespace = "http://ws.server/", className = "server.AddFlight")
    @ResponseWrapper(localName = "addFlightResponse", targetNamespace = "http://ws.server/", className = "server.AddFlightResponse")
    @Action(input = "http://ws.server/ResourceManager/addFlightRequest", output = "http://ws.server/ResourceManager/addFlightResponse")
    public boolean addFlight(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        int arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        int arg3);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getPrice", targetNamespace = "http://ws.server/", className = "server.GetPrice")
    @ResponseWrapper(localName = "getPriceResponse", targetNamespace = "http://ws.server/", className = "server.GetPriceResponse")
    @Action(input = "http://ws.server/ResourceManager/getPriceRequest", output = "http://ws.server/ResourceManager/getPriceResponse")
    public int getPrice(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

}
