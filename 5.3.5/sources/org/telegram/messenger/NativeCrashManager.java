package org.telegram.messenger;

import android.app.Activity;
import android.net.Uri;
import com.persianswitch.sdk.base.log.LogCollector;
import io.fabric.sdk.android.services.network.HttpRequest;
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
import net.hockeyapp.android.Constants;
import net.hockeyapp.android.utils.SimpleMultipartEntity;

public class NativeCrashManager {

    /* renamed from: org.telegram.messenger.NativeCrashManager$2 */
    static class C15672 implements FilenameFilter {
        C15672() {
        }

        public boolean accept(File dir, String name) {
            return name.endsWith(".dmp");
        }
    }

    public static void handleDumpFiles(Activity activity) {
    }

    public static String createLogFile() {
        Date now = new Date();
        try {
            String filename = UUID.randomUUID().toString();
            BufferedWriter write = new BufferedWriter(new FileWriter(Constants.FILES_PATH + "/" + filename + ".faketrace"));
            write.write("Package: " + Constants.APP_PACKAGE + LogCollector.LINE_SEPARATOR);
            write.write("Version Code: " + Constants.APP_VERSION + LogCollector.LINE_SEPARATOR);
            write.write("Version Name: " + Constants.APP_VERSION_NAME + LogCollector.LINE_SEPARATOR);
            write.write("Android: " + Constants.ANDROID_VERSION + LogCollector.LINE_SEPARATOR);
            write.write("Manufacturer: " + Constants.PHONE_MANUFACTURER + LogCollector.LINE_SEPARATOR);
            write.write("Model: " + Constants.PHONE_MODEL + LogCollector.LINE_SEPARATOR);
            write.write("Date: " + now + LogCollector.LINE_SEPARATOR);
            write.write(LogCollector.LINE_SEPARATOR);
            write.write("MinidumpContainer");
            write.flush();
            write.close();
            return filename + ".faketrace";
        } catch (Exception e) {
            FileLog.e(e);
            return null;
        }
    }

    public static void uploadDumpAndLog(final Activity activity, final String identifier, final String dumpFilename, final String logFilename) {
        new Thread() {
            public void run() {
                try {
                    SimpleMultipartEntity entity = new SimpleMultipartEntity();
                    entity.writeFirstBoundaryIfNeeds();
                    Uri attachmentUri = Uri.fromFile(new File(Constants.FILES_PATH, dumpFilename));
                    entity.addPart("attachment0", attachmentUri.getLastPathSegment(), activity.getContentResolver().openInputStream(attachmentUri), false);
                    attachmentUri = Uri.fromFile(new File(Constants.FILES_PATH, logFilename));
                    entity.addPart("log", attachmentUri.getLastPathSegment(), activity.getContentResolver().openInputStream(attachmentUri), true);
                    entity.writeLastBoundaryIfNeeds();
                    HttpURLConnection urlConnection = (HttpURLConnection) new URL("https://rink.hockeyapp.net/api/2/apps/" + identifier + "/crashes/upload").openConnection();
                    urlConnection.setDoOutput(true);
                    urlConnection.setRequestMethod(HttpRequest.METHOD_POST);
                    urlConnection.setRequestProperty(HttpRequest.HEADER_CONTENT_TYPE, entity.getContentType());
                    urlConnection.setRequestProperty(HttpRequest.HEADER_CONTENT_LENGTH, String.valueOf(entity.getContentLength()));
                    BufferedOutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
                    outputStream.write(entity.getOutputStream().toByteArray());
                    outputStream.flush();
                    outputStream.close();
                    urlConnection.connect();
                    FileLog.e("response code = " + urlConnection.getResponseCode() + " message = " + urlConnection.getResponseMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    activity.deleteFile(logFilename);
                    activity.deleteFile(dumpFilename);
                }
            }
        }.start();
    }

    private static String[] searchForDumpFiles() {
        if (Constants.FILES_PATH == null) {
            return new String[0];
        }
        File dir = new File(Constants.FILES_PATH + "/");
        if (dir.mkdir() || dir.exists()) {
            return dir.list(new C15672());
        }
        return new String[0];
    }
}
