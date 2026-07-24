package com.cleandrive.service;

import com.cleandrive.datastructures.DirectoryGraph;
import com.cleandrive.model.FileRecord;
import com.cleandrive.model.FolderNode;
import java.io.File;
import java.util.Date;

public class DirectoryScanner {
    private DirectoryGraph graph;
    private final HashGenerator hashGenerator;

    public DirectoryScanner() {
        this.graph = new DirectoryGraph();
        this.hashGenerator = new HashGenerator();
    }

    public DirectoryGraph getGraph() {
        return graph;
    }

    public void scan(String rootPath) {
        File rootDir = new File(rootPath);
        if (!rootDir.exists() || !rootDir.isDirectory()) {
            throw new IllegalArgumentException("Provided path does not exist or is not a directory.");
        }

        FolderNode rootNode = new FolderNode(rootDir.getAbsolutePath(), rootDir.getName());
        graph = new DirectoryGraph();
        graph.setRootNode(rootNode);

        scanRecursively(rootDir, rootNode);
    }

    private void scanRecursively(File currentDir, FolderNode currentNode) {
        File[] filesList = currentDir.listFiles();
        if (filesList == null) return;

        for (File f : filesList) {
            if (f.isDirectory()) {
                FolderNode childNode = new FolderNode(f.getAbsolutePath(), f.getName());
                graph.addEdge(currentNode, childNode);
                currentNode.addSubFolder(childNode);
                scanRecursively(f, childNode);
            } else if (f.isFile()) {
                String hash = hashGenerator.generateHash(f);
                FileRecord record = new FileRecord(
                    f.getAbsolutePath(), 
                    f.getName(), 
                    f.length(), 
                    hash, 
                    new Date(f.lastModified())
                );
                currentNode.addFile(record);
            }
        }
    }

    public void printStructure() {
        FolderNode root = graph.getRootNode();
        if (root == null) {
            System.out.println("No directory scanned yet.");
            return;
        }
        printDFSHelper(root, 0);
    }

    private void printDFSHelper(FolderNode node, int depth) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            indent.append("  ");
        }
        System.out.println(indent.toString() + "[D] " + node.getName() + " (" + node.getPath() + ")");
        for (FileRecord file : node.getFiles()) {
            System.out.println(indent.toString() + "  - [F] " + file.getName() + " (" + file.getSize() + " bytes)");
        }
        for (FolderNode child : graph.getNeighbors(node)) {
            printDFSHelper(child, depth + 1);
        }
    }
}
