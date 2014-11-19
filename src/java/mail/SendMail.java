/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author mananda
 */
public class SendMail {
 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//public void triggerMail(String userID, String fName, String lName, String pwd, String email)
public void triggerMail(String userID, String email) {
       
        // Recipient's email ID needs to be mentioned.
        String to = "\"" + email + "\"";

        // Sender's email ID needs to be mentioned
        String from = "mananda@ilstu.edu";

        // Assuming you are sending email from this host
        String host = "smtp.ilstu.edu";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        //properties.setProperty("mail.user", "yourID"); // if needed
        //properties.setProperty("mail.password", "yourPassword"); // if needed

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("LinkedEDU Sign Up Successful !");

            // Send the actual HTML message, as big as you like
            //message.setContent("<center><img src=\"https://www.dropbox.com/s/nkgiyuqpoygrxya/linkedULogo2.png?dl=0\"</centre><br>" + 
                    
            message.setContent("<h1>Hi " + userID + ",<br></h1>"
                     + "<p>You have been successfully signed up!<br></p>",
              //      + "<br> Password: " + pwd +" </p>",
              //      + "<p>You have been successfully signed up!<br> Username:"+ userID
              //      + "<br> Password: " + pwd +" </p>",
                    "text/html");

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
   

