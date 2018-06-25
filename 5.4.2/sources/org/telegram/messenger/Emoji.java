package org.telegram.messenger;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.Spannable;
import android.text.Spannable.Factory;
import android.text.TextPaint;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;

public class Emoji {
    private static int bigImgSize = AndroidUtilities.dp(AndroidUtilities.isTablet() ? 40.0f : 32.0f);
    private static final int[][] cols = new int[][]{new int[]{16, 16, 16, 16}, new int[]{6, 6, 6, 6}, new int[]{9, 9, 9, 9}, new int[]{9, 9, 9, 9}, new int[]{10, 10, 10, 10}};
    private static int drawImgSize = AndroidUtilities.dp(20.0f);
    private static Bitmap[][] emojiBmp = ((Bitmap[][]) Array.newInstance(Bitmap.class, new int[]{5, 4}));
    public static HashMap<String, String> emojiColor = new HashMap();
    public static HashMap<String, Integer> emojiUseHistory = new HashMap();
    private static boolean inited = false;
    private static boolean[][] loadingEmoji = ((boolean[][]) Array.newInstance(Boolean.TYPE, new int[]{5, 4}));
    private static Paint placeholderPaint = new Paint();
    public static ArrayList<String> recentEmoji = new ArrayList();
    private static boolean recentEmojiLoaded = false;
    private static HashMap<CharSequence, DrawableInfo> rects = new HashMap();
    private static final int splitCount = 4;

    /* renamed from: org.telegram.messenger.Emoji$2 */
    static class C30222 implements Comparator<String> {
        C30222() {
        }

        public int compare(String str, String str2) {
            Integer num = (Integer) Emoji.emojiUseHistory.get(str);
            Integer num2 = (Integer) Emoji.emojiUseHistory.get(str2);
            if (num == null) {
                num = Integer.valueOf(0);
            }
            if (num2 == null) {
                num2 = Integer.valueOf(0);
            }
            return num.intValue() > num2.intValue() ? -1 : num.intValue() < num2.intValue() ? 1 : 0;
        }
    }

    private static class DrawableInfo {
        public int emojiIndex;
        public byte page;
        public byte page2;
        public Rect rect;

        public DrawableInfo(Rect rect, byte b, byte b2, int i) {
            this.rect = rect;
            this.page = b;
            this.page2 = b2;
            this.emojiIndex = i;
        }
    }

    public static class EmojiDrawable extends Drawable {
        private static Paint paint = new Paint(2);
        private static Rect rect = new Rect();
        private static TextPaint textPaint = new TextPaint(1);
        private boolean fullSize = false;
        private DrawableInfo info;

        /* renamed from: org.telegram.messenger.Emoji$EmojiDrawable$1 */
        class C30231 implements Runnable {
            C30231() {
            }

            public void run() {
                Emoji.loadEmoji(EmojiDrawable.this.info.page, EmojiDrawable.this.info.page2);
                Emoji.loadingEmoji[EmojiDrawable.this.info.page][EmojiDrawable.this.info.page2] = false;
            }
        }

        public EmojiDrawable(DrawableInfo drawableInfo) {
            this.info = drawableInfo;
        }

        public void draw(Canvas canvas) {
            if (Emoji.emojiBmp[this.info.page][this.info.page2] != null) {
                canvas.drawBitmap(Emoji.emojiBmp[this.info.page][this.info.page2], this.info.rect, this.fullSize ? getDrawRect() : getBounds(), paint);
            } else if (!Emoji.loadingEmoji[this.info.page][this.info.page2]) {
                Emoji.loadingEmoji[this.info.page][this.info.page2] = true;
                Utilities.globalQueue.postRunnable(new C30231());
                canvas.drawRect(getBounds(), Emoji.placeholderPaint);
            }
        }

        public Rect getDrawRect() {
            Rect bounds = getBounds();
            int centerX = bounds.centerX();
            int centerY = bounds.centerY();
            rect.left = centerX - ((this.fullSize ? Emoji.bigImgSize : Emoji.drawImgSize) / 2);
            rect.right = ((this.fullSize ? Emoji.bigImgSize : Emoji.drawImgSize) / 2) + centerX;
            rect.top = centerY - ((this.fullSize ? Emoji.bigImgSize : Emoji.drawImgSize) / 2);
            rect.bottom = ((this.fullSize ? Emoji.bigImgSize : Emoji.drawImgSize) / 2) + centerY;
            return rect;
        }

        public DrawableInfo getDrawableInfo() {
            return this.info;
        }

        public int getOpacity() {
            return -2;
        }

        public void setAlpha(int i) {
        }

        public void setColorFilter(ColorFilter colorFilter) {
        }
    }

    public static class EmojiSpan extends ImageSpan {
        private FontMetricsInt fontMetrics = null;
        private int size = AndroidUtilities.dp(20.0f);

        public EmojiSpan(EmojiDrawable emojiDrawable, int i, int i2, FontMetricsInt fontMetricsInt) {
            super(emojiDrawable, i);
            this.fontMetrics = fontMetricsInt;
            if (fontMetricsInt != null) {
                this.size = Math.abs(this.fontMetrics.descent) + Math.abs(this.fontMetrics.ascent);
                if (this.size == 0) {
                    this.size = AndroidUtilities.dp(20.0f);
                }
            }
        }

        public int getSize(Paint paint, CharSequence charSequence, int i, int i2, FontMetricsInt fontMetricsInt) {
            FontMetricsInt fontMetricsInt2 = fontMetricsInt == null ? new FontMetricsInt() : fontMetricsInt;
            if (this.fontMetrics == null) {
                int size = super.getSize(paint, charSequence, i, i2, fontMetricsInt2);
                int dp = AndroidUtilities.dp(8.0f);
                int dp2 = AndroidUtilities.dp(10.0f);
                fontMetricsInt2.top = (-dp2) - dp;
                fontMetricsInt2.bottom = dp2 - dp;
                fontMetricsInt2.ascent = (-dp2) - dp;
                fontMetricsInt2.leading = 0;
                fontMetricsInt2.descent = dp2 - dp;
                return size;
            }
            if (fontMetricsInt2 != null) {
                fontMetricsInt2.ascent = this.fontMetrics.ascent;
                fontMetricsInt2.descent = this.fontMetrics.descent;
                fontMetricsInt2.top = this.fontMetrics.top;
                fontMetricsInt2.bottom = this.fontMetrics.bottom;
            }
            if (getDrawable() != null) {
                getDrawable().setBounds(0, 0, this.size, this.size);
            }
            return this.size;
        }

        public void replaceFontMetrics(FontMetricsInt fontMetricsInt, int i) {
            this.fontMetrics = fontMetricsInt;
            this.size = i;
        }
    }

    static {
        int i;
        int i2 = 2;
        if (AndroidUtilities.density <= 1.0f) {
            i = 32;
            i2 = 1;
        } else {
            i = AndroidUtilities.density <= 1.5f ? 64 : AndroidUtilities.density <= 2.0f ? 64 : 64;
        }
        for (int i3 = 0; i3 < EmojiData.data.length; i3++) {
            int ceil = (int) Math.ceil((double) (((float) EmojiData.data[i3].length) / 4.0f));
            for (int i4 = 0; i4 < EmojiData.data[i3].length; i4++) {
                int i5 = i4 / ceil;
                int i6 = i4 - (i5 * ceil);
                int i7 = i6 % cols[i3][i5];
                i6 /= cols[i3][i5];
                rects.put(EmojiData.data[i3][i4], new DrawableInfo(new Rect((i7 * i) + (i7 * i2), (i6 * i) + (i6 * i2), (i7 * i2) + ((i7 + 1) * i), (i6 * i2) + ((i6 + 1) * i)), (byte) i3, (byte) i5, i4));
            }
        }
        placeholderPaint.setColor(0);
    }

    public static void addRecentEmoji(String str) {
        Integer num = (Integer) emojiUseHistory.get(str);
        Integer valueOf = num == null ? Integer.valueOf(0) : num;
        if (valueOf.intValue() == 0 && emojiUseHistory.size() > 50) {
            for (int size = recentEmoji.size() - 1; size >= 0; size--) {
                emojiUseHistory.remove((String) recentEmoji.get(size));
                recentEmoji.remove(size);
                if (emojiUseHistory.size() <= 50) {
                    break;
                }
            }
        }
        emojiUseHistory.put(str, Integer.valueOf(valueOf.intValue() + 1));
    }

    public static void clearRecentEmoji() {
        ApplicationLoader.applicationContext.getSharedPreferences("emoji", 0).edit().putBoolean("filled_default", true).commit();
        emojiUseHistory.clear();
        recentEmoji.clear();
        saveRecentEmoji();
    }

    public static String fixEmoji(String str) {
        int length = str.length();
        int i = 0;
        String str2 = str;
        while (i < length) {
            char charAt = str2.charAt(i);
            if (charAt < '?' || charAt > '?') {
                if (charAt == '⃣') {
                    break;
                } else if (charAt >= '‼' && charAt <= '㊙' && EmojiData.emojiToFE0FMap.containsKey(Character.valueOf(charAt))) {
                    str2 = str2.substring(0, i + 1) + "️" + str2.substring(i + 1);
                    length++;
                    i++;
                }
            } else if (charAt != '?' || i >= length - 1) {
                i++;
            } else {
                charAt = str2.charAt(i + 1);
                if (charAt == '?' || charAt == '?' || charAt == '?' || charAt == '?') {
                    str2 = str2.substring(0, i + 2) + "️" + str2.substring(i + 2);
                    length++;
                    i += 2;
                } else {
                    i++;
                }
            }
            i++;
        }
        return str2;
    }

    public static Drawable getEmojiBigDrawable(String str) {
        Drawable emojiDrawable;
        Drawable emojiDrawable2 = getEmojiDrawable(str);
        if (emojiDrawable2 == null) {
            CharSequence charSequence = (CharSequence) EmojiData.emojiAliasMap.get(str);
            if (charSequence != null) {
                emojiDrawable = getEmojiDrawable(charSequence);
                if (emojiDrawable == null) {
                    return null;
                }
                emojiDrawable.setBounds(0, 0, bigImgSize, bigImgSize);
                emojiDrawable.fullSize = true;
                return emojiDrawable;
            }
        }
        emojiDrawable = emojiDrawable2;
        if (emojiDrawable == null) {
            return null;
        }
        emojiDrawable.setBounds(0, 0, bigImgSize, bigImgSize);
        emojiDrawable.fullSize = true;
        return emojiDrawable;
    }

    public static EmojiDrawable getEmojiDrawable(CharSequence charSequence) {
        DrawableInfo drawableInfo;
        DrawableInfo drawableInfo2 = (DrawableInfo) rects.get(charSequence);
        if (drawableInfo2 == null) {
            CharSequence charSequence2 = (CharSequence) EmojiData.emojiAliasMap.get(charSequence);
            if (charSequence2 != null) {
                drawableInfo = (DrawableInfo) rects.get(charSequence2);
                if (drawableInfo != null) {
                    FileLog.m13726e("No drawable for emoji " + charSequence);
                    return null;
                }
                EmojiDrawable emojiDrawable = new EmojiDrawable(drawableInfo);
                emojiDrawable.setBounds(0, 0, drawImgSize, drawImgSize);
                return emojiDrawable;
            }
        }
        drawableInfo = drawableInfo2;
        if (drawableInfo != null) {
            EmojiDrawable emojiDrawable2 = new EmojiDrawable(drawableInfo);
            emojiDrawable2.setBounds(0, 0, drawImgSize, drawImgSize);
            return emojiDrawable2;
        }
        FileLog.m13726e("No drawable for emoji " + charSequence);
        return null;
    }

    public static native Object[] getSuggestion(String str);

    private static boolean inArray(char c, char[] cArr) {
        for (char c2 : cArr) {
            if (c2 == c) {
                return true;
            }
        }
        return false;
    }

    public static void invalidateAll(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                invalidateAll(viewGroup.getChildAt(i));
            }
        } else if (view instanceof TextView) {
            view.invalidate();
        }
    }

    private static void loadEmoji(final int i, final int i2) {
        float f;
        Throwable th;
        Bitmap decodeStream;
        int i3 = 2;
        try {
            int i4;
            File fileStreamPath;
            if (AndroidUtilities.density <= 1.0f) {
                f = 2.0f;
            } else if (AndroidUtilities.density <= 1.5f) {
                i3 = 1;
                f = 2.0f;
            } else if (AndroidUtilities.density <= 2.0f) {
                i3 = 1;
                f = 2.0f;
            } else {
                i3 = 1;
                f = 2.0f;
            }
            for (i4 = 4; i4 < 7; i4++) {
                fileStreamPath = ApplicationLoader.applicationContext.getFileStreamPath(String.format(Locale.US, "v%d_emoji%.01fx_%d.jpg", new Object[]{Integer.valueOf(i4), Float.valueOf(f), Integer.valueOf(i)}));
                if (fileStreamPath.exists()) {
                    fileStreamPath.delete();
                }
                fileStreamPath = ApplicationLoader.applicationContext.getFileStreamPath(String.format(Locale.US, "v%d_emoji%.01fx_a_%d.jpg", new Object[]{Integer.valueOf(i4), Float.valueOf(f), Integer.valueOf(i)}));
                if (fileStreamPath.exists()) {
                    fileStreamPath.delete();
                }
            }
            for (i4 = 8; i4 < 12; i4++) {
                fileStreamPath = ApplicationLoader.applicationContext.getFileStreamPath(String.format(Locale.US, "v%d_emoji%.01fx_%d.png", new Object[]{Integer.valueOf(i4), Float.valueOf(f), Integer.valueOf(i)}));
                if (fileStreamPath.exists()) {
                    fileStreamPath.delete();
                }
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        } catch (Throwable th2) {
            FileLog.m13727e("Error loading emoji", th2);
            return;
        }
        try {
            InputStream open = ApplicationLoader.applicationContext.getAssets().open("emoji/" + String.format(Locale.US, "v12_emoji%.01fx_%d_%d.png", new Object[]{Float.valueOf(f), Integer.valueOf(i), Integer.valueOf(i2)}));
            Options options = new Options();
            options.inJustDecodeBounds = false;
            options.inSampleSize = i3;
            decodeStream = BitmapFactory.decodeStream(open, null, options);
            try {
                open.close();
            } catch (Throwable th3) {
                th2 = th3;
                FileLog.m13728e(th2);
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        Emoji.emojiBmp[i][i2] = decodeStream;
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.emojiDidLoaded, new Object[0]);
                    }
                });
            }
        } catch (Throwable th4) {
            th2 = th4;
            decodeStream = null;
            FileLog.m13728e(th2);
            AndroidUtilities.runOnUIThread(/* anonymous class already generated */);
        }
        AndroidUtilities.runOnUIThread(/* anonymous class already generated */);
    }

    public static void loadRecentEmoji() {
        if (!recentEmojiLoaded) {
            String string;
            int i;
            String[] strArr;
            recentEmojiLoaded = true;
            SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("emoji", 0);
            try {
                emojiUseHistory.clear();
                if (sharedPreferences.contains("emojis")) {
                    string = sharedPreferences.getString("emojis", TtmlNode.ANONYMOUS_REGION_ID);
                    if (string != null && string.length() > 0) {
                        for (String string2 : string2.split(",")) {
                            String[] split = string2.split("=");
                            long longValue = Utilities.parseLong(split[0]).longValue();
                            string2 = TtmlNode.ANONYMOUS_REGION_ID;
                            long j = longValue;
                            for (int i2 = 0; i2 < 4; i2++) {
                                string2 = String.valueOf((char) ((int) j)) + string2;
                                j >>= 16;
                                if (j == 0) {
                                    break;
                                }
                            }
                            if (string2.length() > 0) {
                                emojiUseHistory.put(string2, Utilities.parseInt(split[1]));
                            }
                        }
                    }
                    sharedPreferences.edit().remove("emojis").commit();
                    saveRecentEmoji();
                } else {
                    string2 = sharedPreferences.getString("emojis2", TtmlNode.ANONYMOUS_REGION_ID);
                    if (string2 != null && string2.length() > 0) {
                        for (String split2 : string2.split(",")) {
                            String[] split3 = split2.split("=");
                            emojiUseHistory.put(split3[0], Utilities.parseInt(split3[1]));
                        }
                    }
                }
                if (emojiUseHistory.isEmpty() && !sharedPreferences.getBoolean("filled_default", false)) {
                    strArr = new String[]{"😂", "😘", "❤", "😍", "😊", "😁", "👍", "☺", "😔", "😄", "😭", "💋", "😒", "😳", "😜", "🙈", "😉", "😃", "😢", "😝", "😱", "😡", "😏", "😞", "😅", "😚", "🙊", "😌", "😀", "😋", "😆", "👌", "😐", "😕"};
                    for (i = 0; i < strArr.length; i++) {
                        emojiUseHistory.put(strArr[i], Integer.valueOf(strArr.length - i));
                    }
                    sharedPreferences.edit().putBoolean("filled_default", true).commit();
                    saveRecentEmoji();
                }
                sortEmoji();
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
            try {
                string2 = sharedPreferences.getString(TtmlNode.ATTR_TTS_COLOR, TtmlNode.ANONYMOUS_REGION_ID);
                if (string2 != null && string2.length() > 0) {
                    strArr = string2.split(",");
                    for (String split4 : strArr) {
                        String[] split5 = split4.split("=");
                        emojiColor.put(split5[0], split5[1]);
                    }
                }
            } catch (Throwable e2) {
                FileLog.m13728e(e2);
            }
        }
    }

    public static CharSequence replaceEmoji(CharSequence charSequence, FontMetricsInt fontMetricsInt, int i, boolean z) {
        return replaceEmoji(charSequence, fontMetricsInt, i, z, null);
    }

    public static CharSequence replaceEmoji(CharSequence charSequence, FontMetricsInt fontMetricsInt, int i, boolean z, int[] iArr) {
        if (MessagesController.getInstance().useSystemEmoji || charSequence == null || charSequence.length() == 0) {
            return charSequence;
        }
        CharSequence newSpannable = (z || !(charSequence instanceof Spannable)) ? Factory.getInstance().newSpannable(charSequence.toString()) : (Spannable) charSequence;
        if (VERSION.SDK_INT >= 19 && ApplicationLoader.SHOW_ANDROID_EMOJI) {
            return newSpannable;
        }
        long j = 0;
        int i2 = 0;
        int i3 = -1;
        int i4 = 0;
        int i5 = 0;
        StringBuilder stringBuilder = new StringBuilder(16);
        StringBuilder stringBuilder2 = new StringBuilder(2);
        int length = charSequence.length();
        Object obj = null;
        int i6 = 0;
        int[] iArr2 = iArr;
        while (i6 < length) {
            try {
                long j2;
                int[] iArr3;
                int i7;
                Object obj2;
                int i8;
                Object obj3;
                char charAt;
                char charAt2;
                int i9;
                EmojiDrawable emojiDrawable;
                Object obj4;
                char charAt3 = charSequence.charAt(i6);
                if ((charAt3 >= '?' && charAt3 <= '?') || (j != 0 && (-4294967296L & j) == 0 && (65535 & j) == 55356 && charAt3 >= '?' && charAt3 <= '?')) {
                    if (i3 == -1) {
                        i3 = i6;
                    }
                    stringBuilder.append(charAt3);
                    j2 = (j << 16) | ((long) charAt3);
                    iArr3 = iArr2;
                    i7 = i3;
                    obj2 = obj;
                    i8 = i4 + 1;
                } else if (stringBuilder.length() > 0 && (charAt3 == '♀' || charAt3 == '♂' || charAt3 == '⚕')) {
                    stringBuilder.append(charAt3);
                    j2 = 0;
                    iArr3 = iArr2;
                    i7 = i3;
                    i3 = 1;
                    i8 = i4 + 1;
                } else if (j <= 0 || (61440 & charAt3) != 53248) {
                    if (charAt3 == '⃣') {
                        if (i6 > 0) {
                            char charAt4 = charSequence.charAt(i5);
                            if ((charAt4 >= '0' && charAt4 <= '9') || charAt4 == '#' || charAt4 == '*') {
                                i4 = (i6 - i5) + 1;
                                stringBuilder.append(charAt4);
                                stringBuilder.append(charAt3);
                                obj = 1;
                            } else {
                                i5 = i3;
                            }
                            obj2 = obj;
                            j2 = j;
                            i8 = i4;
                            iArr3 = iArr2;
                            i7 = i5;
                        }
                    } else if ((charAt3 == '©' || charAt3 == '®' || (charAt3 >= '‼' && charAt3 <= '㊙')) && EmojiData.dataCharsMap.containsKey(Character.valueOf(charAt3))) {
                        if (i3 == -1) {
                            i3 = i6;
                        }
                        i4++;
                        stringBuilder.append(charAt3);
                        j2 = j;
                        iArr3 = iArr2;
                        i7 = i3;
                        i3 = 1;
                        i8 = i4;
                    } else if (i3 != -1) {
                        stringBuilder.setLength(0);
                        j2 = j;
                        iArr3 = iArr2;
                        i7 = -1;
                        obj2 = null;
                        i8 = 0;
                    } else if (!(charAt3 == '️' || iArr2 == null)) {
                        iArr2[0] = 0;
                        i7 = i3;
                        j2 = j;
                        obj2 = obj;
                        iArr3 = null;
                        i8 = i4;
                    }
                    j2 = j;
                    iArr3 = iArr2;
                    i7 = i3;
                    obj2 = obj;
                    i8 = i4;
                } else {
                    stringBuilder.append(charAt3);
                    j2 = 0;
                    iArr3 = iArr2;
                    i7 = i3;
                    i3 = 1;
                    i8 = i4 + 1;
                }
                if (obj2 != null && i6 + 2 < length) {
                    char charAt5 = charSequence.charAt(i6 + 1);
                    if (charAt5 == '?') {
                        charAt5 = charSequence.charAt(i6 + 2);
                        if (charAt5 >= '?' && charAt5 <= '?') {
                            stringBuilder.append(charSequence.subSequence(i6 + 1, i6 + 3));
                            i4 = i8 + 2;
                            i8 = i6 + 2;
                            obj3 = obj2;
                            i3 = i4;
                            i4 = i8;
                            for (i6 = 0; i6 < 3; i6++) {
                                if (i4 + 1 >= length) {
                                    charAt = charSequence.charAt(i4 + 1);
                                    if (i6 == 1) {
                                        if (charAt == '‍' && stringBuilder.length() > 0) {
                                            stringBuilder.append(charAt);
                                            i4++;
                                            i3++;
                                            obj3 = null;
                                        }
                                    } else if (charAt >= '︀' && charAt <= '️') {
                                        i4++;
                                        i3++;
                                    }
                                }
                            }
                            if (obj3 != null && i4 + 2 < length) {
                                if (charSequence.charAt(i4 + 1) == '?') {
                                    charAt2 = charSequence.charAt(i4 + 2);
                                    if (charAt2 >= '?' && charAt2 <= '?') {
                                        stringBuilder.append(charSequence.subSequence(i4 + 1, i4 + 3));
                                        i9 = i4 + 2;
                                        i4 = i3 + 2;
                                        if (obj3 == null) {
                                            if (iArr3 != null) {
                                                iArr3[0] = iArr3[0] + 1;
                                            }
                                            emojiDrawable = getEmojiDrawable(stringBuilder.subSequence(0, stringBuilder.length()));
                                            if (emojiDrawable == null) {
                                                newSpannable.setSpan(new EmojiSpan(emojiDrawable, 0, i, fontMetricsInt), i7, i4 + i7, 33);
                                                i6 = i2 + 1;
                                            } else {
                                                i6 = i2;
                                            }
                                            i4 = 0;
                                            i3 = -1;
                                            stringBuilder.setLength(0);
                                            obj3 = null;
                                        } else {
                                            i3 = i7;
                                            i6 = i2;
                                        }
                                        if (VERSION.SDK_INT >= 23 && i6 >= 50) {
                                            return newSpannable;
                                        }
                                        i2 = i6;
                                        i6 = i9 + 1;
                                        iArr2 = iArr3;
                                        j = j2;
                                        obj4 = obj3;
                                        i5 = i8;
                                        obj = obj4;
                                    }
                                }
                            }
                            i9 = i4;
                            i4 = i3;
                            if (obj3 == null) {
                                i3 = i7;
                                i6 = i2;
                            } else {
                                if (iArr3 != null) {
                                    iArr3[0] = iArr3[0] + 1;
                                }
                                emojiDrawable = getEmojiDrawable(stringBuilder.subSequence(0, stringBuilder.length()));
                                if (emojiDrawable == null) {
                                    i6 = i2;
                                } else {
                                    newSpannable.setSpan(new EmojiSpan(emojiDrawable, 0, i, fontMetricsInt), i7, i4 + i7, 33);
                                    i6 = i2 + 1;
                                }
                                i4 = 0;
                                i3 = -1;
                                stringBuilder.setLength(0);
                                obj3 = null;
                            }
                            if (VERSION.SDK_INT >= 23) {
                            }
                            i2 = i6;
                            i6 = i9 + 1;
                            iArr2 = iArr3;
                            j = j2;
                            obj4 = obj3;
                            i5 = i8;
                            obj = obj4;
                        }
                    } else if (stringBuilder.length() >= 2 && stringBuilder.charAt(0) == '?' && stringBuilder.charAt(1) == '?' && charAt5 == '?') {
                        i4 = i8;
                        i8 = i6 + 1;
                        do {
                            stringBuilder.append(charSequence.subSequence(i8, i8 + 2));
                            i4 += 2;
                            i8 += 2;
                            if (i8 >= charSequence.length()) {
                                break;
                            }
                        } while (charSequence.charAt(i8) == '?');
                        i8--;
                        obj3 = obj2;
                        i3 = i4;
                        i4 = i8;
                        for (i6 = 0; i6 < 3; i6++) {
                            if (i4 + 1 >= length) {
                                charAt = charSequence.charAt(i4 + 1);
                                if (i6 == 1) {
                                    i4++;
                                    i3++;
                                } else {
                                    stringBuilder.append(charAt);
                                    i4++;
                                    i3++;
                                    obj3 = null;
                                }
                            }
                        }
                        if (charSequence.charAt(i4 + 1) == '?') {
                            charAt2 = charSequence.charAt(i4 + 2);
                            stringBuilder.append(charSequence.subSequence(i4 + 1, i4 + 3));
                            i9 = i4 + 2;
                            i4 = i3 + 2;
                            if (obj3 == null) {
                                if (iArr3 != null) {
                                    iArr3[0] = iArr3[0] + 1;
                                }
                                emojiDrawable = getEmojiDrawable(stringBuilder.subSequence(0, stringBuilder.length()));
                                if (emojiDrawable == null) {
                                    newSpannable.setSpan(new EmojiSpan(emojiDrawable, 0, i, fontMetricsInt), i7, i4 + i7, 33);
                                    i6 = i2 + 1;
                                } else {
                                    i6 = i2;
                                }
                                i4 = 0;
                                i3 = -1;
                                stringBuilder.setLength(0);
                                obj3 = null;
                            } else {
                                i3 = i7;
                                i6 = i2;
                            }
                            if (VERSION.SDK_INT >= 23) {
                            }
                            i2 = i6;
                            i6 = i9 + 1;
                            iArr2 = iArr3;
                            j = j2;
                            obj4 = obj3;
                            i5 = i8;
                            obj = obj4;
                        }
                        i9 = i4;
                        i4 = i3;
                        if (obj3 == null) {
                            i3 = i7;
                            i6 = i2;
                        } else {
                            if (iArr3 != null) {
                                iArr3[0] = iArr3[0] + 1;
                            }
                            emojiDrawable = getEmojiDrawable(stringBuilder.subSequence(0, stringBuilder.length()));
                            if (emojiDrawable == null) {
                                i6 = i2;
                            } else {
                                newSpannable.setSpan(new EmojiSpan(emojiDrawable, 0, i, fontMetricsInt), i7, i4 + i7, 33);
                                i6 = i2 + 1;
                            }
                            i4 = 0;
                            i3 = -1;
                            stringBuilder.setLength(0);
                            obj3 = null;
                        }
                        if (VERSION.SDK_INT >= 23) {
                        }
                        i2 = i6;
                        i6 = i9 + 1;
                        iArr2 = iArr3;
                        j = j2;
                        obj4 = obj3;
                        i5 = i8;
                        obj = obj4;
                    }
                }
                i4 = i8;
                i8 = i6;
                obj3 = obj2;
                i3 = i4;
                i4 = i8;
                for (i6 = 0; i6 < 3; i6++) {
                    if (i4 + 1 >= length) {
                        charAt = charSequence.charAt(i4 + 1);
                        if (i6 == 1) {
                            stringBuilder.append(charAt);
                            i4++;
                            i3++;
                            obj3 = null;
                        } else {
                            i4++;
                            i3++;
                        }
                    }
                }
                if (charSequence.charAt(i4 + 1) == '?') {
                    charAt2 = charSequence.charAt(i4 + 2);
                    stringBuilder.append(charSequence.subSequence(i4 + 1, i4 + 3));
                    i9 = i4 + 2;
                    i4 = i3 + 2;
                    if (obj3 == null) {
                        if (iArr3 != null) {
                            iArr3[0] = iArr3[0] + 1;
                        }
                        emojiDrawable = getEmojiDrawable(stringBuilder.subSequence(0, stringBuilder.length()));
                        if (emojiDrawable == null) {
                            newSpannable.setSpan(new EmojiSpan(emojiDrawable, 0, i, fontMetricsInt), i7, i4 + i7, 33);
                            i6 = i2 + 1;
                        } else {
                            i6 = i2;
                        }
                        i4 = 0;
                        i3 = -1;
                        stringBuilder.setLength(0);
                        obj3 = null;
                    } else {
                        i3 = i7;
                        i6 = i2;
                    }
                    if (VERSION.SDK_INT >= 23) {
                    }
                    i2 = i6;
                    i6 = i9 + 1;
                    iArr2 = iArr3;
                    j = j2;
                    obj4 = obj3;
                    i5 = i8;
                    obj = obj4;
                }
                i9 = i4;
                i4 = i3;
                if (obj3 == null) {
                    i3 = i7;
                    i6 = i2;
                } else {
                    if (iArr3 != null) {
                        iArr3[0] = iArr3[0] + 1;
                    }
                    emojiDrawable = getEmojiDrawable(stringBuilder.subSequence(0, stringBuilder.length()));
                    if (emojiDrawable == null) {
                        i6 = i2;
                    } else {
                        newSpannable.setSpan(new EmojiSpan(emojiDrawable, 0, i, fontMetricsInt), i7, i4 + i7, 33);
                        i6 = i2 + 1;
                    }
                    i4 = 0;
                    i3 = -1;
                    stringBuilder.setLength(0);
                    obj3 = null;
                }
                if (VERSION.SDK_INT >= 23) {
                }
                i2 = i6;
                i6 = i9 + 1;
                iArr2 = iArr3;
                j = j2;
                obj4 = obj3;
                i5 = i8;
                obj = obj4;
            } catch (Throwable e) {
                FileLog.m13728e(e);
                return charSequence;
            }
        }
        return newSpannable;
    }

    public static void saveEmojiColors() {
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("emoji", 0);
        StringBuilder stringBuilder = new StringBuilder();
        for (Entry entry : emojiColor.entrySet()) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append((String) entry.getKey());
            stringBuilder.append("=");
            stringBuilder.append((String) entry.getValue());
        }
        sharedPreferences.edit().putString(TtmlNode.ATTR_TTS_COLOR, stringBuilder.toString()).commit();
    }

    public static void saveRecentEmoji() {
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("emoji", 0);
        StringBuilder stringBuilder = new StringBuilder();
        for (Entry entry : emojiUseHistory.entrySet()) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append((String) entry.getKey());
            stringBuilder.append("=");
            stringBuilder.append(entry.getValue());
        }
        sharedPreferences.edit().putString("emojis2", stringBuilder.toString()).commit();
    }

    public static void sortEmoji() {
        recentEmoji.clear();
        for (Entry key : emojiUseHistory.entrySet()) {
            recentEmoji.add(key.getKey());
        }
        Collections.sort(recentEmoji, new C30222());
        while (recentEmoji.size() > 50) {
            recentEmoji.remove(recentEmoji.size() - 1);
        }
    }
}
