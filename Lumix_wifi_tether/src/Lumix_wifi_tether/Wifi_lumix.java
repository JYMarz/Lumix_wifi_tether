package Lumix_wifi_tether;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;
import java.net.SocketException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.StringUtils;
import org.w3c.dom.*;
import javax.xml.parsers.*;

public class Wifi_lumix {


    private int udpPort;
    private String cameraIp;
	public Wifi_lumix(String camIp,int udp,int udpinit) throws UnknownHostException, SocketException, IOException, InterruptedException  {

		udpPort=udp;
		cameraIp=camIp;


    	String cmd1="GET /00AEFA2E107E/Server0/ddd HTTP/1.1\r\nHost: "+cameraIp+":"+String.valueOf(udpinit)+" \r\nUser-Agent: Panasonic Android/1 DM-CP\r\n\r\n";
    	System.out.println(SendRaw(cmd1,udpinit));
//		TimeUnit.SECONDS.sleep(1);
    	String cmd2="GET /cam.cgi?mode=acctrl&type=req_acc&value=4D454930-0100-1000-8001-024D00026C8C&value2=SM-930F HTTP/1.1\r\nHost: 192.168.54.1\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient\r\n\r\n"; 
    	System.out.println(SendRaw(cmd2,udp));
//		TimeUnit.SECONDS.sleep(1);
    	String cmd3= "GET /cam.cgi?mode=camcmd&value=recmode HTTP/1.1\r\nHost: "+cameraIp+"\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient\r\n\r\n"; 
    	System.out.println(SendRaw(cmd3,udp));
//		TimeUnit.SECONDS.sleep(1);
         try (Socket socket = new Socket(cameraIp, udpPort)) {

            try (PrintWriter wtr = new PrintWriter(socket.getOutputStream())) {
            	System.out.println("connected");
// start video flux            	
 //           	String cmd="GET /cam.cgi?mode=startstream&value=49199 HTTP/1.1\r\n"
  //              		+ "Host: 192.168.54.1\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient\r\n\r\n";
//            	byte[] bytes = StringUtils.getBytesUtf8(cmd);
            	//String utf8bytes = StringUtils.newStringUtf8(bytes);
//            	System.out.println(utf8bytes);
//                wtr.print(utf8bytes);
//                wtr.flush();
                socket.shutdownOutput();

                String outStr;
                String outStr2="";

                try (BufferedReader bufRead = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()))) {

                    while ((outStr = bufRead.readLine()) != null) {
                    	if (outStr.length() >0) {
                    	if (outStr.charAt(0)=='<') {
                    	outStr2 += outStr;		
                    	}
                    	}
                        
                    }
//                    System.out.println(outStr2);

                    socket.shutdownInput();
                }
            }
        }
    }
    public String SendCom( String command) throws UnknownHostException, SocketException, IOException  {
		try (Socket socket = new Socket(cameraIp, udpPort)) {

            try (PrintWriter wtr = new PrintWriter(socket.getOutputStream())) {
//            	System.out.println("connected");
// start video flux            	
            	String cmd="GET /cam.cgi?"+command+" HTTP/1.1\r\n"
                		+ "Host: "+cameraIp+"\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient\r\n\r\n";
            	byte[] bytes = StringUtils.getBytesUtf8(cmd);
            	String utf8bytes = StringUtils.newStringUtf8(bytes);
//            	System.out.println(utf8bytes);
                wtr.print(utf8bytes);
                wtr.flush();
                socket.shutdownOutput();

                String outStr;
                String outStr2="";

                try (BufferedReader bufRead = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()))) {

                    while ((outStr = bufRead.readLine()) != null) {
//                    	System.out.println(outStr);
                    	if (outStr.length() >0) {
                    	if (outStr.charAt(0)=='<') {
                    	outStr2 += outStr;		
                    	}
                    	}
                        
                    }
//                    System.out.println(outStr);

                    socket.shutdownInput();
                    return outStr2;
                }
            }
        }
    	
    }
    public String SendRaw( String cmd, int udp_initial) throws UnknownHostException, SocketException, IOException, InterruptedException   {
		try (Socket socket = new Socket(cameraIp, udp_initial)) {

            try (PrintWriter wtr = new PrintWriter(socket.getOutputStream())) {
            	System.out.println("connected");
// start video flux            	

            	byte[] bytes = StringUtils.getBytesUtf8(cmd);
            	String utf8bytes = StringUtils.newStringUtf8(bytes);
            	System.out.println(utf8bytes);
                wtr.print(utf8bytes);
                wtr.flush();
                socket.shutdownOutput();

                String outStr;
                String outStr2="";

                try (BufferedReader bufRead = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()))) {

                    while ((outStr = bufRead.readLine()) != null) {
//                    	System.out.println(outStr);
                    	if (outStr.length() >0) {
                    	if (outStr.charAt(0)=='<') {
                    	outStr2 += outStr;		
                    	}
                    	}
                        
                    }
//                    System.out.println(outStr);

                    socket.shutdownInput();
                    return outStr2;
                }
            }
        }
    	
    }
}