// Controls the flow of the whole server
import network.server.*;

public class Main
{
	public static void main(String args[])
	{
		// Create a server
		/*Server server = new Server(9011);

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

		new RuleEngine(server);*/

		Database.connect();
		/*Database.insertIncreaseValue("RR","ask",20,3000,true);
		Database.insertIncreaseValue("RR","ask",20,5000,true);
		Database.insertIncreaseValue("RR","ask",20,12000,true);
		Database.insertIncreaseValue("RR","ask",20,24000,true);*/
		
		int[] temp = Database.getIncreaseTimes("RR","ask",0,true);
		System.out.println(temp[0] + " " + temp[1] + " " + temp[2] + " " + temp[3]);

	}
}
