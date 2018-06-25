package com.persianswitch.sdk.payment.payment;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import com.persianswitch.sdk.C0770R;
import com.persianswitch.sdk.api.Request.General;
import com.persianswitch.sdk.api.Request.Payment;
import com.persianswitch.sdk.api.Response.Status;
import com.persianswitch.sdk.base.BaseSetting;
import com.persianswitch.sdk.base.jsevaluator.JsEvaluator;
import com.persianswitch.sdk.base.jsevaluator.interfaces.JsCallback;
import com.persianswitch.sdk.base.log.SDKLog;
import com.persianswitch.sdk.base.manager.LanguageManager;
import com.persianswitch.sdk.base.utils.RootUtils;
import com.persianswitch.sdk.base.utils.strings.Jsonable.JsonParseException;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.base.webservice.StatusCode;
import com.persianswitch.sdk.base.webservice.WebService;
import com.persianswitch.sdk.base.webservice.WebService.WSStatus;
import com.persianswitch.sdk.base.webservice.data.WSResponse;
import com.persianswitch.sdk.base.webservice.data.WSTranRequest;
import com.persianswitch.sdk.base.webservice.data.WSTranResponse;
import com.persianswitch.sdk.payment.SDKSetting;
import com.persianswitch.sdk.payment.managers.CardManager;
import com.persianswitch.sdk.payment.managers.SDKResultManager;
import com.persianswitch.sdk.payment.managers.ScheduledTaskManager;
import com.persianswitch.sdk.payment.managers.SyncManager;
import com.persianswitch.sdk.payment.model.Bank;
import com.persianswitch.sdk.payment.model.CVV2Status;
import com.persianswitch.sdk.payment.model.CardPin;
import com.persianswitch.sdk.payment.model.ClientConfig;
import com.persianswitch.sdk.payment.model.ClientConfig.RootConfigTypes;
import com.persianswitch.sdk.payment.model.Cvv2JsonParameter;
import com.persianswitch.sdk.payment.model.HostDataRequestField;
import com.persianswitch.sdk.payment.model.HostDataResponseField;
import com.persianswitch.sdk.payment.model.PaymentProfile;
import com.persianswitch.sdk.payment.model.SyncType;
import com.persianswitch.sdk.payment.model.SyncableData;
import com.persianswitch.sdk.payment.model.TransactionStatus;
import com.persianswitch.sdk.payment.model.UserCard;
import com.persianswitch.sdk.payment.model.UserCard.CardProtocolConverter;
import com.persianswitch.sdk.payment.model.req.AbsRequest;
import com.persianswitch.sdk.payment.model.req.InquiryMerchantRequest;
import com.persianswitch.sdk.payment.model.req.InquiryTransactionStatusRequest;
import com.persianswitch.sdk.payment.model.req.PaymentRequest;
import com.persianswitch.sdk.payment.model.req.TrustCodeRequest;
import com.persianswitch.sdk.payment.model.res.InquiryMerchantResponse;
import com.persianswitch.sdk.payment.model.res.TrustResponse;
import com.persianswitch.sdk.payment.payment.PaymentContract.ActionListener;
import com.persianswitch.sdk.payment.payment.PaymentContract.View;
import com.persianswitch.sdk.payment.repo.SyncRepo;
import com.persianswitch.sdk.payment.webservice.SDKWebServiceCallback;
import com.thin.downloadmanager.BuildConfig;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

class PaymentPresenter implements ActionListener {
    private static final String TAG = "PaymentPresenter";
    private boolean mCanContinueOnMerchantError;
    private CardManager mCardManager = new CardManager(getContext());
    private CountDownTimer mCountDownTimer;
    private CVV2Status mCvv2Status;
    private String mHostCardNo;
    private String mLastOperationCode;
    private long mLastSendPaymentDateTime;
    private String mLastTranId;
    private boolean mPaymentIsInProgress;
    private boolean mPaymentLaunched;
    private PaymentProfile mPaymentProfile;
    private long mPaymentStartedTime;
    private boolean mPaymentTimeout;
    private boolean mRecreated;
    private String mRequestHostData;
    private String mRequestHostDataSign;
    private String mSDKProtocolVersion;
    private final View mView;

    /* renamed from: com.persianswitch.sdk.payment.payment.PaymentPresenter$1 */
    class C08181 extends SDKWebServiceCallback<WSResponse> {
        C08181() {
        }

        public void onPreExecute() {
            PaymentPresenter.this.getView().showProgress();
        }

        public void onSuccessful(String message, WSResponse result) {
            PaymentPresenter.this.onMerchantInquirySuccessful(result);
        }

        public void onError(WSStatus error, String errorMessage, WSResponse response) {
            PaymentPresenter.this.onMerchantInquiryFailed(error, errorMessage, response);
        }
    }

    /* renamed from: com.persianswitch.sdk.payment.payment.PaymentPresenter$3 */
    class C08203 implements JsCallback {
        C08203() {
        }

        public void onResult(String value) {
            try {
                boolean cvv2Required = Boolean.parseBoolean(value);
                PaymentPresenter.this.mCvv2Status = cvv2Required ? CVV2Status.CVV2_REQUIRED : CVV2Status.CVV2_NOT_REQUIRED_STATUS;
                PaymentPresenter.this.getView().showCVV2(cvv2Required);
            } catch (Exception e) {
                SDKLog.m37e(PaymentPresenter.TAG, "error while invoke javascript for calculate cvv2/expiration status !", new Object[0]);
            }
        }
    }

    /* renamed from: com.persianswitch.sdk.payment.payment.PaymentPresenter$4 */
    class C08214 extends SDKWebServiceCallback<WSResponse> {
        C08214() {
        }

        public void onPreExecute() {
            PaymentPresenter.this.getView().showProgress();
        }

        public void onPreProcess() {
            PaymentPresenter.this.getView().dismissProgress();
        }

        public void onSuccessful(String message, WSResponse result) {
            try {
                TrustResponse trustResponse = new TrustResponse(PaymentPresenter.this.getContext(), result);
                Bundle dialogArg = new Bundle();
                dialogArg.putString("code", trustResponse.getTrustCode());
                dialogArg.putString("ussdDial", trustResponse.getUssdDial());
                dialogArg.putString("url", trustResponse.getWebURL());
                dialogArg.putBoolean("ussd_available", trustResponse.isUssdAvailable());
                dialogArg.putBoolean("web_available", trustResponse.isWebAvailable());
                dialogArg.putString("desc", trustResponse.getDescription());
                PaymentPresenter.this.getView().showTrustDialog(dialogArg);
            } catch (JsonParseException e) {
                PaymentPresenter.this.getView().showTrustCodeErrorDialog(WSStatus.PARSE_ERROR, PaymentPresenter.this.getContext().getString(C0770R.string.asanpardakht_message_error_bad_response), result);
            }
        }

        public void onError(WSStatus error, String errorMessage, WSResponse response) {
            PaymentPresenter.this.getView().showTrustCodeErrorDialog(error, errorMessage, response);
        }
    }

    /* renamed from: com.persianswitch.sdk.payment.payment.PaymentPresenter$5 */
    class C08225 extends SDKWebServiceCallback<WSResponse> {
        C08225() {
        }

        public void onPreExecute() {
        }

        public void onPreProcess() {
            PaymentPresenter.this.getView().dismissProgress();
        }

        public void onError(WSStatus error, String errorMessage, WSResponse response) {
            PaymentPresenter.this.onTransactionInquiryFailed();
        }

        public void onSuccessful(String message, WSResponse result) {
            PaymentPresenter.this.onTransactionInquirySuccessful(result);
        }
    }

    PaymentPresenter(View view) {
        this.mView = view;
    }

    private Context getContext() {
        return getView().getApplicationContext();
    }

    public View getView() {
        return this.mView;
    }

    private void extractDataFromBundle(Bundle transactionBundle) {
        this.mHostCardNo = StringUtils.keepNumbersOnly(transactionBundle.getString(Payment.HOST_CARD_NO, ""));
        this.mSDKProtocolVersion = transactionBundle.getString(General.PROTOCOL_VERSION);
        this.mRequestHostData = transactionBundle.getString(General.HOST_DATA, "");
        this.mRequestHostDataSign = transactionBundle.getString(General.HOST_DATA_SIGN, "");
    }

    public void onRequestPayment(Bundle dataBundle) {
        if (this.mRecreated && this.mPaymentProfile != null && this.mPaymentLaunched) {
            getView().showMerchantInfo(this.mPaymentProfile);
            getView().showTrustButton(ClientConfig.getInstance(getContext()).isTrustAvailable());
            getView().showProgress();
            doInquiryPaymentTransactionStatus();
            return;
        }
        extractDataFromBundle(dataBundle);
        doMerchantInquiry();
    }

    public void cancelTimer() {
        if (this.mCountDownTimer != null) {
            this.mCountDownTimer.cancel();
        }
    }

    public void doMerchantInquiry() {
        this.mCanContinueOnMerchantError = true;
        this.mPaymentProfile = new PaymentProfile();
        this.mPaymentProfile.setHostSuggestCardNo(this.mHostCardNo);
        String currentLang = LanguageManager.getInstance(getContext()).getCurrentLanguage();
        InquiryMerchantRequest inquiryRequest = new InquiryMerchantRequest();
        injectHostRequestData(inquiryRequest);
        inquiryRequest.setClientSyncRequest(new SyncManager(getContext()).getSyncRequest(currentLang));
        inquiryRequest.getWebService(getView().getApplicationContext()).launch(getContext(), new C08181());
    }

    private void injectHostRequestData(AbsRequest inquiryRequest) {
        inquiryRequest.setHostId(BaseSetting.getHostId(getContext()));
        inquiryRequest.setSDKProtocolVersion(this.mSDKProtocolVersion);
        inquiryRequest.setHostData(this.mRequestHostData);
        inquiryRequest.setHostSign(this.mRequestHostDataSign);
    }

    private void onMerchantInquiryFailed(WSStatus error, String errorMessage, WSResponse response) {
        getView().dismissProgress();
        this.mPaymentProfile.setStatusCode(1002);
        if (TransactionStatus.isUnknown(error, response)) {
            this.mCanContinueOnMerchantError = false;
        } else if (response.getHostData() != null) {
            HostDataResponseField hostData = HostDataResponseField.fromJson(response.getHostData().toString());
            if (hostData != null) {
                if (hostData.getStatusCode() > 0) {
                    this.mPaymentProfile.setStatusCode(hostData.getStatusCode());
                    this.mCanContinueOnMerchantError = false;
                } else {
                    this.mCanContinueOnMerchantError = true;
                }
                this.mPaymentProfile.setHostData(hostData.getHostDataResponse());
                this.mPaymentProfile.setHostDataSign(hostData.getHostDataSign());
            }
        }
        getView().showInquiryErrorDialog(errorMessage, this.mCanContinueOnMerchantError);
    }

    public void onMerchantErrorDialogNegativeClicked() {
        if (this.mCanContinueOnMerchantError) {
            cancelPayment();
        } else {
            getView().setPaymentResult(SDKResultManager.onPaymentResult(getContext(), this.mPaymentProfile));
        }
        this.mCanContinueOnMerchantError = true;
    }

    public void onRootErrorDialogNegativeClicked() {
        getView().setPaymentResult(SDKResultManager.onDeviceIsRoot(getContext()));
        this.mCanContinueOnMerchantError = true;
    }

    private void onMerchantInquirySuccessful(WSResponse response) {
        try {
            InquiryMerchantResponse inquiryResponse = new InquiryMerchantResponse(getContext(), response);
            this.mPaymentProfile.setServerData(inquiryResponse.getServerData());
            this.mPaymentProfile.setCvv2Status(inquiryResponse.getCVV2Status());
            this.mPaymentProfile.setCard(inquiryResponse.getDefaultCard());
            this.mPaymentProfile.setMerchantCode(inquiryResponse.getMerchantCode());
            this.mPaymentProfile.setMerchantName(inquiryResponse.getMerchantName());
            this.mPaymentProfile.setMerchantLogoURL(inquiryResponse.getMerchantLogoURL());
            this.mPaymentProfile.setServerMessage(inquiryResponse.getDescription());
            this.mPaymentProfile.setPaymentId(inquiryResponse.getPaymentId());
            this.mPaymentProfile.setDistributorMobileNumber(inquiryResponse.getDistributorMobileNo());
            this.mPaymentProfile.setToken(inquiryResponse.getTransactionToken());
            this.mPaymentProfile.setIsAutoSettle(StringUtils.isEquals(BuildConfig.VERSION_NAME, inquiryResponse.getAutoSettle()));
            if (StringUtils.isEquals(BuildConfig.VERSION_NAME, inquiryResponse.getAmountStatus())) {
                this.mPaymentProfile.setAmount(inquiryResponse.getAmount());
            } else {
                getView().showInquiryErrorDialog(getContext().getString(C0770R.string.asanpardakht_error_inquiry_amount), this.mCanContinueOnMerchantError);
            }
            String currentLang = LanguageManager.getInstance(getContext()).getCurrentLanguage();
            if (inquiryResponse.getSyncData() != null) {
                new SyncManager(getContext()).updateSyncData(inquiryResponse.getSyncData().toString(), currentLang);
            } else {
                SDKLog.m37e(TAG, "sync data is null", new Object[0]);
            }
            getView().showMerchantInfo(this.mPaymentProfile);
            getView().showTrustButton(ClientConfig.getInstance(getContext()).isTrustAvailable());
            getView().dismissProgress();
            showSecurityHints();
            startTimeoutTimer();
            checkForCVV2();
        } catch (JsonParseException e) {
            onMerchantInquiryFailed(WSStatus.PARSE_ERROR, getContext().getString(C0770R.string.asanpardakht_message_error_bad_response), response);
        }
    }

    public void doPayment() {
        boolean z;
        setPaymentInProgress(true);
        if (getView().getSelectedSuggestedCard() == null) {
            z = true;
        } else {
            z = false;
        }
        if (areInputsValid(z)) {
            UserCard tranCard;
            Context context = getContext();
            PaymentRequest request = new PaymentRequest();
            injectHostRequestData(request);
            request.setServerData(this.mPaymentProfile.getServerData());
            request.setMerchantCode(this.mPaymentProfile.getMerchantCode());
            request.setPaymentId(this.mPaymentProfile.getPaymentId());
            request.setDistributorMobileNo(this.mPaymentProfile.getDistributorMobileNumber());
            request.setToken(this.mPaymentProfile.getToken());
            WSTranRequest wstRequest = (WSTranRequest) request.getWebServiceDescriptor(context).getRequest();
            Long optAmount = StringUtils.toLong(this.mPaymentProfile.getAmount());
            if (optAmount != null) {
                wstRequest.setAmount(optAmount.longValue());
            }
            String cardNo = StringUtils.keepNumbersOnly(getView().getCard());
            if (getView().getSelectedSuggestedCard() == null) {
                tranCard = new UserCard(cardNo);
                tranCard.setRegisterCard(true);
            } else {
                tranCard = getView().getSelectedSuggestedCard();
            }
            boolean sendCards = ScheduledTaskManager.SyncCardTask.checkForRun(context, System.currentTimeMillis());
            tranCard.setSendCards(sendCards);
            this.mPaymentProfile.setCard(tranCard);
            wstRequest.setHostData(new HostDataRequestField(this.mRequestHostData, this.mRequestHostDataSign, this.mSDKProtocolVersion, AbsRequest.getHostVersion(context)).toJson());
            wstRequest.setPin(new CardPin(getView().getPin2(), getView().getCvv2(), getView().getExpMonth(), getView().getExpYear(), getView().isExpirationEditedByUser()).toString());
            wstRequest.setCard(new CardProtocolConverter(context).serialize(tranCard));
            final WSTranRequest wSTranRequest = wstRequest;
            final Context context2 = context;
            final boolean z2 = sendCards;
            WebService.create(wstRequest).launch(context, new SDKWebServiceCallback<WSTranResponse>() {
                public void onPreExecute() {
                    SDKLog.m39i(PaymentPresenter.TAG, "payment launched.", new Object[0]);
                    PaymentPresenter.this.mLastTranId = String.valueOf(wSTranRequest.getTransactionId());
                    PaymentPresenter.this.mLastOperationCode = String.valueOf(wSTranRequest.getOperationCode());
                    PaymentPresenter.this.mLastSendPaymentDateTime = System.currentTimeMillis();
                    PaymentPresenter.this.mPaymentLaunched = true;
                    PaymentPresenter.this.getView().showProgress();
                }

                public void onPreProcess() {
                    PaymentPresenter.this.mPaymentProfile.setUniqueTranId(SDKResultManager.generateUniqueTranId(context2, wSTranRequest).longValue());
                }

                public void onSuccessful(String message, WSTranResponse result) {
                    PaymentPresenter.this.onPaymentSuccessful(result, z2, tranCard);
                }

                public void onError(WSStatus error, String errorMessage, WSTranResponse result) {
                    PaymentPresenter.this.onPaymentError(error, errorMessage, result, tranCard, z2);
                }
            });
        }
    }

    public void setPaymentInProgress(boolean val) {
        this.mPaymentIsInProgress = val;
    }

    public void checkForCVV2() {
        long bankId;
        String currentLang = LanguageManager.getInstance(getContext()).getCurrentLanguage();
        if (getView().getSelectedSuggestedCard() == null) {
            bankId = Bank.getByCardNo(StringUtils.keepNumbersOnly(getView().getCard())).getBankId();
        } else {
            bankId = getView().getSelectedSuggestedCard().getBankId().longValue();
        }
        SyncableData javascript = new SyncRepo(getContext()).findByType(SyncType.CVV2_JAVASCRIPT, currentLang);
        Cvv2JsonParameter jsParam = new Cvv2JsonParameter();
        jsParam.HostId = BaseSetting.getHostId(getContext()) + "";
        jsParam.Cvv2Force = this.mPaymentProfile.getCvv2Status() == CVV2Status.CVV2_REQUIRED ? BuildConfig.VERSION_NAME : "0";
        jsParam.OpCode = "209";
        jsParam.Amount = this.mPaymentProfile.getAmount();
        jsParam.BankId = bankId + "";
        jsParam.MerchantCode = this.mPaymentProfile.getMerchantCode();
        jsParam.Source = "";
        jsParam.MobileNo = BaseSetting.getMobileNumber(getContext());
        if (javascript != null && !StringUtils.isEmpty(javascript.getData())) {
            new JsEvaluator(getContext()).callFunction(javascript.getData(), new C08203(), "isInternetChanel", jsParam.toJson());
        }
    }

    public void onRequestTrustCode() {
        TrustCodeRequest request = new TrustCodeRequest();
        injectHostRequestData(request);
        request.getWebService(getContext()).launch(getContext(), new C08214());
    }

    public void showSecurityHints() {
        if (RootUtils.isRooted() && ClientConfig.getInstance(getContext()).getRootConfig() == RootConfigTypes.EnableWithWarning) {
            if (SDKSetting.getRootWarningShowed(getContext())) {
                showValidNumberDialog();
            } else {
                getView().showIsRootWarningDialog();
            }
        } else if (RootUtils.isRooted() && ClientConfig.getInstance(getContext()).getRootConfig() == RootConfigTypes.Disable) {
            SDKSetting.setRootWarningShowed(getContext(), false);
            getView().showIsRootDisableDialog();
        } else {
            SDKSetting.setRootWarningShowed(getContext(), false);
            showValidNumberDialog();
        }
    }

    private void showValidNumberDialog() {
        if (ClientConfig.getInstance(getContext()).getShowValidationDialog() && !SDKSetting.getNumberValidateDialogIsShowed(getContext())) {
            getView().showValidateNumberDialog();
        }
    }

    public void proceedRootWarningDialog() {
        SDKSetting.setRootWarningShowed(getContext(), true);
        if (!SDKSetting.getNumberValidateDialogIsShowed(getContext())) {
            getView().showValidateNumberDialog();
        }
    }

    public void setMobileNumberValidated() {
        SDKSetting.setNumberValidateDialogIsShowed(getContext(), true);
    }

    public void onValidateNumberNegativeClicked() {
        this.mPaymentProfile.setStatusType(TransactionStatus.FAIL);
        this.mPaymentProfile.setStatusCode(Status.STATUS_INVALID_USER_DATA);
        getView().setPaymentResult(SDKResultManager.onPaymentResult(getContext(), this.mPaymentProfile));
    }

    private void doInquiryPaymentTransactionStatus() {
        InquiryTransactionStatusRequest request = new InquiryTransactionStatusRequest();
        injectHostRequestData(request);
        request.setTranId(this.mLastTranId);
        request.setOperationCode(this.mLastOperationCode);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(this.mLastSendPaymentDateTime);
        request.setPaymentDateTime(new SimpleDateFormat("yyyyMMddHHmmss", Locale.US).format(calendar.getTime()));
        long waitTimeMillis = ClientConfig.getInstance(getContext()).getInquiryWaitTimeMillis() - Math.abs(System.currentTimeMillis() - this.mLastSendPaymentDateTime);
        if (waitTimeMillis < 0) {
            waitTimeMillis = 0;
        } else {
            SDKLog.m39i(TAG, "inquiry waited for %d milli seconds. config = %d", Long.valueOf(waitTimeMillis), Long.valueOf(inquiryWaitTimeMillis));
        }
        request.getWebService(getContext()).launch(getContext(), waitTimeMillis, new C08225());
    }

    private void onPaymentSuccessful(WSTranResponse result, boolean sendCards, UserCard tranCard) {
        this.mCardManager.syncByResponse(sendCards, tranCard, result, getView());
        HostDataResponseField hostData = null;
        if (result.getHostData() != null) {
            hostData = HostDataResponseField.fromJson(result.getHostData().toString());
        }
        if (verifyResponse(hostData)) {
            this.mPaymentProfile.setStatusCode(0);
            this.mPaymentProfile.setStatusType(TransactionStatus.SUCCESS);
            this.mPaymentProfile.setHostData(hostData.getHostDataResponse());
            this.mPaymentProfile.setHostDataSign(hostData.getHostDataSign());
            this.mPaymentProfile.setPoint(result.getPoint());
            this.mPaymentProfile.setServerMessage(result.getDescription());
            this.mPaymentProfile.setReferenceNumber(result.getReferenceNumber());
            getView().dismissProgress();
            getView().showReportFragment(this.mPaymentProfile);
            return;
        }
        onPaymentError(WSStatus.SERVER_INTERNAL_ERROR, "", null, tranCard, sendCards);
    }

    private boolean verifyResponse(HostDataResponseField hostData) {
        return (hostData == null || StringUtils.isEmpty(hostData.getHostDataResponse()) || StringUtils.isEmpty(hostData.getHostDataSign())) ? false : true;
    }

    private void onPaymentError(WSStatus error, String errorMessage, WSTranResponse result, UserCard tranCard, boolean sendCards) {
        if (TransactionStatus.isUnknown(error, result)) {
            this.mPaymentProfile.setStatusType(TransactionStatus.UNKNOWN);
            this.mPaymentProfile.setStatusCode(1001);
            if (!(result == null || result.getHostData() == null)) {
                HostDataResponseField hostResponseData = HostDataResponseField.fromJson(result.getHostData().toString());
                if (hostResponseData != null && hostResponseData.getStatusCode() > 0) {
                    this.mPaymentProfile.setHostData(hostResponseData.getHostDataSign());
                    this.mPaymentProfile.setHostDataSign(hostResponseData.getHostDataSign());
                    this.mPaymentProfile.setStatusCode(hostResponseData.getStatusCode());
                }
            }
            SDKLog.m39i(TAG, "try to inquiry the unknown transaction state", new Object[0]);
            doInquiryPaymentTransactionStatus();
            return;
        }
        this.mCardManager.syncByResponse(sendCards, tranCard, result, getView());
        this.mPaymentProfile.setUniqueTranId(0);
        this.mPaymentProfile.setPoint(result.getPoint());
        this.mPaymentProfile.setServerMessage(result.getErrorMessage(getContext()));
        this.mPaymentProfile.setReferenceNumber(result.getReferenceNumber());
        boolean terminatePayment = false;
        this.mPaymentProfile.setStatusCode(1001);
        this.mPaymentProfile.setStatusType(TransactionStatus.FAIL);
        if (result.getHostData() != null) {
            HostDataResponseField hostData = HostDataResponseField.fromJson(result.getHostData().toString());
            if (hostData != null) {
                if (hostData.getStatusCode() > 0) {
                    this.mPaymentProfile.setStatusCode(hostData.getStatusCode());
                    terminatePayment = true;
                }
                this.mPaymentProfile.setHostData(hostData.getHostDataResponse());
                this.mPaymentProfile.setHostDataSign(hostData.getHostDataSign());
            }
        }
        getView().dismissProgress();
        if (terminatePayment) {
            getView().showReportFragment(this.mPaymentProfile);
            return;
        }
        if (result.getStatus().getCode() == StatusCode.NEED_SEND_CVV2.getCode() || result.getStatus().getCode() == StatusCode.NEED_SEND_CVV2_GLOBAL.getCode()) {
            this.mCvv2Status = CVV2Status.CVV2_REQUIRED;
            getView().showCVV2(true);
        }
        getView().showPaymentErrorDialog(errorMessage, this.mPaymentProfile);
    }

    public void cancelPayment() {
        getView().setPaymentResult(SDKResultManager.onPaymentCanceled(getContext()));
    }

    private void onTransactionInquirySuccessful(WSResponse result) {
        int statusCode = Integer.parseInt(result.getLegacyExtraData()[0]);
        TransactionStatus transactionStatusType = extractInquiredStatusType(statusCode);
        String serverMessage = result.getLegacyExtraData()[1];
        SDKLog.m39i(TAG, "inquiry transactions successful with status : %s", transactionStatusType);
        if (StringUtils.isEmpty(serverMessage)) {
            serverMessage = StatusCode.getErrorMessage(getContext(), statusCode);
        }
        String referenceNumber = result.getLegacyExtraData()[2];
        String optHostData = result.getLegacyExtraData()[4];
        this.mPaymentProfile.setStatusType(transactionStatusType);
        this.mPaymentProfile.setServerMessage(serverMessage);
        this.mPaymentProfile.setReferenceNumber(referenceNumber);
        int hostDataStatusCode = 0;
        if (!StringUtils.isEmpty(optHostData)) {
            HostDataResponseField hostData = HostDataResponseField.fromJson(optHostData);
            if (hostData != null) {
                hostDataStatusCode = hostData.getStatusCode();
                this.mPaymentProfile.setHostData(hostData.getHostDataResponse());
                this.mPaymentProfile.setHostDataSign(hostData.getHostDataSign());
            }
        }
        boolean terminatePayment = false;
        switch (transactionStatusType) {
            case SUCCESS:
                terminatePayment = true;
                this.mPaymentProfile.setStatusCode(0);
                break;
            case FAIL:
                if (hostDataStatusCode <= 0) {
                    terminatePayment = false;
                    this.mPaymentProfile.setStatusCode(1001);
                    break;
                }
                terminatePayment = true;
                this.mPaymentProfile.setStatusCode(hostDataStatusCode);
                break;
            case UNKNOWN:
                terminatePayment = true;
                if (hostDataStatusCode <= 0) {
                    hostDataStatusCode = 1001;
                }
                this.mPaymentProfile.setStatusCode(hostDataStatusCode);
                break;
        }
        if (terminatePayment) {
            getView().showReportFragment(this.mPaymentProfile);
        } else {
            getView().showPaymentErrorDialog(serverMessage, this.mPaymentProfile);
        }
    }

    private void onTransactionInquiryFailed() {
        SDKLog.m39i(TAG, "error while inquiry transaction status", new Object[0]);
        getView().showReportFragment(this.mPaymentProfile);
    }

    public void startTimeoutTimer() {
        cancelTimer();
        long timeoutThreshold = ClientConfig.getInstance(getContext()).getTransactionTimeout() * 1000;
        final long j = timeoutThreshold;
        this.mCountDownTimer = new CountDownTimer(timeoutThreshold, 1000) {
            public void onTick(long millisUntilFinished) {
                checkForTimeout();
            }

            private void checkForTimeout() {
                long passedMillis = System.currentTimeMillis() - PaymentPresenter.this.mPaymentStartedTime;
                if (passedMillis >= j) {
                    cancel();
                    PaymentPresenter.this.mPaymentTimeout = true;
                    PaymentPresenter.this.checkPaymentTimeout();
                    return;
                }
                PaymentPresenter.this.getView().onUpdateTimer(j - passedMillis);
            }

            public void onFinish() {
                checkForTimeout();
            }
        };
        this.mCountDownTimer.start();
    }

    public void checkPaymentTimeout() {
        if (this.mPaymentTimeout && !this.mPaymentIsInProgress) {
            getView().showTimeoutError();
        }
    }

    public void terminatePaymentDeuTimeout() {
        getView().setPaymentResult(SDKResultManager.onPaymentTimeout(getContext()));
    }

    private TransactionStatus extractInquiredStatusType(int statusCode) {
        if (statusCode == 0) {
            return TransactionStatus.SUCCESS;
        }
        if (statusCode == 25 || statusCode == StatusCode.TRANSACTION_NOT_FOUND.getCode()) {
            return TransactionStatus.FAIL;
        }
        return StatusCode.getByCode(statusCode).isUnknown() ? TransactionStatus.UNKNOWN : TransactionStatus.FAIL;
    }

    private boolean areInputsValid(boolean cardEnteredDirectly) {
        if (cardEnteredDirectly && getView().getCard().equals("")) {
            getView().setCardError(getContext().getString(C0770R.string.asanpardakht_input_error_is_empty), true);
            return false;
        } else if (cardEnteredDirectly && getView().getCard().length() < 16) {
            getView().setCardError(getContext().getString(C0770R.string.asanpardakht_input_error_card_length), true);
            return false;
        } else if (getView().getPin2().equals("")) {
            getView().setPin2Error(getContext().getString(C0770R.string.asanpardakht_input_error_is_empty), true);
            return false;
        } else if (getView().getPin2().length() < 5) {
            getView().setPin2Error(getContext().getString(C0770R.string.asanpardakht_input_error_pin_length), true);
            return false;
        } else {
            if (this.mCvv2Status == CVV2Status.CVV2_REQUIRED) {
                if (getView().getCvv2().equals("")) {
                    getView().setCvv2Error(getContext().getString(C0770R.string.asanpardakht_input_error_is_empty), true);
                    return false;
                } else if (getView().getCvv2().length() < 3) {
                    getView().setCvv2Error(getContext().getString(C0770R.string.asanpardakht_input_error_cvv2_length), true);
                    return false;
                } else if (getView().getExpMonth().equals("")) {
                    getView().setExpMonthError(getContext().getString(C0770R.string.asanpardakht_input_error_is_empty), true);
                    return false;
                } else if (getView().getExpMonth().length() < 2) {
                    getView().setExpMonthError(getContext().getString(C0770R.string.asanpardakht_input_month_length), true);
                    return false;
                } else {
                    int month = Integer.parseInt(getView().getExpMonth());
                    if (month < 1 || month > 12) {
                        getView().setExpMonthError(getContext().getString(C0770R.string.asanpardakht_input_month_range), true);
                        return false;
                    } else if (getView().getExpYear().equals("")) {
                        getView().setExpYearError(getContext().getString(C0770R.string.asanpardakht_input_error_is_empty), true);
                        return false;
                    } else if (getView().getExpYear().length() < 2) {
                        getView().setExpYearError(getContext().getString(C0770R.string.asanpardakht_input_year_length), true);
                        return false;
                    }
                }
            }
            return true;
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putLong("payment_started_time", this.mPaymentStartedTime);
        outState.putParcelable("payment_profile", this.mPaymentProfile);
        outState.putString(Payment.HOST_CARD_NO, this.mHostCardNo);
        outState.putBoolean("payment_launched", this.mPaymentLaunched);
        outState.putString("tran_id", this.mLastTranId);
        outState.putString("op_code", this.mLastOperationCode);
        outState.putLong("send_payment_time_millis", this.mLastSendPaymentDateTime);
        outState.putString("sdk_protocol_ver", this.mSDKProtocolVersion);
        outState.putString("req_host_data", this.mRequestHostData);
        outState.putString("req_host_data_sign", this.mRequestHostDataSign);
    }

    public void onRecoverInstanceState(Bundle savedState) {
        SDKLog.m35d(TAG, "onRecoverInstanceState Called ?" + String.valueOf(savedState != null), new Object[0]);
        if (savedState != null) {
            this.mRecreated = true;
            this.mPaymentStartedTime = savedState.getLong("payment_started_time", System.currentTimeMillis());
            this.mPaymentProfile = (PaymentProfile) savedState.getParcelable("payment_profile");
            this.mHostCardNo = savedState.getString(Payment.HOST_CARD_NO);
            this.mPaymentLaunched = savedState.getBoolean("payment_launched");
            this.mLastTranId = savedState.getString("tran_id");
            this.mLastOperationCode = savedState.getString("op_code");
            this.mLastSendPaymentDateTime = savedState.getLong("send_payment_time_millis");
            this.mSDKProtocolVersion = savedState.getString("sdk_protocol_ver");
            this.mRequestHostData = savedState.getString("req_host_data");
            this.mRequestHostDataSign = savedState.getString("req_host_data_sign");
            return;
        }
        this.mPaymentStartedTime = System.currentTimeMillis();
    }
}
