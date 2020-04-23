package com.example.artistapp2;

import android.net.Uri;
import android.os.Bundle;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ExampleActivity extends AppCompatActivity {

    ArFragment arFragment;
    private String ASSET_3D = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        arFragment = (ArFragment)getSupportFragmentManager().findFragmentById(R.id.sceneform_ux_fragment);

        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            placeModel(hitResult.createAnchor());
        });

    }

    private void placeModel(Anchor anchor) {
        ModelRenderable
                .builder()
                .setSource(
                        this,
                        RenderableSource
                        .builder()
                        .setSource(this, Uri.parse(ASSET_3D), RenderableSource.SourceType.GLTF2)
                        .setScale(.75f)
                        .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                        .build()
                )
                .setRegistryId(ASSET_3D)
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
