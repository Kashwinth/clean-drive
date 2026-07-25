package com.cleandrive.model;

import java.util.ArrayList;
import java.util.List;

public class FolderNode {
    private String folderPath;
    private List<FolderNode> subfolders;
    private List<FileRecord> files;

    public FolderNode(String folderPath) {
        this.folderPath = folderPath;
        this.subfolders = new ArrayList<>();
        this.files = new ArrayList<>();
    }

    public String getFolderPath() { return folderPath; }
    public List<FolderNode> getSubfolders() { return subfolders; }
    public List<FileRecord> getFiles() { return files; }

    public void addSubfolder(FolderNode child) {
        this.subfolders.add(child);
    }

    public void addFile(FileRecord file) {
        this.files.add(file);
    }
}