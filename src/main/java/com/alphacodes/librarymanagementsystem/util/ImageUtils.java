package com.alphacodes.librarymanagementsystem.util;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtils {

    // Compress the image bytes before storing it in the database
    public static byte [] compressBytes(byte [] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(tmp);
            outputStream.write(tmp, 0, count);
        }


        try{
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }

    // Decompress the image bytes before returning it to the angular application
    public static byte [] decompressBytes(byte [] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

        byte[] tmp = new byte[5 * 1024 * 1024];

        while (!inflater.finished()) {
            try {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return outputStream.toByteArray();
    }
}

