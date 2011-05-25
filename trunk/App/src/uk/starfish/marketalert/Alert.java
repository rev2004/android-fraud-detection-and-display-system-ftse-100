package uk.starfish.marketalert;

import java.util.Date;
import java.text.SimpleDateFormat;

// Holds all the data for an alert
public class Alert
{
	// Alert details
	public String id;
	public Date time;
	public Integer points;
	public String company;
	public RuleItem[] rules;
	public NewsItem[] news;

	// Message encoding variables
	private static String SEP = "_SEP_";
	private static String SUBSEP = "_SUBSEP_";

	private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

	// Constructor
	Alert(String id,  Date time, Integer points, String company, RuleItem[] rules, NewsItem[] news)
	{
		this.id = id;
		this.time = time;
		this.points = points;
		this.rules = rules;
		this.news = news;
		this.company = company;
	}

	// Decodes an alert from String format
	// Returns the decoded alert
	public static Alert decode(String msg)
	{
		// Split the message up into an array
		String[] bit = msg.split(SEP);
		try 
		{
			// Create a new alert
			Alert decoded = new Alert(
				bit[0],
				sdf.parse(bit[1]),
				Integer.parseInt(bit[2]),
				bit[3],
				RuleItem.decodeArray(bit[4].split(SUBSEP)),
				NewsItem.decodeArray(bit[5].split(SUBSEP)));
			return decoded;
		}
		catch (Exception e) {return null;}
	}

	// Encodes an alert into string format
	// Returns the encoded alert
	public String encode()
	{
		// Transform this alert into a string
		String encoded = 
			id + SEP + 
			sdf.format(time) + SEP + 
			points.toString() + SEP + 
			company + SEP + 
			Util.implode(RuleItem.encodeArray(rules), SUBSEP) + SEP + 
			Util.implode(NewsItem.encodeArray(news), SUBSEP);

		// Return the encoded alert
		return encoded;
	}
	public String getCompany(){
		return company;
	}
}
