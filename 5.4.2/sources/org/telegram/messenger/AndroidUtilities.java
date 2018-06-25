package org.telegram.messenger;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ShortcutInfo.Builder;
import android.content.pm.ShortcutManager;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.CallLog.Calls;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.C0700i;
import android.telephony.TelephonyManager;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.StateSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.p051a.p052a.p053a.C1244a;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import org.ir.talaeii.R;
import org.telegram.messenger.exoplayer2.source.ExtractorMediaSource;
import org.telegram.p149a.C2488b;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_document;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.EncryptedChat;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.ForegroundDetector;
import org.telegram.ui.Components.TypefaceSpan;
import utils.view.FontUtil;

public class AndroidUtilities {
    public static final int FLAG_TAG_ALL = 3;
    public static final int FLAG_TAG_BOLD = 2;
    public static final int FLAG_TAG_BR = 1;
    public static final int FLAG_TAG_COLOR = 4;
    public static Pattern WEB_URL;
    private static int adjustOwnerClassGuid = 0;
    private static RectF bitmapRect;
    private static final Object callLock = new Object();
    private static ContentObserver callLogContentObserver;
    public static DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
    public static float density = 1.0f;
    public static DisplayMetrics displayMetrics = new DisplayMetrics();
    public static Point displaySize = new Point();
    private static boolean hasCallPermissions = (VERSION.SDK_INT >= 23);
    public static boolean incorrectDisplaySizeFix;
    public static boolean isInMultiwindow;
    private static Boolean isTablet = null;
    public static int leftBaseline = (isTablet() ? 80 : 72);
    private static Field mAttachInfoField;
    private static Field mStableInsetsField;
    public static OvershootInterpolator overshootInterpolator = new OvershootInterpolator();
    public static Integer photoSize = null;
    private static int prevOrientation = -10;
    public static int roundMessageSize;
    private static Paint roundPaint;
    private static final Object smsLock = new Object();
    public static int statusBarHeight = 0;
    private static final Hashtable<String, Typeface> typefaceCache = new Hashtable();
    private static Runnable unregisterRunnable;
    public static boolean usingHardwareInput;
    private static boolean waitingForCall = false;
    private static boolean waitingForSms = false;

    static {
        WEB_URL = null;
        try {
            String str = "a-zA-Z0-9 -퟿豈-﷏ﷰ-￯";
            String str2 = "[a-zA-Z0-9 -퟿豈-﷏ﷰ-￯]([a-zA-Z0-9 -퟿豈-﷏ﷰ-￯\\-]{0,61}[a-zA-Z0-9 -퟿豈-﷏ﷰ-￯]){0,1}";
            str2 = "a-zA-Z -퟿豈-﷏ﷰ-￯";
            str2 = "[a-zA-Z -퟿豈-﷏ﷰ-￯]{2,63}";
            str2 = "([a-zA-Z0-9 -퟿豈-﷏ﷰ-￯]([a-zA-Z0-9 -퟿豈-﷏ﷰ-￯\\-]{0,61}[a-zA-Z0-9 -퟿豈-﷏ﷰ-￯]){0,1}\\.)+[a-zA-Z -퟿豈-﷏ﷰ-￯]{2,63}";
            WEB_URL = Pattern.compile("((?:(http|https|Http|Https):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?(?:" + Pattern.compile("(([a-zA-Z0-9 -퟿豈-﷏ﷰ-￯]([a-zA-Z0-9 -퟿豈-﷏ﷰ-￯\\-]{0,61}[a-zA-Z0-9 -퟿豈-﷏ﷰ-￯]){0,1}\\.)+[a-zA-Z -퟿豈-﷏ﷰ-￯]{2,63}|" + Pattern.compile("((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9]))") + ")") + ")(?:\\:\\d{1,5})?)(\\/(?:(?:[" + "a-zA-Z0-9 -퟿豈-﷏ﷰ-￯" + "\\;\\/\\?\\:\\@\\&\\=\\#\\~\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?(?:\\b|$)");
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
        checkDisplaySize(ApplicationLoader.applicationContext, null);
    }

    public static void addMediaToGallery(Uri uri) {
        if (uri != null) {
            try {
                Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                intent.setData(uri);
                ApplicationLoader.applicationContext.sendBroadcast(intent);
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
        }
    }

    public static void addMediaToGallery(String str) {
        if (str != null) {
            addMediaToGallery(Uri.fromFile(new File(str)));
        }
    }

    public static void addToClipboard(CharSequence charSequence) {
        try {
            ((ClipboardManager) ApplicationLoader.applicationContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("label", charSequence));
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
    }

    public static byte[] calcAuthKeyHash(byte[] bArr) {
        Object obj = new byte[16];
        System.arraycopy(Utilities.computeSHA1(bArr), 0, obj, 0, 16);
        return obj;
    }

    public static int[] calcDrawableColor(Drawable drawable) {
        int i = Theme.ACTION_BAR_VIDEO_EDIT_COLOR;
        int[] iArr = new int[2];
        try {
            if (drawable instanceof BitmapDrawable) {
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                if (bitmap != null) {
                    Bitmap createScaledBitmap = Bitmaps.createScaledBitmap(bitmap, 1, 1, true);
                    if (createScaledBitmap != null) {
                        i = createScaledBitmap.getPixel(0, 0);
                        if (bitmap != createScaledBitmap) {
                            createScaledBitmap.recycle();
                        }
                    }
                }
            } else if (drawable instanceof ColorDrawable) {
                i = ((ColorDrawable) drawable).getColor();
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
        double[] rgbToHsv = rgbToHsv((i >> 16) & 255, (i >> 8) & 255, i & 255);
        rgbToHsv[1] = Math.min(1.0d, (rgbToHsv[1] + 0.05d) + (0.1d * (1.0d - rgbToHsv[1])));
        rgbToHsv[2] = Math.max(0.0d, rgbToHsv[2] * 0.65d);
        int[] hsvToRgb = hsvToRgb(rgbToHsv[0], rgbToHsv[1], rgbToHsv[2]);
        iArr[0] = Color.argb(102, hsvToRgb[0], hsvToRgb[1], hsvToRgb[2]);
        iArr[1] = Color.argb(136, hsvToRgb[0], hsvToRgb[1], hsvToRgb[2]);
        return iArr;
    }

    public static void cancelRunOnUIThread(Runnable runnable) {
        ApplicationLoader.applicationHandler.removeCallbacks(runnable);
    }

    public static void checkDisplaySize(Context context, Configuration configuration) {
        boolean z = true;
        try {
            int ceil;
            density = context.getResources().getDisplayMetrics().density;
            if (configuration == null) {
                configuration = context.getResources().getConfiguration();
            }
            if (configuration.keyboard == 1 || configuration.hardKeyboardHidden != 1) {
                z = false;
            }
            usingHardwareInput = z;
            WindowManager windowManager = (WindowManager) context.getSystemService("window");
            if (windowManager != null) {
                Display defaultDisplay = windowManager.getDefaultDisplay();
                if (defaultDisplay != null) {
                    defaultDisplay.getMetrics(displayMetrics);
                    defaultDisplay.getSize(displaySize);
                }
            }
            if (configuration.screenWidthDp != 0) {
                ceil = (int) Math.ceil((double) (((float) configuration.screenWidthDp) * density));
                if (Math.abs(displaySize.x - ceil) > 3) {
                    displaySize.x = ceil;
                }
            }
            if (configuration.screenHeightDp != 0) {
                ceil = (int) Math.ceil((double) (((float) configuration.screenHeightDp) * density));
                if (Math.abs(displaySize.y - ceil) > 3) {
                    displaySize.y = ceil;
                }
            }
            if (roundMessageSize == 0) {
                if (isTablet()) {
                    roundMessageSize = (int) (((float) getMinTabletSide()) * 0.6f);
                } else {
                    roundMessageSize = (int) (((float) Math.min(displaySize.x, displaySize.y)) * 0.6f);
                }
            }
            FileLog.m13726e("display size = " + displaySize.x + " " + displaySize.y + " " + displayMetrics.xdpi + "x" + displayMetrics.ydpi);
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
    }

    public static void checkForCrashes(Activity activity) {
    }

    public static void checkForUpdates(Activity activity) {
    }

    public static boolean checkPhonePattern(String str, String str2) {
        if (TextUtils.isEmpty(str) || str.equals("*")) {
            return true;
        }
        String[] split = str.split("\\*");
        String b = C2488b.m12190b(str2);
        int i = 0;
        for (String str3 : split) {
            if (!TextUtils.isEmpty(str3)) {
                i = b.indexOf(str3, i);
                if (i == -1) {
                    return false;
                }
                i += str3.length();
            }
        }
        return true;
    }

    public static void clearCursorDrawable(EditText editText) {
        if (editText != null) {
            try {
                Field declaredField = TextView.class.getDeclaredField("mCursorDrawableRes");
                declaredField.setAccessible(true);
                declaredField.setInt(editText, 0);
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
        }
    }

    @SuppressLint({"NewApi"})
    public static void clearDrawableAnimation(View view) {
        if (VERSION.SDK_INT >= 21 && view != null) {
            Drawable selector;
            if (view instanceof ListView) {
                selector = ((ListView) view).getSelector();
                if (selector != null) {
                    selector.setState(StateSet.NOTHING);
                    return;
                }
                return;
            }
            selector = view.getBackground();
            if (selector != null) {
                selector.setState(StateSet.NOTHING);
                selector.jumpToCurrentState();
            }
        }
    }

    public static int compare(int i, int i2) {
        return i == i2 ? 0 : i > i2 ? 1 : -1;
    }

    public static boolean copyFile(File file, File file2) {
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
        Throwable e;
        FileInputStream fileInputStream2;
        FileOutputStream fileOutputStream2 = null;
        if (!file2.exists()) {
            file2.createNewFile();
        }
        try {
            fileInputStream = new FileInputStream(file);
            try {
                fileOutputStream = new FileOutputStream(file2);
            } catch (Exception e2) {
                e = e2;
                fileInputStream2 = fileInputStream;
                try {
                    FileLog.m13728e(e);
                    if (fileInputStream2 != null) {
                        fileInputStream2.close();
                    }
                    if (fileOutputStream2 != null) {
                        return false;
                    }
                    fileOutputStream2.close();
                    return false;
                } catch (Throwable th) {
                    e = th;
                    fileOutputStream = fileOutputStream2;
                    fileInputStream = fileInputStream2;
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    throw e;
                }
            } catch (Throwable th2) {
                e = th2;
                fileOutputStream = null;
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw e;
            }
            try {
                fileOutputStream.getChannel().transferFrom(fileInputStream.getChannel(), 0, fileInputStream.getChannel().size());
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                return true;
            } catch (Exception e3) {
                e = e3;
                fileOutputStream2 = fileOutputStream;
                fileInputStream2 = fileInputStream;
                FileLog.m13728e(e);
                if (fileInputStream2 != null) {
                    fileInputStream2.close();
                }
                if (fileOutputStream2 != null) {
                    return false;
                }
                fileOutputStream2.close();
                return false;
            } catch (Throwable th3) {
                e = th3;
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw e;
            }
        } catch (Exception e4) {
            e = e4;
            fileInputStream2 = null;
            FileLog.m13728e(e);
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            if (fileOutputStream2 != null) {
                return false;
            }
            fileOutputStream2.close();
            return false;
        } catch (Throwable th4) {
            e = th4;
            fileOutputStream = null;
            fileInputStream = null;
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            throw e;
        }
    }

    public static boolean copyFile(InputStream inputStream, File file) {
        OutputStream fileOutputStream = new FileOutputStream(file);
        byte[] bArr = new byte[4096];
        while (true) {
            int read = inputStream.read(bArr);
            if (read > 0) {
                Thread.yield();
                fileOutputStream.write(bArr, 0, read);
            } else {
                fileOutputStream.close();
                return true;
            }
        }
    }

    private static Intent createIntrnalShortcutIntent(long j) {
        Intent intent = new Intent(ApplicationLoader.applicationContext, OpenChatReceiver.class);
        int i = (int) j;
        int i2 = (int) (j >> 32);
        if (i == 0) {
            intent.putExtra("encId", i2);
            if (MessagesController.getInstance().getEncryptedChat(Integer.valueOf(i2)) == null) {
                return null;
            }
        } else if (i > 0) {
            intent.putExtra("userId", i);
        } else if (i >= 0) {
            return null;
        } else {
            intent.putExtra("chatId", -i);
        }
        intent.setAction("com.tmessages.openchat" + j);
        intent.addFlags(ConnectionsManager.FileTypeFile);
        return intent;
    }

    public static byte[] decodeQuotedPrintable(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i = 0;
        while (i < bArr.length) {
            byte b = bArr[i];
            if (b == (byte) 61) {
                i++;
                try {
                    int digit = Character.digit((char) bArr[i], 16);
                    i++;
                    byteArrayOutputStream.write((char) ((digit << 4) + Character.digit((char) bArr[i], 16)));
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                    return null;
                }
            }
            byteArrayOutputStream.write(b);
            i++;
        }
        byte[] toByteArray = byteArrayOutputStream.toByteArray();
        try {
            byteArrayOutputStream.close();
            return toByteArray;
        } catch (Throwable e2) {
            FileLog.m13728e(e2);
            return toByteArray;
        }
    }

    public static int dp(float f) {
        return f == BitmapDescriptorFactory.HUE_RED ? 0 : (int) Math.ceil((double) (density * f));
    }

    public static int dp2(float f) {
        return f == BitmapDescriptorFactory.HUE_RED ? 0 : (int) Math.floor((double) (density * f));
    }

    public static float dpf2(float f) {
        return f == BitmapDescriptorFactory.HUE_RED ? BitmapDescriptorFactory.HUE_RED : density * f;
    }

    public static void endIncomingCall() {
        if (hasCallPermissions) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) ApplicationLoader.applicationContext.getSystemService("phone");
                Method declaredMethod = Class.forName(telephonyManager.getClass().getName()).getDeclaredMethod("getITelephony", new Class[0]);
                declaredMethod.setAccessible(true);
                C1244a c1244a = (C1244a) declaredMethod.invoke(telephonyManager, new Object[0]);
                C1244a c1244a2 = (C1244a) declaredMethod.invoke(telephonyManager, new Object[0]);
                c1244a2.m6478b();
                c1244a2.m6477a();
            } catch (Throwable e) {
                FileLog.m13727e("tmessages", e);
            }
        }
    }

    public static String formatFileSize(long j) {
        if (j < 1024) {
            return String.format("%d B", new Object[]{Long.valueOf(j)});
        } else if (j < 1048576) {
            return String.format("%.1f KB", new Object[]{Float.valueOf(((float) j) / 1024.0f)});
        } else if (j < 1073741824) {
            return String.format("%.1f MB", new Object[]{Float.valueOf((((float) j) / 1024.0f) / 1024.0f)});
        } else {
            return String.format("%.1f GB", new Object[]{Float.valueOf(((((float) j) / 1024.0f) / 1024.0f) / 1024.0f)});
        }
    }

    public static File generatePicturePath() {
        try {
            File albumDir = getAlbumDir();
            Date date = new Date();
            date.setTime((System.currentTimeMillis() + ((long) Utilities.random.nextInt(1000))) + 1);
            return new File(albumDir, "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss_SSS", Locale.US).format(date) + ".jpg");
        } catch (Throwable e) {
            FileLog.m13728e(e);
            return null;
        }
    }

    public static CharSequence generateSearchName(String str, String str2, String str3) {
        if (str == null && str2 == null) {
            return TtmlNode.ANONYMOUS_REGION_ID;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (str == null || str.length() == 0) {
            str = str2;
        } else if (!(str2 == null || str2.length() == 0)) {
            str = str + " " + str2;
        }
        String trim = str.trim();
        String str4 = " " + trim.toLowerCase();
        int i = 0;
        while (true) {
            int indexOf = str4.indexOf(" " + str3, i);
            if (indexOf == -1) {
                break;
            }
            int i2 = indexOf - (indexOf == 0 ? 0 : 1);
            int length = ((indexOf == 0 ? 0 : 1) + str3.length()) + i2;
            if (i != 0 && i != i2 + 1) {
                spannableStringBuilder.append(trim.substring(i, i2));
            } else if (i == 0 && i2 != 0) {
                spannableStringBuilder.append(trim.substring(0, i2));
            }
            String substring = trim.substring(i2, Math.min(trim.length(), length));
            if (substring.startsWith(" ")) {
                spannableStringBuilder.append(" ");
            }
            Object trim2 = substring.trim();
            indexOf = spannableStringBuilder.length();
            spannableStringBuilder.append(trim2);
            spannableStringBuilder.setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4)), indexOf, trim2.length() + indexOf, 33);
            i = length;
        }
        if (i != -1 && i < trim.length()) {
            spannableStringBuilder.append(trim.substring(i, trim.length()));
        }
        return spannableStringBuilder;
    }

    public static File generateVideoPath() {
        try {
            File albumDir = getAlbumDir();
            Date date = new Date();
            date.setTime((System.currentTimeMillis() + ((long) Utilities.random.nextInt(1000))) + 1);
            return new File(albumDir, "VID_" + new SimpleDateFormat("yyyyMMdd_HHmmss_SSS", Locale.US).format(date) + ".mp4");
        } catch (Throwable e) {
            FileLog.m13728e(e);
            return null;
        }
    }

    private static File getAlbumDir() {
        if (VERSION.SDK_INT >= 23 && ApplicationLoader.applicationContext.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != 0) {
            return FileLoader.getInstance().getDirectory(4);
        }
        File file;
        if ("mounted".equals(Environment.getExternalStorageState())) {
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Telegram");
            if (!(file.mkdirs() || file.exists())) {
                FileLog.m13725d("failed to create directory");
                return null;
            }
        }
        FileLog.m13725d("External storage is not mounted READ/WRITE.");
        file = null;
        return file;
    }

    public static File getCacheDir() {
        File externalCacheDir;
        String str = null;
        try {
            str = Environment.getExternalStorageState();
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
        if (str == null || str.startsWith("mounted")) {
            try {
                externalCacheDir = ApplicationLoader.applicationContext.getExternalCacheDir();
                if (externalCacheDir != null) {
                    return externalCacheDir;
                }
            } catch (Throwable e2) {
                FileLog.m13728e(e2);
            }
        }
        try {
            externalCacheDir = ApplicationLoader.applicationContext.getCacheDir();
            if (externalCacheDir != null) {
                return externalCacheDir;
            }
        } catch (Throwable e22) {
            FileLog.m13728e(e22);
        }
        return new File(TtmlNode.ANONYMOUS_REGION_ID);
    }

    public static String getDataColumn(Context context, Uri uri, String str, String[] strArr) {
        Cursor cursor;
        Throwable th;
        String str2 = "_data";
        Cursor query;
        try {
            query = context.getContentResolver().query(uri, new String[]{"_data"}, str, strArr, null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        str2 = query.getString(query.getColumnIndexOrThrow("_data"));
                        if (str2.startsWith("content://") || !(str2.startsWith("/") || str2.startsWith("file://"))) {
                            if (query != null) {
                                query.close();
                            }
                            return null;
                        } else if (query == null) {
                            return str2;
                        } else {
                            query.close();
                            return str2;
                        }
                    }
                } catch (Exception e) {
                    cursor = query;
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    if (query != null) {
                        query.close();
                    }
                    throw th;
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (Exception e2) {
            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th;
        }
        return null;
    }

    public static int getMinTabletSide() {
        int i;
        if (isSmallTablet()) {
            int min = Math.min(displaySize.x, displaySize.y);
            int max = Math.max(displaySize.x, displaySize.y);
            i = (max * 35) / 100;
            if (i < dp(320.0f)) {
                i = dp(320.0f);
            }
            return Math.min(min, max - i);
        }
        min = Math.min(displaySize.x, displaySize.y);
        i = (min * 35) / 100;
        if (i < dp(320.0f)) {
            i = dp(320.0f);
        }
        return min - i;
    }

    public static int getMyLayerVersion(int i) {
        return 65535 & i;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @android.annotation.SuppressLint({"NewApi"})
    public static java.lang.String getPath(android.net.Uri r7) {
        /*
        r3 = 1;
        r1 = 0;
        r0 = 0;
        r2 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x0103 }
        r4 = 19;
        if (r2 < r4) goto L_0x0051;
    L_0x0009:
        r2 = r3;
    L_0x000a:
        if (r2 == 0) goto L_0x00d9;
    L_0x000c:
        r2 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Exception -> 0x0103 }
        r2 = android.provider.DocumentsContract.isDocumentUri(r2, r7);	 Catch:{ Exception -> 0x0103 }
        if (r2 == 0) goto L_0x00d9;
    L_0x0014:
        r2 = isExternalStorageDocument(r7);	 Catch:{ Exception -> 0x0103 }
        if (r2 == 0) goto L_0x0053;
    L_0x001a:
        r1 = android.provider.DocumentsContract.getDocumentId(r7);	 Catch:{ Exception -> 0x0103 }
        r2 = ":";
        r1 = r1.split(r2);	 Catch:{ Exception -> 0x0103 }
        r2 = 0;
        r2 = r1[r2];	 Catch:{ Exception -> 0x0103 }
        r3 = "primary";
        r2 = r3.equalsIgnoreCase(r2);	 Catch:{ Exception -> 0x0103 }
        if (r2 == 0) goto L_0x0050;
    L_0x0031:
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0103 }
        r2.<init>();	 Catch:{ Exception -> 0x0103 }
        r3 = android.os.Environment.getExternalStorageDirectory();	 Catch:{ Exception -> 0x0103 }
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0103 }
        r3 = "/";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0103 }
        r3 = 1;
        r1 = r1[r3];	 Catch:{ Exception -> 0x0103 }
        r1 = r2.append(r1);	 Catch:{ Exception -> 0x0103 }
        r0 = r1.toString();	 Catch:{ Exception -> 0x0103 }
    L_0x0050:
        return r0;
    L_0x0051:
        r2 = r1;
        goto L_0x000a;
    L_0x0053:
        r2 = isDownloadsDocument(r7);	 Catch:{ Exception -> 0x0103 }
        if (r2 == 0) goto L_0x0079;
    L_0x0059:
        r1 = android.provider.DocumentsContract.getDocumentId(r7);	 Catch:{ Exception -> 0x0103 }
        r2 = "content://downloads/public_downloads";
        r2 = android.net.Uri.parse(r2);	 Catch:{ Exception -> 0x0103 }
        r1 = java.lang.Long.valueOf(r1);	 Catch:{ Exception -> 0x0103 }
        r4 = r1.longValue();	 Catch:{ Exception -> 0x0103 }
        r1 = android.content.ContentUris.withAppendedId(r2, r4);	 Catch:{ Exception -> 0x0103 }
        r2 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Exception -> 0x0103 }
        r3 = 0;
        r4 = 0;
        r0 = getDataColumn(r2, r1, r3, r4);	 Catch:{ Exception -> 0x0103 }
        goto L_0x0050;
    L_0x0079:
        r2 = isMediaDocument(r7);	 Catch:{ Exception -> 0x0103 }
        if (r2 == 0) goto L_0x0050;
    L_0x007f:
        r2 = android.provider.DocumentsContract.getDocumentId(r7);	 Catch:{ Exception -> 0x0103 }
        r4 = ":";
        r4 = r2.split(r4);	 Catch:{ Exception -> 0x0103 }
        r2 = 0;
        r5 = r4[r2];	 Catch:{ Exception -> 0x0103 }
        r2 = -1;
        r6 = r5.hashCode();	 Catch:{ Exception -> 0x0103 }
        switch(r6) {
            case 93166550: goto L_0x00c5;
            case 100313435: goto L_0x00b0;
            case 112202875: goto L_0x00ba;
            default: goto L_0x0095;
        };	 Catch:{ Exception -> 0x0103 }
    L_0x0095:
        r1 = r2;
    L_0x0096:
        switch(r1) {
            case 0: goto L_0x00d0;
            case 1: goto L_0x00d3;
            case 2: goto L_0x00d6;
            default: goto L_0x0099;
        };	 Catch:{ Exception -> 0x0103 }
    L_0x0099:
        r1 = r0;
    L_0x009a:
        r2 = "_id=?";
        r2 = 1;
        r2 = new java.lang.String[r2];	 Catch:{ Exception -> 0x0103 }
        r3 = 0;
        r5 = 1;
        r4 = r4[r5];	 Catch:{ Exception -> 0x0103 }
        r2[r3] = r4;	 Catch:{ Exception -> 0x0103 }
        r3 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Exception -> 0x0103 }
        r4 = "_id=?";
        r0 = getDataColumn(r3, r1, r4, r2);	 Catch:{ Exception -> 0x0103 }
        goto L_0x0050;
    L_0x00b0:
        r3 = "image";
        r3 = r5.equals(r3);	 Catch:{ Exception -> 0x0103 }
        if (r3 == 0) goto L_0x0095;
    L_0x00b9:
        goto L_0x0096;
    L_0x00ba:
        r1 = "video";
        r1 = r5.equals(r1);	 Catch:{ Exception -> 0x0103 }
        if (r1 == 0) goto L_0x0095;
    L_0x00c3:
        r1 = r3;
        goto L_0x0096;
    L_0x00c5:
        r1 = "audio";
        r1 = r5.equals(r1);	 Catch:{ Exception -> 0x0103 }
        if (r1 == 0) goto L_0x0095;
    L_0x00ce:
        r1 = 2;
        goto L_0x0096;
    L_0x00d0:
        r1 = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;	 Catch:{ Exception -> 0x0103 }
        goto L_0x009a;
    L_0x00d3:
        r1 = android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI;	 Catch:{ Exception -> 0x0103 }
        goto L_0x009a;
    L_0x00d6:
        r1 = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;	 Catch:{ Exception -> 0x0103 }
        goto L_0x009a;
    L_0x00d9:
        r1 = "content";
        r2 = r7.getScheme();	 Catch:{ Exception -> 0x0103 }
        r1 = r1.equalsIgnoreCase(r2);	 Catch:{ Exception -> 0x0103 }
        if (r1 == 0) goto L_0x00f0;
    L_0x00e6:
        r1 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Exception -> 0x0103 }
        r2 = 0;
        r3 = 0;
        r0 = getDataColumn(r1, r7, r2, r3);	 Catch:{ Exception -> 0x0103 }
        goto L_0x0050;
    L_0x00f0:
        r1 = "file";
        r2 = r7.getScheme();	 Catch:{ Exception -> 0x0103 }
        r1 = r1.equalsIgnoreCase(r2);	 Catch:{ Exception -> 0x0103 }
        if (r1 == 0) goto L_0x0050;
    L_0x00fd:
        r0 = r7.getPath();	 Catch:{ Exception -> 0x0103 }
        goto L_0x0050;
    L_0x0103:
        r1 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r1);
        goto L_0x0050;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.AndroidUtilities.getPath(android.net.Uri):java.lang.String");
    }

    public static int getPeerLayerVersion(int i) {
        return (i >> 16) & 65535;
    }

    public static int getPhotoSize() {
        if (photoSize == null) {
            photoSize = Integer.valueOf(1280);
        }
        return photoSize.intValue();
    }

    public static float getPixelsInCM(float f, boolean z) {
        return (z ? displayMetrics.xdpi : displayMetrics.ydpi) * (f / 2.54f);
    }

    public static Point getRealScreenSize() {
        Point point = new Point();
        try {
            WindowManager windowManager = (WindowManager) ApplicationLoader.applicationContext.getSystemService("window");
            if (VERSION.SDK_INT >= 17) {
                windowManager.getDefaultDisplay().getRealSize(point);
            } else {
                try {
                    point.set(((Integer) Display.class.getMethod("getRawWidth", new Class[0]).invoke(windowManager.getDefaultDisplay(), new Object[0])).intValue(), ((Integer) Display.class.getMethod("getRawHeight", new Class[0]).invoke(windowManager.getDefaultDisplay(), new Object[0])).intValue());
                } catch (Throwable e) {
                    point.set(windowManager.getDefaultDisplay().getWidth(), windowManager.getDefaultDisplay().getHeight());
                    FileLog.m13728e(e);
                }
            }
        } catch (Throwable e2) {
            FileLog.m13728e(e2);
        }
        return point;
    }

    public static CharSequence getTrimmedString(CharSequence charSequence) {
        if (!(charSequence == null || charSequence.length() == 0)) {
            while (charSequence.length() > 0 && (charSequence.charAt(0) == '\n' || charSequence.charAt(0) == ' ')) {
                charSequence = charSequence.subSequence(1, charSequence.length());
            }
            while (charSequence.length() > 0 && (charSequence.charAt(charSequence.length() - 1) == '\n' || charSequence.charAt(charSequence.length() - 1) == ' ')) {
                charSequence = charSequence.subSequence(0, charSequence.length() - 1);
            }
        }
        return charSequence;
    }

    public static Typeface getTypeface(String str) {
        Typeface typeface = null;
        if (!ApplicationLoader.USE_DEVICE_FONT) {
            String a = FontUtil.a();
            synchronized (typefaceCache) {
                if (!typefaceCache.containsKey(a)) {
                    try {
                        typefaceCache.put(a, Typeface.createFromAsset(ApplicationLoader.applicationContext.getAssets(), a));
                    } catch (Exception e) {
                        FileLog.m13726e("Could not get typeface '" + a + "' because " + e.getMessage());
                    }
                }
                typeface = (Typeface) typefaceCache.get(a);
            }
        }
        return typeface;
    }

    public static int getViewInset(View view) {
        if (view == null || VERSION.SDK_INT < 21 || view.getHeight() == displaySize.y || view.getHeight() == displaySize.y - statusBarHeight) {
            return 0;
        }
        try {
            if (mAttachInfoField == null) {
                mAttachInfoField = View.class.getDeclaredField("mAttachInfo");
                mAttachInfoField.setAccessible(true);
            }
            Object obj = mAttachInfoField.get(view);
            if (obj != null) {
                if (mStableInsetsField == null) {
                    mStableInsetsField = obj.getClass().getDeclaredField("mStableInsets");
                    mStableInsetsField.setAccessible(true);
                }
                return ((Rect) mStableInsetsField.get(obj)).bottom;
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
        return 0;
    }

    public static boolean handleProxyIntent(Activity activity, Intent intent) {
        String str = null;
        if (intent == null) {
            return false;
        }
        try {
            if ((intent.getFlags() & ExtractorMediaSource.DEFAULT_LOADING_CHECK_INTERVAL_BYTES) != 0) {
                return false;
            }
            Uri data = intent.getData();
            if (data != null) {
                Object obj;
                Object obj2;
                String scheme = data.getScheme();
                if (scheme != null) {
                    String queryParameter;
                    String queryParameter2;
                    String str2;
                    String str3;
                    if (scheme.equals("http") || scheme.equals("https")) {
                        scheme = data.getHost().toLowerCase();
                        if (scheme.equals("telegram.me") || scheme.equals("t.me") || scheme.equals("telegram.dog") || scheme.equals("telesco.pe")) {
                            scheme = data.getPath();
                            if (scheme != null && scheme.startsWith("/socks")) {
                                str = data.getQueryParameter("server");
                                scheme = data.getQueryParameter("port");
                                queryParameter = data.getQueryParameter("user");
                                queryParameter2 = data.getQueryParameter("pass");
                                str2 = str;
                                str = queryParameter2;
                                obj = str2;
                                str3 = scheme;
                                scheme = queryParameter;
                                obj2 = str3;
                                if (!(TextUtils.isEmpty(obj) || TextUtils.isEmpty(obj2))) {
                                    if (scheme == null) {
                                        scheme = TtmlNode.ANONYMOUS_REGION_ID;
                                    }
                                    if (str == null) {
                                        str = TtmlNode.ANONYMOUS_REGION_ID;
                                    }
                                    showProxyAlert(activity, obj, obj2, scheme, str);
                                    return true;
                                }
                            }
                        }
                        scheme = null;
                        queryParameter2 = null;
                        queryParameter = null;
                        str2 = str;
                        str = queryParameter2;
                        obj = str2;
                        str3 = scheme;
                        scheme = queryParameter;
                        obj2 = str3;
                        if (scheme == null) {
                            scheme = TtmlNode.ANONYMOUS_REGION_ID;
                        }
                        if (str == null) {
                            str = TtmlNode.ANONYMOUS_REGION_ID;
                        }
                        showProxyAlert(activity, obj, obj2, scheme, str);
                        return true;
                    } else if (scheme.equals("tg")) {
                        scheme = data.toString();
                        if (scheme.startsWith("tg:socks") || scheme.startsWith("tg://socks")) {
                            data = Uri.parse(scheme.replace("tg:proxy", "tg://telegram.org").replace("tg://proxy", "tg://telegram.org"));
                            str = data.getQueryParameter("server");
                            scheme = data.getQueryParameter("port");
                            queryParameter = data.getQueryParameter("user");
                            str2 = str;
                            str = data.getQueryParameter("pass");
                            queryParameter2 = str2;
                            str3 = scheme;
                            scheme = queryParameter;
                            queryParameter = str3;
                            if (scheme == null) {
                                scheme = TtmlNode.ANONYMOUS_REGION_ID;
                            }
                            if (str == null) {
                                str = TtmlNode.ANONYMOUS_REGION_ID;
                            }
                            showProxyAlert(activity, obj, obj2, scheme, str);
                            return true;
                        }
                    }
                }
                obj = null;
                obj2 = null;
                scheme = null;
                if (scheme == null) {
                    scheme = TtmlNode.ANONYMOUS_REGION_ID;
                }
                if (str == null) {
                    str = TtmlNode.ANONYMOUS_REGION_ID;
                }
                showProxyAlert(activity, obj, obj2, scheme, str);
                return true;
            }
            return false;
        } catch (Exception e) {
        }
    }

    public static void hideKeyboard(View view) {
        if (view != null) {
            try {
                InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService("input_method");
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
        }
    }

    private static int[] hsvToRgb(double d, double d2, double d3) {
        double floor = (double) ((int) Math.floor(6.0d * d));
        double d4 = (6.0d * d) - floor;
        double d5 = (1.0d - d2) * d3;
        double d6 = (1.0d - (d4 * d2)) * d3;
        d4 = (1.0d - ((1.0d - d4) * d2)) * d3;
        double d7;
        switch (((int) floor) % 6) {
            case 0:
                d6 = d3;
                d3 = d5;
                d5 = d4;
                break;
            case 1:
                d7 = d5;
                d5 = d3;
                d3 = d7;
                break;
            case 2:
                d6 = d5;
                d5 = d3;
                d3 = d4;
                break;
            case 3:
                d7 = d6;
                d6 = d5;
                d5 = d7;
                break;
            case 4:
                d6 = d4;
                break;
            case 5:
                d7 = d6;
                d6 = d3;
                d3 = d7;
                break;
            default:
                d3 = 0.0d;
                d5 = 0.0d;
                d6 = 0.0d;
                break;
        }
        return new int[]{(int) (d6 * 255.0d), (int) (d5 * 255.0d), (int) (255.0d * d3)};
    }

    public static void installShortcut(long j) {
        Chat chat;
        User user;
        Object obj;
        Throwable th;
        Builder intent;
        Intent intent2;
        Object obj2 = null;
        Parcelable parcelable = null;
        Parcelable createIntrnalShortcutIntent = createIntrnalShortcutIntent(j);
        int i = (int) j;
        int i2 = (int) (j >> 32);
        if (i == 0) {
            EncryptedChat encryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf(i2));
            if (encryptedChat != null) {
                chat = null;
                user = MessagesController.getInstance().getUser(Integer.valueOf(encryptedChat.user_id));
            } else {
                return;
            }
        } else if (i > 0) {
            chat = null;
            user = MessagesController.getInstance().getUser(Integer.valueOf(i));
        } else if (i < 0) {
            chat = MessagesController.getInstance().getChat(Integer.valueOf(-i));
            user = null;
        } else {
            return;
        }
        if (user != null || chat != null) {
            TLObject tLObject;
            if (user == null) {
                obj = chat.title;
                if (chat.photo != null) {
                    tLObject = chat.photo.photo_small;
                }
                tLObject = null;
            } else if (UserObject.isUserSelf(user)) {
                obj = LocaleController.getString("SavedMessages", R.string.SavedMessages);
                obj2 = 1;
                tLObject = null;
            } else {
                obj = ContactsController.formatName(user.first_name, user.last_name);
                if (user.photo != null) {
                    tLObject = user.photo.photo_small;
                }
                tLObject = null;
            }
            if (!(obj2 == null && tLObject == null)) {
                Bitmap decodeFile;
                if (obj2 == null) {
                    try {
                        decodeFile = BitmapFactory.decodeFile(FileLoader.getPathToAttach(tLObject, true).toString());
                    } catch (Throwable th2) {
                        th = th2;
                        try {
                            FileLog.m13728e(th);
                            if (VERSION.SDK_INT >= 26) {
                                intent = new Builder(ApplicationLoader.applicationContext, "sdid_" + j).setShortLabel(obj).setIntent(createIntrnalShortcutIntent);
                                if (parcelable == null) {
                                    intent.setIcon(Icon.createWithBitmap(parcelable));
                                } else if (user != null) {
                                    if (user.bot) {
                                        intent.setIcon(Icon.createWithResource(ApplicationLoader.applicationContext, R.drawable.book_bot));
                                    } else {
                                        intent.setIcon(Icon.createWithResource(ApplicationLoader.applicationContext, R.drawable.book_user));
                                    }
                                } else if (chat != null) {
                                    if (ChatObject.isChannel(chat)) {
                                    }
                                    intent.setIcon(Icon.createWithResource(ApplicationLoader.applicationContext, R.drawable.book_group));
                                }
                                ((ShortcutManager) ApplicationLoader.applicationContext.getSystemService(ShortcutManager.class)).requestPinShortcut(intent.build(), null);
                                return;
                            }
                            intent2 = new Intent();
                            if (parcelable == null) {
                                intent2.putExtra("android.intent.extra.shortcut.ICON", parcelable);
                            } else if (user != null) {
                                if (user.bot) {
                                    intent2.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", ShortcutIconResource.fromContext(ApplicationLoader.applicationContext, R.drawable.book_bot));
                                } else {
                                    intent2.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", ShortcutIconResource.fromContext(ApplicationLoader.applicationContext, R.drawable.book_user));
                                }
                            } else if (chat != null) {
                                if (ChatObject.isChannel(chat)) {
                                }
                                intent2.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", ShortcutIconResource.fromContext(ApplicationLoader.applicationContext, R.drawable.book_group));
                            }
                            intent2.putExtra("android.intent.extra.shortcut.INTENT", createIntrnalShortcutIntent);
                            intent2.putExtra("android.intent.extra.shortcut.NAME", obj);
                            intent2.putExtra("duplicate", false);
                            intent2.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
                            ApplicationLoader.applicationContext.sendBroadcast(intent2);
                        } catch (Throwable th3) {
                            FileLog.m13728e(th3);
                            return;
                        }
                    }
                }
                decodeFile = null;
                if (!(obj2 == null && decodeFile == null)) {
                    try {
                        int dp = dp(58.0f);
                        Bitmap createBitmap = Bitmap.createBitmap(dp, dp, Config.ARGB_8888);
                        createBitmap.eraseColor(0);
                        Canvas canvas = new Canvas(createBitmap);
                        if (obj2 != null) {
                            AvatarDrawable avatarDrawable = new AvatarDrawable(user);
                            avatarDrawable.setSavedMessages(1);
                            avatarDrawable.setBounds(0, 0, dp, dp);
                            avatarDrawable.draw(canvas);
                        } else {
                            Shader bitmapShader = new BitmapShader(decodeFile, TileMode.CLAMP, TileMode.CLAMP);
                            if (roundPaint == null) {
                                roundPaint = new Paint(1);
                                bitmapRect = new RectF();
                            }
                            float width = ((float) dp) / ((float) decodeFile.getWidth());
                            canvas.save();
                            canvas.scale(width, width);
                            roundPaint.setShader(bitmapShader);
                            bitmapRect.set(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) decodeFile.getWidth(), (float) decodeFile.getHeight());
                            canvas.drawRoundRect(bitmapRect, (float) decodeFile.getWidth(), (float) decodeFile.getHeight(), roundPaint);
                            canvas.restore();
                        }
                        Drawable drawable = ApplicationLoader.applicationContext.getResources().getDrawable(R.drawable.book_logo);
                        int dp2 = dp(15.0f);
                        int dp3 = (dp - dp2) - dp(2.0f);
                        dp = (dp - dp2) - dp(2.0f);
                        drawable.setBounds(dp3, dp, dp3 + dp2, dp2 + dp);
                        drawable.draw(canvas);
                        try {
                            canvas.setBitmap(null);
                        } catch (Exception e) {
                        }
                        decodeFile = createBitmap;
                    } catch (Throwable th4) {
                        Throwable th5 = th4;
                        parcelable = decodeFile;
                        th3 = th5;
                        FileLog.m13728e(th3);
                        if (VERSION.SDK_INT >= 26) {
                            intent2 = new Intent();
                            if (parcelable == null) {
                                intent2.putExtra("android.intent.extra.shortcut.ICON", parcelable);
                            } else if (user != null) {
                                if (chat != null) {
                                    if (ChatObject.isChannel(chat)) {
                                    }
                                    intent2.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", ShortcutIconResource.fromContext(ApplicationLoader.applicationContext, R.drawable.book_group));
                                }
                            } else if (user.bot) {
                                intent2.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", ShortcutIconResource.fromContext(ApplicationLoader.applicationContext, R.drawable.book_user));
                            } else {
                                intent2.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", ShortcutIconResource.fromContext(ApplicationLoader.applicationContext, R.drawable.book_bot));
                            }
                            intent2.putExtra("android.intent.extra.shortcut.INTENT", createIntrnalShortcutIntent);
                            intent2.putExtra("android.intent.extra.shortcut.NAME", obj);
                            intent2.putExtra("duplicate", false);
                            intent2.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
                            ApplicationLoader.applicationContext.sendBroadcast(intent2);
                        }
                        intent = new Builder(ApplicationLoader.applicationContext, "sdid_" + j).setShortLabel(obj).setIntent(createIntrnalShortcutIntent);
                        if (parcelable == null) {
                            intent.setIcon(Icon.createWithBitmap(parcelable));
                        } else if (user != null) {
                            if (chat != null) {
                                if (ChatObject.isChannel(chat)) {
                                }
                                intent.setIcon(Icon.createWithResource(ApplicationLoader.applicationContext, R.drawable.book_group));
                            }
                        } else if (user.bot) {
                            intent.setIcon(Icon.createWithResource(ApplicationLoader.applicationContext, R.drawable.book_user));
                        } else {
                            intent.setIcon(Icon.createWithResource(ApplicationLoader.applicationContext, R.drawable.book_bot));
                        }
                        ((ShortcutManager) ApplicationLoader.applicationContext.getSystemService(ShortcutManager.class)).requestPinShortcut(intent.build(), null);
                        return;
                    }
                }
                parcelable = decodeFile;
            }
            if (VERSION.SDK_INT >= 26) {
                intent = new Builder(ApplicationLoader.applicationContext, "sdid_" + j).setShortLabel(obj).setIntent(createIntrnalShortcutIntent);
                if (parcelable == null) {
                    intent.setIcon(Icon.createWithBitmap(parcelable));
                } else if (user != null) {
                    if (user.bot) {
                        intent.setIcon(Icon.createWithResource(ApplicationLoader.applicationContext, R.drawable.book_bot));
                    } else {
                        intent.setIcon(Icon.createWithResource(ApplicationLoader.applicationContext, R.drawable.book_user));
                    }
                } else if (chat != null) {
                    if (ChatObject.isChannel(chat) || chat.megagroup) {
                        intent.setIcon(Icon.createWithResource(ApplicationLoader.applicationContext, R.drawable.book_group));
                    } else {
                        intent.setIcon(Icon.createWithResource(ApplicationLoader.applicationContext, R.drawable.book_channel));
                    }
                }
                ((ShortcutManager) ApplicationLoader.applicationContext.getSystemService(ShortcutManager.class)).requestPinShortcut(intent.build(), null);
                return;
            }
            intent2 = new Intent();
            if (parcelable == null) {
                intent2.putExtra("android.intent.extra.shortcut.ICON", parcelable);
            } else if (user != null) {
                if (user.bot) {
                    intent2.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", ShortcutIconResource.fromContext(ApplicationLoader.applicationContext, R.drawable.book_bot));
                } else {
                    intent2.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", ShortcutIconResource.fromContext(ApplicationLoader.applicationContext, R.drawable.book_user));
                }
            } else if (chat != null) {
                if (ChatObject.isChannel(chat) || chat.megagroup) {
                    intent2.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", ShortcutIconResource.fromContext(ApplicationLoader.applicationContext, R.drawable.book_group));
                } else {
                    intent2.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", ShortcutIconResource.fromContext(ApplicationLoader.applicationContext, R.drawable.book_channel));
                }
            }
            intent2.putExtra("android.intent.extra.shortcut.INTENT", createIntrnalShortcutIntent);
            intent2.putExtra("android.intent.extra.shortcut.NAME", obj);
            intent2.putExtra("duplicate", false);
            intent2.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
            ApplicationLoader.applicationContext.sendBroadcast(intent2);
        }
    }

    public static boolean isBannedForever(int i) {
        return Math.abs(((long) i) - (System.currentTimeMillis() / 1000)) > 157680000;
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isGoogleMapsInstalled(final BaseFragment baseFragment) {
        try {
            ApplicationLoader.applicationContext.getPackageManager().getApplicationInfo("com.google.android.apps.maps", 0);
            return true;
        } catch (NameNotFoundException e) {
            if (baseFragment.getParentActivity() == null) {
                return false;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(baseFragment.getParentActivity());
            builder.setMessage("Install Google Maps?");
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        baseFragment.getParentActivity().startActivityForResult(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.google.android.apps.maps")), ChatActivity.startAllServices);
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                }
            });
            builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
            baseFragment.showDialog(builder.create());
            return false;
        }
    }

    public static boolean isInternalUri(Uri uri) {
        String path = uri.getPath();
        if (path == null) {
            return false;
        }
        while (true) {
            String readlink = Utilities.readlink(path);
            if (readlink != null && !readlink.equals(path)) {
                path = readlink;
            } else if (path != null) {
                try {
                    readlink = new File(path).getCanonicalPath();
                    if (readlink != null) {
                        path = readlink;
                    }
                } catch (Exception e) {
                    path.replace("/./", "/");
                }
            }
        }
        if (path != null) {
            readlink = new File(path).getCanonicalPath();
            if (readlink != null) {
                path = readlink;
            }
        }
        boolean z = path != null && path.toLowerCase().contains("/data/data/" + ApplicationLoader.applicationContext.getPackageName() + "/files");
        return z;
    }

    public static boolean isKeyboardShowed(View view) {
        if (view == null) {
            return false;
        }
        try {
            return ((InputMethodManager) view.getContext().getSystemService("input_method")).isActive(view);
        } catch (Throwable e) {
            FileLog.m13728e(e);
            return false;
        }
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isSmallTablet() {
        return ((float) Math.min(displaySize.x, displaySize.y)) / density <= 700.0f;
    }

    public static boolean isTablet() {
        if (isTablet == null) {
            isTablet = Boolean.valueOf(ApplicationLoader.applicationContext.getResources().getBoolean(R.bool.isTablet));
        }
        return isTablet.booleanValue();
    }

    public static boolean isWaitingForCall() {
        boolean z;
        synchronized (callLock) {
            z = waitingForCall;
        }
        return z;
    }

    public static boolean isWaitingForSms() {
        boolean z;
        synchronized (smsLock) {
            z = waitingForSms;
        }
        return z;
    }

    public static void lockOrientation(Activity activity) {
        if (activity != null && prevOrientation == -10) {
            try {
                prevOrientation = activity.getRequestedOrientation();
                WindowManager windowManager = (WindowManager) activity.getSystemService("window");
                if (windowManager != null && windowManager.getDefaultDisplay() != null) {
                    int rotation = windowManager.getDefaultDisplay().getRotation();
                    int i = activity.getResources().getConfiguration().orientation;
                    if (rotation == 3) {
                        if (i == 1) {
                            activity.setRequestedOrientation(1);
                        } else {
                            activity.setRequestedOrientation(8);
                        }
                    } else if (rotation == 1) {
                        if (i == 1) {
                            activity.setRequestedOrientation(9);
                        } else {
                            activity.setRequestedOrientation(0);
                        }
                    } else if (rotation == 0) {
                        if (i == 2) {
                            activity.setRequestedOrientation(0);
                        } else {
                            activity.setRequestedOrientation(1);
                        }
                    } else if (i == 2) {
                        activity.setRequestedOrientation(8);
                    } else {
                        activity.setRequestedOrientation(9);
                    }
                }
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
        }
    }

    public static long makeBroadcastId(int i) {
        return 4294967296L | (((long) i) & 4294967295L);
    }

    public static boolean needShowPasscode(boolean z) {
        boolean isWasInBackground = ForegroundDetector.getInstance().isWasInBackground(z);
        if (z) {
            ForegroundDetector.getInstance().resetBackgroundVar();
        }
        return UserConfig.passcodeHash.length() > 0 && isWasInBackground && (UserConfig.appLocked || (!(UserConfig.autoLockIn == 0 || UserConfig.lastPauseTime == 0 || UserConfig.appLocked || UserConfig.lastPauseTime + UserConfig.autoLockIn > ConnectionsManager.getInstance().getCurrentTime()) || ConnectionsManager.getInstance().getCurrentTime() + 5 < UserConfig.lastPauseTime));
    }

    public static String obtainLoginPhoneCall(String str) {
        Cursor query;
        Throwable e;
        if (!hasCallPermissions) {
            return null;
        }
        try {
            query = ApplicationLoader.applicationContext.getContentResolver().query(Calls.CONTENT_URI, new String[]{"number", "date"}, "type IN (3,1,5)", null, "date DESC LIMIT 5");
            while (query.moveToNext()) {
                try {
                    String string = query.getString(0);
                    long j = query.getLong(1);
                    FileLog.m13726e("number = " + string);
                    if (Math.abs(System.currentTimeMillis() - j) < 3600000 && checkPhonePattern(str, string)) {
                        if (query == null) {
                            return string;
                        }
                        query.close();
                        return string;
                    }
                } catch (Exception e2) {
                    e = e2;
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (Exception e3) {
            e = e3;
            query = null;
            try {
                FileLog.m13728e(e);
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (Throwable th) {
                e = th;
                if (query != null) {
                    query.close();
                }
                throw e;
            }
        } catch (Throwable th2) {
            e = th2;
            query = null;
            if (query != null) {
                query.close();
            }
            throw e;
        }
        return null;
    }

    public static void openForView(MessageObject messageObject, Activity activity) {
        String fileName = messageObject.getFileName();
        File file = (messageObject.messageOwner.attachPath == null || messageObject.messageOwner.attachPath.length() == 0) ? null : new File(messageObject.messageOwner.attachPath);
        File pathToMessage = (file == null || !file.exists()) ? FileLoader.getPathToMessage(messageObject.messageOwner) : file;
        if (pathToMessage != null && pathToMessage.exists()) {
            String mimeTypeFromExtension;
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setFlags(1);
            MimeTypeMap singleton = MimeTypeMap.getSingleton();
            int lastIndexOf = fileName.lastIndexOf(46);
            if (lastIndexOf != -1) {
                mimeTypeFromExtension = singleton.getMimeTypeFromExtension(fileName.substring(lastIndexOf + 1).toLowerCase());
                if (mimeTypeFromExtension == null) {
                    if (messageObject.type == 9 || messageObject.type == 0) {
                        mimeTypeFromExtension = messageObject.getDocument().mime_type;
                    }
                    if (mimeTypeFromExtension == null || mimeTypeFromExtension.length() == 0) {
                        mimeTypeFromExtension = null;
                    }
                }
            } else {
                mimeTypeFromExtension = null;
            }
            if (VERSION.SDK_INT >= 24) {
                intent.setDataAndType(FileProvider.m1845a(activity, "org.ir.talaeii.provider", pathToMessage), mimeTypeFromExtension != null ? mimeTypeFromExtension : "text/plain");
            } else {
                intent.setDataAndType(Uri.fromFile(pathToMessage), mimeTypeFromExtension != null ? mimeTypeFromExtension : "text/plain");
            }
            if (mimeTypeFromExtension != null) {
                try {
                    activity.startActivityForResult(intent, ChatActivity.startAllServices);
                    return;
                } catch (Exception e) {
                    if (VERSION.SDK_INT >= 24) {
                        intent.setDataAndType(FileProvider.m1845a(activity, "org.ir.talaeii.provider", pathToMessage), "text/plain");
                    } else {
                        intent.setDataAndType(Uri.fromFile(pathToMessage), "text/plain");
                    }
                    activity.startActivityForResult(intent, ChatActivity.startAllServices);
                    return;
                }
            }
            activity.startActivityForResult(intent, ChatActivity.startAllServices);
        }
    }

    public static void openForView(TLObject tLObject, Activity activity) {
        if (tLObject != null && activity != null) {
            String attachFileName = FileLoader.getAttachFileName(tLObject);
            File pathToAttach = FileLoader.getPathToAttach(tLObject, true);
            if (pathToAttach != null && pathToAttach.exists()) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setFlags(1);
                MimeTypeMap singleton = MimeTypeMap.getSingleton();
                int lastIndexOf = attachFileName.lastIndexOf(46);
                if (lastIndexOf != -1) {
                    attachFileName = singleton.getMimeTypeFromExtension(attachFileName.substring(lastIndexOf + 1).toLowerCase());
                    if (attachFileName == null) {
                        if (tLObject instanceof TLRPC$TL_document) {
                            attachFileName = ((TLRPC$TL_document) tLObject).mime_type;
                        }
                        if (attachFileName == null || attachFileName.length() == 0) {
                            attachFileName = null;
                        }
                    }
                } else {
                    attachFileName = null;
                }
                if (VERSION.SDK_INT >= 24) {
                    intent.setDataAndType(FileProvider.m1845a(activity, "org.ir.talaeii.provider", pathToAttach), attachFileName != null ? attachFileName : "text/plain");
                } else {
                    intent.setDataAndType(Uri.fromFile(pathToAttach), attachFileName != null ? attachFileName : "text/plain");
                }
                if (attachFileName != null) {
                    try {
                        activity.startActivityForResult(intent, ChatActivity.startAllServices);
                        return;
                    } catch (Exception e) {
                        if (VERSION.SDK_INT >= 24) {
                            intent.setDataAndType(FileProvider.m1845a(activity, "org.ir.talaeii.provider", pathToAttach), "text/plain");
                        } else {
                            intent.setDataAndType(Uri.fromFile(pathToAttach), "text/plain");
                        }
                        activity.startActivityForResult(intent, ChatActivity.startAllServices);
                        return;
                    }
                }
                activity.startActivityForResult(intent, ChatActivity.startAllServices);
            }
        }
    }

    private static void registerLoginContentObserver(boolean z, final String str) {
        if (z) {
            if (callLogContentObserver == null) {
                ContentResolver contentResolver = ApplicationLoader.applicationContext.getContentResolver();
                Uri uri = Calls.CONTENT_URI;
                ContentObserver c29732 = new ContentObserver(new Handler()) {
                    public boolean deliverSelfNotifications() {
                        return true;
                    }

                    public void onChange(boolean z) {
                        AndroidUtilities.registerLoginContentObserver(false, str);
                        AndroidUtilities.removeLoginPhoneCall(str, false);
                    }
                };
                callLogContentObserver = c29732;
                contentResolver.registerContentObserver(uri, true, c29732);
                Runnable c29743 = new Runnable() {
                    public void run() {
                        AndroidUtilities.unregisterRunnable = null;
                        AndroidUtilities.registerLoginContentObserver(false, str);
                    }
                };
                unregisterRunnable = c29743;
                runOnUIThread(c29743, 10000);
            }
        } else if (callLogContentObserver != null) {
            if (unregisterRunnable != null) {
                cancelRunOnUIThread(unregisterRunnable);
                unregisterRunnable = null;
            }
            try {
                ApplicationLoader.applicationContext.getContentResolver().unregisterContentObserver(callLogContentObserver);
            } catch (Exception e) {
            } finally {
                callLogContentObserver = null;
            }
        }
    }

    public static void removeAdjustResize(Activity activity, int i) {
        if (activity != null && !isTablet() && adjustOwnerClassGuid == i) {
            activity.getWindow().setSoftInputMode(32);
        }
    }

    public static void removeLoginPhoneCall(String str, boolean z) {
        Throwable e;
        if (hasCallPermissions) {
            Cursor query;
            try {
                Object obj;
                query = ApplicationLoader.applicationContext.getContentResolver().query(Calls.CONTENT_URI, new String[]{"_id", "number"}, "type IN (3,1,5)", null, "date DESC LIMIT 5");
                CharSequence string;
                do {
                    try {
                        if (!query.moveToNext()) {
                            obj = null;
                            break;
                        }
                        string = query.getString(1);
                        if (string.contains(str)) {
                            break;
                        }
                    } catch (Exception e2) {
                        e = e2;
                    }
                } while (!str.contains(string));
                ApplicationLoader.applicationContext.getContentResolver().delete(Calls.CONTENT_URI, "_id = ? ", new String[]{String.valueOf(query.getInt(0))});
                obj = 1;
                if (obj == null && z) {
                    registerLoginContentObserver(true, str);
                }
                if (query != null) {
                    query.close();
                }
            } catch (Exception e3) {
                e = e3;
                query = null;
                try {
                    FileLog.m13728e(e);
                    if (query != null) {
                        query.close();
                    }
                } catch (Throwable th) {
                    e = th;
                    if (query != null) {
                        query.close();
                    }
                    throw e;
                }
            } catch (Throwable th2) {
                e = th2;
                query = null;
                if (query != null) {
                    query.close();
                }
                throw e;
            }
        }
    }

    public static SpannableStringBuilder replaceTags(String str) {
        return replaceTags(str, 3);
    }

    public static SpannableStringBuilder replaceTags(String str, int i) {
        try {
            int indexOf;
            CharSequence stringBuilder = new StringBuilder(str);
            if ((i & 1) != 0) {
                while (true) {
                    indexOf = stringBuilder.indexOf("<br>");
                    if (indexOf != -1) {
                        stringBuilder.replace(indexOf, indexOf + 4, "\n");
                    } else {
                        while (true) {
                            stringBuilder.replace(indexOf, indexOf + 5, "\n");
                        }
                    }
                }
                indexOf = stringBuilder.indexOf("<br/>");
                if (indexOf == -1) {
                    break;
                }
                stringBuilder.replace(indexOf, indexOf + 5, "\n");
            }
            ArrayList arrayList = new ArrayList();
            if ((i & 2) != 0) {
                int indexOf2;
                while (true) {
                    indexOf2 = stringBuilder.indexOf("<b>");
                    if (indexOf2 == -1) {
                        break;
                    }
                    stringBuilder.replace(indexOf2, indexOf2 + 3, TtmlNode.ANONYMOUS_REGION_ID);
                    indexOf = stringBuilder.indexOf("</b>");
                    if (indexOf == -1) {
                        indexOf = stringBuilder.indexOf("<b>");
                    }
                    stringBuilder.replace(indexOf, indexOf + 4, TtmlNode.ANONYMOUS_REGION_ID);
                    arrayList.add(Integer.valueOf(indexOf2));
                    arrayList.add(Integer.valueOf(indexOf));
                }
                while (true) {
                    indexOf = stringBuilder.indexOf("**");
                    if (indexOf == -1) {
                        break;
                    }
                    stringBuilder.replace(indexOf, indexOf + 2, TtmlNode.ANONYMOUS_REGION_ID);
                    indexOf2 = stringBuilder.indexOf("**");
                    if (indexOf2 >= 0) {
                        stringBuilder.replace(indexOf2, indexOf2 + 2, TtmlNode.ANONYMOUS_REGION_ID);
                        arrayList.add(Integer.valueOf(indexOf));
                        arrayList.add(Integer.valueOf(indexOf2));
                    }
                }
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(stringBuilder);
            for (int i2 = 0; i2 < arrayList.size() / 2; i2++) {
                spannableStringBuilder.setSpan(new TypefaceSpan(getTypeface("fonts/rmedium.ttf")), ((Integer) arrayList.get(i2 * 2)).intValue(), ((Integer) arrayList.get((i2 * 2) + 1)).intValue(), 33);
            }
            return spannableStringBuilder;
        } catch (Throwable e) {
            FileLog.m13728e(e);
            return new SpannableStringBuilder(str);
        }
    }

    public static void requestAdjustResize(Activity activity, int i) {
        if (activity != null && !isTablet()) {
            activity.getWindow().setSoftInputMode(16);
            adjustOwnerClassGuid = i;
        }
    }

    private static double[] rgbToHsv(int i, int i2, int i3) {
        double d;
        double d2 = ((double) i) / 255.0d;
        double d3 = ((double) i2) / 255.0d;
        double d4 = ((double) i3) / 255.0d;
        double d5 = (d2 <= d3 || d2 <= d4) ? d3 > d4 ? d3 : d4 : d2;
        double d6 = (d2 >= d3 || d2 >= d4) ? d3 < d4 ? d3 : d4 : d2;
        double d7 = d5 - d6;
        double d8 = d5 == 0.0d ? 0.0d : d7 / d5;
        if (d5 == d6) {
            d = 0.0d;
        } else {
            if (d2 <= d3 || d2 <= d4) {
                d = d3 > d4 ? ((d4 - d2) / d7) + 2.0d : ((d2 - d3) / d7) + 4.0d;
            } else {
                d = ((double) (d3 < d4 ? 6 : 0)) + ((d3 - d4) / d7);
            }
            d /= 6.0d;
        }
        return new double[]{d, d8, d5};
    }

    public static void runOnUIThread(Runnable runnable) {
        runOnUIThread(runnable, 0);
    }

    public static void runOnUIThread(Runnable runnable, long j) {
        if (j == 0) {
            ApplicationLoader.applicationHandler.post(runnable);
        } else {
            ApplicationLoader.applicationHandler.postDelayed(runnable, j);
        }
    }

    public static int setMyLayerVersion(int i, int i2) {
        return (-65536 & i) | i2;
    }

    public static int setPeerLayerVersion(int i, int i2) {
        return (65535 & i) | (i2 << 16);
    }

    public static void setRectToRect(Matrix matrix, RectF rectF, RectF rectF2, int i, ScaleToFit scaleToFit) {
        float height;
        float width;
        if (i == 90 || i == 270) {
            height = rectF2.height() / rectF.width();
            width = rectF2.width() / rectF.height();
        } else {
            height = rectF2.width() / rectF.width();
            width = rectF2.height() / rectF.height();
        }
        if (scaleToFit != ScaleToFit.FILL) {
            if (height > width) {
                height = width;
            } else {
                width = height;
            }
        }
        float f = (-rectF.left) * height;
        float f2 = (-rectF.top) * width;
        matrix.setTranslate(rectF2.left, rectF2.top);
        if (i == 90) {
            matrix.preRotate(90.0f);
            matrix.preTranslate(BitmapDescriptorFactory.HUE_RED, -rectF2.width());
        } else if (i == 180) {
            matrix.preRotate(180.0f);
            matrix.preTranslate(-rectF2.width(), -rectF2.height());
        } else if (i == 270) {
            matrix.preRotate(270.0f);
            matrix.preTranslate(-rectF2.height(), BitmapDescriptorFactory.HUE_RED);
        }
        matrix.preScale(height, width);
        matrix.preTranslate(f, f2);
    }

    public static void setScrollViewEdgeEffectColor(ScrollView scrollView, int i) {
        if (VERSION.SDK_INT >= 21) {
            try {
                Field declaredField = ScrollView.class.getDeclaredField("mEdgeGlowTop");
                declaredField.setAccessible(true);
                EdgeEffect edgeEffect = (EdgeEffect) declaredField.get(scrollView);
                if (edgeEffect != null) {
                    edgeEffect.setColor(i);
                }
                declaredField = ScrollView.class.getDeclaredField("mEdgeGlowBottom");
                declaredField.setAccessible(true);
                edgeEffect = (EdgeEffect) declaredField.get(scrollView);
                if (edgeEffect != null) {
                    edgeEffect.setColor(i);
                }
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
        }
    }

    public static void setViewPagerEdgeEffectColor(ViewPager viewPager, int i) {
        if (VERSION.SDK_INT >= 21) {
            try {
                Field declaredField;
                EdgeEffect edgeEffect;
                Field declaredField2 = ViewPager.class.getDeclaredField("mLeftEdge");
                declaredField2.setAccessible(true);
                C0700i c0700i = (C0700i) declaredField2.get(viewPager);
                if (c0700i != null) {
                    declaredField = C0700i.class.getDeclaredField("a");
                    declaredField.setAccessible(true);
                    edgeEffect = (EdgeEffect) declaredField.get(c0700i);
                    if (edgeEffect != null) {
                        edgeEffect.setColor(i);
                    }
                }
                declaredField2 = ViewPager.class.getDeclaredField("mRightEdge");
                declaredField2.setAccessible(true);
                c0700i = (C0700i) declaredField2.get(viewPager);
                if (c0700i != null) {
                    declaredField = C0700i.class.getDeclaredField("a");
                    declaredField.setAccessible(true);
                    edgeEffect = (EdgeEffect) declaredField.get(c0700i);
                    if (edgeEffect != null) {
                        edgeEffect.setColor(i);
                    }
                }
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
        }
    }

    public static void setWaitingForCall(boolean z) {
        synchronized (callLock) {
            waitingForCall = z;
        }
    }

    public static void setWaitingForSms(boolean z) {
        synchronized (smsLock) {
            waitingForSms = z;
        }
    }

    public static void shakeView(final View view, final float f, final int i) {
        if (i == 6) {
            view.setTranslationX(BitmapDescriptorFactory.HUE_RED);
            return;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        Animator[] animatorArr = new Animator[1];
        animatorArr[0] = ObjectAnimator.ofFloat(view, "translationX", new float[]{(float) dp(f)});
        animatorSet.playTogether(animatorArr);
        animatorSet.setDuration(50);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                AndroidUtilities.shakeView(view, i == 5 ? BitmapDescriptorFactory.HUE_RED : -f, i + 1);
            }
        });
        animatorSet.start();
    }

    public static void showKeyboard(View view) {
        if (view != null) {
            try {
                ((InputMethodManager) view.getContext().getSystemService("input_method")).showSoftInput(view, 1);
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
        }
    }

    public static void showProxyAlert(Activity activity, final String str, final String str2, final String str3, final String str4) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(LocaleController.getString("Proxy", R.string.Proxy));
        StringBuilder stringBuilder = new StringBuilder(LocaleController.getString("EnableProxyAlert", R.string.EnableProxyAlert));
        stringBuilder.append("\n\n");
        stringBuilder.append(LocaleController.getString("UseProxyAddress", R.string.UseProxyAddress)).append(": ").append(str).append("\n");
        stringBuilder.append(LocaleController.getString("UseProxyPort", R.string.UseProxyPort)).append(": ").append(str2).append("\n");
        if (!TextUtils.isEmpty(str3)) {
            stringBuilder.append(LocaleController.getString("UseProxyUsername", R.string.UseProxyUsername)).append(": ").append(str3).append("\n");
        }
        if (!TextUtils.isEmpty(str4)) {
            stringBuilder.append(LocaleController.getString("UseProxyPassword", R.string.UseProxyPassword)).append(": ").append(str4).append("\n");
        }
        stringBuilder.append("\n").append(LocaleController.getString("EnableProxyAlert2", R.string.EnableProxyAlert2));
        builder.setMessage(stringBuilder.toString());
        builder.setPositiveButton(LocaleController.getString("ConnectingToProxyEnable", R.string.ConnectingToProxyEnable), new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
                edit.putBoolean("proxy_enabled", true);
                edit.putString("proxy_ip", str);
                int intValue = Utilities.parseInt(str2).intValue();
                edit.putInt("proxy_port", intValue);
                if (TextUtils.isEmpty(str4)) {
                    edit.remove("proxy_pass");
                } else {
                    edit.putString("proxy_pass", str4);
                }
                if (TextUtils.isEmpty(str3)) {
                    edit.remove("proxy_user");
                } else {
                    edit.putString("proxy_user", str3);
                }
                edit.commit();
                ConnectionsManager.native_setProxySettings(str, intValue, str3, str4);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.proxySettingsChanged, new Object[0]);
            }
        });
        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
        builder.show().setCanceledOnTouchOutside(true);
    }

    public static void uninstallShortcut(long j) {
        Chat chat = null;
        try {
            if (VERSION.SDK_INT >= 26) {
                ShortcutManager shortcutManager = (ShortcutManager) ApplicationLoader.applicationContext.getSystemService(ShortcutManager.class);
                List arrayList = new ArrayList();
                arrayList.add("sdid_" + j);
                shortcutManager.removeDynamicShortcuts(arrayList);
                return;
            }
            User user;
            int i = (int) j;
            int i2 = (int) (j >> 32);
            if (i == 0) {
                EncryptedChat encryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf(i2));
                if (encryptedChat != null) {
                    user = MessagesController.getInstance().getUser(Integer.valueOf(encryptedChat.user_id));
                } else {
                    return;
                }
            } else if (i > 0) {
                user = MessagesController.getInstance().getUser(Integer.valueOf(i));
            } else if (i < 0) {
                Chat chat2 = MessagesController.getInstance().getChat(Integer.valueOf(-i));
                user = null;
                chat = chat2;
            } else {
                return;
            }
            if (user != null || chat != null) {
                String formatName = user != null ? ContactsController.formatName(user.first_name, user.last_name) : chat.title;
                Intent intent = new Intent();
                intent.putExtra("android.intent.extra.shortcut.INTENT", createIntrnalShortcutIntent(j));
                intent.putExtra("android.intent.extra.shortcut.NAME", formatName);
                intent.putExtra("duplicate", false);
                intent.setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
                ApplicationLoader.applicationContext.sendBroadcast(intent);
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
    }

    public static void unlockOrientation(Activity activity) {
        if (activity != null) {
            try {
                if (prevOrientation != -10) {
                    activity.setRequestedOrientation(prevOrientation);
                    prevOrientation = -10;
                }
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
        }
    }

    public static void unregisterUpdates() {
    }
}
