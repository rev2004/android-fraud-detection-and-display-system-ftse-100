package network.server;

import network.server.Client;

import java.util.*;

// Connection from the server to the client class
public class Connection extends Thread
{
	public Client client;
	public LinkedList<String> messages;
	public Boolean running;
	public Boolean accept;

	// Constructor
	public Connection(Client client)
	{
		// Initialize variables
		this.client = client;
		this.running = true;
		this.accept = false;
		this.messages = new LinkedList<String>();

		// Start main thread
		this.start();
	}

	public void run()
	{
		while (running)
		{
			// Wait for next message
			String msg = client.recieveMessage();

			// If there was no problem
			if (msg != null)
			{
				// queue a recieved message
				messages.add(msg);
			}
		}
	}
}
