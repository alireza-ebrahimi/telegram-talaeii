package com.persianswitch.sdk.base.webservice;

import android.content.Context;
import android.support.v4.view.PointerIconCompat;
import com.persianswitch.sdk.BuildConfig;
import com.persianswitch.sdk.C0770R;
import com.persianswitch.sdk.api.Response.Status;
import com.persianswitch.sdk.base.webservice.data.WSResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum StatusCode {
    UNDEFINED_STATUS(-1),
    SUCCESS(0, C0770R.string.asanpardakht_message_success),
    FINANCIAL_ERROR(999),
    UNKNOWN_TRANSACTION_RESULT(1001, C0770R.string.asanpardakht_message_error_1001, true),
    FAILED_TRANSACTION(1002, C0770R.string.asanpardakht_message_error_1002),
    GLOBAL_WARNING(PointerIconCompat.TYPE_ALIAS, C0770R.string.asanpardakht_message_error_1010),
    DUPLICATE_MERCHANT_TRANSACTION(1098, C0770R.string.asanpardakht_message_error_1098),
    INVALID_MERCHANT_TIME(1099, C0770R.string.asanpardakht_message_error_1099),
    MERCHANT_BAD_REQUEST(Status.STATUS_INVALID_HOST_REQUEST, C0770R.string.asanpardakht_message_error_1100),
    BAD_REQUEST(1101, C0770R.string.asanpardakht_message_error_1101),
    APP_NOT_REGISTERED(Status.STATUS_REGISTER_NEEDED, C0770R.string.asanpardakht_message_error_1102),
    APP_HAS_BEEN_DEACTIVATED(Status.STATUS_MOBILE_DEACTIVATED, C0770R.string.asanpardakht_message_error_1103),
    APP_WORM_STATUS(1104, C0770R.string.asanpardakht_message_error_1104),
    APP_MUST_UPDATE(Status.STATUS_SDK_NEED_UPDATE, C0770R.string.asanpardakht_message_error_1105),
    WRONG_ACTIVATION_CODE(1106, C0770R.string.asanpardakht_message_error_1106),
    WRONG_ACTIVATION_DATA(1107, C0770R.string.asanpardakht_message_error_1107),
    APP_HAS_ACTIVE_USER(BuildConfig.SETARE_AVAL_HOST_ID, C0770R.string.asanpardakht_message_error_1108),
    MAX_TRY_PASSWORD(1109, C0770R.string.asanpardakht_message_error_1109),
    SOON_SHAKE_AND_SAVE(1110, C0770R.string.asanpardakht_message_error_1110),
    TRANSACTION_NOT_FOUND(1111, C0770R.string.asanpardakht_message_error_1111),
    TRANSACTION_CAN_NOT_RESTORE(1112, C0770R.string.asanpardakht_message_error_1112),
    INVALID_CARD_NO(1113, C0770R.string.asanpardakht_message_error_1113),
    CARD_NOT_FOUND(1114, C0770R.string.asanpardakht_message_error_1114),
    CARD_HAS_BEEN_DEACTIVATED(1115, C0770R.string.asanpardakht_message_error_1115),
    SYNC_TIME_BY_SERVER_FAILED(Status.STATUS_SERVER_TIME_NOT_SYNCED, C0770R.string.asanpardakht_message_error_1116),
    NEED_APP_RE_VERIFICATION(1117, C0770R.string.asanpardakht_message_error_1117),
    NEED_SEND_CVV2_GLOBAL(1118, C0770R.string.asanpardakht_message_error_1118),
    NEED_SSL_CONNECTION(1119, C0770R.string.asanpardakht_message_error_1119),
    USER_IS_EXIST(1120, C0770R.string.asanpardakht_message_error_1120),
    WRONG_USER_OR_PASSWORD(1121, C0770R.string.asanpardakht_message_error_1121),
    NEED_SEND_CVV2(1123, C0770R.string.asanpardakht_message_error_1123),
    EXPIRATION_DATE_NOT_FOUND(1124, C0770R.string.asanpardakht_message_error_1124),
    MOBILE_OPERATOR_NOT_SPECIFY(1126, C0770R.string.asanpardakht_message_error_1126),
    SECURITY_REQUIREMENT_NOT_PASSED(1127, C0770R.string.asanpardakht_message_error_1127),
    NON_SECURITY_REQUIREMENT_NOT_PASSED(1128, C0770R.string.asanpardakht_message_error_1128),
    SYNC_DATA_CODE_NOT_FOUND(1130, C0770R.string.asanpardakht_message_error_1130),
    INVALID_MERCHANT_CODE(1135, C0770R.string.asanpardakht_message_error_1135),
    WRONG_UPLOAD_INFORMATION(1140, C0770R.string.asanpardakht_message_error_1140),
    WRONG_UPLOAD_FILE_SIZE(1141, C0770R.string.asanpardakht_message_error_1140),
    INVALID_FILE_FOR_UPLOAD(1142, C0770R.string.asanpardakht_message_error_1142),
    TOO_LONG_FILE_FOR_UPLOAD(1143, C0770R.string.asanpardakht_message_error_1143),
    WRONG_FILE_EXTENSION(1144, C0770R.string.asanpardakht_message_error_1144),
    UPLOAD_FAILED(1145, C0770R.string.asanpardakht_message_error_1145),
    CAMPAIGN_TIME_IS_OVER(1150, C0770R.string.asanpardakht_message_error_1150),
    BACKUP_DATA_NOT_FOUND(1160, C0770R.string.asanpardakht_message_error_1160),
    CAMPAIGN_INFO_NOT_FOUND(1161, C0770R.string.asanpardakht_message_error_1161),
    DATA_NOT_FOUND(1161, C0770R.string.asanpardakht_message_error_1161),
    WRONG_DATA_TOKEN(1162, C0770R.string.asanpardakht_message_error_1162),
    ACTIVATION_CODE_HAS_ALREADY_BEEN_SENT(1171, C0770R.string.asanpardakht_message_error_1171),
    TOO_MANY_INVALID_ACTIVATION_CODE(1172, C0770R.string.asanpardakht_message_error_1172),
    SERVER_INTERNAL_ERROR(1200, C0770R.string.asanpardakht_message_error_1200),
    UNKNOWN_ERROR(Status.STATUS_UNKNOWN, C0770R.string.asanpardakht_message_error_1201, true),
    SERVER_IS_BUSY(1202, C0770R.string.asanpardakht_message_error_1202),
    NOTIFICATION_CALL_ID_NOT_FOUND(1301, C0770R.string.asanpardakht_message_error_1301);
    
    private static StatusCode[] statusCodes;
    private final int code;
    private int errorMessageResource;
    private boolean isUnknownTransaction;

    private StatusCode(int code) {
        this(r2, r3, code, -1);
    }

    private StatusCode(int code, int errorMessageResource) {
        this(r7, r8, code, errorMessageResource, false);
    }

    private StatusCode(int code, int errorMessageResource, boolean isUnknownTransaction) {
        this.code = code;
        this.errorMessageResource = errorMessageResource;
        this.isUnknownTransaction = isUnknownTransaction;
    }

    public static String getErrorMessage(Context context, int statusCode) {
        String errorMsg = getByCode(statusCode).getErrorMessage(context);
        if (errorMsg != null) {
            return errorMsg;
        }
        return context.getString(C0770R.string.asanpardakht_message_error_general, new Object[]{Integer.valueOf(statusCode)});
    }

    private String getErrorMessage(Context context) {
        if (this == UNDEFINED_STATUS || this == FINANCIAL_ERROR) {
            return null;
        }
        return context.getString(this.errorMessageResource);
    }

    public static StatusCode getByCode(int code) {
        if (code == 0) {
            return SUCCESS;
        }
        if (code >= 1 && code <= 999) {
            return FINANCIAL_ERROR;
        }
        for (StatusCode statusCode : values()) {
            if (statusCode.getCode() == code) {
                return statusCode;
            }
        }
        return UNDEFINED_STATUS;
    }

    public int getCode() {
        return this.code;
    }

    public static int determineTransactionNewStatus(int statusCode) {
        StatusCode stCode = getByCode(statusCode);
        if (statusCode == 0) {
            return 0;
        }
        if (stCode.isUnknownTransaction) {
            return 2;
        }
        if (statusCode == 25 || statusCode == 1111) {
            return 1;
        }
        return 1;
    }

    public static boolean isSuccess(WSResponse wsResponse) {
        return wsResponse != null && wsResponse.getStatus() == SUCCESS;
    }

    public boolean isUnknown() {
        return this.isUnknownTransaction;
    }

    public static StatusCode[] errorStatusCodes() {
        List<StatusCode> statusCodes = new ArrayList(Arrays.asList(values()));
        statusCodes.remove(UNDEFINED_STATUS);
        statusCodes.remove(SUCCESS);
        return (StatusCode[]) statusCodes.toArray(new StatusCode[statusCodes.size()]);
    }
}
