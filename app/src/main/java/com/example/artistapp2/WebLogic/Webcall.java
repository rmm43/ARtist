package com.example.artistapp2.WebLogic;

import android.content.Context;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class Webcall {

    public static void webCall(final Context context, String requestURL, String userId)
    {
        String finalURL = requestURL;
        if(requestURL.equals(URL.getFriendsList))
        {
            finalURL += userId;
            final AsyncHttpClient client = new AsyncHttpClient();
            client.get(finalURL, null, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    //
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {

                    JSONParser.parseFriendsList(responseString);
                }
            });
        }



    }
}
