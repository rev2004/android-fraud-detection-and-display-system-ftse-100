package network.client;

import network.client.Client;

// Client Handler class
public abstract class Handler implements Runnable
{
	private Boolean running;
	public Client client;

	// Constructor
	public Handler(Client client)
	{
		this.client = client;
		this.running = true;
		//this.start();
	}

	// Thread
	public void run()
	{
		while (running)
		{
			if (client.isConnected())
			{
				// Wait for next message
				String msg = client.recieveMessage();
				
				// If there was a problem
				if (msg == null)
				{
					// Handle a disconnect
					handleDisconnect();
				}
				else
				{
					// Handle a recieved message
					handleServerMessage(msg);
				}
			}
		}
	}

	// Called when a message is recieved from the server
	public abstract void handleServerMessage(String msg);

	// Called when the client is disconnected
	public abstract void handleDisconnect();
}

