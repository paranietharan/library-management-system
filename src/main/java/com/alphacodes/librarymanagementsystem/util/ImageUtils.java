package com.alphacodes.librarymanagementsystem.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtils {

    // Compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        if (data == null || data.length == 0) {
            return data;
        }

        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)) {
            byte[] tmp = new byte[4 * 1024];
            while (!deflater.finished()) {
                int count = deflater.deflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to close output stream", e);
        }
    }

    // Decompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        if (data == null || data.length == 0) {
            return data;
        }

        Inflater inflater = new Inflater();
        inflater.setInput(data);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)) {
            byte[] tmp = new byte[4 * 1024];
            while (!inflater.finished()) {
                try {
                    int count = inflater.inflate(tmp);
                    outputStream.write(tmp, 0, count);
                } catch (DataFormatException e) {
                    // Add detailed debug information
                    System.err.println("DataFormatException: " + e.getMessage());
                    System.err.println("Data length: " + data.length);
                    for (int i = 0; i < data.length; i++) {
                        System.err.print(data[i] + " ");
                    }
                    System.err.println();
                    throw new RuntimeException("Data format exception during decompression", e);
                }
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to close output stream", e);
        }
    }
}
