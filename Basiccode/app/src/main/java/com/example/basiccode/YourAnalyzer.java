package com.example.basiccode;

import android.annotation.SuppressLint;
import android.media.Image;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import com.google.mlkit.vision.common.InputImage;

public class YourAnalyzer implements ImageAnalysis.Analyzer{

    //이미지의 텍스트를 인식하려면
    // 1. 바이트배열에서 InputImage개체 만들기
    // 2. 개체를 메소드에 전달

    //CameraX 라이브러리를 사용하는 경우
    @Override
    public void analyze(@NonNull ImageProxy imageProxy) {
        @SuppressLint("UnsafeOptInUsageError")
        Image mediaImage = imageProxy.getImage();
        if (mediaImage != null) {
            //자동으로 회전값 계산산
           InputImage image =
                    InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
            // Pass image to an ML Kit Vision API
            // ...
        }
    }
}
