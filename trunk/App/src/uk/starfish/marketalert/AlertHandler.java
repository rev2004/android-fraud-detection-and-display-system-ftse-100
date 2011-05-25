package uk.starfish.marketalert;

import network.client.*;

// A Handler for alerts
public abstract class AlertHandler extends Handler
{
	private static String SEP = ",";

	// Constructor
	public AlertHandler(Client client)
	{
		super(client);
	}

	// When a server message is recieved
	public void handleServerMessage(String msg)
	{
		// Decode the alert
		Alert alert = Alert.decode(msg);

		// Handle the alert
		handleAlert(alert);
	}

	// Request an alert identified by id
	public void requestAlert(String id)
	{
		client.sendMessage("REQ" + SEP + id);
	}

	// Report an alert identified by id, with report message report.
	public void reportAlert(String id, String report)
	{
		client.sendMessage("REP" + SEP + id + SEP + report);
	}

	// Handle an alert
	public abstract void handleAlert(Alert alert);
}
