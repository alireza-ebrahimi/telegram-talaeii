package com.googlecode.mp4parser.h264;

import java.nio.ShortBuffer;

public class Debug {
    public static final boolean debug = false;

    public static final void print8x8(int[] output) {
        int i = 0;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                System.out.printf("%3d, ", new Object[]{Integer.valueOf(output[i])});
                i++;
            }
            System.out.println();
        }
    }

    public static final void print8x8(short[] output) {
        int i = 0;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                System.out.printf("%3d, ", new Object[]{Short.valueOf(output[i])});
                i++;
            }
            System.out.println();
        }
    }

    public static final void print8x8(ShortBuffer output) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                System.out.printf("%3d, ", new Object[]{Short.valueOf(output.get())});
            }
            System.out.println();
        }
    }

    public static void print(short[] table) {
        int i = 0;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                System.out.printf("%3d, ", new Object[]{Short.valueOf(table[i])});
                i++;
            }
            System.out.println();
        }
    }

    public static void trace(String format, Object... args) {
    }

    public static void print(int i) {
    }

    public static void print(String string) {
    }

    public static void println(String string) {
    }
}
