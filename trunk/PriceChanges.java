import java.util.HashMap;
import java.io.*;

	class PriceChanges {
	
		public static int range = 20;
		public static int[] times = {5};
		public static int length = times.length;

		//Check for volume fraud, get volume data from 5,10,30,60 mins ago
		public static void volumeCheck(String company, int volume, int time) {
		      		      
		      int[] oldvolume = new int[length];

		      for (int i=0; i<length; i++) {
					oldvolume[i] = Database.getFinanceData("volume", company, time - (times[i]*60) - range, time - (times[i]*60) + range);
					if (oldvolume[i] != 0) {

						if (oldvolume[i] * 1.06 > volume) {
							//Store company name, time of increase and say it was volume increase
								Database.insertIncreaseValue(company,"volume",(volume / oldvolume[i]),time, true);
						}
					}
		      }

		     
		}
		
		public static void askCheck(String company, double ask, int time) {
		      
		     // int[] times = {5};
		      //int length = times.length;
		      int[] oldask = new int[length];

		      for (int i=0; i<length; i++) {
			      oldask[i] = Database.getFinanceData("ask", company, time - (times[i]*60) - range, time - (times[i]*60) + range);
					if (oldask[i] != 0) {
					
						if (oldask[i] * 1.05 > ask) {
							//Store company name, time of increase and say it was volume increase
								Database.insertIncreaseValue(company,"ask",(ask / oldask[i]),time, true);
								RuleEngine.checkRules(company);
						} else if (oldask[i] * 0.95 < ask) {
								Database.insertIncreaseValue(company,"ask",(ask / oldask[i]),time, false);
								RuleEngine.checkRules(company);
						}
					}
		      }

		     
		}
		
		public static void bidCheck(String company, double bid, int time) {
		      
		      int[] oldbid = new int[length];

		      for (int i=0; i<length; i++) {
			      oldbid[i] = Database.getFinanceData("bid", company, time - (times[i]*60) - range, time - (times[i]*60) + range);
					if (oldbid[i] != 0) {
						if (oldbid[i] * 1.05 > bid) {
						//Store company name, time of increase and say it was volume increase
							Database.insertIncreaseValue(company,"bid",(bid / oldbid[i]),time, true);
							RuleEngine.checkRules(company);
						} else if (oldbid[i] * 0.95 < bid) {
							Database.insertIncreaseValue(company,"bid",(bid / oldbid[i]),time, false);
							RuleEngine.checkRules(company);
						}
					}
		      }

		     
		}
	
		
	}


