package com.persianswitch.sdk.payment.managers;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.RemoteException;
import com.persianswitch.sdk.BuildConfig;
import com.persianswitch.sdk.api.IPaymentService.Stub;
import com.persianswitch.sdk.api.Request.General;
import com.persianswitch.sdk.api.Request.Register;
import com.persianswitch.sdk.api.Response;
import com.persianswitch.sdk.base.BaseSetting;
import com.persianswitch.sdk.base.log.SDKLog;
import com.persianswitch.sdk.base.manager.LanguageManager;
import com.persianswitch.sdk.base.preference.SqliteSecurePreference;
import com.persianswitch.sdk.base.security.DeviceInfo;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.base.webservice.ResultPack;
import com.persianswitch.sdk.base.webservice.data.WSResponse;
import com.persianswitch.sdk.payment.SDKConfig;
import com.persianswitch.sdk.payment.SDKSetting;
import com.persianswitch.sdk.payment.model.HostDataResponseField;
import com.persianswitch.sdk.payment.model.TransactionStatus;
import com.persianswitch.sdk.payment.model.req.ReVerificationRequest;
import com.persianswitch.sdk.payment.model.req.RegisterRequest;
import com.persianswitch.sdk.payment.model.req.SendActivationRequest;
import com.persianswitch.sdk.payment.payment.PaymentActivity;
import com.persianswitch.sdk.payment.webservice.SDKSyncWebServiceCallback;
import org.json.JSONException;

public class ServiceManager extends Stub {
    public static String PAYMENT_PAGE_DATA_BUNDLE_KEY = "dataBundle";
    private static final String TAG = "ServiceManager";
    private final Context mContext;

    public ServiceManager(Context context) {
        this.mContext = context;
    }

    public void initSDK(Bundle config) throws RemoteException {
        SDKConfig.injectQAConfig(config);
        if (LanguageManager.PERSIAN.equalsIgnoreCase(config.getString("language"))) {
            LanguageManager.getInstance(this.mContext).setCurrentLanguage(LanguageManager.PERSIAN);
        } else {
            LanguageManager.getInstance(this.mContext).setCurrentLanguage(LanguageManager.ENGLISH);
        }
    }

    public String getSDKVersion() throws RemoteException {
        return BuildConfig.SDK_VERSION;
    }

    public Bundle sendActivationCode(Bundle registerData) {
        Bundle bundle = new Bundle();
        SendActivationRequest request = new SendActivationRequest();
        request.setHostId(registerData.getInt(General.HOST_ID));
        request.setSDKProtocolVersion(registerData.getString(General.PROTOCOL_VERSION));
        request.setHostData(registerData.getString(General.HOST_DATA));
        request.setHostSign(registerData.getString(General.HOST_DATA_SIGN));
        request.setTablet(DeviceInfo.isTablet(this.mContext));
        request.setModel(DeviceInfo.getDeviceModel());
        request.setMobileNo(registerData.getString(Register.MOBILE_NO));
        request.setIMEI(registerData.getString(Register.IMEI));
        request.setWifiMac(registerData.getString(Register.WIFI_MAC));
        ResultPack resultPack = request.getSyncWebservice(this.mContext).syncLaunch(this.mContext, new SDKSyncWebServiceCallback());
        WSResponse wsResponse = resultPack.getResponse();
        if (resultPack.getStatus() == TransactionStatus.SUCCESS) {
            String activationId = wsResponse.getLegacyExtraData()[0];
            if (wsResponse.getExtraData() != null && wsResponse.getExtraData().has("locktime")) {
                try {
                    bundle.putInt(Response.Register.WAIT_TIME, wsResponse.getExtraData().getInt("locktime"));
                } catch (JSONException e) {
                    SDKLog.m36e(TAG, "error while read lock_time from extra data", e, new Object[0]);
                }
            }
            bundle.putString(Response.Register.ACTIVATION_ID, activationId);
            bundle.putInt(Response.General.STATUS_CODE, 0);
        } else {
            bundle.putInt(Response.General.STATUS_CODE, 1001);
            if (!(wsResponse == null || wsResponse.getExtraData() == null || !wsResponse.getExtraData().has("locktime"))) {
                try {
                    bundle.putInt(Response.Register.WAIT_TIME, wsResponse.getExtraData().getInt("locktime"));
                } catch (JSONException e2) {
                    SDKLog.m36e(TAG, "error while read lock_time from extra data", e2, new Object[0]);
                }
            }
            if (wsResponse == null || wsResponse.getHostData() == null) {
                bundle.putString(Response.General.HOST_RESPONSE, "");
                bundle.putString(Response.General.HOST_RESPONSE_SIGN, "");
            } else {
                HostDataResponseField hdata = HostDataResponseField.fromJson(wsResponse.getHostData().toString());
                if (hdata != null) {
                    if (hdata.getStatusCode() > 0) {
                        bundle.putInt(Response.General.STATUS_CODE, hdata.getStatusCode());
                    }
                    bundle.putString(Response.General.HOST_RESPONSE, hdata.getHostDataResponse());
                    bundle.putString(Response.General.HOST_RESPONSE_SIGN, hdata.getHostDataSign());
                }
            }
        }
        return bundle;
    }

    public Bundle registerUser(Bundle registerData) throws RemoteException {
        RegisterRequest request;
        initSecurePreference(registerData);
        boolean needReVerification = BaseSetting.isNeedReVerification(this.mContext);
        if (!needReVerification) {
            DataManager.wipeAllData(this.mContext);
            initSecurePreference(registerData);
            SqliteSecurePreference.setEncryptionCheckPass(BaseSetting.getSecurePreferences(this.mContext));
            SqliteSecurePreference.setEncryptionCheckPass(SDKSetting.getSecurePreferences(this.mContext));
        }
        String mobileNo = registerData.getString(Register.MOBILE_NO, "");
        String IMEI = registerData.getString(Register.IMEI, "");
        String wifiMAC = registerData.getString(Register.WIFI_MAC, "");
        BaseSetting.setIMEI(this.mContext, IMEI);
        BaseSetting.setWifiMAC(this.mContext, wifiMAC);
        Bundle bundle = new Bundle();
        if (needReVerification) {
            request = new ReVerificationRequest();
        } else {
            request = new RegisterRequest();
        }
        request.setMobileNo(mobileNo);
        request.setActivationId(registerData.getString(Register.ACTIVATION_ID));
        request.setActivationCode(registerData.getString(Register.ACTIVATION_CODE));
        request.setAndroidId(DeviceInfo.getAndroidID(this.mContext));
        request.setIMEI(IMEI);
        request.setWifiMAC(wifiMAC);
        request.setHostId(registerData.getInt(General.HOST_ID));
        request.setSDKProtocolVersion(registerData.getString(General.PROTOCOL_VERSION));
        request.setHostData(registerData.getString(General.HOST_DATA));
        request.setHostSign(registerData.getString(General.HOST_DATA_SIGN));
        request.setAuthCode(DeviceInfo.generateAuthCode(this.mContext, request.getMobileNo()));
        request.setAndroidVersion(VERSION.RELEASE);
        request.setTablet(DeviceInfo.isTablet(this.mContext));
        request.setModel(DeviceInfo.getDeviceModel());
        request.setDeviceId(DeviceInfo.generateDeviceIdentifier(this.mContext));
        ResultPack resultPack = request.getSyncWebservice(this.mContext).syncLaunch(this.mContext, new SDKSyncWebServiceCallback());
        WSResponse wsResponse = resultPack.getResponse();
        if (resultPack.getStatus() == TransactionStatus.SUCCESS) {
            String[] extraData = wsResponse.getLegacyExtraData();
            Long applicationId = StringUtils.toLong(extraData[0]);
            String mode = extraData[1];
            String applicationToken = extraData[2];
            if (applicationId != null) {
                BaseSetting.setApplicationId(this.mContext, applicationId.longValue());
            }
            BaseSetting.setApplicationToken(this.mContext, applicationToken);
            BaseSetting.setHostId(this.mContext, request.getHostId());
            BaseSetting.setMobileNumber(this.mContext, request.getMobileNo());
            bundle.putInt(Response.General.STATUS_CODE, 0);
            bundle.putString(Response.General.HOST_RESPONSE, wsResponse.getHostData().toString());
            try {
                CardManager cardsManager = new CardManager(this.mContext);
                cardsManager.syncInCurrentThread(cardsManager.getDefaultCallback());
            } catch (Exception e) {
                SDKLog.m37e(TAG, "error in sync cards in register", new Object[0]);
            }
        } else {
            bundle.putInt(Response.General.STATUS_CODE, 1001);
            if (!(wsResponse == null || wsResponse.getExtraData() == null || !wsResponse.getExtraData().has("locktime"))) {
                try {
                    bundle.putInt(Response.Register.WAIT_TIME, wsResponse.getExtraData().getInt("locktime"));
                } catch (JSONException e2) {
                    SDKLog.m36e(TAG, "error while read lock_time from extra data", e2, new Object[0]);
                }
            }
            if (wsResponse == null || wsResponse.getHostData() == null) {
                bundle.putString(Response.General.HOST_RESPONSE, "");
                bundle.putString(Response.General.HOST_RESPONSE_SIGN, "");
            } else {
                HostDataResponseField hdata = HostDataResponseField.fromJson(wsResponse.getHostData().toString());
                if (hdata != null) {
                    if (hdata.getStatusCode() > 0) {
                        bundle.putInt(Response.General.STATUS_CODE, hdata.getStatusCode());
                    }
                    bundle.putString(Response.General.HOST_RESPONSE, hdata.getHostDataResponse());
                    bundle.putString(Response.General.HOST_RESPONSE_SIGN, hdata.getHostDataSign());
                }
            }
        }
        return bundle;
    }

    public Bundle getPaymentIntent(Bundle request) throws RemoteException {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this.mContext, PaymentActivity.class);
        intent.putExtra(PAYMENT_PAGE_DATA_BUNDLE_KEY, request);
        bundle.putParcelable("payment_intent", PendingIntent.getActivity(this.mContext, 0, intent, 134217728));
        return bundle;
    }

    private void initSecurePreference(Bundle registerData) {
        String secureToken = registerData.getString(General.SECURE_TOKEN, "");
        BaseSetting.initSecurePreference(secureToken);
        SDKSetting.initSecurePreference(secureToken);
    }
}
