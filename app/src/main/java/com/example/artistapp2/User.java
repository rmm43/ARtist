package com.example.artistapp2;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

//file is deprecated
public class User {

    private static User sUser;
    private static List<String> friendsList;

    public static User get()//perhaps needs context?
    {
        if(sUser == null)
        {
            sUser = new User();
        }

        return sUser;

    }

    public static List<String> getFriends()
    {
        if(sUser == null)
        {
            sUser = new User();
        }

        return friendsList;
    }

    public static void addFriend(String friend){
        boolean newFriend = true;
        for(String person:friendsList){
            if(person.equals(friend))
            {
                newFriend = false;
            }
        }
        if(newFriend)
        {
            friendsList.add(friend);
        }
    }

    private User()
    {
        friendsList = new ArrayList<String>();
    }

}
