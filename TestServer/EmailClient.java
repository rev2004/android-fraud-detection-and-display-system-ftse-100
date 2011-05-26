/**
 * File:  EmailClient.java
 * Created: 2011-5-6
 * Author: Group 17
 */

import java.util.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * File: EmailClient.java Created: 2011-5-6 Author: Group 15 Project 247
 */
public class EmailClient {

	// singleton design pattern
	private static EmailClient	instance	= null;

	private String				subject		= null;

	private String				text		= null;

	private EmailClient() {

	}

	public static EmailClient getInstance() {

		if ( instance == null ) {
			instance = new EmailClient ();
		}
		return instance;
	}

	private synchronized void sendEmail(ArrayList<String> users) {

		String client = "";
		ArrayList<String> usernames = users;

		Iterator<String> it = usernames.iterator ();

		while ( it.hasNext () ) {
			client = it.next ();

			final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
			// Get a Properties object for gmail setting.
			Properties props = System.getProperties ();
			props.setProperty ("mail.smtp.host", "smtp.gmail.com");
			props.setProperty ("mail.smtp.socketFactory.class", SSL_FACTORY);
			props.setProperty ("mail.smtp.socketFactory.fallback", "false");
			props.setProperty ("mail.smtp.port", "465");
			props.setProperty ("mail.smtp.socketFactory.port", "465");
			props.put ("mail.smtp.auth", "true");
			final String username = "starfish.warwick@gmail.com";
			final String password = "wildgoose";
			Session session = Session.getDefaultInstance (props, new Authenticator () {

				protected PasswordAuthentication getPasswordAuthentication() {

					return new PasswordAuthentication (username, password);
				}
			});

			// -- Create a new message --
			Message msg = new MimeMessage (session);

			// -- Set the FROM and TO fields --
			try {
				msg.setFrom (new InternetAddress (username));
				msg.setRecipients (Message.RecipientType.TO,
								InternetAddress.parse (client, false));
				msg.setSubject (subject);
				msg.setText (text);
				msg.setSentDate (new Date ());
				Transport.send (msg);
			} catch ( AddressException e ) {
				
			} catch ( MessagingException e ) {
			}

			System.out.println ("Message is sent to " + client  + ".");
		}

	}

	/**
	 * Send Insider Trading Alert to client
	 */
	public synchronized void sendInsiderTrading(ArrayList<String> users, String text) {

		this.subject = "Insider Trading Alert!";
		this.text = text;
		sendEmail(users);

	}

	/**
	 * Send Security Fraud to client
	 */
	public synchronized void sendSecurityFraud(ArrayList<String> users, String text) {

		this.subject = "Security Fraud Alert!";
		this.text = text;
		sendEmail(users);

	}

}
