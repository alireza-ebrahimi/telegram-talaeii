package utils;

import android.content.ContentProviderOperation;
import android.content.ContentProviderOperation.Builder;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import org.ir.talaeii.R;
import org.telegram.customization.util.AppUtilities;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import utils.app.AppPreferences;
import utils.view.PixelUtil;

public class Utilities {
    public static String lightingColor(String color, String transparency) {
        if (TextUtils.isEmpty(transparency)) {
            transparency = "99";
        }
        if (color.startsWith("#")) {
            color = color.replace("#", "");
        }
        if (color.length() > 6) {
            color = color.substring(color.length() - 6, color.length() - 1);
        }
        return correctColor(transparency + color);
    }

    public static String correctColor(String color) {
        if (TextUtils.isEmpty(color)) {
            color = "#55000000";
        }
        if (color.startsWith("#")) {
            return color;
        }
        return "#" + color;
    }

    public static String normalizeTagText(String name) {
        if (!name.startsWith(" ")) {
            name = " " + name;
        }
        if (name.endsWith(" ")) {
            return name;
        }
        return name + " ";
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int convertDpToPixel(float dp, Context context) {
        return PixelUtil.dpToPx(context, (int) dp);
    }

    public static float convertPixelsToDp(float px, Context context) {
        return (float) PixelUtil.pxToDp(context, (int) px);
    }

    public static int getDeviceApiLevel() {
        return VERSION.SDK_INT;
    }

    public static int getVersionNumber(Context context) {
        return 135;
    }

    public static String getCurrentShamsidate() {
        Locale loc = new Locale("en_US");
        Utilities util = new Utilities();
        util.getClass();
        return String.valueOf(new Utilities$SolarCalendar(util).year) + "/" + String.format(loc, "%02d", new Object[]{Integer.valueOf(sc.month)}) + "/" + String.format(loc, "%02d", new Object[]{Integer.valueOf(sc.date)});
    }

    public static String getCurrentShamsidate(Date date) {
        Locale loc = new Locale("en_US");
        Utilities util = new Utilities();
        util.getClass();
        return String.valueOf(new Utilities$SolarCalendar(util, date).year) + "/" + String.format(loc, "%02d", new Object[]{Integer.valueOf(sc.month)}) + "/" + String.format(loc, "%02d", new Object[]{Integer.valueOf(sc.date)}) + " - " + date.getHours() + ":" + date.getMinutes();
    }

    public static String getShamsiDateDay(Date date) {
        Locale loc = new Locale("en_US");
        new Utilities().getClass();
        return String.format(loc, "%02d", new Object[]{Integer.valueOf(new Utilities$SolarCalendar(util, date).date)});
    }

    public static String getShamsiDateMonth(Date date) {
        Utilities util = new Utilities();
        util.getClass();
        return new Utilities$SolarCalendar(util, date).strMonth;
    }

    public static String getShamsiDateYear(Date date) {
        Utilities util = new Utilities();
        util.getClass();
        return new Utilities$SolarCalendar(util, date).year + "";
    }

    public static int darker(int color, float factor) {
        return Color.argb(Color.alpha(color), Math.max((int) (((float) Color.red(color)) * factor), 0), Math.max((int) (((float) Color.green(color)) * factor), 0), Math.max((int) (((float) Color.blue(color)) * factor), 0));
    }

    public static String getRootFolder() {
        String result = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + ApplicationLoader.applicationContext.getString(R.string.ROOT_FOLDER) + File.separator;
        File dir = new File(result);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return result;
    }

    public static int getAndroidVersion() {
        try {
            return VERSION.SDK_INT;
        } catch (Exception e) {
            return 1;
        }
    }

    public static List<String> translateBannerUrl(URL url) {
        List<String> result = new ArrayList(2);
        String productId = null;
        String catId = null;
        Map<String, String> map = null;
        try {
            map = splitQuery(url);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for (String key : map.keySet()) {
            String value = (String) map.get(key);
            if (key.contains("product_id")) {
                productId = value;
            }
            if (key.contentEquals("path")) {
                if (value.contains(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR)) {
                    catId = value.split(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR)[0];
                } else {
                    catId = value;
                }
            }
        }
        result.add(productId);
        result.add(catId);
        return result;
    }

    public static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap();
        String query = url.getQuery();
        if (!TextUtils.isEmpty(query)) {
            for (String pair : query.split("&")) {
                int idx = pair.indexOf("=");
                query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
            }
        }
        return query_pairs;
    }

    public static int decreaseMenuItemWidth(int width) {
        if (AndroidUtilities.displaySize == null) {
            return width;
        }
        int w2 = AndroidUtilities.displaySize.x;
        float w = w2 > 720 ? 0.9f : w2 > 600 ? 0.85f : 0.75f;
        FileLog.m91d("alireza compatible width: " + w + " - w2: " + w2 + " - w3 : ");
        return (int) (((float) width) * w);
    }

    public static void addViewActionByActionName(String actionName) {
    }

    public static String convert(String str) {
        char[] arabicChars = new char[]{'٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩'};
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                builder.append(arabicChars[str.charAt(i) - 48]);
            } else {
                builder.append(str.charAt(i));
            }
        }
        return builder.toString();
    }

    public static Uri getLocalBitmapUri(ImageView imageView) {
        if (!(imageView.getDrawable() instanceof BitmapDrawable)) {
            return null;
        }
        Bitmap bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        Uri bmpUri = null;
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(CompressFormat.PNG, 90, out);
            out.close();
            return Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            return bmpUri;
        }
    }

    public static String readFromFile(Context context, String fileName) {
        String text = "";
        try {
            InputStream e = context.getAssets().open("data/" + fileName);
            byte[] buffer = new byte[e.available()];
            e.read(buffer);
            e.close();
            return new String(buffer);
        } catch (IOException var6) {
            var6.printStackTrace();
            return "";
        }
    }

    public static byte[] getFileByteArray(File file) {
        if (file == null) {
            return new byte[0];
        }
        byte[] bytes = new byte[((int) file.length())];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
            return bytes;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return bytes;
        } catch (IOException e2) {
            e2.printStackTrace();
            return bytes;
        }
    }

    public static void update(Context context, long contactId, byte[] bytes) {
        ArrayList<ContentProviderOperation> ops = new ArrayList();
        Builder builder = ContentProviderOperation.newUpdate(Data.CONTENT_URI);
        try {
            builder = ContentProviderOperation.newUpdate(Data.CONTENT_URI);
            builder.withSelection("contact_id=? AND mimetype=?", new String[]{String.valueOf(contactId), "vnd.android.cursor.item/photo"});
            builder.withValue("data15", bytes);
            ops.add(builder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            context.getContentResolver().applyBatch("com.android.contacts", ops);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static final int getThemeColor() {
        return ApplicationLoader.applicationContext.getSharedPreferences(AppUtilities.THEME_PREFS, 0).getInt("themeColor", AppUtilities.defColor);
    }

    public static String getLauncherClassName(Context context) {
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        for (ResolveInfo resolveInfo : pm.queryIntentActivities(intent, 0)) {
            if (resolveInfo.activityInfo.applicationInfo.packageName.equalsIgnoreCase(context.getPackageName())) {
                return resolveInfo.activityInfo.name;
            }
        }
        return null;
    }

    public static void setBadge(int count) {
        try {
            ContentValues cv = new ContentValues();
            cv.put("tag", ApplicationLoader.applicationContext.getPackageName() + "/org.telegram.ui.LaunchActivity");
            cv.put("count", Integer.valueOf(count));
            ApplicationLoader.applicationContext.getContentResolver().insert(Uri.parse("content://com.teslacoilsw.notifier/unread_count"), cv);
        } catch (Throwable th) {
        }
        try {
            String launcherClassName = getLauncherClassName(ApplicationLoader.applicationContext);
            if (launcherClassName != null) {
                AndroidUtilities.runOnUIThread(new Utilities$1(count, launcherClassName));
            }
        } catch (Throwable e) {
            FileLog.m93e("tmessages", e);
        }
    }

    public static void pinToTop(long dialogId) {
        if (ApplicationLoader.applicationContext != null) {
            long[] pinned = AppPreferences.getPinnedDialog(ApplicationLoader.applicationContext);
            if (pinned == null) {
                AppPreferences.addPinnedDialog(ApplicationLoader.applicationContext, dialogId);
                return;
            }
            boolean exist = false;
            for (long aPinned : pinned) {
                if (aPinned == dialogId) {
                    exist = true;
                    break;
                }
            }
            if (exist) {
                AppPreferences.removePinnedDialog(ApplicationLoader.applicationContext, dialogId);
            } else {
                AppPreferences.addPinnedDialog(ApplicationLoader.applicationContext, dialogId);
            }
        }
    }

    public static boolean isPinnedToTop(long dialogId) {
        if (ApplicationLoader.applicationContext == null) {
            return false;
        }
        long[] pinned = AppPreferences.getPinnedDialog(ApplicationLoader.applicationContext);
        if (pinned == null) {
            return false;
        }
        for (long aPinned : pinned) {
            if (aPinned == dialogId) {
                return true;
            }
        }
        return false;
    }

    public static String getShamsiDate(long dateMiladi) {
        Date date = new Date((long) (Double.valueOf((double) dateMiladi).doubleValue() * 1000.0d));
        String day = getShamsiDateDay(date);
        String month = getShamsiDateMonth(date);
        String year = getShamsiDateYear(date);
        String currentYear = getShamsiDateYear(new Date());
        String d = "" + day + " " + month;
        if (currentYear.contentEquals(year)) {
            return d;
        }
        return d + " " + year;
    }

    public static void addContact(Context context, String name, String lastName, String number) {
        Log.d("slspushreceiver msg:", "add contact 1");
        ArrayList<ContentProviderOperation> operationList = new ArrayList();
        operationList.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI).withValue("account_type", null).withValue("account_name", null).build());
        Log.d("slspushreceiver msg:", "add contact 2");
        operationList.add(ContentProviderOperation.newInsert(Data.CONTENT_URI).withValueBackReference("raw_contact_id", 0).withValue("mimetype", "vnd.android.cursor.item/name").withValue("data2", name).withValue("data3", lastName).build());
        Log.d("slspushreceiver msg:", "add contact 3");
        operationList.add(ContentProviderOperation.newInsert(Data.CONTENT_URI).withValueBackReference("raw_contact_id", 0).withValue("mimetype", "vnd.android.cursor.item/phone_v2").withValue("data1", number).withValue("data2", Integer.valueOf(1)).build());
        try {
            context.getContentResolver().applyBatch("com.android.contacts", operationList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean deleteContact(android.content.Context r10, java.lang.String r11, java.lang.String r12, java.lang.String r13) {
        /*
        r2 = 0;
        r0 = android.provider.ContactsContract.PhoneLookup.CONTENT_FILTER_URI;
        r3 = android.net.Uri.encode(r11);
        r1 = android.net.Uri.withAppendedPath(r0, r3);
        r0 = r10.getContentResolver();
        r3 = r2;
        r4 = r2;
        r5 = r2;
        r6 = r0.query(r1, r2, r3, r4, r5);
        r0 = r6.moveToFirst();	 Catch:{ Exception -> 0x0094 }
        if (r0 == 0) goto L_0x008f;
    L_0x001c:
        r0 = "LEE";
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0094 }
        r2.<init>();	 Catch:{ Exception -> 0x0094 }
        r3 = "Contact:";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0094 }
        r3 = "display_name";
        r3 = r6.getColumnIndex(r3);	 Catch:{ Exception -> 0x0094 }
        r3 = r6.getString(r3);	 Catch:{ Exception -> 0x0094 }
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0094 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0094 }
        android.util.Log.d(r0, r2);	 Catch:{ Exception -> 0x0094 }
        r0 = "display_name";
        r0 = r6.getColumnIndex(r0);	 Catch:{ Exception -> 0x0094 }
        r0 = r6.getString(r0);	 Catch:{ Exception -> 0x0094 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0094 }
        r2.<init>();	 Catch:{ Exception -> 0x0094 }
        r2 = r2.append(r12);	 Catch:{ Exception -> 0x0094 }
        r3 = " ";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0094 }
        r2 = r2.append(r13);	 Catch:{ Exception -> 0x0094 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0094 }
        r0 = r0.equalsIgnoreCase(r2);	 Catch:{ Exception -> 0x0094 }
        if (r0 == 0) goto L_0x0089;
    L_0x006a:
        r0 = "lookup";
        r0 = r6.getColumnIndex(r0);	 Catch:{ Exception -> 0x0094 }
        r8 = r6.getString(r0);	 Catch:{ Exception -> 0x0094 }
        r0 = android.provider.ContactsContract.Contacts.CONTENT_LOOKUP_URI;	 Catch:{ Exception -> 0x0094 }
        r9 = android.net.Uri.withAppendedPath(r0, r8);	 Catch:{ Exception -> 0x0094 }
        r0 = r10.getContentResolver();	 Catch:{ Exception -> 0x0094 }
        r2 = 0;
        r3 = 0;
        r0.delete(r9, r2, r3);	 Catch:{ Exception -> 0x0094 }
        r0 = 1;
        r6.close();
    L_0x0088:
        return r0;
    L_0x0089:
        r0 = r6.moveToNext();	 Catch:{ Exception -> 0x0094 }
        if (r0 != 0) goto L_0x001c;
    L_0x008f:
        r6.close();
    L_0x0092:
        r0 = 0;
        goto L_0x0088;
    L_0x0094:
        r7 = move-exception;
        r0 = java.lang.System.out;	 Catch:{ all -> 0x00a2 }
        r2 = r7.getStackTrace();	 Catch:{ all -> 0x00a2 }
        r0.println(r2);	 Catch:{ all -> 0x00a2 }
        r6.close();
        goto L_0x0092;
    L_0x00a2:
        r0 = move-exception;
        r6.close();
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: utils.Utilities.deleteContact(android.content.Context, java.lang.String, java.lang.String, java.lang.String):boolean");
    }

    public static int getRandomMinute() {
        return new Random().nextInt(55) + 1;
    }

    public static String getCarrierName() {
        return ((TelephonyManager) ApplicationLoader.applicationContext.getSystemService("phone")).getNetworkOperatorName();
    }
}
