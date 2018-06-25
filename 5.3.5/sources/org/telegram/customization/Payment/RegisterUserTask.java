package org.telegram.customization.Payment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.app.AlertDialog.Builder;
import android.util.Log;
import android.widget.Toast;
import com.persianswitch.sdk.api.IPaymentService;
import com.persianswitch.sdk.api.IPaymentService.Stub;
import com.persianswitch.sdk.api.PaymentService;
import com.persianswitch.sdk.api.Response.General;
import com.persianswitch.sdk.api.Response.Status;
import org.telegram.customization.Interfaces.SdkRegisterCallback;

public class RegisterUserTask extends AsyncTask<Void, Void, Bundle> {
    SdkRegisterCallback callback;
    private Context mContext;
    private IPaymentService mService;
    private final ServiceConnection mServiceConnection = new C11871();
    private Bundle registerData;

    /* renamed from: org.telegram.customization.Payment.RegisterUserTask$1 */
    class C11871 implements ServiceConnection {
        C11871() {
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            RegisterUserTask.this.mService = Stub.asInterface(service);
            AsyncTaskCompat.executeParallel(RegisterUserTask.this, new Void[0]);
        }

        public void onServiceDisconnected(ComponentName name) {
            RegisterUserTask.this.mService = null;
        }
    }

    public RegisterUserTask(Bundle registerData, SdkRegisterCallback callback) {
        this.registerData = registerData;
        this.callback = callback;
    }

    protected Bundle doInBackground(Void... params) {
        try {
            return this.mService.registerUser(this.registerData);
        } catch (Exception e) {
            return null;
        }
    }

    protected void onPostExecute(Bundle bundle) {
        if (bundle != null) {
            int status = bundle.getInt(General.STATUS_CODE);
            if (status == 0) {
                Log.i("alirezaaaaa", "alirezaaaaa sdk error : " + status);
                Toast.makeText(this.mContext, "The mobile number registered successfully. Now user can pay...", 1).show();
                this.callback.onSuccessRegisterSDK();
            } else if (status == Status.STATUS_REGISTER_NEEDED) {
                Log.i("alirezaaaaa", "alirezaaaaa sdk error : " + status);
                Toast.makeText(this.mContext, "The mobile number registered successfully. Now user can pay...", 1).show();
                this.callback.onFailedRegisterSDK();
            } else {
                Toast.makeText(this.mContext, "The Registration failed. Host app should retry registration phase." + status, 1).show();
                this.callback.onFailedRegisterSDK();
            }
        }
        this.mContext.unbindService(this.mServiceConnection);
    }

    private void showBundleResponse(Activity activity, Bundle result) {
        StringBuilder sb = new StringBuilder();
        for (String key : result.keySet()) {
            sb.append('\"');
            sb.append(key);
            sb.append("\"=\"");
            sb.append(result.get(key));
            sb.append("\", \n");
        }
        new Builder(activity).setMessage(sb.toString()).show();
    }

    public void start(Context context) {
        this.mContext = context;
        context.bindService(new Intent(context, PaymentService.class), this.mServiceConnection, 1);
    }
}
