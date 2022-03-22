package com.example.basiccode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.internal.utils.ImageUtil;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    PreviewView previewView;
    Button startButton;
    Button stopButton;
    Button recogButton;
    ImageView imageView;
    String TAG="MainActivity";
    ProcessCameraProvider processCameraProvider;
    int lensFacing = CameraSelector.LENS_FACING_BACK;
    ImageCapture imageCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        previewView=findViewById(R.id.previewView);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        recogButton=findViewById(R.id.RecogButton); //인식버튼
        imageView=findViewById(R.id.imageview);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);

        try{
            processCameraProvider=ProcessCameraProvider.getInstance(this).get();
        }
        catch (ExecutionException e){
            e.printStackTrace();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    bindPreview();
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processCameraProvider.unbindAll();
            }
        });

        recogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageCapture.takePicture(ContextCompat.getMainExecutor(MainActivity.this), new ImageCapture.OnImageCapturedCallback() {
                    @Override
                    public void onCaptureSuccess(@NonNull ImageProxy image){

                        @SuppressLint({"UnsafeExperimentalUsageError", "UnsafeOptInUsageError"})
                        Image mediaImage=image.getImage();
                        Bitmap bitmap = ImageUtil.mediaImageToBitmap(mediaImage);
                        Log.d("MainActivity", Integer.toString(bitmap.getWidth())); //4128
                        Log.d("MainActivity", Integer.toString(bitmap.getHeight())); //3096

                        imageView.setImageBitmap(bitmap);

                        super.onCaptureSuccess(image);
                    }
                });
            }
        });
    }
    void bindPreview(){
        previewView.setScaleType(PreviewView.ScaleType.FIT_CENTER);
        CameraSelector cameraSelector=new CameraSelector.Builder().requireLensFacing(lensFacing).build();
        Preview preview = new Preview.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3) //디폴트 표준 비율
                .build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        processCameraProvider.bindToLifecycle(this, cameraSelector, preview);
    }
    void bindImageCapture() {
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(lensFacing)
                .build();
        imageCapture = new ImageCapture.Builder()
                .build();

        processCameraProvider.bindToLifecycle(this, cameraSelector, imageCapture);
    }


    @Override
    protected void onPause() {
        super.onPause();
        processCameraProvider.unbindAll();
    }
}