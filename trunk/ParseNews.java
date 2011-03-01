/*
<nitem>
	<nsource>Reuters: Business News</nsource>
	<ndate>00:21:25 01/03/2011</ndate>
	<ntitle>D.Boerse-NYSE not seen U.S. security risk</ntitle>
	<nbody>WASHINGTON (Reuters) - A senior U.S. Treasury official said he did not see any national security concerns with Deutsche Boerse's planned takeover of NYSE Euronext .</nbody>
</nitem>
*/

// Date format is "HH:mm:ss dd/MM/yyyy"

import javax.xml.parsers.*;
import org.xml.sax.InputSource;
import org.w3c.dom.*;
import java.io.*;

import java.util.Date;
import java.text.SimpleDateFormat;


class ParseNews
{
	public static void parseXML(String data)
	{
		try
		{
			// set up the DOM parser
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(data));
			
			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("nitem");
			
			// iterate through the news items
			for (int i = 0; i < nodes.getLength(); i ++)
			{
				Element element = (Element) nodes.item(i);
				
				NodeList source = element.getElementsByTagName("nsource");
				Element line = (Element) source.item(0);
				String sourceStr = getCharacterDataFromElement(line);
				
				NodeList date = element.getElementsByTagName("ndate");
				line = (Element) date.item(0);
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
				Date dateDate = sdf.parse(getCharacterDataFromElement(line));
				
				NodeList title = element.getElementsByTagName("ntitle");
				line = (Element) title.item(0);
				String titleStr = getCharacterDataFromElement(line);
				
				NodeList body = element.getElementsByTagName("nbody");
				line = (Element) body.item(0);
				String bodyStr = getCharacterDataFromElement(line);
				
				// create a NewsItem object from the information
				NewsItem newsItem = new NewsItem(sourceStr, dateDate, titleStr, bodyStr);
				
				newsItem.print();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static String getCharacterDataFromElement(Element e)
	{
		Node child = e.getFirstChild();
		if (child instanceof CharacterData)
		{
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "?";
	}
}

class NewsItem
{
	String source;
	Date date;
	String title;
	String body;
	
	NewsItem(String source, Date date, String title, String body)
	{
		this.source = source;
		this.date = date;
		this.title = title;
		this.body = body;
	}
	
	public void print()
	{
		System.out.println("Source: " + source);
		System.out.println("Date: " + date.toString());
		System.out.println("Title: " + title);
		System.out.println("Body: " + body);
	}
}