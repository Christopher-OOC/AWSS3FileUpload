package com.appsdevelopersblogs.spa.spa_example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class UploadController {

    @Autowired
    private FileStoreServiceImpl fsServiceImpl;

    @PostMapping("/multipart")
    public ResponseEntity<?> uploadMultipartFile(
            @RequestParam("file") MultipartFile data) {
        String response = fsServiceImpl.uploadMultipartFile(data);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/base64")
    public ResponseEntity<?> uploadBase64File(@RequestBody FileStorageDto data) {
        String response = fsServiceImpl.uploadBase64File(
                data.getFilename(),
                data.getFiletype(),
                data.getFilebase64()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping(value="/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> upload(
            @RequestParam(name="file", required = false) MultipartFile multipartData,
            @RequestBody(required = false) FileStorageDto base64Data

    ) {

        if (base64Data == null) {
            String response = fsServiceImpl.uploadMultipartFile(multipartData);

            return ResponseEntity.ok(response);
        }
        else {
            String response = fsServiceImpl.uploadBase64File(
                    base64Data.getFilename(),
                    base64Data.getFiletype(),
                    base64Data.getFilebase64());

            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/multipart/{filename}")
    public ResponseEntity<?> downloadMultipartFile(@PathVariable String filename) {
        FileStorage fileDetails = fsServiceImpl.retrieveFile(filename);

        byte[] file = fsServiceImpl.downloadMultipartFile(filename);

        return ResponseEntity.ok().contentType(
                MediaType.valueOf(fileDetails.getFiletype()
                )
        ).body(file);
    }

    @GetMapping("/base64/{filename}")
    public ResponseEntity<?> downloadBase64File(@PathVariable String filename) {
        FileStorage fileDetails = fsServiceImpl.retrieveFile(filename);

        byte[] file = fsServiceImpl.downloadS3File(filename);

        return ResponseEntity.ok().contentType(
                MediaType.valueOf(fileDetails.getFiletype()
                )
        ).body(file);
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<?> download(@PathVariable String filename) {

        FileStorage fileDetails = fsServiceImpl.retrieveFile(filename);

        if (fileDetails.getFilebyte() != null) {

            byte[] file = fsServiceImpl.downloadMultipartFile(filename);

            return ResponseEntity.ok().contentType(
                    MediaType.valueOf(fileDetails.getFiletype()
                    )
            ).body(file);
        }
        else {

            byte[] file = fsServiceImpl.downloadS3File(filename);

            return ResponseEntity.ok().contentType(
                    MediaType.valueOf(fileDetails.getFiletype()
                    )
            ).body(file);

        }

    }

    @GetMapping("/base64/{filename}/details")
    public ResponseEntity<?> getBase64FileDetails(@PathVariable String filename) {
        FileStorage fileDetails = fsServiceImpl.retrieveFile(filename);

        return ResponseEntity.ok(fileDetails);
    }

    @PostMapping("/multipart/s3")
    public ResponseEntity<?> uploadMulipartToS3(@RequestParam("file")MultipartFile data) {

        String response = fsServiceImpl.uploadFileToS3(data);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/multipart/s3/{filename}")
    public ResponseEntity<?> downloadMultipartS3File(@PathVariable String filename) {

        FileStorage fileDetails = fsServiceImpl.retrieveFile(filename);
        byte[] file = fsServiceImpl.downloadS3File(filename);

        return ResponseEntity.ok().contentType(
                MediaType.valueOf(fileDetails.getFiletype()
                )
        ).body(file);
    }
}