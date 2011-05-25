package network.server;

import network.server.Server;
import network.server.Connection;
import network.server.Client;

import java.util.*;

// Abstract server handler
public abstract class Handler extends Thread
{
	private Server server;
	private Boolean running;

	// Constructor
	public Handler(Server server)
	{
		// Initialize variables
		this.server = server;
		this.running = true;

		// Start main thread
		this.start();
	}

	// Main handler thread
	public void run()
	{
		while (running)
		{
			// Synchronize over the connections
			synchronized (server.connections)
			{
				// Iterate over the connections
				Iterator<Connection> itr = server.connections.iterator();
			
				while (itr.hasNext())
				{
					Connection cxn = itr.next();

					// If the client accept handle hasn't been called yet
					if (cxn.accept == false)
					{
						// Handle the new client
						handleClientAccept(cxn.client);

						// Flag accepted as true
						cxn.accept = true;
					}

					// If there are messages waiting
					while (cxn.messages.size() > 0)
					{
						// Get next message
						String msg = cxn.messages.remove();

						// Handle a recieved message
						handleClientMessage(cxn.client, msg);
					}

					// If the client disconnected
					if (!cxn.client.isConnected())
					{
						// handle disconnect
						handleClientDisconnect(cxn.client);
					
						// Stop thread
						cxn.running = false;
					
						// remove connection
						itr.remove();
					}
				}
			}
		}
	}
	// Handle a new client
	public abstract void handleClientAccept(Client client);
	
	// Handle a client disconnect
	public abstract void handleClientDisconnect(Client client);

	// Handle a message from a client
	public abstract void handleClientMessage(Client client, String msg);
}
