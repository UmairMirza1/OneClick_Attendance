package com.example.oneclick_attendance.Utils;

import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class Utility {
    public static final int CHUNK_SIZE = 1024;

    public static void ParseCSV() {

    }


    public static String encodeVideoChunk(byte[] chunk) {
        return Base64.encodeToString(chunk, 0);
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
    public static String encodeVideo(ByteArrayOutputStream outputStream) {
        byte[] data = outputStream.toByteArray();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < data.length; i += CHUNK_SIZE) {
            int end = Math.min(data.length, i + CHUNK_SIZE);
            byte[] chunk = Arrays.copyOfRange(data, i, end);

            String encodedChunk = encodeVideoChunk(chunk);
            sb.append(encodedChunk);
        }

        return "data:video/mp4;base64," + sb.toString();
    }

}
