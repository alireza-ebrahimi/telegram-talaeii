package com.crashlytics.android.p066c;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;

/* renamed from: com.crashlytics.android.c.am */
final class am {
    /* renamed from: a */
    private static final FilenameFilter f4229a = new C13941();

    /* renamed from: com.crashlytics.android.c.am$1 */
    static class C13941 implements FilenameFilter {
        C13941() {
        }

        public boolean accept(File file, String str) {
            return true;
        }
    }

    /* renamed from: a */
    static int m7036a(File file, int i, Comparator<File> comparator) {
        return am.m7037a(file, f4229a, i, comparator);
    }

    /* renamed from: a */
    static int m7037a(File file, FilenameFilter filenameFilter, int i, Comparator<File> comparator) {
        int i2 = 0;
        File[] listFiles = file.listFiles(filenameFilter);
        if (listFiles != null) {
            int length = listFiles.length;
            Arrays.sort(listFiles, comparator);
            int length2 = listFiles.length;
            i2 = length;
            length = 0;
            while (length < length2) {
                File file2 = listFiles[length];
                if (i2 <= i) {
                    break;
                }
                file2.delete();
                length++;
                i2--;
            }
        }
        return i2;
    }
}
