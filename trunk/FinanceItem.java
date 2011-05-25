import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

class FinanceItem {
	
	String symbol, name, time;
	double bid, ask;
	int volume;
	Date datetime;
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	FinanceItem(String str) {
	
		decodeFromFeed(str);
	}
	
	public void decodeFromFeed(String str) {
	
	Calendar cal = Calendar.getInstance();
	int day = cal.get(Calendar.DATE);
	int month = cal.get(Calendar.MONTH) + 1;
	int year = cal.get(Calendar.YEAR);
	
		//str = "TLW,TULLOW OIL,13:34:29,1290.00,1291.00,833031"; //testing only
		String[] data = new String[6];
	
		if (str != null) {
			
			data = str.split(",");
			symbol = data[0];
			name = data[1];
			time = day + "/" + month + "/" + year + " " + data[2];
				
			try {
				datetime = sdf.parse(time);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				System.exit(-1);
			}
			
			bid = Double.parseDouble(data[3]);
			ask = Double.parseDouble(data[4]);
			volume = Integer.parseInt(data[5]);
		}
		
	}	
	
	public void print() {
	
		System.out.println("Symbol: " + symbol);
		System.out.println("Time: " + time + " (" + (datetime.getTime() / 1000) + ")");
		System.out.println("Bin: " + bid);
		System.out.println("Ask: " + ask);
		System.out.println("Volume: " + volume);			
	}
}
