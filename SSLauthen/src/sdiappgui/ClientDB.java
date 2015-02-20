package sdiappgui;

import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.JTable;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

public class ClientDB extends Thread {

    private Socket clientSocket;
    private String REQUEST_STR = "CLNT";
    private JTable tbl;
    private String[] tblColNames = {"IP", "Name", "Email"};
    private String ip;

    public ClientDB(JTable tbl) {
        this.tbl = tbl;
       
    }

    @Override
    public void run() {
        try {
            
            /*
             * Put Server Ip address
             */
            clientSocket = new Socket("172.22.35.131", 9000);
            
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
            dos.writeBytes(REQUEST_STR + "\n");
            System.out.println("Sending request");

            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            String data[][] = (String[][]) ois.readObject();
            System.out.println("Data received");

            clientSocket.close();
            
           
            tbl.setModel(new DataTableModel(data, tblColNames));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
