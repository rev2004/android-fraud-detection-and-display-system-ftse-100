package uk.starfish.marketalert;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import network.client.Client;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

public class AlertsPage extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	  super.onCreate(savedInstanceState);
    	  
    	  //connect();

    	  String[] alert_name = getResources().getStringArray(R.array.alerts_array);
    	  setListAdapter(new ArrayAdapter<String>(this, R.layout.alert_item, alert_name));
    	  
    	  ListView lv = getListView();
    	  lv.setTextFilterEnabled(true);

    	  lv.setOnItemClickListener(new OnItemClickListener() {
    	    public void onItemClick(AdapterView<?> parent, View view,
    	        int position, long id) {
    	      // When clicked, show the alert info
    	    	startActivityForResult(new Intent(AlertsPage.this, AlertDetailsPage.class), 0); 

    	    }
    	  });
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.delete:
        	deleteAlerts();
            return true;
        case R.id.help:
        	openHelp();
            return true;
        case R.id.info:
	    	startActivityForResult(new Intent(AlertsPage.this, InfoPage.class), 0); 
            return true;
        case R.id.notifications:
        	openNotificationSettings();
            return true;
        case R.id.preferences:
        	openPreferences();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

	void deleteAlerts() {}
	void openHelp() {}
	void openNotificationSettings() {}
	void openPreferences() {}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu, menu);
        return true;
    }
    
    protected void alertbox(String title, String mymessage){
    /** basic alert box function*/
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage(mymessage)
    		   .setTitle(title)
    		   .setCancelable(true)
    		   .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int whichButton){ 
    	        	   dialog.cancel();
    	           }
          });
          builder.show();    }
    

	public void connect () {
		// Create a new client object
		Client client = new Client("86.25.186.203", 1234);

		// Try to connect to the server
		if (!client.connect())
		{
			// Failure
			Toast.makeText(getApplicationContext(), 
					"Connection failed", Toast.LENGTH_SHORT).show();
			
			client.reconnectLoop(0, 1000, -1);
		}

		Toast.makeText(getApplicationContext(), 
				"Connected", Toast.LENGTH_SHORT).show();
		
		// Create the handler object
		AppAlertHandler clientHandler = new AppAlertHandler(client);

		// This next bit of code is just for testing the handler.
		// Basically it just sends a request for an alert every time the user
		// presses return.
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		try
		{
			while (input.readLine() != null)
			{
				clientHandler.requestAlert("4");
			}
		}
		catch (Exception e)
		{
            System.err.println(e.getMessage());
			System.exit(-1);
        }
	}
}

// Handler class
class AppAlertHandler extends AlertHandler {
	// Constructor
	AppAlertHandler(Client client) { super(client); }

	// When an alert is received from the server
	public void handleAlert(Alert alert)
	{
	
		/*
		System.out.println("RECIEVED ALERT");
		System.out.println("ID: " + alert.id);
		System.out.println("Time: " + alert.time);
		System.out.println("Points: " + alert.points);
		System.out.println("Rules Broken: " + Util.implode(alert.rules, ","));
		System.out.println("Related News: " + Util.implode(alert.news, ","));
		System.out.println("Companies Involved: " + Util.implode(alert.companies, ","));
		System.out.println();
		*/
	}

	// When the server disconnects
	public void handleDisconnect()
	{
		client.reconnectLoop(0, 1000, -1);
	}
}
