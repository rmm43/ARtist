package com.example.artistapp2;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Thread.sleep;

public class ExampleActivity extends AppCompatActivity implements Runnable{

    cloudARFragment arFragment;
    String ASSET_3D = "bear.sfb";
    String targetUsername = "null";
    boolean flag = false;
    Anchor anc;

    enum state {
        NONE,
        HOSTING,
        HOSTED
    }
    state anchorState = state.NONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        arFragment = (cloudARFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_ux_fragment);

        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {

            anc = arFragment.getArSceneView().getSession().hostCloudAnchor(hitResult.createAnchor());
            anchorState = state.HOSTING;
           placeModel(anc);
        });

        if(anchorState == state.HOSTING)
        {
            arFragment.getArSceneView().getScene().addOnUpdateListener(frameTime -> {

                Anchor.CloudAnchorState cloudState = anc.getCloudAnchorState();
                if(cloudState == Anchor.CloudAnchorState.SUCCESS)
                {
                    //Do a webcall to put it in the database.
                }
            });
        }

        EditText usernameSelection = findViewById(R.id.usernameSelection);
        usernameSelection.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changeTargetUsername(usernameSelection.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //
            }
        });

        Button getBoardButton = findViewById(R.id.getBoardButton);
        getBoardButton.setOnClickListener(view -> {
            //Send a webcall to get the most recent anchorID from the chosen username
        });
    }

    private void changeTargetUsername(String target)
    {
        targetUsername = target;
    }

    @Override
    public void run(){

        while(flag){
            try{
                sleep(500);
                placeModel(anc);
            }catch(Exception e){
                Log.d("Test", "Error sleeping thread");
            }
        }
    }

    private void placeModel(Anchor anchor) {
        ModelRenderable
                .builder()
                .setSource(this, Uri.parse(ASSET_3D))
                .build()
                .thenAccept(modelRenderable -> addNodeToScene(modelRenderable, anchor))
                .exceptionally(throwable -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(throwable.getMessage()).show();
                    return null;
                });
    }

    private void addNodeToScene(ModelRenderable modelRenderable, Anchor anchor) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);

    }

}
