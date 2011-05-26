import network.client.*;

// Handler class
class AppAlertHandler extends AlertHandler
{
	private AlertDb alertDb;
	
	// Constructor
	AppAlertHandler(Client client, AlertDb alertDb)
	{
		super(client);
		
		this.alertDb = alertDb;
	}

	// When an alert is recieved from the server
	public void handleAlert(Alert alert)
	{
		System.out.println("================================================================================");
		System.out.println("RECIEVED ALERT");
		System.out.println("================================================================================");
		System.out.println("ID: " + alert.id);
		System.out.println("Time: " + alert.time);
		System.out.println("Total Points: " + alert.points);
		System.out.println("Company: " + alert.company);
		System.out.println("Rules Broken: ");
		for (RuleItem r : alert.rules)
			System.out.println("---> " + r.name + " (points = " + r.points.toString() + ")");
		System.out.println("Related News: ");
		for (NewsItem n : alert.news)
		{
			System.out.println("---> Title: " + n.title);
			System.out.println("     Source: " + n.source);
			System.out.println("     Date: " + n.sdf.format(n.date));
			System.out.println("     " + n.body);
		}

		alertDb.add(alert);
		alertDb.save();
	}

	// When the server disconnects
	public void handleDisconnect()
	{
		System.out.println("Disconnected");
		System.out.println("Attempting to reconnect...");
		client.reconnectLoop(0, 1000, -1);
	}
}
