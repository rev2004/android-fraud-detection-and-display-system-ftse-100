import network.server.*;

// This class Handles communication between the client and the server
class AlertServerHandler extends Handler
{
	private static String SEP = ",";
	
	// Constructor
	AlertServerHandler(Server server) { super(server); }

	public void sendAlert(Client client, Alert alert)
	{
		client.sendMessage(alert.encode());
	}
	
	// When a client is accepted
	public void handleClientAccept(Client client)
	{
		System.out.println("Accepted a new client :)");
	}
	
	// When a message is recieved from a client
	public void handleClientMessage(Client client, String msg)
	{
		String[] args = msg.split(SEP);

		if (args[0] == "REQ")
		{
			// REQ, 123342344 (timestamp)
			/*Alert alerts[] = AlertDatabase.requestNewAlerts(args[1]);

			for (Alert alert : alerts)
				sendAlert(client, alert);*/
			
			/*
			// Create a dummy alert
			Alert alert = new Alert(
				"4", 
				"14:03", 
				"67", 
				new String[] {"rule 1", "rule 2"}, 
				new String[] {"news 1", "news 2", "news 3"}, 
				new String[] {"company 1", "company 2"} );
			
			client.sendMessage(alert.encode());
			*/
		}
		else if (args[0] == "REP")
		{
			// REP, 1 (alert ID), 1 (report 1 = yes,  0 = no)
			//Database.query("INSERT INTO AlertReport (AlertID, Report) VALUES (" + args[0] + ", " + args[1] + ")");
			//AlertDatabase.reportAlert(arg[0], arg[1]);
		}

		/*
		// Create a dummy alert
		Alert alert = new Alert(
					"4", 
					"14:03", 
					"67", 
					new String[] {"rule 1", "rule 2"}, 
					new String[] {"news 1", "news 2", "news 3"}, 
					new String[] {"company 1", "company 2"} );

		// Send the alert to the client
		client.sendMessage(alert.encode());*/
	}

	// When a client disconnects
	public void handleClientDisconnect(Client client)
	{
		System.out.println("Client disconnected");
	}
}
