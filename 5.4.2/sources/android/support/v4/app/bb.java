package android.support.v4.app;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ba.C0312a;

@TargetApi(16)
class bb {
    /* renamed from: a */
    static Bundle m1413a(Intent intent) {
        ClipData clipData = intent.getClipData();
        if (clipData == null) {
            return null;
        }
        ClipDescription description = clipData.getDescription();
        return (description.hasMimeType("text/vnd.android.intent") && description.getLabel().equals("android.remoteinput.results")) ? (Bundle) clipData.getItemAt(0).getIntent().getExtras().getParcelable("android.remoteinput.resultsData") : null;
    }

    /* renamed from: a */
    static Bundle m1414a(C0312a c0312a) {
        Bundle bundle = new Bundle();
        bundle.putString("resultKey", c0312a.mo236a());
        bundle.putCharSequence("label", c0312a.mo237b());
        bundle.putCharSequenceArray("choices", c0312a.mo238c());
        bundle.putBoolean("allowFreeFormInput", c0312a.mo239d());
        bundle.putBundle("extras", c0312a.mo240e());
        return bundle;
    }

    /* renamed from: a */
    static Bundle[] m1415a(C0312a[] c0312aArr) {
        if (c0312aArr == null) {
            return null;
        }
        Bundle[] bundleArr = new Bundle[c0312aArr.length];
        for (int i = 0; i < c0312aArr.length; i++) {
            bundleArr[i] = m1414a(c0312aArr[i]);
        }
        return bundleArr;
    }
}
