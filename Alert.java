// Holds all the data for an alert
class Alert
{
	// Alert details
	public String id;
	public String time;
	public String points;
	public String[] rules;
	public String[] news;
	public String[] companies;

	// Message encoding variables
	private static String SEP = "{sep}";
	private static String SUBSEP = "{subsep}";

	// Constructor
	Alert(String id,  String time, String points, String[] rules, String[] news, String[] companies)
	{
		this.id = id;
		this.time = time;
		this.points = points;
		this.rules = rules;
		this.news = news;
		this.companies = companies;
	}

	// Decodes an alert from String format
	// Returns the decoded alert
	public static Alert decode(String msg)
	{
		// Split the message up into an array
		String[] bit = msg.split(SEP);

		// Create a new alert
		Alert decoded = new Alert(bit[0], bit[1], bit[2], bit[3].split(SUBSEP), bit[4].split(SUBSEP), bit[5].split(SUBSEP));

		// Return decoded alert
		return decoded;
	}

	// Encodes an alert into string format
	// Returns the encoded alert
	public String encode()
	{
		// Transform this alert into a string
		String encoded = 
			id + SEP + 
			time + SEP + 
			points + SEP + 
			Util.implode(rules, SUBSEP) + SEP + 
			Util.implode(news, SUBSEP) + SEP + 
			Util.implode(companies, SUBSEP);

		// Return the encoded alert
		return encoded;
	}
}
