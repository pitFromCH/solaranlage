package com.pit.solarserver.helper;

public class CircularBuffer {

    private int maxSize;
    private int selectedIndex =0;
    private int[] buf;
    private boolean isFull = false;

    public CircularBuffer(int size) {
        maxSize = size;
        selectedIndex = 0;
        buf = new int[size];
    }

    public int getSize() {
        return buf.length;
    }

    public void clear() {
        selectedIndex = 0;
        isFull = false;
        buf = new int[maxSize];
    }

    public void insert(int i) {
        buf[selectedIndex] = i;
        selectedIndex ++;
        if (selectedIndex == buf.length) {
            // set to pos 1
            selectedIndex = 0;
            isFull = true;
        }
    }

    public double getAverageValue() {
        double average = 0;
        int maxIndex = buf.length;
        if (!isFull) {
            maxIndex = selectedIndex;
        }
        for (int i = 0; i<maxIndex; i++) {
            average  = average + buf[i];
        }
        if (maxIndex != 0) {
            average = average / maxIndex;
        }
        return average;
    }
}
