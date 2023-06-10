package com.example.oneclick_attendance.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import android.Manifest;

import com.example.oneclick_attendance.API.APIWrapper;
import com.example.oneclick_attendance.Activities.kotlin.DetectionResultActivity;
import com.example.oneclick_attendance.JavaClasses.Section;
import com.example.oneclick_attendance.R;
import com.example.oneclick_attendance.Utils.Utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NewLectureActivity extends AppCompatActivity {
    private static final int REQUEST_VIDEO_CAPTURE = 200;
    private static final int SELECT_PHOTO = 150;
    Button Proceed, Cancel, Camera, Browse;

    Button prev , next ;
    ProgressBar progressBar;
    VideoView video;

    LinearLayout progressBarLayout;
    Uri videoUri;

    ArrayList<Uri> videoUriArrays;

    String result;

    APIWrapper Api;

    String TAG = "NewLectureActivity";

    int videoIndex = 0;
    ActivityResultLauncher<Intent> attendanceLauncher;

    List<String> RegisteredStudents = new ArrayList<>();

    Section section;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lecture);
        SetViews();
        Setlisteners();


        videoUriArrays = new ArrayList<>();
        String userID = getIntent().getStringExtra("userID");
        section = (Section) getIntent().getSerializableExtra("Section");
        Log.d(TAG, userID);

        RegisteredStudents = section.getRegistredStudents();
        Log.d(TAG, "val set" + RegisteredStudents.toString());

        attendanceLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    Log.d(TAG, "onActivityResult:  result ok ");
                }
            }
        });

        Api = new APIWrapper(this);


    }


    private void SetViews() {

        Proceed = findViewById(R.id.Proceed);
        Cancel = findViewById(R.id.Cancel);
        Camera = findViewById(R.id.Camera);
        Browse = findViewById(R.id.Browse);
        video = findViewById(R.id.lectureVideo);
        progressBar = findViewById(R.id.progressBar_);
        progressBarLayout = findViewById( R.id.progressLayout);
        prev = findViewById(R.id.buttonPrev);
        next = findViewById(R.id.buttonNext);

    }

    private void getCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        }
    }

    private void Setlisteners() {
        Proceed.setOnClickListener(v -> {
            if (videoUri == null) {
                Toast.makeText(this, "Please select a video", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, "Proceed", Toast.LENGTH_SHORT).show();
            progressBarLayout.setVisibility(View.VISIBLE);
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
            // TODO: Send cancelled result to calling activity
        });

        Camera.setOnClickListener(v -> {
            Toast.makeText(this, "Camera", Toast.LENGTH_SHORT).show();
            getCameraPermission();
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            intent.putExtra(android.provider.MediaStore.EXTRA_VIDEO_QUALITY, 0);
            startActivityForResult(intent, REQUEST_VIDEO_CAPTURE);

        });
        Browse.setOnClickListener(v -> {
            Toast.makeText(this, "Browse", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("video/*");
            startActivityForResult(intent, SELECT_PHOTO);
        });


        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoIndex > 0) {
                    videoIndex--;
                    Log.i("bolal", "playing video " + videoUriArrays.get(videoIndex));
                    video.setVideoURI(videoUriArrays.get(videoIndex));
                    video.start();
                }
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoIndex < videoUriArrays.size() - 1) {
                    videoIndex++;
                    Log.i("bolal", "playing video " + videoUriArrays.get(videoIndex));
                    video.setVideoURI(videoUriArrays.get(videoIndex));
                    video.start();
                }
            }
        });

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


            Intent intent = new Intent(NewLectureActivity.this, DetectionResultActivity.class);
            ArrayList<String> res = Tokenize(result);
            Log.d("newLectureActivity", "Token: " + res);

            intent.putExtra("StudentsPresent", res);
            intent.putExtra("courseCode", section.getCourseCode());
            RegisteredStudents.removeIf(res::contains);
            intent.putExtra("RegisteredStudents", (ArrayList<String>) RegisteredStudents);
            attendanceLauncher.launch(intent);

            Log.i("newLectureActivity", "result from API: " + result);
        }
    }

    private ArrayList<String> Tokenize(String result) {

        ArrayList<String> res = new ArrayList<>();

        String[] tokens = result.split(" ");

        for (String token : tokens) {
            Log.d(TAG, "Tokenize: bolal" + token);
            res.add(token);
        }
        return res;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Log.i("bolal", "onActivityResult: Video saved to: " + intent.getData());

            videoUri = intent.getData();
            videoUriArrays.add(videoUri);
            video.setVideoURI(videoUriArrays.get(videoIndex));
            videoIndex++;
            video.start();
            Camera.setText("Another?");

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