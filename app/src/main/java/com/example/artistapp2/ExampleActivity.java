package com.example.artistapp2;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.artistapp2.Models.Board;
import com.example.artistapp2.WebLogic.Webcall;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class ExampleActivity extends AppCompatActivity implements Runnable{

    cloudARFragment arFragment;
    String ASSET_3D = "bear.sfb";
    String targetUsername = "null";
    boolean flag = false;
    Anchor anc;
    SharedPreferences sp;
    String[] assets;
    int num_assets = 3;
    int counter = 0;

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

        assets = new String[num_assets];
        assets[0] = "bear.sfb";
        assets[1] = "cat.sfb";
        assets[2] = "cow.sfb";

        sp = getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        arFragment = (cloudARFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_ux_fragment);

        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {

        anc = arFragment.getArSceneView().getSession().hostCloudAnchor(hitResult.createAnchor());
            anchorState = state.HOSTING;

            placeModel(anc);

            Anchor.CloudAnchorState cloudState = anc.getCloudAnchorState();
            Log.d("Test", cloudState.toString());
        });

        arFragment.getArSceneView().getScene().addOnUpdateListener(frameTime -> {

            if(anchorState == state.HOSTING)
            {
                Anchor.CloudAnchorState cloudState = anc.getCloudAnchorState();
                Log.d("Test", cloudState.toString());
                if(cloudState == Anchor.CloudAnchorState.SUCCESS)
                {
                    anchorState = state.HOSTED;
                    Webcall.newBoardWebcall(sp.getString("Name", null), anc.getCloudAnchorId());
                    Log.d("Test", "RAN WEBCALL");
                }
            }
        });



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

            Log.d("Test", "Made webcall for retieving hash..");
            Webcall.retrieveBoardWebcall(targetUsername);
            Board mBoard = Board.getInstance();

            ArrayList<String> hashes = Board.getInstance().getHash();

            for(int i = 0; i < hashes.size(); i++)
            {
                Anchor retrievedAnchor = arFragment.getArSceneView().getSession().resolveCloudAnchor(hashes.get(i));
                placeModel(retrievedAnchor);
            }
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
                .setSource(this, Uri.parse(assets[counter%num_assets]))
                .build()
                .thenAccept(modelRenderable -> addNodeToScene(modelRenderable, anchor))
                .exceptionally(throwable -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(throwable.getMessage()).show();
                    return null;
                });
        counter++;
    }

    private void addNodeToScene(ModelRenderable modelRenderable, Anchor anchor) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);

    }

}
