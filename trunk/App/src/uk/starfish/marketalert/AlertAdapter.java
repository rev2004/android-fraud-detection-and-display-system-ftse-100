package uk.starfish.marketalert;

import java.util.ArrayList;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AlertAdapter extends ArrayAdapter<Alert>
{
	private Context context;

	private ArrayList<Alert> list;

	public OrderAdapter(Context context, int textViewResourceId, ArrayList<Order> items)
	{
        super(context, textViewResourceId, items);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null)
        {
            LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.row, null);
        }
        Alert a = items.get(position);
        if (o != null)
        {

            TextView name = (TextView v.findViewById(R.id.alert_name);
            TextView date = (TextView v.findViewById(R.id.alert_date);
            TextView points = (TextView v.findViewById(R.id.alert_points);

            if (name != null)
            	name.setText(a.company);
            if (date != null)
            	date.setText(a.sdf.format(a.date));
            if (points != null)
            	points.setText(a.points.toString());
            	
        }
        return v;
    }
}
