package com.google.android.gms.vision.text;

import android.graphics.Point;
import android.graphics.Rect;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzdlu;
import java.util.ArrayList;
import java.util.List;

public class Element implements Text {
    private zzdlu zzlhw;

    @Hide
    Element(zzdlu zzdlu) {
        this.zzlhw = zzdlu;
    }

    public Rect getBoundingBox() {
        return zzc.zza((Text) this);
    }

    public List<? extends Text> getComponents() {
        return new ArrayList();
    }

    public Point[] getCornerPoints() {
        return zzc.zza(this.zzlhw.zzlih);
    }

    public String getLanguage() {
        return this.zzlhw.zzlib;
    }

    public String getValue() {
        return this.zzlhw.zzlik;
    }
}
