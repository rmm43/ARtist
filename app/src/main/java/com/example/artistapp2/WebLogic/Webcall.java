package com.example.artistapp2.WebLogic;

import android.content.Context;
import android.util.Log;

import com.example.artistapp2.Board;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class Webcall {

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
                Log.d("Test", "webcall failed..");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("Test", "webcall worked!");
            }
        });
    }

    public static void newBoardWebcall(String username, String boardHash)
    {
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("hash", boardHash);

        final AsyncHttpClient client = new AsyncHttpClient();
        client.post(URL.newBoard, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //TODO add in a connection error message
                Log.d("Test", "webcall failed..");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("Test", "webcall worked!");
            }
        });
    }

    public static void retrieveBoardWebcall(String username)
    {
        String finalAddress = URL.getBoard + username;

        final AsyncHttpClient client = new AsyncHttpClient();
        client.get(finalAddress, null, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //TODO add in a connection error message
                Log.d("Test", "webcall failed..");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("Test", "webcall worked!");
                    JSONParser.parseBoardInformation(responseString);
            }
        });
    }
}
