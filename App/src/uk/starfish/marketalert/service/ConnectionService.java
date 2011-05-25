package uk.starfish.marketalert.service;


import java.util.*;
import uk.starfish.marketalert.*;
import uk.starfish.marketalert.Alert;
import uk.starfish.marketalert.AlertsPage;
import uk.starfish.marketalert.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import network.client.*;

public class ConnectionService extends Service {
    public Vector<uk.starfish.marketalert.Alert> alerts;
	public int notifId = 0;
    
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		Log.d("Connection Service: ", "C: Service created...");
		
		alerts = new Vector<Alert>();
		
		/*if (!connected) {
        	Thread cThread = new Thread(new ClientThread());
        	Log.d("ClientActivity", "C: Creating Thread");
        	cThread.start();
        }*/
		Client client = new Client("clintonio.com", 9011);
		
		// Try to connect to the server
		if (!client.connect())
		{
			// Failure
			System.out.println("Failed to connect");
			
			client.reconnectLoop(0, 1000, -1);
		}
		
		Thread cThread = new Thread(new AppAlertHandler(client));
		Log.d("", "");
		cThread.start();

	}
	
	class AppAlertHandler extends AlertHandler	{
		
		// Constructor
		AppAlertHandler(Client client) {
			super(client);
		}

		// When an alert is received from the server
		public void handleAlert(Alert alert)
		{
			
			System.out.println("RECIEVED ALERT");
			System.out.println("ID: " + alert.id);
			System.out.println("Time: " + alert.time);
			System.out.println("Total Points: " + alert.points);
			System.out.println("Company: " + alert.company);
			System.out.println("Rules Broken: ");
			for (RuleItem r : alert.rules)
				System.out.println("	" + r.name + " (points = " + r.points.toString() + ")");
			System.out.println("Related News: ");
			for (NewsItem n : alert.news)
				n.print();
			
			System.out.println();
			
			// Add an alert to the list
			alerts.add(alert);
			
			// produce notification
			statNotif(alert);
		}

		// When the server disconnects
		public void handleDisconnect()
		{
			Log.d("Client Service: ", "Server lost...");
			client.reconnectLoop(0, 1000, -1);
			Log.d("Client Service: ", "Reconnected");
		}
	}

	
	private void statNotif(Alert alert) {
		
		Log.d("Notification", "Notification");
    	String ns = Context.NOTIFICATION_SERVICE;
    	NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
    	
    	int icon = R.drawable.stat_notify_error;
    	CharSequence tickerText = "New Alert!";
    	long when = System.currentTimeMillis();

    	Notification notification = new Notification(icon, tickerText, when);
    	
    	Context context = getApplicationContext();
    	CharSequence contentTitle = "Market Alert";
    	CharSequence contentText = "Company: " +alert.getCompany();
    	Intent notificationIntent = new Intent(this, AlertsPage.class);
    	PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

    	notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
    	
    	int NOTIF_ID = notifId;
		notifId++;

    	mNotificationManager.notify(NOTIF_ID, notification);
    }
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}
	
	@Override
	public void onStart(Intent intent, int startid)
	{
		Log.d("ConnectionService", "Connection Service Started");
	}
}

