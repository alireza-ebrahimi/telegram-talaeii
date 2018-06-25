package com.p096g.p097a;

import java.io.PrintWriter;

/* renamed from: com.g.a.s */
public class C1644s {
    /* renamed from: a */
    public final int f5073a;
    /* renamed from: b */
    public final int f5074b;
    /* renamed from: c */
    public final long f5075c;
    /* renamed from: d */
    public final long f5076d;
    /* renamed from: e */
    public final long f5077e;
    /* renamed from: f */
    public final long f5078f;
    /* renamed from: g */
    public final long f5079g;
    /* renamed from: h */
    public final long f5080h;
    /* renamed from: i */
    public final long f5081i;
    /* renamed from: j */
    public final long f5082j;
    /* renamed from: k */
    public final int f5083k;
    /* renamed from: l */
    public final int f5084l;
    /* renamed from: m */
    public final int f5085m;
    /* renamed from: n */
    public final long f5086n;

    public C1644s(int i, int i2, long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, int i3, int i4, int i5, long j9) {
        this.f5073a = i;
        this.f5074b = i2;
        this.f5075c = j;
        this.f5076d = j2;
        this.f5077e = j3;
        this.f5078f = j4;
        this.f5079g = j5;
        this.f5080h = j6;
        this.f5081i = j7;
        this.f5082j = j8;
        this.f5083k = i3;
        this.f5084l = i4;
        this.f5085m = i5;
        this.f5086n = j9;
    }

    /* renamed from: a */
    public void m8051a(PrintWriter printWriter) {
        printWriter.println("===============BEGIN PICASSO STATS ===============");
        printWriter.println("Memory Cache Stats");
        printWriter.print("  Max Cache Size: ");
        printWriter.println(this.f5073a);
        printWriter.print("  Cache Size: ");
        printWriter.println(this.f5074b);
        printWriter.print("  Cache % Full: ");
        printWriter.println((int) Math.ceil((double) ((((float) this.f5074b) / ((float) this.f5073a)) * 100.0f)));
        printWriter.print("  Cache Hits: ");
        printWriter.println(this.f5075c);
        printWriter.print("  Cache Misses: ");
        printWriter.println(this.f5076d);
        printWriter.println("Network Stats");
        printWriter.print("  Download Count: ");
        printWriter.println(this.f5083k);
        printWriter.print("  Total Download Size: ");
        printWriter.println(this.f5077e);
        printWriter.print("  Average Download Size: ");
        printWriter.println(this.f5080h);
        printWriter.println("Bitmap Stats");
        printWriter.print("  Total Bitmaps Decoded: ");
        printWriter.println(this.f5084l);
        printWriter.print("  Total Bitmap Size: ");
        printWriter.println(this.f5078f);
        printWriter.print("  Total Transformed Bitmaps: ");
        printWriter.println(this.f5085m);
        printWriter.print("  Total Transformed Bitmap Size: ");
        printWriter.println(this.f5079g);
        printWriter.print("  Average Bitmap Size: ");
        printWriter.println(this.f5081i);
        printWriter.print("  Average Transformed Bitmap Size: ");
        printWriter.println(this.f5082j);
        printWriter.println("===============END PICASSO STATS ===============");
        printWriter.flush();
    }

    public String toString() {
        return "StatsSnapshot{maxSize=" + this.f5073a + ", size=" + this.f5074b + ", cacheHits=" + this.f5075c + ", cacheMisses=" + this.f5076d + ", downloadCount=" + this.f5083k + ", totalDownloadSize=" + this.f5077e + ", averageDownloadSize=" + this.f5080h + ", totalOriginalBitmapSize=" + this.f5078f + ", totalTransformedBitmapSize=" + this.f5079g + ", averageOriginalBitmapSize=" + this.f5081i + ", averageTransformedBitmapSize=" + this.f5082j + ", originalBitmapCount=" + this.f5084l + ", transformedBitmapCount=" + this.f5085m + ", timeStamp=" + this.f5086n + '}';
    }
}
