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

import com.example.oneclick_attendance.API.APIWrapper;
import com.example.oneclick_attendance.R;
import com.example.oneclick_attendance.Utils.Utility;
import com.google.android.gms.common.api.Api;

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
    private static final int SELECT_PHOTO = 150;
    Button Proceed, Cancel, Camera, Browse;


    VideoView video;

    Uri videoUri;

    String result;

    APIWrapper Api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lecture);
        SetViews();
        Setlisteners();
        Api = new APIWrapper(this);
    }




    private void SetViews() {
        Proceed = findViewById(R.id.Proceed);
        Cancel = findViewById(R.id.Cancel);
        Camera = findViewById(R.id.Camera);
        Browse = findViewById(R.id.Browse);
        video = findViewById(R.id.lectureVideo);

    }

    private void getCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        }


    }

    private void Setlisteners() {
        Proceed.setOnClickListener(v -> {
            Toast.makeText(this, "Proceed", Toast.LENGTH_SHORT).show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    ApiCall();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(NewLectureActivity.this, result, Toast.LENGTH_LONG).show();
                        }
                    });
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
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("video/*");
            startActivityForResult(intent, SELECT_PHOTO);
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


    private void ApiCall() {
        ByteArrayOutputStream outputStream;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            try (InputStream inputStream = getContentResolver().openInputStream(videoUri)) {
                outputStream = Utility.getByteArrayOutputStream(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String EncodedVideo = Utility.EncodeVideo(outputStream);
            Log.d("newLectureActivity", "calling API");
            result = Api.GetAPIData(EncodedVideo);
            Log.i("newLectureActivity", "result from API: " + result);
        }
    }
}