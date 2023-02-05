package com.example.oneclick_attendance.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import android.Manifest;
import com.example.oneclick_attendance.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewLectureActivity extends AppCompatActivity {


    private static final int REQUEST_VIDEO_CAPTURE = 200;
    private static final int SELECT_PHOTO = 150 ;
    Button Proceed,Cancel,Camera,Browse;


    VideoView video;

    Uri videoUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lecture);

        SetViews();
        Setlisteners();
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

       // StartPython();

    }



    private void StartPython() {


//        // TODO
        //getResources().openRawResource(R.raw.testimage)
        ByteArrayOutputStream outputStream;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            try (InputStream inputStream = getContentResolver().openInputStream(videoUri)) {

                outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while (true) {
                    try {
                        if ((length = inputStream.read(buffer)) == -1) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    outputStream.write(buffer, 0, length);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            byte[] data = outputStream.toByteArray();
            // encode the file into base64
            String encoded = Base64.encodeToString(data, 0);
            //String filedata = "data:image/png;base64," + encoded;
            String filedata = "data:video/mp4;base64," + encoded;

            Python py = Python.getInstance();
            PyObject pyObject = py.getModule("app");
            PyObject obj = pyObject.callAttr("fun", "images", filedata,null);
            String databoi = obj.toString();

            String name = databoi.substring(2, databoi.indexOf("]")-1);

            List<String> myList = new ArrayList<String>(Arrays.asList(name.split(",")));


            //Toast.makeText(this, databoi, Toast.LENGTH_SHORT).show();
            Log.i("bolal", "StartPython: "+databoi);
            Log.i(" bolal", String.valueOf(obj.getClass()));
            Log.i("bolal", "mylist : "+myList);
            Log.i("bolal", "name : "+name);
        }
    }

    private void SetViews() {
        Proceed = findViewById(R.id.Proceed);
        Cancel = findViewById(R.id.Cancel);
        Camera=findViewById(R.id.Camera);
        Browse =findViewById(R.id.Browse);
        video=findViewById(R.id.lectureVideo);

    }

    private  void  getCameraPermission(){
        if( ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        }


    }

    private void Setlisteners() {
        Proceed.setOnClickListener(v -> {
            Toast.makeText(this, "Proceed", Toast.LENGTH_SHORT).show();

           new Thread(new Runnable() {
               @Override
               public void run() {
                   StartPython();
               }
           }).start();

        });


        Cancel.setOnClickListener(v -> {
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
            // TODO: Send cancelled result to calling  activity
        });


        Camera.setOnClickListener(v -> {
            Toast.makeText(this, "Camera", Toast.LENGTH_SHORT).show();
            getCameraPermission();
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            startActivityForResult(intent, REQUEST_VIDEO_CAPTURE);

        });
        Browse.setOnClickListener(v -> {
            Toast.makeText(this, "Browse", Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(Intent.ACTION_PICK);
            intent.setType("video/*");
            startActivityForResult(intent,SELECT_PHOTO);
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Log.i("bolal", "onActivityResult: Video saved to: " + intent.getData());
            videoUri = intent.getData();
            video.setVideoURI(videoUri);
            video.start();

        }
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_CANCELED) {
            Log.i("bolal", "video recording cancelled  " + intent.getData());
        }

        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK) {
            Log.i("bolal", "onActivityResult: Video saved to: " + intent.getData());
            videoUri = intent.getData();
            video.setVideoURI(videoUri);
            video.start();

        }
    }
}