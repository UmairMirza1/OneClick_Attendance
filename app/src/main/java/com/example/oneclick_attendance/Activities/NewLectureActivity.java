package com.example.oneclick_attendance.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.oneclick_attendance.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class NewLectureActivity extends AppCompatActivity {


    Button Proceed,Cancel,Camera,Browse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lecture);
        SetViews();
        StartPython();
        
        
    }

    private void StartPython() {
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }


       // Drawable myImage = ContextCompat.getDrawable(this, R.raw.testimage);
        ByteArrayOutputStream outputStream;
        try (InputStream inputStream = getResources().openRawResource(R.raw.testimage)) {

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
        String filedata = "data:image/png;base64," + encoded;


        Python py=Python.getInstance();
        PyObject pyObject=py.getModule("app");
        PyObject obj=pyObject.callAttr("fun", "images",null,filedata);
        String databoi=obj.toString();
        Toast.makeText(this, databoi, Toast.LENGTH_SHORT).show();
    }

    private void SetViews() {

    }
}