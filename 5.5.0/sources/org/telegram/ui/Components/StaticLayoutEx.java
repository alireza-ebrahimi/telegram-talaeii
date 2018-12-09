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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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

    public static StaticLayout createStaticLayout(CharSequence charSequence, int i, int i2, TextPaint textPaint, int i3, Alignment alignment, float f, float f2, boolean z, TruncateAt truncateAt, int i4, int i5) {
        if (i5 == 1) {
            try {
                CharSequence ellipsize = TextUtils.ellipsize(charSequence, textPaint, (float) i4, TruncateAt.END);
                return new StaticLayout(ellipsize, 0, ellipsize.length(), textPaint, i3, alignment, f, f2, z);
            } catch (Throwable e) {
                FileLog.e(e);
                return null;
            }
        }
        StaticLayout build = VERSION.SDK_INT >= 23 ? Builder.obtain(charSequence, 0, charSequence.length(), textPaint, i3).setAlignment(alignment).setLineSpacing(f2, f).setIncludePad(z).setEllipsize(null).setEllipsizedWidth(i4).setBreakStrategy(1).setHyphenationFrequency(1).build() : new StaticLayout(charSequence, textPaint, i3, alignment, f, f2, z);
        if (build.getLineCount() <= i5) {
            return build;
        }
        float lineLeft = build.getLineLeft(i5 - 1);
        ellipsize = new SpannableStringBuilder(charSequence.subSequence(0, Math.max(0, (lineLeft != BitmapDescriptorFactory.HUE_RED ? build.getOffsetForHorizontal(i5 - 1, lineLeft) : build.getOffsetForHorizontal(i5 - 1, build.getLineWidth(i5 - 1))) - 1)));
        ellipsize.append("…");
        return new StaticLayout(ellipsize, textPaint, i3, alignment, f, f2, z);
    }

    public static StaticLayout createStaticLayout(CharSequence charSequence, TextPaint textPaint, int i, Alignment alignment, float f, float f2, boolean z, TruncateAt truncateAt, int i2, int i3) {
        return createStaticLayout(charSequence, 0, charSequence.length(), textPaint, i, alignment, f, f2, z, truncateAt, i2, i3);
    }

    public static void init() {
        if (!initialized) {
            try {
                Class cls;
                if (VERSION.SDK_INT >= 18) {
                    cls = TextDirectionHeuristic.class;
                    sTextDirection = TextDirectionHeuristics.FIRSTSTRONG_LTR;
                } else {
                    ClassLoader classLoader = StaticLayoutEx.class.getClassLoader();
                    cls = classLoader.loadClass(TEXT_DIR_CLASS);
                    Class loadClass = classLoader.loadClass(TEXT_DIRS_CLASS);
                    sTextDirection = loadClass.getField(TEXT_DIR_FIRSTSTRONG_LTR).get(loadClass);
                }
                Class[] clsArr = new Class[]{CharSequence.class, Integer.TYPE, Integer.TYPE, TextPaint.class, Integer.TYPE, Alignment.class, cls, Float.TYPE, Float.TYPE, Boolean.TYPE, TruncateAt.class, Integer.TYPE, Integer.TYPE};
                sConstructor = StaticLayout.class.getDeclaredConstructor(clsArr);
                sConstructor.setAccessible(true);
                sConstructorArgs = new Object[clsArr.length];
                initialized = true;
            } catch (Throwable th) {
                FileLog.e(th);
            }
        }
    }
}
