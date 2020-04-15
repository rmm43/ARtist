package com.example.artistapp2;

import android.os.Bundle;
import com.google.ar.sceneform.ux.ArFragment;
import androidx.appcompat.app.AppCompatActivity;

public class ExampleActivity extends AppCompatActivity {


    ArFragment arFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        arFragment = (ArFragment)getSupportFragmentManager().findFragmentById(R.id.sceneform_ux_fragment);

    }

}
