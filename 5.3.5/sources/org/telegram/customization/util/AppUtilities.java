package org.telegram.customization.util;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.os.Environment;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.TextUtils;
import android.widget.AbsListView;
import android.widget.EdgeEffect;
import android.widget.Toast;
import com.persianswitch.sdk.base.log.LogCollector;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import org.ir.talaeii.R;
import org.ocpsoft.prettytime.PrettyTime;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.ui.LaunchActivity;
import utils.Utilities;

public class AppUtilities {
    public static final String THEME_PREFS = "theme";
    public static final int THEME_PREFS_MODE = 0;
    private static Typeface boldSansTypeface;
    public static final int defColor = getIntColor("themeColor");
    private static Typeface lightSansTypeface;
    private static Typeface mediumSansTypeface;
    private static Typeface mobileSansTypeface;
    public static boolean needRestart = false;
    private static final Hashtable<String, Typeface> typefaceCache = new Hashtable();
    private static Typeface ultraLightSansTypeface;
    private static Typeface ultraNumericSansTypeface;

    public static int getIntColor(String key) {
        return ApplicationLoader.applicationContext.getSharedPreferences(THEME_PREFS, 0).getInt(key, defColor);
    }

    public static void setListViewEdgeEffectColor(AbsListView listView, int color) {
        if (VERSION.SDK_INT >= 21) {
            try {
                Field field = AbsListView.class.getDeclaredField("mEdgeGlowTop");
                field.setAccessible(true);
                EdgeEffect mEdgeGlowTop = (EdgeEffect) field.get(listView);
                if (mEdgeGlowTop != null) {
                    mEdgeGlowTop.setColor(color);
                }
                field = AbsListView.class.getDeclaredField("mEdgeGlowBottom");
                field.setAccessible(true);
                EdgeEffect mEdgeGlowBottom = (EdgeEffect) field.get(listView);
                if (mEdgeGlowBottom != null) {
                    mEdgeGlowBottom.setColor(color);
                }
            } catch (Exception e) {
                FileLog.e("tmessages", e);
            }
        }
    }

    public static void restartApp() {
        ((AlarmManager) ApplicationLoader.applicationContext.getSystemService("alarm")).set(1, System.currentTimeMillis() + 1000, PendingIntent.getActivity(ApplicationLoader.applicationContext, 123456, new Intent(ApplicationLoader.applicationContext, LaunchActivity.class), 268435456));
        System.exit(0);
    }

    public static void savePreferencesToSD(Context context, String folder, String prefName, String tName, boolean toast) {
        File dataF = new File(findPrefFolder(context), prefName);
        if (checkSDStatus() > 1) {
            File f = new File(Environment.getExternalStorageDirectory(), folder);
            f.mkdirs();
            File sdF = new File(f, tName);
            String s = getError(copyFile(dataF, sdF, true));
            if (s.equalsIgnoreCase("4")) {
                if (toast && sdF.getName() != "") {
                    Toast.makeText(context, context.getString(R.string.SavedTo, new Object[]{sdF.getName(), folder}), 0).show();
                    return;
                }
                return;
            } else if (s.contains("0")) {
                Toast.makeText(context, "ERROR: " + context.getString(R.string.SaveErrorMsg0), 1).show();
                return;
            } else {
                Toast.makeText(context, "ERROR: " + s, 1).show();
                Toast.makeText(context, dataF.getAbsolutePath(), 1).show();
                return;
            }
        }
        Toast.makeText(context, "ERROR: " + context.getString(R.string.NoMediaMessage), 1).show();
    }

    static String findPrefFolder(Context context) {
        String appDir = context.getFilesDir().getAbsolutePath();
        File SPDir = new File(appDir.substring(0, appDir.lastIndexOf(47) + 1) + "shared_prefs/");
        if (!SPDir.exists()) {
            SPDir = new File("/dbdata/databases/" + context.getPackageName() + "/shared_prefs/");
        }
        return SPDir.getAbsolutePath();
    }

    static int checkSDStatus() {
        String s = Environment.getExternalStorageState();
        if (s.equals("mounted")) {
            return 2;
        }
        if (s.equals("mounted_ro")) {
            return 1;
        }
        return 0;
    }

    static String getError(int i) {
        String s = "-1";
        if (i == 0) {
            s = "0: SOURCE FILE DOESN'T EXIST";
        }
        if (i == 1) {
            s = "1: DESTINATION FILE DOESN'T EXIST";
        }
        if (i == 2) {
            s = "2: NULL SOURCE & DESTINATION FILES";
        }
        if (i == 3) {
            s = "3: NULL SOURCE FILE";
        }
        if (i == 4) {
            return "4";
        }
        return s;
    }

    static int copyFile(File sourceFile, File destFile, boolean save) {
        int i = -1;
        try {
            if (!sourceFile.exists()) {
                return 0;
            }
            if (!destFile.exists()) {
                if (save) {
                    i = -1 + 2;
                }
                destFile.createNewFile();
            }
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            FileChannel source = fileInputStream.getChannel();
            FileOutputStream fileOutputStream = new FileOutputStream(destFile);
            FileChannel destination = fileOutputStream.getChannel();
            if (!(destination == null || source == null)) {
                destination.transferFrom(source, 0, source.size());
                i = 2;
            }
            if (source != null) {
                source.close();
                i = 3;
            }
            if (destination != null) {
                destination.close();
                i = 4;
            }
            fileInputStream.close();
            fileOutputStream.close();
            return i;
        } catch (Exception e) {
            System.err.println("Error saving preferences: " + e.getMessage());
        }
    }

    public static int loadPrefFromSD(Context context, String prefPath, String name) {
        File dataF = new File(findPrefFolder(context), name + ".xml");
        String s = getError(copyFile(new File(prefPath), dataF, false));
        if (!s.contains("4")) {
            Toast.makeText(context, "ERROR: " + s + LogCollector.LINE_SEPARATOR + context.getString(R.string.restoreErrorMsg, new Object[]{prefFile.getAbsolutePath()}), 1).show();
        }
        return Integer.parseInt(s);
    }

    public static String convertUnixTimeToString(long creationDate) {
        String result = "";
        try {
            result = new PrettyTime(new Locale("FA")).format(new Date((long) (Double.valueOf((double) creationDate).doubleValue() * 1000.0d)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getPersianDate(long mDate) {
        Date date = new Date((long) (Double.valueOf((double) mDate).doubleValue() * 1000.0d));
        String day = Utilities.getShamsiDateDay(date);
        String month = Utilities.getShamsiDateMonth(date);
        String year = Utilities.getShamsiDateYear(date);
        String currentYear = Utilities.getShamsiDateYear(new Date());
        String d = "" + day + " " + month;
        if (currentYear.contentEquals(year)) {
            return d;
        }
        return d + " " + year;
    }

    @SuppressLint({"NewApi"})
    public static Bitmap blurRenderScript(Context context, Bitmap smallBitmap, int radius) {
        try {
            smallBitmap = RGB565toARGB888(smallBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (smallBitmap == null) {
            return null;
        }
        try {
            Bitmap bitmap = Bitmap.createBitmap(smallBitmap.getWidth(), smallBitmap.getHeight(), Config.ARGB_8888);
            RenderScript renderScript = RenderScript.create(context);
            Allocation blurInput = Allocation.createFromBitmap(renderScript, smallBitmap);
            Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);
            ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
            blur.setInput(blurInput);
            blur.setRadius((float) radius);
            blur.forEach(blurOutput);
            blurOutput.copyTo(bitmap);
            renderScript.destroy();
            blur.destroy();
            return bitmap;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static Bitmap RGB565toARGB888(Bitmap img) throws Exception {
        int[] pixels = new int[(img.getWidth() * img.getHeight())];
        img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());
        Bitmap result = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Config.ARGB_8888);
        result.setPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());
        return result;
    }

    public static String normalizeUsername(String username) {
        if (TextUtils.isEmpty(username) || !username.startsWith("@")) {
            return username;
        }
        return username.replace("@", "");
    }

    public static Typeface getLightSansTypeface(Context ctx) {
        if (ApplicationLoader.USE_DEVICE_FONT) {
            return null;
        }
        if (lightSansTypeface == null) {
            lightSansTypeface = Typeface.createFromAsset(ctx.getAssets(), "fonts/IRANSansMobile_Light.ttf");
        }
        return lightSansTypeface;
    }

    public static Typeface getUltraLightSansTypeface(Context ctx) {
        if (ApplicationLoader.USE_DEVICE_FONT) {
            return null;
        }
        if (ultraLightSansTypeface == null) {
            ultraLightSansTypeface = Typeface.createFromAsset(ctx.getAssets(), "fonts/IRANSansMobile_UltraLight.ttf");
        }
        return ultraLightSansTypeface;
    }

    public static int getIntAlphaColor(String key, int def, float factor) {
        int color = ApplicationLoader.applicationContext.getSharedPreferences(THEME_PREFS, 0).getInt(key, def);
        return Color.argb(Math.round(((float) Color.alpha(color)) * factor), Color.red(color), Color.green(color), Color.blue(color));
    }

    public static int setDarkColor(int color, int factor) {
        int alpha = Color.alpha(color);
        int red = Color.red(color) - factor;
        int green = Color.green(color) - factor;
        int blue = Color.blue(color) - factor;
        if (factor < 0) {
            if (red > 255) {
                red = 255;
            }
            if (green > 255) {
                green = 255;
            }
            if (blue > 255) {
                blue = 255;
            }
            if (red == 255 && green == 255 && blue == 255) {
                red = factor;
                green = factor;
                blue = factor;
            }
        }
        if (factor > 0) {
            if (red < 0) {
                red = 0;
            }
            if (green < 0) {
                green = 0;
            }
            if (blue < 0) {
                blue = 0;
            }
            if (red == 0 && green == 0 && blue == 0) {
                red = factor;
                green = factor;
                blue = factor;
            }
        }
        return Color.argb(alpha, red, green, blue);
    }
}
