import network.client.*;

import java.io.*;

class EmailApp
{
	public static void main(String[] args)
	{
		// Create a new client object clintonio.com
		Client client = new Client("clintonio.com", 9011);

		// Try to connect to the server
		if (!client.connect())
		{
			// Failure
			System.out.println("Failed to connect");
			
			client.reconnectLoop(0, 1000, -1);
		}

		// Create the handler object
		EmailAppAlertHandler clientHandler = new EmailAppAlertHandler(client);
	}
}
