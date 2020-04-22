package com.example.artistapp2;

import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.List;
import com.google.firebase.auth.FirebaseUser;

//file is deprecated
public class User {

    private static User sUser;
    private static List<String> friendsList;
    private static SharedPreferences sharedpreferences;

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

    public static void setPreference(SharedPreferences sp){
        sharedpreferences = sp;
    }

    public static SharedPreferences getPreference(){

        return sharedpreferences;
    }


    public User(){
        friendsList = new ArrayList<String>();
    }

    public User(FirebaseUser user)
    {

        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString("Name", user.getDisplayName());
        editor.putString("Email", user.getEmail());
        editor.putString("Uid", user.getUid());
        editor.commit();
    }

}
