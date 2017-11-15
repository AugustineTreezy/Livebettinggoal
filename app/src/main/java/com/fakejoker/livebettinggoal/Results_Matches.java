package com.fakejoker.livebettinggoal;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by FakeJoker on 26/05/2017.
 */

public class Results_Matches {
    public String match;
    public String state;
    public String dateplayed;
    public String time;
    public Results_Matches(JSONObject object){
        try {
            this.match=object.getString("result");
            this.state=object.getString("won");
            this.dateplayed=object.getString("date");
            this.time=object.getString("time");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    public static ArrayList<Results_Matches> fromJson(JSONArray jsonArray){
        ArrayList<Results_Matches> result_match = new ArrayList<Results_Matches>();

        for (int i = 0; i <jsonArray.length(); i++) {

            try {

                result_match.add(new Results_Matches(jsonArray.getJSONObject(i)));

            } catch (JSONException e) {

                e.printStackTrace();

            }

        }
        return result_match;
    }
}
