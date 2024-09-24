package com.appsdevelopersblogs.spa.spa_example;

import jakarta.persistence.*;

@Entity
@Table(name="file_storages")
public class FileStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    private String filetype;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] filebyte;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String filebase64;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public byte[] getFilebyte() {
        return filebyte;
    }

    public void setFilebyte(byte[] filebyte) {
        this.filebyte = filebyte;
    }

    public String getFilebase64() {
        return filebase64;
    }

    public void setFilebase64(String filebase64) {
        this.filebase64 = filebase64;
    }
}
