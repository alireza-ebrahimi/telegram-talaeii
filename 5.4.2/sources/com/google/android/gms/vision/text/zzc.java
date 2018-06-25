package com.google.android.gms.vision.text;

import android.graphics.Point;
import android.graphics.Rect;
import com.google.android.gms.internal.vision.zzn;

final class zzc {
    static Rect zza(Text text) {
        int i = Integer.MAX_VALUE;
        int i2 = Integer.MIN_VALUE;
        int i3 = Integer.MIN_VALUE;
        int i4 = Integer.MAX_VALUE;
        for (Point point : text.getCornerPoints()) {
            i4 = Math.min(i4, point.x);
            i3 = Math.max(i3, point.x);
            i = Math.min(i, point.y);
            i2 = Math.max(i2, point.y);
        }
        return new Rect(i4, i, i3, i2);
    }

    static Point[] zza(zzn zzn) {
        r0 = new Point[4];
        double sin = Math.sin(Math.toRadians((double) zzn.zzdc));
        double cos = Math.cos(Math.toRadians((double) zzn.zzdc));
        r0[0] = new Point(zzn.left, zzn.top);
        r0[1] = new Point((int) (((double) zzn.left) + (((double) zzn.width) * cos)), (int) (((double) zzn.top) + (((double) zzn.width) * sin)));
        r0[2] = new Point((int) (((double) r0[1].x) - (sin * ((double) zzn.height))), (int) ((cos * ((double) zzn.height)) + ((double) r0[1].y)));
        r0[3] = new Point(r0[0].x + (r0[2].x - r0[1].x), r0[0].y + (r0[2].y - r0[1].y));
        return r0;
    }
}
