package org.telegram.ui.Components;

import android.os.Build.VERSION;
import android.text.Layout.Alignment;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.StaticLayout.Builder;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import java.lang.reflect.Constructor;
import org.telegram.messenger.FileLog;

public class StaticLayoutEx {
    private static final String TEXT_DIRS_CLASS = "android.text.TextDirectionHeuristics";
    private static final String TEXT_DIR_CLASS = "android.text.TextDirectionHeuristic";
    private static final String TEXT_DIR_FIRSTSTRONG_LTR = "FIRSTSTRONG_LTR";
    private static boolean initialized;
    private static Constructor<StaticLayout> sConstructor;
    private static Object[] sConstructorArgs;
    private static Object sTextDirection;

    public static void init() {
        if (!initialized) {
            try {
                Class<?> textDirClass;
                if (VERSION.SDK_INT >= 18) {
                    textDirClass = TextDirectionHeuristic.class;
                    sTextDirection = TextDirectionHeuristics.FIRSTSTRONG_LTR;
                } else {
                    ClassLoader loader = StaticLayoutEx.class.getClassLoader();
                    textDirClass = loader.loadClass(TEXT_DIR_CLASS);
                    Class<?> textDirsClass = loader.loadClass(TEXT_DIRS_CLASS);
                    sTextDirection = textDirsClass.getField(TEXT_DIR_FIRSTSTRONG_LTR).get(textDirsClass);
                }
                Class<?>[] signature = new Class[]{CharSequence.class, Integer.TYPE, Integer.TYPE, TextPaint.class, Integer.TYPE, Alignment.class, textDirClass, Float.TYPE, Float.TYPE, Boolean.TYPE, TruncateAt.class, Integer.TYPE, Integer.TYPE};
                sConstructor = StaticLayout.class.getDeclaredConstructor(signature);
                sConstructor.setAccessible(true);
                sConstructorArgs = new Object[signature.length];
                initialized = true;
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }
    }

    public static StaticLayout createStaticLayout(CharSequence source, TextPaint paint, int width, Alignment align, float spacingmult, float spacingadd, boolean includepad, TruncateAt ellipsize, int ellipsisWidth, int maxLines) {
        return createStaticLayout(source, 0, source.length(), paint, width, align, spacingmult, spacingadd, includepad, ellipsize, ellipsisWidth, maxLines);
    }

    public static StaticLayout createStaticLayout(CharSequence source, int bufstart, int bufend, TextPaint paint, int outerWidth, Alignment align, float spacingMult, float spacingAdd, boolean includePad, TruncateAt ellipsize, int ellipsisWidth, int maxLines) {
        if (maxLines == 1) {
            try {
                CharSequence text = TextUtils.ellipsize(source, paint, (float) ellipsisWidth, TruncateAt.END);
                return new StaticLayout(text, 0, text.length(), paint, outerWidth, align, spacingMult, spacingAdd, includePad);
            } catch (Exception e) {
                FileLog.e(e);
                return null;
            }
        }
        StaticLayout layout;
        if (VERSION.SDK_INT >= 23) {
            layout = Builder.obtain(source, 0, source.length(), paint, outerWidth).setAlignment(align).setLineSpacing(spacingAdd, spacingMult).setIncludePad(includePad).setEllipsize(null).setEllipsizedWidth(ellipsisWidth).setBreakStrategy(1).setHyphenationFrequency(1).build();
        } else {
            layout = new StaticLayout(source, paint, outerWidth, align, spacingMult, spacingAdd, includePad);
        }
        if (layout.getLineCount() <= maxLines) {
            return layout;
        }
        int off;
        float left = layout.getLineLeft(maxLines - 1);
        if (left != 0.0f) {
            off = layout.getOffsetForHorizontal(maxLines - 1, left);
        } else {
            off = layout.getOffsetForHorizontal(maxLines - 1, layout.getLineWidth(maxLines - 1));
        }
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(source.subSequence(0, Math.max(0, off - 1)));
        stringBuilder.append("…");
        return new StaticLayout(stringBuilder, paint, outerWidth, align, spacingMult, spacingAdd, includePad);
    }
}
