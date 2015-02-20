/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sdiappgui;
import java.awt.EventQueue;
import java.io.*;
import java.net.*;
import java.security.*;
import java.util.Scanner;

import javax.net.ssl.*;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
/**
 *
 * Adapted from Dr Herong Yang JDk tutorials
 */
public class sslsimpletestserver1 extends Thread {
    String message,mess;
    public JTextArea area;

    public sslsimpletestserver1(JTextArea area) {
        this.area = area;
    }

    
    
  //  public static void main(String [] args){
    @Override
    public void run(){
    
   
	
		      char ksPass[] = "sn99999".toCharArray();
		      char ctPass[] = "sn99999".toCharArray();
		  //    char[] password = getPassword();
		      java.io.FileInputStream fis = null;
		      
		          
		      try {
		    	  fis = new java.io.FileInputStream("deepou.jks");
		          KeyStore ks = KeyStore.getInstance("JKS");
		         ks.load(fis, ksPass);
		         KeyManagerFactory kmf = 
		         KeyManagerFactory.getInstance("SunX509");
		         kmf.init(ks, ctPass);
		         SSLContext sc = SSLContext.getInstance("TLS");
		         sc.init(kmf.getKeyManagers(), null, null);
		         SSLServerSocketFactory ssf = sc.getServerSocketFactory();
		         SSLServerSocket s 
		            = (SSLServerSocket) ssf.createServerSocket(8887);
		         printServerSocketInfo(s);
                        while(true){
		         SSLSocket c = (SSLSocket) s.accept();
		         printSocketInfo(c);
		        DataOutputStream outToClient = new DataOutputStream(c.getOutputStream());
		         Scanner r = new Scanner(c.getInputStream());
		        String m = "Welcome to my server";
		        outToClient.writeBytes(m+'\n');
		                              
                         mess=r.nextLine();
                   SwingUtilities.invokeLater(new Runnable(){
                       @Override
                            public void run(){
                        //   area.setText("");
                       area.append(mess+'\n');
                       
                            }
                   });
		       // while ((m=r.readLine())!= null) {
		           // if (m.equals(".")) break;
		            //char[] a = m.toCharArray();
		            //int n = a.length;
		          // for (int i=0; i<n/2; i++) {
		            //   char t = a[i];
		              // a[i] = a[n-1-i];
		               //a[n-i-1] = t;
                              // message=a.toString();
                             System.out.println("received from client"+mess);
		           
		            outToClient.flush();
		   //     }
		    //     w.close();
		         r.close();
		        // c.close();
		      //   s.close();
		       
                      }
                      }catch (Exception e) {
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
 private static void printServerSocketInfo(SSLServerSocket s) {
   System.out.println("Server socket class: "+s.getClass());
   System.out.println("   Socker address = "
		         +s.getInetAddress().toString());
   System.out.println("   Socker port = "
		         +s.getLocalPort());
		     
}	   

    
}
