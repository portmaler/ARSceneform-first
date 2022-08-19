package com.devya.arscenforme;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class MainActivity extends AppCompatActivity {

    private ArFragment arFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arFragment = (ArFragment)getSupportFragmentManager().findFragmentById(R.id.arFragment);

        arFragment.setOnTapArPlaneListener(((hitResult, plane, motionEvent) -> {
            Anchor anchor = hitResult.createAnchor();
            ModelRenderable.builder()
                    .setSource(this, Uri.parse("TocoToucan.sfb"))
                    .build()
                    .thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable));
        }));

    }

    private void addModelToScene(Anchor anchor, ModelRenderable modelRenderable) {

        AnchorNode node = new AnchorNode(anchor);
        TransformableNode model = new TransformableNode(arFragment.getTransformationSystem());
        model.setParent(node);
        model.setRenderable(modelRenderable);

        arFragment.getArSceneView().getScene().addChild(node);
        model.select();

        // Create the transformable model and add it to the anchor.
    }
}