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

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        TextView nameText = findViewById(R.id.nameText);
        nameText.setText("" + user.getDisplayName());

        TextView emailText = findViewById(R.id.emailText);
        emailText.setText("" + user.getEmail());

        String friends = "";
        User.getFriends();
        for(String friend:User.getFriends()){

            friends += (friend + "\n");
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
