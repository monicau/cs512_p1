package main;

import java.io.File;
import java.io.PrintWriter;

import org.apache.catalina.startup.Tomcat;


public class Main {

    public static void main(String[] args) 
    throws Exception {
    
        if (args.length != 3 && args.length != 9) {
            System.out.println(
                "Usage: java Main <service-name> <service-port> <deploy-dir> [<rm1-host> <rm1-port> <rm2-host> <rm2-port> <rm3-host> <rm3-port>]");
            System.exit(-1);
        }
    
        String serviceName = args[0];
        int port = Integer.parseInt(args[1]);
        String deployDir = args[2];
        
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.setBaseDir(deployDir);
        tomcat.enableNaming();
        
        tomcat.getHost().setAppBase(deployDir);
        tomcat.getHost().setDeployOnStartup(true);
        tomcat.getHost().setAutoDeploy(true);

        //tomcat.addWebapp("", new File(deployDir).getAbsolutePath());

        tomcat.addWebapp("/" + serviceName, 
                new File(deployDir + "/" + serviceName).getAbsolutePath());
        
        
        if (serviceName.equals("mw")) {
            //Add environment entries to web.xml to create rm proxies for middleware
	        String rmHost1 = args[3];
	        int rmPort1 = Integer.parseInt(args[4]);
	        String rmHost2 = args[5];
	        int rmPort2 = Integer.parseInt(args[6]);
	        String rmHost3 = args[7];
	        int rmPort3 = Integer.parseInt(args[8]);
	        
	        PrintWriter writer = new PrintWriter("rm.txt", "UTF-8");
	        writer.println(rmHost1);
	        writer.println(rmPort1);
	        writer.println(rmHost2);
	        writer.println(rmPort2);
	        writer.println(rmHost3);
	        writer.println(rmPort3);
	        writer.close();
        }
        
        tomcat.start();
        tomcat.getServer().await();
    }
    
}
