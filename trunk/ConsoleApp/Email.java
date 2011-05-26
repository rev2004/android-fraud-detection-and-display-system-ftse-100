
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

class Email
{
	private String from;
	private String to;
	private String subject;
	private String body;

	private Session session;
	
	Email(String from, String to, String subject, String body)
	{
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.body = body;
		init();
		send();
	}
	public void init()
	{
      // Get system properties
      Properties props = System.getProperties();

      // Setup mail server (gmail)
      props.setProperty ("mail.smtp.host", "smtp.gmail.com");
		props.setProperty ("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty ("mail.smtp.socketFactory.fallback", "false");
		props.setProperty ("mail.smtp.port", "465");
		props.setProperty ("mail.smtp.socketFactory.port", "465");
		props.put ("mail.smtp.auth", "true");
		final String username = "starfish.warwick@gmail.com";
		final String password = "wildgoose";
		Session session = Session.getDefaultInstance(props, new Authenticator () {

				protected PasswordAuthentication getPasswordAuthentication()
				{
					return new PasswordAuthentication (username, password);
				}
			});
   }
	public void send()
	{
		try{
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

         // Set Subject: header field
         message.setSubject(subject);

         // Set the send date
         message.setSentDate(new Date());

         // Now set the actual message
         message.setText(body);

         // Send message
         Transport.send(message);
         
         System.out.println("Sent message successfully....");
         
      }catch (MessagingException mex) {
         mex.printStackTrace();
      }
	}
}


      

