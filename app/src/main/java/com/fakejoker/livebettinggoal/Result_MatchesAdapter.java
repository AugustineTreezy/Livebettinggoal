package com.fakejoker.livebettinggoal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

/**
 * Created by FakeJoker on 12/08/2017.
 */

public class Result_MatchesAdapter extends ArrayAdapter<Results_Matches> {
    public Result_MatchesAdapter(Context context, int resource, List<Results_Matches> objects) {
        super(context, resource, objects);
    }
    @Override

    public View getView(int position, View convertView, ViewGroup parent) {



        // Get the data item for this position

        Results_Matches std = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.results_list, parent, false);

        }

        // Lookup view for data population

        TextView tvName = convertView.findViewById(R.id.result);
        ImageView tick=convertView.findViewById(R.id.tick);
        TextView date_played=convertView.findViewById(R.id.date_played);
        TextView result_time=convertView.findViewById(R.id.result_time);


        // Populate the data into the template view using the data object
        final String statte=std.state;
        tvName.setText(std.match);
        date_played.setText(std.dateplayed);
        result_time.setText(std.time);


        if (statte.equals("true")){
            tick.setImageResource(R.mipmap.correct_mark);


        }else {
            tick.setImageResource(R.mipmap.wrong_mark);
        }



        // Return the completed view to render on screen

        return convertView;


    }
    public interface IMethodCaller{
        void yourDesiredMethod(String statte);
    }
}
