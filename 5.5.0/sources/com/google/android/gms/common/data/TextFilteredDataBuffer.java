package com.google.android.gms.common.data;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.common.data.TextFilterable.StringFilter;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.ArrayList;
import java.util.Locale;

public final class TextFilteredDataBuffer<T> extends FilteredDataBuffer<T> implements TextFilterable {
    private final ArrayList<Integer> zzob = new ArrayList();
    private AbstractDataBuffer<T> zzoc;
    private final String zzoo;
    private String zzop;
    private StringFilter zzoq;
    private Locale zzor;

    public TextFilteredDataBuffer(AbstractDataBuffer<T> abstractDataBuffer, String str) {
        super(abstractDataBuffer);
        this.zzoc = abstractDataBuffer;
        this.zzoo = str;
    }

    private final String zzh(String str) {
        String toLowerCase = str.toLowerCase(this.zzor);
        StringBuilder stringBuilder = new StringBuilder();
        int length = toLowerCase.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isIdentifierIgnorable(toLowerCase.charAt(i))) {
                stringBuilder.append(toLowerCase.charAt(i));
            }
        }
        return stringBuilder.toString();
    }

    public final int computeRealPosition(int i) {
        if (TextUtils.isEmpty(this.zzop)) {
            return i;
        }
        if (i >= 0 && i < this.zzob.size()) {
            return ((Integer) this.zzob.get(i)).intValue();
        }
        throw new IllegalArgumentException("Position " + i + " is out of bounds for this buffer");
    }

    public final int getCount() {
        return TextUtils.isEmpty(this.zzop) ? this.mDataBuffer.getCount() : this.zzob.size();
    }

    public final void setFilterTerm(Context context, StringFilter stringFilter, String str) {
        Preconditions.checkNotNull(stringFilter);
        this.zzop = str;
        this.zzoq = stringFilter;
        if (TextUtils.isEmpty(this.zzop)) {
            this.zzob.clear();
            return;
        }
        this.zzor = context.getResources().getConfiguration().locale;
        this.zzop = zzh(this.zzop);
        this.zzob.clear();
        DataHolder dataHolder = this.zzoc.mDataHolder;
        String str2 = this.zzoo;
        boolean z = this.zzoc instanceof EntityBuffer;
        int i = 0;
        int count = this.zzoc.getCount();
        while (i < count) {
            int zzi = z ? ((EntityBuffer) this.zzoc).zzi(i) : i;
            Object string = dataHolder.getString(str2, zzi, dataHolder.getWindowIndex(zzi));
            if (!TextUtils.isEmpty(string) && this.zzoq.matches(zzh(string), this.zzop)) {
                this.zzob.add(Integer.valueOf(i));
            }
            i++;
        }
    }

    @VisibleForTesting
    public final void setFilterTerm(Context context, String str) {
        setFilterTerm(context, CONTAINS, str);
    }
}
