package org.telegram.messenger;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Video;

class MediaController$2 implements Runnable {
    final /* synthetic */ int val$prevSize;

    MediaController$2(int i) {
        this.val$prevSize = i;
    }

    @SuppressLint({"NewApi"})
    public void run() {
        int count = 0;
        Cursor cursor = null;
        try {
            if (ApplicationLoader.applicationContext.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0) {
                cursor = Media.query(ApplicationLoader.applicationContext.getContentResolver(), Media.EXTERNAL_CONTENT_URI, new String[]{"COUNT(_id)"}, null, null, null);
                if (cursor != null && cursor.moveToNext()) {
                    count = 0 + cursor.getInt(0);
                }
            }
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
        try {
            if (ApplicationLoader.applicationContext.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0) {
                cursor = Media.query(ApplicationLoader.applicationContext.getContentResolver(), Video.Media.EXTERNAL_CONTENT_URI, new String[]{"COUNT(_id)"}, null, null, null);
                if (cursor != null && cursor.moveToNext()) {
                    count += cursor.getInt(0);
                }
            }
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th2) {
            if (cursor != null) {
                cursor.close();
            }
        }
        if (this.val$prevSize != count) {
            if (MediaController.access$1600() != null) {
                AndroidUtilities.cancelRunOnUIThread(MediaController.access$1600());
                MediaController.access$1602(null);
            }
            MediaController.loadGalleryPhotosAlbums(0);
        }
    }
}
