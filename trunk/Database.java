import java.sql.*;

class Database
{
	public static Connection cxn;
	
	public static void connect()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");

			cxn = DriverManager.getConnection("jdbc:mysql://localhost/group17", "jonathan", "apple123");
					}
		catch (Exception e)
		{
			System.out.println("Exception in Connection " + e);
			System.exit(-1);
		}
	}
	
	public static void insertFinanceItem(FinanceItem fi)
	{

		connect();
		try
		{

			int time = (int) (fi.datetime.getTime() / 1000);
			

			ResultSet rs;
			PreparedStatement ps = cxn.prepareStatement("SELECT volume FROM group17_finance WHERE symbol = ? ORDER BY datetime desc");			
			ps.setString(1, fi.symbol);

			rs = ps.executeQuery();
			rs.next();
			int i = rs.getInt(1);

			int volume = Math.max(fi.volume, i); 

			ps = cxn.prepareStatement("insert group17_finance values(?, ?, ?, ?, ?)");
			
			ps.setString(1,fi.symbol);
			ps.setInt(2,time);
			ps.setDouble(3,fi.bid);
			ps.setDouble(4,fi.ask);
			ps.setInt(5,volume);
			ps.executeUpdate();

			cxn.close();
			ps.close();
						
		}
		catch(Exception e)
		{
			System.out.println("Exception in Connection " + e);
			System.exit(-1);
		}

	}

	public static void insertNewsItem(NewsItem ni)
	{
		connect();
		try
		{
			int time = (int) (ni.date.getTime() / 1000);
			
			ResultSet rs;
			PreparedStatement ps = cxn.prepareStatement("SELECT COUNT(*) from group17_news where source = ? AND datetime = ? AND title  = ?");			
			ps.setString(1, ni.source);
			ps.setInt(2, time); 
			ps.setString(3, ni.title);
			rs = ps.executeQuery();
			rs.next();
			int i = rs.getInt(1);
			
			if (i == 0) {

			  ps = cxn.prepareStatement("INSERT group17_news VALUES(?, ?, ?, ?, ?)");
			  
			  ps.setString(1, ni.source);
			  ps.setInt(2, time); //date is unix time
			  ps.setString(3, ni.title);
			  ps.setString(4, ni.body);
			  ps.setBoolean(5, ni.anaysis);
			  ps.executeUpdate();

			}

			ps.close();
			rs.close();
			cxn.close();
			
		}
		catch(Exception e)
		{
			System.out.println("Exception in Connection " + e);
			System.exit(-1);
		}

		
	}

	public static int getFinanceData(String type, String company, int time1, int time2) {

		connect();
		try
		{
			ResultSet rs;
			PreparedStatement ps = cxn.prepareStatement("SELECT " + type + " from group17_finance where symbol = ? AND datetime > ? AND datetime < ? ORDER BY datetime desc");			
			ps.setString(1, company);
			ps.setInt(2, time1); 
			ps.setInt(3, time2);
			//System.out.println(ps.toString());
			rs = ps.executeQuery();
			
			if (rs.next()) {
			      int i = rs.getInt(1);
			      rs.close();
			      ps.close();
			      cxn.close();
			      return i;
			}
			rs.close();
			ps.close();
			cxn.close();
			
		}
		catch(Exception e)
		{
			System.out.println("Exception in Connection " + e);
			System.exit(-1);
		}

		//no data
		return 0;
	}
	
	public static void insertIncreaseValue(String company, String type, double percent, int time, boolean increase) {
			//get increase for same company and type in the last 5 mins, if there is one add a end time, else create new one
			connect();
			try
			{
				ResultSet rs;
				PreparedStatement ps = cxn.prepareStatement("SELECT id FROM group17_increases WHERE company = ? AND type = ? AND datetime > ? AND increase = ?");
				ps.setString(1, company);
				ps.setString(2, type);
				ps.setInt(3, time - 300); //Any within the last 5 mins
				ps.setBoolean(4, increase);
				rs = ps.executeQuery();
				if (rs.next()) {
					int id = rs.getInt(1);
					System.out.println(id);
										
					ps = cxn.prepareStatement("UPDATE group17_increases SET datetime_end = ?  WHERE id = ?");
					ps.setInt(1, time);
					ps.setInt(2, id);
					ps.executeUpdate();
				
				} else {
					System.out.println("adding new");
					ps = cxn.prepareStatement("INSERT group17_increases (company, type, percent, datetime, increase) VALUES(?, ?, ?, ?, ?)");
					
					ps.setString(1, company);
					ps.setString(2, type);
					ps.setDouble(3, percent);
					ps.setInt(4, time);
					ps.setBoolean(5, increase);
					ps.executeUpdate();
				
				}
				
			}
			catch(Exception e)
			{
				System.out.println("Exception in Connection " + e);
				System.exit(-1);
			}

	}


}
