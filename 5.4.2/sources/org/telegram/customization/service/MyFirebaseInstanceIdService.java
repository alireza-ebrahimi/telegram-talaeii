package org.telegram.customization.service;

import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import org.telegram.messenger.exoplayer2.C3446C;
import utils.p178a.C3791b;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService implements C2497d {
    /* renamed from: a */
    public void mo3490a() {
        String d = FirebaseInstanceId.m8755a().m8776d();
        Log.e("LEE", "Refreshed token: " + d);
        C3791b.m13899D(getApplicationContext(), d);
        C2818c.m13087a(getApplicationContext(), (C2497d) this).m13123a(false);
    }

    public void onResult(Object obj, int i) {
        switch (i) {
            case C3446C.RESULT_FORMAT_READ /*-5*/:
                C3791b.m13936a(getApplicationContext(), false);
                return;
            default:
                return;
        }
    }
}
