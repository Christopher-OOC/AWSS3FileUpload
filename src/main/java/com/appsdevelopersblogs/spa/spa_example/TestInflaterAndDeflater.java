package com.appsdevelopersblogs.spa.spa_example;

import java.util.Base64;
import java.util.zip.DataFormatException;

public class TestInflaterAndDeflater {

    public static void main(String[] args) throws DataFormatException {

        StringBuilder content = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            content.append("abcdefghijklmnopqrstuvwxyz\n");
        }

        byte[] byteContent = content.toString().getBytes();
        System.out.println("Actual size is: " + byteContent.length);
        System.out.println();
        System.out.println(content);


        // Deflater
        byte[] compressFileDeflater = FileStorageUtil.compressFileDeflater(byteContent);
        byte[] deCompressFileDeflater = FileStorageUtil.deCompressFileDeflater(compressFileDeflater);

        System.out.println("Deflater size is: " + compressFileDeflater.length);
        System.out.println("Inflater size is: " + deCompressFileDeflater.length);
        System.out.println();
        System.out.println(FileStorageUtil.toReadableString(compressFileDeflater));
        System.out.println();
        System.out.println(FileStorageUtil.toReadableString(deCompressFileDeflater));

        // Base64
        byte[] base64Encoded = Base64.getEncoder().encode(byteContent);
        byte[] decode = Base64.getDecoder().decode(base64Encoded);

        System.out.println("Base64 encoded size is: " + base64Encoded.length);
        System.out.println("Base64 decoded size is: " + decode.length);
        System.out.println();
        System.out.println(FileStorageUtil.toReadableString(base64Encoded));
        System.out.println();
        System.out.println(FileStorageUtil.toReadableString(decode));

    }

}
