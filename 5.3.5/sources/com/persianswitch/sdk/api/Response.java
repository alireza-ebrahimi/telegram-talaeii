package com.persianswitch.sdk.api;

public final class Response {
    public static String BUNDLE_KEY = "result";

    public static final class General {
        public static String HOST_RESPONSE = "host_response";
        public static String HOST_RESPONSE_SIGN = "host_response_sign";
        public static String MESSAGE = "message";
        public static String STATUS_CODE = "status_code";
    }

    public static final class Payment {
        public static String UNIQUE_TRAN_ID = "utran";
    }

    public static final class Register {
        public static String ACTIVATION_ID = com.persianswitch.sdk.api.Request.Register.ACTIVATION_ID;
        public static String WAIT_TIME = "wait_time";
    }

    public static final class Status {
        public static final int STATUS_CANCELED = 2020;
        public static final int STATUS_DECRYPTION_ERROR = 2023;
        public static final int STATUS_FAILED = 1002;
        public static final int STATUS_INVALID_HOST_REQUEST = 1100;
        public static final int STATUS_INVALID_USER_DATA = 2022;
        public static final int STATUS_MOBILE_DEACTIVATED = 1103;
        public static final int STATUS_NO_RESPONSE = 1001;
        public static final int STATUS_PAYMENT_TIMEOUT = 2021;
        public static final int STATUS_REGISTER_NEEDED = 1102;
        public static final int STATUS_SDK_NEED_UPDATE = 1105;
        public static final int STATUS_SERVER_TIME_NOT_SYNCED = 1116;
        public static final int STATUS_SUCCESS = 0;
        public static final int STATUS_UNKNOWN = 1201;
    }
}
