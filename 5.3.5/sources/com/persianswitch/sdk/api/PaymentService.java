package com.persianswitch.sdk.api;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import com.persianswitch.sdk.payment.managers.ServiceManager;

public class PaymentService extends Service {
    @Nullable
    public IBinder onBind(Intent intent) {
        return new ServiceManager(this);
    }
}
