package com.cleandrive.datastructures;

import com.cleandrive.model.FileRecord;
import java.util.ArrayList;
import java.util.List;

public class AVLTree {
    private class Node {
        String hash;
        List<FileRecord> files;
        Node left, right;
        int height;

        Node(String hash, FileRecord file) {
            this.hash = hash;
            this.files = new ArrayList<>();
            this.files.add(file);
            this.height = 1;
        }
    }

    private Node root;

    private int height(Node n) {
        return n == null ? 0 : n.height;
    }

    private int getBalance(Node n) {
        return n == null ? 0 : height(n.left) - height(n.right);
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    public void insert(String hash, FileRecord file) {
        root = insert(root, hash, file);
    }

    private Node insert(Node node, String hash, FileRecord file) {
        if (node == null) {
            return new Node(hash, file);
        }

        int cmp = hash.compareTo(node.hash);
        if (cmp < 0) {
            node.left = insert(node.left, hash, file);
        } else if (cmp > 0) {
            node.right = insert(node.right, hash, file);
        } else {
            // Duplicate hash found, append FileRecord to the list in this node
            node.files.add(file);
            return node;
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        // Left Left Case
        if (balance > 1 && hash.compareTo(node.left.hash) < 0) {
            return rightRotate(node);
        }

        // Right Right Case
        if (balance < -1 && hash.compareTo(node.right.hash) > 0) {
            return leftRotate(node);
        }

        // Left Right Case
        if (balance > 1 && hash.compareTo(node.left.hash) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && hash.compareTo(node.right.hash) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public List<List<FileRecord>> getAllDuplicates() {
        List<List<FileRecord>> duplicates = new ArrayList<>();
        collectDuplicates(root, duplicates);
        return duplicates;
    }

    private void collectDuplicates(Node node, List<List<FileRecord>> duplicates) {
        if (node == null) return;
        collectDuplicates(node.left, duplicates);
        if (node.files.size() > 1) {
            duplicates.add(node.files);
        }
        collectDuplicates(node.right, duplicates);
    }
}
