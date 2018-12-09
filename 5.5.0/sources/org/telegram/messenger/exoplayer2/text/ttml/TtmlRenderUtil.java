package org.telegram.messenger.exoplayer2.text.ttml;

import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan.Standard;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import java.util.Map;

final class TtmlRenderUtil {
    private TtmlRenderUtil() {
    }

    public static void applyStylesToSpan(SpannableStringBuilder spannableStringBuilder, int i, int i2, TtmlStyle ttmlStyle) {
        if (ttmlStyle.getStyle() != -1) {
            spannableStringBuilder.setSpan(new StyleSpan(ttmlStyle.getStyle()), i, i2, 33);
        }
        if (ttmlStyle.isLinethrough()) {
            spannableStringBuilder.setSpan(new StrikethroughSpan(), i, i2, 33);
        }
        if (ttmlStyle.isUnderline()) {
            spannableStringBuilder.setSpan(new UnderlineSpan(), i, i2, 33);
        }
        if (ttmlStyle.hasFontColor()) {
            spannableStringBuilder.setSpan(new ForegroundColorSpan(ttmlStyle.getFontColor()), i, i2, 33);
        }
        if (ttmlStyle.hasBackgroundColor()) {
            spannableStringBuilder.setSpan(new BackgroundColorSpan(ttmlStyle.getBackgroundColor()), i, i2, 33);
        }
        if (ttmlStyle.getFontFamily() != null) {
            spannableStringBuilder.setSpan(new TypefaceSpan(ttmlStyle.getFontFamily()), i, i2, 33);
        }
        if (ttmlStyle.getTextAlign() != null) {
            spannableStringBuilder.setSpan(new Standard(ttmlStyle.getTextAlign()), i, i2, 33);
        }
        switch (ttmlStyle.getFontSizeUnit()) {
            case 1:
                spannableStringBuilder.setSpan(new AbsoluteSizeSpan((int) ttmlStyle.getFontSize(), true), i, i2, 33);
                return;
            case 2:
                spannableStringBuilder.setSpan(new RelativeSizeSpan(ttmlStyle.getFontSize()), i, i2, 33);
                return;
            case 3:
                spannableStringBuilder.setSpan(new RelativeSizeSpan(ttmlStyle.getFontSize() / 100.0f), i, i2, 33);
                return;
            default:
                return;
        }
    }

    static String applyTextElementSpacePolicy(String str) {
        return str.replaceAll("\r\n", "\n").replaceAll(" *\n *", "\n").replaceAll("\n", " ").replaceAll("[ \t\\x0B\f\r]+", " ");
    }

    static void endParagraph(SpannableStringBuilder spannableStringBuilder) {
        int length = spannableStringBuilder.length() - 1;
        while (length >= 0 && spannableStringBuilder.charAt(length) == ' ') {
            length--;
        }
        if (length >= 0 && spannableStringBuilder.charAt(length) != '\n') {
            spannableStringBuilder.append('\n');
        }
    }

    public static TtmlStyle resolveStyle(TtmlStyle ttmlStyle, String[] strArr, Map<String, TtmlStyle> map) {
        if (ttmlStyle == null && strArr == null) {
            return null;
        }
        if (ttmlStyle == null && strArr.length == 1) {
            return (TtmlStyle) map.get(strArr[0]);
        }
        if (ttmlStyle == null && strArr.length > 1) {
            ttmlStyle = new TtmlStyle();
            for (Object obj : strArr) {
                ttmlStyle.chain((TtmlStyle) map.get(obj));
            }
            return ttmlStyle;
        } else if (ttmlStyle != null && strArr != null && strArr.length == 1) {
            return ttmlStyle.chain((TtmlStyle) map.get(strArr[0]));
        } else {
            if (ttmlStyle == null || strArr == null || strArr.length <= 1) {
                return ttmlStyle;
            }
            for (Object obj2 : strArr) {
                ttmlStyle.chain((TtmlStyle) map.get(obj2));
            }
            return ttmlStyle;
        }
    }
}
