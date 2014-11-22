/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author skdonep
 */
public class EmailController {
    /*
     * To change this template, choose Tools | Templates
     * and open the template in the editor.
     */

    public String cid;// = generateCID();
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
            message.setSubject("LinkEDU Sign Up Successful !");

            // Send the actual HTML message, as big as you like
            // This mail has 2 part, the BODY and the embedded image
            MimeMultipart multipart = new MimeMultipart("related");

             // first part (the html)
             BodyPart messageBodyPart = new MimeBodyPart();
             String htmlText = "<center><img src=\"cid:image\"></center><br/><H1>Hello " + userID + "!!! </H1><br/><p>Welcome to LinkedU ! You have been successfully registered</p>";
             messageBodyPart.setContent(htmlText, "text/html");
             // add it
             multipart.addBodyPart(messageBodyPart);

             // second part (the image)
             messageBodyPart = new MimeBodyPart();
             DataSource fds = new FileDataSource(
             // "./LinkedU/web/resources/images/linkedulogo2.png");
             "H:/LinkedU/web/resources/images/linkedulogo2.png");

             messageBodyPart.setDataHandler(new DataHandler(fds));
             messageBodyPart.setHeader("Content-ID", "<image>");

             // add image to the multipart
             multipart.addBodyPart(messageBodyPart);

             // put everything together
             message.setContent(multipart); 
            
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }    
    
}
