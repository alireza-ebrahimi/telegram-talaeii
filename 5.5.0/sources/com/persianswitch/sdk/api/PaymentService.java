package com.persianswitch.sdk.api;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.persianswitch.sdk.payment.managers.ServiceManager;

public class PaymentService extends Service {
    public IBinder onBind(Intent intent) {
        return new ServiceManager(this);
    }
}
