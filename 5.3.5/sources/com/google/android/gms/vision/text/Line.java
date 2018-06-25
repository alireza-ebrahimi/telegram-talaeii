package com.google.android.gms.vision.text;

import android.graphics.Point;
import android.graphics.Rect;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzdll;
import com.google.android.gms.internal.zzdlu;
import java.util.ArrayList;
import java.util.List;

public class Line implements Text {
    private zzdll zzlhx;
    private List<Element> zzlhy;

    @Hide
    Line(zzdll zzdll) {
        this.zzlhx = zzdll;
    }

    public float getAngle() {
        return this.zzlhx.zzlih.zzlif;
    }

    public Rect getBoundingBox() {
        return zzc.zza((Text) this);
    }

    public List<? extends Text> getComponents() {
        if (this.zzlhx.zzlig.length == 0) {
            return new ArrayList(0);
        }
        if (this.zzlhy == null) {
            this.zzlhy = new ArrayList(this.zzlhx.zzlig.length);
            for (zzdlu element : this.zzlhx.zzlig) {
                this.zzlhy.add(new Element(element));
            }
        }
        return this.zzlhy;
    }

    public Point[] getCornerPoints() {
        return zzc.zza(this.zzlhx.zzlih);
    }

    public String getLanguage() {
        return this.zzlhx.zzlib;
    }

    public String getValue() {
        return this.zzlhx.zzlik;
    }

    public boolean isVertical() {
        return this.zzlhx.zzlin;
    }
}
