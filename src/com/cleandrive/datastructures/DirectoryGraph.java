package com.cleandrive.datastructures;

import com.cleandrive.model.FolderNode;
import java.util.LinkedList;
import java.util.Queue;

public class DirectoryGraph {
    private FolderNode root;

    public DirectoryGraph(String rootPath) {
        this.root = new FolderNode(rootPath);
    }

    public FolderNode getRoot() {
        return root;
    }

    public void printGraphStructure() {
        System.out.println("\n--- Folder Structure Hierarchy (Graph Visualization) ---");
        printRecursive(root, "");
    }

    private void printRecursive(FolderNode node, String indent) {
        System.out.println(indent + "├── [Folder] " + node.getFolderPath());
        for (FolderNode child : node.getSubfolders()) {
            printRecursive(child, indent + "│   ");
        }
    }
}