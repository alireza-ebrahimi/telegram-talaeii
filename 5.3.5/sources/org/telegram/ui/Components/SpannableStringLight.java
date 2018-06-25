package org.telegram.ui.Components;

import android.text.SpannableString;
import java.lang.reflect.Field;
import org.telegram.messenger.FileLog;

public class SpannableStringLight extends SpannableString {
    private static boolean fieldsAvailable;
    private static Field mSpanCountField;
    private static Field mSpanDataField;
    private static Field mSpansField;
    private int mSpanCountOverride;
    private int[] mSpanDataOverride;
    private Object[] mSpansOverride;
    private int num;

    public SpannableStringLight(CharSequence source) {
        super(source);
        try {
            this.mSpansOverride = (Object[]) mSpansField.get(this);
            this.mSpanDataOverride = (int[]) mSpanDataField.get(this);
            this.mSpanCountOverride = ((Integer) mSpanCountField.get(this)).intValue();
        } catch (Throwable e) {
            FileLog.e(e);
        }
    }

    public void setSpansCount(int count) {
        count += this.mSpanCountOverride;
        this.mSpansOverride = new Object[count];
        this.mSpanDataOverride = new int[(count * 3)];
        this.num = this.mSpanCountOverride;
        this.mSpanCountOverride = count;
        try {
            mSpansField.set(this, this.mSpansOverride);
            mSpanDataField.set(this, this.mSpanDataOverride);
            mSpanCountField.set(this, Integer.valueOf(this.mSpanCountOverride));
        } catch (Throwable e) {
            FileLog.e(e);
        }
    }

    public static boolean isFieldsAvailable() {
        if (!fieldsAvailable && mSpansField == null) {
            try {
                mSpansField = SpannableString.class.getSuperclass().getDeclaredField("mSpans");
                mSpansField.setAccessible(true);
                mSpanDataField = SpannableString.class.getSuperclass().getDeclaredField("mSpanData");
                mSpanDataField.setAccessible(true);
                mSpanCountField = SpannableString.class.getSuperclass().getDeclaredField("mSpanCount");
                mSpanCountField.setAccessible(true);
            } catch (Throwable e) {
                FileLog.e(e);
            }
            fieldsAvailable = true;
        }
        if (mSpansField != null) {
            return true;
        }
        return false;
    }

    public void setSpanLight(Object what, int start, int end, int flags) {
        this.mSpansOverride[this.num] = what;
        this.mSpanDataOverride[this.num * 3] = start;
        this.mSpanDataOverride[(this.num * 3) + 1] = end;
        this.mSpanDataOverride[(this.num * 3) + 2] = flags;
        this.num++;
    }

    public void removeSpan(Object what) {
        super.removeSpan(what);
    }
}
