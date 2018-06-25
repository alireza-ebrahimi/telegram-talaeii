package com.persianswitch.sdk.api;

public final class Request {

    public static final class General {
        public static final String HOST_DATA = "host_data";
        public static final String HOST_DATA_SIGN = "host_data_sign";
        public static final String HOST_ID = "host_id";
        public static final String HOST_TRAN_ID = "host_tran_id";
        public static final String PROTOCOL_VERSION = "protocol_version";
        public static final String SECURE_TOKEN = "host_security_token";
    }

    public static final class Payment {
        public static final String HOST_CARD_NO = "host_card_no";
    }

    public static final class Register {
        public static final String ACTIVATION_CODE = "activation_code";
        public static final String ACTIVATION_ID = "activation_id";
        public static final String IMEI = "client_imei";
        public static final String MOBILE_NO = "client_mobile_no";
        public static final String WIFI_MAC = "client_wifi_mac";
    }
}
