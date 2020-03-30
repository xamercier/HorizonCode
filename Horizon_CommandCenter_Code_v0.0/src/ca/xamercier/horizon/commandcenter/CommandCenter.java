package ca.xamercier.horizon.commandcenter;

import ca.xamercier.horizon.commandcenter.communicationsystem.Server;

public class CommandCenter {

	public static boolean isRocketConnected = false;
	static Server server;

    
    /*
    static Logger logger = Logger.getLogger("MyLog");  
    
    static public Logger getLogger() {
		return logger;
	}
	FileHandler fh;  
    */
    
	CommandCenter(){
		/*
        try {
			fh = new FileHandler("C:/Users/xamer/Desktop/log.txt");
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
        logger.addHandler(fh);
        //SimpleFormatter formatter = new SimpleFormatter();  
        //fh.setFormatter(formatter);  
		*/
		server = new Server();
	}
	

	public static Server getServer() {
		return server;
	}


	@SuppressWarnings("static-access")
	public void setServer(Server server) {
		this.server = server;
	}

}
