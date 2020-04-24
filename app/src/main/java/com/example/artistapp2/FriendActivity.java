package com.example.artistapp2;

import android.content.Intent;
import android.os.Bundle;

import com.example.artistapp2.Models.User;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FriendActivity extends AppCompatActivity {

    EditText friendET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        friendET = findViewById(R.id.friendEditText);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "This action takes you back to the AR screen", Snackbar.LENGTH_LONG)
                        .setAction("Back", null).show();
                openInformationActivity();
            }
        });

        Button goButton = findViewById(R.id.goButton);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.get().addFriend(friendET.getText().toString());
                Snackbar.make(view, "Added friend: " + friendET.getText(), Snackbar.LENGTH_LONG)
                        .setAction("Go", null).show();
            }
        });
    }

    public void openInformationActivity(){
        Intent intent = new Intent(this, InformationActivity.class);
        startActivity(intent);
    }

}
