package com.squareup.picasso;

import android.util.Log;
import java.io.PrintWriter;
import java.io.StringWriter;

public class StatsSnapshot {
    public final long averageDownloadSize;
    public final long averageOriginalBitmapSize;
    public final long averageTransformedBitmapSize;
    public final long cacheHits;
    public final long cacheMisses;
    public final int downloadCount;
    public final int maxSize;
    public final int originalBitmapCount;
    public final int size;
    public final long timeStamp;
    public final long totalDownloadSize;
    public final long totalOriginalBitmapSize;
    public final long totalTransformedBitmapSize;
    public final int transformedBitmapCount;

    public StatsSnapshot(int maxSize, int size, long cacheHits, long cacheMisses, long totalDownloadSize, long totalOriginalBitmapSize, long totalTransformedBitmapSize, long averageDownloadSize, long averageOriginalBitmapSize, long averageTransformedBitmapSize, int downloadCount, int originalBitmapCount, int transformedBitmapCount, long timeStamp) {
        this.maxSize = maxSize;
        this.size = size;
        this.cacheHits = cacheHits;
        this.cacheMisses = cacheMisses;
        this.totalDownloadSize = totalDownloadSize;
        this.totalOriginalBitmapSize = totalOriginalBitmapSize;
        this.totalTransformedBitmapSize = totalTransformedBitmapSize;
        this.averageDownloadSize = averageDownloadSize;
        this.averageOriginalBitmapSize = averageOriginalBitmapSize;
        this.averageTransformedBitmapSize = averageTransformedBitmapSize;
        this.downloadCount = downloadCount;
        this.originalBitmapCount = originalBitmapCount;
        this.transformedBitmapCount = transformedBitmapCount;
        this.timeStamp = timeStamp;
    }

    public void dump() {
        StringWriter logWriter = new StringWriter();
        dump(new PrintWriter(logWriter));
        Log.i("Picasso", logWriter.toString());
    }

    public void dump(PrintWriter writer) {
        writer.println("===============BEGIN PICASSO STATS ===============");
        writer.println("Memory Cache Stats");
        writer.print("  Max Cache Size: ");
        writer.println(this.maxSize);
        writer.print("  Cache Size: ");
        writer.println(this.size);
        writer.print("  Cache % Full: ");
        writer.println((int) Math.ceil((double) ((((float) this.size) / ((float) this.maxSize)) * 100.0f)));
        writer.print("  Cache Hits: ");
        writer.println(this.cacheHits);
        writer.print("  Cache Misses: ");
        writer.println(this.cacheMisses);
        writer.println("Network Stats");
        writer.print("  Download Count: ");
        writer.println(this.downloadCount);
        writer.print("  Total Download Size: ");
        writer.println(this.totalDownloadSize);
        writer.print("  Average Download Size: ");
        writer.println(this.averageDownloadSize);
        writer.println("Bitmap Stats");
        writer.print("  Total Bitmaps Decoded: ");
        writer.println(this.originalBitmapCount);
        writer.print("  Total Bitmap Size: ");
        writer.println(this.totalOriginalBitmapSize);
        writer.print("  Total Transformed Bitmaps: ");
        writer.println(this.transformedBitmapCount);
        writer.print("  Total Transformed Bitmap Size: ");
        writer.println(this.totalTransformedBitmapSize);
        writer.print("  Average Bitmap Size: ");
        writer.println(this.averageOriginalBitmapSize);
        writer.print("  Average Transformed Bitmap Size: ");
        writer.println(this.averageTransformedBitmapSize);
        writer.println("===============END PICASSO STATS ===============");
        writer.flush();
    }

    public String toString() {
        return "StatsSnapshot{maxSize=" + this.maxSize + ", size=" + this.size + ", cacheHits=" + this.cacheHits + ", cacheMisses=" + this.cacheMisses + ", downloadCount=" + this.downloadCount + ", totalDownloadSize=" + this.totalDownloadSize + ", averageDownloadSize=" + this.averageDownloadSize + ", totalOriginalBitmapSize=" + this.totalOriginalBitmapSize + ", totalTransformedBitmapSize=" + this.totalTransformedBitmapSize + ", averageOriginalBitmapSize=" + this.averageOriginalBitmapSize + ", averageTransformedBitmapSize=" + this.averageTransformedBitmapSize + ", originalBitmapCount=" + this.originalBitmapCount + ", transformedBitmapCount=" + this.transformedBitmapCount + ", timeStamp=" + this.timeStamp + '}';
    }
}
