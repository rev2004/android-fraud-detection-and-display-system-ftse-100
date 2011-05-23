package uk.starfish.marketalert;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;

public class AlertInfo extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_info);
    }

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu, menu);
        return true;
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
	    	startActivityForResult(new Intent(AlertInfo.this, InfoPage.class), 0); 
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
    
    public void finishActivity(View view) {
        finish();
    }
    
    public void report(View view){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Are you sure this alert is incorrect?")
    		   .setTitle("Report Alert")
    		   .setCancelable(true)
    		   .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    		/** Currently doesn't send anything */
    	           public void onClick(DialogInterface dialog, int whichButton){ 
    	        	   dialog.cancel();
    	           }
         });
    	builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
    				public void onClick(DialogInterface dialog, int whichButton){ 
    					dialog.cancel();
	           }
    	 });
    	builder.setIcon(R.drawable.alert_dialog_icon);
        builder.show();    
    }
}