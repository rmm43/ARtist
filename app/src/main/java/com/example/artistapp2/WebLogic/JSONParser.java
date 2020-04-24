package com.example.artistapp2.WebLogic;

import android.util.Log;

import com.example.artistapp2.Board;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONParser {

    public static void parseBoardInformation(String response)
    {
        try
        {
            JSONArray array = new JSONArray(response);
            for(int i = 0; i < array.length(); i++)
            {
                JSONObject obj = array.getJSONObject(i);
                Board.getInstance().addHash(obj.getString("board_hash"));
            }
        }
        catch(Exception e)
        {
            Log.d("Test", "JSON Parser Exception" + e.toString());
        }
    }

}
