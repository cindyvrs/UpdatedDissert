/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sdiappgui;
import java.io.*;
import java.util.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.mail.Folder;
//import javax.mail.Message;
//import javax.mail.MessagingException;
import javax.net.ssl.*;
/**
 *
 *  Adapted from Dr Herong Yang JDk tutorials
 */
public class sslclient extends Thread{
     String m,user,rec,ip;
     String recipient;
    // String messages;
//static String spoofedFolder = null;
     
    public sslclient (String ip,String user,String recipient ) {
        this.ip = ip;
        this.user=user;
        this.recipient=recipient;
    }
    
  /*   public void appendMessages(String messages) throws MessagingException {
            
            this.messages = messages;
        }*/
     
    @Override
	public void run(){
        
       
     /*
     * @param messages
     * @throws MessagingException
     */
    
     
             
        
	//	System.setProperty("javax.net.ssl.trustStore", "public.jks");
		 Properties systemProps = System.getProperties();
		    systemProps.put( "javax.net.ssl.trustStore", "public.jks");
		    System.setProperties(systemProps);
        System.setProperty("javax.net.ssl.trustStorePassword", "sn99999");
        
  
        
     //  System.setProperty("java.protocol.handler.pkgs","com.sun.net.ssl.internal.www.protocol");
	    //  BufferedReader in = new BufferedReader(
	      //   new InputStreamReader(System.in));
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			   //get current date time with Date()
			   Date date = new Date();
			 //  System.out.println(dateFormat.format(date));
		 
			   //get current date time with Calendar()
			   Calendar cal = Calendar.getInstance();
 // String port = field2.getText();
  //int pnum = Integer.parseInt(port);
  //String mess = area.getText();
  m="This is a confirmation message sent to authenticate the message sent by "
          +user+" to "+recipient+ "on" +dateFormat.format(date);
	      PrintStream out = System.out;
	      SSLSocketFactory f = 
	         (SSLSocketFactory) SSLSocketFactory.getDefault();
	      try {
	         SSLSocket c =
	         (SSLSocket) f.createSocket(ip, 8887);
                  //(SSLSocket) f.createSocket("192.168.5.1", 8887);
	         printSocketInfo(c);
	         c.startHandshake();
	         DataOutputStream outToServer = new DataOutputStream(c.getOutputStream());
	         Scanner r = new Scanner(
	          c.getInputStream());
	      //   String m = null;
                 
	          rec=r.nextLine();
                 System.out.println("received from server"+rec);
                    
                    
                    
	        //    m = in.readLine();
	            outToServer.writeBytes(m+'\n');
                    //outToServer.writeObject(JFrame);
                    System.out.println("giving server"+m);
	         //   w.newLine();
	           outToServer.flush();
	            
	   //     }
	        outToServer.close();
	         r.close();
	         c.close();
	      } catch (IOException e) {
	         System.err.println(e.toString());
	      }
	   }
    
   
    
    private static void printSocketInfo(SSLSocket s) {
      System.out.println("Socket class: "+s.getClass());
      System.out.println("   Local socket address = "
	         +s.getLocalSocketAddress().toString());
      System.out.println("   Local address = "
	         +s.getLocalAddress().toString());
      System.out.println("   Local port = "+s.getLocalPort());
	      SSLSession ss = s.getSession();
      System.out.println("   Cipher suite = "+ss.getCipherSuite());
      System.out.println("   Protocol = "+ss.getProtocol());
	   }
    
}
