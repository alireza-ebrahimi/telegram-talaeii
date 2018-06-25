package org.telegram.messenger;

import android.database.ContentObserver;
import org.telegram.customization.fetch.FetchConst;
import org.telegram.ui.PhotoViewer;

class MediaController$GalleryObserverInternal extends ContentObserver {
    final /* synthetic */ MediaController this$0;

    /* renamed from: org.telegram.messenger.MediaController$GalleryObserverInternal$1 */
    class C14341 implements Runnable {
        C14341() {
        }

        public void run() {
            if (PhotoViewer.getInstance().isVisible()) {
                MediaController$GalleryObserverInternal.this.scheduleReloadRunnable();
                return;
            }
            MediaController.access$1602(null);
            MediaController.loadGalleryPhotosAlbums(0);
        }
    }

    public MediaController$GalleryObserverInternal(MediaController mediaController) {
        this.this$0 = mediaController;
        super(null);
    }

    private void scheduleReloadRunnable() {
        AndroidUtilities.runOnUIThread(MediaController.access$1602(new C14341()), FetchConst.DEFAULT_ON_UPDATE_INTERVAL);
    }

    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        if (MediaController.access$1600() != null) {
            AndroidUtilities.cancelRunOnUIThread(MediaController.access$1600());
        }
        scheduleReloadRunnable();
    }
}
