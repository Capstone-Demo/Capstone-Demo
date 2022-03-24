package com.example.basiccode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.internal.utils.ImageUtil;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
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

    private static final int REQUEST_IMAGE_CODE=101;

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
                    bindImageCapture();
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
                //캡처된 이미지를 제공된 파일 위치에 저장
                imageCapture.takePicture(ContextCompat.getMainExecutor(MainActivity.this),
                        new ImageCapture.OnImageCapturedCallback() {
                    @Override
                    public void onCaptureSuccess(@NonNull ImageProxy image){
                        @SuppressLint({"UnsafeExperimentalUsageError", "UnsafeOptInUsageError"})
                        Image mediaImage=image.getImage();
                        Bitmap bitmap = mediaImageToBitmap(mediaImage);
                        Log.d("MainActivity", Integer.toString(bitmap.getWidth())); //4128
                        Log.d("MainActivity", Integer.toString(bitmap.getHeight())); //3096
                        imageView.setImageBitmap(bitmap);
                        super.onCaptureSuccess(image);
                    }
                });
                //takePicture();

            };
        });
    }
    public static Bitmap mediaImageToBitmap(Image mediaImage) {
        byte[] byteArray = mediaImageToByteArray(mediaImage);
        Bitmap bitmap = null;
        if (mediaImage.getFormat() == ImageFormat.JPEG) {
            bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }
        else if (mediaImage.getFormat() == ImageFormat.YUV_420_888) {
            YuvImage yuvImage = new YuvImage(byteArray, ImageFormat.NV21, mediaImage.getWidth(), mediaImage.getHeight(), null);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            yuvImage.compressToJpeg(new Rect(0, 0, yuvImage.getWidth(), yuvImage.getHeight()), 100, out);
            byte[] imageBytes = out.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        }

        return bitmap;
    }
    public static byte[] mediaImageToByteArray(Image mediaImage) {
// Converting YUV_420_888 data to YUV_420_SP (NV21).
        //https://developer.android.com/reference/android/media/Image.html#getFormat()
        //https://developer.android.com/reference/android/graphics/ImageFormat#JPEG
        //https://developer.android.com/reference/android/graphics/ImageFormat#YUV_420_888
        byte[] byteArray = null;
        if (mediaImage.getFormat() == ImageFormat.JPEG) {
            ByteBuffer buffer0 = mediaImage.getPlanes()[0].getBuffer();
            buffer0.rewind();
            int buffer0_size = buffer0.remaining();
            byteArray = new byte[buffer0_size];
            buffer0.get(byteArray, 0, buffer0_size);
        }
        else if (mediaImage.getFormat() == ImageFormat.YUV_420_888) {
            ByteBuffer buffer0 = mediaImage.getPlanes()[0].getBuffer();
            ByteBuffer buffer2 = mediaImage.getPlanes()[2].getBuffer();
            int buffer0_size = buffer0.remaining();
            int buffer2_size = buffer2.remaining();
            byteArray = new byte[buffer0_size + buffer2_size];
            buffer0.get(byteArray, 0, buffer0_size);
            buffer2.get(byteArray, buffer0_size, buffer2_size);
        }

        return byteArray;
    }

    void bindPreview(){
        previewView.setScaleType(PreviewView.ScaleType.FIT_CENTER);
        CameraSelector cameraSelector=new CameraSelector.Builder()
                .requireLensFacing(lensFacing)
                .build();
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
        //bindToLifecycle은 Camera객체를 반환
        processCameraProvider.bindToLifecycle(this, cameraSelector, imageCapture);
    }

    @Override
    protected void onPause() {
        super.onPause();
        processCameraProvider.unbindAll();
    }

    //사진찍기
    public void takePicture(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //앱이 처리할 수 없는 인텐트 사용시 앱 비정상 종료
        //null이 아닌 경우에만 인텐트 사용가능
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(intent,REQUEST_IMAGE_CODE);
        }
    }

    //미리보기 이미지 가져오기
    //onActivityResult에 전달된 intent의 "data"키 아래 extras에 작은 bitmap으로 사진 인토딩
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==REQUEST_IMAGE_CODE&&resultCode==RESULT_OK){
            //Bundle : 여러가지 타입의 값을 저장하는 Map 클래스
            Bundle extras=data.getExtras();
            Bitmap bitmap=(Bitmap) extras.get("data");
            imageView.setImageBitmap(bitmap);
        }
    }
}