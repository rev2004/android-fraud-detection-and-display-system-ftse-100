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
	
	public static RuleItem[] rules = new RuleItem[4];
	
	public static ArrayList<RuleItem> ALrules = new ArrayList<RuleItem>();
	public static ArrayList<NewsItem> ALnews = new ArrayList<NewsItem>();
	
	public static void setup(Server server) {
	

		//company = Database.getCompanies();
		setRules();		
	
		getTodayDate();
	
		reset();
		
	
	}
	
	public static void checkRules(String company) {
		boolean found = false;
		Calendar cal = Calendar.getInstance();
		currentTime = (int) (cal.getTimeInMillis() / 1000);
		int time = currentTime - 1800; //half hour = 60*30
		
		if (rule0(company, time)) {
			found = true;
		}
		if (rule1(company, time)) {
			found = true;
		}
		if (rule2(company, time)) {
			found = true;
		}
		if (rule3(company, time)) {
			found = true;
		}
		
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
	
	
	
	public static void setRules() {
	
		rules[0] = new RuleItem("Large increase in ask price followed shortly by decrease",20);
		rules[1] = new RuleItem("Large decrease in ask price followed shortly by increase",20);
		rules[2] = new RuleItem("Large increase in bid price followed shortly by decrease",20);
		rules[3] = new RuleItem("Large decrease in bid price followed shortly by increase",20);
	}
	
	public static void reset() {
		points = 0;
	}
	
	public static boolean rule0(String company, int time) {
		int[] askI = Database.getIncreaseTimes(company,"ask",time,true);
		int[] askD = Database.getIncreaseTimes(company,"ask",time,false);
		int askILength = askI.length;
		int askDLength = askD.length;
		if (askILength != 0 || askDLength != 0) {
			for (int i=0; i<askILength; i++) {
			
				for (int j=0; j<askDLength; j++) {
				
					if (((Math.abs(askI[i] - askD[j])) <= 600) && (askI[i] < askD[j])) {
						ALrules.add(rules[0]);
						return true;
					} 
				}	
			}		
		} 
		return false;
	}
	
	public static boolean rule1(String company, int time) {
		int[] askI = Database.getIncreaseTimes(company,"ask",time,true);
		int[] askD = Database.getIncreaseTimes(company,"ask",time,false);
		int askILength = askI.length;
		int askDLength = askD.length;
		if (askILength != 0 || askDLength != 0) {
			for (int i=0; i<askILength; i++) {
			
				for (int j=0; j<askDLength; j++) {
				
					if (((Math.abs(askI[i] - askD[j])) <= 600) && (askI[i] > askD[j])) {
						ALrules.add(rules[1]);
						return true;
					} 
				}	
			}
		
		} 
		return false;
	}
	
	public static boolean rule2(String company, int time) {
		int[] bidI = Database.getIncreaseTimes(company,"bid",time,true);
		int[] bidD = Database.getIncreaseTimes(company,"bid",time,false);
		int bidILength = bidI.length;
		int bidDLength = bidD.length;
		if (bidILength != 0 || bidDLength != 0) {
			for (int i=0; i<bidILength; i++) {
			
				for (int j=0; j<bidDLength; j++) {
				
					if (((Math.abs(bidI[i] - bidD[j])) <= 600) && (bidI[i] < bidD[j])) {
						ALrules.add(rules[2]);
						return true;
					} 
				}	
			}
		
		} 
		return false;
	}
	
	public static boolean rule3(String company, int time) {
		int[] bidI = Database.getIncreaseTimes(company,"bid",time,true);
		int[] bidD = Database.getIncreaseTimes(company,"bid",time,false);
		int bidILength = bidI.length;
		int bidDLength = bidD.length;
		if (bidILength != 0 || bidDLength != 0) {
			for (int i=0; i<bidILength; i++) {
			
				for (int j=0; j<bidDLength; j++) {
				
					if (((Math.abs(bidI[i] - bidD[j])) <= 600) && (bidI[i] > bidD[j])) {
						ALrules.add(rules[2]);
						return true;
					} 
				}	
			}
		
		} 
		return false;
	}
	
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