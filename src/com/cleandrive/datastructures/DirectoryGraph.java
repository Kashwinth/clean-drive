package com.cleandrive.datastructures;

import com.cleandrive.model.FolderNode;
import com.cleandrive.model.FileRecord;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectoryGraph {
    private FolderNode rootNode;
    private final Map<FolderNode, List<FolderNode>> adjList;

    public DirectoryGraph() {
        this.adjList = new HashMap<>();
    }

    public FolderNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(FolderNode rootNode) {
        this.rootNode = rootNode;
        addVertex(rootNode);
    }

    public void addVertex(FolderNode folder) {
        adjList.putIfAbsent(folder, new ArrayList<>());
    }

    public void addEdge(FolderNode parent, FolderNode child) {
        addVertex(parent);
        addVertex(child);
        if (!adjList.get(parent).contains(child)) {
            adjList.get(parent).add(child);
        }
    }

    public List<FolderNode> getNeighbors(FolderNode folder) {
        return adjList.getOrDefault(folder, new ArrayList<>());
    }

    public List<FileRecord> getAllFilesDFS() {
        List<FileRecord> allFiles = new ArrayList<>();
        if (rootNode != null) {
            dfsHelper(rootNode, allFiles);
        }
        return allFiles;
    }

    private void dfsHelper(FolderNode node, List<FileRecord> allFiles) {
        allFiles.addAll(node.getFiles());
        for (FolderNode child : getNeighbors(node)) {
            dfsHelper(child, allFiles);
        }
    }
}
