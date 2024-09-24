package com.appsdevelopersblogs.spa.spa_example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

@Service
public class FileStoreServiceImpl {

    @Autowired
    FileStoreService fsService;

    private String accessKey = "AKIAXYKJRRSIQ7Q3RZPK";
    private String secretKey = "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";
    private String bucket = "chris-spring-upload-testings";


    public String uploadMultipartFile(MultipartFile data) {
        FileStorage file = new FileStorage();
        file.setFilename(data.getOriginalFilename());
        file.setFiletype(data.getContentType());

        try {
            file.setFilebyte(FileStorageUtil.compressFile(data.getBytes()));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        FileStorage newFile = fsService.persistFile(file);

        if (newFile != null) {
            return String.format("File $s uploaded successfully!", data.getOriginalFilename());
        }

        return String.format("File $s upload failed!", data.getOriginalFilename());
    }

    public String uploadBase64File(String filename, String filetype, String data) {
        FileStorage file = new FileStorage();
        file.setFilename(filename);
        file.setFiletype(filetype);
        file.setFilebase64(data);

        FileStorage newFile = fsService.persistFile(file);

        if (newFile != null) {
            return String.format("File $s uploaded successfully!", filename);
        }

        return String.format("File $s upload failed!", filename);
    }

    public FileStorage retrieveFile(String filename) {
        return fsService.retrieveFileByName(filename);
    }

    public byte[] downloadMultipartFile(String filename) {
        return FileStorageUtil.deCompressFile(fsService.retrieveFileByName(filename).getFilebyte());
    }

    public byte[] downloadBase64File(String filename) {
        String data = fsService.retrieveFileByName(filename).getFilebase64();
        return Base64.getDecoder().decode(data.split(",")[1]);
    }

    public String uploadFileToS3(MultipartFile data) {

        try {
            uploadMultipartFile(data);

            AwsCloudUtil util = new AwsCloudUtil();
            util.uploadFileToS3(data.getOriginalFilename(), data.getBytes(), accessKey, secretKey, bucket);

            return String.format("File $s uploaded successfully!", data.getOriginalFilename());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return String.format("File $s upload failed!", data.getOriginalFilename());
    }

    public byte[] downloadS3File(String filename) {

        try {
            AwsCloudUtil util = new AwsCloudUtil();

            return util.downloadFileFroomS3(filename, accessKey, secretKey, bucket).readAllBytes();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        return  null;
    }

}
