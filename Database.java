import java.sql.*;
import java.util.Calendar;

class Database
{
	public static Connection cxn;
	public static boolean lock = false;
	public static ResultSet rs;
	public static PreparedStatement ps;
	
	public static void connect()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");

			cxn = DriverManager.getConnection("jdbc:mysql://localhost/group17", "jonathan", "apple123");
					}
		catch (Exception e)
		{
			System.out.println("Exception in Connect " + e);
			System.exit(-1);
		}
	}
	
	public static void insertFinanceItem(FinanceItem fi)
	{
		int i = 0;
// 		connect();
		try
		{

			int time = (int) (fi.datetime.getTime() / 1000);
			

			ps = cxn.prepareStatement("SELECT volume FROM group17_finance WHERE symbol = ? ORDER BY datetime desc");			
			ps.setString(1, fi.symbol);

			rs = ps.executeQuery();
			if (rs.next()) {
				i = rs.getInt(1);
			}
			
			
			int volume = Math.max(fi.volume, i); 

			ps = cxn.prepareStatement("insert group17_finance values(?, ?, ?, ?, ?)");
			
			ps.setString(1,fi.symbol);
			ps.setInt(2,time);
			ps.setDouble(3,fi.bid);
			ps.setDouble(4,fi.ask);
			ps.setInt(5,volume);
			ps.executeUpdate();

		}
		catch(Exception e)
		{
			System.out.println("Exception in insertFinanceItem " + e);
			System.exit(-1);
		}

	}

	public static void insertNewsItem(NewsItem ni)
	{
		int i = 0;
// 		connect();
		try
		{
			int time = (int) (ni.date.getTime() / 1000);
			
			ps = cxn.prepareStatement("SELECT COUNT(*) from group17_news where source = ? AND datetime = ? AND title  = ?");			
			ps.setString(1, ni.source);
			ps.setInt(2, time); 
			ps.setString(3, ni.title);
			rs = ps.executeQuery();
			if (rs.next()) {
				i = rs.getInt(1);
			}
			
			if (i == 0) {

				ps = cxn.prepareStatement("INSERT group17_news (source, datetime, title, body, analysis) VALUES(?, ?, ?, ?, ?)");
				
				ps.setString(1, ni.source);
				ps.setInt(2, time); //date is unix time
				ps.setString(3, ni.title);
				ps.setString(4, ni.body);
				ps.setBoolean(5, ni.anaysis);
				ps.executeUpdate();
				
				ps = cxn.prepareStatement("SELECT id from group17_news where source = ? AND datetime = ? AND title  = ?");
				ps.setString(1, ni.source);
				ps.setInt(2, time); 
				ps.setString(3, ni.title);				
				rs = ps.executeQuery();
				
				rs.next();
				int id = rs.getInt(1);
				//System.out.println(id);
				
				findCompanies(ni, id);
			}

			
		}
		catch(Exception e)
		{
			System.out.println("Exception in insertNewsItem " + e);
			System.exit(-1);
		}

		
	}
	
	public static void insertRating(String company, boolean rating, int newsid) {
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int year = cal.get(Calendar.YEAR);
		int id;
// 		connect();
		
		try {
			ps = cxn.prepareStatement("SELECT id, amount FROM group17_rating WHERE company = ? AND rating = ? AND day = ? AND year = ?");
			ps.setString(1, company);
			ps.setBoolean(2, rating);
			ps.setInt(3, day);
			ps.setInt(4, year);
			//System.out.println(ps);
			rs = ps.executeQuery();
				if (rs.next()) {
					id = rs.getInt("id");
					int amount = rs.getInt("amount") + 1;
					
					ps = cxn.prepareStatement("UPDATE group17_rating SET amount = ? WHERE id = ?");
					ps.setInt(1, amount);
					ps.setInt(2, id);
					ps.executeUpdate();
					//System.out.println(ps);
				} else {
				
					ps = cxn.prepareStatement("INSERT group17_rating (company, rating, day, year, amount) VALUES(?, ?, ?, ?, 1)");
					ps.setString(1, company);
					ps.setBoolean(2, rating);
					ps.setInt(3, day);
					ps.setInt(4, year);
					ps.executeUpdate();
					
					ps = cxn.prepareStatement("SELECT id FROM group17_rating WHERE company = ? AND rating = ? AND day = ? AND year = ?");
					ps.setString(1, company);
					ps.setBoolean(2, rating);
					ps.setInt(3, day);
					ps.setInt(4, year);
					rs = ps.executeQuery();
					
					rs.next();
					id = rs.getInt(1);
					
					ps = cxn.prepareStatement("INSERT group17_news_rating (news_id, rating_id) VALUES(?, ?)");
					ps.setInt(1, newsid);
					ps.setInt(2, id);
					ps.executeUpdate();
				}
				
// 				ps.close();
// 				rs.close();
// 				cxn.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception in insertRating " + e);
			System.exit(-1);
		}
	}

	public static void findCompanies(NewsItem ni, int newsid) {
// 		connect();
		try 
		{
			ps = cxn.prepareStatement("SELECT * FROM group17_companies");
			rs = ps.executeQuery();
			while (rs.next()) {
				String symbol = rs.getString("symbol");
				String company = rs.getString("name");
				
				if (Sentiment.company(company, symbol, ni.title, ni.body)) {
					insertRating(symbol, ni.anaysis, newsid);
					RuleEngine.checkRulesNews(symbol, ni);
					//System.out.println(company);
				}
			}
			
// 			ps.close();
// 			rs.close();
// 			cxn.close();
		}
			catch(Exception e)
		{
			System.out.println("Exception in findCompanies " + e);
			System.exit(-1);
		}
	}

	public static int getFinanceData(String type, String company, int time1, int time2) {

// 		connect();
		try
		{
			ps = cxn.prepareStatement("SELECT " + type + " from group17_finance where symbol = ? AND datetime > ? AND datetime < ? ORDER BY datetime desc");			
			ps.setString(1, company);
			ps.setInt(2, time1); 
			ps.setInt(3, time2);
			//System.out.println(ps.toString());
			rs = ps.executeQuery();
			
			if (rs.next()) {
			      int i = rs.getInt(1);
// 			      rs.close();
// 			      ps.close();
// 			      cxn.close();
			      return i;
			} else {
// 				rs.close();
// 				ps.close();
// 				cxn.close();
			}
			
		}
		catch(Exception e)
		{
			System.out.println("Exception in getFinanceData " + e);
			System.exit(-1);
		}

		//no data
		return 0;
	}
	
	public static void insertIncreaseValue(String company, String type, double percent, int time, boolean increase) {
			//get increase for same company and type in the last 5 mins, if there is one add a end time, else create new one
// 			connect();
			try
			{
				ps = cxn.prepareStatement("SELECT id FROM group17_increases WHERE company = ? AND type = ? AND datetime > ? AND increase = ?");
				ps.setString(1, company);
				ps.setString(2, type);
				ps.setInt(3, time - 300); //Any within the last 5 mins
				ps.setBoolean(4, increase);
				rs = ps.executeQuery();
				if (rs.next()) {
					int id = rs.getInt(1);
					//System.out.println(id);
										
					ps = cxn.prepareStatement("UPDATE group17_increases SET datetime_end = ?  WHERE id = ?");
					ps.setInt(1, time);
					ps.setInt(2, id);
					ps.executeUpdate();
				
				} else {
					//System.out.println("adding new");
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
				System.out.println("Exception in insertIncreaseValue " + e);
				System.exit(-1);
			}
	}
	
	public static void insertAlert(String company, int time, int points, String rules, String newsids) {
		
		try
		{
			ps = cxn.prepareStatement("INSERT group17_alerts (datetime, symbol, rule, points, news_ids) VALUES(?, ?, ?, ?, ?)");
					
			ps.setInt(1, time);
			ps.setString(2, company);
			ps.setString(3, rules);
			ps.setInt(4, points);
			ps.setString(5, newsids);
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			System.out.println("Exception in insertAlert " + e);
			System.exit(-1);
		}
		
	
	}
	
	public static int getAlertID(String company, int time) {
		
		try
		{
			ps = cxn.prepareStatement("SELECT id FROM group17_alerts where symbol = ? and datetime = ?");
			ps.setString(1,company);
			ps.setInt(2,time);
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		
		}
		catch(Exception e)
		{
			System.out.println("Exception in getAlertID " + e);
			System.exit(-1);
		}
		return 0;
	}
	public static String[] getCompanies() {
	
		String[] companies = new String[102];
		int i = 0;
		
		try
		{
			ps = cxn.prepareStatement("SELECT symbol FROM group17_companies");
			rs = ps.executeQuery();
			while (rs.next()) {
			
				companies[i] = rs.getString("symbol");
				i++;
				
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in insertIncreaseValue " + e);
			System.exit(-1);
		}
		return companies;
	}
	
	//select percent, datetime, datetime_end, increase from group17_increases where company = 'RR' AND type = 'ask' and datetime > start of today AND ;
	
	public static int[] getIncreaseTimes(String company, String type, int startTime, boolean increase) {
	
		int[] times;
		int count;
		int i = 0;
		try
		{
			ps = cxn.prepareStatement("SELECT COUNT(*) FROM group17_increases where company = ? and type = ? and datetime > ? and increase = ?");
			ps.setString(1, company);
			ps.setString(2, type);
			ps.setInt(3, startTime);
			ps.setBoolean(4, increase);
			rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);
			System.out.println(ps);
			System.out.println(ps);

			if (count > 0) {
				
				times = new int[count];
				
				ps = cxn.prepareStatement("SELECT datetime FROM group17_increases where company = ? and type = ? and datetime > ? and increase = ? order by datetime");
				ps.setString(1, company);
				ps.setString(2, type);
				ps.setInt(3, startTime);
				ps.setBoolean(4, increase);
				rs = ps.executeQuery();
				System.out.println(ps);
				while (rs.next()) {
					
					times[i] = rs.getInt(1);
					System.out.println(times[i]);
					i++;
				
				}
				return times;
				
			}
	
	
		}
		catch(Exception e)
		{
			System.out.println("Exception in getIncreaseTimes " + e);
			System.exit(-1);
		}	
		return null;
	
	}
}
