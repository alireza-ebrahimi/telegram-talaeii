package org.telegram.messenger;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ShortcutInfo;
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
import android.provider.CallLog.Calls;
import android.support.v4.content.FileProvider;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.EdgeEffectCompat;
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
import com.android.internal.telephony.ITelephony;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.regex.Pattern;
import org.apache.commons.lang3.time.DateUtils;
import org.ir.talaeii.R;
import org.telegram.PhoneFormat.PhoneFormat;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$EncryptedChat;
import org.telegram.tgnet.TLRPC$TL_document;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
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
    private static boolean hasCallPermissions;
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

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void removeLoginPhoneCall(java.lang.String r10, boolean r11) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0004 in list [B:21:0x0072]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:43)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
*/
        /*
        r0 = hasCallPermissions;
        if (r0 != 0) goto L_0x0005;
    L_0x0004:
        return;
    L_0x0005:
        r6 = 0;
        r0 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        r0 = r0.getContentResolver();	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        r1 = android.provider.CallLog.Calls.CONTENT_URI;	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        r2 = 2;	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        r2 = new java.lang.String[r2];	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        r3 = 0;	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        r4 = "_id";	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        r2[r3] = r4;	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        r3 = 1;	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        r4 = "number";	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        r2[r3] = r4;	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        r3 = "type IN (3,1,5)";	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        r4 = 0;	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        r5 = "date DESC LIMIT 5";	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        r6 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        r9 = 0;	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
    L_0x0029:
        r0 = r6.moveToNext();	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        if (r0 == 0) goto L_0x005e;	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
    L_0x002f:
        r0 = 1;	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        r8 = r6.getString(r0);	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        r0 = r8.contains(r10);	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        if (r0 != 0) goto L_0x0040;	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
    L_0x003a:
        r0 = r10.contains(r8);	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        if (r0 == 0) goto L_0x0029;	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
    L_0x0040:
        r9 = 1;	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        r0 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        r0 = r0.getContentResolver();	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        r1 = android.provider.CallLog.Calls.CONTENT_URI;	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        r2 = "_id = ? ";	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        r3 = 1;	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        r3 = new java.lang.String[r3];	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        r4 = 0;	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        r5 = 0;	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        r5 = r6.getInt(r5);	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        r5 = java.lang.String.valueOf(r5);	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        r3[r4] = r5;	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        r0.delete(r1, r2, r3);	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
    L_0x005e:
        if (r9 != 0) goto L_0x0066;	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
    L_0x0060:
        if (r11 == 0) goto L_0x0066;	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
    L_0x0062:
        r0 = 1;	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        registerLoginContentObserver(r0, r10);	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
    L_0x0066:
        if (r6 == 0) goto L_0x0004;
    L_0x0068:
        r6.close();
        goto L_0x0004;
    L_0x006c:
        r7 = move-exception;
        org.telegram.messenger.FileLog.m94e(r7);	 Catch:{ Exception -> 0x006c, all -> 0x0076 }
        if (r6 == 0) goto L_0x0004;
    L_0x0072:
        r6.close();
        goto L_0x0004;
    L_0x0076:
        r0 = move-exception;
        if (r6 == 0) goto L_0x007c;
    L_0x0079:
        r6.close();
    L_0x007c:
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.AndroidUtilities.removeLoginPhoneCall(java.lang.String, boolean):void");
    }

    static {
        boolean z;
        WEB_URL = null;
        try {
            String GOOD_IRI_CHAR = "a-zA-Z0-9 -퟿豈-﷏ﷰ-￯";
            String IRI = "[a-zA-Z0-9 -퟿豈-﷏ﷰ-￯]([a-zA-Z0-9 -퟿豈-﷏ﷰ-￯\\-]{0,61}[a-zA-Z0-9 -퟿豈-﷏ﷰ-￯]){0,1}";
            String GOOD_GTLD_CHAR = "a-zA-Z -퟿豈-﷏ﷰ-￯";
            String GTLD = "[a-zA-Z -퟿豈-﷏ﷰ-￯]{2,63}";
            String HOST_NAME = "([a-zA-Z0-9 -퟿豈-﷏ﷰ-￯]([a-zA-Z0-9 -퟿豈-﷏ﷰ-￯\\-]{0,61}[a-zA-Z0-9 -퟿豈-﷏ﷰ-￯]){0,1}\\.)+[a-zA-Z -퟿豈-﷏ﷰ-￯]{2,63}";
            WEB_URL = Pattern.compile("((?:(http|https|Http|Https):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?(?:" + Pattern.compile("(([a-zA-Z0-9 -퟿豈-﷏ﷰ-￯]([a-zA-Z0-9 -퟿豈-﷏ﷰ-￯\\-]{0,61}[a-zA-Z0-9 -퟿豈-﷏ﷰ-￯]){0,1}\\.)+[a-zA-Z -퟿豈-﷏ﷰ-￯]{2,63}|" + Pattern.compile("((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9]))") + ")") + ")" + "(?:\\:\\d{1,5})?)" + "(\\/(?:(?:[" + "a-zA-Z0-9 -퟿豈-﷏ﷰ-￯" + "\\;\\/\\?\\:\\@\\&\\=\\#\\~" + "\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?" + "(?:\\b|$)");
        } catch (Throwable e) {
            FileLog.m94e(e);
        }
        checkDisplaySize(ApplicationLoader.applicationContext, null);
        if (VERSION.SDK_INT >= 23) {
            z = true;
        } else {
            z = false;
        }
        hasCallPermissions = z;
    }

    public static int[] calcDrawableColor(Drawable drawable) {
        int bitmapColor = -16777216;
        int[] result = new int[2];
        try {
            if (drawable instanceof BitmapDrawable) {
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                if (bitmap != null) {
                    Bitmap b = Bitmaps.createScaledBitmap(bitmap, 1, 1, true);
                    if (b != null) {
                        bitmapColor = b.getPixel(0, 0);
                        if (bitmap != b) {
                            b.recycle();
                        }
                    }
                }
            } else if (drawable instanceof ColorDrawable) {
                bitmapColor = ((ColorDrawable) drawable).getColor();
            }
        } catch (Throwable e) {
            FileLog.m94e(e);
        }
        double[] hsv = rgbToHsv((bitmapColor >> 16) & 255, (bitmapColor >> 8) & 255, bitmapColor & 255);
        hsv[1] = Math.min(1.0d, (hsv[1] + 0.05d) + (0.1d * (1.0d - hsv[1])));
        hsv[2] = Math.max(0.0d, hsv[2] * 0.65d);
        int[] rgb = hsvToRgb(hsv[0], hsv[1], hsv[2]);
        result[0] = Color.argb(102, rgb[0], rgb[1], rgb[2]);
        result[1] = Color.argb(136, rgb[0], rgb[1], rgb[2]);
        return result;
    }

    private static double[] rgbToHsv(int r, int g, int b) {
        double h;
        double rf = ((double) r) / 255.0d;
        double gf = ((double) g) / 255.0d;
        double bf = ((double) b) / 255.0d;
        double max = (rf <= gf || rf <= bf) ? gf > bf ? gf : bf : rf;
        double min = (rf >= gf || rf >= bf) ? gf < bf ? gf : bf : rf;
        double d = max - min;
        double s = max == 0.0d ? 0.0d : d / max;
        if (max == min) {
            h = 0.0d;
        } else {
            if (rf > gf && rf > bf) {
                h = ((gf - bf) / d) + ((double) (gf < bf ? 6 : 0));
            } else if (gf > bf) {
                h = ((bf - rf) / d) + 2.0d;
            } else {
                h = ((rf - gf) / d) + 4.0d;
            }
            h /= 6.0d;
        }
        return new double[]{h, s, max};
    }

    private static int[] hsvToRgb(double h, double s, double v) {
        double r = 0.0d;
        double g = 0.0d;
        double b = 0.0d;
        double i = (double) ((int) Math.floor(6.0d * h));
        double f = (6.0d * h) - i;
        double p = v * (1.0d - s);
        double q = v * (1.0d - (f * s));
        double t = v * (1.0d - ((1.0d - f) * s));
        switch (((int) i) % 6) {
            case 0:
                r = v;
                g = t;
                b = p;
                break;
            case 1:
                r = q;
                g = v;
                b = p;
                break;
            case 2:
                r = p;
                g = v;
                b = t;
                break;
            case 3:
                r = p;
                g = q;
                b = v;
                break;
            case 4:
                r = t;
                g = p;
                b = v;
                break;
            case 5:
                r = v;
                g = p;
                b = q;
                break;
        }
        return new int[]{(int) (255.0d * r), (int) (255.0d * g), (int) (255.0d * b)};
    }

    public static void requestAdjustResize(Activity activity, int classGuid) {
        if (activity != null && !isTablet()) {
            activity.getWindow().setSoftInputMode(16);
            adjustOwnerClassGuid = classGuid;
        }
    }

    public static void removeAdjustResize(Activity activity, int classGuid) {
        if (activity != null && !isTablet() && adjustOwnerClassGuid == classGuid) {
            activity.getWindow().setSoftInputMode(32);
        }
    }

    public static boolean isGoogleMapsInstalled(BaseFragment fragment) {
        try {
            ApplicationLoader.applicationContext.getPackageManager().getApplicationInfo("com.google.android.apps.maps", 0);
            return true;
        } catch (NameNotFoundException e) {
            if (fragment.getParentActivity() == null) {
                return false;
            }
            Builder builder = new Builder(fragment.getParentActivity());
            builder.setMessage("Install Google Maps?");
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new AndroidUtilities$1(fragment));
            builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
            fragment.showDialog(builder.create());
            return false;
        }
    }

    public static boolean isInternalUri(Uri uri) {
        String pathString = uri.getPath();
        if (pathString == null) {
            return false;
        }
        while (true) {
            String path;
            String newPath = Utilities.readlink(pathString);
            if (newPath != null && !newPath.equals(pathString)) {
                pathString = newPath;
            } else if (pathString != null) {
                try {
                    path = new File(pathString).getCanonicalPath();
                    if (path != null) {
                        pathString = path;
                    }
                } catch (Exception e) {
                    pathString.replace("/./", "/");
                }
            }
        }
        if (pathString != null) {
            path = new File(pathString).getCanonicalPath();
            if (path != null) {
                pathString = path;
            }
        }
        if (pathString == null || !pathString.toLowerCase().contains("/data/data/" + ApplicationLoader.applicationContext.getPackageName() + "/files")) {
            return false;
        }
        return true;
    }

    public static void lockOrientation(Activity activity) {
        if (activity != null && prevOrientation == -10) {
            try {
                prevOrientation = activity.getRequestedOrientation();
                WindowManager manager = (WindowManager) activity.getSystemService("window");
                if (manager != null && manager.getDefaultDisplay() != null) {
                    int rotation = manager.getDefaultDisplay().getRotation();
                    int orientation = activity.getResources().getConfiguration().orientation;
                    if (rotation == 3) {
                        if (orientation == 1) {
                            activity.setRequestedOrientation(1);
                        } else {
                            activity.setRequestedOrientation(8);
                        }
                    } else if (rotation == 1) {
                        if (orientation == 1) {
                            activity.setRequestedOrientation(9);
                        } else {
                            activity.setRequestedOrientation(0);
                        }
                    } else if (rotation == 0) {
                        if (orientation == 2) {
                            activity.setRequestedOrientation(0);
                        } else {
                            activity.setRequestedOrientation(1);
                        }
                    } else if (orientation == 2) {
                        activity.setRequestedOrientation(8);
                    } else {
                        activity.setRequestedOrientation(9);
                    }
                }
            } catch (Throwable e) {
                FileLog.m94e(e);
            }
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
                FileLog.m94e(e);
            }
        }
    }

    public static Typeface getTypeface(String assetPath) {
        Typeface typeface = null;
        if (!ApplicationLoader.USE_DEVICE_FONT) {
            assetPath = FontUtil.getDeafultFontAddress();
            synchronized (typefaceCache) {
                if (!typefaceCache.containsKey(assetPath)) {
                    try {
                        typefaceCache.put(assetPath, Typeface.createFromAsset(ApplicationLoader.applicationContext.getAssets(), assetPath));
                    } catch (Exception e) {
                        FileLog.m92e("Could not get typeface '" + assetPath + "' because " + e.getMessage());
                    }
                }
                typeface = (Typeface) typefaceCache.get(assetPath);
            }
        }
        return typeface;
    }

    public static boolean isWaitingForSms() {
        boolean value;
        synchronized (smsLock) {
            value = waitingForSms;
        }
        return value;
    }

    public static void setWaitingForSms(boolean value) {
        synchronized (smsLock) {
            waitingForSms = value;
        }
    }

    public static boolean isWaitingForCall() {
        boolean value;
        synchronized (callLock) {
            value = waitingForCall;
        }
        return value;
    }

    public static void setWaitingForCall(boolean value) {
        synchronized (callLock) {
            waitingForCall = value;
        }
    }

    public static void showKeyboard(View view) {
        if (view != null) {
            try {
                ((InputMethodManager) view.getContext().getSystemService("input_method")).showSoftInput(view, 1);
            } catch (Throwable e) {
                FileLog.m94e(e);
            }
        }
    }

    public static boolean isKeyboardShowed(View view) {
        boolean z = false;
        if (view != null) {
            try {
                z = ((InputMethodManager) view.getContext().getSystemService("input_method")).isActive(view);
            } catch (Throwable e) {
                FileLog.m94e(e);
            }
        }
        return z;
    }

    public static void hideKeyboard(View view) {
        if (view != null) {
            try {
                InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService("input_method");
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            } catch (Throwable e) {
                FileLog.m94e(e);
            }
        }
    }

    public static File getCacheDir() {
        File file;
        String state = null;
        try {
            state = Environment.getExternalStorageState();
        } catch (Throwable e) {
            FileLog.m94e(e);
        }
        if (state == null || state.startsWith("mounted")) {
            try {
                file = ApplicationLoader.applicationContext.getExternalCacheDir();
                if (file != null) {
                    return file;
                }
            } catch (Throwable e2) {
                FileLog.m94e(e2);
            }
        }
        try {
            file = ApplicationLoader.applicationContext.getCacheDir();
            if (file != null) {
                return file;
            }
        } catch (Throwable e22) {
            FileLog.m94e(e22);
        }
        return new File("");
    }

    public static int dp(float value) {
        if (value == 0.0f) {
            return 0;
        }
        return (int) Math.ceil((double) (density * value));
    }

    public static int dp2(float value) {
        if (value == 0.0f) {
            return 0;
        }
        return (int) Math.floor((double) (density * value));
    }

    public static int compare(int lhs, int rhs) {
        if (lhs == rhs) {
            return 0;
        }
        if (lhs > rhs) {
            return 1;
        }
        return -1;
    }

    public static float dpf2(float value) {
        if (value == 0.0f) {
            return 0.0f;
        }
        return density * value;
    }

    public static void checkDisplaySize(Context context, Configuration newConfiguration) {
        boolean z = true;
        try {
            int newSize;
            density = context.getResources().getDisplayMetrics().density;
            Configuration configuration = newConfiguration;
            if (configuration == null) {
                configuration = context.getResources().getConfiguration();
            }
            if (configuration.keyboard == 1 || configuration.hardKeyboardHidden != 1) {
                z = false;
            }
            usingHardwareInput = z;
            WindowManager manager = (WindowManager) context.getSystemService("window");
            if (manager != null) {
                Display display = manager.getDefaultDisplay();
                if (display != null) {
                    display.getMetrics(displayMetrics);
                    display.getSize(displaySize);
                }
            }
            if (configuration.screenWidthDp != 0) {
                newSize = (int) Math.ceil((double) (((float) configuration.screenWidthDp) * density));
                if (Math.abs(displaySize.x - newSize) > 3) {
                    displaySize.x = newSize;
                }
            }
            if (configuration.screenHeightDp != 0) {
                newSize = (int) Math.ceil((double) (((float) configuration.screenHeightDp) * density));
                if (Math.abs(displaySize.y - newSize) > 3) {
                    displaySize.y = newSize;
                }
            }
            if (roundMessageSize == 0) {
                if (isTablet()) {
                    roundMessageSize = (int) (((float) getMinTabletSide()) * 0.6f);
                } else {
                    roundMessageSize = (int) (((float) Math.min(displaySize.x, displaySize.y)) * 0.6f);
                }
            }
            FileLog.m92e("display size = " + displaySize.x + " " + displaySize.y + " " + displayMetrics.xdpi + "x" + displayMetrics.ydpi);
        } catch (Throwable e) {
            FileLog.m94e(e);
        }
    }

    public static float getPixelsInCM(float cm, boolean isX) {
        return (isX ? displayMetrics.xdpi : displayMetrics.ydpi) * (cm / 2.54f);
    }

    public static long makeBroadcastId(int id) {
        return 4294967296L | (((long) id) & 4294967295L);
    }

    public static int getMyLayerVersion(int layer) {
        return SupportMenu.USER_MASK & layer;
    }

    public static int getPeerLayerVersion(int layer) {
        return (layer >> 16) & SupportMenu.USER_MASK;
    }

    public static int setMyLayerVersion(int layer, int version) {
        return (SupportMenu.CATEGORY_MASK & layer) | version;
    }

    public static int setPeerLayerVersion(int layer, int version) {
        return (SupportMenu.USER_MASK & layer) | (version << 16);
    }

    public static void runOnUIThread(Runnable runnable) {
        runOnUIThread(runnable, 0);
    }

    public static void runOnUIThread(Runnable runnable, long delay) {
        if (delay == 0) {
            ApplicationLoader.applicationHandler.post(runnable);
        } else {
            ApplicationLoader.applicationHandler.postDelayed(runnable, delay);
        }
    }

    public static void cancelRunOnUIThread(Runnable runnable) {
        ApplicationLoader.applicationHandler.removeCallbacks(runnable);
    }

    public static boolean isTablet() {
        if (isTablet == null) {
            isTablet = Boolean.valueOf(ApplicationLoader.applicationContext.getResources().getBoolean(R.bool.isTablet));
        }
        return isTablet.booleanValue();
    }

    public static boolean isSmallTablet() {
        return ((float) Math.min(displaySize.x, displaySize.y)) / density <= 700.0f;
    }

    public static int getMinTabletSide() {
        int leftSide;
        if (isSmallTablet()) {
            int smallSide = Math.min(displaySize.x, displaySize.y);
            int maxSide = Math.max(displaySize.x, displaySize.y);
            leftSide = (maxSide * 35) / 100;
            if (leftSide < dp(320.0f)) {
                leftSide = dp(320.0f);
            }
            return Math.min(smallSide, maxSide - leftSide);
        }
        smallSide = Math.min(displaySize.x, displaySize.y);
        leftSide = (smallSide * 35) / 100;
        if (leftSide < dp(320.0f)) {
            leftSide = dp(320.0f);
        }
        return smallSide - leftSide;
    }

    public static int getPhotoSize() {
        if (photoSize == null) {
            photoSize = Integer.valueOf(1280);
        }
        return photoSize.intValue();
    }

    public static void endIncomingCall() {
        if (hasCallPermissions) {
            try {
                TelephonyManager tm = (TelephonyManager) ApplicationLoader.applicationContext.getSystemService("phone");
                Method m = Class.forName(tm.getClass().getName()).getDeclaredMethod("getITelephony", new Class[0]);
                m.setAccessible(true);
                ITelephony telephonyService = (ITelephony) m.invoke(tm, new Object[0]);
                telephonyService = (ITelephony) m.invoke(tm, new Object[0]);
                telephonyService.silenceRinger();
                telephonyService.endCall();
            } catch (Exception e) {
                FileLog.m93e("tmessages", e);
            }
        }
    }

    public static boolean checkPhonePattern(String pattern, String phone) {
        if (TextUtils.isEmpty(pattern) || pattern.equals("*")) {
            return true;
        }
        String[] args = pattern.split("\\*");
        phone = PhoneFormat.stripExceptNumbers(phone);
        int checkStart = 0;
        for (String arg : args) {
            if (!TextUtils.isEmpty(arg)) {
                int index = phone.indexOf(arg, checkStart);
                if (index == -1) {
                    return false;
                }
                checkStart = index + arg.length();
            }
        }
        return true;
    }

    public static String obtainLoginPhoneCall(String pattern) {
        if (!hasCallPermissions) {
            return null;
        }
        Cursor cursor = null;
        try {
            cursor = ApplicationLoader.applicationContext.getContentResolver().query(Calls.CONTENT_URI, new String[]{"number", "date"}, "type IN (3,1,5)", null, "date DESC LIMIT 5");
            while (cursor.moveToNext()) {
                String number = cursor.getString(0);
                long date = cursor.getLong(1);
                FileLog.m92e("number = " + number);
                if (Math.abs(System.currentTimeMillis() - date) < DateUtils.MILLIS_PER_HOUR && checkPhonePattern(pattern, number)) {
                    if (cursor == null) {
                        return number;
                    }
                    cursor.close();
                    return number;
                }
            }
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable e) {
            FileLog.m94e(e);
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    private static void registerLoginContentObserver(boolean shouldRegister, String number) {
        if (shouldRegister) {
            if (callLogContentObserver == null) {
                ContentResolver contentResolver = ApplicationLoader.applicationContext.getContentResolver();
                Uri uri = Calls.CONTENT_URI;
                ContentObserver androidUtilities$2 = new AndroidUtilities$2(new Handler(), number);
                callLogContentObserver = androidUtilities$2;
                contentResolver.registerContentObserver(uri, true, androidUtilities$2);
                Runnable androidUtilities$3 = new AndroidUtilities$3(number);
                unregisterRunnable = androidUtilities$3;
                runOnUIThread(androidUtilities$3, 10000);
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

    private static Intent createIntrnalShortcutIntent(long did) {
        Intent shortcutIntent = new Intent(ApplicationLoader.applicationContext, OpenChatReceiver.class);
        int lower_id = (int) did;
        int high_id = (int) (did >> 32);
        if (lower_id == 0) {
            shortcutIntent.putExtra("encId", high_id);
            if (MessagesController.getInstance().getEncryptedChat(Integer.valueOf(high_id)) == null) {
                return null;
            }
        } else if (lower_id > 0) {
            shortcutIntent.putExtra("userId", lower_id);
        } else if (lower_id >= 0) {
            return null;
        } else {
            shortcutIntent.putExtra("chatId", -lower_id);
        }
        shortcutIntent.setAction("com.tmessages.openchat" + did);
        shortcutIntent.addFlags(ConnectionsManager.FileTypeFile);
        return shortcutIntent;
    }

    public static void installShortcut(long did) {
        try {
            Intent shortcutIntent = createIntrnalShortcutIntent(did);
            int lower_id = (int) did;
            int high_id = (int) (did >> 32);
            User user = null;
            TLRPC$Chat chat = null;
            if (lower_id == 0) {
                TLRPC$EncryptedChat encryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf(high_id));
                if (encryptedChat != null) {
                    user = MessagesController.getInstance().getUser(Integer.valueOf(encryptedChat.user_id));
                } else {
                    return;
                }
            } else if (lower_id > 0) {
                user = MessagesController.getInstance().getUser(Integer.valueOf(lower_id));
            } else if (lower_id < 0) {
                chat = MessagesController.getInstance().getChat(Integer.valueOf(-lower_id));
            } else {
                return;
            }
            if (user != null || chat != null) {
                String name;
                TLObject photo = null;
                boolean selfUser = false;
                if (user == null) {
                    name = chat.title;
                    if (chat.photo != null) {
                        photo = chat.photo.photo_small;
                    }
                } else if (UserObject.isUserSelf(user)) {
                    name = LocaleController.getString("SavedMessages", R.string.SavedMessages);
                    selfUser = true;
                } else {
                    name = ContactsController.formatName(user.first_name, user.last_name);
                    if (user.photo != null) {
                        photo = user.photo.photo_small;
                    }
                }
                Bitmap bitmap = null;
                if (selfUser || photo != null) {
                    if (!selfUser) {
                        try {
                            bitmap = BitmapFactory.decodeFile(FileLoader.getPathToAttach(photo, true).toString());
                        } catch (Throwable e) {
                            FileLog.m94e(e);
                        }
                    }
                    if (selfUser || bitmap != null) {
                        int size = dp(58.0f);
                        Bitmap result = Bitmap.createBitmap(size, size, Config.ARGB_8888);
                        result.eraseColor(0);
                        Canvas canvas = new Canvas(result);
                        if (selfUser) {
                            AvatarDrawable avatarDrawable = new AvatarDrawable(user);
                            avatarDrawable.setSavedMessages(1);
                            avatarDrawable.setBounds(0, 0, size, size);
                            avatarDrawable.draw(canvas);
                        } else {
                            Shader bitmapShader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
                            if (roundPaint == null) {
                                roundPaint = new Paint(1);
                                bitmapRect = new RectF();
                            }
                            float scale = ((float) size) / ((float) bitmap.getWidth());
                            canvas.save();
                            canvas.scale(scale, scale);
                            roundPaint.setShader(bitmapShader);
                            bitmapRect.set(0.0f, 0.0f, (float) bitmap.getWidth(), (float) bitmap.getHeight());
                            canvas.drawRoundRect(bitmapRect, (float) bitmap.getWidth(), (float) bitmap.getHeight(), roundPaint);
                            canvas.restore();
                        }
                        Drawable drawable = ApplicationLoader.applicationContext.getResources().getDrawable(R.drawable.book_logo);
                        int w = dp(15.0f);
                        int left = (size - w) - dp(2.0f);
                        int top = (size - w) - dp(2.0f);
                        drawable.setBounds(left, top, left + w, top + w);
                        drawable.draw(canvas);
                        try {
                            canvas.setBitmap(null);
                        } catch (Exception e2) {
                        }
                        bitmap = result;
                    }
                }
                if (VERSION.SDK_INT >= 26) {
                    ShortcutInfo.Builder pinShortcutInfo = new ShortcutInfo.Builder(ApplicationLoader.applicationContext, "sdid_" + did).setShortLabel(name).setIntent(shortcutIntent);
                    if (bitmap != null) {
                        pinShortcutInfo.setIcon(Icon.createWithBitmap(bitmap));
                    } else if (user != null) {
                        if (user.bot) {
                            pinShortcutInfo.setIcon(Icon.createWithResource(ApplicationLoader.applicationContext, R.drawable.book_bot));
                        } else {
                            pinShortcutInfo.setIcon(Icon.createWithResource(ApplicationLoader.applicationContext, R.drawable.book_user));
                        }
                    } else if (chat != null) {
                        if (!ChatObject.isChannel(chat) || chat.megagroup) {
                            pinShortcutInfo.setIcon(Icon.createWithResource(ApplicationLoader.applicationContext, R.drawable.book_group));
                        } else {
                            pinShortcutInfo.setIcon(Icon.createWithResource(ApplicationLoader.applicationContext, R.drawable.book_channel));
                        }
                    }
                    ((ShortcutManager) ApplicationLoader.applicationContext.getSystemService(ShortcutManager.class)).requestPinShortcut(pinShortcutInfo.build(), null);
                    return;
                }
                Intent addIntent = new Intent();
                if (bitmap != null) {
                    addIntent.putExtra("android.intent.extra.shortcut.ICON", bitmap);
                } else if (user != null) {
                    if (user.bot) {
                        addIntent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", ShortcutIconResource.fromContext(ApplicationLoader.applicationContext, R.drawable.book_bot));
                    } else {
                        addIntent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", ShortcutIconResource.fromContext(ApplicationLoader.applicationContext, R.drawable.book_user));
                    }
                } else if (chat != null) {
                    if (!ChatObject.isChannel(chat) || chat.megagroup) {
                        addIntent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", ShortcutIconResource.fromContext(ApplicationLoader.applicationContext, R.drawable.book_group));
                    } else {
                        addIntent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", ShortcutIconResource.fromContext(ApplicationLoader.applicationContext, R.drawable.book_channel));
                    }
                }
                addIntent.putExtra("android.intent.extra.shortcut.INTENT", shortcutIntent);
                addIntent.putExtra("android.intent.extra.shortcut.NAME", name);
                addIntent.putExtra("duplicate", false);
                addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
                ApplicationLoader.applicationContext.sendBroadcast(addIntent);
            }
        } catch (Throwable e3) {
            FileLog.m94e(e3);
        }
    }

    public static void uninstallShortcut(long did) {
        try {
            if (VERSION.SDK_INT >= 26) {
                ShortcutManager shortcutManager = (ShortcutManager) ApplicationLoader.applicationContext.getSystemService(ShortcutManager.class);
                ArrayList<String> arrayList = new ArrayList();
                arrayList.add("sdid_" + did);
                shortcutManager.removeDynamicShortcuts(arrayList);
                return;
            }
            int lower_id = (int) did;
            int high_id = (int) (did >> 32);
            User user = null;
            TLRPC$Chat chat = null;
            if (lower_id == 0) {
                TLRPC$EncryptedChat encryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf(high_id));
                if (encryptedChat != null) {
                    user = MessagesController.getInstance().getUser(Integer.valueOf(encryptedChat.user_id));
                } else {
                    return;
                }
            } else if (lower_id > 0) {
                user = MessagesController.getInstance().getUser(Integer.valueOf(lower_id));
            } else if (lower_id < 0) {
                chat = MessagesController.getInstance().getChat(Integer.valueOf(-lower_id));
            } else {
                return;
            }
            if (user != null || chat != null) {
                String name;
                if (user != null) {
                    name = ContactsController.formatName(user.first_name, user.last_name);
                } else {
                    name = chat.title;
                }
                Intent addIntent = new Intent();
                addIntent.putExtra("android.intent.extra.shortcut.INTENT", createIntrnalShortcutIntent(did));
                addIntent.putExtra("android.intent.extra.shortcut.NAME", name);
                addIntent.putExtra("duplicate", false);
                addIntent.setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
                ApplicationLoader.applicationContext.sendBroadcast(addIntent);
            }
        } catch (Throwable e) {
            FileLog.m94e(e);
        }
    }

    public static int getViewInset(View view) {
        int i = 0;
        if (!(view == null || VERSION.SDK_INT < 21 || view.getHeight() == displaySize.y || view.getHeight() == displaySize.y - statusBarHeight)) {
            try {
                if (mAttachInfoField == null) {
                    mAttachInfoField = View.class.getDeclaredField("mAttachInfo");
                    mAttachInfoField.setAccessible(true);
                }
                Object mAttachInfo = mAttachInfoField.get(view);
                if (mAttachInfo != null) {
                    if (mStableInsetsField == null) {
                        mStableInsetsField = mAttachInfo.getClass().getDeclaredField("mStableInsets");
                        mStableInsetsField.setAccessible(true);
                    }
                    i = ((Rect) mStableInsetsField.get(mAttachInfo)).bottom;
                }
            } catch (Throwable e) {
                FileLog.m94e(e);
            }
        }
        return i;
    }

    public static Point getRealScreenSize() {
        Point size = new Point();
        try {
            WindowManager windowManager = (WindowManager) ApplicationLoader.applicationContext.getSystemService("window");
            if (VERSION.SDK_INT >= 17) {
                windowManager.getDefaultDisplay().getRealSize(size);
            } else {
                try {
                    size.set(((Integer) Display.class.getMethod("getRawWidth", new Class[0]).invoke(windowManager.getDefaultDisplay(), new Object[0])).intValue(), ((Integer) Display.class.getMethod("getRawHeight", new Class[0]).invoke(windowManager.getDefaultDisplay(), new Object[0])).intValue());
                } catch (Throwable e) {
                    size.set(windowManager.getDefaultDisplay().getWidth(), windowManager.getDefaultDisplay().getHeight());
                    FileLog.m94e(e);
                }
            }
        } catch (Throwable e2) {
            FileLog.m94e(e2);
        }
        return size;
    }

    public static CharSequence getTrimmedString(CharSequence src) {
        if (!(src == null || src.length() == 0)) {
            while (src.length() > 0 && (src.charAt(0) == '\n' || src.charAt(0) == ' ')) {
                src = src.subSequence(1, src.length());
            }
            while (src.length() > 0 && (src.charAt(src.length() - 1) == '\n' || src.charAt(src.length() - 1) == ' ')) {
                src = src.subSequence(0, src.length() - 1);
            }
        }
        return src;
    }

    public static void setViewPagerEdgeEffectColor(ViewPager viewPager, int color) {
        if (VERSION.SDK_INT >= 21) {
            try {
                EdgeEffect mEdgeEffect;
                Field field = ViewPager.class.getDeclaredField("mLeftEdge");
                field.setAccessible(true);
                EdgeEffectCompat mLeftEdge = (EdgeEffectCompat) field.get(viewPager);
                if (mLeftEdge != null) {
                    field = EdgeEffectCompat.class.getDeclaredField("mEdgeEffect");
                    field.setAccessible(true);
                    mEdgeEffect = (EdgeEffect) field.get(mLeftEdge);
                    if (mEdgeEffect != null) {
                        mEdgeEffect.setColor(color);
                    }
                }
                field = ViewPager.class.getDeclaredField("mRightEdge");
                field.setAccessible(true);
                EdgeEffectCompat mRightEdge = (EdgeEffectCompat) field.get(viewPager);
                if (mRightEdge != null) {
                    field = EdgeEffectCompat.class.getDeclaredField("mEdgeEffect");
                    field.setAccessible(true);
                    mEdgeEffect = (EdgeEffect) field.get(mRightEdge);
                    if (mEdgeEffect != null) {
                        mEdgeEffect.setColor(color);
                    }
                }
            } catch (Throwable e) {
                FileLog.m94e(e);
            }
        }
    }

    public static void setScrollViewEdgeEffectColor(ScrollView scrollView, int color) {
        if (VERSION.SDK_INT >= 21) {
            try {
                Field field = ScrollView.class.getDeclaredField("mEdgeGlowTop");
                field.setAccessible(true);
                EdgeEffect mEdgeGlowTop = (EdgeEffect) field.get(scrollView);
                if (mEdgeGlowTop != null) {
                    mEdgeGlowTop.setColor(color);
                }
                field = ScrollView.class.getDeclaredField("mEdgeGlowBottom");
                field.setAccessible(true);
                EdgeEffect mEdgeGlowBottom = (EdgeEffect) field.get(scrollView);
                if (mEdgeGlowBottom != null) {
                    mEdgeGlowBottom.setColor(color);
                }
            } catch (Throwable e) {
                FileLog.m94e(e);
            }
        }
    }

    @SuppressLint({"NewApi"})
    public static void clearDrawableAnimation(View view) {
        if (VERSION.SDK_INT >= 21 && view != null) {
            Drawable drawable;
            if (view instanceof ListView) {
                drawable = ((ListView) view).getSelector();
                if (drawable != null) {
                    drawable.setState(StateSet.NOTHING);
                    return;
                }
                return;
            }
            drawable = view.getBackground();
            if (drawable != null) {
                drawable.setState(StateSet.NOTHING);
                drawable.jumpToCurrentState();
            }
        }
    }

    public static SpannableStringBuilder replaceTags(String str) {
        return replaceTags(str, 3);
    }

    public static SpannableStringBuilder replaceTags(String str, int flag) {
        try {
            int start;
            StringBuilder stringBuilder = new StringBuilder(str);
            if ((flag & 1) != 0) {
                while (true) {
                    start = stringBuilder.indexOf("<br>");
                    if (start != -1) {
                        stringBuilder.replace(start, start + 4, LogCollector.LINE_SEPARATOR);
                    } else {
                        while (true) {
                            stringBuilder.replace(start, start + 5, LogCollector.LINE_SEPARATOR);
                        }
                    }
                }
                start = stringBuilder.indexOf("<br/>");
                if (start == -1) {
                    break;
                }
                stringBuilder.replace(start, start + 5, LogCollector.LINE_SEPARATOR);
            }
            ArrayList<Integer> bolds = new ArrayList();
            if ((flag & 2) != 0) {
                int end;
                while (true) {
                    start = stringBuilder.indexOf("<b>");
                    if (start == -1) {
                        break;
                    }
                    stringBuilder.replace(start, start + 3, "");
                    end = stringBuilder.indexOf("</b>");
                    if (end == -1) {
                        end = stringBuilder.indexOf("<b>");
                    }
                    stringBuilder.replace(end, end + 4, "");
                    bolds.add(Integer.valueOf(start));
                    bolds.add(Integer.valueOf(end));
                }
                while (true) {
                    start = stringBuilder.indexOf("**");
                    if (start == -1) {
                        break;
                    }
                    stringBuilder.replace(start, start + 2, "");
                    end = stringBuilder.indexOf("**");
                    if (end >= 0) {
                        stringBuilder.replace(end, end + 2, "");
                        bolds.add(Integer.valueOf(start));
                        bolds.add(Integer.valueOf(end));
                    }
                }
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(stringBuilder);
            for (int a = 0; a < bolds.size() / 2; a++) {
                spannableStringBuilder.setSpan(new TypefaceSpan(getTypeface("fonts/rmedium.ttf")), ((Integer) bolds.get(a * 2)).intValue(), ((Integer) bolds.get((a * 2) + 1)).intValue(), 33);
            }
            return spannableStringBuilder;
        } catch (Throwable e) {
            FileLog.m94e(e);
            return new SpannableStringBuilder(str);
        }
    }

    public static boolean needShowPasscode(boolean reset) {
        boolean wasInBackground = ForegroundDetector.getInstance().isWasInBackground(reset);
        if (reset) {
            ForegroundDetector.getInstance().resetBackgroundVar();
        }
        return UserConfig.passcodeHash.length() > 0 && wasInBackground && (UserConfig.appLocked || (!(UserConfig.autoLockIn == 0 || UserConfig.lastPauseTime == 0 || UserConfig.appLocked || UserConfig.lastPauseTime + UserConfig.autoLockIn > ConnectionsManager.getInstance().getCurrentTime()) || ConnectionsManager.getInstance().getCurrentTime() + 5 < UserConfig.lastPauseTime));
    }

    public static void shakeView(View view, float x, int num) {
        if (num == 6) {
            view.setTranslationX(0.0f);
            return;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        Animator[] animatorArr = new Animator[1];
        animatorArr[0] = ObjectAnimator.ofFloat(view, "translationX", new float[]{(float) dp(x)});
        animatorSet.playTogether(animatorArr);
        animatorSet.setDuration(50);
        animatorSet.addListener(new AndroidUtilities$4(view, num, x));
        animatorSet.start();
    }

    public static void checkForCrashes(Activity context) {
    }

    public static void checkForUpdates(Activity context) {
    }

    public static void unregisterUpdates() {
    }

    public static void addToClipboard(CharSequence str) {
        try {
            ((ClipboardManager) ApplicationLoader.applicationContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("label", str));
        } catch (Throwable e) {
            FileLog.m94e(e);
        }
    }

    public static void addMediaToGallery(String fromPath) {
        if (fromPath != null) {
            addMediaToGallery(Uri.fromFile(new File(fromPath)));
        }
    }

    public static void addMediaToGallery(Uri uri) {
        if (uri != null) {
            try {
                Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                mediaScanIntent.setData(uri);
                ApplicationLoader.applicationContext.sendBroadcast(mediaScanIntent);
            } catch (Throwable e) {
                FileLog.m94e(e);
            }
        }
    }

    private static File getAlbumDir() {
        if (VERSION.SDK_INT >= 23 && ApplicationLoader.applicationContext.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != 0) {
            return FileLoader.getInstance().getDirectory(4);
        }
        if ("mounted".equals(Environment.getExternalStorageState())) {
            File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Telegram");
            if (storageDir.mkdirs() || storageDir.exists()) {
                return storageDir;
            }
            FileLog.m91d("failed to create directory");
            return null;
        }
        FileLog.m91d("External storage is not mounted READ/WRITE.");
        return null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @android.annotation.SuppressLint({"NewApi"})
    public static java.lang.String getPath(android.net.Uri r14) {
        /*
        r9 = 0;
        r12 = 1;
        r10 = 0;
        r11 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x0103 }
        r13 = 19;
        if (r11 < r13) goto L_0x0051;
    L_0x0009:
        r4 = r12;
    L_0x000a:
        if (r4 == 0) goto L_0x00d9;
    L_0x000c:
        r11 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Exception -> 0x0103 }
        r11 = android.provider.DocumentsContract.isDocumentUri(r11, r14);	 Catch:{ Exception -> 0x0103 }
        if (r11 == 0) goto L_0x00d9;
    L_0x0014:
        r11 = isExternalStorageDocument(r14);	 Catch:{ Exception -> 0x0103 }
        if (r11 == 0) goto L_0x0053;
    L_0x001a:
        r1 = android.provider.DocumentsContract.getDocumentId(r14);	 Catch:{ Exception -> 0x0103 }
        r10 = ":";
        r7 = r1.split(r10);	 Catch:{ Exception -> 0x0103 }
        r10 = 0;
        r8 = r7[r10];	 Catch:{ Exception -> 0x0103 }
        r10 = "primary";
        r10 = r10.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0103 }
        if (r10 == 0) goto L_0x0050;
    L_0x0031:
        r10 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0103 }
        r10.<init>();	 Catch:{ Exception -> 0x0103 }
        r11 = android.os.Environment.getExternalStorageDirectory();	 Catch:{ Exception -> 0x0103 }
        r10 = r10.append(r11);	 Catch:{ Exception -> 0x0103 }
        r11 = "/";
        r10 = r10.append(r11);	 Catch:{ Exception -> 0x0103 }
        r11 = 1;
        r11 = r7[r11];	 Catch:{ Exception -> 0x0103 }
        r10 = r10.append(r11);	 Catch:{ Exception -> 0x0103 }
        r9 = r10.toString();	 Catch:{ Exception -> 0x0103 }
    L_0x0050:
        return r9;
    L_0x0051:
        r4 = r10;
        goto L_0x000a;
    L_0x0053:
        r11 = isDownloadsDocument(r14);	 Catch:{ Exception -> 0x0103 }
        if (r11 == 0) goto L_0x0079;
    L_0x0059:
        r3 = android.provider.DocumentsContract.getDocumentId(r14);	 Catch:{ Exception -> 0x0103 }
        r10 = "content://downloads/public_downloads";
        r10 = android.net.Uri.parse(r10);	 Catch:{ Exception -> 0x0103 }
        r11 = java.lang.Long.valueOf(r3);	 Catch:{ Exception -> 0x0103 }
        r12 = r11.longValue();	 Catch:{ Exception -> 0x0103 }
        r0 = android.content.ContentUris.withAppendedId(r10, r12);	 Catch:{ Exception -> 0x0103 }
        r10 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Exception -> 0x0103 }
        r11 = 0;
        r12 = 0;
        r9 = getDataColumn(r10, r0, r11, r12);	 Catch:{ Exception -> 0x0103 }
        goto L_0x0050;
    L_0x0079:
        r11 = isMediaDocument(r14);	 Catch:{ Exception -> 0x0103 }
        if (r11 == 0) goto L_0x0050;
    L_0x007f:
        r1 = android.provider.DocumentsContract.getDocumentId(r14);	 Catch:{ Exception -> 0x0103 }
        r11 = ":";
        r7 = r1.split(r11);	 Catch:{ Exception -> 0x0103 }
        r11 = 0;
        r8 = r7[r11];	 Catch:{ Exception -> 0x0103 }
        r0 = 0;
        r11 = -1;
        r13 = r8.hashCode();	 Catch:{ Exception -> 0x0103 }
        switch(r13) {
            case 93166550: goto L_0x00c5;
            case 100313435: goto L_0x00b0;
            case 112202875: goto L_0x00ba;
            default: goto L_0x0096;
        };	 Catch:{ Exception -> 0x0103 }
    L_0x0096:
        r10 = r11;
    L_0x0097:
        switch(r10) {
            case 0: goto L_0x00d0;
            case 1: goto L_0x00d3;
            case 2: goto L_0x00d6;
            default: goto L_0x009a;
        };	 Catch:{ Exception -> 0x0103 }
    L_0x009a:
        r5 = "_id=?";
        r10 = 1;
        r6 = new java.lang.String[r10];	 Catch:{ Exception -> 0x0103 }
        r10 = 0;
        r11 = 1;
        r11 = r7[r11];	 Catch:{ Exception -> 0x0103 }
        r6[r10] = r11;	 Catch:{ Exception -> 0x0103 }
        r10 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Exception -> 0x0103 }
        r11 = "_id=?";
        r9 = getDataColumn(r10, r0, r11, r6);	 Catch:{ Exception -> 0x0103 }
        goto L_0x0050;
    L_0x00b0:
        r12 = "image";
        r12 = r8.equals(r12);	 Catch:{ Exception -> 0x0103 }
        if (r12 == 0) goto L_0x0096;
    L_0x00b9:
        goto L_0x0097;
    L_0x00ba:
        r10 = "video";
        r10 = r8.equals(r10);	 Catch:{ Exception -> 0x0103 }
        if (r10 == 0) goto L_0x0096;
    L_0x00c3:
        r10 = r12;
        goto L_0x0097;
    L_0x00c5:
        r10 = "audio";
        r10 = r8.equals(r10);	 Catch:{ Exception -> 0x0103 }
        if (r10 == 0) goto L_0x0096;
    L_0x00ce:
        r10 = 2;
        goto L_0x0097;
    L_0x00d0:
        r0 = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;	 Catch:{ Exception -> 0x0103 }
        goto L_0x009a;
    L_0x00d3:
        r0 = android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI;	 Catch:{ Exception -> 0x0103 }
        goto L_0x009a;
    L_0x00d6:
        r0 = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;	 Catch:{ Exception -> 0x0103 }
        goto L_0x009a;
    L_0x00d9:
        r10 = "content";
        r11 = r14.getScheme();	 Catch:{ Exception -> 0x0103 }
        r10 = r10.equalsIgnoreCase(r11);	 Catch:{ Exception -> 0x0103 }
        if (r10 == 0) goto L_0x00f0;
    L_0x00e6:
        r10 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Exception -> 0x0103 }
        r11 = 0;
        r12 = 0;
        r9 = getDataColumn(r10, r14, r11, r12);	 Catch:{ Exception -> 0x0103 }
        goto L_0x0050;
    L_0x00f0:
        r10 = "file";
        r11 = r14.getScheme();	 Catch:{ Exception -> 0x0103 }
        r10 = r10.equalsIgnoreCase(r11);	 Catch:{ Exception -> 0x0103 }
        if (r10 == 0) goto L_0x0050;
    L_0x00fd:
        r9 = r14.getPath();	 Catch:{ Exception -> 0x0103 }
        goto L_0x0050;
    L_0x0103:
        r2 = move-exception;
        org.telegram.messenger.FileLog.m94e(r2);
        goto L_0x0050;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.AndroidUtilities.getPath(android.net.Uri):java.lang.String");
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = "_data";
        try {
            cursor = context.getContentResolver().query(uri, new String[]{"_data"}, selection, selectionArgs, null);
            if (cursor == null || !cursor.moveToFirst()) {
                if (cursor != null) {
                    cursor.close();
                }
                return null;
            }
            String value = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
            if (value.startsWith("content://") || !(value.startsWith("/") || value.startsWith("file://"))) {
                if (cursor != null) {
                    cursor.close();
                }
                return null;
            } else if (cursor == null) {
                return value;
            } else {
                cursor.close();
                return value;
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static File generatePicturePath() {
        try {
            File storageDir = getAlbumDir();
            Date date = new Date();
            date.setTime((System.currentTimeMillis() + ((long) Utilities.random.nextInt(1000))) + 1);
            return new File(storageDir, "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss_SSS", Locale.US).format(date) + ".jpg");
        } catch (Throwable e) {
            FileLog.m94e(e);
            return null;
        }
    }

    public static CharSequence generateSearchName(String name, String name2, String q) {
        if (name == null && name2 == null) {
            return "";
        }
        CharSequence builder = new SpannableStringBuilder();
        String wholeString = name;
        if (wholeString == null || wholeString.length() == 0) {
            wholeString = name2;
        } else if (!(name2 == null || name2.length() == 0)) {
            wholeString = wholeString + " " + name2;
        }
        wholeString = wholeString.trim();
        String lower = " " + wholeString.toLowerCase();
        int lastIndex = 0;
        while (true) {
            int index = lower.indexOf(" " + q, lastIndex);
            if (index == -1) {
                break;
            }
            int idx = index - (index == 0 ? 0 : 1);
            int end = ((index == 0 ? 0 : 1) + q.length()) + idx;
            if (lastIndex != 0 && lastIndex != idx + 1) {
                builder.append(wholeString.substring(lastIndex, idx));
            } else if (lastIndex == 0 && idx != 0) {
                builder.append(wholeString.substring(0, idx));
            }
            String query = wholeString.substring(idx, Math.min(wholeString.length(), end));
            if (query.startsWith(" ")) {
                builder.append(" ");
            }
            query = query.trim();
            int start = builder.length();
            builder.append(query);
            builder.setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4)), start, query.length() + start, 33);
            lastIndex = end;
        }
        if (lastIndex == -1 || lastIndex >= wholeString.length()) {
            return builder;
        }
        builder.append(wholeString.substring(lastIndex, wholeString.length()));
        return builder;
    }

    public static File generateVideoPath() {
        try {
            File storageDir = getAlbumDir();
            Date date = new Date();
            date.setTime((System.currentTimeMillis() + ((long) Utilities.random.nextInt(1000))) + 1);
            return new File(storageDir, "VID_" + new SimpleDateFormat("yyyyMMdd_HHmmss_SSS", Locale.US).format(date) + ".mp4");
        } catch (Throwable e) {
            FileLog.m94e(e);
            return null;
        }
    }

    public static String formatFileSize(long size) {
        if (size < PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            return String.format("%d B", new Object[]{Long.valueOf(size)});
        } else if (size < 1048576) {
            return String.format("%.1f KB", new Object[]{Float.valueOf(((float) size) / 1024.0f)});
        } else if (size < 1073741824) {
            return String.format("%.1f MB", new Object[]{Float.valueOf((((float) size) / 1024.0f) / 1024.0f)});
        } else {
            return String.format("%.1f GB", new Object[]{Float.valueOf(((((float) size) / 1024.0f) / 1024.0f) / 1024.0f)});
        }
    }

    public static byte[] decodeQuotedPrintable(byte[] bytes) {
        byte[] bArr = null;
        if (bytes != null) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int i = 0;
            while (i < bytes.length) {
                int b = bytes[i];
                if (b == 61) {
                    i++;
                    try {
                        int u = Character.digit((char) bytes[i], 16);
                        i++;
                        buffer.write((char) ((u << 4) + Character.digit((char) bytes[i], 16)));
                    } catch (Throwable e) {
                        FileLog.m94e(e);
                    }
                } else {
                    buffer.write(b);
                }
                i++;
            }
            bArr = buffer.toByteArray();
            try {
                buffer.close();
            } catch (Throwable e2) {
                FileLog.m94e(e2);
            }
        }
        return bArr;
    }

    public static boolean copyFile(InputStream sourceFile, File destFile) throws IOException {
        OutputStream out = new FileOutputStream(destFile);
        byte[] buf = new byte[4096];
        while (true) {
            int len = sourceFile.read(buf);
            if (len > 0) {
                Thread.yield();
                out.write(buf, 0, len);
            } else {
                out.close();
                return true;
            }
        }
    }

    public static boolean copyFile(File sourceFile, File destFile) throws IOException {
        Throwable e;
        Throwable th;
        if (!destFile.exists()) {
            destFile.createNewFile();
        }
        FileInputStream source = null;
        FileOutputStream destination = null;
        try {
            FileInputStream source2 = new FileInputStream(sourceFile);
            try {
                FileOutputStream destination2 = new FileOutputStream(destFile);
                try {
                    destination2.getChannel().transferFrom(source2.getChannel(), 0, source2.getChannel().size());
                    if (source2 != null) {
                        source2.close();
                    }
                    if (destination2 != null) {
                        destination2.close();
                    }
                    destination = destination2;
                    source = source2;
                    return true;
                } catch (Exception e2) {
                    e = e2;
                    destination = destination2;
                    source = source2;
                    try {
                        FileLog.m94e(e);
                        if (source != null) {
                            source.close();
                        }
                        if (destination != null) {
                            return false;
                        }
                        destination.close();
                        return false;
                    } catch (Throwable th2) {
                        th = th2;
                        if (source != null) {
                            source.close();
                        }
                        if (destination != null) {
                            destination.close();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    destination = destination2;
                    source = source2;
                    if (source != null) {
                        source.close();
                    }
                    if (destination != null) {
                        destination.close();
                    }
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                source = source2;
                FileLog.m94e(e);
                if (source != null) {
                    source.close();
                }
                if (destination != null) {
                    return false;
                }
                destination.close();
                return false;
            } catch (Throwable th4) {
                th = th4;
                source = source2;
                if (source != null) {
                    source.close();
                }
                if (destination != null) {
                    destination.close();
                }
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            FileLog.m94e(e);
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                return false;
            }
            destination.close();
            return false;
        }
    }

    public static byte[] calcAuthKeyHash(byte[] auth_key) {
        byte[] key_hash = new byte[16];
        System.arraycopy(Utilities.computeSHA1(auth_key), 0, key_hash, 0, 16);
        return key_hash;
    }

    public static void openForView(MessageObject message, Activity activity) throws Exception {
        File f = null;
        String fileName = message.getFileName();
        if (!(message.messageOwner.attachPath == null || message.messageOwner.attachPath.length() == 0)) {
            f = new File(message.messageOwner.attachPath);
        }
        if (f == null || !f.exists()) {
            f = FileLoader.getPathToMessage(message.messageOwner);
        }
        if (f != null && f.exists()) {
            String realMimeType = null;
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setFlags(1);
            MimeTypeMap myMime = MimeTypeMap.getSingleton();
            int idx = fileName.lastIndexOf(46);
            if (idx != -1) {
                realMimeType = myMime.getMimeTypeFromExtension(fileName.substring(idx + 1).toLowerCase());
                if (realMimeType == null) {
                    if (message.type == 9 || message.type == 0) {
                        realMimeType = message.getDocument().mime_type;
                    }
                    if (realMimeType == null || realMimeType.length() == 0) {
                        realMimeType = null;
                    }
                }
            }
            if (VERSION.SDK_INT >= 24) {
                intent.setDataAndType(FileProvider.getUriForFile(activity, "org.ir.talaeii.provider", f), realMimeType != null ? realMimeType : "text/plain");
            } else {
                intent.setDataAndType(Uri.fromFile(f), realMimeType != null ? realMimeType : "text/plain");
            }
            if (realMimeType != null) {
                try {
                    activity.startActivityForResult(intent, 500);
                    return;
                } catch (Exception e) {
                    if (VERSION.SDK_INT >= 24) {
                        intent.setDataAndType(FileProvider.getUriForFile(activity, "org.ir.talaeii.provider", f), "text/plain");
                    } else {
                        intent.setDataAndType(Uri.fromFile(f), "text/plain");
                    }
                    activity.startActivityForResult(intent, 500);
                    return;
                }
            }
            activity.startActivityForResult(intent, 500);
        }
    }

    public static void openForView(TLObject media, Activity activity) throws Exception {
        if (media != null && activity != null) {
            String fileName = FileLoader.getAttachFileName(media);
            File f = FileLoader.getPathToAttach(media, true);
            if (f != null && f.exists()) {
                String realMimeType = null;
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setFlags(1);
                MimeTypeMap myMime = MimeTypeMap.getSingleton();
                int idx = fileName.lastIndexOf(46);
                if (idx != -1) {
                    realMimeType = myMime.getMimeTypeFromExtension(fileName.substring(idx + 1).toLowerCase());
                    if (realMimeType == null) {
                        if (media instanceof TLRPC$TL_document) {
                            realMimeType = ((TLRPC$TL_document) media).mime_type;
                        }
                        if (realMimeType == null || realMimeType.length() == 0) {
                            realMimeType = null;
                        }
                    }
                }
                if (VERSION.SDK_INT >= 24) {
                    intent.setDataAndType(FileProvider.getUriForFile(activity, "org.ir.talaeii.provider", f), realMimeType != null ? realMimeType : "text/plain");
                } else {
                    intent.setDataAndType(Uri.fromFile(f), realMimeType != null ? realMimeType : "text/plain");
                }
                if (realMimeType != null) {
                    try {
                        activity.startActivityForResult(intent, 500);
                        return;
                    } catch (Exception e) {
                        if (VERSION.SDK_INT >= 24) {
                            intent.setDataAndType(FileProvider.getUriForFile(activity, "org.ir.talaeii.provider", f), "text/plain");
                        } else {
                            intent.setDataAndType(Uri.fromFile(f), "text/plain");
                        }
                        activity.startActivityForResult(intent, 500);
                        return;
                    }
                }
                activity.startActivityForResult(intent, 500);
            }
        }
    }

    public static boolean isBannedForever(int time) {
        return Math.abs(((long) time) - (System.currentTimeMillis() / 1000)) > 157680000;
    }

    public static void setRectToRect(Matrix matrix, RectF src, RectF dst, int rotation, ScaleToFit align) {
        float sx;
        float sy;
        if (rotation == 90 || rotation == 270) {
            sx = dst.height() / src.width();
            sy = dst.width() / src.height();
        } else {
            sx = dst.width() / src.width();
            sy = dst.height() / src.height();
        }
        if (align != ScaleToFit.FILL) {
            if (sx > sy) {
                sx = sy;
            } else {
                sy = sx;
            }
        }
        float tx = (-src.left) * sx;
        float ty = (-src.top) * sy;
        matrix.setTranslate(dst.left, dst.top);
        if (rotation == 90) {
            matrix.preRotate(90.0f);
            matrix.preTranslate(0.0f, -dst.width());
        } else if (rotation == 180) {
            matrix.preRotate(180.0f);
            matrix.preTranslate(-dst.width(), -dst.height());
        } else if (rotation == 270) {
            matrix.preRotate(270.0f);
            matrix.preTranslate(-dst.height(), 0.0f);
        }
        matrix.preScale(sx, sy);
        matrix.preTranslate(tx, ty);
    }

    public static boolean handleProxyIntent(Activity activity, Intent intent) {
        if (intent == null) {
            return false;
        }
        try {
            if ((intent.getFlags() & 1048576) != 0) {
                return false;
            }
            Uri data = intent.getData();
            if (data == null) {
                return false;
            }
            String user = null;
            String password = null;
            String port = null;
            String address = null;
            String scheme = data.getScheme();
            if (scheme != null) {
                if (scheme.equals("http") || scheme.equals("https")) {
                    String host = data.getHost().toLowerCase();
                    if (host.equals("telegram.me") || host.equals("t.me") || host.equals("telegram.dog") || host.equals("telesco.pe")) {
                        String path = data.getPath();
                        if (path != null && path.startsWith("/socks")) {
                            address = data.getQueryParameter("server");
                            port = data.getQueryParameter("port");
                            user = data.getQueryParameter("user");
                            password = data.getQueryParameter("pass");
                        }
                    }
                } else if (scheme.equals("tg")) {
                    String url = data.toString();
                    if (url.startsWith("tg:socks") || url.startsWith("tg://socks")) {
                        data = Uri.parse(url.replace("tg:proxy", "tg://telegram.org").replace("tg://proxy", "tg://telegram.org"));
                        address = data.getQueryParameter("server");
                        port = data.getQueryParameter("port");
                        user = data.getQueryParameter("user");
                        password = data.getQueryParameter("pass");
                    }
                }
            }
            if (TextUtils.isEmpty(address) || TextUtils.isEmpty(port)) {
                return false;
            }
            if (user == null) {
                user = "";
            }
            if (password == null) {
                password = "";
            }
            showProxyAlert(activity, address, port, user, password);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void showProxyAlert(Activity activity, String address, String port, String user, String password) {
        Builder builder = new Builder(activity);
        builder.setTitle(LocaleController.getString("Proxy", R.string.Proxy));
        StringBuilder stringBuilder = new StringBuilder(LocaleController.getString("EnableProxyAlert", R.string.EnableProxyAlert));
        stringBuilder.append("\n\n");
        stringBuilder.append(LocaleController.getString("UseProxyAddress", R.string.UseProxyAddress)).append(": ").append(address).append(LogCollector.LINE_SEPARATOR);
        stringBuilder.append(LocaleController.getString("UseProxyPort", R.string.UseProxyPort)).append(": ").append(port).append(LogCollector.LINE_SEPARATOR);
        if (!TextUtils.isEmpty(user)) {
            stringBuilder.append(LocaleController.getString("UseProxyUsername", R.string.UseProxyUsername)).append(": ").append(user).append(LogCollector.LINE_SEPARATOR);
        }
        if (!TextUtils.isEmpty(password)) {
            stringBuilder.append(LocaleController.getString("UseProxyPassword", R.string.UseProxyPassword)).append(": ").append(password).append(LogCollector.LINE_SEPARATOR);
        }
        stringBuilder.append(LogCollector.LINE_SEPARATOR).append(LocaleController.getString("EnableProxyAlert2", R.string.EnableProxyAlert2));
        builder.setMessage(stringBuilder.toString());
        builder.setPositiveButton(LocaleController.getString("ConnectingToProxyEnable", R.string.ConnectingToProxyEnable), new AndroidUtilities$5(address, port, password, user));
        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
        builder.show().setCanceledOnTouchOutside(true);
    }

    public static void clearCursorDrawable(EditText editText) {
        if (editText != null) {
            try {
                Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
                mCursorDrawableRes.setAccessible(true);
                mCursorDrawableRes.setInt(editText, 0);
            } catch (Throwable e) {
                FileLog.m94e(e);
            }
        }
    }
}
