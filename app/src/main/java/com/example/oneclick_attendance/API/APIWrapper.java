package com.example.oneclick_attendance.API;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.ArrayList;

public class APIWrapper {


    public APIWrapper(Context context) {
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(context));
        }
    }

    public String GetAPIData(String EncodedVideo) {

        Python py = Python.getInstance();
        PyObject pyObject = py.getModule("app");
        PyObject obj = pyObject.callAttr("fun", "images", EncodedVideo, null);

        return obj.toString();
    }

}
