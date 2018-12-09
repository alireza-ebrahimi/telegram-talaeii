package com.persianswitch.sdk.base.webservice;

import android.content.Context;
import com.persianswitch.sdk.C2262R;

public enum StatusCode {
    UNDEFINED_STATUS(-1),
    SUCCESS(0, C2262R.string.asanpardakht_message_success),
    FINANCIAL_ERROR(999),
    UNKNOWN_TRANSACTION_RESULT(1001, C2262R.string.asanpardakht_message_error_1001, true),
    FAILED_TRANSACTION(1002, C2262R.string.asanpardakht_message_error_1002),
    GLOBAL_WARNING(1010, C2262R.string.asanpardakht_message_error_1010),
    DUPLICATE_MERCHANT_TRANSACTION(1098, C2262R.string.asanpardakht_message_error_1098),
    INVALID_MERCHANT_TIME(1099, C2262R.string.asanpardakht_message_error_1099),
    MERCHANT_BAD_REQUEST(1100, C2262R.string.asanpardakht_message_error_1100),
    BAD_REQUEST(1101, C2262R.string.asanpardakht_message_error_1101),
    APP_NOT_REGISTERED(1102, C2262R.string.asanpardakht_message_error_1102),
    APP_HAS_BEEN_DEACTIVATED(1103, C2262R.string.asanpardakht_message_error_1103),
    APP_WORM_STATUS(1104, C2262R.string.asanpardakht_message_error_1104),
    APP_MUST_UPDATE(1105, C2262R.string.asanpardakht_message_error_1105),
    WRONG_ACTIVATION_CODE(1106, C2262R.string.asanpardakht_message_error_1106),
    WRONG_ACTIVATION_DATA(1107, C2262R.string.asanpardakht_message_error_1107),
    APP_HAS_ACTIVE_USER(1108, C2262R.string.asanpardakht_message_error_1108),
    MAX_TRY_PASSWORD(1109, C2262R.string.asanpardakht_message_error_1109),
    SOON_SHAKE_AND_SAVE(1110, C2262R.string.asanpardakht_message_error_1110),
    TRANSACTION_NOT_FOUND(1111, C2262R.string.asanpardakht_message_error_1111),
    TRANSACTION_CAN_NOT_RESTORE(1112, C2262R.string.asanpardakht_message_error_1112),
    INVALID_CARD_NO(1113, C2262R.string.asanpardakht_message_error_1113),
    CARD_NOT_FOUND(1114, C2262R.string.asanpardakht_message_error_1114),
    CARD_HAS_BEEN_DEACTIVATED(1115, C2262R.string.asanpardakht_message_error_1115),
    SYNC_TIME_BY_SERVER_FAILED(1116, C2262R.string.asanpardakht_message_error_1116),
    NEED_APP_RE_VERIFICATION(1117, C2262R.string.asanpardakht_message_error_1117),
    NEED_SEND_CVV2_GLOBAL(1118, C2262R.string.asanpardakht_message_error_1118),
    NEED_SSL_CONNECTION(1119, C2262R.string.asanpardakht_message_error_1119),
    USER_IS_EXIST(1120, C2262R.string.asanpardakht_message_error_1120),
    WRONG_USER_OR_PASSWORD(1121, C2262R.string.asanpardakht_message_error_1121),
    NEED_SEND_CVV2(1123, C2262R.string.asanpardakht_message_error_1123),
    EXPIRATION_DATE_NOT_FOUND(1124, C2262R.string.asanpardakht_message_error_1124),
    MOBILE_OPERATOR_NOT_SPECIFY(1126, C2262R.string.asanpardakht_message_error_1126),
    SECURITY_REQUIREMENT_NOT_PASSED(1127, C2262R.string.asanpardakht_message_error_1127),
    NON_SECURITY_REQUIREMENT_NOT_PASSED(1128, C2262R.string.asanpardakht_message_error_1128),
    SYNC_DATA_CODE_NOT_FOUND(1130, C2262R.string.asanpardakht_message_error_1130),
    INVALID_MERCHANT_CODE(1135, C2262R.string.asanpardakht_message_error_1135),
    WRONG_UPLOAD_INFORMATION(1140, C2262R.string.asanpardakht_message_error_1140),
    WRONG_UPLOAD_FILE_SIZE(1141, C2262R.string.asanpardakht_message_error_1140),
    INVALID_FILE_FOR_UPLOAD(1142, C2262R.string.asanpardakht_message_error_1142),
    TOO_LONG_FILE_FOR_UPLOAD(1143, C2262R.string.asanpardakht_message_error_1143),
    WRONG_FILE_EXTENSION(1144, C2262R.string.asanpardakht_message_error_1144),
    UPLOAD_FAILED(1145, C2262R.string.asanpardakht_message_error_1145),
    CAMPAIGN_TIME_IS_OVER(1150, C2262R.string.asanpardakht_message_error_1150),
    BACKUP_DATA_NOT_FOUND(1160, C2262R.string.asanpardakht_message_error_1160),
    CAMPAIGN_INFO_NOT_FOUND(1161, C2262R.string.asanpardakht_message_error_1161),
    DATA_NOT_FOUND(1161, C2262R.string.asanpardakht_message_error_1161),
    WRONG_DATA_TOKEN(1162, C2262R.string.asanpardakht_message_error_1162),
    ACTIVATION_CODE_HAS_ALREADY_BEEN_SENT(1171, C2262R.string.asanpardakht_message_error_1171),
    TOO_MANY_INVALID_ACTIVATION_CODE(1172, C2262R.string.asanpardakht_message_error_1172),
    SERVER_INTERNAL_ERROR(1200, C2262R.string.asanpardakht_message_error_1200),
    UNKNOWN_ERROR(1201, C2262R.string.asanpardakht_message_error_1201, true),
    SERVER_IS_BUSY(1202, C2262R.string.asanpardakht_message_error_1202),
    NOTIFICATION_CALL_ID_NOT_FOUND(1301, C2262R.string.asanpardakht_message_error_1301);
    
    private final int ac;
    private int ad;
    private boolean ae;

    private StatusCode(int i) {
        this(r2, r3, i, -1);
    }

    private StatusCode(int i, int i2) {
        this(r7, r8, i, i2, false);
    }

    private StatusCode(int i, int i2, boolean z) {
        this.ac = i;
        this.ad = i2;
        this.ae = z;
    }

    /* renamed from: a */
    public static StatusCode m10848a(int i) {
        if (i == 0) {
            return SUCCESS;
        }
        if (i >= 1 && i <= 999) {
            return FINANCIAL_ERROR;
        }
        for (StatusCode statusCode : values()) {
            if (statusCode.m10851a() == i) {
                return statusCode;
            }
        }
        return UNDEFINED_STATUS;
    }

    /* renamed from: a */
    private String m10849a(Context context) {
        return (this == UNDEFINED_STATUS || this == FINANCIAL_ERROR) ? null : context.getString(this.ad);
    }

    /* renamed from: a */
    public static String m10850a(Context context, int i) {
        String a = m10848a(i).m10849a(context);
        if (a != null) {
            return a;
        }
        return context.getString(C2262R.string.asanpardakht_message_error_general, new Object[]{Integer.valueOf(i)});
    }

    /* renamed from: a */
    public int m10851a() {
        return this.ac;
    }

    /* renamed from: b */
    public boolean m10852b() {
        return this.ae;
    }
}
