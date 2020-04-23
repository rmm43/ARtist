package com.example.artistapp2;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        FirebaseUser fbuser = FirebaseAuth.getInstance().getCurrentUser();

        TextView nameText = findViewById(R.id.nameText);

        try{
            nameText.setText(fbuser.getDisplayName());
        }catch(Exception e){
            Log.d("test", "Null pointer exception caught in user.getDisplayName()");
        }

        TextView emailText = findViewById(R.id.emailText);
        emailText.setText(fbuser.getEmail());

        StringBuilder friends = new StringBuilder();
        for(String friend:User.getFriends()){

            friends.append(friend);
            friends.append("\n");
        }
        TextView friendText = findViewById(R.id.friendText);
        friendText.setText(friends);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "This action takes you back to the AR screen", Snackbar.LENGTH_LONG)
                        .setAction("Back", null).show();
                openMainActivity();
            }
        });

        Button friendButton = findViewById(R.id.friendButton);
        friendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "This action takes you to the add a friend screen", Snackbar.LENGTH_LONG)
                        .setAction("NEXT", null).show();
                openFriendActivity();
            }
        });
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openFriendActivity(){
        Intent intent = new Intent(this, FriendActivity.class);
        startActivity(intent);
    }
}
