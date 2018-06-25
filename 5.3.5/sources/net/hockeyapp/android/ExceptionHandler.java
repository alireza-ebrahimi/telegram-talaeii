package net.hockeyapp.android;

import android.os.Process;
import android.text.TextUtils;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Date;
import java.util.UUID;
import net.hockeyapp.android.objects.CrashDetails;
import net.hockeyapp.android.utils.HockeyLog;

public class ExceptionHandler implements UncaughtExceptionHandler {
    private CrashManagerListener mCrashManagerListener;
    private UncaughtExceptionHandler mDefaultExceptionHandler;
    private boolean mIgnoreDefaultHandler = false;

    public ExceptionHandler(UncaughtExceptionHandler defaultExceptionHandler, CrashManagerListener listener, boolean ignoreDefaultHandler) {
        this.mDefaultExceptionHandler = defaultExceptionHandler;
        this.mIgnoreDefaultHandler = ignoreDefaultHandler;
        this.mCrashManagerListener = listener;
    }

    public void setListener(CrashManagerListener listener) {
        this.mCrashManagerListener = listener;
    }

    @Deprecated
    public static void saveException(Throwable exception, CrashManagerListener listener) {
        saveException(exception, null, listener);
    }

    public static void saveException(Throwable exception, Thread thread, CrashManagerListener listener) {
        Date now = new Date();
        Date startDate = new Date(CrashManager.getInitializeTimestamp());
        exception.printStackTrace(new PrintWriter(new StringWriter()));
        String filename = UUID.randomUUID().toString();
        CrashDetails crashDetails = new CrashDetails(filename, exception);
        crashDetails.setAppPackage(Constants.APP_PACKAGE);
        crashDetails.setAppVersionCode(Constants.APP_VERSION);
        crashDetails.setAppVersionName(Constants.APP_VERSION_NAME);
        crashDetails.setAppStartDate(startDate);
        crashDetails.setAppCrashDate(now);
        if (listener == null || listener.includeDeviceData()) {
            crashDetails.setOsVersion(Constants.ANDROID_VERSION);
            crashDetails.setOsBuild(Constants.ANDROID_BUILD);
            crashDetails.setDeviceManufacturer(Constants.PHONE_MANUFACTURER);
            crashDetails.setDeviceModel(Constants.PHONE_MODEL);
        }
        if (thread != null && (listener == null || listener.includeThreadDetails())) {
            crashDetails.setThreadName(thread.getName() + "-" + thread.getId());
        }
        if (Constants.CRASH_IDENTIFIER != null && (listener == null || listener.includeDeviceIdentifier())) {
            crashDetails.setReporterKey(Constants.CRASH_IDENTIFIER);
        }
        crashDetails.writeCrashReport();
        if (listener != null) {
            try {
                writeValueToFile(limitedString(listener.getUserID()), filename + ".user");
                writeValueToFile(limitedString(listener.getContact()), filename + ".contact");
                writeValueToFile(listener.getDescription(), filename + ".description");
            } catch (IOException e) {
                HockeyLog.error("Error saving crash meta data!", e);
            }
        }
    }

    public static void saveNativeException(Throwable exception, String managedExceptionString, Thread thread, CrashManagerListener listener) {
        if (!TextUtils.isEmpty(managedExceptionString)) {
            String[] splits = managedExceptionString.split("--- End of managed exception stack trace ---", 2);
            if (splits != null && splits.length > 0) {
                managedExceptionString = splits[0];
            }
        }
        saveXamarinException(exception, thread, managedExceptionString, Boolean.valueOf(false), listener);
    }

    public static void saveManagedException(Throwable exception, Thread thread, CrashManagerListener listener) {
        saveXamarinException(exception, thread, null, Boolean.valueOf(true), listener);
    }

    private static void saveXamarinException(Throwable exception, Thread thread, String additionalManagedException, Boolean isManagedException, CrashManagerListener listener) {
        Date startDate = new Date(CrashManager.getInitializeTimestamp());
        String filename = UUID.randomUUID().toString();
        Date now = new Date();
        PrintWriter printWriter = new PrintWriter(new StringWriter());
        if (exception != null) {
            exception.printStackTrace(printWriter);
        }
        CrashDetails crashDetails = new CrashDetails(filename, exception, additionalManagedException, isManagedException);
        crashDetails.setAppPackage(Constants.APP_PACKAGE);
        crashDetails.setAppVersionCode(Constants.APP_VERSION);
        crashDetails.setAppVersionName(Constants.APP_VERSION_NAME);
        crashDetails.setAppStartDate(startDate);
        crashDetails.setAppCrashDate(now);
        if (listener == null || listener.includeDeviceData()) {
            crashDetails.setOsVersion(Constants.ANDROID_VERSION);
            crashDetails.setOsBuild(Constants.ANDROID_BUILD);
            crashDetails.setDeviceManufacturer(Constants.PHONE_MANUFACTURER);
            crashDetails.setDeviceModel(Constants.PHONE_MODEL);
        }
        if (thread != null && (listener == null || listener.includeThreadDetails())) {
            crashDetails.setThreadName(thread.getName() + "-" + thread.getId());
        }
        if (Constants.CRASH_IDENTIFIER != null && (listener == null || listener.includeDeviceIdentifier())) {
            crashDetails.setReporterKey(Constants.CRASH_IDENTIFIER);
        }
        crashDetails.writeCrashReport();
        if (listener != null) {
            try {
                writeValueToFile(limitedString(listener.getUserID()), filename + ".user");
                writeValueToFile(limitedString(listener.getContact()), filename + ".contact");
                writeValueToFile(listener.getDescription(), filename + ".description");
            } catch (IOException e) {
                HockeyLog.error("Error saving crash meta data!", e);
            }
        }
    }

    public void uncaughtException(Thread thread, Throwable exception) {
        if (Constants.FILES_PATH == null) {
            this.mDefaultExceptionHandler.uncaughtException(thread, exception);
            return;
        }
        saveException(exception, thread, this.mCrashManagerListener);
        if (this.mIgnoreDefaultHandler) {
            Process.killProcess(Process.myPid());
            System.exit(10);
            return;
        }
        this.mDefaultExceptionHandler.uncaughtException(thread, exception);
    }

    private static void writeValueToFile(String value, String filename) throws IOException {
        Throwable th;
        if (!TextUtils.isEmpty(value)) {
            BufferedWriter writer = null;
            try {
                String path = Constants.FILES_PATH + "/" + filename;
                if (!TextUtils.isEmpty(value) && TextUtils.getTrimmedLength(value) > 0) {
                    BufferedWriter writer2 = new BufferedWriter(new FileWriter(path));
                    try {
                        writer2.write(value);
                        writer2.flush();
                        writer = writer2;
                    } catch (IOException e) {
                        writer = writer2;
                        if (writer != null) {
                            writer.close();
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        writer = writer2;
                        if (writer != null) {
                            writer.close();
                        }
                        throw th;
                    }
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e2) {
                if (writer != null) {
                    writer.close();
                }
            } catch (Throwable th3) {
                th = th3;
                if (writer != null) {
                    writer.close();
                }
                throw th;
            }
        }
    }

    private static String limitedString(String string) {
        if (TextUtils.isEmpty(string) || string.length() <= 255) {
            return string;
        }
        return string.substring(0, 255);
    }
}
