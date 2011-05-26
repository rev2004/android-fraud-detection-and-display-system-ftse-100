package network.server;

import network.server.Connection;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server extends Thread
{
	private int port;
	private ServerSocket serverSocket = null;
	public List<Connection> connections;

	// Constructor
	public Server(int port)
	{
		this.port = port;

		// Create a thread safe list for the connections
		this.connections = Collections.synchronizedList(new Vector<Connection>());
	}

	// Start listening for connections
	// Returns true if success, false otherwise
	public Boolean listen()
	{
		try
		{
			// Start listening on a port
			serverSocket = new ServerSocket(port);

			// Starts the listening thread
			this.start();

			// Return success
			return true;
		}
		catch (IOException e)
		{
			// Return failure
			return false;
		}
	}

	// The main listening thread
	public void run()
	{
		while (true)
		{
			try
			{
				// Accept clients and store their details
				Client client = new Client(serverSocket.accept());

				// If the client can initialise
				if (client.init())
				{
					// Add the server connection to the client to the list
					connections.add(new Connection(client));
				}
				
			}
			catch (IOException e)
			{
				// Could not accept client
				System.out.println("Accept failed on port: " + port);
			}
		}
	}

	// Sends a message to all clients currently connected.
	public void sendMessageToAll(String msg)
	{
		// Synchronise over connections
		synchronized (connections)
		{
			// Iterate through connections
		    for (Iterator<Connection> itr = connections.iterator(); itr.hasNext();)
			{
				// Send a message to each client
				Connection cxn = itr.next();
		    	cxn.client.sendMessage(msg);
		    }
		}
	}
}



	
