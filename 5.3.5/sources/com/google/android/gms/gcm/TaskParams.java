package com.google.android.gms.gcm;

import android.net.Uri;
import android.os.Bundle;
import com.google.android.gms.common.internal.Hide;
import java.util.List;

public class TaskParams {
    private final Bundle extras;
    private final String tag;
    private final List<Uri> zzila;

    public TaskParams(String str) {
        this(str, null, null);
    }

    public TaskParams(String str, Bundle bundle) {
        this(str, bundle, null);
    }

    @Hide
    public TaskParams(String str, Bundle bundle, List<Uri> list) {
        this.tag = str;
        this.extras = bundle;
        this.zzila = list;
    }

    public Bundle getExtras() {
        return this.extras;
    }

    public String getTag() {
        return this.tag;
    }
}
