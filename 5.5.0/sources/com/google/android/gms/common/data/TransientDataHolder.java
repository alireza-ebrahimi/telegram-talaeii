package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder.Builder;
import com.google.android.gms.common.util.VisibleForTesting;

public final class TransientDataHolder {
    private String zzos;
    private String zzot;
    private Builder zzou;

    public TransientDataHolder(String[] strArr) {
        this(strArr, null, null, null);
    }

    public TransientDataHolder(String[] strArr, String str, String str2, String str3) {
        this.zzos = str2;
        this.zzot = str3;
        if (str != null) {
            this.zzou = DataHolder.builder(strArr, str);
        } else {
            this.zzou = DataHolder.builder(strArr);
        }
    }

    public final void addRow(ContentValues contentValues) {
        this.zzou.withRow(contentValues);
    }

    @VisibleForTesting
    public final DataHolder build(int i) {
        return build(i, new Bundle(), -1);
    }

    public final DataHolder build(int i, Bundle bundle, int i2) {
        bundle.putString(DataBufferUtils.KEY_NEXT_PAGE_TOKEN, this.zzot);
        bundle.putString(DataBufferUtils.KEY_PREV_PAGE_TOKEN, this.zzos);
        return this.zzou.build(i, bundle, i2);
    }

    public final boolean containsRowWithValue(String str, Object obj) {
        return this.zzou.containsRowWithValue(str, obj);
    }

    public final int getCount() {
        return this.zzou.getCount();
    }

    public final String getNextToken() {
        return this.zzot;
    }

    public final String getPrevToken() {
        return this.zzos;
    }

    public final void modifyUniqueRowValue(Object obj, String str, Object obj2) {
        this.zzou.modifyUniqueRowValue(obj, str, obj2);
    }

    @VisibleForTesting
    public final void removeRows(String str, Object obj) {
        this.zzou.removeRowsWithValue(str, obj);
    }

    public final void setNextToken(String str) {
        this.zzot = str;
    }

    public final void setPrevToken(String str) {
        this.zzos = str;
    }

    public final void sortData(String str) {
        this.zzou.sort(str);
    }

    public final void sortDataDescending(String str) {
        this.zzou.descendingSort(str);
    }
}
