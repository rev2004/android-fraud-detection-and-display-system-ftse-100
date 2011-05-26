import network.client.*;

// Handler class
class EmailAppAlertHandler extends AlertHandler
{
	// Constructor
	EmailAppAlertHandler(Client client) { super(client); }

	// When an alert is recieved from the server
	public void handleAlert(Alert alert)
	{
		String from = "alert@starfish.co.uk";
		String to = "randomface666@hotmail.co.uk";
		String title = "Alert!";
		String body = "no body";
		
		new Email(from, to, title, body);
		
		System.out.println();
	}

	// When the server disconnects
	public void handleDisconnect()
	{
		client.reconnectLoop(0, 1000, -1);
	}
}
