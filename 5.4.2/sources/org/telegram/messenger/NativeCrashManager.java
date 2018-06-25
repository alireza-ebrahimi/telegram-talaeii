package org.telegram.messenger;

import android.app.Activity;
import android.net.Uri;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.UUID;
import net.hockeyapp.android.C2367a;
import net.hockeyapp.android.p137e.C2406h;

public class NativeCrashManager {

    /* renamed from: org.telegram.messenger.NativeCrashManager$2 */
    static class C33032 implements FilenameFilter {
        C33032() {
        }

        public boolean accept(File file, String str) {
            return str.endsWith(".dmp");
        }
    }

    public static String createLogFile() {
        Date date = new Date();
        try {
            String uuid = UUID.randomUUID().toString();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(C2367a.f7955a + "/" + uuid + ".faketrace"));
            bufferedWriter.write("Package: " + C2367a.f7958d + "\n");
            bufferedWriter.write("Version Code: " + C2367a.f7956b + "\n");
            bufferedWriter.write("Version Name: " + C2367a.f7957c + "\n");
            bufferedWriter.write("Android: " + C2367a.f7959e + "\n");
            bufferedWriter.write("Manufacturer: " + C2367a.f7962h + "\n");
            bufferedWriter.write("Model: " + C2367a.f7961g + "\n");
            bufferedWriter.write("Date: " + date + "\n");
            bufferedWriter.write("\n");
            bufferedWriter.write("MinidumpContainer");
            bufferedWriter.flush();
            bufferedWriter.close();
            return uuid + ".faketrace";
        } catch (Throwable e) {
            FileLog.m13728e(e);
            return null;
        }
    }

    public static void handleDumpFiles(Activity activity) {
    }

    private static String[] searchForDumpFiles() {
        if (C2367a.f7955a == null) {
            return new String[0];
        }
        File file = new File(C2367a.f7955a + "/");
        return (file.mkdir() || file.exists()) ? file.list(new C33032()) : new String[0];
    }

    public static void uploadDumpAndLog(final Activity activity, final String str, final String str2, final String str3) {
        new Thread() {
            public void run() {
                try {
                    C2406h c2406h = new C2406h();
                    c2406h.m11873b();
                    Uri fromFile = Uri.fromFile(new File(C2367a.f7955a, str2));
                    c2406h.m11872a("attachment0", fromFile.getLastPathSegment(), activity.getContentResolver().openInputStream(fromFile), false);
                    fromFile = Uri.fromFile(new File(C2367a.f7955a, str3));
                    c2406h.m11872a("log", fromFile.getLastPathSegment(), activity.getContentResolver().openInputStream(fromFile), true);
                    c2406h.m11874c();
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("https://rink.hockeyapp.net/api/2/apps/" + str + "/crashes/upload").openConnection();
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("Content-Type", c2406h.m11876e());
                    httpURLConnection.setRequestProperty("Content-Length", String.valueOf(c2406h.m11875d()));
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
                    bufferedOutputStream.write(c2406h.m11877f().toByteArray());
                    bufferedOutputStream.flush();
                    bufferedOutputStream.close();
                    httpURLConnection.connect();
                    FileLog.m13726e("response code = " + httpURLConnection.getResponseCode() + " message = " + httpURLConnection.getResponseMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    activity.deleteFile(str3);
                    activity.deleteFile(str2);
                }
            }
        }.start();
    }
}
