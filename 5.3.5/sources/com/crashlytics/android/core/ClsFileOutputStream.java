package com.crashlytics.android.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

class ClsFileOutputStream extends FileOutputStream {
    public static final String IN_PROGRESS_SESSION_FILE_EXTENSION = ".cls_temp";
    public static final String SESSION_FILE_EXTENSION = ".cls";
    public static final FilenameFilter TEMP_FILENAME_FILTER = new C04341();
    private boolean closed;
    private File complete;
    private File inProgress;
    private final String root;

    /* renamed from: com.crashlytics.android.core.ClsFileOutputStream$1 */
    static class C04341 implements FilenameFilter {
        C04341() {
        }

        public boolean accept(File dir, String filename) {
            return filename.endsWith(ClsFileOutputStream.IN_PROGRESS_SESSION_FILE_EXTENSION);
        }
    }

    public ClsFileOutputStream(String dir, String fileRoot) throws FileNotFoundException {
        this(new File(dir), fileRoot);
    }

    public ClsFileOutputStream(File dir, String fileRoot) throws FileNotFoundException {
        super(new File(dir, fileRoot + IN_PROGRESS_SESSION_FILE_EXTENSION));
        this.closed = false;
        this.root = dir + File.separator + fileRoot;
        this.inProgress = new File(this.root + IN_PROGRESS_SESSION_FILE_EXTENSION);
    }

    public synchronized void close() throws IOException {
        if (!this.closed) {
            this.closed = true;
            super.flush();
            super.close();
            File complete = new File(this.root + SESSION_FILE_EXTENSION);
            if (this.inProgress.renameTo(complete)) {
                this.inProgress = null;
                this.complete = complete;
            } else {
                String reason = "";
                if (complete.exists()) {
                    reason = " (target already exists)";
                } else if (!this.inProgress.exists()) {
                    reason = " (source does not exist)";
                }
                throw new IOException("Could not rename temp file: " + this.inProgress + " -> " + complete + reason);
            }
        }
    }

    public void closeInProgressStream() throws IOException {
        if (!this.closed) {
            this.closed = true;
            super.flush();
            super.close();
        }
    }

    public File getCompleteFile() {
        return this.complete;
    }

    public File getInProgressFile() {
        return this.inProgress;
    }
}
