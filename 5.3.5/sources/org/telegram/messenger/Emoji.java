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
    static class C13162 implements Comparator<String> {
        C13162() {
        }

        public int compare(String lhs, String rhs) {
            Integer count1 = (Integer) Emoji.emojiUseHistory.get(lhs);
            Integer count2 = (Integer) Emoji.emojiUseHistory.get(rhs);
            if (count1 == null) {
                count1 = Integer.valueOf(0);
            }
            if (count2 == null) {
                count2 = Integer.valueOf(0);
            }
            if (count1.intValue() > count2.intValue()) {
                return -1;
            }
            if (count1.intValue() < count2.intValue()) {
                return 1;
            }
            return 0;
        }
    }

    private static class DrawableInfo {
        public int emojiIndex;
        public byte page;
        public byte page2;
        public Rect rect;

        public DrawableInfo(Rect r, byte p, byte p2, int index) {
            this.rect = r;
            this.page = p;
            this.page2 = p2;
            this.emojiIndex = index;
        }
    }

    public static class EmojiDrawable extends Drawable {
        private static Paint paint = new Paint(2);
        private static Rect rect = new Rect();
        private static TextPaint textPaint = new TextPaint(1);
        private boolean fullSize = false;
        private DrawableInfo info;

        /* renamed from: org.telegram.messenger.Emoji$EmojiDrawable$1 */
        class C13171 implements Runnable {
            C13171() {
            }

            public void run() {
                Emoji.loadEmoji(EmojiDrawable.this.info.page, EmojiDrawable.this.info.page2);
                Emoji.loadingEmoji[EmojiDrawable.this.info.page][EmojiDrawable.this.info.page2] = false;
            }
        }

        public EmojiDrawable(DrawableInfo i) {
            this.info = i;
        }

        public DrawableInfo getDrawableInfo() {
            return this.info;
        }

        public Rect getDrawRect() {
            Rect original = getBounds();
            int cX = original.centerX();
            int cY = original.centerY();
            rect.left = cX - ((this.fullSize ? Emoji.bigImgSize : Emoji.drawImgSize) / 2);
            rect.right = ((this.fullSize ? Emoji.bigImgSize : Emoji.drawImgSize) / 2) + cX;
            rect.top = cY - ((this.fullSize ? Emoji.bigImgSize : Emoji.drawImgSize) / 2);
            rect.bottom = ((this.fullSize ? Emoji.bigImgSize : Emoji.drawImgSize) / 2) + cY;
            return rect;
        }

        public void draw(Canvas canvas) {
            if (Emoji.emojiBmp[this.info.page][this.info.page2] != null) {
                Rect b;
                if (this.fullSize) {
                    b = getDrawRect();
                } else {
                    b = getBounds();
                }
                canvas.drawBitmap(Emoji.emojiBmp[this.info.page][this.info.page2], this.info.rect, b, paint);
            } else if (!Emoji.loadingEmoji[this.info.page][this.info.page2]) {
                Emoji.loadingEmoji[this.info.page][this.info.page2] = true;
                Utilities.globalQueue.postRunnable(new C13171());
                canvas.drawRect(getBounds(), Emoji.placeholderPaint);
            }
        }

        public int getOpacity() {
            return -2;
        }

        public void setAlpha(int alpha) {
        }

        public void setColorFilter(ColorFilter cf) {
        }
    }

    public static class EmojiSpan extends ImageSpan {
        private FontMetricsInt fontMetrics = null;
        private int size = AndroidUtilities.dp(20.0f);

        public EmojiSpan(EmojiDrawable d, int verticalAlignment, int s, FontMetricsInt original) {
            super(d, verticalAlignment);
            this.fontMetrics = original;
            if (original != null) {
                this.size = Math.abs(this.fontMetrics.descent) + Math.abs(this.fontMetrics.ascent);
                if (this.size == 0) {
                    this.size = AndroidUtilities.dp(20.0f);
                }
            }
        }

        public void replaceFontMetrics(FontMetricsInt newMetrics, int newSize) {
            this.fontMetrics = newMetrics;
            this.size = newSize;
        }

        public int getSize(Paint paint, CharSequence text, int start, int end, FontMetricsInt fm) {
            if (fm == null) {
                fm = new FontMetricsInt();
            }
            if (this.fontMetrics == null) {
                int sz = super.getSize(paint, text, start, end, fm);
                int offset = AndroidUtilities.dp(8.0f);
                int w = AndroidUtilities.dp(10.0f);
                fm.top = (-w) - offset;
                fm.bottom = w - offset;
                fm.ascent = (-w) - offset;
                fm.leading = 0;
                fm.descent = w - offset;
                return sz;
            }
            if (fm != null) {
                fm.ascent = this.fontMetrics.ascent;
                fm.descent = this.fontMetrics.descent;
                fm.top = this.fontMetrics.top;
                fm.bottom = this.fontMetrics.bottom;
            }
            if (getDrawable() != null) {
                getDrawable().setBounds(0, 0, this.size, this.size);
            }
            return this.size;
        }
    }

    public static native Object[] getSuggestion(String str);

    static {
        int emojiFullSize;
        int add = 2;
        if (AndroidUtilities.density <= 1.0f) {
            emojiFullSize = 32;
            add = 1;
        } else if (AndroidUtilities.density <= 1.5f) {
            emojiFullSize = 64;
        } else if (AndroidUtilities.density <= 2.0f) {
            emojiFullSize = 64;
        } else {
            emojiFullSize = 64;
        }
        for (int j = 0; j < EmojiData.data.length; j++) {
            int count2 = (int) Math.ceil((double) (((float) EmojiData.data[j].length) / 4.0f));
            for (int i = 0; i < EmojiData.data[j].length; i++) {
                int page = i / count2;
                int position = i - (page * count2);
                int row = position % cols[j][page];
                int col = position / cols[j][page];
                rects.put(EmojiData.data[j][i], new DrawableInfo(new Rect((row * emojiFullSize) + (row * add), (col * emojiFullSize) + (col * add), ((row + 1) * emojiFullSize) + (row * add), ((col + 1) * emojiFullSize) + (col * add)), (byte) j, (byte) page, i));
            }
        }
        placeholderPaint.setColor(0);
    }

    private static void loadEmoji(int page, int page2) {
        float scale;
        int imageResize = 1;
        try {
            int a;
            File imageFile;
            if (AndroidUtilities.density <= 1.0f) {
                scale = 2.0f;
                imageResize = 2;
            } else if (AndroidUtilities.density <= 1.5f) {
                scale = 2.0f;
            } else if (AndroidUtilities.density <= 2.0f) {
                scale = 2.0f;
            } else {
                scale = 2.0f;
            }
            for (a = 4; a < 7; a++) {
                imageFile = ApplicationLoader.applicationContext.getFileStreamPath(String.format(Locale.US, "v%d_emoji%.01fx_%d.jpg", new Object[]{Integer.valueOf(a), Float.valueOf(scale), Integer.valueOf(page)}));
                if (imageFile.exists()) {
                    imageFile.delete();
                }
                imageFile = ApplicationLoader.applicationContext.getFileStreamPath(String.format(Locale.US, "v%d_emoji%.01fx_a_%d.jpg", new Object[]{Integer.valueOf(a), Float.valueOf(scale), Integer.valueOf(page)}));
                if (imageFile.exists()) {
                    imageFile.delete();
                }
            }
            for (a = 8; a < 12; a++) {
                imageFile = ApplicationLoader.applicationContext.getFileStreamPath(String.format(Locale.US, "v%d_emoji%.01fx_%d.png", new Object[]{Integer.valueOf(a), Float.valueOf(scale), Integer.valueOf(page)}));
                if (imageFile.exists()) {
                    imageFile.delete();
                }
            }
        } catch (Exception e) {
            FileLog.e(e);
        } catch (Throwable x) {
            FileLog.e("Error loading emoji", x);
            return;
        }
        Bitmap bitmap = null;
        try {
            InputStream is = ApplicationLoader.applicationContext.getAssets().open("emoji/" + String.format(Locale.US, "v12_emoji%.01fx_%d_%d.png", new Object[]{Float.valueOf(scale), Integer.valueOf(page), Integer.valueOf(page2)}));
            Options opts = new Options();
            opts.inJustDecodeBounds = false;
            opts.inSampleSize = imageResize;
            bitmap = BitmapFactory.decodeStream(is, null, opts);
            is.close();
        } catch (Throwable e2) {
            FileLog.e(e2);
        }
        final Bitmap finalBitmap = bitmap;
        final int i = page;
        final int i2 = page2;
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                Emoji.emojiBmp[i][i2] = finalBitmap;
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.emojiDidLoaded, new Object[0]);
            }
        });
    }

    public static void invalidateAll(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup g = (ViewGroup) view;
            for (int i = 0; i < g.getChildCount(); i++) {
                invalidateAll(g.getChildAt(i));
            }
        } else if (view instanceof TextView) {
            view.invalidate();
        }
    }

    public static String fixEmoji(String emoji) {
        int lenght = emoji.length();
        int a = 0;
        while (a < lenght) {
            char ch = emoji.charAt(a);
            if (ch < '?' || ch > '?') {
                if (ch == '⃣') {
                    break;
                } else if (ch >= '‼' && ch <= '㊙' && EmojiData.emojiToFE0FMap.containsKey(Character.valueOf(ch))) {
                    emoji = emoji.substring(0, a + 1) + "️" + emoji.substring(a + 1);
                    lenght++;
                    a++;
                }
            } else if (ch != '?' || a >= lenght - 1) {
                a++;
            } else {
                ch = emoji.charAt(a + 1);
                if (ch == '?' || ch == '?' || ch == '?' || ch == '?') {
                    emoji = emoji.substring(0, a + 2) + "️" + emoji.substring(a + 2);
                    lenght++;
                    a += 2;
                } else {
                    a++;
                }
            }
            a++;
        }
        return emoji;
    }

    public static EmojiDrawable getEmojiDrawable(CharSequence code) {
        DrawableInfo info = (DrawableInfo) rects.get(code);
        if (info == null) {
            CharSequence newCode = (CharSequence) EmojiData.emojiAliasMap.get(code);
            if (newCode != null) {
                info = (DrawableInfo) rects.get(newCode);
            }
        }
        if (info == null) {
            FileLog.e("No drawable for emoji " + code);
            return null;
        }
        EmojiDrawable ed = new EmojiDrawable(info);
        ed.setBounds(0, 0, drawImgSize, drawImgSize);
        return ed;
    }

    public static Drawable getEmojiBigDrawable(String code) {
        EmojiDrawable ed = getEmojiDrawable(code);
        if (ed == null) {
            CharSequence newCode = (CharSequence) EmojiData.emojiAliasMap.get(code);
            if (newCode != null) {
                ed = getEmojiDrawable(newCode);
            }
        }
        if (ed == null) {
            return null;
        }
        ed.setBounds(0, 0, bigImgSize, bigImgSize);
        ed.fullSize = true;
        return ed;
    }

    private static boolean inArray(char c, char[] a) {
        for (char cc : a) {
            if (cc == c) {
                return true;
            }
        }
        return false;
    }

    public static CharSequence replaceEmoji(CharSequence cs, FontMetricsInt fontMetrics, int size, boolean createNew) {
        return replaceEmoji(cs, fontMetrics, size, createNew, null);
    }

    public static CharSequence replaceEmoji(CharSequence cs, FontMetricsInt fontMetrics, int size, boolean createNew, int[] emojiOnly) {
        if (MessagesController.getInstance().useSystemEmoji || cs == null || cs.length() == 0) {
            return cs;
        }
        CharSequence s;
        if (createNew || !(cs instanceof Spannable)) {
            s = Factory.getInstance().newSpannable(cs.toString());
        } else {
            s = (Spannable) cs;
        }
        if (VERSION.SDK_INT >= 19 && ApplicationLoader.SHOW_ANDROID_EMOJI) {
            return s;
        }
        long buf = 0;
        int emojiCount = 0;
        int startIndex = -1;
        int startLength = 0;
        int previousGoodIndex = 0;
        StringBuilder stringBuilder = new StringBuilder(16);
        StringBuilder addionalCode = new StringBuilder(2);
        int length = cs.length();
        boolean doneEmoji = false;
        int i = 0;
        while (i < length) {
            char next;
            char c = cs.charAt(i);
            if ((c < '?' || c > '?') && (buf == 0 || (-4294967296L & buf) != 0 || (65535 & buf) != 55356 || c < '?' || c > '?')) {
                try {
                    if (stringBuilder.length() > 0 && (c == '♀' || c == '♂' || c == '⚕')) {
                        stringBuilder.append(c);
                        startLength++;
                        buf = 0;
                        doneEmoji = true;
                    } else if (buf > 0 && (61440 & c) == 53248) {
                        stringBuilder.append(c);
                        startLength++;
                        buf = 0;
                        doneEmoji = true;
                    } else if (c == '⃣') {
                        if (i > 0) {
                            char c2 = cs.charAt(previousGoodIndex);
                            if ((c2 >= '0' && c2 <= '9') || c2 == '#' || c2 == '*') {
                                startIndex = previousGoodIndex;
                                startLength = (i - previousGoodIndex) + 1;
                                stringBuilder.append(c2);
                                stringBuilder.append(c);
                                doneEmoji = true;
                            }
                        }
                    } else if ((c == '©' || c == '®' || (c >= '‼' && c <= '㊙')) && EmojiData.dataCharsMap.containsKey(Character.valueOf(c))) {
                        if (startIndex == -1) {
                            startIndex = i;
                        }
                        startLength++;
                        stringBuilder.append(c);
                        doneEmoji = true;
                    } else if (startIndex != -1) {
                        stringBuilder.setLength(0);
                        startIndex = -1;
                        startLength = 0;
                        doneEmoji = false;
                    } else if (!(c == '️' || emojiOnly == null)) {
                        emojiOnly[0] = 0;
                        emojiOnly = null;
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                    return cs;
                }
            }
            if (startIndex == -1) {
                startIndex = i;
            }
            stringBuilder.append(c);
            startLength++;
            buf = (buf << 16) | ((long) c);
            if (doneEmoji && i + 2 < length) {
                next = cs.charAt(i + 1);
                if (next == '?') {
                    next = cs.charAt(i + 2);
                    if (next >= '?' && next <= '?') {
                        stringBuilder.append(cs.subSequence(i + 1, i + 3));
                        startLength += 2;
                        i += 2;
                    }
                } else if (stringBuilder.length() >= 2 && stringBuilder.charAt(0) == '?' && stringBuilder.charAt(1) == '?' && next == '?') {
                    i++;
                    do {
                        stringBuilder.append(cs.subSequence(i, i + 2));
                        startLength += 2;
                        i += 2;
                        if (i >= cs.length()) {
                            break;
                        }
                    } while (cs.charAt(i) == '?');
                    i--;
                }
            }
            previousGoodIndex = i;
            for (int a = 0; a < 3; a++) {
                if (i + 1 < length) {
                    c = cs.charAt(i + 1);
                    if (a == 1) {
                        if (c == '‍' && stringBuilder.length() > 0) {
                            stringBuilder.append(c);
                            i++;
                            startLength++;
                            doneEmoji = false;
                        }
                    } else if (c >= '︀' && c <= '️') {
                        i++;
                        startLength++;
                    }
                }
            }
            if (doneEmoji && i + 2 < length && cs.charAt(i + 1) == '?') {
                next = cs.charAt(i + 2);
                if (next >= '?' && next <= '?') {
                    stringBuilder.append(cs.subSequence(i + 1, i + 3));
                    startLength += 2;
                    i += 2;
                }
            }
            if (doneEmoji) {
                if (emojiOnly != null) {
                    emojiOnly[0] = emojiOnly[0] + 1;
                }
                EmojiDrawable drawable = getEmojiDrawable(stringBuilder.subSequence(0, stringBuilder.length()));
                if (drawable != null) {
                    s.setSpan(new EmojiSpan(drawable, 0, size, fontMetrics), startIndex, startIndex + startLength, 33);
                    emojiCount++;
                }
                startLength = 0;
                startIndex = -1;
                stringBuilder.setLength(0);
                doneEmoji = false;
            }
            if (VERSION.SDK_INT < 23 && emojiCount >= 50) {
                return s;
            }
            i++;
        }
        return s;
    }

    public static void addRecentEmoji(String code) {
        Integer count = (Integer) emojiUseHistory.get(code);
        if (count == null) {
            count = Integer.valueOf(0);
        }
        if (count.intValue() == 0 && emojiUseHistory.size() > 50) {
            for (int a = recentEmoji.size() - 1; a >= 0; a--) {
                emojiUseHistory.remove((String) recentEmoji.get(a));
                recentEmoji.remove(a);
                if (emojiUseHistory.size() <= 50) {
                    break;
                }
            }
        }
        emojiUseHistory.put(code, Integer.valueOf(count.intValue() + 1));
    }

    public static void sortEmoji() {
        recentEmoji.clear();
        for (Entry<String, Integer> entry : emojiUseHistory.entrySet()) {
            recentEmoji.add(entry.getKey());
        }
        Collections.sort(recentEmoji, new C13162());
        while (recentEmoji.size() > 50) {
            recentEmoji.remove(recentEmoji.size() - 1);
        }
    }

    public static void saveRecentEmoji() {
        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("emoji", 0);
        StringBuilder stringBuilder = new StringBuilder();
        for (Entry<String, Integer> entry : emojiUseHistory.entrySet()) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append((String) entry.getKey());
            stringBuilder.append("=");
            stringBuilder.append(entry.getValue());
        }
        preferences.edit().putString("emojis2", stringBuilder.toString()).commit();
    }

    public static void clearRecentEmoji() {
        ApplicationLoader.applicationContext.getSharedPreferences("emoji", 0).edit().putBoolean("filled_default", true).commit();
        emojiUseHistory.clear();
        recentEmoji.clear();
        saveRecentEmoji();
    }

    public static void loadRecentEmoji() {
        if (!recentEmojiLoaded) {
            String str;
            String[] args2;
            int a;
            recentEmojiLoaded = true;
            SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("emoji", 0);
            try {
                emojiUseHistory.clear();
                if (preferences.contains("emojis")) {
                    str = preferences.getString("emojis", "");
                    if (str != null && str.length() > 0) {
                        for (String arg : str.split(",")) {
                            args2 = arg.split("=");
                            long value = Utilities.parseLong(args2[0]).longValue();
                            String string = "";
                            for (a = 0; a < 4; a++) {
                                string = String.valueOf((char) ((int) value)) + string;
                                value >>= 16;
                                if (value == 0) {
                                    break;
                                }
                            }
                            if (string.length() > 0) {
                                emojiUseHistory.put(string, Utilities.parseInt(args2[1]));
                            }
                        }
                    }
                    preferences.edit().remove("emojis").commit();
                    saveRecentEmoji();
                } else {
                    str = preferences.getString("emojis2", "");
                    if (str != null && str.length() > 0) {
                        for (String arg2 : str.split(",")) {
                            args2 = arg2.split("=");
                            emojiUseHistory.put(args2[0], Utilities.parseInt(args2[1]));
                        }
                    }
                }
                if (emojiUseHistory.isEmpty() && !preferences.getBoolean("filled_default", false)) {
                    String[] newRecent = new String[]{"😂", "😘", "❤", "😍", "😊", "😁", "👍", "☺", "😔", "😄", "😭", "💋", "😒", "😳", "😜", "🙈", "😉", "😃", "😢", "😝", "😱", "😡", "😏", "😞", "😅", "😚", "🙊", "😌", "😀", "😋", "😆", "👌", "😐", "😕"};
                    for (int i = 0; i < newRecent.length; i++) {
                        emojiUseHistory.put(newRecent[i], Integer.valueOf(newRecent.length - i));
                    }
                    preferences.edit().putBoolean("filled_default", true).commit();
                    saveRecentEmoji();
                }
                sortEmoji();
            } catch (Exception e) {
                FileLog.e(e);
            }
            try {
                str = preferences.getString(TtmlNode.ATTR_TTS_COLOR, "");
                if (str != null && str.length() > 0) {
                    String[] args = str.split(",");
                    for (String arg22 : args) {
                        args2 = arg22.split("=");
                        emojiColor.put(args2[0], args2[1]);
                    }
                }
            } catch (Exception e2) {
                FileLog.e(e2);
            }
        }
    }

    public static void saveEmojiColors() {
        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("emoji", 0);
        StringBuilder stringBuilder = new StringBuilder();
        for (Entry<String, String> entry : emojiColor.entrySet()) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append((String) entry.getKey());
            stringBuilder.append("=");
            stringBuilder.append((String) entry.getValue());
        }
        preferences.edit().putString(TtmlNode.ATTR_TTS_COLOR, stringBuilder.toString()).commit();
    }
}
