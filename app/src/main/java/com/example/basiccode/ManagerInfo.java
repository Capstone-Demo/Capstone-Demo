package com.example.basiccode;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ManagerInfo extends AppCompatActivity {
    PreviewView previewView;
    Button startButton;
    Button stopButton;
    Button recogButton;
    ImageView imageView;
    String TAG="MainActivity";
    ProcessCameraProvider processCameraProvider;
    int lensFacing = CameraSelector.LENS_FACING_BACK;
    ImageCapture imageCapture;

    FirebaseVisionImage visionImage;

    private TextRecognizer textRecognizer ;
    private int scriptLang = -1;

    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_camera);
        previewView=findViewById(R.id.previewView);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        recogButton=findViewById(R.id.RecogButton); //인식버튼
        imageView=findViewById(R.id.imageview);

        view=findViewById(R.id.View); //빨간 박스
        
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

        //카메라 퍼미션 허용버튼
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(ManagerInfo.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    bindPreview();
                    bindImageCapture();
                    //빨간 박스 동적 크기 조정
                    FrameLayout.LayoutParams lp = null;
                    lp = (FrameLayout.LayoutParams) view.getLayoutParams();
                    lp.height=previewView.getHeight()/3;
                    lp.width=previewView.getWidth()/2;
                    view.setLayoutParams(lp);
                }
            }
        });

        //카메라 퍼미션 불허용버튼
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processCameraProvider.unbindAll();
            }
        });

        //주차인식 버튼
        recogButton.setOnClickListener(new View.OnClickListener() {
            Bitmap bitmap;
            Bitmap rotatedBitmap;
            @Override
            public void onClick(View v) {
                imageCapture.takePicture(ContextCompat.getMainExecutor(ManagerInfo.this),
                        new ImageCapture.OnImageCapturedCallback() {
                            @Override
                            public void onCaptureSuccess(@NonNull ImageProxy image){
                                analyze(image);
                                image.close();
                            }
                        });
            };
        });
    }
    @SuppressLint("UnsafeOptInUsageError")
    public void analyze(@NonNull ImageProxy imageProxy) {
        Bitmap bitmap;
        Bitmap rotatedBitmap;
        Bitmap sliceBitmap;

        @SuppressLint({"UnsafeExperimentalUsageError", "UnsafeOptInUsageError"})
        Image mediaImage=imageProxy.getImage();
        bitmap = mediaImageToBitmap(mediaImage);

        //비트맵 이미지 슬라이스
        sliceBitmap = Bitmap.createBitmap(bitmap, bitmap.getWidth()/4, bitmap.getHeight()/4, bitmap.getWidth()/2, bitmap.getHeight()/2);
        InputImage image = InputImage.fromBitmap(sliceBitmap, imageProxy.getImageInfo().getRotationDegrees());
        imageView.setImageBitmap(sliceBitmap);


        //korean version
        TextRecognizer textRecognizer = TextRecognition.getClient(new KoreanTextRecognizerOptions.Builder().build());

        textRecognizer.process(image)
                .addOnSuccessListener(new OnSuccessListener<Text>() {
                    @Override
                    public void onSuccess(Text text) {
                        Map<String, Object> textResult = new HashMap<>();

                        textResult.put("text", text.getText());

                        List<Map<String, Object>> textBlocks = new ArrayList<>();
                        for (Text.TextBlock block : text.getTextBlocks()) {
                            Map<String, Object> blockData = new HashMap<>();

                            addData(blockData,
                                    block.getText(),
                                    block.getBoundingBox(),
                                    block.getCornerPoints(),
                                    block.getRecognizedLanguage());

                            List<Map<String, Object>> textLines = new ArrayList<>();
                            for (Text.Line line : block.getLines()) {
                                Map<String, Object> lineData = new HashMap<>();

                                addData(lineData,
                                        line.getText(),
                                        line.getBoundingBox(),
                                        line.getCornerPoints(),
                                        line.getRecognizedLanguage());

                                List<Map<String, Object>> elementsData = new ArrayList<>();
                                for (Text.Element element : line.getElements()) {
                                    Map<String, Object> elementData = new HashMap<>();

                                    addData(elementData,
                                            element.getText(),
                                            element.getBoundingBox(),
                                            element.getCornerPoints(),
                                            element.getRecognizedLanguage());

                                    elementsData.add(elementData);
                                }
                                lineData.put("elements", elementsData);
                                textLines.add(lineData);
                            }
                            blockData.put("lines", textLines);
                            textBlocks.add(blockData);
                        }
                        textResult.put("blocks", textBlocks);
                        showDialog_OCR2(textResult.get("text").toString());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ManagerInfo.this, "wrong", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    private void addData(Map<String, Object> addTo,
                         String text,
                         Rect rect,
                         Point[] cornerPoints,
                         String recognizedLanguage) {
        List<String> recognizedLanguages = new ArrayList<>();
        recognizedLanguages.add(recognizedLanguage);
        List<Map<String, Integer>> points = new ArrayList<>();
        addPoints(cornerPoints, points);
        addTo.put("points", points);
        addTo.put("rect", getBoundingPoints(rect));
        addTo.put("recognizedLanguages", recognizedLanguages);
        addTo.put("text", text);
    }
    private void addPoints(Point[] cornerPoints, List<Map<String, Integer>> points) {
        for (Point point : cornerPoints) {
            Map<String, Integer> p = new HashMap<>();
            p.put("x", point.x);
            p.put("y", point.y);
            points.add(p);
        }
    }
    private Map<String, Integer> getBoundingPoints(Rect rect) {
        Map<String, Integer> frame = new HashMap<>();
        frame.put("left", rect.left);
        frame.put("right", rect.right);
        frame.put("top", rect.top);
        frame.put("bottom", rect.bottom);
        return frame;
    }
    private void closeDetector() {
        if(textRecognizer==null) return;
        textRecognizer.close();
        textRecognizer = null;
    }

    //카메라 프리뷰 설정
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
    //카메라 캡쳐
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
    //사용자정의메소드
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
    public static Bitmap rotateBitmap(Bitmap bitmap, float degree){
        try{
            Matrix matrix = new Matrix();
            matrix.postRotate(degree);
            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            return rotatedBitmap;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    void showDialog_OCR2(String Text){
        AlertDialog.Builder builder=new AlertDialog.Builder(ManagerInfo.this)
                .setMessage("차량번호\n"+Text)
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String carnum=Text.toString(); //차량번호
                        Intent intent=new Intent(getApplicationContext(),ManagerInfoResult.class);
                        intent.putExtra("carnum",carnum);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();
    }
}