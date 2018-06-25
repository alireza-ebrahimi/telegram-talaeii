package org.telegram.customization.p167h;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.p014d.C0430a;
import android.util.Log;
import android.widget.Toast;
import com.persianswitch.sdk.api.IPaymentService;
import com.persianswitch.sdk.api.IPaymentService.Stub;
import com.persianswitch.sdk.api.PaymentService;
import com.persianswitch.sdk.api.Response.General;
import org.telegram.customization.p152f.C2506b;

/* renamed from: org.telegram.customization.h.a */
public class C2824a extends AsyncTask<Void, Void, Bundle> {
    /* renamed from: a */
    C2506b f9279a;
    /* renamed from: b */
    private Bundle f9280b;
    /* renamed from: c */
    private Context f9281c;
    /* renamed from: d */
    private IPaymentService f9282d;
    /* renamed from: e */
    private final ServiceConnection f9283e = new C28231(this);

    /* renamed from: org.telegram.customization.h.a$1 */
    class C28231 implements ServiceConnection {
        /* renamed from: a */
        final /* synthetic */ C2824a f9278a;

        C28231(C2824a c2824a) {
            this.f9278a = c2824a;
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            this.f9278a.f9282d = Stub.m10439a(iBinder);
            C0430a.m1910a(this.f9278a, new Void[0]);
        }

        public void onServiceDisconnected(ComponentName componentName) {
            this.f9278a.f9282d = null;
        }
    }

    public C2824a(Bundle bundle, C2506b c2506b) {
        this.f9280b = bundle;
        this.f9279a = c2506b;
    }

    /* renamed from: a */
    protected Bundle m13159a(Void... voidArr) {
        try {
            return this.f9282d.mo3225b(this.f9280b);
        } catch (Exception e) {
            return null;
        }
    }

    /* renamed from: a */
    public void m13160a(Context context) {
        this.f9281c = context;
        context.bindService(new Intent(context, PaymentService.class), this.f9283e, 1);
    }

    /* renamed from: a */
    protected void m13161a(Bundle bundle) {
        if (bundle != null) {
            int i = bundle.getInt(General.f6983a);
            if (i == 0) {
                Log.i("alirezaaaaa", "alirezaaaaa sdk error : " + i);
                Toast.makeText(this.f9281c, "The mobile number registered successfully. Now user can pay...", 1).show();
                this.f9279a.mo3416a();
            } else if (i == 1102) {
                Log.i("alirezaaaaa", "alirezaaaaa sdk error : " + i);
                Toast.makeText(this.f9281c, "The mobile number registered successfully. Now user can pay...", 1).show();
                this.f9279a.mo3417b();
            } else {
                Toast.makeText(this.f9281c, "The Registration failed. Host app should retry registration phase." + i, 1).show();
                this.f9279a.mo3417b();
            }
        }
        this.f9281c.unbindService(this.f9283e);
    }

    protected /* synthetic */ Object doInBackground(Object[] objArr) {
        return m13159a((Void[]) objArr);
    }

    protected /* synthetic */ void onPostExecute(Object obj) {
        m13161a((Bundle) obj);
    }
}
