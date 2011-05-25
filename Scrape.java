import java.io.*;
import java.net.*;

class Scrape extends Thread
{
	Scrape()
	{
		this.start();
	}
	
	public static BufferedReader getFeed(String host, int port)
	{
		BufferedReader in = null;
		Socket socket;
		
		try
		{
			// connect to the host
			System.out.println("Connecting to " + host + ":" + port);
			socket = new Socket(host, port);
				
			// set up the input/output objects
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("Successfully connected to " + host + ":" + port);
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		
		return in;
	}
	
	public void run()
	{
		String host = "condor.dcs.warwick.ac.uk";
		int newsPort = 1720;
		int stockPort = 3389;
		BufferedReader newsIn, stockIn;

		
		try
		{
			newsIn = getFeed(host, newsPort);
			stockIn = getFeed(host, stockPort);
			
			new NewsFeed(newsIn);
			new StockFeed(stockIn);
			while (true);
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}
}

class NewsFeed extends Thread
{
	BufferedReader in;
	String inputLine;
	char inputChar;
	
	NewsFeed(BufferedReader in)
	{
		inputLine = "";
		this.in = in;
		this.start();
	}
	
	public void run()
	{
		while (true)
		{
			try
			{
				inputChar = (char)((in.read()));
				inputLine += inputChar;
				if (inputChar == '>')
					if (inputLine.contains("</nitem>"))
					{
						NewsItem n = new NewsItem(inputLine);
						//Database.insertNewsItem(n);
						n.print();
						inputLine = "";
					}
			}
			catch (Exception e)
			{
				System.err.println(e.getMessage());
				System.exit(-1);
			}
		}
	}
}

class StockFeed extends Thread
{
	BufferedReader in;
	String inputLine;
	StockFeed(BufferedReader in)
	{
		this.in = in;
		this.start();
	}
	
	public void run()
	{
		while (true)
		{
			try
			{
				inputLine = in.readLine().replaceAll("\"","").replaceAll(".L,",",");
				System.out.println(inputLine);
				FinanceItem f = new FinanceItem(inputLine);
				//Database.insertFinanceItem(fi);
				f.print();
				inputLine = "";
			}
			catch (Exception e)
			{
				System.err.println(e.getMessage());
				System.exit(-1);
			}
		}
	}
}
