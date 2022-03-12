package com.example.camera_test;//package com.example.camera_test;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.pm.PackageManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.SurfaceView;
//
//import org.opencv.android.BaseLoaderCallback;
//import org.opencv.android.CameraBridgeViewBase;
//import org.opencv.android.LoaderCallbackInterface;
//import org.opencv.android.OpenCVLoader;
//import org.opencv.core.Mat;
//
//import java.util.Collections;
//import java.util.List;
//
//import static android.Manifest.permission.CAMERA;
//
//public class MainActivity extends AppCompatActivity
//        implements CameraBridgeViewBase.CvCameraViewListener2 {
//
//    private int m_Camidx = 0;//front : 1, back : 0
//    private CameraBridgeViewBase m_CameraView;
//
//    private Mat matInput;
//
//    private static final int CAMERA_PERMISSION_CODE = 200;
//    private static final String TAG = "opencv";
//
//    // Used to load the 'native-lib' library on application startup.
//    static {
//        System.loadLibrary("camera_test");
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        m_CameraView = (CameraBridgeViewBase)findViewById(R.id.activity_surface_view);
//        m_CameraView.setVisibility(SurfaceView.VISIBLE);
//        m_CameraView.setCvCameraViewListener(this);
//        m_CameraView.setCameraIndex(m_Camidx);
//
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        boolean _Permission = true; //변수 추가
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){//최소 버전보다 버전이 높은지 확인
//            if(checkSelfPermission(CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(new String[]{CAMERA}, CAMERA_PERMISSION_CODE);
//                _Permission = false;
//            }
//        }
//        if(_Permission){
//            //여기서 카메라뷰 받아옴
//            onCameraPermissionGranted();
//        }
//    }
//
//    @Override
//    public void onResume()
//    {
//        super.onResume();
//
//        if (OpenCVLoader.initDebug()) {
//            Log.d(TAG, "onResum :: OpenCV library found inside package. Using it!");
//            m_LoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
//        }
//    }
//
//
//
//    @Override
//    public void onPause()
//    {
//        super.onPause();
//        if (m_CameraView != null)
//            m_CameraView.disableView();
//    }
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//
//        if (m_CameraView != null)
//            m_CameraView.disableView();
//    }
//
//    private BaseLoaderCallback m_LoaderCallback = new BaseLoaderCallback(this) {
//        @Override
//        public void onManagerConnected(int status) {
//            switch (status) {
//                case LoaderCallbackInterface.SUCCESS:
//                {
//                    m_CameraView.enableView();
//                } break;
//                default:
//                {
//                    super.onManagerConnected(status);
//                } break;
//            }
//        }
//    };
//
//    protected void onCameraPermissionGranted() {
//        List<? extends CameraBridgeViewBase> cameraViews = getCameraViewList();
//        if (cameraViews == null) {
//            return;
//        }
//        for (CameraBridgeViewBase cameraBridgeViewBase: cameraViews) {
//            if (cameraBridgeViewBase != null) {
//                cameraBridgeViewBase.setCameraPermissionGranted();
//            }
//        }
//    }
//
//    protected List<? extends CameraBridgeViewBase> getCameraViewList() {
//        return Collections.singletonList(m_CameraView);
//    }
//
//    public native String stringFromJNI();
//
//    @Override
//    public void onCameraViewStarted(int width, int height) {
//
//    }
//
//    @Override
//    public void onCameraViewStopped() {
//
//    }
//
//    @Override
//    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
//        matInput = inputFrame.rgba();
//        return matInput;
//    }
//}

<<<<<<< HEAD
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
=======
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
>>>>>>> parent of aeb9edc (camera setting finish and permission error clear)
import android.widget.TextView;
import android.widget.Toast;

<<<<<<< HEAD
import androidx.appcompat.app.AppCompatActivity;

import com.example.camera_test.CameraSurfaceView;
import com.example.camera_test.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    TessBaseAPI tessBaseAPI;

    Button button;
    ImageView imageView;
    CameraSurfaceView surfaceView;
    TextView textView;
=======
import com.example.camera_test.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'camera_test' library on application startup.
    static {
        System.loadLibrary("camera_test");
    }
>>>>>>> parent of aeb9edc (camera setting finish and permission error clear)

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

<<<<<<< HEAD
        imageView = findViewById(R.id.imageView);
        surfaceView = findViewById(R.id.surfaceView);
        textView = findViewById(R.id.textView);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capture();
            }
        });

        tessBaseAPI = new TessBaseAPI();
        String dir = getFilesDir() + "/tesseract";
        if(checkLanguageFile(dir+"/tessdata"))
            tessBaseAPI.init(dir, "eng");
    }

    boolean checkLanguageFile(String dir)
    {
        File file = new File(dir);
        if(!file.exists() && file.mkdirs())
            createFiles(dir);
        else if(file.exists()){
            String filePath = dir + "/eng.traineddata";
            File langDataFile = new File(filePath);
            if(!langDataFile.exists())
                createFiles(dir);
        }
        return true;
    }

    private void createFiles(String dir)
    {
        AssetManager assetMgr = this.getAssets();

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = assetMgr.open("eng.traineddata");

            String destFile = dir + "/eng.traineddata";

            outputStream = new FileOutputStream(destFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            inputStream.close();
            outputStream.flush();
            outputStream.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void capture()
    {
        surfaceView.capture(new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] bytes, Camera camera) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;

                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                bitmap = GetRotatedBitmap(bitmap, 90);

                imageView.setImageBitmap(bitmap);

                button.setEnabled(false);
                button.setText("텍스트 인식중...");
                new AsyncTess().execute(bitmap);

                camera.startPreview();
            }
        });
    }

    public synchronized static Bitmap GetRotatedBitmap(Bitmap bitmap, int degrees) {
        if (degrees != 0 && bitmap != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
            try {
                Bitmap b2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
                if (bitmap != b2) {
                    bitmap = b2;
                }
            } catch (OutOfMemoryError ex) {
                ex.printStackTrace();
            }
        }
        return bitmap;
    }

=======
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Example of a call to a native method
        TextView tv = binding.sampleText;
        tv.setText(stringFromJNI());
    }

    /**
     * A native method that is implemented by the 'camera_test' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
>>>>>>> parent of aeb9edc (camera setting finish and permission error clear)
}