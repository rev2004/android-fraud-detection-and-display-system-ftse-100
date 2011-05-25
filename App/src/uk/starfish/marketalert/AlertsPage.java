package uk.starfish.marketalert;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import java.util.*;

public class AlertsPage extends ListActivity {
    public Vector<uk.starfish.marketalert.Alert> alerts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        alerts = new Vector<Alert>();
        
        Log.d("Servive Intent: ","Creating service startup intent...");
        Intent serviceIntent = new Intent(this, uk.starfish.marketalert.service.ConnectionService.class);
        Log.d("Servive Intent: ","Firing service startup intent...");
        startService(serviceIntent);
        
        for (int i=0; i<alerts.size(); i++) {
        	alerts[i].company
        	
        }
        
        List<String> alert_name = ;
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
    
    public void alertbox(String title, String body){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage(body)
    		   .setTitle(title)
    		   .setCancelable(true)
    		   .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
    				public void onClick(DialogInterface dialog, int whichButton){ 
    					dialog.cancel();
	           }
    	 });
    	builder.setIcon(R.drawable.alert_dialog_icon);
        builder.show();    
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.help:
        	openHelp();
            return true;
        case R.id.info:
	    	startActivityForResult(new Intent(AlertsPage.this, InfoPage.class), 0); 
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
}