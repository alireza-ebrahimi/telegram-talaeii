package com.squareup.picasso;

import android.graphics.Bitmap;
import com.squareup.picasso.Picasso.LoadedFrom;

class FetchAction extends Action<Object> {
    private Callback callback;
    private final Object target = new Object();

    FetchAction(Picasso picasso, Request data, int memoryPolicy, int networkPolicy, Object tag, String key, Callback callback) {
        super(picasso, null, data, memoryPolicy, networkPolicy, 0, null, key, tag, false);
        this.callback = callback;
    }

    void complete(Bitmap result, LoadedFrom from) {
        if (this.callback != null) {
            this.callback.onSuccess();
        }
    }

    void error() {
        if (this.callback != null) {
            this.callback.onError();
        }
    }

    void cancel() {
        super.cancel();
        this.callback = null;
    }

    Object getTarget() {
        return this.target;
    }
}
