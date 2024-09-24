package com.appsdevelopersblogs.spa.spa_example;

import java.util.Base64;

import java.util.zip.Deflater;

public class FileStorageUtil {

    public static byte[] compressFile(byte[] file) {



        return Base64.getEncoder().encode(file);
    }

    public static byte[] deCompressFile(byte[] file) {
        return Base64.getDecoder().decode(file);
    }
}
