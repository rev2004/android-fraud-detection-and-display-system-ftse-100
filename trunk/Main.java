// Controls the flow of the whole server

public class Main
{
	public static void main(String args[])
	{
		// listens for clients on port 1101
		new Server(1101);

		// Gets the feeds
		new Scrape();
	}
}
