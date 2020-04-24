package com.example.artistapp2;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Thread.sleep;

public class ExampleActivity extends AppCompatActivity implements Runnable{

    ArFragment arFragment;
    String ASSET_3D = "bear.sfb";
    boolean flag = false;
    Anchor anc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        arFragment = (ArFragment)getSupportFragmentManager().findFragmentById(R.id.sceneform_ux_fragment);

        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {

           anc = hitResult.createAnchor();
           placeModel(anc);

        });

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
