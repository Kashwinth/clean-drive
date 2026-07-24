package com.cleandrive.model;

import java.util.ArrayList;
import java.util.List;

public class FolderNode {
    private String path;
    private String name;
    private final List<FolderNode> subFolders;
    private final List<FileRecord> files;

    public FolderNode(String path, String name) {
        this.path = path;
        this.name = name;
        this.subFolders = new ArrayList<>();
        this.files = new ArrayList<>();
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

    public List<FolderNode> getSubFolders() {
        return subFolders;
    }

    public List<FileRecord> getFiles() {
        return files;
    }

    public void addSubFolder(FolderNode folder) {
        subFolders.add(folder);
    }

    public void addFile(FileRecord file) {
        files.add(file);
    }

    @Override
    public String toString() {
        return name + " (" + path + ")";
    }
}
