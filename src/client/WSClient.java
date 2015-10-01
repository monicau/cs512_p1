package client;

import java.net.URL;
import java.net.MalformedURLException;

import server.ws.ResourceManager;


public class WSClient {

//    ResourceManagerImplService service;
   	MiddlewareImplService service;
    
    ResourceManager proxy;
//   	Middleware proxy;
    
    public WSClient(String serviceName, String serviceHost, int servicePort) 
    throws MalformedURLException {
    
        URL wsdlLocation = new URL("http", serviceHost, servicePort, 
                "/" + serviceName + "/service?wsdl");
                
        service = new MiddlewareImplService(wsdlLocation);
        
        proxy = service.getMiddlewareImplPort();
    }

}
