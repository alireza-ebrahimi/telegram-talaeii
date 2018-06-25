package org.telegram.messenger;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

class MediaController$26 implements OnCancelListener {
    final /* synthetic */ boolean[] val$cancelled;

    MediaController$26(boolean[] zArr) {
        this.val$cancelled = zArr;
    }

    public void onCancel(DialogInterface dialog) {
        this.val$cancelled[0] = true;
    }
}
