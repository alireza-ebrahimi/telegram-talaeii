package org.telegram.messenger;

import android.database.ContentObserver;
import android.provider.MediaStore.Images.Media;

class MediaController$ExternalObserver extends ContentObserver {
    final /* synthetic */ MediaController this$0;

    public MediaController$ExternalObserver(MediaController mediaController) {
        this.this$0 = mediaController;
        super(null);
    }

    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        MediaController.access$1500(this.this$0, Media.EXTERNAL_CONTENT_URI);
    }
}
