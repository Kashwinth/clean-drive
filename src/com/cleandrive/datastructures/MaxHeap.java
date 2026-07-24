package com.cleandrive.datastructures;

import com.cleandrive.model.FileRecord;
import java.util.ArrayList;
import java.util.List;

public class MaxHeap {
    private final List<FileRecord> heap;

    public MaxHeap() {
        this.heap = new ArrayList<>();
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }

    private int rightChild(int i) {
        return 2 * i + 2;
    }

    private void swap(int i, int j) {
        FileRecord temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public void insert(FileRecord file) {
        heap.add(file);
        int current = heap.size() - 1;
        while (current > 0 && heap.get(current).getSize() > heap.get(parent(current)).getSize()) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    public FileRecord extractMax() {
        if (heap.isEmpty()) return null;
        FileRecord max = heap.get(0);
        FileRecord last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            maxHeapify(0);
        }
        return max;
    }

    private void maxHeapify(int i) {
        int left = leftChild(i);
        int right = rightChild(i);
        int largest = i;

        if (left < heap.size() && heap.get(left).getSize() > heap.get(largest).getSize()) {
            largest = left;
        }
        if (right < heap.size() && heap.get(right).getSize() > heap.get(largest).getSize()) {
            largest = right;
        }

        if (largest != i) {
            swap(i, largest);
            maxHeapify(largest);
        }
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int size() {
        return heap.size();
    }
}
