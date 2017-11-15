package com.fakejoker.livebettinggoal;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

/**
 * Created by FakeJoker on 12/08/2017.
 */

public class Free_MatchesAdapter extends ArrayAdapter<Free_Matches> {
    public Free_MatchesAdapter(Context context, int resource, List<Free_Matches> objects) {
        super(context, resource, objects);
    }
    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position

        Free_Matches std = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.matches_list, parent, false);

        }

        // Lookup view for data population

        TextView tvName = convertView.findViewById(R.id.tip);
        TextView tip_time=convertView.findViewById(R.id.tip_time);
        TextView post_date=convertView.findViewById(R.id.post_date);


        // Populate the data into the template view using the data object

        tvName.setText(std.tip);
        tip_time.setText(std.tip_time);
        post_date.setText(std.post_date);


        // Return the completed view to render on screen

        return convertView;

    }
}
