package org.telegram.messenger;

import android.database.ContentObserver;
import org.telegram.customization.fetch.FetchConst;

class MediaController$GalleryObserverExternal extends ContentObserver {
    final /* synthetic */ MediaController this$0;

    /* renamed from: org.telegram.messenger.MediaController$GalleryObserverExternal$1 */
    class C14331 implements Runnable {
        C14331() {
        }

        public void run() {
            MediaController.access$1602(null);
            MediaController.loadGalleryPhotosAlbums(0);
        }
    }

    public MediaController$GalleryObserverExternal(MediaController mediaController) {
        this.this$0 = mediaController;
        super(null);
    }

    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        if (MediaController.access$1600() != null) {
            AndroidUtilities.cancelRunOnUIThread(MediaController.access$1600());
        }
        AndroidUtilities.runOnUIThread(MediaController.access$1602(new C14331()), FetchConst.DEFAULT_ON_UPDATE_INTERVAL);
    }
}
