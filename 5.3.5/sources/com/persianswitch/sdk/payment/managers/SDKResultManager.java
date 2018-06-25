package com.persianswitch.sdk.payment.managers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.persianswitch.sdk.C0770R;
import com.persianswitch.sdk.api.Response;
import com.persianswitch.sdk.api.Response.General;
import com.persianswitch.sdk.api.Response.Payment;
import com.persianswitch.sdk.api.Response.Status;
import com.persianswitch.sdk.base.BaseSetting;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.base.webservice.StatusCode;
import com.persianswitch.sdk.base.webservice.data.WSTranRequest;
import com.persianswitch.sdk.payment.model.PaymentProfile;
import com.persianswitch.sdk.payment.model.TransactionStatus;

public final class SDKResultManager {
    public static Bundle onDecryptionError(Context context) {
        return buildResultBundle(Status.STATUS_DECRYPTION_ERROR, context.getString(C0770R.string.asanpardakht_message_error_2023));
    }

    public static Bundle onUserNotRegistered(Context context) {
        return buildResultBundle(Status.STATUS_REGISTER_NEEDED, context.getString(C0770R.string.asanpardakht_message_sdk_status_register_need));
    }

    public static Bundle onDeviceIsRoot(Context context) {
        int status = StatusCode.SECURITY_REQUIREMENT_NOT_PASSED.getCode();
        return buildResultBundle(status, StatusCode.getErrorMessage(context, status));
    }

    public static Bundle onPaymentCanceled(Context context) {
        return buildResultBundle(Status.STATUS_CANCELED, context.getString(C0770R.string.asanpardakht_message_sdk_status_canceled));
    }

    public static Bundle onPaymentTimeout(Context context) {
        return buildResultBundle(Status.STATUS_PAYMENT_TIMEOUT, context.getString(C0770R.string.asanpardakht_message_sdk_status_timeout));
    }

    public static Bundle onPaymentResult(Context context, @NonNull PaymentProfile paymentProfile) {
        if (paymentProfile.getStatusType() == TransactionStatus.SUCCESS) {
            return onPaymentSucceed(context, paymentProfile);
        }
        if (paymentProfile.isUnknown()) {
            return onPaymentUnknown(context, paymentProfile);
        }
        return onPaymentFailed(context, paymentProfile);
    }

    private static Bundle onPaymentSucceed(Context context, PaymentProfile paymentProfile) {
        Bundle resultBundle = buildResultBundle(paymentProfile.getStatusCode(), context.getString(C0770R.string.asanpardakht_message_sdk_status_successful));
        if (paymentProfile.getHostData() != null) {
            resultBundle.putString(General.HOST_RESPONSE, paymentProfile.getHostData());
        }
        if (paymentProfile.getHostDataSign() != null) {
            resultBundle.putString(General.HOST_RESPONSE_SIGN, paymentProfile.getHostDataSign());
        }
        if (paymentProfile.getUniqueTranId().longValue() > 0) {
            resultBundle.putLong(Payment.UNIQUE_TRAN_ID, paymentProfile.getUniqueTranId().longValue());
        }
        return resultBundle;
    }

    private static Bundle onPaymentFailed(Context context, PaymentProfile paymentProfile) {
        Bundle resultBundle = buildResultBundle(paymentProfile.getStatusCode(), context.getString(C0770R.string.asanpardakht_message_sdk_status_failed));
        if (paymentProfile.getHostData() != null) {
            resultBundle.putString(General.HOST_RESPONSE, paymentProfile.getHostData());
        }
        if (paymentProfile.getHostDataSign() != null) {
            resultBundle.putString(General.HOST_RESPONSE_SIGN, paymentProfile.getHostDataSign());
        }
        if (paymentProfile.getUniqueTranId().longValue() > 0) {
            resultBundle.putLong(Payment.UNIQUE_TRAN_ID, paymentProfile.getUniqueTranId().longValue());
        }
        return resultBundle;
    }

    private static Bundle onPaymentUnknown(Context context, PaymentProfile paymentProfile) {
        Bundle resultBundle = buildResultBundle(paymentProfile.getStatusCode(), context.getString(C0770R.string.asanpardakht_message_sdk_status_unknown));
        if (paymentProfile.getHostData() != null) {
            resultBundle.putString(General.HOST_RESPONSE, paymentProfile.getHostData());
        }
        if (paymentProfile.getHostDataSign() != null) {
            resultBundle.putString(General.HOST_RESPONSE_SIGN, paymentProfile.getHostDataSign());
        }
        if (paymentProfile.getUniqueTranId().longValue() > 0) {
            resultBundle.putLong(Payment.UNIQUE_TRAN_ID, paymentProfile.getUniqueTranId().longValue());
        }
        return resultBundle;
    }

    private static Bundle buildResultBundle(int status, String message) {
        Bundle bundle = new Bundle();
        bundle.putInt(General.STATUS_CODE, status);
        bundle.putString(General.MESSAGE, message);
        return bundle;
    }

    public static boolean isUserRegistered(Context context) {
        return BaseSetting.getApplicationId(context) > 0 && !StringUtils.isEmpty(BaseSetting.getMobileNumber(context));
    }

    public static void finishActivityWithResult(Activity activity, Bundle result) {
        Intent intent = new Intent();
        intent.putExtra(Response.BUNDLE_KEY, result);
        activity.setResult(-1, intent);
        activity.finish();
    }

    public static Long generateUniqueTranId(Context context, WSTranRequest wstRequest) {
        return Long.valueOf((BaseSetting.getApplicationId(context) * 100000000) + wstRequest.getTransactionId());
    }
}
