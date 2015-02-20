/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sdiappgui;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;


/**
 
 * Adapted from NetBeans forum at http://wiki.netbeans.org/SendMailUsingJavaFromNetBeans
 */
public class SMTPAuthenticator extends Authenticator{
    private String username;
    private String password;
   private boolean needAuth;

    public SMTPAuthenticator(String username, String password, boolean auth)
    {
        this.username = username;
        this.password = password;
       this.needAuth = needAuth;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
       
            return new PasswordAuthentication(username, password);
        
         }
    
}
