import com.alchemyapi.api.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import java.io.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class Sentiment {

	public static boolean analysis(String text) {
		//perform anaysis
		try
		{
		AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromString("b63294755373feee8ac3673c5b9d0c780148fc3e");

		Document doc = alchemyObj.TextGetTextSentiment(text);
		String xml = getStringFromDocument(doc);
		System.out.println(xml);
		String analysis = getTagData(xml, "type");
		System.out.println(analysis);
		if (analysis.equals("positive")) {
			return true;
		} else {
			return false;
		}

		}
		catch(Exception e)
		{
			System.out.println("Exception " + e);
			System.exit(-1);
		}


		return true;

	}
	
	public static String getTagData(String tag, String str)
	{
		Pattern pattern = Pattern.compile("<" + tag + ">.*</" + tag + ">");
		Matcher matcher = pattern.matcher(str);
		
		if (matcher.find())
		{
			return (matcher.group());
		}
		else
			return "";
	}
	
	public static String stripTags(String str)
	{
		return str.replaceAll("<.*?>", "");
	}
	
	private static String getStringFromDocument(Document doc) {
	try {
		DOMSource domSource = new DOMSource(doc);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.transform(domSource, result);

		return writer.toString();
	} catch (TransformerException ex) {
		ex.printStackTrace();
		return null;
	}
    }

	public static boolean company(String company, String symbol, String title, String text) {

		if (text.contains(company) | text.contains(symbol) | title.contains(company) | title.contains(symbol)) {
			return true;
		} else {
			return false;
		}
	}

}