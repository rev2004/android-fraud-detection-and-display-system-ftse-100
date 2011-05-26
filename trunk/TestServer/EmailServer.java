import network.server.*;

import java.util.*;

// Master server class
class EmailServer
{
	public static void main(String[] args)
	{
		// Create a server
		Server server = new Server(9011);

		// Attempt to start listening
		if (!server.listen())
		{
			// Failure
			System.out.println("Failed to listen");
			return;
		}

		// Create a handle to the server
		new AlertServerHandler(server);

		// This next bit of code is just for testing the handler.
		// Basically it just sends an alert to all clients everytime the user
		// presses return.

		Alert alert = new Alert(
				"1", 
				new Date(), 
				50, 
				"Yahoo",
				new RuleItem[] {
					new RuleItem("Sudden increase in share prices followed by news release", 50)}, 
				new NewsItem[] {
					new NewsItem("yahoo", new Date(), "Yahoo declares 4% increase profits", "Internet giant yahoo declared 4% increase profits for the year.")}
			);
		
		int i = 0;
		
		while (true)
		{
			try { Thread.sleep(15 * 1000); } catch (Exception e) {}
			System.out.println("Sending Email");

			ArrayList<String> users = new ArrayList<String>();
			users.add("csujcx@live.warwick.ac.uk");
			
			EmailClient email = EmailClient.getInstance();
			email.sendSecurityFraud(users, "Alert: \nCompany: Yahoo,\nPoints: 50,\nRules:\nRule 1: Sudden increase in share prices followed by news release\nNews:\nNews Article 1: Yahoo declares 4% increase profits\nInternet giant yahoo declared 4% increase profits for the year.");
		}
		
    }
}
