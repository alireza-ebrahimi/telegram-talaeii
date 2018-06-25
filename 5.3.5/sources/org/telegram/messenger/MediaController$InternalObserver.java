package org.telegram.messenger;

import android.database.ContentObserver;
import android.provider.MediaStore.Images.Media;

class MediaController$InternalObserver extends ContentObserver {
    final /* synthetic */ MediaController this$0;

    public MediaController$InternalObserver(MediaController mediaController) {
        this.this$0 = mediaController;
        super(null);
    }

    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        MediaController.access$1500(this.this$0, Media.INTERNAL_CONTENT_URI);
    }
}
