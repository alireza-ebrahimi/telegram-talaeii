package org.telegram.customization.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.persianswitch.sdk.api.Request.General;
import com.persianswitch.sdk.api.Request.Register;
import java.util.ArrayList;
import java.util.List;
import org.ir.talaeii.R;
import org.telegram.customization.Interfaces.SdkRegisterCallback;
import org.telegram.customization.Internet.BaseResponseModel;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.Model.Payment.Bank;
import org.telegram.customization.Model.Payment.HostRequestData;
import org.telegram.customization.Model.Payment.User;
import org.telegram.customization.Payment.RegisterUserTask;
import org.telegram.customization.util.MaterialSpinner;
import org.telegram.customization.util.PhoneUtils;
import org.telegram.customization.util.ReadTextFile;
import org.telegram.customization.util.Validation;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.UserConfig;
import org.telegram.ui.ActionBar.Theme;
import utils.app.AppPreferences;
import utils.view.Constants;
import utils.view.FarsiButton;
import utils.view.FarsiEditText;
import utils.view.ToastUtil;

public class PaymentRegisterActivitySls extends Activity implements OnClickListener, IResponseReceiver {
    static ProgressDialog dialogLoading;
    MaterialSpinnerAdapter adapter;
    ArrayList<Bank> banks = new ArrayList();
    FarsiButton btnRegister;
    FarsiEditText etAddress;
    FarsiEditText etCardNo;
    FarsiEditText etDesc;
    FarsiEditText etLastName;
    FarsiEditText etName;
    FarsiEditText etNationalCode;
    FarsiEditText etPass;
    FarsiEditText etPassConf;
    FarsiEditText etShaba;
    MaterialSpinner spBank;
    TextInputLayout tilNationalCode;
    Toolbar toolbar;

    /* renamed from: org.telegram.customization.Activities.PaymentRegisterActivitySls$1 */
    class C10481 implements SdkRegisterCallback {
        C10481() {
        }

        public void onSuccessRegisterSDK() {
            AppPreferences.setApRegisterStatus(true);
        }

        public void onFailedRegisterSDK() {
            AppPreferences.setApRegisterStatus(false);
        }
    }

    /* renamed from: org.telegram.customization.Activities.PaymentRegisterActivitySls$2 */
    class C10492 implements OnClickListener {
        C10492() {
        }

        public void onClick(View view) {
            PaymentRegisterActivitySls.this.onBackPressed();
        }
    }

    /* renamed from: org.telegram.customization.Activities.PaymentRegisterActivitySls$3 */
    class C10503 extends TypeToken<ArrayList<Bank>> {
        C10503() {
        }
    }

    public class MaterialSpinnerAdapter extends ArrayAdapter<Bank> {
        List<Bank> banks = new ArrayList();

        public MaterialSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Bank> objects) {
            super(context, resource, objects);
            this.banks = objects;
        }

        public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
            return getCustomView(position, cnvtView, prnt);
        }

        public View getView(int pos, View cnvtView, ViewGroup prnt) {
            return getCustomView(pos, cnvtView, prnt);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            View mySpinner = PaymentRegisterActivitySls.this.getLayoutInflater().inflate(R.layout.multi_select_spinner_item, parent, false);
            ((TextView) mySpinner.findViewById(R.id.ftv_color_name)).setText(((Bank) this.banks.get(position)).getName());
            return mySpinner;
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_payment);
        initView();
        registerSdkAp(new C10481());
    }

    public void initView() {
        if (VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(Theme.getColor(Theme.key_actionBarDefault));
        }
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.toolbar.setBackgroundColor(Theme.getColor(Theme.key_actionBarDefault));
        findViewById(R.id.iv_back).setOnClickListener(new C10492());
        this.tilNationalCode = (TextInputLayout) findViewById(R.id.til_national_code);
        this.etName = (FarsiEditText) findViewById(R.id.fet_first_name);
        this.etLastName = (FarsiEditText) findViewById(R.id.fet_last_name);
        this.etPass = (FarsiEditText) findViewById(R.id.fet_password);
        this.etPassConf = (FarsiEditText) findViewById(R.id.fet_password_conf);
        this.etDesc = (FarsiEditText) findViewById(R.id.fet_desc);
        this.etAddress = (FarsiEditText) findViewById(R.id.fet_address);
        this.etNationalCode = (FarsiEditText) findViewById(R.id.fet_national_code);
        this.etCardNo = (FarsiEditText) findViewById(R.id.fet_card_no);
        this.etShaba = (FarsiEditText) findViewById(R.id.fet_shaba);
        this.spBank = (MaterialSpinner) findViewById(R.id.sp_activity_group);
        this.btnRegister = (FarsiButton) findViewById(R.id.ftv_register);
        this.btnRegister.setOnClickListener(this);
        this.etName.setText(UserConfig.getCurrentUser().first_name);
        this.etLastName.setText(UserConfig.getCurrentUser().last_name);
        this.banks = (ArrayList) new Gson().fromJson(ReadTextFile.getFileText(ApplicationLoader.applicationContext, "banks.txt"), new C10503().getType());
        this.adapter = new MaterialSpinnerAdapter(ApplicationLoader.applicationContext, R.layout.multi_select_spinner_item, this.banks);
        this.spBank.setAdapter(this.adapter);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ftv_register:
                if (TextUtils.isEmpty(this.etName.getText().toString())) {
                    ToastUtil.AppToast(ApplicationLoader.applicationContext, "نام خود را وارد کنید").show();
                    return;
                } else if (TextUtils.isEmpty(this.etLastName.getText().toString())) {
                    ToastUtil.AppToast(ApplicationLoader.applicationContext, "نام خود خانوادگی را وارد کنید").show();
                    return;
                } else if (TextUtils.isEmpty(this.etPass.getText().toString())) {
                    ToastUtil.AppToast(ApplicationLoader.applicationContext, "رمزعبور خود را وارد کنید").show();
                    return;
                } else if (TextUtils.isEmpty(this.etPassConf.getText().toString())) {
                    ToastUtil.AppToast(ApplicationLoader.applicationContext, "تاییده رمزعبور خود را وارد کنید").show();
                    return;
                } else if (TextUtils.isEmpty(this.etDesc.getText().toString())) {
                    ToastUtil.AppToast(ApplicationLoader.applicationContext, "توضیخات خود را وارد کنید").show();
                    return;
                } else if (TextUtils.isEmpty(this.etAddress.getText().toString())) {
                    ToastUtil.AppToast(ApplicationLoader.applicationContext, "آدرس خود را وارد کنید").show();
                    return;
                } else if (TextUtils.isEmpty(this.etNationalCode.getText().toString())) {
                    ToastUtil.AppToast(ApplicationLoader.applicationContext, "کد ملی خود را وارد کنید").show();
                    return;
                } else if (TextUtils.isEmpty(this.etCardNo.getText().toString())) {
                    ToastUtil.AppToast(ApplicationLoader.applicationContext, "شماره کارت خود را وارد کنید").show();
                    return;
                } else if (!Validation.isValidShaba(this.etShaba.getText().toString())) {
                    ToastUtil.AppToast(ApplicationLoader.applicationContext, "شماره شبا خود را وارد کنید").show();
                    return;
                } else if (!Validation.isValidNationalCode(this.etNationalCode, this.tilNationalCode, R.string.error_national_code, this)) {
                    return;
                } else {
                    if (this.etPass.getText().toString().contentEquals(this.etPassConf.getText().toString())) {
                        User user = new User();
                        user.setUsername(UserConfig.getCurrentUser().username);
                        user.setFirstName(this.etName.getText().toString());
                        user.setLastName(this.etLastName.getText().toString());
                        user.setPassword(this.etPass.getText().toString());
                        user.setDescription(this.etDesc.getText().toString());
                        user.setAddress(this.etAddress.getText().toString());
                        user.setNationalCode(this.etNationalCode.getText().toString());
                        user.setCardNo(this.etCardNo.getText().toString());
                        user.setShaba(this.etShaba.getText().toString());
                        user.setBankId(((Bank) this.banks.get(this.spBank.getSelectedItemPosition())).getId());
                        user.setMobileNumber(Long.valueOf(Long.parseLong(PhoneUtils.normalize(UserConfig.getCurrentUser().phone))));
                        HandleRequest.getNew(ApplicationLoader.applicationContext, this).registerSupporter(user);
                        showLoadingDialog(ApplicationLoader.applicationContext, LocaleController.getString("HotgramPayment", R.string.HotgramPayment), "لطفا منتظر بمانید");
                        return;
                    }
                    ToastUtil.AppToast(ApplicationLoader.applicationContext, "رمز عبور وارد شده یکسان نیست").show();
                    return;
                }
            default:
                return;
        }
    }

    public void onResult(Object object, int StatusCode) {
        switch (StatusCode) {
            case Constants.ERROR_REGISTGER_SUPPORTER /*-28*/:
                if (dialogLoading != null) {
                    dialogLoading.dismiss();
                    return;
                }
                return;
            case 28:
                Log.d("alireza", "alireza user registerd in payment");
                BaseResponseModel baseResponseModel = (BaseResponseModel) object;
                if (baseResponseModel.getCode() == 200) {
                    ToastUtil.AppToast(ApplicationLoader.applicationContext, "ثبت نام با موفقیت انجام شد").show();
                    finish();
                    return;
                }
                ToastUtil.AppToast(getApplicationContext(), baseResponseModel.getMessage()).show();
                if (dialogLoading != null) {
                    dialogLoading.dismiss();
                    return;
                }
                return;
            default:
                return;
        }
    }

    private void showLoadingDialog(Context context, String title, String message) {
        dialogLoading = new ProgressDialog(this);
        dialogLoading.setTitle(title);
        dialogLoading.setMessage(message);
        dialogLoading.show();
    }

    public static void registerSdkAp(final SdkRegisterCallback callback) {
        HandleRequest.getNew(ApplicationLoader.applicationContext, new IResponseReceiver() {
            public void onResult(Object object, int StatusCode) {
                switch (StatusCode) {
                    case 27:
                        HostRequestData hostRequestData = (HostRequestData) object;
                        Log.d("APPPPP:", new Gson().toJson(hostRequestData));
                        Bundle registerData = new Bundle();
                        registerData.putString(General.SECURE_TOKEN, AppPreferences.getSecurityToken(ApplicationLoader.applicationContext));
                        registerData.putInt(General.HOST_ID, 1152);
                        registerData.putString(General.PROTOCOL_VERSION, "1.8.0");
                        registerData.putString(General.HOST_DATA, hostRequestData.getHostRequest());
                        registerData.putString(General.HOST_DATA_SIGN, hostRequestData.getHostRequestSign());
                        String tmp = PhoneUtils.normalize(UserConfig.getCurrentUser().phone);
                        Log.d("alireza", "alireza phone no" + tmp);
                        registerData.putString(Register.MOBILE_NO, tmp);
                        new RegisterUserTask(registerData, callback).start(ApplicationLoader.applicationContext);
                        return;
                    default:
                        return;
                }
            }
        }).registerApSdk();
    }
}
