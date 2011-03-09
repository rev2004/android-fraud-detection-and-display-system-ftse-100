import java.io.*;
import java.net.*;
import java.util.*;


class Scrape
{
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
		
	public static void main(String args[])
	{
		String host = "condor.dcs.warwick.ac.uk";
		String inputLine;
		char inputChar;
		int newsPort = 4444;
		int stockPort = 4445;
		Socket newsSock, stockSock;
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
				inputLine = in.readLine();
				//System.out.println(inputLine);
				 // FinanceItem f = new FinanceItem(inputLine);
			}
			catch (Exception e)
			{
				System.err.println(e.getMessage());
				System.exit(-1);
			}
		}
	}
}