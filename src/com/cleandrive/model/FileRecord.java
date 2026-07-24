package com.cleandrive.model;

import java.util.Date;

public class FileRecord {
    private String path;
    private String name;
    private long size;
    private String hash;
    private Date lastModified;

    public FileRecord(String path, String name, long size, String hash, Date lastModified) {
        this.path = path;
        this.name = name;
        this.size = size;
        this.hash = hash;
        this.lastModified = lastModified;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public String toString() {
        return String.format("%s (%d bytes, hash: %s)", name, size, hash);
    }
}
