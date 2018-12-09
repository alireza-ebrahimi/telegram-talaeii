package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.RemoteInput;
import android.app.RemoteInput.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ba.C0312a;

@TargetApi(20)
class az {
    /* renamed from: a */
    static Bundle m1408a(Intent intent) {
        return RemoteInput.getResultsFromIntent(intent);
    }

    /* renamed from: a */
    static RemoteInput[] m1409a(C0312a[] c0312aArr) {
        if (c0312aArr == null) {
            return null;
        }
        RemoteInput[] remoteInputArr = new RemoteInput[c0312aArr.length];
        for (int i = 0; i < c0312aArr.length; i++) {
            C0312a c0312a = c0312aArr[i];
            remoteInputArr[i] = new Builder(c0312a.mo236a()).setLabel(c0312a.mo237b()).setChoices(c0312a.mo238c()).setAllowFreeFormInput(c0312a.mo239d()).addExtras(c0312a.mo240e()).build();
        }
        return remoteInputArr;
    }
}
