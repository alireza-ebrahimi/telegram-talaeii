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

    public static FileLog getInstance() {
        FileLog localInstance = Instance;
        if (localInstance == null) {
            synchronized (FileLog.class) {
                try {
                    localInstance = Instance;
                    if (localInstance == null) {
                        FileLog localInstance2 = new FileLog();
                        try {
                            Instance = localInstance2;
                            localInstance = localInstance2;
                        } catch (Throwable th) {
                            Throwable th2 = th;
                            localInstance = localInstance2;
                            throw th2;
                        }
                    }
                } catch (Throwable th3) {
                    th2 = th3;
                    throw th2;
                }
            }
        }
        return localInstance;
    }

    public static boolean shouldLog() {
        return false;
    }

    public FileLog() {
        if (shouldLog()) {
            this.dateFormat = FastDateFormat.getInstance("dd_MM_yyyy_HH_mm_ss", Locale.US);
            try {
                File sdCard = ApplicationLoader.applicationContext.getExternalFilesDir(null);
                if (sdCard != null) {
                    File dir = new File(sdCard.getAbsolutePath() + "/logs");
                    dir.mkdirs();
                    this.currentFile = new File(dir, this.dateFormat.format(System.currentTimeMillis()) + ".txt");
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

    public static String getNetworkLogPath() {
        if (!shouldLog()) {
            return "";
        }
        try {
            File sdCard = ApplicationLoader.applicationContext.getExternalFilesDir(null);
            if (sdCard == null) {
                return "";
            }
            File dir = new File(sdCard.getAbsolutePath() + "/logs");
            dir.mkdirs();
            getInstance().networkFile = new File(dir, getInstance().dateFormat.format(System.currentTimeMillis()) + "_net.txt");
            return getInstance().networkFile.getAbsolutePath();
        } catch (Throwable e) {
            e.printStackTrace();
            return "";
        }
    }

    /* renamed from: e */
    public static void m93e(String message, Throwable exception) {
        if (shouldLog()) {
            Log.e("tmessages", message, exception);
            if (getInstance().streamWriter != null) {
                getInstance().logQueue.postRunnable(new FileLog$1(message, exception));
            }
        }
    }

    /* renamed from: e */
    public static void m92e(String message) {
        if (shouldLog()) {
            Log.e("tmessages", message);
            if (getInstance().streamWriter != null) {
                getInstance().logQueue.postRunnable(new FileLog$2(message));
            }
        }
    }

    /* renamed from: e */
    public static void m94e(Throwable e) {
        if (shouldLog()) {
            e.printStackTrace();
            if (getInstance().streamWriter != null) {
                getInstance().logQueue.postRunnable(new FileLog$3(e));
            } else {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: d */
    public static void m91d(String message) {
        if (shouldLog()) {
            Log.d("tmessages", message);
            if (getInstance().streamWriter != null) {
                getInstance().logQueue.postRunnable(new FileLog$4(message));
            }
        }
    }

    /* renamed from: w */
    public static void m95w(String message) {
        if (shouldLog()) {
            Log.w("tmessages", message);
            if (getInstance().streamWriter != null) {
                getInstance().logQueue.postRunnable(new FileLog$5(message));
            }
        }
    }

    public static void cleanupLogs() {
        File sdCard = ApplicationLoader.applicationContext.getExternalFilesDir(null);
        if (sdCard != null) {
            File[] files = new File(sdCard.getAbsolutePath() + "/logs").listFiles();
            if (files != null) {
                for (File file : files) {
                    if ((getInstance().currentFile == null || !file.getAbsolutePath().equals(getInstance().currentFile.getAbsolutePath())) && (getInstance().networkFile == null || !file.getAbsolutePath().equals(getInstance().networkFile.getAbsolutePath()))) {
                        file.delete();
                    }
                }
            }
        }
    }
}
