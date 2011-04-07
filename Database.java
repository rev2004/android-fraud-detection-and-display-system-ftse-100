import java.sql.*;

class Database
{
	public static Connection cxn;
	
	public static void connect()
	{
		try
		{
			//Change the port, use ssh tunneling from joshua to codd.
			cxn = DriverManager.getConnection("jdbc:mysql://localhost:9611", "group17", "");
		}
		catch (Exception e)
		{
			System.out.println("Exception in Connection " + e);
		}
	}
	
	public static void insertFinanceItem(FinanceItem fi)
	{
		try
		{
			PreparedStatement ps = cxn.prepareStatement("INSERT INTO group17_finance VALUES(NULL,?,?,?,?,?)");
			
			ps.setString(1, fi.symbol);
			ps.setString(2, fi.time); //time is unix time
			ps.setDouble(3, fi.bid);
			ps.setDouble(4, fi.ask);
			ps.setInt(5, fi.volume);
			
			//ps.executeUpdate();
			
		}
		catch(Exception e)
		{
			System.out.println("Exception in Connection " + e);
		}
	}

	public static void insertNewsItem(NewsItem ni)
	{
		try
		{
			PreparedStatement ps = cxn.prepareStatement("INSERT INTO group17_news VALUES(NULL,?,?,?,?,?)");
			
			ps.setString(1, ni.source);
			ps.setInt(2, (int) (ni.date.getTime() / 1000)); //date is unix time
			ps.setString(3, ni.title);
			ps.setString(4, ni.body);
			
			//ps.executeUpdate();
			
		}
		catch(Exception e)
		{
			System.out.println("Exception in Connection " + e);
		}
	}
}
