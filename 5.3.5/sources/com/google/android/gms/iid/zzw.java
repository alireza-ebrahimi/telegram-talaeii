package com.google.android.gms.iid;

import android.os.Bundle;
import android.util.Log;

final class zzw extends zzu<Bundle> {
    zzw(int i, int i2, Bundle bundle) {
        super(i, 1, bundle);
    }

    final boolean zzaww() {
        return false;
    }

    final void zzx(Bundle bundle) {
        Object bundle2 = bundle.getBundle("data");
        if (bundle2 == null) {
            bundle2 = Bundle.EMPTY;
        }
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            String valueOf = String.valueOf(this);
            String valueOf2 = String.valueOf(bundle2);
            Log.d("MessengerIpcClient", new StringBuilder((String.valueOf(valueOf).length() + 16) + String.valueOf(valueOf2).length()).append("Finishing ").append(valueOf).append(" with ").append(valueOf2).toString());
        }
        this.zzgyc.setResult(bundle2);
    }
}
