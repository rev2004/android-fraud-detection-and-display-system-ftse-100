// Controls the flow of the whole server
import network.server.*;

public class Main
{
	public static void main(String args[])
	{
		// Create a server
		Server server = new Server(9011);

		// Attempt to start listening
		if (!server.listen())
		{
			// Failure
			System.out.println("Failed to listen");
			return;
		}

		// Create a handle to the server
		new AlertServerHandler(server);

		// Gets the feeds
		Database.connect();
		new Scrape();

		new RuleEngine(server);

	}
}
