import java.sql.*;

class FinanceItem {
	
	String symbol, name, time;
	double bid, ask;
	int volume;

	public void FinanceItem(String str) {
	
		decode(str);
	}
	
	public void decode(String str) {
	
		str = "\"TLW.L\",\"TULLOW OIL\",06:20:30,1435.0,1437.0,22581708"; //testing only
		String[] data = new String[6];
		
	
		if (str != null) {
			
			data = str.split(",");
			symbol = data[0].substring(0, 2);
			name = data[1];
			time = data[2];
			bid = Double.parseDouble(data[3]);
			ask = Double.parseDouble(data[4]);
			volume = Integer.parseInt(data[5]);

			// insert it into the database
			Database.insertFinanceItem(this);
		}
		
	}
	
	
	
	public void print() {
	
		System.out.println("Symbol: " + symbol);
		System.out.println("Time: " + time);
		System.out.println("Bin: " + bid);
		System.out.println("Ask: " + ask);
		System.out.println("Volume: " + volume);			
	}
}