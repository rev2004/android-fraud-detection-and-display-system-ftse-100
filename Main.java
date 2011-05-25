// Controls the flow of the whole server
import network.server.Server;

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
		new Scrape();

		//FinanceItem f = new FinanceItem("RR,TULLOW OIL,15:51:26,1290.00,1291.00,833031");
		//f.print();

		//NewsItem n = new NewsItem("<nitem><nsource>Reuters: Business News</nsource><ndate>15:40:32 23/05/2011</ndate><ntitle>Oil dives on euro zone debt woes</ntitle><nbody>LONDON (Reuters) - Crude oil futures slipped on Monday due to the peripheral euro zone's debt crisis, which pushed the dollar to a two-month high versus the euro and knocked broader equity markets lower.</nbody></nitem>");
		
		//Database.insertIncreaseValue("RR","volume",12,1306252491, true);
	}
}
