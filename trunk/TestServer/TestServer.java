import network.server.*;

import java.util.Date;

// Master server class
class TestServer
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

		Alert[] alert = new Alert[3];
			
			alert[0] = new Alert(
				"1", 
				new Date(), 
				50, 
				"Yahoo",
				new RuleItem[] {
					new RuleItem("Sudden increase in share prices followed by news release", 50)}, 
				new NewsItem[] {
					new NewsItem("yahoo", new Date(), "Yahoo declares 4% increase profits", "Internet giant yahoo declared 4% increase profits for the year.")}
			);

			alert[1] = new Alert(
				"2", 
				new Date(), 
				50, 
				"Rolls-Royce",
				new RuleItem[] {
					new RuleItem("Sudden increase in share prices followed by news release", 50)}, 
				new NewsItem[] {
					new NewsItem("RR", new Date(), "Rolls-Royce get major contract", "Rolls-Royce aeroengines has won a large contract for 50 Boeing 787 planes, brought by Brishish Airways."), 
					new NewsItem("RR", new Date(), "RR recieves large contract", "British Airways said they would use RR engines rather than GE for their new fleet of 787's")});

			alert[2] = new Alert(
				"3", 
				new Date(), 
				40, 
				"BARCLAYS",
				new RuleItem[] {
					new RuleItem("Following news release share prices fall sharply", 40)}, 
				new NewsItem[] {
					new NewsItem("BARCLAYS", new Date(), "BARCLAYS decreases interest rates again", "After reducing interest rates from 0.5% to 0.2% last month, Barclays have again reduce the rates down to 0.1%")});

		int i = 0;
		
		while (true)
		{
			try { Thread.sleep(15 * 1000); } catch (Exception e) {}
			System.out.println("Sending Alert");
			
			i ++;
			alert[i % 3].time = new Date();
			server.sendMessageToAll(alert[i % 3].encode());
		}
		
    }
}
