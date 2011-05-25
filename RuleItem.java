class RuleItem
{
	public String name;
	public Integer points;

	private static String SEP = "_RULESEP_";
	
	RuleItem(String name, Integer points)
	{
		this.name = name;
		this.points = points;
	}

	// Decodes a rule from String format
	// Returns the decoded rule
	public static RuleItem decode(String msg)
	{	
	System.out.println(msg);
		// Split the message up into an array
		String[] bit = msg.split(SEP);

		// Create a new rule
		RuleItem decoded = new RuleItem(bit[0], Integer.parseInt(bit[1]));

		// Return decoded rule
		return decoded;
	}

	public static RuleItem[] decodeArray(String[] msg)
	{
		RuleItem[] rules = new RuleItem[msg.length];
		for (int i = 0; i < msg.length; i ++)
		{
			rules[i] = decode(msg[i]);
		}
		return rules;
	}

	// Encodes an rule into string format
	// Returns the encoded rule
	public String encode()
	{
		// Transform this rule into a string
		String encoded = 
			name + SEP + 
			points.toString();

		// Return the encoded rule
		return encoded;
	}

	public static String[] encodeArray(RuleItem[] rules)
	{
		String[] encoded = new String[rules.length];
		for (int i = 0; i < rules.length; i ++)
		{
			encoded[i] = rules[i].encode();
		}

		return encoded;
	}
}
