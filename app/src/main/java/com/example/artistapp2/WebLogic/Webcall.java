package com.example.artistapp2.WebLogic;

import android.content.Context;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class Webcall {

    public static void webCall(final Context context, String requestURL, String userId)
    {
        String finalURL = requestURL;
        if(requestURL.equals(URL.getFriendsList)) {
            finalURL += userId;
            final AsyncHttpClient client = new AsyncHttpClient();
            client.get(finalURL, null, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                    //TODO add in a connection error message

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {

                    JSONParser.parseFriendsList(responseString);
                }
            });
        }
    }

    public static void addUserWebcall(String username, String uid, String email)
    {
        RequestParams params = new RequestParams();
        params.put("user_id", uid);
        params.put("username", username);
        params.put("email", email);

        final AsyncHttpClient client = new AsyncHttpClient();
        client.post(URL.createNewUser, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //TODO add in a connection error message
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                //Do nothing, it succeeded.
            }
        });
    }
}
