import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.*;

class scrape
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
			
			new newsFeed(newsIn);
			new stockFeed(stockIn);
			while (true);
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}
}

class newsFeed extends Thread
{
	BufferedReader in;
	char inputChar;
	newsFeed(BufferedReader in)
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
				inputChar = (char)((in.read()));
				System.out.print(inputChar);
			}
			catch (Exception e)
			{
				System.err.println(e.getMessage());
				System.exit(-1);
			}
		}
	}
}

class stockFeed extends Thread
{
	BufferedReader in;
	String inputLine;
	stockFeed(BufferedReader in)
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
				System.out.println(inputLine);
			}
			catch (Exception e)
			{
				System.err.println(e.getMessage());
				System.exit(-1);
			}
		}
	}
	
	public void decode(String str) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		String str = "\"TLW.L\",\"TULLOW OIL\",06:20:30,1435.0,1437.0,22581708"; //testing only
		String[] data = new String[6];
		String symbol, name, time;
		double bid, ask;
		int volume;
	
		if (str != null) {
			
			data = str.split(",");
			symbol = data[0].substring(0, 2);
			name = data[1];
			time = data[2];
			bid = Double.parseDouble(data[3]);
			ask = Double.parseDouble(data[4]);
			volume = Integer.parseInt(data[5]);
		
			try {
			
				Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306", "group17", "");
				//System.out.println("Worked");
				
				PreparedStatement addshare = connect.prepareStatement("INSERT INTO group17_finance VALUES(NULL,?,?,?,?,?)");
				
				addshare.setString(1, symbol);
				addshare.setString(2, time); //time is unix time
				addshare.setString(3, bid);
				addshare.setString(4, ask);
				addshare.setString(5, volume);
				
				//addshare.executeUpdate();
				
			} catch(Exception e) {
				System.out.println("Exception in Connection "+ e);
			}
		}	
	}
}