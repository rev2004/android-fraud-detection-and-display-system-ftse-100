package uk.starfish.marketalert;

import java.io.*;
import java.util.*;

public class AlertDb
{
	private List<Alert> alerts;
	public static String SEP = "_ALERTSEP_";
	public static String FILENAME = "alerts.txt";
	
	public AlertDb()
	{
		this.alerts = new ArrayList<Alert>();
	}

	public void save()
	{
		try
		{
			File file = new File(FILENAME);
			ReadWriteTextFile.setContents(file, encode()); 
		}
		catch (Exception e) { e.printStackTrace(); }
	}

	public void load()
	{
		File file = new File(FILENAME);
		String contents = ReadWriteTextFile.getContents(file);
		decode(contents);
	}

	public void add(Alert alert)
	{
		alerts.add(alert);
	}

	public void delete(String id);
	{
		for (int i = 0; i < alerts.size(); i ++)
		{
			Alert alert = (Alert) alerts.get(i);
			if (alert.id == id)
			{
				alerts.remove(i);
				return;
			}
		}
	}
				

	public void decode(String s)
	{
		alerts.clear();

		if (s.length() > 5)
		{
			String[] alertStr = s.split(SEP);

			for (int i = 0; i < alertStr.length; i ++)
			{
				alerts.add(Alert.decode(alertStr[i]));
			}
		}

		System.out.print("Loaded ");
		System.out.print(alerts.size());
		System.out.println(" alerts");
	}

	public String encode()
	{
		if (alerts.isEmpty())
			return "";
		else
		{
			String[] result = new String[alerts.size()];
			for (int i = 0; i < alerts.size(); i ++)
			{
				Alert alert = (Alert) alerts.get(i);
				String e = alert.encode();
				result[i] = e;
			}

			return Util.implode(result, SEP);
		}
	}
}
