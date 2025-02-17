package com.example.artistapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;
import android.widget.Button;

import com.example.artistapp2.WebLogic.Webcall;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.view.View;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 7117; //any number
    List<AuthUI.IdpConfig> providers;
    Button btn_sign_out;
    Button btn_info;
    Button btn_ar;
    FirebaseUser user;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        btn_sign_out = findViewById(R.id.btn_sign_out);
        btn_sign_out.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //logout

                SharedPreferences.Editor editor = sp.edit();

                editor.putString("Name", null);
                editor.putString("Email", null);
                editor.putString("Uid", null);
                editor.commit();

                AuthUI.getInstance()
                        .signOut(MainActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                btn_sign_out.setEnabled(false);

                                showSignInOptions();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn_info = findViewById(R.id.btn_info);
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "This action takes you to the information screen", Snackbar.LENGTH_LONG)
                        .setAction("INFO", null).show();
                openInformationActivity();
            }
        });

        btn_ar = findViewById(R.id.btn_ar);
        btn_ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "This action takes you to the ar screen", Snackbar.LENGTH_LONG)
                        .setAction("AR", null).show();
                openExampleActivity();
            }
        });

        //Init provider
        providers = Arrays.asList(
                new AuthUI. IdpConfig.EmailBuilder().build(), //Email
                new AuthUI. IdpConfig.PhoneBuilder().build(), //Phone
                // new AuthUI. IdpConfig.FacebookBuilder().build(), //FB
                new AuthUI. IdpConfig.GoogleBuilder().build() //Google
        );

        if(sp.getString("Name", null) == null){
            showSignInOptions();
        }

    }

    public void openInformationActivity(){

        Intent intent = new Intent(this, InformationActivity.class);
        startActivity(intent);
    }

    public void openExampleActivity(){

        Intent intent = new Intent(this, ExampleActivity.class);
        startActivity(intent);
    }

    private void showSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.MyTheme)
                        .build(),MY_REQUEST_CODE
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MY_REQUEST_CODE)
        {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if(resultCode == RESULT_OK)
            {
                //get user
                user = FirebaseAuth.getInstance().getCurrentUser();

                SharedPreferences.Editor editor = sp.edit();

                editor.putString("Name", user.getDisplayName());
                editor.putString("Email", user.getEmail());
                editor.putString("Uid", user.getUid());
                editor.apply();

                Webcall.addUserWebcall(user.getDisplayName(), user.getUid(), user.getEmail());
                Log.d("Test", "webcall made..");

                //show email on toast
                Toast.makeText(  this, ""+user.getEmail(), Toast.LENGTH_SHORT).show();
                //SET BUTTON SIGNOUT
                btn_sign_out.setEnabled(true);
            }
            else
            {
                Toast.makeText(this, ""+response.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
