package com.google.android.gms.vision.text;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.SparseArray;
import com.google.android.gms.internal.vision.zzn;
import com.google.android.gms.internal.vision.zzt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class TextBlock implements Text {
    private Point[] cornerPoints;
    private zzt[] zzcw;
    private List<Line> zzcx;
    private String zzcy;
    private Rect zzcz;

    TextBlock(SparseArray<zzt> sparseArray) {
        this.zzcw = new zzt[sparseArray.size()];
        for (int i = 0; i < this.zzcw.length; i++) {
            this.zzcw[i] = (zzt) sparseArray.valueAt(i);
        }
    }

    public Rect getBoundingBox() {
        if (this.zzcz == null) {
            this.zzcz = zzc.zza((Text) this);
        }
        return this.zzcz;
    }

    public List<? extends Text> getComponents() {
        if (this.zzcw.length == 0) {
            return new ArrayList(0);
        }
        if (this.zzcx == null) {
            this.zzcx = new ArrayList(this.zzcw.length);
            for (zzt line : this.zzcw) {
                this.zzcx.add(new Line(line));
            }
        }
        return this.zzcx;
    }

    public Point[] getCornerPoints() {
        if (this.cornerPoints == null) {
            if (this.zzcw.length == 0) {
                this.cornerPoints = new Point[0];
            } else {
                int i;
                double sin;
                int i2;
                int i3;
                int i4 = Integer.MAX_VALUE;
                int i5 = Integer.MIN_VALUE;
                int i6 = Integer.MAX_VALUE;
                int i7 = Integer.MIN_VALUE;
                for (zzt zzt : this.zzcw) {
                    zzn zzn = zzt.zzde;
                    zzn zzn2 = this.zzcw[0].zzde;
                    int i8 = -zzn2.left;
                    int i9 = -zzn2.top;
                    sin = Math.sin(Math.toRadians((double) zzn2.zzdc));
                    double cos = Math.cos(Math.toRadians((double) zzn2.zzdc));
                    Point[] pointArr = new Point[4];
                    pointArr[0] = new Point(zzn.left, zzn.top);
                    pointArr[0].offset(i8, i9);
                    i2 = (int) ((((double) pointArr[0].x) * cos) + (((double) pointArr[0].y) * sin));
                    i8 = (int) ((sin * ((double) (-pointArr[0].x))) + (cos * ((double) pointArr[0].y)));
                    pointArr[0].x = i2;
                    pointArr[0].y = i8;
                    pointArr[1] = new Point(zzn.width + i2, i8);
                    pointArr[2] = new Point(zzn.width + i2, zzn.height + i8);
                    pointArr[3] = new Point(i2, zzn.height + i8);
                    i3 = 0;
                    while (i3 < 4) {
                        Point point = pointArr[i3];
                        i2 = Math.min(i4, point.x);
                        i4 = Math.max(i5, point.x);
                        i5 = Math.min(i6, point.y);
                        i3++;
                        i7 = Math.max(i7, point.y);
                        i6 = i5;
                        i5 = i4;
                        i4 = i2;
                    }
                }
                zzn zzn3 = this.zzcw[0].zzde;
                i3 = zzn3.left;
                i2 = zzn3.top;
                double sin2 = Math.sin(Math.toRadians((double) zzn3.zzdc));
                sin = Math.cos(Math.toRadians((double) zzn3.zzdc));
                Point[] pointArr2 = new Point[]{new Point(i4, i6), new Point(i5, i6), new Point(i5, i7), new Point(i4, i7)};
                for (i = 0; i < 4; i++) {
                    i6 = (int) ((((double) pointArr2[i].x) * sin2) + (((double) pointArr2[i].y) * sin));
                    pointArr2[i].x = (int) ((((double) pointArr2[i].x) * sin) - (((double) pointArr2[i].y) * sin2));
                    pointArr2[i].y = i6;
                    pointArr2[i].offset(i3, i2);
                }
                this.cornerPoints = pointArr2;
            }
        }
        return this.cornerPoints;
    }

    public String getLanguage() {
        if (this.zzcy != null) {
            return this.zzcy;
        }
        HashMap hashMap = new HashMap();
        for (zzt zzt : this.zzcw) {
            hashMap.put(zzt.zzcy, Integer.valueOf((hashMap.containsKey(zzt.zzcy) ? ((Integer) hashMap.get(zzt.zzcy)).intValue() : 0) + 1));
        }
        this.zzcy = (String) ((Entry) Collections.max(hashMap.entrySet(), new zza(this))).getKey();
        if (this.zzcy == null || this.zzcy.isEmpty()) {
            this.zzcy = "und";
        }
        return this.zzcy;
    }

    public String getValue() {
        if (this.zzcw.length == 0) {
            return TtmlNode.ANONYMOUS_REGION_ID;
        }
        StringBuilder stringBuilder = new StringBuilder(this.zzcw[0].zzdh);
        for (int i = 1; i < this.zzcw.length; i++) {
            stringBuilder.append("\n");
            stringBuilder.append(this.zzcw[i].zzdh);
        }
        return stringBuilder.toString();
    }
}
