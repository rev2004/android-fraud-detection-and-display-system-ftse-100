package uk.starfish.marketalert;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class InfoPage extends Activity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
    }
    
    public void finishActivity(View view) {
        finish();
    }
}