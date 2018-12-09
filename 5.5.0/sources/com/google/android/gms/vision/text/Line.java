package com.google.android.gms.vision.text;

import android.graphics.Point;
import android.graphics.Rect;
import com.google.android.gms.internal.vision.zzac;
import com.google.android.gms.internal.vision.zzt;
import java.util.ArrayList;
import java.util.List;

public class Line implements Text {
    private zzt zzcu;
    private List<Element> zzcv;

    Line(zzt zzt) {
        this.zzcu = zzt;
    }

    public float getAngle() {
        return this.zzcu.zzde.zzdc;
    }

    public Rect getBoundingBox() {
        return zzc.zza((Text) this);
    }

    public List<? extends Text> getComponents() {
        if (this.zzcu.zzdd.length == 0) {
            return new ArrayList(0);
        }
        if (this.zzcv == null) {
            this.zzcv = new ArrayList(this.zzcu.zzdd.length);
            for (zzac element : this.zzcu.zzdd) {
                this.zzcv.add(new Element(element));
            }
        }
        return this.zzcv;
    }

    public Point[] getCornerPoints() {
        return zzc.zza(this.zzcu.zzde);
    }

    public String getLanguage() {
        return this.zzcu.zzcy;
    }

    public String getValue() {
        return this.zzcu.zzdh;
    }

    public boolean isVertical() {
        return this.zzcu.zzdj;
    }
}
