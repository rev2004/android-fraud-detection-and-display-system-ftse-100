import network.client.*;

import java.io.*;

class App
{
	public static void main(String[] args)
	{
		// Create a new client object clintonio.com
		Client client = new Client("127.0.0.1", 9011);

		// Try to connect to the server
		if (!client.connect())
		{
			// Failure
			System.out.println("Failed to connect");
			System.out.println("Attemping to reconnect...");
			client.reconnectLoop(0, 1000, -1);
		}
		System.out.println("Connected");

		// Connect to the database
		AlertDb alertDb = new AlertDb();
		alertDb.load();
		
		// Create the handler object
		AppAlertHandler clientHandler = new AppAlertHandler(client, alertDb);
		
		// This next bit of code is just for testing the handler.
		// Basically it just sends a request for an alert every time the user
		// presses return.
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String inputLine;
		try
		{
			do
			{
				inputLine = input.readLine();
				if (inputLine.equals("alerts"))
					for (Alert alert : alertDb.alerts)
					{
						System.out.println("================================================================================");
						System.out.println("Alert " + alert.id + " Company: " + alert.company + " Date: " + alert.sdf.format(alert.time));
						System.out.println("================================================================================");
					}
				else if (inputLine.equals("clear"))
					{
						alertDb.reset();
					}
				
			} while (inputLine != null);
		}
		catch (Exception e)
		{
            System.err.println(e.getMessage());
			System.exit(-1);
        }
	}
}
