package com.fakejoker.livebettinggoal;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by FakeJoker on 26/05/2017.
 */

public class Free_Matches {
    public String tip;
    public String tip_time;
    public String post_date;
    public Free_Matches(JSONObject object){
        try {
            this.tip=object.getString("tips");
            this.tip_time=object.getString("posttime");
            this.post_date=object.getString("postdate");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    public static ArrayList<Free_Matches> fromJson(JSONArray jsonArray){
        ArrayList<Free_Matches> free_match = new ArrayList<Free_Matches>();

        for (int i = 0; i <jsonArray.length(); i++) {

            try {

                free_match.add(new Free_Matches(jsonArray.getJSONObject(i)));

            } catch (JSONException e) {

                e.printStackTrace();

            }

        }
        return free_match;
    }
}
