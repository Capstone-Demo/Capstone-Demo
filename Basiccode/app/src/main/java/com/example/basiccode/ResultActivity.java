package com.example.basiccode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class ResultActivity extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        imageView=findViewById(R.id.imageview);

        Intent intent=getIntent();
        Bitmap bitmap= (Bitmap) intent.getParcelableExtra("image");
        imageView.setImageBitmap(bitmap);


    }
}