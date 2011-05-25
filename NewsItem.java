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

class NewsItem
{
	String source;
	Date date;
	String title;
	String body;
	
	NewsItem(String str)
	{
		decode(str);
	}
			
	public void decode(String str)
	{
		source = getTagData("nsource", str);
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
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
		System.out.println("Date: " + date.toString());
		System.out.println("Title: " + title);
		System.out.println("Body: " + body);
	}
}
