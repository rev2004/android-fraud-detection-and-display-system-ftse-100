package uk.starfish.marketalert;

// Utility library
public class Util
{
	// opposite of String.split()
	public static String implode(String[] pieces, String delimiter)
	{
		// Create a new string
		String result = new String();

		// Check number of pieces
		if (pieces.length == 0)
			return "";

		// For each piece
		for (String piece : pieces)
		{
			// Append the result
			result += piece + delimiter;
		}

		// Return the imploded string
		return result.substring(0, result.length() - delimiter.length());
	}
}
