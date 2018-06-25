package org.telegram.messenger;

import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Locale;
import org.telegram.messenger.time.FastDateFormat;

public class FileLog {
    private static volatile FileLog Instance = null;
    private File currentFile = null;
    private FastDateFormat dateFormat = null;
    private DispatchQueue logQueue = null;
    private File networkFile = null;
    private OutputStreamWriter streamWriter = null;

    public FileLog() {
        if (shouldLog()) {
            this.dateFormat = FastDateFormat.getInstance("dd_MM_yyyy_HH_mm_ss", Locale.US);
            try {
                File externalFilesDir = ApplicationLoader.applicationContext.getExternalFilesDir(null);
                if (externalFilesDir != null) {
                    File file = new File(externalFilesDir.getAbsolutePath() + "/logs");
                    file.mkdirs();
                    this.currentFile = new File(file, this.dateFormat.format(System.currentTimeMillis()) + ".txt");
                    try {
                        this.logQueue = new DispatchQueue("logQueue");
                        this.currentFile.createNewFile();
                        this.streamWriter = new OutputStreamWriter(new FileOutputStream(this.currentFile));
                        this.streamWriter.write("-----start log " + this.dateFormat.format(System.currentTimeMillis()) + "-----\n");
                        this.streamWriter.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void cleanupLogs() {
        File externalFilesDir = ApplicationLoader.applicationContext.getExternalFilesDir(null);
        if (externalFilesDir != null) {
            File[] listFiles = new File(externalFilesDir.getAbsolutePath() + "/logs").listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    if ((getInstance().currentFile == null || !file.getAbsolutePath().equals(getInstance().currentFile.getAbsolutePath())) && (getInstance().networkFile == null || !file.getAbsolutePath().equals(getInstance().networkFile.getAbsolutePath()))) {
                        file.delete();
                    }
                }
            }
        }
    }

    /* renamed from: d */
    public static void m13725d(final String str) {
        if (shouldLog()) {
            Log.d("tmessages", str);
            if (getInstance().streamWriter != null) {
                getInstance().logQueue.postRunnable(new Runnable() {
                    public void run() {
                        try {
                            FileLog.getInstance().streamWriter.write(FileLog.getInstance().dateFormat.format(System.currentTimeMillis()) + " D/tmessages: " + str + "\n");
                            FileLog.getInstance().streamWriter.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    /* renamed from: e */
    public static void m13726e(final String str) {
        if (shouldLog()) {
            Log.e("tmessages", str);
            if (getInstance().streamWriter != null) {
                getInstance().logQueue.postRunnable(new Runnable() {
                    public void run() {
                        try {
                            FileLog.getInstance().streamWriter.write(FileLog.getInstance().dateFormat.format(System.currentTimeMillis()) + " E/tmessages: " + str + "\n");
                            FileLog.getInstance().streamWriter.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    /* renamed from: e */
    public static void m13727e(final String str, final Throwable th) {
        if (shouldLog()) {
            Log.e("tmessages", str, th);
            if (getInstance().streamWriter != null) {
                getInstance().logQueue.postRunnable(new Runnable() {
                    public void run() {
                        try {
                            FileLog.getInstance().streamWriter.write(FileLog.getInstance().dateFormat.format(System.currentTimeMillis()) + " E/tmessages: " + str + "\n");
                            FileLog.getInstance().streamWriter.write(th.toString());
                            FileLog.getInstance().streamWriter.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    /* renamed from: e */
    public static void m13728e(final Throwable th) {
        if (shouldLog()) {
            th.printStackTrace();
            if (getInstance().streamWriter != null) {
                getInstance().logQueue.postRunnable(new Runnable() {
                    public void run() {
                        try {
                            FileLog.getInstance().streamWriter.write(FileLog.getInstance().dateFormat.format(System.currentTimeMillis()) + " E/tmessages: " + th + "\n");
                            StackTraceElement[] stackTrace = th.getStackTrace();
                            for (Object obj : stackTrace) {
                                FileLog.getInstance().streamWriter.write(FileLog.getInstance().dateFormat.format(System.currentTimeMillis()) + " E/tmessages: " + obj + "\n");
                            }
                            FileLog.getInstance().streamWriter.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                th.printStackTrace();
            }
        }
    }

    public static FileLog getInstance() {
        FileLog fileLog = Instance;
        if (fileLog == null) {
            synchronized (FileLog.class) {
                fileLog = Instance;
                if (fileLog == null) {
                    fileLog = new FileLog();
                    Instance = fileLog;
                }
            }
        }
        return fileLog;
    }

    public static String getNetworkLogPath() {
        if (!shouldLog()) {
            return TtmlNode.ANONYMOUS_REGION_ID;
        }
        try {
            File externalFilesDir = ApplicationLoader.applicationContext.getExternalFilesDir(null);
            if (externalFilesDir == null) {
                return TtmlNode.ANONYMOUS_REGION_ID;
            }
            File file = new File(externalFilesDir.getAbsolutePath() + "/logs");
            file.mkdirs();
            getInstance().networkFile = new File(file, getInstance().dateFormat.format(System.currentTimeMillis()) + "_net.txt");
            return getInstance().networkFile.getAbsolutePath();
        } catch (Throwable th) {
            th.printStackTrace();
            return TtmlNode.ANONYMOUS_REGION_ID;
        }
    }

    public static boolean shouldLog() {
        return false;
    }

    /* renamed from: w */
    public static void m13729w(final String str) {
        if (shouldLog()) {
            Log.w("tmessages", str);
            if (getInstance().streamWriter != null) {
                getInstance().logQueue.postRunnable(new Runnable() {
                    public void run() {
                        try {
                            FileLog.getInstance().streamWriter.write(FileLog.getInstance().dateFormat.format(System.currentTimeMillis()) + " W/tmessages: " + str + "\n");
                            FileLog.getInstance().streamWriter.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }
}
