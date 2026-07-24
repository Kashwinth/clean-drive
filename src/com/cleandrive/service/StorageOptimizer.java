package com.cleandrive.service;

import com.cleandrive.datastructures.AVLTree;
import com.cleandrive.datastructures.DirectoryGraph;
import com.cleandrive.datastructures.MaxHeap;
import com.cleandrive.model.FileRecord;
import java.util.List;

public class StorageOptimizer {

    public void findDuplicates(DirectoryGraph graph) {
        if (graph == null || graph.getRootNode() == null) {
            System.out.println("No directory graph loaded. Scan a directory first.");
            return;
        }

        AVLTree avl = new AVLTree();
        List<FileRecord> files = graph.getAllFilesDFS();
        for (FileRecord file : files) {
            avl.insert(file.getHash(), file);
        }

        List<List<FileRecord>> duplicateGroups = avl.getAllDuplicates();
        if (duplicateGroups.isEmpty()) {
            System.out.println("No duplicate files found.");
            return;
        }

        System.out.println("\n--- Duplicate File Groups Found ---");
        for (int i = 0; i < duplicateGroups.size(); i++) {
            List<FileRecord> group = duplicateGroups.get(i);
            System.out.println("Group " + (i + 1) + " (Hash: " + group.get(0).getHash() + "):");
            for (FileRecord record : group) {
                System.out.println("  - " + record.getPath() + " (" + record.getSize() + " bytes)");
            }
        }
    }

    public void findLargeFiles(DirectoryGraph graph, int limit) {
        if (graph == null || graph.getRootNode() == null) {
            System.out.println("No directory graph loaded. Scan a directory first.");
            return;
        }

        MaxHeap heap = new MaxHeap();
        List<FileRecord> files = graph.getAllFilesDFS();
        for (FileRecord file : files) {
            heap.insert(file);
        }

        System.out.println("\n--- Top " + limit + " Largest Files ---");
        int count = 0;
        while (!heap.isEmpty() && count < limit) {
            FileRecord max = heap.extractMax();
            if (max == null) break;
            System.out.println((count + 1) + ". " + max.getName() + " - " + max.getSize() + " bytes");
            System.out.println("   Path: " + max.getPath());
            count++;
        }
    }

    public void optimize(DirectoryGraph graph) {
        if (graph == null || graph.getRootNode() == null) {
            System.out.println("No directory graph loaded. Scan a directory first.");
            return;
        }

        AVLTree avl = new AVLTree();
        List<FileRecord> files = graph.getAllFilesDFS();
        for (FileRecord file : files) {
            avl.insert(file.getHash(), file);
        }

        List<List<FileRecord>> duplicateGroups = avl.getAllDuplicates();
        if (duplicateGroups.isEmpty()) {
            System.out.println("No duplicates found. Storage is optimized.");
            return;
        }

        long totalSavings = 0;
        System.out.println("\n--- Safe Storage Optimization Recommendations ---");
        for (List<FileRecord> group : duplicateGroups) {
            FileRecord keep = group.get(0);
            System.out.println("Keep: " + keep.getPath());
            for (int i = 1; i < group.size(); i++) {
                FileRecord duplicate = group.get(i);
                System.out.println("  [Recommendation: Delete] -> " + duplicate.getPath() + " (Saves " + duplicate.getSize() + " bytes)");
                totalSavings += duplicate.getSize();
            }
        }
        System.out.println("----------------------------------------------");
        System.out.println("Total potential savings by deleting duplicates: " + totalSavings + " bytes.");
    }
}
