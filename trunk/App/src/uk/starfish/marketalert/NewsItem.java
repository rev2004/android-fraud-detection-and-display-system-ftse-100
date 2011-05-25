package uk.starfish.marketalert;

/*
<nitem>
	<nsource>Reuters: Business News</nsource>
	<ndate>00:21:25 01/03/2011</ndate>
	<ntitle>D.Boerse-NYSE not seen U.S. security risk</ntitle>
	<nbody>WASHINGTON (Reuters) - A senior U.S. Treasury official said he did not see any national security concerns with Deutsche Boerse's planned takeover of NYSE Euronext .</nbody>
</nitem>
*/

// Date format is "HH:mm:ss dd/MM/yyyy"

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Date;
import java.text.SimpleDateFormat;

public class NewsItem
{
	public String source;
	public Date date;
	public String title;
	public String body;
	public boolean anaysis;

	private static String SEP = "_NEWSSEP_";

	private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
	
	NewsItem(String str)
	{
		decode(str);
	}

	NewsItem(String source, Date date, String title, String body)
	{
		this.source = source;
		this.date = date;
		this.title = title;
		this.body = body;
	}

	public void setAnaysis(boolean anaysis) {
		this.anaysis = anaysis;
	}
			
	public void decode(String str)
	{
		source = getTagData("nsource", str);
		try
		{
			date = sdf.parse(getTagData("ndate", str));
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		title = getTagData("ntitle", str);
		body = getTagData("nbody", str);

	}
	
	public String getTagData(String tag, String str)
	{
		Pattern pattern = Pattern.compile("<" + tag + ">.*</" + tag + ">");
		Matcher matcher = pattern.matcher(str);
		
		if (matcher.find())
		{
			return stripTags(matcher.group());
		}
		else
			return "";
	}
	
	public String stripTags(String str)
	{
		return str.replaceAll("<.*?>", "");
	}
	
	public void print()
	{
		System.out.println("Source: " + source);
		System.out.println("Date: " + sdf.format(date));
		System.out.println("Title: " + title);
		System.out.println("Body: " + body);
	}

	// Decodes a news from String format
	// Returns the decoded news
	public static NewsItem decodeMsg(String msg)
	{
		// Split the message up into an array
		String[] bit = msg.split(SEP);

		NewsItem decoded;

		try
		{
		// Create a new news
		decoded = new NewsItem(bit[0], sdf.parse(bit[1]), bit[2], bit[3]);
		return decoded;
		}
		catch (Exception e) { return null; }
	}

	public static NewsItem[] decodeArray(String[] msg)
	{
		NewsItem[] news = new NewsItem[msg.length];
		for (int i = 0; i < msg.length; i ++)
		{
			news[i] = decodeMsg(msg[i]);
		}
		return news;
	}

	// Encodes an news into string format
	// Returns the encoded news
	public String encode()
	{
		// Transform this news into a string
		String encoded = 
			source + SEP +
			sdf.format(date) + SEP + 
			title + SEP +
			body;

		// Return the encoded news
		return encoded;
	}

	public static String[] encodeArray(NewsItem[]  news)
	{
		String[] encoded = new String[news.length];
		for (int i = 0; i < news.length; i ++)
		{
			encoded[i] = news[i].encode();
		}

		return encoded;
	}
}
