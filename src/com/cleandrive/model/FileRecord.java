package com.cleandrive.model;

import java.time.Instant;

public class FileRecord {
    private String filePath;
    private String fileName;
    private long sizeInBytes;
    private String fileHash;
    private Instant lastModified;

    public FileRecord(String filePath, String fileName, long sizeInBytes, Instant lastModified) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.sizeInBytes = sizeInBytes;
        this.lastModified = lastModified;
        this.fileHash = "";
    }

    public String getFilePath() { return filePath; }
    public String getFileName() { return fileName; }
    public long getSizeInBytes() { return sizeInBytes; }
    public String getFileHash() { return fileHash; }
    public void setFileHash(String fileHash) { this.fileHash = fileHash; }
    public Instant getLastModified() { return lastModified; }

    @Override
    public String toString() {
        double mb = sizeInBytes / (1024.0 * 1024.0);
        return String.format("%s (%.2f MB) - %s", fileName, mb, filePath);
    }
}