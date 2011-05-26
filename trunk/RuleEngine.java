import network.server.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.*; 

class RuleEngine
{
	public static String[] company;
	public static Server server;

	public static int currentTime;
	public static int points = 0;
	
	public static RuleItem[] rules = new RuleItem[5];

	public static ArrayList<RuleItem> ALrules = new ArrayList<RuleItem>();
	public static ArrayList<NewsItem> ALnews = new ArrayList<NewsItem>();
	
	public static void setup() {
	

		//company = Database.getCompanies();
		setRules();		
	
		getTodayDate();
	
		reset();		
	
	}
	
	public static void checkRulesNews(String company, NewsItem ni) {
		boolean found = false;
		Calendar cal = Calendar.getInstance();
		currentTime = (int) (cal.getTimeInMillis() / 1000);
		int time = currentTime - 1800; //half hour = 60*30
		
		setup();
		
		if (rule0(company, time, ni)) {
			found = true;
		}
		if (rule1(company, time, ni)) {
			found = true;
		}
		if (rule2(company, time, ni)) {
			found = true;
		}
		if (rule3(company, time, ni)) {
			found = true;
		}
		
		if (found) {
			//send alert
			
			addPoints();
			
			Database.insertAlert(company, currentTime, points, rulesToString(), "");
			int alertID = Database.getAlertID(company, currentTime);
			String id =  Integer.toString(alertID);
			
			Alert alert = new Alert(id, new Date(currentTime), points, company, (RuleItem[]) ALrules.toArray(), (NewsItem[]) ALnews.toArray());
			
			server.sendMessageToAll(alert.encode());
		
		}
	}
	
	public static void checkRulesFinance(String company) {
		boolean found = false;
		Calendar cal = Calendar.getInstance();
		currentTime = (int) (cal.getTimeInMillis() / 1000);
		int time = currentTime - 1800; //half hour = 60*30
		
		setup();
		/*
		if (rule4(company, time)) {
			found = true;
		}
		if (rule5(company, time)) {
			found = true;
		}
		if (rule6(company, time)) {
			found = true;
		}
		if (rule7(company, time)) {
			found = true;
		}*/
		
		if (found) {
			//send alert
			
			addPoints();
			
			Database.insertAlert(company, currentTime, points, rulesToString(), "");
			int alertID = Database.getAlertID(company, currentTime);
			String id =  Integer.toString(alertID);
			
			Alert alert = new Alert(id, new Date(currentTime), points, company, (RuleItem[]) ALrules.toArray(), (NewsItem[]) ALnews.toArray());
			
			//server.sendMessageToAll(alert.encode());
		
		}
	}
		
	public static String rulesToString() {
		String x = "";
		boolean first = false;
	
		for (int i=0; i<ALrules.size(); i++) {
			if (!first) {
				x += ALrules.get(i).name + ":" + ALrules.get(i).points;
				first = true;
			} else {
				x += "," + ALrules.get(i).name + ":" + ALrules.get(i).points;
			}
			
		}
		
		return x;
		
	}
	
	public static void addPoints() {
		for (int i=0; i<ALrules.size(); i++) {
			points += ALrules.get(i).points;
		}
	}

	public static void reset() {
		points = 0;
	}	
	
	public static void setRules() {
	
		//Called when new news comes in
		rules[0] = new RuleItem("Sudden increase in bid share prices followed by news release",50);
		rules[1] = new RuleItem("Sudden decrease in bid share prices followed by news release",50);
		rules[2] = new RuleItem("Sudden increase in ask share prices followed by news release",50);
		rules[3] = new RuleItem("Sudden decrease in ask share prices followed by news release",50);

		rules[4] = new RuleItem("Following news release bid share price increases",50);
		rules[5] = new RuleItem("Following news release bid share price decreases",50);
		rules[6] = new RuleItem("Following news release ask share price increases",50);
		rules[7] = new RuleItem("Following news release ask share price decreases",50);
	}
	
	public static boolean rule0(String company, int time, NewsItem ni) {
		int[] bid = Database.getIncreaseTimes(company,"bid",time,true);
		int bidLength = bid.length;
		int askLength = bid.length;
		if (bidLength != 0) {
			// If there is an increase it will be before the news
			for (int i=0; i<bidLength; i++) {
				if (bid[i] < time) {
					ALrules.add(rules[0]);
 					ALnews.add(ni);
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static boolean rule1(String company, int time, NewsItem ni) {
		int[] bid = Database.getIncreaseTimes(company,"bid",time,false);
		int bidLength = bid.length;
		int askLength = bid.length;
		if (bidLength != 0) {
			// If there is an increase it will be before the news
			for (int i=0; i<bidLength; i++) {
				if (bid[i] < time) {
					ALrules.add(rules[1]);
 					ALnews.add(ni);
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static boolean rule2(String company, int time, NewsItem ni) {
		int[] bid = Database.getIncreaseTimes(company,"ask",time,true);
		int bidLength = bid.length;
		int askLength = bid.length;
		if (bidLength != 0) {
			// If there is an increase it will be before the news
			for (int i=0; i<bidLength; i++) {
				if (bid[i] < time) {
					ALrules.add(rules[2]);
 					ALnews.add(ni);
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static boolean rule3(String company, int time, NewsItem ni) {
		int[] bid = Database.getIncreaseTimes(company,"ask",time,false);
		int bidLength = bid.length;
		int askLength = bid.length;
		if (bidLength != 0) {
			// If there is an increase it will be before the news
			for (int i=0; i<bidLength; i++) {
				if (bid[i] < time) {
					ALrules.add(rules[3]);
 					ALnews.add(ni);
					return true;
				}
			}
		}
		
		return false;
	}
	
	/*public static boolean rule4(String company, int time) {
		//Get list of news articles from about company that are today and before time
		//int bidLength = bid.length;
		//int askLength = bid.length;
		if (bidLength != 0) {
			// If there is an increase it will be before the news
			for (int i=0; i<bidLength; i++) {
				if (bid[i] < time) {
					ALrules.add(rules[4]);
 					ALnews.add(ni);
					return true;
				}
			}
		}
		
		return false;
	}*/
	
	/*public static boolean rule5(String company, int time) {
		//Get list of news articles from about company that are today and before time
		//int bidLength = bid.length;
		//int askLength = bid.length;
		if (bidLength != 0) {
			// If there is an increase it will be before the news
			for (int i=0; i<bidLength; i++) {
				if (bid[i] < time) {
					ALrules.add(rules[5]);
 					ALnews.add(ni);
					return true;
				}
			}
		}
		
		return false;
	}*/

	public static int getTodayDate() {
		Date datetime;
		int time = 0;
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		try {
			datetime = sdf.parse(day + "/" + month + "/" + year + " 00:00:01");
			time = (int) (datetime.getTime() / 1000);
		} catch (Exception e) {}
		return time;
	}
}