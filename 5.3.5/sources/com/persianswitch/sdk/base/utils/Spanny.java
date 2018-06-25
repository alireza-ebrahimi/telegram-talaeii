package com.persianswitch.sdk.base.utils;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;

public class Spanny extends SpannableStringBuilder {
    private int flag = 33;

    public interface GetSpan {
        Object getSpan();
    }

    public Spanny() {
        super("");
    }

    public Spanny(CharSequence text) {
        super(text);
    }

    public Spanny(CharSequence text, Object... spans) {
        super(text);
        for (Object span : spans) {
            setSpan(span, 0, length());
        }
    }

    public Spanny(CharSequence text, Object span) {
        super(text);
        setSpan(span, 0, text.length());
    }

    public Spanny append(CharSequence text, Object... spans) {
        append(text);
        for (Object span : spans) {
            setSpan(span, length() - text.length(), length());
        }
        return this;
    }

    public Spanny append(CharSequence text, Object span) {
        append(text);
        setSpan(span, length() - text.length(), length());
        return this;
    }

    public Spanny append(CharSequence text, ImageSpan imageSpan) {
        text = "." + text;
        append(text);
        setSpan(imageSpan, length() - text.length(), (length() - text.length()) + 1);
        return this;
    }

    public Spanny append(CharSequence text) {
        super.append(text);
        return this;
    }

    @Deprecated
    public Spanny appendText(CharSequence text) {
        append(text);
        return this;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    private void setSpan(Object span, int start, int end) {
        setSpan(span, start, end, this.flag);
    }

    public Spanny findAndSpan(CharSequence textToSpan, GetSpan getSpan) {
        int lastIndex = 0;
        while (lastIndex != -1) {
            lastIndex = toString().indexOf(textToSpan.toString(), lastIndex);
            if (lastIndex != -1) {
                setSpan(getSpan.getSpan(), lastIndex, textToSpan.length() + lastIndex);
                lastIndex += textToSpan.length();
            }
        }
        return this;
    }

    public static SpannableString spanText(CharSequence text, Object... spans) {
        SpannableString spannableString = new SpannableString(text);
        for (Object span : spans) {
            spannableString.setSpan(span, 0, text.length(), 33);
        }
        return spannableString;
    }

    public static SpannableString spanText(CharSequence text, Object span) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(span, 0, text.length(), 33);
        return spannableString;
    }
}
