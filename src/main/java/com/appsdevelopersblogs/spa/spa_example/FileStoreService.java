package com.appsdevelopersblogs.spa.spa_example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileStoreService {

    @Autowired
    private FileStoreRepository repository;

    public FileStorage persistFile(FileStorage file) {
        return repository.save(file);
    }

    public FileStorage retrieveFileByName(String filename) {
        return repository.findByFilename(filename);
    }

    public void removeFile(String filename) throws Exception {
        FileStorage file = retrieveFileByName(filename);

        if (file == null) {
            throw new Exception(String.format("File with name %s not found!", filename));
        }

        repository.delete(file);
    }
}
