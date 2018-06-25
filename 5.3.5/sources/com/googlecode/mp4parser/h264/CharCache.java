package com.googlecode.mp4parser.h264;

public class CharCache {
    private char[] cache;
    private int pos;

    public CharCache(int capacity) {
        this.cache = new char[capacity];
    }

    public void append(String str) {
        int toWrite;
        char[] chars = str.toCharArray();
        int available = this.cache.length - this.pos;
        if (chars.length < available) {
            toWrite = chars.length;
        } else {
            toWrite = available;
        }
        System.arraycopy(chars, 0, this.cache, this.pos, toWrite);
        this.pos += toWrite;
    }

    public String toString() {
        return new String(this.cache, 0, this.pos);
    }

    public void clear() {
        this.pos = 0;
    }

    public void append(char c) {
        if (this.pos < this.cache.length - 1) {
            this.cache[this.pos] = c;
            this.pos++;
        }
    }

    public int length() {
        return this.pos;
    }
}
