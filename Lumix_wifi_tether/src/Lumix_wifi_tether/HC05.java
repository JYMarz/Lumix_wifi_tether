package Lumix_wifi_tether;
import java.io.IOException;
import java.util.Vector;
import javax.bluetooth.*;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

import org.apache.commons.codec.binary.StringUtils;

import java.io.InputStream;
import java.io.OutputStream;
public class HC05 {
	   public static String blueAd;
	   public static String hc05Url;
	   public static StreamConnection streamConnection;
	   public static OutputStream os;
	   public static InputStream is;
	   public static int return_code=-1;
	   public static boolean locblue;
	   public  HC05(String blueAdress) {
		   blueAd=blueAdress;
		   System.out.println(blueAdress);
		   hc05Url=
		    		"btspp://"+blueAd+":1;authenticate=false;encrypt=false;master=false"; //Replace this with your bluetooth URL
	      try  {
	    	   System.out.println("Connect to HC-05.");
	    	   go();
	    	   locblue=true;
	       } catch (Exception e) {
	    	   //
	    	   e.printStackTrace();
	    	   locblue=false;
	    	   
	       }
	    	
	       } 
	    	   
	
	       
	   
	    private static int  go() throws Exception {
	    	System.out.println("Trying to establish connection " );
	    	streamConnection = (StreamConnection) 	Connector.open(hc05Url);
	    	System.out.println("I am here " );
	    	os = streamConnection.openOutputStream();
	    	 is = streamConnection.openInputStream();
	    	os.write("S".getBytes()); //'1' means ON and '0' means OFF
//	    	os.close();
	    	byte[] b = new byte[200];
	    	Thread.sleep(200);

	    	return_code=  is.read(b,0,is.available()
	    			);
//	    	is.close();
//	    	streamConnection.close();
	    	System.out.println("received " + new String(b));
	    	return return_code;
	    	}
	    
	    public static String send(String cmd) throws InterruptedException {
	    	try {
//		    	os = streamConnection.openOutputStream();
//		    	is = streamConnection.openInputStream();
		    	byte[] bytes = StringUtils.getBytesUtf8(cmd);
		    	os.write(bytes); //'1' means ON and '0' means OFF
//		    	os.close();
		    	if (cmd.equals("G\r\n")) {
		    	byte[] b = new byte[200];
		    	Thread.sleep(200); //pas sûr
		    	return_code=  is.read(b,0,is.available());
		    	String retval= new String(b);
//		    	System.out.println(retval);
//		    	is.close();
		    	return retval;
		    	}

		    	else {
//		    		is.close();
		    		return "";
		    	}

	    		
	    	} catch (IOException e) {
	    		
	    		 e.printStackTrace();
	    		 locblue=false;
	    		 return "";
	    	}
	    }
	    public static boolean getblue() {
	    	return locblue;
	    }

}
