package com.google.android.gms.vision.text;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.SparseArray;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzdlf;
import com.google.android.gms.internal.zzdll;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class TextBlock implements Text {
    private Point[] cornerPoints;
    private zzdll[] zzlhz;
    private List<Line> zzlia;
    private String zzlib;
    private Rect zzlic;

    @Hide
    TextBlock(SparseArray<zzdll> sparseArray) {
        this.zzlhz = new zzdll[sparseArray.size()];
        for (int i = 0; i < this.zzlhz.length; i++) {
            this.zzlhz[i] = (zzdll) sparseArray.valueAt(i);
        }
    }

    private static Point[] zza(int i, int i2, int i3, int i4, zzdlf zzdlf) {
        int i5 = zzdlf.left;
        int i6 = zzdlf.top;
        double sin = Math.sin(Math.toRadians((double) zzdlf.zzlif));
        double cos = Math.cos(Math.toRadians((double) zzdlf.zzlif));
        Point[] pointArr = new Point[]{new Point(i, i2), new Point(i3, i2), new Point(i3, i4), new Point(i, i4)};
        for (int i7 = 0; i7 < 4; i7++) {
            int i8 = (int) ((((double) pointArr[i7].x) * sin) + (((double) pointArr[i7].y) * cos));
            pointArr[i7].x = (int) ((((double) pointArr[i7].x) * cos) - (((double) pointArr[i7].y) * sin));
            pointArr[i7].y = i8;
            pointArr[i7].offset(i5, i6);
        }
        return pointArr;
    }

    public Rect getBoundingBox() {
        if (this.zzlic == null) {
            this.zzlic = zzc.zza((Text) this);
        }
        return this.zzlic;
    }

    public List<? extends Text> getComponents() {
        if (this.zzlhz.length == 0) {
            return new ArrayList(0);
        }
        if (this.zzlia == null) {
            this.zzlia = new ArrayList(this.zzlhz.length);
            for (zzdll line : this.zzlhz) {
                this.zzlia.add(new Line(line));
            }
        }
        return this.zzlia;
    }

    public Point[] getCornerPoints() {
        if (this.cornerPoints == null) {
            if (this.zzlhz.length == 0) {
                this.cornerPoints = new Point[0];
            } else {
                int i = Integer.MAX_VALUE;
                int i2 = Integer.MIN_VALUE;
                int i3 = Integer.MAX_VALUE;
                int i4 = Integer.MIN_VALUE;
                for (zzdll zzdll : this.zzlhz) {
                    zzdlf zzdlf = zzdll.zzlih;
                    zzdlf zzdlf2 = this.zzlhz[0].zzlih;
                    int i5 = -zzdlf2.left;
                    int i6 = -zzdlf2.top;
                    double sin = Math.sin(Math.toRadians((double) zzdlf2.zzlif));
                    double cos = Math.cos(Math.toRadians((double) zzdlf2.zzlif));
                    Point[] pointArr = new Point[4];
                    pointArr[0] = new Point(zzdlf.left, zzdlf.top);
                    pointArr[0].offset(i5, i6);
                    int i7 = (int) ((((double) pointArr[0].x) * cos) + (((double) pointArr[0].y) * sin));
                    i5 = (int) ((sin * ((double) (-pointArr[0].x))) + (cos * ((double) pointArr[0].y)));
                    pointArr[0].x = i7;
                    pointArr[0].y = i5;
                    pointArr[1] = new Point(zzdlf.width + i7, i5);
                    pointArr[2] = new Point(zzdlf.width + i7, zzdlf.height + i5);
                    pointArr[3] = new Point(i7, zzdlf.height + i5);
                    int i8 = 0;
                    while (i8 < 4) {
                        Point point = pointArr[i8];
                        i7 = Math.min(i, point.x);
                        i = Math.max(i2, point.x);
                        i2 = Math.min(i3, point.y);
                        i8++;
                        i4 = Math.max(i4, point.y);
                        i3 = i2;
                        i2 = i;
                        i = i7;
                    }
                }
                this.cornerPoints = zza(i, i3, i2, i4, this.zzlhz[0].zzlih);
            }
        }
        return this.cornerPoints;
    }

    public String getLanguage() {
        if (this.zzlib != null) {
            return this.zzlib;
        }
        HashMap hashMap = new HashMap();
        for (zzdll zzdll : this.zzlhz) {
            hashMap.put(zzdll.zzlib, Integer.valueOf((hashMap.containsKey(zzdll.zzlib) ? ((Integer) hashMap.get(zzdll.zzlib)).intValue() : 0) + 1));
        }
        this.zzlib = (String) ((Entry) Collections.max(hashMap.entrySet(), new zza(this))).getKey();
        if (this.zzlib == null || this.zzlib.isEmpty()) {
            this.zzlib = "und";
        }
        return this.zzlib;
    }

    public String getValue() {
        if (this.zzlhz.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(this.zzlhz[0].zzlik);
        for (int i = 1; i < this.zzlhz.length; i++) {
            stringBuilder.append(LogCollector.LINE_SEPARATOR);
            stringBuilder.append(this.zzlhz[i].zzlik);
        }
        return stringBuilder.toString();
    }
}
