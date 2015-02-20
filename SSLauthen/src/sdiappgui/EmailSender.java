/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sdiappgui;
import com.sun.mail.util.MailSSLSocketFactory;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.swing.JOptionPane;



       
                
 



    /**
 
 * Adapted from Netbeans forum at http://wiki.netbeans.org/SendMailUsingJavaFromNetBeanshttp://wiki.netbeans.
 */
public class EmailSender {
    private String to;
    private String message;
    private String user;
    private String from;
    private String auth;
    private String smtpServ;
    private String subject;
    private String port;
    private String password;
    private String attachPath;
    private String name;
    private String mess;
    private String authenMessage;
    //private String [] ip;
    private Message[] msgs; 
    static boolean expunge = false;
    

    public EmailSender(String smtpServ, String user,String password,String auth,String from,String to,String message,String subject,String attachPath,String name,String mess) {
        this.user=user;
        this.from = from;
        this.auth = auth;
        this.smtpServ = smtpServ;
     //  this.port=port;
        this.password=password;
        this.to=to;
        this.message=message;
        this.subject=subject;
        this.attachPath= attachPath;
        this.name = name;
        this.mess = mess;
        
        
       
    }


    public int sendEmail(){
        try
        {
            Properties props = System.getProperties();
             
              props.put("mail.transport.protocol", "smtp" );
              props.put("mail.smtp.starttls.enable","true" );
              props.put("mail.smtp.host",smtpServ);  // specify which host to send to. Important if we are using Transport.send
              props.put("mail.smtp.auth", "true" );
              props.put("mail.smtp.ssl.trust", smtpServ);
              props.put("mail.smtp.port",587);
              
              props.put("mail.smtp.socketFactory.port", "25");
              props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
              props.put("mail.smtp.socketFactory.fallback", "true");
              
              MailSSLSocketFactory sf = null;
              try {
                    sf = new MailSSLSocketFactory();
                  } catch (GeneralSecurityException e1) {
              // TODO Auto-generated catch block
              e1.printStackTrace();
        }
              
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.socketFactory", sf);
        
              
              Authenticator authen = new SMTPAuthenticator(from, password,true);
              Session session = Session.getInstance(props, authen);
              
              DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			   //get current date time with Date()
			   Date date = new Date();
         
              
              Message msg = new MimeMessage(session);
             
              msg.setFrom(new InternetAddress(from));
              msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
              msg.setSubject(subject);
              
              MimeBodyPart messageBodyPart = new MimeBodyPart();
              messageBodyPart.setText(to);
              Multipart multipart =  new MimeMultipart();
              multipart.addBodyPart(messageBodyPart);
              
              messageBodyPart = new MimeBodyPart();
              DataSource source =  new FileDataSource(attachPath);
              messageBodyPart.setDataHandler(new DataHandler(source));
              messageBodyPart.setFileName(name);
              multipart.addBodyPart(messageBodyPart);
              msg.setContent(multipart);
              
               if(!mess.equalsIgnoreCase(authenMessage))
              

              //msg.setText(message);
              Transport.send(msg);

               if(!mess.equalsIgnoreCase(authenMessage))
              
              javax.swing. JOptionPane.showMessageDialog(null,"Alert you have received a spoofed email","Alert",JOptionPane.INFORMATION_MESSAGE); 
               
               else
                   
                javax.swing.JOptionPane.showMessageDialog(null,"Authenticated Message has been received","Notification",JOptionPane.INFORMATION_MESSAGE);
              
              //folder.close(expunge);
              //store.close();
              
              System.out.println("Message sent to"+to+" OK." );
              
  
              Store store = session.getStore("pop3");
              store.connect(smtpServ, user, password);
              
              Folder folder = store.getFolder("INBOX");
              folder.open(Folder.READ_WRITE);

              Folder sffolder = store.getFolder("Spoof Folder");
              if (!sffolder.exists())
              sffolder.create(Folder.HOLDS_MESSAGES);
              
              authenMessage = "This is a confirmation message sent to authenticate the message sent by "
              +user+" to "+to+ "on" +dateFormat.format(date);
              
              if(!mess.equalsIgnoreCase(authenMessage)){
              
             // if (!from.equals(ip))
                  
                   //Get the message objects to copy
                msgs = folder.getMessages();
                System.out.println("Moving " + msgs.length + " messages");

              if (msgs.length != 0) {
                     folder.copyMessages(msgs, sffolder);
                        folder.setFlags(msgs, new Flags(Flags.Flag.DELETED), true);
                }
              }

        // Dump out the Flags of the moved messages, to insure that
        // all got deleted
                for (int i = 0; i < msgs.length; i++) {
            if (!msgs[i].isSet(Flags.Flag.DELETED))
            System.out.println("Message # " + msgs[i] + " not deleted");
                
            }  
            
            Folder authenfolder = store.getFolder("Authenticated Message Filter");
              if (!authenfolder.exists())
              authenfolder.create(Folder.HOLDS_MESSAGES);
              
              
              if(mess.equalsIgnoreCase(authenMessage)){
              
             // if (!from.equals(ip))
                  
                   //Get the message objects to copy
                msgs = folder.getMessages();
                System.out.println("Moving " + msgs.length + " messages");

                if (msgs.length != 0) {
                     folder.copyMessages(msgs, authenfolder);
                        folder.setFlags(msgs, new Flags(Flags.Flag.DELETED), true);
                }
              }
        // Dump out the Flags of the moved messages, to insure that
        // all got deleted
            for (int i = 0; i < msgs.length; i++) {
            if (!msgs[i].isSet(Flags.Flag.DELETED))
            System.out.println("Message # " + msgs[i] + " not deleted");
                
            }  
             Folder spoofNotFolder = store.getFolder("Spoof Notification folder");
              if (!spoofNotFolder.exists())
              spoofNotFolder.create(Folder.HOLDS_MESSAGES);
              
              if(!mess.equalsIgnoreCase(authenMessage)){
                
                  
              }
              
            
                    
        folder.close(false);
        store.close();
              
              return 0;
        }
        catch (Exception ex)
        {
          ex.printStackTrace();
          System.out.println("Exception "+ex);
          return -1;
        }
       
}

}


            