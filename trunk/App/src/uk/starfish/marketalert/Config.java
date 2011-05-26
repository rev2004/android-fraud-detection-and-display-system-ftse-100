package uk.starfish.marketalert;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class Config
{
    public String host;
    public int port;
    public int points;
    SharedPreferences pref;

    public Config(Activity activity)
    {
    	// Get the app's shared preferences
        pref = PreferenceManager.getDefaultSharedPreferences(activity);

        // load preferences
		load();
    }

    public load()
    {
		pref.getString("host", "clintonio.com");
		pref.getInt("port", 9011);
		pref.getInt("points", 0);
    }

    public save()
    {
		SharedPreferences.Editor editor = pref.edit();
        editor.putString("host", host);
        editor.putInt("port", port);
        editor.putInt("points", points);
        editor.commit(); // Very important
    }
}
