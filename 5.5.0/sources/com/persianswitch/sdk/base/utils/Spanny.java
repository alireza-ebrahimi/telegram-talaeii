package com.persianswitch.sdk.base.utils;

import android.text.Editable;
import android.text.SpannableStringBuilder;

public class Spanny extends SpannableStringBuilder {
    /* renamed from: a */
    private int f7102a = 33;

    public interface GetSpan {
    }

    public Spanny() {
        super(TtmlNode.ANONYMOUS_REGION_ID);
    }

    /* renamed from: a */
    private void m10768a(Object obj, int i, int i2) {
        setSpan(obj, i, i2, this.f7102a);
    }

    /* renamed from: a */
    public Spanny m10769a(CharSequence charSequence) {
        super.append(charSequence);
        return this;
    }

    /* renamed from: a */
    public Spanny m10770a(CharSequence charSequence, Object obj) {
        m10769a(charSequence);
        m10768a(obj, length() - charSequence.length(), length());
        return this;
    }

    /* renamed from: a */
    public Spanny m10771a(CharSequence charSequence, Object... objArr) {
        m10769a(charSequence);
        for (Object a : objArr) {
            m10768a(a, length() - charSequence.length(), length());
        }
        return this;
    }

    public /* synthetic */ Editable append(CharSequence charSequence) {
        return m10769a(charSequence);
    }

    /* renamed from: append */
    public /* synthetic */ SpannableStringBuilder m14413append(CharSequence charSequence) {
        return m10769a(charSequence);
    }

    /* renamed from: append */
    public /* synthetic */ Appendable m14414append(CharSequence charSequence) {
        return m10769a(charSequence);
    }
}
