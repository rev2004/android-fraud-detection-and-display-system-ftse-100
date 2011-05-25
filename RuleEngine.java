import network.server.*;

class RuleEngine extends Thread
{
	public String[] company;
	Server server;
	
	RuleEngine(Server server)
	{
		this.server = server;
		this.start();
	}
	
	public void run() {
		
		company = Database.getCompanies();
		
		while(true) {
		
			// for each company check each rule, run every 1 min
			try
			{
			Thread.sleep(60*1000);
			} catch (Exception e) {}
			//ask increase followed by decrease, less than 10 mins 
		
		
		
		
		
		
		
		}	
	}
	

}