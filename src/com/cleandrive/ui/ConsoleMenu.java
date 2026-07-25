package com.cleandrive.ui;

import com.cleandrive.datastructures.AVLTree;
import com.cleandrive.datastructures.DirectoryGraph;
import com.cleandrive.datastructures.MaxHeap;
import com.cleandrive.model.FileRecord;
import com.cleandrive.service.DirectoryScanner;
import com.cleandrive.service.StorageOptimizer;

import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {

    private AVLTree avlTree = new AVLTree();
    private MaxHeap maxHeap = new MaxHeap();
    private DirectoryScanner scannerService = new DirectoryScanner();
    private DirectoryGraph activeGraph = null;
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\n=================================================");
            System.out.println("             CleanDrive+ Storage Optimizer       ");
            System.out.println("=================================================");
            System.out.println("1. Scan Directory (Graph DFS)");
            System.out.println("2. View Duplicate Files (AVL Tree)");
            System.out.println("3. View Top Largest Files (Max Heap)");
            System.out.println("4. View Folder Graph Hierarchy");
            System.out.println("5. Safe Cleanup Recommendations");
            System.out.println("6. Delete File Manually");
            System.out.println("7. Exit");
            System.out.print("Select an option (1-7): ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    handleScan();
                    break;
                case 2:
                    handleDuplicates();
                    break;
                case 3:
                    handleLargestFiles();
                    break;
                case 4:
                    handleGraphView();
                    break;
                case 5:
                    StorageOptimizer.generateRecommendations(avlTree.getDuplicateGroups());
                    break;
                case 6:
                    handleDelete();
                    break;
                case 7:
                    System.out.println("Exiting CleanDrive+. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please enter a number from 1 to 7.");
            }
        }
    }

    private void handleScan() {
        System.out.print("Enter absolute directory path to scan: ");
        String path = scanner.nextLine();

        System.out.println("\nScanning directory and processing data structures...");
        activeGraph = scannerService.scanDirectoryDFS(path, avlTree, maxHeap);
        System.out.println("Scan successfully completed!");
    }

    private void handleDuplicates() {
        List<List<FileRecord>> duplicates = avlTree.getDuplicateGroups();
        if (duplicates.isEmpty()) {
            System.out.println("\nNo duplicate files found.");
            return;
        }

        System.out.println("\n--- Duplicate File Groups Found (AVL Tree Indexing) ---");
        for (int i = 0; i < duplicates.size(); i++) {
            System.out.println("\nGroup " + (i + 1) + ":");
            for (FileRecord file : duplicates.get(i)) {
                System.out.println("  -> " + file.getFilePath());
            }
        }
    }

    private void handleLargestFiles() {
        if (maxHeap.isEmpty()) {
            System.out.println("\nNo files scanned yet.");
            return;
        }

        System.out.print("Enter number of top largest files to view: ");
        int topN = getIntInput();

        MaxHeap tempHeap = maxHeap.cloneHeap();
        System.out.println("\n--- Top " + topN + " Largest Files (Max Heap Prioritization) ---");
        int count = 0;

        while (!tempHeap.isEmpty() && count < topN) {
            FileRecord maxFile = tempHeap.extractMax();
            System.out.println((count + 1) + ". " + maxFile);
            count++;
        }
    }

    private void handleGraphView() {
        if (activeGraph == null) {
            System.out.println("\nPlease run a directory scan first.");
            return;
        }
        activeGraph.printGraphStructure();
    }

    private void handleDelete() {
        System.out.print("Enter full path of file to delete: ");
        String filePath = scanner.nextLine();

        System.out.print("Are you sure you want to permanently delete this file? (yes/no): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("yes")) {
            boolean deleted = StorageOptimizer.deleteFile(filePath);
            if (deleted) {
                System.out.println("File deleted successfully!");
            } else {
                System.out.println("Failed to delete file. Check path permissions.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private int getIntInput() {
        try {
            int input = Integer.parseInt(scanner.nextLine());
            return input;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}