package com.google.android.gms.vision.face;

import android.graphics.PointF;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.Arrays;
import java.util.List;

public class Face {
    public static final float UNCOMPUTED_PROBABILITY = -1.0f;
    private int mId;
    private PointF zzbd;
    private float zzbe;
    private float zzbf;
    private float zzbg;
    private float zzbh;
    private List<Landmark> zzbi;
    private float zzbj;
    private float zzbk;
    private float zzbl;

    public Face(int i, PointF pointF, float f, float f2, float f3, float f4, Landmark[] landmarkArr, float f5, float f6, float f7) {
        this.mId = i;
        this.zzbd = pointF;
        this.zzbe = f;
        this.zzbf = f2;
        this.zzbg = f3;
        this.zzbh = f4;
        this.zzbi = Arrays.asList(landmarkArr);
        if (f5 < BitmapDescriptorFactory.HUE_RED || f5 > 1.0f) {
            this.zzbj = -1.0f;
        } else {
            this.zzbj = f5;
        }
        if (f6 < BitmapDescriptorFactory.HUE_RED || f6 > 1.0f) {
            this.zzbk = -1.0f;
        } else {
            this.zzbk = f6;
        }
        if (f7 < BitmapDescriptorFactory.HUE_RED || f7 > 1.0f) {
            this.zzbl = -1.0f;
        } else {
            this.zzbl = f7;
        }
    }

    public float getEulerY() {
        return this.zzbg;
    }

    public float getEulerZ() {
        return this.zzbh;
    }

    public float getHeight() {
        return this.zzbf;
    }

    public int getId() {
        return this.mId;
    }

    public float getIsLeftEyeOpenProbability() {
        return this.zzbj;
    }

    public float getIsRightEyeOpenProbability() {
        return this.zzbk;
    }

    public float getIsSmilingProbability() {
        return this.zzbl;
    }

    public List<Landmark> getLandmarks() {
        return this.zzbi;
    }

    public PointF getPosition() {
        return new PointF(this.zzbd.x - (this.zzbe / 2.0f), this.zzbd.y - (this.zzbf / 2.0f));
    }

    public float getWidth() {
        return this.zzbe;
    }
}
