package com.cleandrive.ui;

import java.util.Scanner;
import com.cleandrive.service.DirectoryScanner;
import com.cleandrive.service.StorageOptimizer;

public class ConsoleMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final DirectoryScanner directoryScanner = new DirectoryScanner();
    private final StorageOptimizer storageOptimizer = new StorageOptimizer();

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n==================================");
            System.out.println("        CLEANDRIVE PLUS           ");
            System.out.println("==================================");
            System.out.println("1. Scan Directory");
            System.out.println("2. View Directory Structure");
            System.out.println("3. Find Duplicate Files (AVL Tree)");
            System.out.println("4. Find Large Files (Max Heap)");
            System.out.println("5. Safe Storage Cleanup Options");
            System.out.println("6. Exit");
            System.out.print("Select an option (1-6): ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    scanDirectory();
                    break;
                case "2":
                    viewStructure();
                    break;
                case "3":
                    findDuplicates();
                    break;
                case "4":
                    findLargeFiles();
                    break;
                case "5":
                    optimizeStorage();
                    break;
                case "6":
                    System.out.println("Exiting CleanDrive Plus. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void scanDirectory() {
        System.out.print("Enter directory path to scan: ");
        String path = scanner.nextLine().trim();
        System.out.println("Scanning directory: " + path);
        try {
            directoryScanner.scan(path);
            System.out.println("Scan completed successfully.");
        } catch (Exception e) {
            System.out.println("Error scanning directory: " + e.getMessage());
        }
    }

    private void viewStructure() {
        System.out.println("Displaying directory structure...");
        directoryScanner.printStructure();
    }

    private void findDuplicates() {
        System.out.println("Finding duplicate files using AVL Tree...");
        storageOptimizer.findDuplicates(directoryScanner.getGraph());
    }

    private void findLargeFiles() {
        System.out.print("Enter number of large files to list: ");
        try {
            int limit = Integer.parseInt(scanner.nextLine().trim());
            storageOptimizer.findLargeFiles(directoryScanner.getGraph(), limit);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Please enter a valid integer.");
        }
    }

    private void optimizeStorage() {
        System.out.println("Performing safe storage cleanup and recommendations...");
        storageOptimizer.optimize(directoryScanner.getGraph());
    }
}
