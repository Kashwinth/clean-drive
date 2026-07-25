package com.cleandrive.datastructures;

import com.cleandrive.model.FileRecord;
import java.util.ArrayList;
import java.util.List;

public class AVLTree {

    private class Node {
        String hash;
        List<FileRecord> files;
        int height;
        Node left, right;

        Node(FileRecord record) {
            this.hash = record.getFileHash();
            this.files = new ArrayList<>();
            this.files.add(record);
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

    public void insert(FileRecord record) {
        if (record.getFileHash().isEmpty()) return;
        root = insertRec(root, record);
    }

    private Node insertRec(Node node, FileRecord record) {
        if (node == null) return new Node(record);

        int cmp = record.getFileHash().compareTo(node.hash);
        if (cmp < 0) {
            node.left = insertRec(node.left, record);
        } else if (cmp > 0) {
            node.right = insertRec(node.right, record);
        } else {
            node.files.add(record);
            return node;
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = getBalance(node);

        if (balance > 1 && record.getFileHash().compareTo(node.left.hash) < 0)
            return rightRotate(node);
        if (balance < -1 && record.getFileHash().compareTo(node.right.hash) > 0)
            return leftRotate(node);
        if (balance > 1 && record.getFileHash().compareTo(node.left.hash) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && record.getFileHash().compareTo(node.right.hash) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public List<List<FileRecord>> getDuplicateGroups() {
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

    public void clear() {
        root = null;
    }
}