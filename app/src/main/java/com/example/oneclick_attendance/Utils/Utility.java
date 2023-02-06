package com.example.oneclick_attendance.Utils;

import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utility {


    public static void ParseCSV(){

    }

    public static ByteArrayOutputStream getByteArrayOutputStream(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
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
        return outputStream;
    }

   public static String EncodeVideo( ByteArrayOutputStream outputStream)

   {
       byte[] data = outputStream.toByteArray();
       // encode the file into base64
       String encoded = Base64.encodeToString(data, 0);
       //String filedata = "data:image/png;base64," + encoded;

       return "data:video/mp4;base64," + encoded;

   }

}
