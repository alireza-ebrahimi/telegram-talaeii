package org.telegram.messenger;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.media.session.PlaybackStateCompat;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import org.telegram.ui.ActionBar.AlertDialog;

class MediaController$27 implements Runnable {
    final /* synthetic */ boolean[] val$cancelled;
    final /* synthetic */ AlertDialog val$finalProgress;
    final /* synthetic */ String val$mime;
    final /* synthetic */ String val$name;
    final /* synthetic */ File val$sourceFile;
    final /* synthetic */ int val$type;

    /* renamed from: org.telegram.messenger.MediaController$27$2 */
    class C14292 implements Runnable {
        C14292() {
        }

        public void run() {
            try {
                MediaController$27.this.val$finalProgress.dismiss();
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
    }

    MediaController$27(int i, String str, File file, boolean[] zArr, AlertDialog alertDialog, String str2) {
        this.val$type = i;
        this.val$name = str;
        this.val$sourceFile = file;
        this.val$cancelled = zArr;
        this.val$finalProgress = alertDialog;
        this.val$mime = str2;
    }

    public void run() {
        try {
            File destFile;
            long a;
            if (this.val$type == 0) {
                destFile = AndroidUtilities.generatePicturePath();
            } else if (this.val$type == 1) {
                destFile = AndroidUtilities.generateVideoPath();
            } else {
                File dir;
                if (this.val$type == 2) {
                    dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                } else {
                    dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
                }
                dir.mkdir();
                destFile = new File(dir, this.val$name);
                if (destFile.exists()) {
                    int idx = this.val$name.lastIndexOf(46);
                    for (a = null; a < 10; a++) {
                        String newName;
                        if (idx != -1) {
                            newName = this.val$name.substring(0, idx) + "(" + (a + 1) + ")" + this.val$name.substring(idx);
                        } else {
                            newName = this.val$name + "(" + (a + 1) + ")";
                        }
                        destFile = new File(dir, newName);
                        if (!destFile.exists()) {
                            break;
                        }
                    }
                }
            }
            if (!destFile.exists()) {
                destFile.createNewFile();
            }
            FileChannel source = null;
            FileChannel destination = null;
            boolean result = true;
            long lastProgress = System.currentTimeMillis() - 500;
            try {
                source = new FileInputStream(this.val$sourceFile).getChannel();
                destination = new FileOutputStream(destFile).getChannel();
                long size = source.size();
                for (a = 0; a < size && !this.val$cancelled[0]; a += PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM) {
                    destination.transferFrom(source, a, Math.min(PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM, size - a));
                    if (this.val$finalProgress != null && lastProgress <= System.currentTimeMillis() - 500) {
                        lastProgress = System.currentTimeMillis();
                        final int i = (int) ((((float) a) / ((float) size)) * 100.0f);
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                try {
                                    MediaController$27.this.val$finalProgress.setProgress(i);
                                } catch (Exception e) {
                                    FileLog.e(e);
                                }
                            }
                        });
                    }
                }
                if (source != null) {
                    try {
                        source.close();
                    } catch (Exception e) {
                    }
                }
                if (destination != null) {
                    try {
                        destination.close();
                    } catch (Exception e2) {
                    }
                }
            } catch (Exception e3) {
                FileLog.e(e3);
                result = false;
                if (source != null) {
                    try {
                        source.close();
                    } catch (Exception e4) {
                    }
                }
                if (destination != null) {
                    try {
                        destination.close();
                    } catch (Exception e5) {
                    }
                }
            } catch (Throwable th) {
                if (source != null) {
                    try {
                        source.close();
                    } catch (Exception e6) {
                    }
                }
                if (destination != null) {
                    try {
                        destination.close();
                    } catch (Exception e7) {
                    }
                }
            }
            if (this.val$cancelled[0]) {
                destFile.delete();
                result = false;
            }
            if (result) {
                if (this.val$type == 2) {
                    ((DownloadManager) ApplicationLoader.applicationContext.getSystemService("download")).addCompletedDownload(destFile.getName(), destFile.getName(), false, this.val$mime, destFile.getAbsolutePath(), destFile.length(), true);
                } else {
                    AndroidUtilities.addMediaToGallery(Uri.fromFile(destFile));
                }
            }
        } catch (Exception e32) {
            FileLog.e(e32);
        }
        if (this.val$finalProgress != null) {
            AndroidUtilities.runOnUIThread(new C14292());
        }
    }
}
