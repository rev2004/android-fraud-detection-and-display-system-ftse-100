package uk.starfish.marketalert;

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

public class prototype extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	  super.onCreate(savedInstanceState);

    	  String[] alert_name = getResources().getStringArray(R.array.alerts_array);
    	  setListAdapter(new ArrayAdapter<String>(this, R.layout.alert_item, alert_name));
    	  
    	  ListView lv = getListView();
    	  lv.setTextFilterEnabled(true);

    	  lv.setOnItemClickListener(new OnItemClickListener() {
    	    public void onItemClick(AdapterView<?> parent, View view,
    	        int position, long id) {
    	      // When clicked, show the alert info
    	    	startActivityForResult(new Intent(prototype.this, AlertInfo.class), 0); 

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
	    	startActivityForResult(new Intent(prototype.this, InfoPage.class), 0); 
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
}