package org.telegram.customization.Activities;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.ServiceConnection;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.persianswitch.sdk.api.IPaymentService;
import com.persianswitch.sdk.api.IPaymentService.Stub;
import com.persianswitch.sdk.api.PaymentService;
import com.persianswitch.sdk.api.Request;
import com.persianswitch.sdk.api.Response;
import com.persianswitch.sdk.api.Response.General;
import com.persianswitch.sdk.api.Response.Payment;
import com.persianswitch.sdk.api.Response.Status;
import org.ir.talaeii.R;
import org.telegram.customization.Interfaces.SdkRegisterCallback;
import org.telegram.customization.Internet.BaseResponseModel;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.Model.Payment.HostRequestData;
import org.telegram.customization.Model.Payment.HostResponseData;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.ui.ActionBar.Theme;
import utils.app.AppPreferences;
import utils.view.Constants;
import utils.view.ToastUtil;

public class PaymentConfirmActivity extends AppCompatActivity implements OnClickListener, IResponseReceiver {
    private static final int REQUEST_CODE = 100;
    Button btnSubmit;
    ProgressDialog dialogLoading;
    HostRequestData hostRequestData;
    View itemDescription;
    View itemIssueTracking;
    View itemPaymentId;
    private IPaymentService mService;
    private final ServiceConnection mServiceConnection = new C10421();
    String paymentId = "";
    ProgressBar progressBar;
    ScrollView scrollView;
    Toolbar toolbar;
    TextView tvAmount;
    TextView tvDate;
    TextView tvDesc;

    /* renamed from: org.telegram.customization.Activities.PaymentConfirmActivity$1 */
    class C10421 implements ServiceConnection {
        C10421() {
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("alireza", "sdk service connected");
            PaymentConfirmActivity.this.mService = Stub.asInterface(service);
        }

        public void onServiceDisconnected(ComponentName name) {
            Log.e("alireza", "sdk service  Dissconnected");
            PaymentConfirmActivity.this.mService = null;
        }
    }

    /* renamed from: org.telegram.customization.Activities.PaymentConfirmActivity$2 */
    class C10432 implements OnClickListener {
        C10432() {
        }

        public void onClick(View view) {
            PaymentConfirmActivity.this.onBackPressed();
        }
    }

    /* renamed from: org.telegram.customization.Activities.PaymentConfirmActivity$3 */
    class C10443 implements SdkRegisterCallback {
        C10443() {
        }

        public void onSuccessRegisterSDK() {
            PaymentConfirmActivity.this.dismissDialog();
            AppPreferences.setApRegisterStatus(true);
        }

        public void onFailedRegisterSDK() {
            AppPreferences.setApRegisterStatus(false);
            PaymentConfirmActivity.this.dismissDialog();
            PaymentConfirmActivity.this.finish();
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_payment);
        if (VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(Theme.getColor(Theme.key_actionBarDefault));
        }
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.toolbar.setBackgroundColor(Theme.getColor(Theme.key_actionBarDefault));
        findViewById(R.id.iv_back).setOnClickListener(new C10432());
        this.paymentId = getIntent().getStringExtra(Constants.EXTRA_PAYMENT_ID);
        this.progressBar = (ProgressBar) findViewById(R.id.pb_loading);
        this.tvDate = (TextView) findViewById(R.id.tv_date);
        this.itemPaymentId = findViewById(R.id.item_id);
        this.itemIssueTracking = findViewById(R.id.item_issue_tracking);
        this.itemDescription = findViewById(R.id.item_desc);
        this.tvAmount = (TextView) findViewById(R.id.tv_amount);
        this.btnSubmit = (Button) findViewById(R.id.btn_submit);
        this.tvDesc = (TextView) findViewById(R.id.tv_desc);
        this.btnSubmit.setOnClickListener(this);
        this.scrollView = (ScrollView) findViewById(R.id.scroll_view);
        getPaymentInfo();
        this.progressBar.setVisibility(0);
    }

    private void getPaymentInfo() {
        HandleRequest.getNew(ApplicationLoader.applicationContext, this).getPaymentDetail(this.paymentId);
        showLoadingDialog(ApplicationLoader.applicationContext, LocaleController.getString("HotgramPayment", R.string.HotgramPayment), "لطفا منتظر بمانید");
    }

    protected void onResume() {
        super.onResume();
        Log.e("LEE", "onResume");
        bindToPaymentService();
    }

    public void onPause() {
        super.onPause();
        Log.e("LEE", "onPause");
        unbindService(this.mServiceConnection);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                showLoadingDialog(ApplicationLoader.applicationContext, LocaleController.getString("HotgramPayment", R.string.HotgramPayment), "لطفا منتظر بمانید");
                HandleRequest.getNew(ApplicationLoader.applicationContext, this).requestPayment(this.paymentId);
                return;
            default:
                return;
        }
    }

    public void onResult(Object object, int StatusCode) {
        switch (StatusCode) {
            case Constants.ERROR_PAYMENT_DETAIL /*-35*/:
                dismissDialog();
                findViewById(R.id.pb_loading).setVisibility(8);
                ToastUtil.AppToast(getApplicationContext(), "خطا در دریافت اطلاعات").show();
                Log.e("alireza", "alireza payment Nok  ");
                finish();
                return;
            case Constants.ERROR_REQUEST_PAYMNET /*-31*/:
                dismissDialog();
                findViewById(R.id.pb_loading).setVisibility(8);
                ToastUtil.AppToast(getApplicationContext(), "خطا در دریافت اطلاعات").show();
                Log.e("alireza", "alireza payment Nok  ");
                finish();
                return;
            case Constants.ERROR_IPG_CALL_BACK /*-30*/:
                dismissDialog();
                ToastUtil.AppToast(getApplicationContext(), "پرداخت نا موفق بود").show();
                return;
            case 30:
                dismissDialog();
                BaseResponseModel baseResponseModel = (BaseResponseModel) object;
                if (baseResponseModel.getCode() == 200) {
                    ToastUtil.AppToast(getApplicationContext(), "پرداخت با موفقیت انجام شد").show();
                } else {
                    ToastUtil.AppToast(getApplicationContext(), baseResponseModel.getMessage()).show();
                    if (this.dialogLoading != null) {
                        this.dialogLoading.dismiss();
                    }
                }
                try {
                    String message = baseResponseModel.getMessage();
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.paymentSuccessMessage, new Object[]{message});
                } catch (Exception e) {
                }
                finish();
                return;
            case 31:
                dismissDialog();
                findViewById(R.id.pb_loading).setVisibility(8);
                this.hostRequestData = (HostRequestData) object;
                requestPayment();
                return;
            case 35:
                findViewById(R.id.pb_loading).setVisibility(8);
                this.hostRequestData = (HostRequestData) object;
                this.tvAmount.setText(this.hostRequestData.getAmount() + "");
                this.tvDate.setText(this.hostRequestData.getDate());
                this.tvDesc.setText(this.hostRequestData.getDescription());
                registerApSDK();
                return;
            default:
                return;
        }
    }

    private void registerApSDK() {
        if (AppPreferences.isApRegistered()) {
            dismissDialog();
        } else {
            PaymentRegisterActivitySls.registerSdkAp(new C10443());
        }
    }

    private void bindToPaymentService() {
        bindService(new Intent(this, PaymentService.class), this.mServiceConnection, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == -1 && data != null) {
            Bundle result = data.getBundleExtra(Response.BUNDLE_KEY);
            if (result != null) {
                int sdkResponseStatus = result.getInt(General.STATUS_CODE);
                String hostData = result.getString(General.HOST_RESPONSE);
                String hostDataSign = result.getString(General.HOST_RESPONSE_SIGN);
                Long uniqueTranId;
                switch (sdkResponseStatus) {
                    case 0:
                        uniqueTranId = Long.valueOf(result.getLong(Payment.UNIQUE_TRAN_ID));
                        HostResponseData hostResponseData = new HostResponseData();
                        hostResponseData.setHresp(hostData);
                        hostResponseData.setHsign(hostDataSign);
                        Log.e("alireza", "alireza" + new Gson().toJson(this.hostRequestData));
                        HandleRequest.getNew(getApplicationContext(), this).ipgCallback(hostResponseData);
                        showLoadingDialog(ApplicationLoader.applicationContext, LocaleController.getString("HotgramPayment", R.string.HotgramPayment), "لطفا منتظر بمانید");
                        Toast.makeText(this, "Transaction Successful in SDK", 0).show();
                        return;
                    case 1001:
                    case Status.STATUS_UNKNOWN /*1201*/:
                        uniqueTranId = Long.valueOf(result.getLong(Payment.UNIQUE_TRAN_ID));
                        Toast.makeText(this, "Transaction Unknown", 0).show();
                        callFailPayment(sdkResponseStatus);
                        return;
                    case 1002:
                        callFailPayment(sdkResponseStatus);
                        Toast.makeText(this, "Transaction Failed", 0).show();
                        return;
                    case Status.STATUS_INVALID_HOST_REQUEST /*1100*/:
                        Toast.makeText(this, "Invalid Host Data, check your host data request ", 0).show();
                        callFailPayment(sdkResponseStatus);
                        return;
                    case Status.STATUS_REGISTER_NEEDED /*1102*/:
                        showLoadingDialog(ApplicationLoader.applicationContext, LocaleController.getString("HotgramPayment", R.string.HotgramPayment), "لطفا منتظر بمانید");
                        registerApSDK();
                        Toast.makeText(this, "First Register User", 0).show();
                        callFailPayment(sdkResponseStatus);
                        return;
                    case Status.STATUS_SDK_NEED_UPDATE /*1105*/:
                        Toast.makeText(this, "You must update your sdk", 0).show();
                        callFailPayment(sdkResponseStatus);
                        return;
                    case Status.STATUS_CANCELED /*2020*/:
                        Toast.makeText(this, "Transaction Canceled By User", 0).show();
                        callFailPayment(sdkResponseStatus);
                        return;
                    case Status.STATUS_PAYMENT_TIMEOUT /*2021*/:
                        Toast.makeText(this, "Transaction Canceled By SDK(due to timeout)", 0).show();
                        callFailPayment(sdkResponseStatus);
                        return;
                    case Status.STATUS_INVALID_USER_DATA /*2022*/:
                        Toast.makeText(this, "User don't confirm registration data (such as mobile no)", 0).show();
                        callFailPayment(sdkResponseStatus);
                        return;
                    case Status.STATUS_DECRYPTION_ERROR /*2023*/:
                        Toast.makeText(this, "Don't change secret key until register again", 0).show();
                        callFailPayment(sdkResponseStatus);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    private void callFailPayment(int sdkResponseStatus) {
        HandleRequest.getNew(getApplicationContext(), this).failPayment(this.paymentId, String.valueOf(sdkResponseStatus));
    }

    public void requestPayment() {
        if (this.mService != null && this.hostRequestData != null) {
            try {
                Bundle transactionData = new Bundle();
                transactionData.putString(Request.General.PROTOCOL_VERSION, "1.8.0");
                transactionData.putString(Request.General.SECURE_TOKEN, AppPreferences.getSecurityToken(ApplicationLoader.applicationContext));
                transactionData.putLong(Request.General.HOST_TRAN_ID, this.hostRequestData.getTranId());
                transactionData.putString(Request.General.HOST_DATA, this.hostRequestData.getHostRequest());
                transactionData.putString(Request.General.HOST_DATA_SIGN, this.hostRequestData.getHostRequestSign());
                try {
                    startIntentSenderForResult(((PendingIntent) this.mService.getPaymentIntent(transactionData).getParcelable("payment_intent")).getIntentSender(), 100, null, 0, 0, 0);
                } catch (SendIntentException e) {
                }
            } catch (RemoteException ignored) {
                ignored.printStackTrace();
            }
        }
    }

    private void dismissDialog() {
        try {
            if (this.dialogLoading != null) {
                this.dialogLoading.dismiss();
            }
        } catch (Exception e) {
        }
    }

    private void showLoadingDialog(Context context, String title, String message) {
        if (this.dialogLoading == null) {
            this.dialogLoading = new ProgressDialog(this);
            this.dialogLoading.setTitle(title);
            this.dialogLoading.setMessage(message);
            this.dialogLoading.setCancelable(false);
        }
        this.dialogLoading.show();
    }
}
