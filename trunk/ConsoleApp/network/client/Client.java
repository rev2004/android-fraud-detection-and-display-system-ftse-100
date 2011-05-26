package network.client;

import network.Communicator;

import java.net.*;

// Main client object
public class Client extends Communicator
{
	private int port;
	private String host;

	// Constructor
	public Client(String host, int port)
	{
		this.host = host;
		this.port = port;
	}

	// Attempts to connect to the server
	// Returns true for success, false otherwise
	public Boolean connect()
	{
		try
		{
			// Connect to the host server
			assignSocket(new Socket(host, port));
			
			if (!init())
			{
				// Return failure
				return false;
			}

			// Return success
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	// Attempt to reconnect
	public Boolean reconnect()
	{
		// If already connected
		if (isConnected())
			return true;
		else
		{
			// Attempt to reconnect
			if (connect())
				return true;
			else
				return false;
		}
	}

	public void reconnectLoop(int delay, int interval, int attempts)
	{
		try
		{
			Thread.sleep(delay);
		}
		catch (InterruptedException e) {}
		
		Boolean success = false;
		
		while (!success && attempts != 0)
		{
			try
			{
				Thread.sleep(interval);
			}
			catch (InterruptedException e) {}

			attempts --;
			success = reconnect();
		}
	}
}

